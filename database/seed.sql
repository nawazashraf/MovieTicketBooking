USE movie_ticket_booking;


-- 1. USERS

INSERT INTO users
(id, name, email, password, phone, role, status)
VALUES
('user-001', 'Admin User', 'admin@movie.com', 'admin123', '9000000001', 'ADMIN', TRUE),
('user-002', 'Mall Admin', 'malladmin@movie.com', 'malladmin123', '9000000002', 'MALL_ADMIN', TRUE),
('user-003', 'Test User', 'user@movie.com', 'user123', '9000000003', 'USER', TRUE);


-- 2. MALLS

INSERT INTO malls
(id, name, address, city, state, pincode, status)
VALUES
('mall-001', 'City Centre', 'GT Road', 'Asansol', 'West Bengal', 713304, TRUE),
('mall-002', 'Junction Mall', 'City Center', 'Durgapur', 'West Bengal', 713216, TRUE);


-- 3. SEAT TYPES

INSERT INTO seat_types
(id, type_name)
VALUES
('seat-type-001', 'REGULAR'),
('seat-type-002', 'PREMIUM'),
('seat-type-003', 'RECLINER');


-- ==========================================
-- 4. SEATS - CITY CENTRE
-- Rows A and B = PREMIUM
-- Rows C and D = REGULAR
-- ==========================================

INSERT INTO seats
(id, mall_id, seat_type_id, row_name, seat_number, status)
VALUES

-- Row A
('seat-001', 'mall-001', 'seat-type-002', 'A', 1, TRUE),
('seat-002', 'mall-001', 'seat-type-002', 'A', 2, TRUE),
('seat-003', 'mall-001', 'seat-type-002', 'A', 3, TRUE),
('seat-004', 'mall-001', 'seat-type-002', 'A', 4, TRUE),
('seat-005', 'mall-001', 'seat-type-002', 'A', 5, TRUE),

-- Row B
('seat-006', 'mall-001', 'seat-type-002', 'B', 1, TRUE),
('seat-007', 'mall-001', 'seat-type-002', 'B', 2, TRUE),
('seat-008', 'mall-001', 'seat-type-002', 'B', 3, TRUE),
('seat-009', 'mall-001', 'seat-type-002', 'B', 4, TRUE),
('seat-010', 'mall-001', 'seat-type-002', 'B', 5, TRUE),

-- Row C
('seat-011', 'mall-001', 'seat-type-001', 'C', 1, TRUE),
('seat-012', 'mall-001', 'seat-type-001', 'C', 2, TRUE),
('seat-013', 'mall-001', 'seat-type-001', 'C', 3, TRUE),
('seat-014', 'mall-001', 'seat-type-001', 'C', 4, TRUE),
('seat-015', 'mall-001', 'seat-type-001', 'C', 5, TRUE),

-- Row D
('seat-016', 'mall-001', 'seat-type-001', 'D', 1, TRUE),
('seat-017', 'mall-001', 'seat-type-001', 'D', 2, TRUE),
('seat-018', 'mall-001', 'seat-type-001', 'D', 3, TRUE),
('seat-019', 'mall-001', 'seat-type-001', 'D', 4, TRUE),
('seat-020', 'mall-001', 'seat-type-001', 'D', 5, TRUE);


-- 5. GENRES

INSERT INTO genres
(id, name)
VALUES
('genre-001', 'Action'),
('genre-002', 'Comedy'),
('genre-003', 'Drama'),
('genre-004', 'Horror'),
('genre-005', 'Sci-Fi'),
('genre-006', 'Adventure');


-- 6. MOVIES

INSERT INTO movies
(id, title, description, duration_minutes, language,
 release_date, certificate, poster_url, trailer_url, status)
VALUES
(
    'movie-001',
    'Sample Action Movie',
    'An action movie used for testing the movie ticket booking system.',
    150,
    'English',
    '2026-08-01',
    'UA',
    'https://example.com/poster1.jpg',
    'https://example.com/trailer1',
    'NOW_SHOWING'
),
(
    'movie-002',
    'Sample Comedy Movie',
    'A comedy movie used for testing.',
    120,
    'Hindi',
    '2026-08-10',
    'U',
    'https://example.com/poster2.jpg',
    'https://example.com/trailer2',
    'NOW_SHOWING'
),
(
    'movie-003',
    'Coming Soon Movie',
    'A movie that will be released soon.',
    140,
    'English',
    '2026-09-15',
    'UA',
    'https://example.com/poster3.jpg',
    'https://example.com/trailer3',
    'COMING_SOON'
);


-- 7. MOVIE GENRES

INSERT INTO movie_genres
(movie_id, genre_id)
VALUES
('movie-001', 'genre-001'),
('movie-001', 'genre-006'),
('movie-002', 'genre-002'),
('movie-003', 'genre-005'),
('movie-003', 'genre-006');


-- 8. SHOWS

INSERT INTO shows
(id, movie_id, mall_id, show_date, start_time, end_time, status)
VALUES
('show-001', 'movie-001', 'mall-001', '2026-08-26', '10:00:00', '12:30:00', 'ACTIVE'),
('show-002', 'movie-001', 'mall-001', '2026-08-26', '14:00:00', '16:30:00', 'ACTIVE'),
('show-003', 'movie-001', 'mall-001', '2026-08-26', '18:00:00', '20:30:00', 'ACTIVE'),
('show-004', 'movie-002', 'mall-001', '2026-08-26', '21:00:00', '23:00:00', 'ACTIVE');


-- 9. SHOW SEATS
-- Create seats for Show 1

INSERT INTO show_seats
(id, show_id, seat_id, price, status)
VALUES

-- PREMIUM SEATS
('show-seat-001', 'show-001', 'seat-001', 250.00, 'AVAILABLE'),
('show-seat-002', 'show-001', 'seat-002', 250.00, 'AVAILABLE'),
('show-seat-003', 'show-001', 'seat-003', 250.00, 'BOOKED'),
('show-seat-004', 'show-001', 'seat-004', 250.00, 'AVAILABLE'),
('show-seat-005', 'show-001', 'seat-005', 250.00, 'AVAILABLE'),

('show-seat-006', 'show-001', 'seat-006', 250.00, 'AVAILABLE'),
('show-seat-007', 'show-001', 'seat-007', 250.00, 'AVAILABLE'),
('show-seat-008', 'show-001', 'seat-008', 250.00, 'AVAILABLE'),
('show-seat-009', 'show-001', 'seat-009', 250.00, 'AVAILABLE'),
('show-seat-010', 'show-001', 'seat-010', 250.00, 'AVAILABLE'),

-- REGULAR SEATS
('show-seat-011', 'show-001', 'seat-011', 150.00, 'AVAILABLE'),
('show-seat-012', 'show-001', 'seat-012', 150.00, 'AVAILABLE'),
('show-seat-013', 'show-001', 'seat-013', 150.00, 'AVAILABLE'),
('show-seat-014', 'show-001', 'seat-014', 150.00, 'AVAILABLE'),
('show-seat-015', 'show-001', 'seat-015', 150.00, 'BOOKED'),

('show-seat-016', 'show-001', 'seat-016', 150.00, 'AVAILABLE'),
('show-seat-017', 'show-001', 'seat-017', 150.00, 'AVAILABLE'),
('show-seat-018', 'show-001', 'seat-018', 150.00, 'AVAILABLE'),
('show-seat-019', 'show-001', 'seat-019', 150.00, 'AVAILABLE'),
('show-seat-020', 'show-001', 'seat-020', 150.00, 'AVAILABLE');