CREATE TABLE project (
  id          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  external_id varchar(25)         NOT NULL,
  created_on  datetime(3)         NOT NULL,
  updated_on  datetime(3)         NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY external_id (external_id),
  KEY created_on (created_on),
  KEY updated_on (updated_on)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE test_case (
  id          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  external_id varchar(50)         NOT NULL,
  project_id  bigint(20) unsigned NOT NULL,
  name        varchar(190)        NOT NULL,
  purpose     varchar(100)        NOT NULL,
  created_on  datetime(3)         NOT NULL,
  updated_on  datetime(3)         NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY external_id (external_id),
  KEY project_id (project_id),
  KEY created_on (created_on),
  KEY updated_on (updated_on),
  FOREIGN KEY (project_id) REFERENCES project (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
