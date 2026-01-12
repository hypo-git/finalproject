CREATE TABLE usersettings_aud
(
    rev               int    NOT NULL,
    revtype           smallint,
    id                bigint NOT NULL,
    user_id           bigint,
    color_scheme      varchar(255),
    color_scheme_mod  bit,
    font_size         varchar(255),
    font_size_mod     bit,
    primary_color     varchar(255),
    primary_color_mod bit,
    CONSTRAINT pk_usersettings_aud PRIMARY KEY (rev, id)
)
    GO

ALTER TABLE usersettings_aud
    ADD CONSTRAINT FK_USERSETTINGS_AUD_ON_REV FOREIGN KEY (rev) REFERENCES revinfo (rev)
    GO