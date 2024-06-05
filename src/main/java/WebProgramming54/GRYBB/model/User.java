package WebProgramming54.GRYBB.model;

import javax.xml.bind.annotation.XmlRootElement;
/** Represents a user of the web application
 * @author GRYBB team 4
 */
@XmlRootElement
public class User {
    private String username;
    private String password;
    //other variables?
    /** Creates a user with the specified username and password.
     * @param username The user's username
     * @param password The user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    /** Gets the user's username
     * @return A string representing the user's username
     *
     */
    public String getUsername() {
        return username;
    }
    /** Sets the user's username
     * @param username A String containing the user's
     *     username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /** Gets the user's passwords
     * @return A string representing the password of the user
     *
     */
    public String getPassword() {
        return password;
    }
    /** Sets the user's password
     * @param password A String containing the user's
     *     password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}