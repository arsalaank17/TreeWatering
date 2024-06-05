package WebProgramming54.GRYBB.DAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/** Represents an instance of Update Sessions
 * @author GRYBB Team 4
 */
@WebListener
public class UpdateSessionsINDB  implements ServletContextListener,Runnable {
    private static String host = "bronto.ewi.utwente.nl";
    private static String dbName = "dab_di20212b_122";
    private static String url = "jdbc:postgresql://" + host + ":5432/" + dbName /*"?currentSchema=movies"*/;
    private static String username = "dab_di20212b_122";
    private static String password = "Ng4eQ0Vz6Pbj55Ab";

    private static final int timeout = 10000000; //in mili
    private Thread thread;
    @Override
    /** A method to start a new session
     * @param ServletContextEvent servletContextEvent which is the Context event of the desired servlet
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        thread = new Thread(new UpdateSessionsINDB());
        thread.start();
    }

    @Override
    /** A method to end the session
     * @param ServletContextEvent servletContextEvent which is the Context event of the desired servlet
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        thread.interrupt();
        thread.stop();
    }


    @Override
    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("postgrsql Driver is found!");
        } catch (ClassNotFoundException e) {
            System.out.println("kkr je hebt pech " + e);
        }

        DatabaseQueries.updateSessionForTimeout(timeout);

        while(true) {
            //after timeout in TimeStamp
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DatabaseQueries.updateSessionForTimeout(timeout);
        }


    }
}
