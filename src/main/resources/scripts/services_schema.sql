CREATE TABLE IF NOT EXISTS services (
	service_id INT AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    last_updated DATETIME NOT NULL,
    status TINYINT NOT NULL,
    PRIMARY KEY (service_id)
) ENGINE=INNODB;