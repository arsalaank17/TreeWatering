package WebProgramming54.GRYBB.Resources;

import WebProgramming54.GRYBB.DAO.DatabaseQueries;
import WebProgramming54.GRYBB.DAO.treesQueries;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;

/** Represents the homepage.
 * @author GRYBB Team 4
 */
@Path("/home")
public class Homepage {

    private static String host = "bronto.ewi.utwente.nl";
    private static String dbName = "dab_di20212b_122";
    private static String url = "jdbc:postgresql://" + host + ":5432/" + dbName /*"?currentSchema=movies"*/;
    private static String username = "dab_di20212b_122";
    private static String password = "Ng4eQ0Vz6Pbj55Ab";

    /** A method to load the homepage of the web application
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @return InputStream of the file containing the homepage of the web application
     * @throws IOException if not logged in
     */
    @GET
    @Produces({MediaType.TEXT_HTML})

        public InputStream getHomePage(@Context HttpServletRequest request,
                                  @Context HttpServletResponse servletResponse) throws IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String sessionID = (String) session.getAttribute("sessionIdentifier");
        System.out.println("User tries to visit /rest/home with username: " + username + "\t sessionID: " + sessionID);
        if(username!= null && sessionID !=null && DatabaseQueries.SessionIdAlreadyInDB(sessionID,username)) {
            System.out.println(System.getProperty("user.dir"));
            String url = "../webapps/GRYBB/WEB-INF/pages/homepage.html";
            System.out.println("Go to Homepage");
            // load the homepage.html with FileReader From class Functions
            File f = new File(url);
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            return new FileInputStream(f);
        } else {    //you are not logged in yet, so OR wrong sessionIdentifier
            System.out.println("not logged yet or wrong sessionIdentifier");
            //empty session
            session.invalidate();
            servletResponse.sendRedirect("../rest/login");
            return null;
        }
    }

    /** A method to get all the data of the trees
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @return String res containing the information of the trees
     * @throws ClassNotFoundException if connection to the database fails
     */
    @GET
    @Produces({"text/plain"})

    public String getTreesData(@Context HttpServletRequest request, @Context HttpServletResponse servletResponse) {
        String res = null;

        try {
            Class.forName("org.postgresql.Driver");
            treesQueries treesQueries = new treesQueries();
            res = treesQueries.getAllTreesInfo();
            System.out.println(res);
        } catch (ClassNotFoundException sqle) {
            String var5 = "kkr je hebt pech " + sqle;
        }

        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println(res);
        return res;
    }

    /** A method to get all the data of the trees based on the applied filters (filter by water level) by the user
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @param  filterWaterlevel A string containing the filter water level selected by the user
     * @return String res containing the information of the trees with the filter
     * @throws ClassNotFoundException if connection to the database fails
     */
    @GET
    @Path("/waterlevel/{filterWaterlevel}")
    @Produces({"text/plain"})
    public String getTreeswaterlevelFilter(@PathParam("filterWaterlevel") String filterWaterlevel, @Context HttpServletRequest request, @Context HttpServletResponse servletResponse) {
        String res = null;

        try {
            Class.forName("org.postgresql.Driver");
            treesQueries treesQueries = new treesQueries();
            if (filterWaterlevel.equals("NoQuery")) {
                res = treesQueries.getAllTreesInfo();
            } else {
                res = treesQueries.sortByWaterLevel(filterWaterlevel);
                System.out.println(res);
            }
        } catch (ClassNotFoundException e) {
            String msg = "kkr je hebt pech " + e;
        }

        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println(res);
        return res;
    }

    /** A method to get all the data of the trees based on the applied filter type by the user
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @param  filterType A string containing the filter type selected by the user
     * @return String res containing the information of the trees with the filter
     * @throws ClassNotFoundException if connection to the database fails
     */
    @GET
    @Path("/type/{filterType}")
    @Produces({"text/plain"})

    public String filterType(@PathParam("filterType") String filterType, @Context HttpServletRequest request, @Context HttpServletResponse servletResponse) {
        System.out.println("\nTesting treesData in JSON with filter \"" + filterType + "\"");
        String result = "";

        try {
            Class.forName("org.postgresql.Driver");
            treesQueries treesQueries = new treesQueries();
            if (filterType.equals("NoQuery")) {
                result = treesQueries.getAllTreesInfo();
            } else {
                result = treesQueries.sortByType(filterType);
            }
        } catch (ClassNotFoundException e) {
            String msg = "kkr je hebt pech " + e;
        }

        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        return result;
    }

    /** A method to get all the data of the trees based on the applied filter (city) by the user
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @param  filterCity A string containing the filter city selected by the user
     * @return String res containing the information of the trees with the filter
     * @throws ClassNotFoundException if connection to the database fails
     */
    @GET
    @Path("/city/{filterCity}")
    @Produces({"text/plain"})

    public String filterCity(@PathParam("filterCity") String filterCity, @Context HttpServletRequest request, @Context HttpServletResponse servletResponse) {
        System.out.println("\nTesting treesData in JSON with filter \"" + filterCity + "\"");
        String result = "";

        try {
            Class.forName("org.postgresql.Driver");
            treesQueries treesQueries = new treesQueries();
            if (filterCity.equals("NoQuery")) {
                result = treesQueries.getAllTreesInfo();
            } else {
                result = treesQueries.sortByCity(filterCity);
            }
        } catch (ClassNotFoundException e) {
            String msg = "kkr je hebt pech " + e;
        }

        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        return result;
    }

    /** A method to update the WaterLevel of the trees in the database
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @param  latForm A double containing the latitude of the tree whose water level needs to be updated
     * @param  lngForm A double containing the longitude of the tree whose water level needs to be updated
     * @param  newWaterLevel An integer containing the new waterlevel of the tree specified by the user
     * @return String res containing the information of the trees with the filter
     * @throws ClassNotFoundException if connection to the database fails
     */
    @GET
    @Produces({"text/plain"})
    @Consumes({"text/plain"})
    @Path("{newWaterLevel}")

    public String updateWaterLevel(@QueryParam("lat") double latForm, @QueryParam("lng") double lngForm, @PathParam("newWaterLevel") int newWaterLevel, @Context HttpServletRequest request, @Context HttpServletResponse servletResponse) throws IOException {
        String res = "";

        try {
            Class.forName("org.postgresql.Driver");
            treesQueries treesQueries = new treesQueries();
            if (treesQueries.UpdateWaterlevel(latForm, lngForm, newWaterLevel)) {
                res = "update successfully";
            } else {
                res = "update failed";
            }
        } catch (ClassNotFoundException e) {
            String msg = "kkr je hebt pech " + e;
        }

        return res;
    }

    /** A method to update the WaterLevel of the trees in the database
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @param  id An integer containing the id of the tree specified by the user
     * @return String res containing the information of the trees with the filter
     * @throws ClassNotFoundException if connection to the database fails
     */
    @GET
    @Path("/search")
    @Produces({"text/plain"})

    public String searchbyID(@QueryParam("id") int id, @Context HttpServletRequest request, @Context HttpServletResponse servletResponse) {
        String result = "";

        try {
            Class.forName("org.postgresql.Driver");
            treesQueries treesQueries = new treesQueries();

                result = treesQueries.searchByTreeId(id);

        } catch (ClassNotFoundException e) {
            String msg = "kkr je hebt pech " + e;
        }

        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        return result;
    }

}
