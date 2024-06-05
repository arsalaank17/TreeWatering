package WebProgramming54.GRYBB.Resources;


import WebProgramming54.GRYBB.DAO.DatabaseQueries;
import WebProgramming54.GRYBB.model.User;
import WebProgramming54.GRYBB.DAO.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.sql.*;

/** Represents an instance of a login request.
 * @author GRYBB Team 4
 */

@Path("/login")
public class LoginRequest {

    @Context
    UriInfo uriInfo;
    /** A method to load the homepage of the web application
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @param   userForm the username entered by the user
     * @param   passForm the password entered by the user
     * @return String msg which states if the login was successful or not
     * @throws ClassNotFoundException if connection to the database fails
     * @throws SQLException if the query fails
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)

    public String ConnectDatabaseLogin(@FormParam("username") String userForm,
                                       @FormParam("password") String passForm, @Context HttpServletRequest request,
                                       @Context HttpServletResponse servletResponse) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String msg;
        System.out.println("\n");
        System.out.println("Via formParam:\tUsername: " + userForm + "\t Password: " + passForm);
        System.out.println("Via Querylink:\tUsername: " + username + "\t Password: " + password);
        try {
            Class.forName("org.postgresql.Driver");
            UserDao userDao = new UserDao();
            try {
                User user = userDao.loginIn(username, password);

                if (user != null) {   //you found a valid user with pasword!
                    msg = "It worked!";

                    boolean idAlreadyUsed = true;
                    String id = null;
                    while (idAlreadyUsed) {
                        id = String.valueOf((int) (Math.random() * (2 ^ 128 - 1)));
                        idAlreadyUsed = DatabaseQueries.SessionIdAlreadyInDB(id, username);
                        System.out.println("ok");
                    }
                    DatabaseQueries.insertOrDeleteSessionID(id, username, true);
                    HttpSession session = request.getSession();

                    System.out.println("succesfully valid account, put it in log in with Unique Indintifier");
                    session.setAttribute("username", username);
                    session.setAttribute("password", password);
                    session.setAttribute("sessionIdentifier", id);//also create a sessionID
                    servletResponse.setHeader("Location", "http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home");

                } else {
                    msg = "wrong username/password or user doesn't exist! redirect to login page";
                }

            } catch (SQLException e /* /*ServletException e*/) {
                msg = e.getMessage();
            }
        } catch (ClassNotFoundException e) {
            msg = "kkr je hebt pech " + e;
        }
        System.out.println(msg);
        return msg;

    }

    /** A method to load the homepage of the web application
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @return InputStream the file containing the homepage of the web application
     * @throws IOException if the user is not logged in
     */
    @GET
    @Produces({MediaType.TEXT_HTML})

    public InputStream getString(@Context HttpServletRequest request,
                                 @Context HttpServletResponse servletResponse) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String sessionID = (String) session.getAttribute("sessionIdentifier");

        System.out.println("in GET METHOD /login: username: " + username + "\t sessionID: " + sessionID);
        if (username != null && sessionID != null && DatabaseQueries.SessionIdAlreadyInDB(sessionID, username)) {
            servletResponse.sendRedirect("../rest/home");
            return null;
        } else {    //you are not logged in yet, so OR wrong sessionIdentifier
            servletResponse.setContentType("text/html;charset=UTF-8");
            System.out.println(System.getProperty("user.dir"));
            System.out.println("Your are not logged in yet so load login.html page");
            String url = "../webapps/GRYBB/WEB-INF/pages/login.html";
            // load the homepage.html with FileReader From class Functions
            File f = new File(url);
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            return new FileInputStream(f);
        }

    }
}
