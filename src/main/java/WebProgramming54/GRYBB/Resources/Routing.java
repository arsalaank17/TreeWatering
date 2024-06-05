package WebProgramming54.GRYBB.Resources;

import WebProgramming54.GRYBB.DAO.DatabaseQueries;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.io.*;
/** Represents an instance of routing
 * @author GRYBB Team 4
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


@Path("/routing")
public class Routing {

    /** A method to load the routing page of the web application
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @return FileInputStream the file containing the routing page of the web application
     * @throws FileNotFoundException if the file containing the routing page of the web application is not found
     */
    @GET
    @Produces({MediaType.TEXT_HTML})
    public InputStream routingPlusMap(@Context HttpServletRequest request,
                                   @Context HttpServletResponse servletResponse) throws
            IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String sessionID = (String) session.getAttribute("sessionIdentifier");
        System.out.println("User tries to visit /rest/home with username: " + username + "\t sessionID: " + sessionID);
        if (username != null && sessionID != null && DatabaseQueries
                .SessionIdAlreadyInDB(sessionID, username)) {
            System.out.println(System.getProperty("user.dir"));
            String url = "../webapps/GRYBB/WEB-INF/pages/Routing.html";
            System.out.println("Go to contact page");
            // load the routing.html with FileReader From class Functions
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
}
