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

/** Represents an instance of ViewData
 * @author GRYBB Team 4
 */

@Path("/viewData")
public class ViewData {
    /** A method to load the View Data page of the web application
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @return InputStream the file containing the ViewData page
     * @throws IOException if not logged in
     */
    @GET
    @Produces({MediaType.TEXT_HTML})
    public InputStream getHomePage(@Context HttpServletRequest request,
                                   @Context HttpServletResponse servletResponse) throws
            IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String sessionID = (String) session.getAttribute("sessionIdentifier");
        System.out.println("User tries to visit /rest/home with username: " + username + "\t sessionID: " + sessionID);
        if (username != null && sessionID != null && DatabaseQueries
                .SessionIdAlreadyInDB(sessionID, username)) {
            System.out.println(System.getProperty("user.dir"));
            String url = "../webapps/GRYBB/WEB-INF/pages/viewData.html";
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
    /** A method to get the data of all the trees
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @return String res the string containing all the data of the trees including all the attributes
     * @throws ClassNotFoundException if the database connection fails
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

    /** A method to delete the trees based on their id
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @param  id the id of the tree to be deleted
     * @return String res indicating which tree has been deleted
     * @throws ClassNotFoundException if connection to the database fails
     */
    @GET
    @Produces({"text/plain"})
    @Consumes({"text/plain"})
    @Path("/delete/{treeId}")

    public String deleteTree(@PathParam("treeId") int id, @Context HttpServletRequest request, @Context HttpServletResponse servletResponse) throws IOException {
        String res = "";

        try {
            Class.forName("org.postgresql.Driver");
            treesQueries treesQueries = new treesQueries();
            if (treesQueries.deleteTree(id)) {
                res = "delete tree " + id + " successfully";
            } else {
                res = "delete tree " + id + " failed";
            }
        } catch (ClassNotFoundException e) {
            String msg = "kkr je hebt pech " + e;
        }

        return res;
    }

    @GET
    @Produces({"text/plain"})
    @Consumes({"text/plain"})
    @Path("/edit/{treeId}")
    /** A method to edit the trees based on all their attributes
     * @param HttpServletRequest request  the request made by the servlet
     * @param HttpServletResponse servletResponse the response given by the servlet
     * @param int id the id of the tree to be updated
     * @param String type the type of the tree to be updated
     * @param String  waterlevel the waterlevel of the tree to be updated
     * @param String  city the city of the tree to be updated
     * @param double  lat the lat of the tree to be updated
     * @param double  lng the lng of the tree to be updated
     * @return String res indicating which tree has been deleted
     * @throws IOException if user is not logged in
     */
    public String editTree(@PathParam("treeId") int id,
                             @QueryParam("type") String type,
                             @QueryParam("waterlevel") int waterlevel,
                             @QueryParam("city") String city,
                             @QueryParam("lat") double lat,
                             @QueryParam("lng") double lng,
                             @Context HttpServletRequest request, @Context HttpServletResponse servletResponse) throws IOException {
        String res = "";

        try {
            Class.forName("org.postgresql.Driver");
            treesQueries treesQueries = new treesQueries();
            if (treesQueries.UpdateEditInfo(id, type, waterlevel, city, lat, lng)) {
                res = "edit tree " + id + " successfully";
            } else {
                res = "edit tree " + id + " failed";
            }
        } catch (ClassNotFoundException e) {
            String msg = "kkr je hebt pech " + e;
        }

        return res;
    }

}