package app.entities;

import java.util.List;

public class Team1Entities {
    public static class Questions {
        private int id;
        private String question;
        private String answer;
        private int points;

        public Questions(int id, String question, String answer, int points) {
            this.id = id;
            setQuestion(question);
            setAnswer(answer);
            setPoints(points);
        }

        public Questions(int points, String answer, String question) {
            setPoints(points);
            setAnswer(answer);
            setQuestion(question);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            if (question == null) {
                throw new NullPointerException("Question cannot be null");
            }
            else if (question.isEmpty() || question.isBlank()) {
                throw new IllegalArgumentException("Question cannot be empty or blank");
            }
            else if (question.length() > 1000) {
                throw new IllegalArgumentException("Question cannot greater than 1000 characters");
            }
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            if (answer == null) {
                throw new NullPointerException("Answer cannot be null");
            }
            else if (answer.isEmpty() || answer.isBlank()) {
                throw new IllegalArgumentException("Answer cannot be empty or blank");
            }
            else if (answer.length() > 256) {
                throw new IllegalArgumentException("Answer cannot greater than 256 characters");
            }
            this.answer = answer;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            switch (points) {
                case 100:
                case 200:
                case 300:
                case 400:
                case 500:
                    this.points = points;
                    break;
                default: throw new IllegalArgumentException("Invalid point value");
            }
        }

        @Override
        public String toString() {
            return "Questions{" +
                    "id=" + id +
                    ", question='" + question + '\'' +
                    ", answer='" + answer + '\'' +
                    ", points=" + points +
                    '}';
        }
    }

    public static class Categories {
        private int id;
        private String categoryName;
        private List<Questions> questions;

        public Categories(int id, String categoryName, List<Questions> questions) {
            this.id = id;
            setCategoryName(categoryName);
            this.questions = questions;
        }

        public Categories(String categoryName, List<Questions> questions) {
            setCategoryName(categoryName);
            this.questions = questions;
        }

        public Categories(String categoryName) {
            setCategoryName(categoryName);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            if (categoryName == null) {
                throw new NullPointerException("Category name cannot be null");
            }
            else if (categoryName.isEmpty() || categoryName.isBlank()) {
                throw new IllegalArgumentException("Category name cannot be empty or blank");
            }
            else if (categoryName.length() > 64) {
                throw new IllegalArgumentException("Category name cannot be greater than 64 characters");
            }
            this.categoryName = categoryName;
        }

        public List<Questions> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Questions> questions) {
            this.questions = questions;
        }

        @Override
        public String toString() {
            return "Categories{" +
                    "id=" + id +
                    ", categoryName='" + categoryName + '\'' +
                    ", questions=" + questions +
                    '}';
        }
    }

    public static class Quiz {
        private int id;
        private String title;
        private List<Categories> categories;

        public Quiz(int id, String title, List<Categories> categories) {
            this.id = id;
            setTitle(title);
            this.categories = categories;
        }

        public Quiz(List<Categories> categories, String title) {
            this.categories = categories;
            setTitle(title);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            if (title == null) {
                throw new NullPointerException("Title cannot be null");
            }
            else if (title.isEmpty() || title.isBlank()) {
                throw new IllegalArgumentException("Title cannot be empty or blank");
            }
            else if (title.length() > 256) {
                throw new IllegalArgumentException("Title cannot be greater than 256 characters");
            }
            this.title = title;
        }

        public List<Categories> getCategories() {
            return categories;
        }

        public void setCategories(List<Categories> categories) {
            this.categories = categories;
        }

        @Override
        public String toString() {
            return "Quiz{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", categories=" + categories +
                    '}';
        }
    }

    public static class Users {
        private int id;
        private String name;
        private String password;
        private List<Quiz> quizzes;

        public Users(int id, String name, String password, List<Quiz> quizzes) {
            this.id = id;
            setName(name);
            setPassword(password);
            this.quizzes = quizzes;
        }

        public Users(String name, String password, List<Quiz> quizzes) {
            setName(name);
            setPassword(password);
            this.quizzes = quizzes;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            if (name == null) {
                throw new NullPointerException("Name cannot be null");
            }
            else if (name.isEmpty() || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be empty or blank");
            }
            else if (name.length() < 2) {
                throw new IllegalArgumentException("Name cannot be shorter than 2 characters");
            }
            else if (name.length() > 64) {
                throw new IllegalArgumentException("Name cannot be greater than 64 characters");
            }
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            if (password == null) {
                throw new NullPointerException("Password cannot be null");
            }
            else if (password.isEmpty() || password.isBlank()) {
                throw new IllegalArgumentException("Password cannot be empty or blank");
            }
            else if (password.length() < 10) {
                throw new IllegalArgumentException("Password cannot be shorter than 10 characters");
            }
            else if (password.length() > 64) {
                throw new IllegalArgumentException("Password cannot be greater than 64 characters");
            }
            this.password = password;
        }

        public List<Quiz> getQuizzes() {
            return quizzes;
        }

        public void setQuizzes(List<Quiz> quizzes) {
            this.quizzes = quizzes;
        }

        @Override
        public String toString() {
            return "Users{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    ", quizzes=" + quizzes +
                    '}';
        }
    }
}