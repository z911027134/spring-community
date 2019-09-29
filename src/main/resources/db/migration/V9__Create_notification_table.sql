CREATE TABLE notification
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notifier BIGINT NOT NULL DEFAULT 0,
    receiver BIGINT NOT NULL DEFAULT 0,
    outer_id BIGINT NOT NULL DEFAULT 0,
    type INT NOT NULL DEFAULT 0,
    status INT NOT NULL DEFAULT 0,
    notifier_name varchar(100) NOT NULL DEFAULT '',
    outer_title varchar(255) NOT NULL DEFAULT '',
    gmt_create BIGINT NOT NULL DEFAULT 0
);