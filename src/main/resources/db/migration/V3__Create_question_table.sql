CREATE TABLE question
(
    id INT unsigned NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL DEFAULT '',
    description TEXT,
    creator INT NOT NULL DEFAULT 0,
    comment_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    tag VARCHAR(256) NOT NULL DEFAULT '',
		gmt_create BIGINT NOT NULL DEFAULT 0,
    gmt_modified BIGINT NOT NULL DEFAULT 0,
		PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;