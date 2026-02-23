-- Customer table
CREATE TABLE emp (
    name         VARCHAR(100),
    meter_number VARCHAR(50) PRIMARY KEY,
    address      VARCHAR(200),
    state        VARCHAR(100),
    city         VARCHAR(100),
    email        VARCHAR(100),
    phone        VARCHAR(15)
);

-- Bill table
CREATE TABLE bill (
    id             SERIAL PRIMARY KEY,
    meter_number   VARCHAR(50),
    month          VARCHAR(20),
    units          INT,
    amount         INT,
    bill_date      DATE,
    payment_status VARCHAR(20) DEFAULT 'PENDING'
);

-- Tax table
CREATE TABLE tax (
    meter_location VARCHAR(100),
    meter_type     VARCHAR(50),
    phase_code     VARCHAR(50),
    bill_type      VARCHAR(50),
    days           INT
);

-- Login table
CREATE TABLE login (
    username VARCHAR(50),
    password VARCHAR(50)
);

-- Insert default login (optional since hardcoded exists)
INSERT INTO login VALUES ('suraj', 'suraj@123');