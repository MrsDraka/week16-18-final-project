DROP TABLE IF EXISTS art_piece_expo;
DROP TABLE IF EXISTS expo;
DROP TABLE IF EXISTS art_piece;
DROP TABLE IF EXISTS artist;

CREATE TABLE artist (
    artist_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    artist_name VARCHAR(255) NOT NULL,
    bio TEXT
);

CREATE TABLE art_piece (
    art_piece_id INT PRIMARY KEY AUTO_INCREMENT,
    artist_id INT,
    art_title VARCHAR(255) NOT NULL,
    art_year INT,
    art_medium VARCHAR(255),
    FOREIGN KEY (artist_id) REFERENCES artist(artist_id) ON DELETE CASCADE
);

CREATE TABLE expo (
    expo_id INT PRIMARY KEY AUTO_INCREMENT,
    expo_name VARCHAR(255) NOT NULL,
    expo_location VARCHAR(255) NOT NULL,
    expo_start_date DATETIME,
    expo_end_date DATETIME
);

CREATE TABLE art_piece_expo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    art_piece_id INT,
    expo_id INT,
    FOREIGN KEY (art_piece_id) REFERENCES art_piece(art_piece_id) ON DELETE CASCADE,
    FOREIGN KEY (expo_id) REFERENCES expo(expo_id) ON DELETE CASCADE
);