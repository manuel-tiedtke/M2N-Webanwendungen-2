/* To test it in SQLite3 manually, do 
 * $ sqlite3 test.db 
 * and you'll get an sqlite3 interactive shell.
 * Then just copy and paste the rest in there. 
 * I use the program "Squeler" https://github.com/Alecaddd/sequeler 
 * from Alessandro Castellani to inspect the database.
 */

DROP TABLE IF EXISTS Flashcard;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS App;


CREATE TABLE Flashcard (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Question TEXT NOT NULL,
    Answer TEXT NOT NULL,
    DueDate TEXT DEFAULT Never
);

CREATE TABLE Category (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Name TEXT UNIQUE NOT NULL,
    Tagline TEXT DEFAULT Tagline,
    Description TEXT DEFAULT Description
);

CREATE TABLE App (
    version TEXT UNIQUE NOT NULL
);

-- Test entries for table App
INSERT INTO App VALUES ("0.1.0");

-- Test entries for table Flashcard
INSERT INTO Flashcard (Id, Question, Answer)
VALUES (10, "U wanna test the default?", "Yes");
INSERT INTO Flashcard (Id, Question, Answer, DueDate)
VALUES
(1, "How ru doin?", "Good", "2021-01-01 10:00:00"),
(2, "Was ist 1 + 1?", "3", "2021-01-01 18:00:00"),
(3, "Ja oder Nein?", "Nein", "2021-01-01 12:00:00"),
(4, "Ist die Welt klein?", "Ja", "2021-01-01 17:30:00");

-- Test entries for table Category
INSERT INTO Category (Id, Name)
VALUES (10, "Empty");
INSERT INTO Category (Id, Name, Tagline, Description)
VALUES
(1, "Python", "Dunno", "Python is awesome"),
(2, "Food", "Dunno", "Eat! Or you'll starve!"),
(3, "Health", "Dunno", "See category Food to stay healthy"),
(4, "Hardware", "Dunno", "Hardware Hacking Topics");
