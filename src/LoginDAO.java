import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO {


    public static User validate(String userName, String password){

        Database database = new Database();

        for (User user: database.getUserList()) {
            if (user.getUserName().equalsIgnoreCase(userName)) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }
}
