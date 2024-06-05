package WebProgramming54.GRYBB.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/** Represents an instance of a tree query
 * @author GRYBB Team 4
 */
import WebProgramming54.GRYBB.model.Tree;

public class treesQueries {
    private static String host = "bronto.ewi.utwente.nl";
    private static String dbName = "dab_di20212b_122";
    private static String  url = "jdbc:postgresql://" + host + ":5432/" + dbName;
    private static String username = "dab_di20212b_122";
    private static String password = "Ng4eQ0Vz6Pbj55Ab";
    public treesQueries() {
    }
    /** A method to sort the tree by the water levels
     * @param  waterlevelStr a String representing the waterlevel selected by the user to sort by
     * @return String jsonString containing the tree which match the sorting criteria
     * @throws SQLException if the connection to the database fails
     */
    public String sortByWaterLevel(String waterlevelStr) {
        System.out.println("\nTesting treesData in JSON with filter \"" + waterlevelStr + "\"");
        List<Tree> treeList = new ArrayList();
        StringBuilder jsonString = new StringBuilder("[");

        try {
            String[] splitedstr = waterlevelStr.split("&");
            int wlrange1 = 0;
            int wlrange2 = 0;
            String query2 = "SELECT * FROM grybb.trees WHERE waterlevel between ? and ?";
            if (splitedstr.length > 1) {
                for(int i = 1; i < splitedstr.length; ++i) {
                    query2 = query2 + "or waterlevel between ? and ?";
                }
            }

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement(query2);
            System.out.println(query2);

            for(int i = 0; i < splitedstr.length; ++i) {
                String wlStr = splitedstr[i];
                System.out.println(wlStr);
                if (wlStr.equals("low")) {
                    wlrange1 = 0;
                    wlrange2 = 3;
                }

                if (wlStr.equals("middle")) {
                    wlrange1 = 4;
                    wlrange2 = 6;
                }

                if (wlStr.equals("high")) {
                    wlrange1 = 7;
                    wlrange2 = 10;
                }

                st.setInt(1 + 2 * i, wlrange1);
                st.setInt(2 + 2 * i, wlrange2);
            }

            ResultSet resultSet = st.executeQuery();
            int i = 1;
            boolean result = false;
            Tree tree;
            if (resultSet.next()) {
                result = true;
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(tree.toStringJSON());
            }

            while(resultSet.next()) {
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(" , ").append(tree.toStringJSON());
            }

            if (!result) {
                System.out.println("No Data found doesn't exist");
            }

            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
        }

        jsonString.append("]");
        return jsonString.toString();
    }

    /** A method to sort the tree by their type
     * @param  types a String representing the types selected by the user to sort by
     * @return String jsonString containing the tree which match the sorting criteria
     * @throws SQLException if the connection to the database fails
     */
    public String sortByType(String types) {
        String[] splitedstr = types.split("&");
        System.out.println("\nTesting treesData in JSON with filter \"" + types + "\"");
        List<Tree> treeList = new ArrayList();
        StringBuilder jsonString = new StringBuilder("[");

        try {
            String query1 = "SELECT * FROM grybb.trees where type = ?";
            if (splitedstr.length > 1) {
                for(int i = 1; i < splitedstr.length; ++i) {
                    query1 = query1 + "or type = ?";
                }
            }

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement(query1);

            for(int i = 0; i < splitedstr.length; ++i) {
                String type = splitedstr[i];
                System.out.println(type);
                st.setString(1 + i, type);
                System.out.println(1 + i + " || " + type);
            }

            ResultSet resultSet = st.executeQuery();
            int i = 1;
            boolean result = false;
            Tree tree;
            if (resultSet.next()) {
                result = true;
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(tree.toStringJSON());
            }

            while(resultSet.next()) {
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(" , ").append(tree.toStringJSON());
            }

            if (!result) {
                System.out.println("No Data found doesn't exist");
            }

            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
        }

        jsonString.append("]");
        return jsonString.toString();
    }
    /** A method to update the water levels of the trees
     * @param  lat a double representing the latitude of the tree to be updated
     * @param  lng a double representing the longitude of the tree to be updated
     * @return Boolean if the waterlevel was successfully updated
     * @throws SQLException if the connection to the database fails
     */
    public Boolean UpdateWaterlevel(double lat, double lng, int waterlevel) {
        System.out.println("\nTesting input type query " + waterlevel);
        new ArrayList();
        new StringBuilder("[");

        try {
            String query1 = "update grybb.trees set waterlevel = ? , lastwatered = ? where lat = ? and lng = ?";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st1 = connection.prepareStatement(query1);
            st1.setInt(1, waterlevel);
            st1.setTimestamp(2, new Timestamp((new Date()).getTime()));
            System.out.println(new Timestamp((new Date()).getTime()));
            st1.setDouble(3, lat);
            st1.setDouble(4, lng);
            st1.executeUpdate();
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
            return false;
        }

        return true;
    }
    /** A method to search for  a tree based on their ID
     * @param  id the id of the tree to be searched input by the user
     * @return String containing all the trees which match the search criteria
     * @throws SQLException if the connection to the database fails
     */
    public String searchByTreeId(int id) {
        System.out.println("\nTesting input search query " + id);
        List<Tree> treeList = new ArrayList();
        StringBuilder jsonString = new StringBuilder("[");

        try {
            String query1 = "select * from  grybb.trees where id = ?";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st1 = connection.prepareStatement(query1);
            st1.setInt(1, id);
            ResultSet resultSet = st1.executeQuery();
            int i = 1;
            boolean result = false;
            Tree tree;
            if (resultSet.next()) {
                result = true;
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(tree.toStringJSON());
            }

            while(resultSet.next()) {
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(" , ").append(tree.toStringJSON());
            }

            if (!result) {
                System.out.println("No Data found doesn't exist");
            }

            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
        }

        jsonString.append("]");
        return jsonString.toString();
    }
    /** A method to get information on all the trees
     * @return String containing all the trees' information
     * @throws SQLException if the connection to the database fails
     */
    public String getAllTreesInfo() {
        List<Tree> treeList = new ArrayList();
        StringBuilder jsonString = new StringBuilder("[");

        try {
            String query1 = "select * from  grybb.trees ";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st1 = connection.prepareStatement(query1);
            ResultSet resultSet = st1.executeQuery();
            int i = 1;
            boolean result = false;
            Tree tree;
            if (resultSet.next()) {
                result = true;
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(tree.toStringJSON());
            }

            while(resultSet.next()) {
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(" , ").append(tree.toStringJSON());
            }

            if (!result) {
                System.out.println("No Data found doesn't exist");
            }

            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
        }

        jsonString.append("]");
        return jsonString.toString();
    }
    /** A method to delete a tree based on their id
     * @param  id the id of the tree to be deleted which is input by the user
     * @return String containing the updated values which is null since the tree has been deleted
     */
    public boolean deleteTree(int id) {
        boolean success = false;

        try {
            String query1 = "delete from grybb.trees where id=?";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st1 = connection.prepareStatement(query1);
            st1.setInt(1, id);
            st1.executeUpdate();
            success = true;
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
            success = false;
        }

        return success;
    }
    /** A method to sort the trees based on the city they are located in
     * @param  filterCity which is the name of the city the user wishes to filter by
     * @return String containing all the trees which match the user's filters
     * @throws SQLException if connection to the database fails
     */
    public String sortByCity(String filterCity) {
        String[] splitedstr = filterCity.split("&");
        System.out.println("\nTesting treesData in JSON with filter \"" + filterCity + "\"");
        List<Tree> treeList = new ArrayList();
        StringBuilder jsonString = new StringBuilder("[");

        try {
            String query1 = "SELECT * FROM grybb.trees where city = ?";
            if (splitedstr.length > 1) {
                for(int i = 1; i < splitedstr.length; ++i) {
                    query1 = query1 + "or city = ?";
                }
            }

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement(query1);

            for(int i = 0; i < splitedstr.length; ++i) {
                String type = splitedstr[i];
                System.out.println(type);
                st.setString(1 + i, type);
                System.out.println(1 + i + " || " + type);
            }

            ResultSet resultSet = st.executeQuery();
            int i = 1;
            boolean result = false;
            Tree tree;
            if (resultSet.next()) {
                result = true;
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(tree.toStringJSON());
            }

            while(resultSet.next()) {
                tree = new Tree(resultSet.getString("id"), resultSet.getTimestamp("lastwatered"), resultSet.getString("city"), resultSet.getInt("waterlevel"), resultSet.getString("type"), resultSet.getDouble("lat"), resultSet.getDouble("lng"));
                System.out.println(tree.toStringJSON());
                treeList.add(tree);
                jsonString.append(" , ").append(tree.toStringJSON());
            }

            if (!result) {
                System.out.println("No Data found doesn't exist");
            }

            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
        }

        jsonString.append("]");
        return jsonString.toString();
    }
    /** A method to update the edit info based on all the attributes of a tree
     * @param  id  which is the id of the tree
     * @param  type which is the type of the tree
     * @param  waterlevel which is the waterlevel of the tree
     * @param  lat which is the latitiude of the tree
     * @param  lng which is the longitude of the tree
     * @param city  which is the city of the tree
     * @return Boolean to indicate if the updation of the trees' information was successful or not
     * @throws SQLException if connection to the database fails
     */
    public Boolean UpdateEditInfo(int id, String type, int waterlevel, String city, double lat, double lng) {
        System.out.println("\nTesting input Edit query ");
        try {
            String query1 = "update grybb.trees set type = ?, waterlevel = ? , lastwatered = ?, lat = ?," +
                    "lng = ?,city = ? where id = ?";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st1 = connection.prepareStatement(query1);
            st1.setString(1, type);
            st1.setInt(2,waterlevel);
            st1.setTimestamp(3, new Timestamp((new Date()).getTime()));
            System.out.println(new Timestamp((new Date()).getTime()));
            st1.setDouble(4, lat);
            st1.setDouble(5, lng);
            st1.setString(6,city);
            st1.setInt(7,id);
            st1.executeUpdate();
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("kkr connecting not: " + sqle);
            return false;
        }
        return true;
    }

}
