package app.entities.team10;

public class Team10User {

        private int userId;
        private String email;
        private String password;  // Hashed password
        private String role;  // "user" or "admin"

        public Team10User(int userId, String email, String password, String role) {
            this.userId = userId;
            this.email = email;
            this.password = password;
            this.role = role;
        }

        // Getters and Setters
        public int getUserId() { return userId; }
        public void setUserId(int userId) { this.userId = userId; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        @Override
        public String toString() {
            return "Team05User ID: " + userId + ", Email: " + email + ", Role: " + role;
        }
    }

