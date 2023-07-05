package edu.ucema.academics.models.auth;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.data.AttestedCredentialData;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;
import edu.ucema.academics.utilities.ByteArrayAttributeConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Optional;

@Entity
@Table(name = "authenticator")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identifier")
public class Authenticator {
    // ! Attributes
    // * Data
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "public_key")
    @Convert(converter = ByteArrayAttributeConverter.class)
    private ByteArray public_key;

    @Column(name = "authenticator_id")
    @Convert(converter = ByteArrayAttributeConverter.class)
    private ByteArray authenticatorId;

    @Column(name = "signature_count")
    private Long signature_count;

    @Column(name = "aaguid", nullable = true)
    @Convert(converter = ByteArrayAttributeConverter.class)
    private ByteArray aaguid;

    // * Relationships
    @ManyToOne
    private Credential credential;

    // ! Constructors
    public Authenticator() {
    }

    public Authenticator(RegistrationResult result, AuthenticatorAttestationResponse response, Credential credential, String name) {
        this.setPublicKey(result.getPublicKeyCose());
        this.setSignatureCount(result.getSignatureCount());
        this.setCredential(credential);
        this.setName(name);
        this.setAuthenticatorId(result.getKeyId().getId());

        Optional<AttestedCredentialData> attestationData = response.getAttestation().getAuthenticatorData().getAttestedCredentialData();
        if (attestationData.isPresent()) {
            this.setAaguid(attestationData.get().getAaguid());
        }
    }

    // ! Methods
    // * Getters
    public String getIdentifier() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ByteArray getPublicKey() {
        return this.public_key;
    }

    public ByteArray getAuthenticatorId() {
        return this.authenticatorId;
    }

    public Long getSignatureCount() {
        return this.signature_count;
    }

    public ByteArray getAaguid() {
        return this.aaguid;
    }

    public Credential getCredential() {
        return this.credential;
    }

    // * Setters
    public void setIdentifier(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicKey(ByteArray public_key) {
        this.public_key = public_key;
    }

    public void setAuthenticatorId(ByteArray credential_id) {
        this.authenticatorId = credential_id;
    }

    public void setSignatureCount(Long signature_count) {
        this.signature_count = signature_count;
    }

    public void setAaguid(ByteArray aaguid) {
        this.aaguid = aaguid;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
