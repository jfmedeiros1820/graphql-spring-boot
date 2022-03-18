CREATE TABLE organization (
    id  INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE department (
    id  INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    organization_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE TABLE employee (
    id  INTEGER NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    position VARCHAR(128) NOT NULL,
    salary FLOAT NOT NULL,
    age INTEGER NOT NULL,
    department_id INTEGER,
    organization_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (organization_id) REFERENCES organization(id),
    FOREIGN KEY (department_id) REFERENCES department(id)
);