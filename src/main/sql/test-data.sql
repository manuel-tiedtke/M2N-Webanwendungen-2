/* To test it in SQLite3 manually, do
 * $ sqlite3 test.db
 * and you'll get an sqlite3 interactive shell.
 * Then just copy and paste the rest in there.
 * I use the program 'Squeler' https://github.com/Alecaddd/sequeler
 * from Alessandro Castellani to inspect the database.
 */

-- Test entries for table flashcard
INSERT INTO flashcard (question, answer)
VALUES ('U wanna test the default?', 'Yes');

INSERT INTO flashcard (question, answer, dueDate)
VALUES
('How ru doin?', 'Good', '2021-01-01 10:00:00'),
('Was ist 1 + 1?', '3', '2021-01-01 18:00:00'),
('Ja oder Nein?', 'Nein', '2021-01-01 12:00:00'),
('Ist die Welt klein?', 'Ja', date('now','+5 days'));


-- Test entries for table category
INSERT INTO category (name)
VALUES ('Default');

INSERT INTO category (name, tagline, description)
VALUES
('Python', 'Programming Language', 'Python is awesome'),
('Food', 'Maultaschen', 'Eat! Or you''ll starve!'),
('Health', 'Sports', 'See category Food to stay healthy'),
('Hardware', 'Security', 'Hardware Hacking Topics');

-- Test entries for table flashcard2category
INSERT INTO flashcard2category (flashcardId, categoryId)
VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 4), (5, 5);
