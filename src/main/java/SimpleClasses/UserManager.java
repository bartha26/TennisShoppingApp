package SimpleClasses;
public class UserManager {
    private static UserManager instance;
    private Users currentUser;

    private UserManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }
}
