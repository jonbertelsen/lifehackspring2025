package app.entities;

import java.util.List;

public class Team1Entities {
    public class Questions {
        private int id;
        private String question;
        private String answer;
        private int points;

        public Questions(int id, String question, String answer, int points) {
            this.id = id;
            this.question = question;
            this.answer = answer;
            this.points = points;
        }

        public Questions(int points, String answer, String question) {
            this.points = points;
            this.answer = answer;
            this.question = question;
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
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
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

    public class Categories {
        private int id;
        private String categoryName;
        private List<Questions> questions;

        public Categories(int id, String categoryName, List<Questions> questions) {
            this.id = id;
            this.categoryName = categoryName;
            this.questions = questions;
        }

        public Categories(String categoryName, List<Questions> questions) {
            this.categoryName = categoryName;
            this.questions = questions;
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

    public class Quiz {
        private int id;
        private String title;
        private List<Categories> categories;

        public Quiz(int id, String title, List<Categories> categories) {
            this.id = id;
            this.title = title;
            this.categories = categories;
        }

        public Quiz(List<Categories> categories, String title) {
            this.categories = categories;
            this.title = title;
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

    public class Users {
        private int id;
        private String name;
        private String password;
        private List<Quiz> quizzes;

        public Users(int id, String name, String password, List<Quiz> quizzes) {
            this.id = id;
            this.name = name;
            this.password = password;
            this.quizzes = quizzes;
        }

        public Users(String name, String password, List<Quiz> quizzes) {
            this.name = name;
            this.password = password;
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
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
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
