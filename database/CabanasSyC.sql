CREATE TABLE rol (
    rolId BigInt PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE users (
    userId BigInt PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    passwordHashed VARCHAR(255) NOT NULL,
    hourlyRate DOUBLE PRECISION,
    state BOOLEAN NOT NULL
);

CREATE TABLE userRol (
    userRolId BigInt PRIMARY KEY,
    userId INT REFERENCES users(userId) ON DELETE CASCADE,
    rolId INT REFERENCES rol(rolId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE cabinType (
    cabinTypeId BigInt PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE cabin (
    cabinId BigInt PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    cabinTypeId INT REFERENCES cabinType(cabinTypeId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE equipment (
    equipmentId BigInt PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE cabinEquipment (
    cabinEquipmentId BigInt PRIMARY KEY,
    cabinId INT REFERENCES cabin(cabinId) ON DELETE CASCADE,
    equipmentId INT REFERENCES equipment(equipmentId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE booking (
    bookingId BigInt PRIMARY KEY,
    userId INT REFERENCES users(userId) ON DELETE CASCADE,
    date DATE NOT NULL,
    totolPrice DOUBLE PRECISION NOT NULL,
    state BOOLEAN NOT NULL
);


CREATE TABLE cabinBooking (
    cabinBookingId BigInt PRIMARY KEY,
    bookingId INT REFERENCES booking(bookingId) ON DELETE CASCADE,
    cabinId INT REFERENCES cabin(cabinId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);


CREATE TABLE tour (
    tourId BigInt PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    state BOOLEAN NOT NULL,
    startTime TIME,
    endTime TIME
);


CREATE TABLE bookingTour (
    bookingTourId BigInt PRIMARY KEY,
    bookingId INT REFERENCES booking(bookingId) ON DELETE CASCADE,
    tourId INT REFERENCES tour(tourId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE materialType (
    materialTypeId BigInt PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE material (
    materialId BigInt PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    stock INT NOT NULL,
    materialTypeId INT REFERENCES materialType(materialTypeId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE materialRequest (
    materialRequestId BigInt PRIMARY KEY,
    userId INT REFERENCES users(userId) ON DELETE CASCADE,
    materialId INT REFERENCES material(materialId) ON DELETE CASCADE,
    quantity INT NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE workingDay (
    workingDayId BigInt PRIMARY KEY,
    userId INT REFERENCES users(userId) ON DELETE CASCADE,
    date DATE NOT NULL,
    checkInTime TIME NOT NULL,
    checkOutTime TIME NOT NULL
);