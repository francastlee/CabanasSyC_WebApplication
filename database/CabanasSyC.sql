CREATE TABLE rol (
    rolId BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE users (
    userId BIGSERIAL PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    passwordHashed VARCHAR(255) NOT NULL,
    hourlyRate DOUBLE PRECISION,
    state BOOLEAN NOT NULL
);

CREATE TABLE userRol (
    userRolId BIGSERIAL PRIMARY KEY,
    userId BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    rolId BIGINT REFERENCES rol(rolId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE cabinType (
    cabinTypeId BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE cabin (
    cabinId BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    cabinTypeId BIGINT REFERENCES cabinType(cabinTypeId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE equipment (
    equipmentId BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE cabinEquipment (
    cabinEquipmentId BIGSERIAL PRIMARY KEY,
    cabinId BIGINT REFERENCES cabin(cabinId) ON DELETE CASCADE,
    equipmentId BIGINT REFERENCES equipment(equipmentId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE booking (
    bookingId BIGSERIAL PRIMARY KEY,
    userId BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    date DATE NOT NULL,
    totalPrice DOUBLE PRECISION NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE cabinBooking (
    cabinBookingId BIGSERIAL PRIMARY KEY,
    bookingId BIGINT REFERENCES booking(bookingId) ON DELETE CASCADE,
    cabinId BIGINT REFERENCES cabin(cabinId) ON DELETE CASCADE,
    adultsQuantity INT NOT NULL,
    childrenQuantity INT NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE tour (
    tourId BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    state BOOLEAN NOT NULL,
    startTime TIME,
    endTime TIME
);

CREATE TABLE bookingTour (
    bookingTourId BIGSERIAL PRIMARY KEY,
    bookingId BIGINT REFERENCES booking(bookingId) ON DELETE CASCADE,
    tourId BIGINT REFERENCES tour(tourId) ON DELETE CASCADE,
    people INT NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE materialType (
    materialTypeId BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE material (
    materialId BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    stock INT NOT NULL,
    materialTypeId BIGINT REFERENCES materialType(materialTypeId) ON DELETE CASCADE,
    state BOOLEAN NOT NULL
);

CREATE TABLE materialRequest (
    materialRequestId BIGSERIAL PRIMARY KEY,
    userId BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    materialId BIGINT REFERENCES material(materialId) ON DELETE CASCADE,
    quantity INT NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE workingDay (
    workingDayId BIGSERIAL PRIMARY KEY,
    userId BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    date DATE NOT NULL,
    checkInTime TIME NOT NULL,
    checkOutTime TIME NOT NULL,
    state BOOLEAN NOT NULL
);

CREATE TABLE contact (
    contactId BIGSERIAL PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    date DATE NOT NULL,
    read BOOLEAN NOT NULL,
    state BOOLEAN NOT NULL
);