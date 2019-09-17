CREATE TABLE comment
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL DEFAULT 0,
    type INT NOT NULL DEFAULT 0,
    commentator INT NOT NULL DEFAULT 0,
    gmt_create BIGINT NOT NULL DEFAULT 0,
    gmt_modified BIGINT NOT NULL DEFAULT 0,
    like_count BIGINT DEFAULT 0,
    content VARCHAR(1024) NULL default ''
);