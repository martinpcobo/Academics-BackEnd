# Record Table Definition
CREATE TABLE email (
    id BIGINT NOT NULL AUTO_INCREMENT,
    verified_status BOOLEAN DEFAULT FALSE,
    verified_email VARCHAR(100) DEFAULT NULL,
    unverified_email VARCHAR(100) DEFAULT NULL,

    PRIMARY KEY (id)
);
CREATE TABLE password (
    id BIGINT auto_increment,
    password VARCHAR(100),

    PRIMARY KEY (id)
);
CREATE TABLE user(
	id BIGINT auto_increment,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email_id BIGINT,
	password_id BIGINT,

	PRIMARY KEY (id),
	FOREIGN KEY (email_id) REFERENCES email(id) ON DELETE SET NULL,
	FOREIGN KEY (password_id) REFERENCES password(id) ON DELETE CASCADE
);

CREATE TABLE student (
    id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES user(id)
);

CREATE TABLE professor (
    id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES user(id)
);

CREATE TABLE course (
    id BIGINT NOT NULL,
    start_date DATE,
    end_date DATE,
    PRIMARY KEY (id)
);
CREATE TABLE subject (
    id BIGINT NOT NULL,
    name VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE class (
    id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES course(id),

    subject_id BIGINT,
    FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE SET NULL
);


# M2M Table Definition
CREATE TABLE student_to_class (
    id BIGINT auto_increment,
    id_student BIGINT,
    id_course BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY (id_student) REFERENCES academics_dev.student(id) ON DELETE SET NULL,
    FOREIGN KEY (id_course) REFERENCES academics_dev.course(id) ON DELETE SET NULL
);

CREATE TABLE professor_to_class (
    id BIGINT auto_increment,
    id_professor BIGINT,
    id_course BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY (id_professor) REFERENCES academics_dev.professor(id) ON DELETE SET NULL,
    FOREIGN KEY (id_course) REFERENCES academics_dev.course(id) ON DELETE SET NULL
);