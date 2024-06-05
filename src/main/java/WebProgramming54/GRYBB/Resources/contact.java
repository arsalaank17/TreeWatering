package WebProgramming54.GRYBB.Resources;

import WebProgramming54.GRYBB.DAO.DatabaseQueries;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;


@Path("/contact")
public class contact {
    /** A method to load the homepage of the web application
     * @param  request  the request made by the servlet
     * @param  servletResponse the response given by the servlet
     * @return InputStream of the file containing the contact page of the web application
     * @throws IOException if not logged in
     */
    @GET
    @Produces({MediaType.TEXT_HTML})
    public InputStream getContactPage(@Context HttpServletRequest request,
                                   @Context HttpServletResponse servletResponse) throws
            IOException {
        //TODO  session    with     cookies
//        request.getCookies()
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String sessionID = (String) session.getAttribute("sessionIdentifier");
        System.out.println("User tries to visit /rest/home with username: " + username + "\t sessionID: " + sessionID);
        if (username != null && sessionID != null && DatabaseQueries
                .SessionIdAlreadyInDB(sessionID, username)) {
            System.out.println(System.getProperty("user.dir"));
            String url = "../webapps/GRYBB/WEB-INF/pages/contact.html";
            System.out.println("Go to contact page");
            // load the contact.html with FileReader From class Functions
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
