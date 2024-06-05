package WebProgramming54.GRYBB.DAO;

import java.sql.*;
import java.util.Date;
/** Represents an instance of Database Queries
 * @author GRYBB Team 4
 */
public class DatabaseQueries {

    private static String host = "bronto.ewi.utwente.nl";
    private static String dbName = "dab_di20212b_122";
    private static String url = "jdbc:postgresql://" + host + ":5432/" + dbName /*"?currentSchema=movies"*/;
    private static String username = "dab_di20212b_122";
    private static String password = "Ng4eQ0Vz6Pbj55Ab";

    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("postgrsql Driver is found!");
        } catch (ClassNotFoundException e) {
            System.out.println("kkr je hebt pech " + e);
        }


        String query = "SELECT DISTINCT p1.name " +
            "FROM movies.person p1, movies.person p2,movies.movie m, movies.writes w, movies.acts a " +
            "WHERE w.mid = m.mid AND w.pid = p1.pid AND p2.name = 'Harrison Ford' " +
            "AND p2.pid = a.pid AND a.mid = m.mid ";


        try {
            System.out.println("Testing a Database in Schema Movies of table movie");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println((resultSet.getString(1)));
            }
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
        }

        System.out.println(loginIn("Daniël","Password"));
        System.out.println(loginIn("yourMom", "Tyfus"));
        try {
            System.out.println(new UserDao().loginIn("Daniël", "Password").getUsername());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String sessionID = "12345";
        String username = "yeet";
        System.out.println("Check if SessionIdentifier "+sessionID+" with username " + username);
        SessionIdAlreadyInDB(sessionID,username);
        insertOrDeleteSessionID(sessionID,username,true);
        try {
            System.out.println("wait for 3 seconds");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        insertOrDeleteSessionID(sessionID,username,false);


    }
    /** A method to check if the user has correct credentials entered in the login page of the web application
     * @param loginUsername  The username entered by the user
     * @param  loginPassword the password entered by the user
     * @return String msg telling the user about its login status
     * @throws SQLException if connection to the database fails
     */
    public static String loginIn(String loginUsername, String loginPassword) {
        System.out.println("\nTesting logging in with username "+loginUsername+" with password " +loginPassword);
        String msg;
        try{
            String query = "SELECT username,password FROM grybb.loginaccounts WHERE username = ? AND password = ?";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, loginUsername);
            st.setString(2, loginPassword);
            ResultSet resultSet = st.executeQuery();

            if(resultSet.next()) {
                msg = "Logged In Succesfully with Username + Password: "
                    + resultSet.getString(1) +"\t"+resultSet.getString(2);
            } else {
                msg = "Your account doesn't exist";
            }
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: "+ sqle);
            msg = "SQLException oof";
        }

        return msg;

    }
    /** A method to check if the sessionID is already in use
     * @param  id the id of the user
     * @param  user the username of the user
     * @return boolean sessionInDB boolean expression to represent if the user already has a sessionID
     * @throws SQLException if connection to the database fails
     */
    public static boolean SessionIdAlreadyInDB(String id, String user) {
        boolean sessionInDB = false;
        boolean result = false;
        try{
            String query = "SELECT sessionidentifier FROM grybb.sessioncookie WHERE username = ?";
            Connection connection = DriverManager.getConnection(url,username, password);
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, user);
            ResultSet resultSet = st.executeQuery();


            while(resultSet.next()) {
                result = true;
                String existingID = resultSet.getString(1);
                System.out.println("ExistingID: " + existingID);
                if (existingID.equals(id)) {
                    System.out
                        .println("There exist a account with this SessionIdentifier already");
                    sessionInDB = true;
                }
            }
            if(!sessionInDB) {
                System.out.println("This account has SessionIdentifier but not id " + id);
            }
            if(!result) {
                System.out.println("This Account has not even 1 SessionID yet ");

            }
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: "+ sqle);
            sessionInDB = true; //because error
        }

        return sessionInDB;




    }

    /** A method to modify a user's sessionID
     * @param  sessionId the session id of the user
     * @param  user the username of the user
     * @param  insertOrNot a boolean which checks if a new sessionID should be created or not
     * @return boolean succes to represent in boolean if the SessionID was modified successfully or not
     * @throws SQLException if connection to the database fails
     */
    public static boolean insertOrDeleteSessionID(String sessionId, String user, boolean insertOrNot) {
        boolean succes = true;
        try {
            String query;
            if(insertOrNot) {//INSERT the sessionID and user
                query =
                    "INSERT INTO grybb.sessioncookie(sessionidentifier,username) VALUES (?,?)";
            } else { //DELETE the user with sessionID
                query = "DELETE FROM grybb.sessioncookie WHERE sessionidentifier = ? AND username = ?";
            }
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, sessionId);
            st.setString(2, user);
            int number = st.executeUpdate();
            System.out.println("row " + number + " has been modified with sessionID " + sessionId +
                " and user "+ user);
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
            succes = false;
        }

        return true;

    }
    /** A method to update a session for timeout
     * @param timeout stores the amount of time before a timeout should occur in milliseconds
     * @throws SQLException if connection to the database fails
     */
    public static void updateSessionForTimeout(int timeout){ //timeout in miliseconds
        try {
            String query = "SELECT * FROM grybb.sessioncookie";
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Timestamp lastDeletedtimeStamp = null;
            long currentTime = new Date().getTime();
            Timestamp timestampTimeOut = new Timestamp(currentTime-timeout); //the timeOut range of 100.000 mili seconds

            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("sessiontime");
                int id = resultSet.getInt(1); //Primary key id of sessioncookie
                int sessionID = resultSet.getInt(3);
                //if the timeStamp is bigger than the timestampTImeOut, it still in the timeoutRange
                //compares the currentTime to a older one, must be greater than 0
                int number = timestampTimeOut.compareTo(timestamp);
                if(number >= 0 && (lastDeletedtimeStamp==null || timestamp.compareTo(lastDeletedtimeStamp) >= 0)) { //there is a timeout so delete the number
                    lastDeletedtimeStamp = timestamp;
                }
            }
            //you found the highestdeleted Timestamp that must be deleted, so everything that \is this
            //timestamp and lower must be deleted. Only delete if you found a timestamp that must be deleted
            if(lastDeletedtimeStamp!=null) {
                System.out.println("DELETE all the TimeStamps that are equal or older than " + lastDeletedtimeStamp.toString());
                query = "DELETE FROM grybb.sessioncookie WHERE sessiontime <= ?";
                PreparedStatement st = connection.prepareStatement(query);
                st.setTimestamp(1, lastDeletedtimeStamp);
                st.executeUpdate();

            }
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
