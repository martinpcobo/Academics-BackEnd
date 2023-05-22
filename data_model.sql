USE academics_dev;

# Record Table Definition
CREATE TABLE credential (
    id VARCHAR(36),
    PRIMARY KEY (id),

    password VARCHAR(100) DEFAULT NULL
);
CREATE TABLE authenticator (
    id VARCHAR(36),
    PRIMARY KEY (id),

    credential_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (credential_id) REFERENCES credential(id) ON DELETE CASCADE,

    name VARCHAR(32),

    public_key VARBINARY(255),
    authenticator_id VARBINARY(255),
    signature_count LONG,
    aaguid VARBINARY(255)
);
CREATE TABLE student (
    user_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (user_id)
);
CREATE TABLE professor (
    user_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE user(
	id VARCHAR(36) ,
	PRIMARY KEY (id),

	credential_id VARCHAR(36),
	FOREIGN KEY (credential_id) REFERENCES credential(id) ON DELETE SET NULL,

	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	unverified_email VARCHAR(100) DEFAULT NULL,
	verified_email VARCHAR(100) NOT NULL,
	email_verification_code VARCHAR(36) DEFAULT NULL,
	handle VARBINARY(64) DEFAULT NULL
);

CREATE TABLE course (
    id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id),

    start_date DATE,
    end_date DATE,
    name VARCHAR(50),
    description VARCHAR(150)
);
CREATE TABLE subject (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE class (
    id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES course(id),

    subject_id VARCHAR(36),
    FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE SET NULL
);

CREATE TABLE grade (
    id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id),


    course_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    student_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student(user_id) ON DELETE CASCADE,
    value FLOAT NOT NULL
);

# M2M Table Definition
CREATE TABLE student_to_course (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_student VARCHAR(36),
    id_course VARCHAR(36),
    PRIMARY KEY(id),
    FOREIGN KEY (id_student) REFERENCES academics_dev.user(id) ON DELETE SET NULL,
    FOREIGN KEY (id_course) REFERENCES academics_dev.course(id) ON DELETE SET NULL
);

CREATE TABLE professor_to_course (
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    id_professor VARCHAR(36),
    FOREIGN KEY (id_professor) REFERENCES user(id) ON DELETE SET NULL,
    id_course VARCHAR(36),
    FOREIGN KEY (id_course) REFERENCES course(id) ON DELETE SET NULL
);