
CREATE TABLE address
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime NULL,
    modified_at    datetime NULL,
    base           VARCHAR(255) NOT NULL,
    detail         VARCHAR(255) NULL,
    postal         INT NULL,
    user_id        BIGINT NULL,
    company_id     BIGINT NULL,
    institution_id BIGINT NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE affiliation
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime NULL,
    modified_at   datetime NULL,
    is_supervisor BIT(1) NOT NULL,
    approval      BIT(1) NOT NULL,
    department_id BIGINT NULL,
    user_id       BIGINT NULL,
    CONSTRAINT pk_affiliation PRIMARY KEY (id)
);

CREATE TABLE attend_history
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime NULL,
    modified_at       datetime NULL,
    rate              INT NULL,
    attendance        BIT(1) NULL,
    enrollment_id     BIGINT NULL,
    live_streaming_id BIGINT NULL,
    CONSTRAINT pk_attend_history PRIMARY KEY (id)
);

CREATE TABLE board
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NULL,
    type        VARCHAR(255) NOT NULL,
    user_id     BIGINT NULL,
    CONSTRAINT pk_board PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    content     VARCHAR(255) NOT NULL,
    board_id    BIGINT NULL,
    user_id     BIGINT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
);

CREATE TABLE company
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime NULL,
    modified_at     datetime NULL,
    name            VARCHAR(255) NOT NULL,
    owner_name      VARCHAR(255) NULL,
    business_number VARCHAR(255) NOT NULL,
    email           VARCHAR(100) NULL,
    email_domain    VARCHAR(50)  NOT NULL,
    phone           VARCHAR(255) NOT NULL,
    CONSTRAINT pk_company PRIMARY KEY (id)
);

CREATE TABLE contents
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime NULL,
    modified_at   datetime NULL,
    title         VARCHAR(100) NOT NULL,
    type          VARCHAR(255) NULL,
    idx           INT          NOT NULL,
    curriculum_id BIGINT NULL,
    CONSTRAINT pk_contents PRIMARY KEY (id)
);

CREATE TABLE contents_history
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime NULL,
    modified_at   datetime NULL,
    state         VARCHAR(255) NOT NULL,
    progress      BIGINT NULL,
    enrollment_id BIGINT NULL,
    contents_id   BIGINT NULL,
    CONSTRAINT pk_contents_history PRIMARY KEY (id)
);

CREATE TABLE contents_material
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    `path`      VARCHAR(255) NOT NULL,
    origin_name VARCHAR(255) NOT NULL,
    saved_name  VARCHAR(255) NOT NULL,
    postfix     VARCHAR(255) NOT NULL,
    contents_id BIGINT NULL,
    CONSTRAINT pk_contents_material PRIMARY KEY (id)
);

CREATE TABLE contents_quiz
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    question    VARCHAR(255) NULL,
    answer      VARCHAR(255) NOT NULL,
    solution    VARCHAR(255) NULL,
    contents_id BIGINT NULL,
    CONSTRAINT pk_contents_quiz PRIMARY KEY (id)
);

CREATE TABLE contents_video
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    `path`      VARCHAR(255) NOT NULL,
    origin_name VARCHAR(255) NOT NULL,
    saved_name  VARCHAR(255) NOT NULL,
    postfix     VARCHAR(255) NOT NULL,
    time_amount BIGINT NULL,
    contents_id BIGINT NULL,
    CONSTRAINT pk_contents_video PRIMARY KEY (id)
);

CREATE TABLE course_provide
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime NULL,
    modified_at     datetime NULL,
    begin_date      date         NOT NULL,
    end_date        date         NOT NULL,
    state           VARCHAR(255) NOT NULL,
    attendee_count  INT          NOT NULL,
    deposit         BIGINT NULL,
    supervisor_id   BIGINT NOT NULL,
    company_id      BIGINT NOT NULL,
    institution_id  BIGINT NOT NULL,
    course_id       BIGINT NULL,
    CONSTRAINT pk_course_provide PRIMARY KEY (id)
);

CREATE TABLE course
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime NULL,
    modified_at    datetime NULL,
    title          VARCHAR(255) NOT NULL,
    introduction   VARCHAR(255) NULL,
    category       VARCHAR(255) NOT NULL,
    institution_id BIGINT NULL,
    tutor_id       BIGINT NULL,
    image_id       BIGINT NULL,
    type           VARCHAR(255) NULL,
    CONSTRAINT pk_course PRIMARY KEY (id)
);

CREATE TABLE course_board
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NULL,
    type        VARCHAR(20)  NOT NULL,
    course_id   BIGINT NULL,
    user_id     BIGINT NULL,
    CONSTRAINT pk_course_board PRIMARY KEY (id)
);

CREATE TABLE course_comment
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    content     VARCHAR(255) NULL,
    post_id     BIGINT NULL,
    user_id     BIGINT NULL,
    CONSTRAINT pk_course_comment PRIMARY KEY (id)
);

CREATE TABLE curriculum
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    title       VARCHAR(255) NOT NULL,
    idx         INT          NOT NULL,
    course_id   BIGINT NULL,
    CONSTRAINT pk_curriculum PRIMARY KEY (id)
);

CREATE TABLE department
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    name        VARCHAR(50) NOT NULL,
    company_id  BIGINT NULL,
    CONSTRAINT pk_department PRIMARY KEY (id)
);

CREATE TABLE enrollment
(
    id                     BIGINT AUTO_INCREMENT NOT NULL,
    created_at             datetime NULL,
    modified_at            datetime NULL,
    state                  VARCHAR(255) NOT NULL,
    certificate_date       datetime NULL,
    user_id                BIGINT NULL,
    course_provide_id      BIGINT NULL,
    CONSTRAINT pk_enrollment PRIMARY KEY (id)
);

CREATE TABLE exam
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    title       VARCHAR(100) NULL,
    course_id   BIGINT NULL,
    CONSTRAINT pk_exam PRIMARY KEY (id)
);

CREATE TABLE exam_option
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime NULL,
    modified_at      datetime NULL,
    text             VARCHAR(255) NULL,
    idx              INT NOT NULL,
    exam_question_id BIGINT NULL,
    CONSTRAINT pk_exam_option PRIMARY KEY (id)
);

CREATE TABLE exam_question
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    question    VARCHAR(255) NULL,
    answer      INT NOT NULL,
    exam_id     BIGINT NULL,
    CONSTRAINT pk_exam_question PRIMARY KEY (id)
);

CREATE TABLE image
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    `path`      VARCHAR(255) NOT NULL,
    origin_name VARCHAR(255) NOT NULL,
    saved_name  VARCHAR(255) NOT NULL,
    postfix     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_image PRIMARY KEY (id)
);

CREATE TABLE institution
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime NULL,
    modified_at      datetime NULL,
    name             VARCHAR(255) NOT NULL,
    owner_name       VARCHAR(255) NULL,
    business_number  VARCHAR(255) NOT NULL,
    certified_number VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NULL,
    phone            VARCHAR(20) NULL,
    CONSTRAINT pk_institution PRIMARY KEY (id)
);

CREATE TABLE live_quiz
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    question    VARCHAR(255) NULL,
    answer      INT NOT NULL,
    solution    VARCHAR(255) NULL,
    live_id     BIGINT NULL,
    CONSTRAINT pk_live_quiz PRIMARY KEY (id)
);

CREATE TABLE live_quiz_answer
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime NULL,
    modified_at       datetime NULL,
    answer            INT NULL,
    attend_history_id BIGINT NULL,
    live_quiz_id      BIGINT NULL,
    CONSTRAINT pk_live_quiz_answer PRIMARY KEY (id)
);

CREATE TABLE live_quiz_option
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_at   datetime NULL,
    modified_at  datetime NULL,
    text         VARCHAR(255) NULL,
    idx          INT NOT NULL,
    live_quiz_id BIGINT NULL,
    CONSTRAINT pk_live_quiz_option PRIMARY KEY (id)
);

CREATE TABLE live_streaming
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    title       VARCHAR(100) NOT NULL,
    start_time  datetime NOT NULL,
    end_time    datetime NULL,
    state       VARCHAR(20) NOT NULL,
    course_provide_id   BIGINT NULL,
    CONSTRAINT pk_live_streaming PRIMARY KEY (id)
);

CREATE TABLE manager
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime NULL,
    modified_at    datetime NULL,
    user_id        BIGINT NULL,
    institution_id BIGINT NULL,
    CONSTRAINT pk_manager PRIMARY KEY (id)
);

CREATE TABLE post_message
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NULL,
    is_delete   BIT(1) DEFAULT 0,
    user_id     BIGINT NULL,
    CONSTRAINT pk_post_message PRIMARY KEY (id)
);

CREATE TABLE quiz_option
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    text        VARCHAR(255) NULL,
    idx         INT NOT NULL,
    quiz_id     BIGINT NULL,
    CONSTRAINT pk_quiz_option PRIMARY KEY (id)
);

CREATE TABLE target_user
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime NULL,
    modified_at     datetime NULL,
    is_read         BIT(1) NOT NULL,
    is_delete         BIT(1) NOT NULL,
    post_message_id BIGINT NULL,
    user_id         BIGINT NULL,
    CONSTRAINT pk_target_user PRIMARY KEY (id)
);

CREATE TABLE tutor
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    introduction   VARCHAR(255) NULL,
    user_id     BIGINT NULL,
    CONSTRAINT pk_tutor PRIMARY KEY (id)
);

CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    username    VARCHAR(20)  NOT NULL,
    name        VARCHAR(20)  NOT NULL,
    password    VARCHAR(60)  NOT NULL,
    phone       VARCHAR(20),
    birth       date         NOT NULL,
    email       VARCHAR(100) NOT NULL,
    gender      VARCHAR(20)  NOT NULL,
    `role`      VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_choice
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime NULL,
    modified_at      datetime NULL,
    choice           INT NOT NULL,
    enrollment_id    BIGINT NULL,
    exam_question_id BIGINT NULL,
    CONSTRAINT pk_users_choice PRIMARY KEY (id)
);

ALTER TABLE affiliation
    ADD CONSTRAINT uc_affiliation_user UNIQUE (user_id);

ALTER TABLE contents_material
    ADD CONSTRAINT uc_contents_material_contents UNIQUE (contents_id);

ALTER TABLE contents_video
    ADD CONSTRAINT uc_contents_video_contents UNIQUE (contents_id);

ALTER TABLE course
    ADD CONSTRAINT uc_course_image UNIQUE (image_id);

ALTER TABLE manager
    ADD CONSTRAINT uc_manager_user UNIQUE (user_id);

ALTER TABLE tutor
    ADD CONSTRAINT uc_tutor_user UNIQUE (user_id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_INSTITUTION FOREIGN KEY (institution_id) REFERENCES institution (id);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE affiliation
    ADD CONSTRAINT FK_AFFILIATION_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE affiliation
    ADD CONSTRAINT FK_AFFILIATION_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE attend_history
    ADD CONSTRAINT FK_ATTEND_HISTORY_ON_ENROLLMENT FOREIGN KEY (enrollment_id) REFERENCES enrollment (id);

ALTER TABLE attend_history
    ADD CONSTRAINT FK_ATTEND_HISTORY_ON_LIVE_STREAMING FOREIGN KEY (live_streaming_id) REFERENCES live_streaming (id);

ALTER TABLE board
    ADD CONSTRAINT FK_BOARD_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_BOARD FOREIGN KEY (board_id) REFERENCES board (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE contents_history
    ADD CONSTRAINT FK_CONTENTS_HISTORY_ON_CONTENTS FOREIGN KEY (contents_id) REFERENCES contents (id);

ALTER TABLE contents_history
    ADD CONSTRAINT FK_CONTENTS_HISTORY_ON_ENROLLMENT FOREIGN KEY (enrollment_id) REFERENCES enrollment (id);

ALTER TABLE contents_material
    ADD CONSTRAINT FK_CONTENTS_MATERIAL_ON_CONTENTS FOREIGN KEY (contents_id) REFERENCES contents (id);

ALTER TABLE contents
    ADD CONSTRAINT FK_CONTENTS_ON_CURRICULUM FOREIGN KEY (curriculum_id) REFERENCES curriculum (id);

ALTER TABLE contents_quiz
    ADD CONSTRAINT FK_CONTENTS_QUIZ_ON_CONTENTS FOREIGN KEY (contents_id) REFERENCES contents (id);

ALTER TABLE contents_video
    ADD CONSTRAINT FK_CONTENTS_VIDEO_ON_CONTENTS FOREIGN KEY (contents_id) REFERENCES contents (id);

ALTER TABLE course_provide
    ADD CONSTRAINT FK_COURSE_PROVIDE_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);

ALTER TABLE course_provide
    ADD CONSTRAINT FK_COURSE_PROVIDE_ON_INSTITUTION FOREIGN KEY (institution_id) REFERENCES institution (id);

ALTER TABLE course_provide
    ADD CONSTRAINT FK_COURSE_PROVIDE_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE course_provide
    ADD CONSTRAINT FK_COURSE_PROVIDE_ON_AFFILIATION FOREIGN KEY (supervisor_id) REFERENCES affiliation (id);

ALTER TABLE course_board
    ADD CONSTRAINT FK_COURSE_BOARD_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE course_board
    ADD CONSTRAINT FK_COURSE_BOARD_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE course_comment
    ADD CONSTRAINT FK_COURSE_COMMENT_ON_POST FOREIGN KEY (post_id) REFERENCES course_board (id);

ALTER TABLE course_comment
    ADD CONSTRAINT FK_COURSE_COMMENT_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE course
    ADD CONSTRAINT FK_COURSE_ON_IMAGE FOREIGN KEY (image_id) REFERENCES image (id);

ALTER TABLE course
    ADD CONSTRAINT FK_COURSE_ON_INSTITUTION FOREIGN KEY (institution_id) REFERENCES institution (id);

ALTER TABLE course
    ADD CONSTRAINT FK_COURSE_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tutor (id);

ALTER TABLE curriculum
    ADD CONSTRAINT FK_CURRICULUM_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE department
    ADD CONSTRAINT FK_DEPARTMENT_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);

ALTER TABLE enrollment
    ADD CONSTRAINT FK_ENROLLMENT_ON_COURSE_PROVIDE FOREIGN KEY (course_provide_id) REFERENCES course_provide (id);

ALTER TABLE enrollment
    ADD CONSTRAINT FK_ENROLLMENT_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE exam
    ADD CONSTRAINT FK_EXAM_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE exam_option
    ADD CONSTRAINT FK_EXAM_OPTION_ON_EXAM_QUESTION FOREIGN KEY (exam_question_id) REFERENCES exam_question (id);

ALTER TABLE exam_question
    ADD CONSTRAINT FK_EXAM_QUESTION_ON_EXAM FOREIGN KEY (exam_id) REFERENCES exam (id);

ALTER TABLE live_quiz_answer
    ADD CONSTRAINT FK_LIVE_QUIZ_ANSWER_ON_ATTEND_HISTORY FOREIGN KEY (attend_history_id) REFERENCES attend_history (id);

ALTER TABLE live_quiz_answer
    ADD CONSTRAINT FK_LIVE_QUIZ_ANSWER_ON_LIVE_QUIZ FOREIGN KEY (live_quiz_id) REFERENCES live_quiz (id);

ALTER TABLE live_quiz
    ADD CONSTRAINT FK_LIVE_QUIZ_ON_LIVE FOREIGN KEY (live_id) REFERENCES live_streaming (id);

ALTER TABLE live_quiz_option
    ADD CONSTRAINT FK_LIVE_QUIZ_OPTION_ON_LIVE_QUIZ FOREIGN KEY (live_quiz_id) REFERENCES live_quiz (id);

ALTER TABLE live_streaming
    ADD CONSTRAINT FK_LIVE_STREAMING_ON_COURSE_PROVIDE FOREIGN KEY (course_provide_id) REFERENCES course_provide (id);

ALTER TABLE manager
    ADD CONSTRAINT FK_MANAGER_ON_INSTITUTION FOREIGN KEY (institution_id) REFERENCES institution (id);

ALTER TABLE manager
    ADD CONSTRAINT FK_MANAGER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE post_message
    ADD CONSTRAINT FK_POST_MESSAGE_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE quiz_option
    ADD CONSTRAINT FK_QUIZ_OPTION_ON_QUIZ FOREIGN KEY (quiz_id) REFERENCES contents_quiz (id);

ALTER TABLE target_user
    ADD CONSTRAINT FK_TARGET_USER_ON_POST_MESSAGE FOREIGN KEY (post_message_id) REFERENCES post_message (id);

ALTER TABLE target_user
    ADD CONSTRAINT FK_TARGET_USER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE tutor
    ADD CONSTRAINT FK_TUTOR_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_choice
    ADD CONSTRAINT FK_USERS_CHOICE_ON_ENROLLMENT FOREIGN KEY (enrollment_id) REFERENCES enrollment (id);

ALTER TABLE users_choice
    ADD CONSTRAINT FK_USERS_CHOICE_ON_EXAM_QUESTION FOREIGN KEY (exam_question_id) REFERENCES exam_question (id);

create view vw_certificate as
select e.id as certificate_number
     , u.name as name
     , cm.name as company
     , i.name as institution
     , c.title as course
     , e.created_at as start_date
     , e.certificate_date as end_date
from enrollment as e, users as u, company as cm, institution as i, course as c, course_provide as ct
where e.user_id = u.id
  and e.course_provide_id = ct.id
  and ct.company_id = cm.id
  and ct.course_id = c.id
  and c.institution_id = i.id;

DELIMITER //

CREATE TRIGGER trg_enrollment_insert AFTER INSERT ON enrollment
    FOR EACH ROW
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE course_id BIGINT;
    DECLARE curriculum_id BIGINT;
    DECLARE content_id BIGINT;
    DECLARE curriculum_cursor CURSOR FOR SELECT id FROM curriculum WHERE course_id = course_id;
    DECLARE content_cursor CURSOR FOR SELECT id FROM contents WHERE curriculum_id = curriculum_id;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    SELECT c.course_id INTO course_id
    FROM course_provide c
    WHERE c.id = NEW.course_provide_id;

    OPEN curriculum_cursor;

    curriculum_loop: LOOP
        FETCH curriculum_cursor INTO curriculum_id;
        IF done THEN
            LEAVE curriculum_loop;
        END IF;

        OPEN content_cursor;

        content_loop: LOOP
            FETCH content_cursor INTO content_id;
            IF done THEN
                LEAVE content_loop;
            END IF;

            INSERT INTO contents_history (created_at, modified_at, state, progress, enrollment_id, contents_id)
            VALUES (NOW(), NOW(), 'NOT_STARTED', 0, NEW.id, content_id);

        END LOOP content_loop;

        CLOSE content_cursor;

    END LOOP curriculum_loop;

    CLOSE curriculum_cursor;

END//

DELIMITER ;