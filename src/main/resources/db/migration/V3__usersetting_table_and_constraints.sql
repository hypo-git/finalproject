CREATE TABLE usersettings
(
    id            bigint IDENTITY (1, 1) NOT NULL,
    user_id       bigint NOT NULL,
    color_scheme  varchar(255) DEFAULT 'light',
    font_size     varchar(255) DEFAULT 'md',
    primary_color varchar(255) DEFAULT 'blue',
    CONSTRAINT pk_usersettings PRIMARY KEY (id)
)
    GO

ALTER TABLE users_aud
    ADD settings_mod bit
    GO

ALTER TABLE usersettings
    ADD CONSTRAINT uc_usersettings_user UNIQUE (user_id)
    GO

ALTER TABLE usersettings
    ADD CONSTRAINT FK_USERSETTINGS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id)
    GO