# Record Table Definition
CREATE TABLE password (
    id VARCHAR(36) ,
    password VARCHAR(100),

    PRIMARY KEY (id)
);
CREATE TABLE student (
    id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE professor (
    id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE user(
	id VARCHAR(36) ,
	PRIMARY KEY (id),
	password_id VARCHAR(36),
	FOREIGN KEY (password_id) REFERENCES password(id) ON DELETE CASCADE,
	professor_id VARCHAR(36),
	FOREIGN KEY (professor_id) REFERENCES professor(id) ON DELETE CASCADE,
	student_id VARCHAR(36),
	FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,

	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	unverified_email VARCHAR(100) DEFAULT NULL,
	verified_email VARCHAR(100) DEFAULT NULL,
	email_verification_code VARCHAR(36) DEFAULT NULL
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
    student_id VARCHAR(36) NOT NULL,
    value FLOAT NOT NULL
);


# M2M Table Definition
CREATE TABLE student_to_course (
    id VARCHAR(36) ,
    id_student VARCHAR(36),
    id_course VARCHAR(36),
    PRIMARY KEY(id),
    FOREIGN KEY (id_student) REFERENCES academics_dev.student(id) ON DELETE SET NULL,
    FOREIGN KEY (id_course) REFERENCES academics_dev.course(id) ON DELETE SET NULL
);

CREATE TABLE professor_to_course (
    id VARCHAR(36) ,
    id_professor VARCHAR(36),
    id_course VARCHAR(36),
    PRIMARY KEY(id),
    FOREIGN KEY (id_professor) REFERENCES academics_dev.professor(id) ON DELETE SET NULL,
    FOREIGN KEY (id_course) REFERENCES academics_dev.course(id) ON DELETE SET NULL
);