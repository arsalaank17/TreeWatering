package WebProgramming54.GRYBB.DAO;

import WebProgramming54.GRYBB.model.User;

import java.sql.*;
/** Represents an instance of a user DAO
 * @author GRYBB Team 4
 */
public class UserDao {
    private static final String host = "bronto.ewi.utwente.nl";
    private static final String dbName = "dab_di20212b_122";
    private static final String url = "jdbc:postgresql://" + host + ":5432/" + dbName /*"?currentSchema=movies"*/;
    private static final String username = "dab_di20212b_122";
    private static final String password = "Ng4eQ0Vz6Pbj55Ab";

    /** A method to login the user
     * @param  loginPassword the password of the user entered during login
     * @param  loginUsername the username of the user entered during login
     * @return User user the user object that wants to log in
     * @throws SQLException if connection to the database fails
     */
    public User loginIn(String loginUsername, String loginPassword) throws SQLException{

        System.out.println("\nTesting logging in in UserDao with username "+loginUsername+" with password " +loginPassword);
        User user = null;

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("postgrsql Driver is found!");
        } catch (ClassNotFoundException e) {
            System.out.println("kkr je hebt pech " + e);
        }


        String query = "SELECT username,password FROM grybb.loginaccounts WHERE username = ? AND password = ?";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, loginUsername);
        st.setString(2, loginPassword);
        ResultSet resultSet = st.executeQuery();

        if(resultSet.next()) {      //if you got a result, then create a new user for information
            user = new User(loginUsername,loginPassword);
        }
        connection.close();

        return user;
    }

    /** A method to register the user
     * @param  user the username of the user entered during register
     * @param pass  the password of the user entered during register
     * @param role  the role of the user entered during register
     * @return User user the user object that wants to log in
     * @throws SQLException if connection to the database fails
     */
    public Boolean registerUser(String user,String pass, String role) {
        System.out.println("\nTesting input register user " + user +"/" + pass +"/" +role);
        try {
            String query1 = "INSERT INTO GRYBB.loginaccounts" +
                    "   (username, password,typeuser) VALUES " +
                    " (?, ?, ?);";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st1 = connection.prepareStatement(query1);
                st1.setString(1, user);
                st1.setString(2, pass);
                st1.setString(3, role);
                st1.executeUpdate();
             connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
            return false;
        }
        return true;
    }

}
