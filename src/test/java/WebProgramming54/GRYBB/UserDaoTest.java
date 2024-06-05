package WebProgramming54.GRYBB;

import WebProgramming54.GRYBB.DAO.UserDao;
import WebProgramming54.GRYBB.model.User;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserDaoTest {
    /**
     * This test class test everey function in UserDao class, so that we can ensure
     * the data we get from database is correct.
     * -------------------------------------------------------------------------
     * we use the same database table that our webapplication used              |
     * so for testRegister method after test it we set it as Ignored            |
     * so that it will not excute everytime.                                    |
     * --------------------------------------------------------------------------
     */

    @Test
    public void testLogin() throws SQLException {
        UserDao userdao = new UserDao();

        User user = userdao.loginIn("test","testLogin123");

        assertTrue(user instanceof User);
    }

    @Ignore
    @Test
    public void testRegister(){
        UserDao userdao = new UserDao();
        assertTrue(userdao.registerUser("testRegister", "testRegister321","Gardener"));
    }
}
