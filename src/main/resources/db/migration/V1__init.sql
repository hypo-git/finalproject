CREATE TABLE entity_definition
(
    id            bigint IDENTITY (1, 1) NOT NULL,
    created_at    datetime     NOT NULL,
    updated_at    datetime     NOT NULL,
    created_by    varchar(255) NOT NULL,
    updated_by    varchar(255) NOT NULL,
    entity_type   varchar(255) NOT NULL,
    entity_code   varchar(65)  NOT NULL,
    entity_name   varchar(100) NOT NULL,
    description   varchar(2000),
    singular_name varchar(500),
    plural_name   varchar(500),
    icon_name     varchar(255),
    CONSTRAINT pk_entity_definition PRIMARY KEY (id)
)
    GO

CREATE TABLE entity_definition_aud
(
    rev               int    NOT NULL,
    created_at        datetime,
    created_at_mod    bit,
    updated_at        datetime,
    updated_at_mod    bit,
    created_by        varchar(255),
    created_by_mod    bit,
    updated_by        varchar(255),
    updated_by_mod    bit,
    revtype           smallint,
    id                bigint NOT NULL,
    entity_type       varchar(255),
    entity_type_mod   bit,
    entity_code       varchar(255),
    entity_code_mod   bit,
    entity_name       varchar(255),
    entity_name_mod   bit,
    description       varchar(2000),
    description_mod   bit,
    singular_name     varchar(500),
    singular_name_mod bit,
    plural_name       varchar(500),
    plural_name_mod   bit,
    icon_name         varchar(255),
    icon_name_mod     bit,
    CONSTRAINT pk_entity_definition_aud PRIMARY KEY (rev, id)
)
    GO

CREATE TABLE revinfo
(
    rev       int IDENTITY (1, 1) NOT NULL,
    revtstmp  bigint,
    user_name varchar(255),
    CONSTRAINT pk_revinfo PRIMARY KEY (rev)
)
    GO

CREATE TABLE users
(
    id             bigint IDENTITY (1, 1) NOT NULL,
    created_at     datetime     NOT NULL,
    updated_at     datetime     NOT NULL,
    created_by     varchar(255) NOT NULL,
    updated_by     varchar(255) NOT NULL,
    username       varchar(255) NOT NULL,
    email          varchar(255) NOT NULL,
    password       varchar(255) NOT NULL,
    role           varchar(255) NOT NULL,
    enabled        bit          NOT NULL,
    logo_user_name varchar(255),
    first_name     varchar(255) NOT NULL,
    last_name      varchar(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
)
    GO

CREATE TABLE users_aud
(
    rev                int    NOT NULL,
    created_at         datetime,
    created_at_mod     bit,
    updated_at         datetime,
    updated_at_mod     bit,
    created_by         varchar(255),
    created_by_mod     bit,
    updated_by         varchar(255),
    updated_by_mod     bit,
    revtype            smallint,
    id                 bigint NOT NULL,
    username           varchar(255),
    username_mod       bit,
    email              varchar(255),
    email_mod          bit,
    password           varchar(255),
    password_mod       bit,
    role               varchar(255),
    role_mod           bit,
    enabled            bit,
    enabled_mod        bit,
    logo_user_name     varchar(255),
    logo_user_name_mod bit,
    first_name         varchar(255),
    first_name_mod     bit,
    last_name          varchar(255),
    last_name_mod      bit,
    CONSTRAINT pk_users_aud PRIMARY KEY (rev, id)
)
    GO

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email)
    GO

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username)
    GO

ALTER TABLE entity_definition_aud
    ADD CONSTRAINT FK_ENTITY_DEFINITION_AUD_ON_REV FOREIGN KEY (rev) REFERENCES revinfo (rev)
    GO

ALTER TABLE users_aud
    ADD CONSTRAINT FK_USERS_AUD_ON_REV FOREIGN KEY (rev) REFERENCES revinfo (rev)
    GO