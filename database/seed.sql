
use movie_verse;

INSERT INTO users (id, name, email, password, phone, role, status, security_question, security_answer) VALUES
('user-001', 'Aarav Sharma', 'aarav@example.com', 'password123', '9000000001', 'USER', TRUE, 'What was the name of your first pet?', 'rocky'),
('user-002', 'Priya Singh', 'priya@example.com', 'password123', '9000000002', 'USER', TRUE, 'What is your mother''s maiden name?', 'sharma'),
('user-003', 'Rahul Verma', 'rahul@example.com', 'password123', '9000000003', 'USER', TRUE, 'What was the name of your primary school?', 'st xavier'),
('user-004', 'Ananya Das', 'ananya@example.com', 'password123', '9000000004', 'USER', TRUE, 'What is the name of the city where you were born?', 'kolkata'),
('user-005', 'Rohan Gupta', 'rohan@example.com', 'password123', '9000000005', 'USER', TRUE, 'What was the make and model of your first car?', 'honda'),
('user-006', 'Sneha Roy', 'sneha@example.com', 'password123', '9000000006', 'USER', TRUE, 'What is your oldest sibling''s middle name?', 'kumar'),
('user-007', 'Aditya Kumar', 'aditya@example.com', 'password123', '9000000007', 'USER', TRUE, 'What was the name of your childhood best friend?', 'rahul'),
('user-008', 'Neha Sharma', 'neha@example.com', 'password123', '9000000008', 'USER', TRUE, 'What was the name of your first pet?', 'bruno'),
('user-009', 'Vikram Singh', 'vikram@example.com', 'password123', '9000000009', 'USER', TRUE, 'What is the name of the city where you were born?', 'delhi'),
('user-010', 'Kavya Patel', 'kavya@example.com', 'password123', '9000000010', 'USER', TRUE, 'What was the name of your primary school?', 'dps'),
('user-011', 'Arjun Mehta', 'arjun@example.com', 'password123', '9000000011', 'USER', TRUE, 'What was the make and model of your first car?', 'swift'),
('user-012', 'Ishita Roy', 'ishita@example.com', 'password123', '9000000012', 'USER', TRUE, 'What was the name of your childhood best friend?', 'priya'),
('user-013', 'Karan Das', 'karan@example.com', 'password123', '9000000013', 'USER', TRUE, 'What was the name of your first pet?', 'charlie'),
('user-014', 'Pooja Gupta', 'pooja@example.com', 'password123', '9000000014', 'USER', TRUE, 'What is your mother''s maiden name?', 'verma'),
('user-015', 'Manish Yadav', 'manish@example.com', 'password123', '9000000015', 'USER', TRUE, 'What is the name of the city where you were born?', 'mumbai'),
('user-016', 'Tanya Kapoor', 'tanya@example.com', 'password123', '9000000016', 'USER', TRUE, 'What was the name of your primary school?', 'st mary'),
('user-017', 'Siddharth Jain', 'siddharth@example.com', 'password123', '9000000017', 'USER', TRUE, 'What was the make and model of your first car?', 'i20'),
('user-018', 'Meera Bose', 'meera@example.com', 'password123', '9000000018', 'USER', TRUE, 'What was the name of your first pet?', 'milo'),
('user-019', 'Nikhil Sen', 'nikhil@example.com', 'password123', '9000000019', 'USER', TRUE, 'What is the name of the city where you were born?', 'bangalore'),
('user-020', 'Riya Chatterjee', 'riya@example.com', 'password123', '9000000020', 'USER', TRUE, 'What was the name of your childhood best friend?', 'sneha'),
('admin-001', 'System Administrator', 'admin@movieticket.com', 'admin123', '9000000099', 'ADMIN', TRUE, 'What was the name of your first pet?', 'admin');
-- =========================================================
-- 14. MALLS
-- =========================================================
INSERT INTO malls (id, name, address, city, state, pincode, status) VALUES
('mall-001', 'Cineplex Central', 'City Centre Mall, Main Road', 'Asansol', 'West Bengal', 713304, TRUE),
('mall-002', 'Galaxy Cinemas', 'Galaxy Mall, GT Road', 'Durgapur', 'West Bengal', 713216, TRUE),
('mall-003', 'PVR Downtown', 'Downtown Shopping Complex', 'Kolkata', 'West Bengal', 700001, TRUE),
('mall-004', 'INOX Riverside', 'Riverside Mall, Park Street', 'Kolkata', 'West Bengal', 700016, TRUE);
-- =========================================================
-- 15. SEAT TYPES
-- =========================================================
INSERT INTO seat_types (id, type_name) VALUES
('seat-type-001', 'REGULAR'),
('seat-type-002', 'PREMIUM'),
('seat-type-003', 'RECLINER');
-- =========================================================
-- 16. GENRES
-- =========================================================
INSERT INTO genres (id, name) VALUES
('genre-001', 'Action'),
('genre-002', 'Adventure'),
('genre-003', 'Comedy'),
('genre-004', 'Drama'),
('genre-005', 'Sci-Fi'),
('genre-006', 'Thriller'),
('genre-007', 'Romance'),
('genre-008', 'Horror'),
('genre-009', 'Animation'),
('genre-010', 'Fantasy');
-- =========================================================
-- 17. MOVIES (ALL 70 MOVIES)
-- =========================================================
INSERT INTO movies (id, title, description, duration_minutes, language, release_date, certificate, poster_url, trailer_url, status) VALUES
('movie-001', 'Avengers: Endgame', 'The Avengers unite for one final battle to reverse the devastating events caused by Thanos.', 181, 'English', '2019-04-26', 'UA', 'https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg', 'https://www.youtube.com/watch?v=TcMBFSGVi1c', 'NOW_SHOWING'),
('movie-002', 'Avengers: Infinity War', 'The Avengers and their allies attempt to stop Thanos from collecting all six Infinity Stones.', 149, 'English', '2018-04-27', 'UA', 'https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg', 'https://www.youtube.com/watch?v=6ZfuNTqbHE8', 'NOW_SHOWING'),
('movie-003', 'John Wick', 'An assassin returns to his old life after a gang attacks his home and kills his dog.', 101, 'English', '2014-10-24', 'A', 'https://image.tmdb.org/t/p/w500/fZPSd91yGE9fCcCe6OoQr6E3Bev.jpg', 'https://www.youtube.com/watch?v=C0BMx-qxsP4', 'NOW_SHOWING'),
('movie-004', 'John Wick: Chapter 2', 'John Wick is forced back into the world of assassins after an old blood oath calls him to Rome.', 122, 'English', '2017-02-10', 'A', 'https://image.tmdb.org/t/p/w500/hXWBc0ioZP3cN4zCu6SN3YHXZVO.jpg', 'https://www.youtube.com/watch?v=XGk2EfbD_Ps', 'NOW_SHOWING'),
('movie-005', 'Mad Max: Fury Road', 'A woman rebels against a tyrant and joins a group escaping across a dangerous wasteland.', 120, 'English', '2015-05-15', 'A', 'https://image.tmdb.org/t/p/w500/hA2ple9q4qnwxp3hKVNhroipsir.jpg', 'https://www.youtube.com/watch?v=hEJnMQG9ev8', 'NOW_SHOWING'),
('movie-006', 'Top Gun: Maverick', 'A legendary pilot trains a new generation of elite fighter pilots for a dangerous mission.', 131, 'English', '2022-05-27', 'UA', 'https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg', 'https://www.youtube.com/watch?v=giXco2jaZ_4', 'NOW_SHOWING'),
('movie-007', 'Black Panther', 'The new king of Wakanda must protect his nation from an enemy challenging his throne.', 134, 'English', '2018-02-16', 'UA', 'https://image.tmdb.org/t/p/w500/uxzzxijgPIY7slzFvMotPv8wjKA.jpg', 'https://www.youtube.com/watch?v=xjDjIWPwcPU', 'NOW_SHOWING'),
('movie-008', 'Gladiator', 'A betrayed Roman general seeks revenge against the corrupt emperor who destroyed his family.', 155, 'English', '2000-05-05', 'UA', 'https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg', 'https://www.youtube.com/watch?v=P5ieIbInFpg', 'NOW_SHOWING'),
('movie-009', 'The Lord of the Rings: The Fellowship of the Ring', 'A hobbit begins an epic journey to destroy a powerful ring before evil can reclaim it.', 178, 'English', '2001-12-19', 'UA', 'https://image.tmdb.org/t/p/w500/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg', 'https://www.youtube.com/watch?v=V75dMMIW2B4', 'NOW_SHOWING'),
('movie-010', 'The Lord of the Rings: The Two Towers', 'The fellowship is divided as Aragorn, Legolas and Gimli continue their fight against the forces of evil.', 179, 'English', '2002-12-18', 'UA', 'https://image.tmdb.org/t/p/w500/5VTN0pR8gcqV3EPUHHfMGnJYN9L.jpg', 'https://www.youtube.com/watch?v=nuTU5XcZTLA', 'NOW_SHOWING'),
('movie-011', 'The Mummy Returns', 'An ancient evil is awakened when a powerful mummy returns and threatens the world.', 130, 'English', '2001-05-04', 'UA', 'https://image.tmdb.org/t/p/w500/kdJsW7hcy1lrj7tdMPycTAQPAiR.jpg', 'https://www.youtube.com/watch?v=H8Mt7V5X4Q8', 'NOW_SHOWING'),
('movie-012', 'Jurassic World', 'A genetically engineered dinosaur escapes from a theme park and threatens everyone inside.', 124, 'English', '2015-06-12', 'UA', 'https://image.tmdb.org/t/p/w500/A0LZHXUzo5C60Oahvt7VxvwuzHw.jpg', 'https://www.youtube.com/watch?v=RFinNxS5KN4', 'NOW_SHOWING'),
('movie-013', 'Indiana Jones and the Raiders of the Lost Ark', 'An archaeologist races against the Nazis to find the legendary Ark of the Covenant.', 115, 'English', '1981-06-12', 'UA', 'https://image.tmdb.org/t/p/w500/ceG9VzoRAVGwivFU403Wc3AHRys.jpg', 'https://www.youtube.com/watch?v=XkkzKHCx154', 'NOW_SHOWING'),
('movie-014', 'Uncharted', 'A treasure hunter and his partner race against rivals to find a legendary lost fortune.', 116, 'English', '2022-02-18', 'UA', 'https://image.tmdb.org/t/p/w500/rJHC1RUORuUhtfNb4Npclx0xnOf.jpg', 'https://www.youtube.com/watch?v=eHp3MbsCbMg', 'NOW_SHOWING'),
('movie-015', 'Oppenheimer', 'A physicist leads the development of the atomic bomb during the Manhattan Project.', 180, 'English', '2023-07-21', 'UA', 'https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg', 'https://www.youtube.com/watch?v=uYPbbksJxIg', 'NOW_SHOWING'),
('movie-016', 'The Matrix', 'A hacker discovers that reality is an artificial simulation controlled by machines.', 136, 'English', '1999-03-31', 'A', 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg', 'https://www.youtube.com/watch?v=vKQi3bBA1y8', 'NOW_SHOWING'),
('movie-017', 'Interstellar', 'Explorers travel through a wormhole in search of a new home for humanity.', 169, 'English', '2014-11-07', 'UA', 'https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg', 'https://www.youtube.com/watch?v=zSWdZVtXT7E', 'NOW_SHOWING'),
('movie-018', 'Inception', 'A skilled thief enters dreams to steal secrets and is given a mission to plant an idea.', 148, 'English', '2010-07-16', 'UA', 'https://image.tmdb.org/t/p/w500/oYuLEt3zVCKq57qu2F8dT7NIa6f.jpg', 'https://www.youtube.com/watch?v=YoHD9XEInc0', 'NOW_SHOWING'),
('movie-019', 'Sunshine', 'A group of astronauts travels toward the dying Sun on a dangerous mission to save humanity.', 107, 'English', '2007-04-05', 'UA', 'https://image.tmdb.org/t/p/w500/8kX6xmEwz0RpEJkeWjXTkxbOCAp.jpg', 'https://www.youtube.com/watch?v=veO73jRDMXw', 'NOW_SHOWING'),
('movie-020', 'Edge of Tomorrow', 'A soldier caught in a time loop relives the same battle while trying to defeat an alien invasion.', 113, 'English', '2014-06-06', 'UA', 'https://image.tmdb.org/t/p/w500/dULtPHV0A6Hfwr4hVfcAFoTsIFo.jpg', 'https://www.youtube.com/watch?v=yUmSVcttXnI', 'NOW_SHOWING'),
('movie-021', 'Ready Player One', 'A teenager enters a massive virtual world to compete for a fortune hidden by its creator.', 140, 'English', '2018-03-29', 'UA', 'https://image.tmdb.org/t/p/w500/pU1ULUq8D3iRxl1fdX2lZIzdHuI.jpg', 'https://www.youtube.com/watch?v=cSp1dM2Vj48', 'NOW_SHOWING'),
('movie-022', 'The Shawshank Redemption', 'A banker imprisoned for a crime he did not commit finds hope and friendship behind bars.', 142, 'English', '1994-09-23', 'A', 'https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg', 'https://www.youtube.com/watch?v=6hB3S9bIaco', 'NOW_SHOWING'),
('movie-023', 'Good Will Hunting', 'A troubled young genius finds guidance and a chance to change his life.', 126, 'English', '1997-12-05', 'A', 'https://image.tmdb.org/t/p/w500/z2FnLKpFi1HPO7BEJxdkv6hpJSU.jpg', 'https://www.youtube.com/watch?v=PaZVjZEFkRs', 'NOW_SHOWING'),
('movie-024', 'Fight Club', 'An ordinary man forms an underground fight club that grows into something far more dangerous.', 139, 'English', '1999-10-15', 'A', 'https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg', 'https://www.youtube.com/watch?v=qtRKdVHc-cE', 'NOW_SHOWING'),
('movie-025', 'Whiplash', 'A young drummer pushes himself to the limit under an intimidating music instructor.', 107, 'English', '2014-10-10', 'A', 'https://image.tmdb.org/t/p/w500/7fn624j5lj3xTme2SgiLCeuedmO.jpg', 'https://www.youtube.com/watch?v=7d_jQycdQGo', 'NOW_SHOWING'),
('movie-026', 'A Beautiful Mind', 'A brilliant mathematician struggles with personal challenges while pursuing groundbreaking work.', 135, 'English', '2001-12-21', 'UA', 'https://image.tmdb.org/t/p/w500/uhekyaBXxGMSu832lDzIoYnoqhm.jpg', 'https://www.youtube.com/watch?v=YWwAOutgWBQ', 'NOW_SHOWING'),
('movie-027', 'The Social Network', 'The story of the creation of a revolutionary social networking platform and the conflicts surrounding it.', 120, 'English', '2010-10-01', 'UA', 'https://image.tmdb.org/t/p/w500/n0ybibhJtQ5icDqTp8eRytcIHJx.jpg', 'https://www.youtube.com/watch?v=lB95KLmpLR4', 'NOW_SHOWING'),
('movie-028', 'Gone Girl', 'A man becomes the center of a media storm when his wife mysteriously disappears.', 149, 'English', '2014-10-03', 'A', 'https://image.tmdb.org/t/p/w500/qymaJhucquUwjpb8oiqynMeXnID.jpg', 'https://www.youtube.com/watch?v=2-_-1nJf8Vg', 'NOW_SHOWING'),
('movie-029', 'Se7en', 'Two detectives hunt a serial killer whose crimes are inspired by the seven deadly sins.', 127, 'English', '1995-09-22', 'A', 'https://image.tmdb.org/t/p/w500/191nKfP0ehp3uIvWqgPbFmI4lv9.jpg', 'https://www.youtube.com/watch?v=znmZoVkCjpI', 'NOW_SHOWING'),
('movie-030', 'Shutter Island', 'A marshal investigates a disappearance at an isolated psychiatric facility and begins questioning reality.', 138, 'English', '2010-02-19', 'A', 'https://image.tmdb.org/t/p/w500/lRq0YDKt5wjLlNfRScQhayS1nxs.jpg', 'https://www.youtube.com/watch?v=v8yrZSkKxTA', 'NOW_SHOWING'),
('movie-031', 'A Quiet Place', 'A family struggles to survive in a world where deadly creatures hunt by sound.', 90, 'English', '2018-04-06', 'A', 'https://image.tmdb.org/t/p/w500/nAU74GmpUk7t5iklEp3bufwDq4n.jpg', 'https://www.youtube.com/watch?v=WR7cc5t7tv8', 'NOW_SHOWING'),
('movie-032', 'Get Out', 'A young man uncovers a disturbing secret while visiting his girlfriend''s family.', 104, 'English', '2017-02-24', 'A', 'https://image.tmdb.org/t/p/w500/97WSXRs5TSCQPup6t9gxB1xkD47.jpg', 'https://www.youtube.com/watch?v=DzfpyUB60YY', 'NOW_SHOWING'),
('movie-033', 'Prisoners', 'A father takes matters into his own hands when his daughter disappears.', 153, 'English', '2013-09-20', 'A', 'https://image.tmdb.org/t/p/w500/uhviyknTT5cEQXbn6vWIqfM4vGm.jpg', 'https://www.youtube.com/watch?v=bpXfcf4d_4E', 'NOW_SHOWING'),
('movie-034', 'The Hangover', 'Three friends try to reconstruct a wild night after waking up with no memory of what happened.', 100, 'English', '2009-06-05', 'A', 'https://image.tmdb.org/t/p/w500/A0uS9rHR56FeBtpjVki16M5xxSW.jpg', 'https://www.youtube.com/watch?v=tcdUhdOlz9M', 'NOW_SHOWING'),
('movie-035', 'Grown Ups', 'Five childhood friends reunite with their families for a weekend filled with memories and chaos.', 102, 'English', '2010-06-25', 'UA', 'https://image.tmdb.org/t/p/w500/ys0LscDFAuZxfUcpH5moiPeFfXs.jpg', 'https://www.youtube.com/watch?v=e01NVCveGkg', 'NOW_SHOWING'),
('movie-036', 'Hot Fuzz', 'A highly skilled police officer is transferred to a quiet village where strange events begin to unfold.', 121, 'English', '2007-02-14', 'A', 'https://image.tmdb.org/t/p/w500/zPib4ukTSdXvHP9pxGkFCe34f3y.jpg', 'https://www.youtube.com/watch?v=ayTnvVpj9t4', 'NOW_SHOWING'),
('movie-037', 'Free Guy', 'A bank employee discovers that he is actually a background character in a video game.', 115, 'English', '2021-08-13', 'UA', 'https://image.tmdb.org/t/p/w500/xmbU4JTUm8rsdtn7Y3Fcm30GpeT.jpg', 'https://www.youtube.com/watch?v=X2m-08cOAbc', 'NOW_SHOWING'),
('movie-038', 'Jumanji: Welcome to the Jungle', 'Four teenagers become trapped inside a video game and must complete a dangerous adventure.', 119, 'English', '2017-12-20', 'UA', 'https://image.tmdb.org/t/p/w500/pSgXKPU5h6U89ipF7HBYajvYt7j.jpg', 'https://www.youtube.com/watch?v=2QKg5SZ_35I', 'NOW_SHOWING'),
('movie-039', 'Notting Hill', 'A London bookseller falls in love with a famous actress when their worlds unexpectedly collide.', 124, 'English', '1999-05-28', 'U', 'https://image.tmdb.org/t/p/w500/hHRIf2XHeQMbyRb3HUx19SF5Ujw.jpg', 'https://www.youtube.com/watch?v=4RI0QvaGoiI', 'NOW_SHOWING'),
('movie-040', 'La La Land', 'A musician and an aspiring actress fall in love while pursuing their dreams in Los Angeles.', 128, 'English', '2016-12-09', 'UA', 'https://image.tmdb.org/t/p/w500/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg', 'https://www.youtube.com/watch?v=45s24h98iOc', 'NOW_SHOWING'),
('movie-041', 'Crazy Rich Asians', 'A woman discovers her boyfriend comes from one of Singapore''s wealthiest families.', 121, 'English', '2018-08-15', 'UA', 'https://image.tmdb.org/t/p/w500/gnTqi4nhIi1eesT5uYMmhEPGNih.jpg', 'https://www.youtube.com/watch?v=ZQ-YX-5bAs0', 'NOW_SHOWING'),
('movie-042', 'Romeo + Juliet', 'Two young lovers from feuding families struggle to be together.', 120, 'English', '1996-11-01', 'A', 'https://image.tmdb.org/t/p/w500/3my9l7h3PJYQLoVo6zn2jgvgURX.jpg', 'https://www.youtube.com/watch?v=4VBsi0VxiLg', 'NOW_SHOWING'),
('movie-043', 'The Bridges of Madison County', 'A brief encounter between a photographer and a married woman changes both of their lives.', 135, 'English', '1995-06-02', 'A', 'https://image.tmdb.org/t/p/w500/8TfLAfIh5Qxp2J4ZjOafHYhWtDb.jpg', 'https://www.youtube.com/watch?v=KqD0H5Wf5vE', 'NOW_SHOWING'),
('movie-044', 'The Conjuring', 'Paranormal investigators help a family terrorized by a dark supernatural presence.', 112, 'English', '2013-07-19', 'A', 'https://image.tmdb.org/t/p/w500/wVYREutTvI2tmxr6ujrHT704wGF.jpg', 'https://www.youtube.com/watch?v=k10ETZ41q5o', 'NOW_SHOWING'),
('movie-045', 'The Conjuring 2', 'A paranormal investigation leads to a terrifying encounter with a supernatural entity in England.', 134, 'English', '2016-06-10', 'A', 'https://image.tmdb.org/t/p/w500/zEqyD0SBt6HL7W9JQoWwtd5Do1T.jpg', 'https://www.youtube.com/watch?v=VFsmuRPClr4', 'NOW_SHOWING'),
('movie-046', 'It', 'A group of children confront a terrifying entity that appears as a clown.', 135, 'English', '2017-09-08', 'A', 'https://image.tmdb.org/t/p/w500/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg', 'https://www.youtube.com/watch?v=xKJmEC5ieOk', 'NOW_SHOWING'),
('movie-047', 'A Nightmare on Elm Street', 'Teenagers are haunted in their dreams by a terrifying supernatural killer.', 91, 'English', '1984-11-09', 'A', 'https://image.tmdb.org/t/p/w500/Aqj5iaJYbgUWaBxpLhgydrkKri5.jpg', 'https://www.youtube.com/watch?v=dCVh4lBfW-c', 'NOW_SHOWING'),
('movie-048', 'Insidious', 'A family discovers that their son is trapped in a mysterious supernatural realm.', 103, 'English', '2010-09-13', 'A', 'https://image.tmdb.org/t/p/w500/tmlDFIUpGRKiuWm9Ixc6CYDk4y0.jpg', 'https://www.youtube.com/watch?v=urg5fU7aQkg', 'NOW_SHOWING'),
('movie-049', 'Coco', 'A young boy enters the Land of the Dead and discovers his family''s musical history.', 105, 'English', '2017-11-22', 'U', 'https://image.tmdb.org/t/p/w500/gGEsBPAijhVUFoiNpgZXqRVWJt2.jpg', 'https://www.youtube.com/watch?v=Ga6RYejo6Hk', 'NOW_SHOWING'),
('movie-050', 'Toy Story 3', 'Woody, Buzz and the rest of the toys face an uncertain future as their owner grows up.', 103, 'English', '2010-06-18', 'U', 'https://image.tmdb.org/t/p/w500/AbbXspMOwdvwWZgVN0nabZq03Ec.jpg', 'https://www.youtube.com/watch?v=JcpWXaA2qeg', 'NOW_SHOWING'),
('movie-051', 'Finding Nemo', 'A clownfish crosses the ocean to find his son after he is captured by humans.', 100, 'English', '2003-05-30', 'U', 'https://image.tmdb.org/t/p/w500/eHuGQ10FUzK1mdOY69wF5pGgEf5.jpg', 'https://www.youtube.com/watch?v=wZdpNglLnkI', 'NOW_SHOWING'),
('movie-052', 'Inside Out', 'A young girl''s emotions struggle to help her adapt after her family moves to a new city.', 95, 'English', '2015-06-19', 'U', 'https://image.tmdb.org/t/p/w500/2H1TmgdfNtsKlU9jKdeNyYL5y8T.jpg', 'https://www.youtube.com/watch?v=yRUAzGQ3nSY', 'NOW_SHOWING'),
('movie-053', 'Despicable Me', 'A criminal mastermind plans a spectacular heist but unexpectedly becomes responsible for three orphaned girls.', 95, 'English', '2010-07-09', 'U', 'https://image.tmdb.org/t/p/w500/5Fh4NdoEnCjCK9wLjdJ9DJNFl2b.jpg', 'https://www.youtube.com/watch?v=zzCZ1W_CUoI', 'NOW_SHOWING'),
('movie-054', 'Harry Potter and the Sorcerer''s Stone', 'A young wizard discovers his magical heritage and begins his education at Hogwarts.', 152, 'English', '2001-11-16', 'U', 'https://image.tmdb.org/t/p/w500/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg', 'https://www.youtube.com/watch?v=VyHV0BRtdxo', 'NOW_SHOWING'),
('movie-055', 'Harry Potter and the Prisoner of Azkaban', 'Harry returns to Hogwarts as a dangerous prisoner escapes from Azkaban.', 142, 'English', '2004-06-04', 'U', 'https://image.tmdb.org/t/p/w500/aWxwnYoe8p2d2fcxOqtvAtJ72Rw.jpg', 'https://www.youtube.com/watch?v=lAxgztbYDbs', 'NOW_SHOWING'),
('movie-056', 'Stardust', 'A young man enters a magical kingdom beyond a mysterious wall and discovers an extraordinary adventure.', 127, 'English', '2007-08-10', 'UA', 'https://image.tmdb.org/t/p/w500/7zbFmxy3DqKYL2M8Hop6uylp2Uy.jpg', 'https://www.youtube.com/watch?v=V1bFr2SWP1I', 'NOW_SHOWING'),
('movie-057', 'Pirates of the Caribbean: The Curse of the Black Pearl', 'A pirate and a young blacksmith join forces to rescue a kidnapped woman and break a supernatural curse.', 143, 'English', '2003-07-09', 'UA', 'https://image.tmdb.org/t/p/w500/z8onk7LV9Mmw6zKz4hT6pzzvmvl.jpg', 'https://www.youtube.com/watch?v=naQr0uTrH_s', 'NOW_SHOWING'),
('movie-058', 'The Hobbit: An Unexpected Journey', 'A peaceful hobbit is recruited to join a dangerous quest to reclaim a lost kingdom.', 169, 'English', '2012-12-14', 'UA', 'https://image.tmdb.org/t/p/w500/yHA9Fc37VmpUA5UncTxxo3rTGVA.jpg', 'https://www.youtube.com/watch?v=SDnYMbYB-nU', 'NOW_SHOWING'),
('movie-059', 'Parasite', 'A struggling family gradually becomes involved with a wealthy household in an unexpected way.', 132, 'Korean', '2019-05-30', 'A', 'https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg', 'https://www.youtube.com/watch?v=SEUXfv87Wpk', 'NOW_SHOWING'),
('movie-060', 'Your Name', 'Two teenagers mysteriously begin switching bodies and search for the truth behind their connection.', 106, 'Japanese', '2016-08-26', 'U', 'https://image.tmdb.org/t/p/w500/q719jXXEzOoYaps6babgKnONONX.jpg', 'https://www.youtube.com/watch?v=xU47nhruN-Q', 'NOW_SHOWING'),
('movie-061', 'Avatar', 'A marine explores the alien world of Pandora and becomes caught between two worlds.', 162, 'English', '2009-12-18', 'UA', 'https://image.tmdb.org/t/p/w500/kyeqWdyUXW608qlYkRqosgbbJyK.jpg', 'https://www.youtube.com/watch?v=5PSNL1qE6VY', 'COMING_SOON'),
('movie-062', 'The Batman', 'Batman investigates corruption in Gotham while hunting a dangerous criminal mastermind.', 176, 'English', '2022-03-04', 'UA', 'https://image.tmdb.org/t/p/w500/74xTEgt7R36Fpooo50r9T25onhq.jpg', 'https://www.youtube.com/watch?v=mqqft2x_Aa4', 'COMING_SOON'),
('movie-063', 'Mission: Impossible - Dead Reckoning Part One', 'Ethan Hunt and his team face a dangerous new threat capable of destabilizing the world.', 163, 'English', '2023-07-12', 'UA', 'https://image.tmdb.org/t/p/w500/NNxYkU70HPurnNCSiCjYAmacwm.jpg', 'https://www.youtube.com/watch?v=avz06PDqDbM', 'COMING_SOON'),
('movie-064', 'The Incredibles', 'A family of superheroes is forced to hide their powers while secretly protecting the world.', 115, 'English', '2004-10-27', 'U', 'https://image.tmdb.org/t/p/w500/2LqaLgk4Z226KkgPJuiOQ58wvrm.jpg', 'https://www.youtube.com/watch?v=-UaGUdNJdRQ', 'COMING_SOON'),
('movie-065', 'Wicked', 'Two young witches form an unexpected friendship that changes their lives and the world around them.', 160, 'English', '2024-11-22', 'UA', 'https://image.tmdb.org/t/p/w500/xDGbZ0JJ3mYaGKy4Nzd9Kph6M9L.jpg', 'https://www.youtube.com/watch?v=6COmYeLsz4c', 'COMING_SOON'),
('movie-066', 'Dune', 'A young nobleman travels to a dangerous desert planet whose resources hold enormous value.', 155, 'English', '2021-10-22', 'UA', 'https://image.tmdb.org/t/p/w500/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg', 'https://www.youtube.com/watch?v=8g18jFHCLXk', 'COMING_SOON'),
('movie-067', 'Toy Story 3', 'Woody, Buzz and the rest of the toys face an uncertain future as their owner grows up.', 103, 'English', '2010-06-18', 'U', 'https://image.tmdb.org/t/p/w500/AbbXspMOwdvwWZgVN0nabZq03Ec.jpg', 'https://www.youtube.com/watch?v=JcpWXaA2qeg', 'COMING_SOON'),
('movie-068', 'Shrek 2', 'Shrek and Fiona visit her parents and face new challenges after returning from their honeymoon.', 93, 'English', '2004-05-19', 'U', 'https://image.tmdb.org/t/p/w500/2yYP0PQjG8zVqturh1BAqu2Tixl.jpg', 'https://www.youtube.com/watch?v=xBgSfhp5Fxo', 'COMING_SOON'),
('movie-069', 'John Wick', 'An assassin returns to his old life after a gang attacks his home and kills his dog.', 101, 'English', '2014-10-24', 'A', 'https://image.tmdb.org/t/p/w500/fZPSd91yGE9fCcCe6OoQr6E3Bev.jpg', 'https://www.youtube.com/watch?v=C0BMx-qxsP4', 'COMING_SOON'),
('movie-070', 'Five Nights at Freddy''s', 'A troubled security guard begins working at Freddy Fazbear''s Pizza and discovers its terrifying secrets.', 110, 'English', '2023-10-27', 'A', 'https://image.tmdb.org/t/p/w500/A4j8S6moJS2zNtRR8oWF08gRnL5.jpg', 'https://www.youtube.com/watch?v=0VH9WCFV6XQ', 'COMING_SOON');
-- =========================================================
-- 18. MOVIE GENRES
-- =========================================================
INSERT INTO movie_genres (movie_id, genre_id) VALUES
('movie-001', 'genre-001'), ('movie-002', 'genre-001'), ('movie-003', 'genre-001'),
('movie-004', 'genre-001'), ('movie-005', 'genre-001'), ('movie-006', 'genre-001'),
('movie-007', 'genre-001'), ('movie-008', 'genre-001'), ('movie-009', 'genre-002'),
('movie-010', 'genre-002'), ('movie-011', 'genre-002'), ('movie-012', 'genre-002'),
('movie-013', 'genre-002'), ('movie-014', 'genre-002'), ('movie-015', 'genre-005'),
('movie-016', 'genre-005'), ('movie-017', 'genre-005'), ('movie-018', 'genre-005'),
('movie-019', 'genre-005'), ('movie-020', 'genre-005'), ('movie-021', 'genre-005'),
('movie-022', 'genre-004'), ('movie-023', 'genre-004'), ('movie-024', 'genre-004'),
('movie-025', 'genre-004'), ('movie-026', 'genre-004'), ('movie-027', 'genre-004'),
('movie-028', 'genre-006'), ('movie-029', 'genre-006'), ('movie-030', 'genre-006'),
('movie-031', 'genre-006'), ('movie-032', 'genre-006'), ('movie-033', 'genre-006'),
('movie-034', 'genre-003'), ('movie-035', 'genre-003'), ('movie-036', 'genre-003'),
('movie-037', 'genre-003'), ('movie-038', 'genre-003'), ('movie-039', 'genre-007'),
('movie-040', 'genre-007'), ('movie-041', 'genre-007'), ('movie-042', 'genre-007'),
('movie-043', 'genre-007'), ('movie-044', 'genre-008'), ('movie-045', 'genre-008'),
('movie-046', 'genre-008'), ('movie-047', 'genre-008'), ('movie-048', 'genre-008'),
('movie-049', 'genre-009'), ('movie-050', 'genre-009'), ('movie-051', 'genre-009'),
('movie-052', 'genre-009'), ('movie-053', 'genre-009'), ('movie-054', 'genre-010'),
('movie-055', 'genre-010'), ('movie-056', 'genre-010'), ('movie-057', 'genre-010'),
('movie-058', 'genre-010'), ('movie-059', 'genre-006'), ('movie-059', 'genre-004'),
('movie-060', 'genre-009'), ('movie-060', 'genre-007'), ('movie-061', 'genre-002'),
('movie-061', 'genre-005'), ('movie-062', 'genre-001'), ('movie-062', 'genre-006'),
('movie-063', 'genre-001'), ('movie-063', 'genre-006'), ('movie-064', 'genre-009'),
('movie-064', 'genre-003'), ('movie-065', 'genre-010'), ('movie-065', 'genre-004'),
('movie-066', 'genre-005'), ('movie-066', 'genre-002'), ('movie-067', 'genre-009'),
('movie-067', 'genre-003'), ('movie-068', 'genre-009'), ('movie-068', 'genre-003'),
('movie-069', 'genre-001'), ('movie-069', 'genre-004'), ('movie-070', 'genre-008'),
('movie-070', 'genre-006');
-- =========================================================
-- 19. CREATE 147 SEATS FOR EACH MALL
-- =========================================================
INSERT INTO seats (id, mall_id, seat_type_id, row_name, seat_number, section, status)
SELECT
  CONCAT('seat-', LPAD(ROW_NUMBER() OVER (ORDER BY m.id, r.row_no, x.section_order, x.seat_no), 4, '0')) AS id,
  m.id AS mall_id,
  r.seat_type_id,
  r.row_name,
  CASE
    WHEN x.section = 'LEFT' THEN x.seat_no
    WHEN x.section = 'CENTER' THEN r.left_count + x.seat_no
    WHEN x.section = 'RIGHT' THEN r.left_count + r.center_count + x.seat_no
  END AS seat_number,
  x.section,
  TRUE AS status
FROM malls m
CROSS JOIN (
  SELECT 1 AS row_no, 'A' AS row_name, 4 AS left_count, 8 AS center_count, 'seat-type-002' AS seat_type_id
  UNION ALL SELECT 2, 'B', 4, 9, 'seat-type-002'
  UNION ALL SELECT 3, 'C', 4, 10, 'seat-type-002'
  UNION ALL SELECT 4, 'D', 4, 10, 'seat-type-002'
  UNION ALL SELECT 5, 'E', 4, 11, 'seat-type-001'
  UNION ALL SELECT 6, 'F', 4, 11, 'seat-type-001'
  UNION ALL SELECT 7, 'G', 4, 12, 'seat-type-001'
  UNION ALL SELECT 8, 'H', 4, 12, 'seat-type-003'
) r
CROSS JOIN (
  SELECT 'LEFT' AS section, 1 AS section_order, 1 AS seat_no
  UNION ALL SELECT 'LEFT', 1, 2 UNION ALL SELECT 'LEFT', 1, 3 UNION ALL SELECT 'LEFT', 1, 4
  UNION ALL SELECT 'CENTER', 2, 1 UNION ALL SELECT 'CENTER', 2, 2 UNION ALL SELECT 'CENTER', 2, 3
  UNION ALL SELECT 'CENTER', 2, 4 UNION ALL SELECT 'CENTER', 2, 5 UNION ALL SELECT 'CENTER', 2, 6
  UNION ALL SELECT 'CENTER', 2, 7 UNION ALL SELECT 'CENTER', 2, 8 UNION ALL SELECT 'CENTER', 2, 9
  UNION ALL SELECT 'CENTER', 2, 10 UNION ALL SELECT 'CENTER', 2, 11 UNION ALL SELECT 'CENTER', 2, 12
  UNION ALL SELECT 'RIGHT', 3, 1 UNION ALL SELECT 'RIGHT', 3, 2 UNION ALL SELECT 'RIGHT', 3, 3 UNION ALL SELECT 'RIGHT', 3, 4
) x
WHERE (x.section = 'LEFT' AND x.seat_no <= r.left_count)
   OR (x.section = 'CENTER' AND x.seat_no <= r.center_count)
   OR (x.section = 'RIGHT' AND x.seat_no <= r.left_count);
-- =========================================================
-- 20. SHOWS (240 SHOWS)
-- =========================================================
INSERT INTO shows (id, movie_id, mall_id, show_date, start_time, end_time, status)
SELECT
  CONCAT('show-', LPAD(ROW_NUMBER() OVER (ORDER BY m.id, slot.slot_no), 3, '0')) AS id,
  m.id AS movie_id,
  CASE slot.slot_no
    WHEN 1 THEN 'mall-001'
    WHEN 2 THEN 'mall-002'
    WHEN 3 THEN 'mall-003'
    WHEN 4 THEN 'mall-004'
  END AS mall_id,
  DATE_ADD('2026-09-16', INTERVAL MOD(CAST(SUBSTRING_INDEX(m.id, '-', -1) AS UNSIGNED) - 1, 15) DAY) AS show_date,
  slot.start_time,
  ADDTIME(slot.start_time, SEC_TO_TIME(m.duration_minutes * 60)) AS end_time,
  'ACTIVE' AS status
FROM movies m
CROSS JOIN (
  SELECT 1 AS slot_no, CAST('10:00:00' AS TIME) AS start_time
  UNION ALL SELECT 2, CAST('13:30:00' AS TIME)
  UNION ALL SELECT 3, CAST('17:00:00' AS TIME)
  UNION ALL SELECT 4, CAST('20:30:00' AS TIME)
) slot
WHERE m.status = 'NOW_SHOWING'
ORDER BY m.id, slot.slot_no;
-- =========================================================
-- 21. GENERATE SHOW SEATS (35,280 SEATS)
-- =========================================================
INSERT INTO show_seats (id, show_id, seat_id, price, status, held_until)
SELECT
  CONCAT('show-seat-', LPAD(ROW_NUMBER() OVER (ORDER BY sh.id, s.mall_id, s.row_name, s.seat_number), 6, '0')) AS id,
  sh.id,
  s.id,
  CASE
    WHEN s.seat_type_id = 'seat-type-001' THEN 150.00
    WHEN s.seat_type_id = 'seat-type-002' THEN 250.00
    WHEN s.seat_type_id = 'seat-type-003' THEN 350.00
  END AS price,
  'AVAILABLE',
  NULL
FROM shows sh
JOIN seats s ON s.mall_id = sh.mall_id;
-- =========================================================
-- 22. BOOKINGS (20 BOOKINGS)
-- =========================================================
INSERT INTO bookings (id, booking_reference, user_id, show_id, total_amount, booking_status, created_at, expires_at) VALUES
('booking-001', 'MTB-260916-0001', 'user-001', 'show-001', 500.00, 'CONFIRMED', '2026-09-15 09:15:00', NULL),
('booking-002', 'MTB-260916-0002', 'user-002', 'show-001', 500.00, 'CONFIRMED', '2026-09-15 10:20:00', NULL),
('booking-003', 'MTB-260916-0003', 'user-003', 'show-002', 750.00, 'CONFIRMED', '2026-09-15 11:30:00', NULL),
('booking-004', 'MTB-260916-0004', 'user-004', 'show-003', 700.00, 'CONFIRMED', '2026-09-15 13:00:00', NULL),
('booking-005', 'MTB-260916-0005', 'user-005', 'show-004', 500.00, 'CONFIRMED', '2026-09-15 14:20:00', NULL),
('booking-006', 'MTB-260916-0006', 'user-006', 'show-005', 500.00, 'CONFIRMED', '2026-09-15 15:10:00', NULL),
('booking-007', 'MTB-260916-0007', 'user-007', 'show-006', 700.00, 'CONFIRMED', '2026-09-15 16:00:00', NULL),
('booking-008', 'MTB-260916-0008', 'user-008', 'show-007', 300.00, 'CONFIRMED', '2026-09-15 17:15:00', NULL),
('booking-009', 'MTB-260916-0009', 'user-009', 'show-008', 500.00, 'CONFIRMED', '2026-09-15 18:20:00', NULL),
('booking-010', 'MTB-260916-0010', 'user-010', 'show-009', 850.00, 'CONFIRMED', '2026-09-15 19:30:00', NULL),
('booking-011', 'MTB-260916-0011', 'user-011', 'show-010', 700.00, 'CONFIRMED', '2026-09-15 20:10:00', NULL),
('booking-012', 'MTB-260916-0012', 'user-012', 'show-011', 500.00, 'CONFIRMED', '2026-09-15 20:40:00', NULL),
('booking-013', 'MTB-260916-0013', 'user-013', 'show-012', 700.00, 'CONFIRMED', '2026-09-15 21:00:00', NULL),
('booking-014', 'MTB-260916-0014', 'user-014', 'show-021', 500.00, 'CONFIRMED', '2026-09-15 21:30:00', NULL),
('booking-015', 'MTB-260916-0015', 'user-015', 'show-022', 700.00, 'CONFIRMED', '2026-09-15 22:00:00', NULL),
('booking-016', 'MTB-260916-0016', 'user-016', 'show-029', 500.00, 'CONFIRMED', '2026-09-15 22:20:00', NULL),
('booking-017', 'MTB-260916-0017', 'user-017', 'show-030', 300.00, 'PENDING', '2026-09-16 10:00:00', '2026-09-16 10:15:00'),
('booking-018', 'MTB-260916-0018', 'user-018', 'show-031', 500.00, 'PENDING', '2026-09-16 10:30:00', '2026-09-16 10:45:00'),
('booking-019', 'MTB-260916-0019', 'user-019', 'show-037', 700.00, 'CONFIRMED', '2026-09-15 23:00:00', NULL),
('booking-020', 'MTB-260916-0020', 'user-020', 'show-038', 500.00, 'CONFIRMED', '2026-09-16 08:30:00', NULL);
-- =========================================================
-- 23. BOOKING SEATS (ALL 42 SEATS)
-- =========================================================
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-001', 'booking-001', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-001' AND s.row_name = 'A' AND s.seat_number = 5;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-002', 'booking-001', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-001' AND s.row_name = 'A' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-003', 'booking-002', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-001' AND s.row_name = 'B' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-004', 'booking-002', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-001' AND s.row_name = 'B' AND s.seat_number = 7;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-005', 'booking-003', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-002' AND s.row_name = 'H' AND s.seat_number = 10;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-006', 'booking-003', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-002' AND s.row_name = 'H' AND s.seat_number = 11;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-007', 'booking-003', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-002' AND s.row_name = 'E' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-008', 'booking-004', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-003' AND s.row_name = 'H' AND s.seat_number = 8;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-009', 'booking-004', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-003' AND s.row_name = 'H' AND s.seat_number = 9;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-010', 'booking-005', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-004' AND s.row_name = 'C' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-011', 'booking-005', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-004' AND s.row_name = 'C' AND s.seat_number = 7;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-012', 'booking-006', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-005' AND s.row_name = 'D' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-013', 'booking-006', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-005' AND s.row_name = 'D' AND s.seat_number = 7;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-014', 'booking-007', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-006' AND s.row_name = 'H' AND s.seat_number = 10;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-015', 'booking-007', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-006' AND s.row_name = 'H' AND s.seat_number = 11;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-016', 'booking-008', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-007' AND s.row_name = 'E' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-017', 'booking-008', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-007' AND s.row_name = 'E' AND s.seat_number = 7;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-018', 'booking-009', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-008' AND s.row_name = 'A' AND s.seat_number = 5;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-019', 'booking-009', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-008' AND s.row_name = 'A' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-020', 'booking-010', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-009' AND s.row_name = 'H' AND s.seat_number = 10;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-021', 'booking-010', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-009' AND s.row_name = 'H' AND s.seat_number = 11;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-022', 'booking-010', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-009' AND s.row_name = 'H' AND s.seat_number = 12;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-023', 'booking-011', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-010' AND s.row_name = 'H' AND s.seat_number = 8;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-024', 'booking-011', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-010' AND s.row_name = 'H' AND s.seat_number = 9;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-025', 'booking-012', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-011' AND s.row_name = 'D' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-026', 'booking-012', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-011' AND s.row_name = 'D' AND s.seat_number = 7;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-027', 'booking-013', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-012' AND s.row_name = 'H' AND s.seat_number = 10;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-028', 'booking-013', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-012' AND s.row_name = 'H' AND s.seat_number = 11;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-029', 'booking-014', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-021' AND s.row_name = 'A' AND s.seat_number = 5;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-030', 'booking-014', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-021' AND s.row_name = 'A' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-031', 'booking-015', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-022' AND s.row_name = 'H' AND s.seat_number = 10;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-032', 'booking-015', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-022' AND s.row_name = 'H' AND s.seat_number = 11;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-033', 'booking-016', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-029' AND s.row_name = 'A' AND s.seat_number = 5;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-034', 'booking-016', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-029' AND s.row_name = 'A' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-035', 'booking-017', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-030' AND s.row_name = 'E' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-036', 'booking-017', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-030' AND s.row_name = 'E' AND s.seat_number = 7;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-037', 'booking-018', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-031' AND s.row_name = 'A' AND s.seat_number = 5;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-038', 'booking-018', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-031' AND s.row_name = 'A' AND s.seat_number = 6;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-039', 'booking-019', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-037' AND s.row_name = 'H' AND s.seat_number = 10;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-040', 'booking-019', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-037' AND s.row_name = 'H' AND s.seat_number = 11;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-041', 'booking-020', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-038' AND s.row_name = 'A' AND s.seat_number = 5;
INSERT INTO booking_seats (id, booking_id, show_seat_id, price)
SELECT 'booking-seat-042', 'booking-020', ss.id, ss.price FROM show_seats ss JOIN seats s ON s.id = ss.seat_id WHERE ss.show_id = 'show-038' AND s.row_name = 'A' AND s.seat_number = 6;
-- =========================================================
-- 24. UPDATE SHOW SEAT STATUS
-- =========================================================
UPDATE show_seats ss
JOIN booking_seats bs ON bs.show_seat_id = ss.id
JOIN bookings b ON b.id = bs.booking_id
SET ss.status = 'BOOKED'
WHERE b.booking_status = 'CONFIRMED';
-- =========================================================
-- 25. UPDATE PENDING SEATS
-- =========================================================
UPDATE show_seats ss
JOIN booking_seats bs ON bs.show_seat_id = ss.id
JOIN bookings b ON b.id = bs.booking_id
SET ss.status = 'HELD', ss.held_until = b.expires_at
WHERE b.booking_status = 'PENDING';
-- =========================================================
-- 26. PAYMENTS (20 PAYMENTS)
-- =========================================================
INSERT INTO payments (id, booking_id, amount, payment_method, transaction_id, payment_status, paid_at) VALUES
('payment-001', 'booking-001', 500.00, 'UPI', 'TXN-260916-0001', 'SUCCESS', '2026-09-15 09:16:20'),
('payment-002', 'booking-002', 500.00, 'CARD', 'TXN-260916-0002', 'SUCCESS', '2026-09-15 10:21:15'),
('payment-003', 'booking-003', 750.00, 'UPI', 'TXN-260916-0003', 'SUCCESS', '2026-09-15 11:31:10'),
('payment-004', 'booking-004', 700.00, 'CARD', 'TXN-260916-0004', 'SUCCESS', '2026-09-15 13:01:40'),
('payment-005', 'booking-005', 500.00, 'UPI', 'TXN-260916-0005', 'SUCCESS', '2026-09-15 14:21:30'),
('payment-006', 'booking-006', 500.00, 'NET_BANKING', 'TXN-260916-0006', 'SUCCESS', '2026-09-15 15:11:22'),
('payment-007', 'booking-007', 700.00, 'UPI', 'TXN-260916-0007', 'SUCCESS', '2026-09-15 16:01:11'),
('payment-008', 'booking-008', 300.00, 'CARD', 'TXN-260916-0008', 'SUCCESS', '2026-09-15 17:16:09'),
('payment-009', 'booking-009', 500.00, 'UPI', 'TXN-260916-0009', 'SUCCESS', '2026-09-15 18:21:45'),
('payment-010', 'booking-010', 850.00, 'CARD', 'TXN-260916-0010', 'SUCCESS', '2026-09-15 19:31:20'),
('payment-011', 'booking-011', 700.00, 'UPI', 'TXN-260916-0011', 'SUCCESS', '2026-09-15 20:11:30'),
('payment-012', 'booking-012', 500.00, 'CARD', 'TXN-260916-0012', 'SUCCESS', '2026-09-15 20:41:15'),
('payment-013', 'booking-013', 700.00, 'UPI', 'TXN-260916-0013', 'SUCCESS', '2026-09-15 21:01:40'),
('payment-014', 'booking-014', 500.00, 'CARD', 'TXN-260916-0014', 'SUCCESS', '2026-09-15 21:31:25'),
('payment-015', 'booking-015', 700.00, 'UPI', 'TXN-260916-0015', 'SUCCESS', '2026-09-15 22:01:50'),
('payment-016', 'booking-016', 500.00, 'CARD', 'TXN-260916-0016', 'SUCCESS', '2026-09-15 22:21:35'),
('payment-017', 'booking-017', 300.00, 'UPI', NULL, 'PENDING', NULL),
('payment-018', 'booking-018', 500.00, 'CARD', NULL, 'PENDING', NULL),
('payment-019', 'booking-019', 700.00, 'UPI', 'TXN-260916-0019', 'SUCCESS', '2026-09-15 23:01:20'),
('payment-020', 'booking-020', 500.00, 'CARD', 'TXN-260916-0020', 'SUCCESS', '2026-09-16 08:31:25');