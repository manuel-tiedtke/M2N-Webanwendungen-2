/* To test it in SQLite3 manually, do
 * $ sqlite3 test.db
 * and you'll get an sqlite3 interactive shell.
 * Then just copy and paste the rest in there.
 * I use the program 'Squeler' https://github.com/Alecaddd/sequeler
 * from Alessandro Castellani to inspect the database.
 */

-- Drop old tables
DROP TABLE IF EXISTS flashcard;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS flashcard2category;
DROP TABLE IF EXISTS app;

-- Create tables
CREATE TABLE flashcard (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    dueDate TEXT DEFAULT (date('now'))
);

CREATE TABLE category (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT UNIQUE NOT NULL,
    tagline TEXT,
    description TEXT
);

CREATE TABLE flashcard2category (
    flashcardId INTEGER REFERENCES flashcard (id) ON DELETE CASCADE,
    categoryId INTEGER REFERENCES category (id) ON DELETE CASCADE,
    PRIMARY KEY (flashcardId, categoryId)
);

CREATE TABLE app (
    version TEXT
);

-- Insert data
INSERT INTO app (version) VALUES ('0.1.0-alpha+1');
