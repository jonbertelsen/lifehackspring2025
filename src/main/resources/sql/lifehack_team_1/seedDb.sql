DO $$
DECLARE
new_quiz_id INT;
new_category_id INT;
BEGIN
-- CREATE QUIZ
INSERT INTO lifehack_team_1_quiz (title) VALUES ('Lifehack Team 1 Quiz') RETURNING id INTO new_quiz_id;

-- CREATE JAVA CATEGORY
INSERT INTO lifehack_team_1_categories (category_name, quiz_id) VALUES ('Java', new_quiz_id) RETURNING id INTO new_category_id;
INSERT INTO lifehack_team_1_questions (question, answer, point, category_id)
VALUES
    ('This term describes the smallest unit of a Java program that is runnable.', 'What is a method?', 100, new_category_id),
    ('In Java, this keyword is used to inherit the properties and methods of another class.', 'What is extends?', 200, new_category_id),
    ('This Java interface is used for sorting collections like ArrayLists and LinkedLists.', 'What is Comparator?', 300, new_category_id),
    ('In Java, this principle allows two or more methods in the same class to have the same name but different parameters.', 'What is method overloading?', 400, new_category_id),
    ('This term refers to a memory area that Java Virtual Machine (JVM) uses to store data being referenced from Java Native Interface (JNI) code, preventing it from being collected by garbage collection.', 'What is a reference pinning?', 500, new_category_id);

-- CREATE SQL CATEGORY
INSERT INTO lifehack_team_1_categories (category_name, quiz_id) VALUES ('SQL', new_quiz_id) RETURNING id INTO new_category_id;
INSERT INTO lifehack_team_1_questions (question, answer, point, category_id)
VALUES
    ('This SQL command is used to retrieve data from a database.', 'What is SELECT', 100, new_category_id),
    ('This SQL clause is used to filter the records returned from a SELECT query.', 'What is WHERE?', 200, new_category_id),
    ('This type of SQL JOIN returns only the records that have matching values in both tables.', 'What is INNER JOIN?', 300, new_category_id),
    ('This SQL function returns the number of distinct values in a column.', 'What is COUNT(DISTINCT column_name)?', 400, new_category_id),
    ('In SQL, this command is used to change the structure of a table, such as adding a column or changing a data type.', 'What is ALTER TABLE?', 500, new_category_id);

-- CREATE MOVIES CATEGORY
INSERT INTO lifehack_team_1_categories (category_name, quiz_id) VALUES ('Movies', new_quiz_id) RETURNING id INTO new_category_id;
INSERT INTO lifehack_team_1_questions (question, answer, point, category_id)
VALUES
    ('This 1997 blockbuster movie directed by James Cameron is set on a ship and stars Leonardo DiCaprio and Kate Winslet.', 'What is "Titanic"?', 100, new_category_id),
    ('This 2001 movie directed by Peter Jackson is the first in a trilogy based on the novels by J.R.R. Tolkien.', 'What is "The Lord of the Rings: The Fellowship of the Ring"?', 200, new_category_id),
    ('This 1980 film directed by Stanley Kubrick, based on a Stephen King novel, features Jack Nicholson saying the iconic line, "Heres Johnny!"', 'What is "The Shining"?', 300, new_category_id),
    ('In this 2000 film directed by Ridley Scott, which won the Academy Award for Best Picture, a former Roman General becomes a gladiator to seek revenge against the corrupt emperor who murdered his family.', 'What is "Gladiator"?', 400, new_category_id),
    ('Name the 1964 satirical comedy that was Stanley Kubricks first film to feature Peter Sellers, where he plays multiple roles including President Merkin Muffley.', 'What is "Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb"?', 500, new_category_id);

-- CREATE TRY YOUR LUCK CATEGORY
INSERT INTO lifehack_team_1_categories (category_name, quiz_id) VALUES ('Try your luck', new_quiz_id) RETURNING id INTO new_category_id;
INSERT INTO lifehack_team_1_questions (question, answer, point, category_id)
VALUES
    ('The number of birthdays a person has, no matter how old they get.', 'What is 1?', 100, new_category_id),
    ('The position you are in after overtaking the person in second place during a race.', 'What is second place?', 200, new_category_id),
    ('The word that is always spelled incorrectly in every dictionary.', 'What is "incorrect"?', 300, new_category_id),
    ('What happens when a red stone is thrown into the blue sea.', 'What is "it sinks"?', 400, new_category_id),
    ('The reason a police officer doesn’t stop a bus driver going the wrong way down a one-way street.', 'What is "because the driver is walking"?', 500, new_category_id);

-- CREATE MUSIC CATEGORY
INSERT INTO lifehack_team_1_categories (category_name, quiz_id) VALUES ('Music', new_quiz_id) RETURNING id INTO new_category_id;
INSERT INTO lifehack_team_1_questions (question, answer, point, category_id)
VALUES
    ('This Canadian artist sang "Sorry" and "Love Yourself."', 'Who is Justin Bieber?', 100, new_category_id),
    ('"Bohemian Rhapsody" was a hit song by this British band.', 'Who is Queen?', 200, new_category_id),
    ('This American singer is known as the "Queen of Pop" and famous for hits like "Like a Virgin" and "Vogue."', 'Who is Madonna?', 300, new_category_id),
    ('This influential British musician, known for his Ziggy Stardust persona, had a hit with the song "Space Oddity" in 1969.', 'Who is David Bowie?', 400, new_category_id),
    ('This classical composer became deaf in later life but continued to produce famous works like Symphony No. 9.', 'Who is Ludwig van Beethoven?', 500, new_category_id);

END $$;