package app.entities;

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
        private int quizId;

        public Categories(int id, String categoryName, int quizId) {
            this.id = id;
            this.categoryName = categoryName;
            this.quizId = quizId;
        }

        public Categories(String categoryName, int quizId) {
            this.categoryName = categoryName;
            this.quizId = quizId;
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

        public int getQuizId() {
            return quizId;
        }

        public void setQuizId(int quizId) {
            this.quizId = quizId;
        }

        @Override
        public String toString() {
            return "Categories{" +
                    "id=" + id +
                    ", categoryName='" + categoryName + '\'' +
                    ", quizId=" + quizId +
                    '}';
        }
    }

    public class Quiz {
        private int id;
        private String title;

        public Quiz(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public Quiz(String title) {
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

        @Override
        public String toString() {
            return "Quiz{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public class Users {
        private int id;
        private String name;
        private String password;

        public Users(int id, String name, String password) {
            this.id = id;
            this.name = name;
            this.password = password;
        }

        public Users(String name, String password) {
            this.name = name;
            this.password = password;
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

        @Override
        public String toString() {
            return "Users{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
