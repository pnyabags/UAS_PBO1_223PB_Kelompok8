package main.uas.pbo;
public class SessionLogin {
    private static dataUser currentUser;

    public void setCurrentUser(dataUser user) {
        currentUser = user;
    }

    public static dataUser getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }
}