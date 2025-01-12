package main.uas.pbo;
public class SessionLogin {
    private static User currentUser;

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }
}