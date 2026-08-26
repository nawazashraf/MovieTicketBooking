CREATE DATABASE movie_ticket_booking;

USE movie_ticket_booking;


-- 1. USERS
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- 2. MALLS
CREATE TABLE malls (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    pincode INT,
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- 3. SEAT TYPES
CREATE TABLE seat_types (
    id VARCHAR(36) PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL
);


-- 4. SEATS
CREATE TABLE seats (
    id VARCHAR(36) PRIMARY KEY,
    mall_id VARCHAR(36) NOT NULL,
    seat_type_id VARCHAR(36) NOT NULL,
    row_name VARCHAR(10) NOT NULL,
    seat_number INT NOT NULL,
    status BOOLEAN DEFAULT TRUE,

    FOREIGN KEY (mall_id)
        REFERENCES malls(id)
        ON DELETE CASCADE,

    FOREIGN KEY (seat_type_id)
        REFERENCES seat_types(id),

    UNIQUE (mall_id, row_name, seat_number)
);


-- 5. MOVIES
CREATE TABLE movies (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    duration_minutes INT NOT NULL,
    language VARCHAR(50),
    release_date DATE,
    certificate VARCHAR(20),
    poster_url VARCHAR(500),
    trailer_url VARCHAR(500),
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- 6. GENRES
CREATE TABLE genres (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);


-- 7. MOVIE GENRES
CREATE TABLE movie_genres (
    movie_id VARCHAR(36) NOT NULL,
    genre_id VARCHAR(36) NOT NULL,

    PRIMARY KEY (movie_id, genre_id),

    FOREIGN KEY (movie_id)
        REFERENCES movies(id)
        ON DELETE CASCADE,

    FOREIGN KEY (genre_id)
        REFERENCES genres(id)
        ON DELETE CASCADE
);


-- 8. SHOWS
CREATE TABLE shows (
    id VARCHAR(36) PRIMARY KEY,
    movie_id VARCHAR(36) NOT NULL,
    mall_id VARCHAR(36) NOT NULL,
    show_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (movie_id)
        REFERENCES movies(id)
        ON DELETE CASCADE,

    FOREIGN KEY (mall_id)
        REFERENCES malls(id)
        ON DELETE CASCADE
);


-- 9. SHOW SEATS
CREATE TABLE show_seats (
    id VARCHAR(36) PRIMARY KEY,
    show_id VARCHAR(36) NOT NULL,
    seat_id VARCHAR(36) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    held_until TIMESTAMP NULL,

    FOREIGN KEY (show_id)
        REFERENCES shows(id)
        ON DELETE CASCADE,

    FOREIGN KEY (seat_id)
        REFERENCES seats(id)
        ON DELETE CASCADE,

    UNIQUE (show_id, seat_id)
);


-- 10. BOOKINGS
CREATE TABLE bookings (
    id VARCHAR(36) PRIMARY KEY,
    booking_reference VARCHAR(50) NOT NULL UNIQUE,
    user_id VARCHAR(36) NOT NULL,
    show_id VARCHAR(36) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    booking_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NULL,

    FOREIGN KEY (user_id)
        REFERENCES users(id),

    FOREIGN KEY (show_id)
        REFERENCES shows(id)
);


-- 11. BOOKING SEATS
CREATE TABLE booking_seats (
    id VARCHAR(36) PRIMARY KEY,
    booking_id VARCHAR(36) NOT NULL,
    show_seat_id VARCHAR(36) NOT NULL,
    price DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (booking_id)
        REFERENCES bookings(id)
        ON DELETE CASCADE,

    FOREIGN KEY (show_seat_id)
        REFERENCES show_seats(id),

    UNIQUE (booking_id, show_seat_id)
);


-- 12. PAYMENTS
CREATE TABLE payments (
    id VARCHAR(36) PRIMARY KEY,
    booking_id VARCHAR(36) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(30),
    transaction_id VARCHAR(100) UNIQUE,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    paid_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (booking_id)
        REFERENCES bookings(id)
);