package WebProgramming54.GRYBB.Resources;
import WebProgramming54.GRYBB.DAO.UserDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

@Path("/register")
public class register {

    /** A method to load the routing page of the web application
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @return FileInputStream the file containing the register page of the web application
     * @throws FileNotFoundException if the file containing the routing page of the web application is not found
     */
    @GET
    @Produces({MediaType.TEXT_HTML})

    public FileInputStream register(@Context HttpServletRequest request,
                                          @Context HttpServletResponse servletResponse)
            throws FileNotFoundException {
        String url = "../webapps/GRYBB/WEB-INF/pages/register.html";
        System.out.println("Go to Routing Page");
        File f = new File(url);
        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        return new FileInputStream(f);
    }

    /** A method to load the homepage of the web application
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @param   username the username entered by the user
     * @param   password the password entered by the user
     * @param   role the role selected by the user
     * @return String msg which states if the register was successful or not
     * @throws ClassNotFoundException if connection to the database fails
     * @throws SQLException if the query fails
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String ConnectDatabaseLogin(@FormParam("username") String username,
                                       @FormParam("password") String password,
                                       @FormParam("role") String role,
                                       @Context HttpServletRequest request,
                                       @Context HttpServletResponse servletResponse) throws IOException {

        String res = "";

        try {
            Class.forName("org.postgresql.Driver");
            UserDao user = new UserDao();
            if (user.registerUser(username, password, role)) {
                res = "register successfully";
            } else {
                res = "register failed";
            }
        } catch (ClassNotFoundException e) {
            res = "kkr je hebt pech " + e;
        }

        return res;
    }
}
