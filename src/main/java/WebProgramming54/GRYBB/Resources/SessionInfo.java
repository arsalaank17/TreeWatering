package WebProgramming54.GRYBB.Resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;

/** Represents an instance of Session information
 * @author GRYBB Team 4
 */
@Path("/session")
public class SessionInfo {

    /** A method to load the session information of a user
     * @param  request  the request made by the servlet
     * @return InputStream the file containing the session of the user
     * @throws IOException if not logged in
     */
    @GET
    @Produces({MediaType.TEXT_HTML})
    public InputStream getSession(@Context HttpServletRequest request, @Context
        HttpServletResponse response)        throws IOException {

        HttpSession session = request.getSession();

        if(session == null) {

        } else {
        }
        String user = (String) session.getAttribute("username");
        System.out.println(System.getProperty("user.dir"));
        String url = "../webapps/GRYBB/WEB-INF/pages/login.html";


        return new FileInputStream(new File(url));
    }

}
