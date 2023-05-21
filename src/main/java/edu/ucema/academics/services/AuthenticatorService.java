package edu.ucema.academics.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yubico.webauthn.*;
import com.yubico.webauthn.data.*;
import com.yubico.webauthn.exception.RegistrationFailedException;
import edu.ucema.academics.authentication.jwt.JwtUtilities;
import edu.ucema.academics.models.auth.Authenticator;
import edu.ucema.academics.models.users.User;
import edu.ucema.academics.repositories.AuthenticatorRepository;
import edu.ucema.academics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticatorService implements CredentialRepository {
    // ! Injected Dependencies
    @Autowired
    private JwtUtilities jwt_utilities;
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private PasswordEncoder password_encoder;
    @Autowired
    private AuthenticatorRepository authenticator_repository;
    // ! Attributes
    private final RelyingParty relyingParty;
    private final Map<String, PublicKeyCredentialCreationOptions> requestOptionMap = new HashMap<>();
    private final Map<String, AssertionRequest> assertionRequestMap = new HashMap<>();

    // ! Constructor
    public AuthenticatorService() {
        RelyingPartyIdentity relyingPartyIdentity = RelyingPartyIdentity.builder()
                .id("localhost")
                .name("UCEMA Academics")
                .build();

        this.relyingParty = RelyingParty.builder()
                .identity(relyingPartyIdentity)
                .credentialRepository(this)
                .build();
    }

    // ! Business Methods
    // * Authenticate User
    public String authenticate(String email, String password) {
        Optional<User> user = this.user_repository.findByVerifiedEmail(email);
        if (user.isEmpty()) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        User db_user = user.get();

        if (db_user.getCredential() == null || !password_encoder.matches(password, db_user.getCredential().getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        return jwt_utilities.generateToken(db_user.getVerifiedEmail(), db_user.getIdentifier());
    }

    /*
    // * Get Authenticated User
    public Optional<User> getUserByUsername(String username) {
        return this.user_repository.findByVerifiedEmail(username);
    }
    */

    // * WebAuthn Start Auth Registration
    public ResponseEntity<?> startAuthnRegistration(String username) throws JsonProcessingException, BadCredentialsException {
        Optional<User> opt_db_user = user_repository.findByVerifiedEmail(username);
        if (opt_db_user.isEmpty()) {
            return ResponseEntity.status(404).body("Could not find the selected User, please try again later.");
        }

        PublicKeyCredentialCreationOptions registration = relyingParty.startRegistration(
                StartRegistrationOptions.builder()
                        .user(
                                UserIdentity.builder()
                                        .name(opt_db_user.get().getVerifiedEmail())
                                        .displayName(opt_db_user.get().getName())
                                        .id(opt_db_user.get().getHandle())
                                        .build()
                        )
                        .build());

        this.requestOptionMap.put(opt_db_user.get().getIdentifier(), registration);
        return ResponseEntity.status(HttpStatus.OK).body(registration.toCredentialsCreateJson());
    }

    public ResponseEntity<?> endAuthnRegistration(String public_key, String username, String credential_name) throws IOException, RegistrationFailedException {
        Optional<User> opt_db_user = user_repository.findByVerifiedEmail(username);
        if (opt_db_user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find the selected User, please try again later.");
        }

        PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> pkc = PublicKeyCredential.parseRegistrationResponseJson(public_key);

        PublicKeyCredentialCreationOptions registration_options = this.requestOptionMap.get(opt_db_user.get().getIdentifier());
        if (registration_options == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The selected User did not start the Authn Registration Process, please start the process before attempting to finish it.");
        }

        RegistrationResult registration_result = relyingParty.finishRegistration(FinishRegistrationOptions.builder()
                .request(registration_options)
                .response(pkc)
                .build());

        Authenticator auth_instance = new Authenticator(registration_result, pkc.getResponse(), opt_db_user.get().getCredential(), credential_name);
        authenticator_repository.save(auth_instance);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully registered the Authn Element for the selected User.");
    }


    // ! Authn Required Methods
    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {
        Optional<User> user = user_repository.findByVerifiedEmail(username);

        if (user.isEmpty()) return null;

        List<Authenticator> auth = authenticator_repository.findAllByCredential(user.get().getCredential());

        return auth.stream()
                .map(
                        credential ->
                                PublicKeyCredentialDescriptor.builder()
                                        .id(credential.getAuthenticatorId())
                                        .build())
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ByteArray> getUserHandleForUsername(String username) {
        Optional<User> user = user_repository.findByVerifiedEmail(username);
        return user.map(User::getHandle);
    }

    @Override
    public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
        Optional<User> user = user_repository.findByHandle(userHandle);
        return user.map(User::getUsername);
    }

    @Override
    public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {
        Optional<Authenticator> auth = authenticator_repository.findByCredentialId(credentialId);
        return auth.map(
                authenticator ->
                        RegisteredCredential.builder()
                                .credentialId(authenticator.getAuthenticatorId())
                                .userHandle(authenticator.getCredential().getUser().getHandle())
                                .publicKeyCose(authenticator.getPublicKey())
                                .signatureCount(authenticator.getSignatureCount())
                                .build()
        );
    }

    @Override
    public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
        List<Authenticator> auth = authenticator_repository.findAllByCredentialId(credentialId);
        return auth.stream()
                .map(
                        authenticator ->
                                RegisteredCredential.builder()
                                        .credentialId(authenticator.getAuthenticatorId())
                                        .userHandle(authenticator.getCredential().getUser().getHandle())
                                        .publicKeyCose(authenticator.getPublicKey())
                                        .signatureCount(authenticator.getSignatureCount())
                                        .build())
                .collect(Collectors.toSet());
    }
}
