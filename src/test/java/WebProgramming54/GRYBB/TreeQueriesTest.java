package WebProgramming54.GRYBB;

import WebProgramming54.GRYBB.DAO.treesQueries;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * This test class test everey function in TreesQueries class, so that we can ensure
 * the data we get from database is correct.
 * -------------------------------------------------------------------------
 * we use the same database table that our webapplication used              |
 * so for testDeleteTree method after test it we set it as Ignored          |
 * so that it will not excute everytime.                                    |
 * and After this test finished we add tree which id is 5 into database.    |
 * --------------------------------------------------------------------------
 */
public class TreeQueriesTest {


    @Ignore
    @Test
    public void testDeleteTree() {
        treesQueries treesqueries = new treesQueries();
        assertTrue(treesqueries.deleteTree(5));
    }
    @Ignore
    @Test
    public void testEditTree() {
        treesQueries treesqueries = new treesQueries();
        assertTrue(treesqueries.UpdateEditInfo(2, "Cherry", 7, "Enschede", 52.210575, 6.849658));
    }

    @Test
    public void testFilterByCity() {
        treesQueries treesqueries = new treesQueries();
        String result = treesqueries.sortByCity("Oldenzaal");
        assertThat(result, CoreMatchers.allOf(
                containsString("\"id\": 3"),
                containsString("\"id\": 4"),
                containsString("\"city\": \"Oldenzaal\"")
        ));
    }

    @Test
    public void testFilterByMultiCities() {
        treesQueries treesqueries = new treesQueries();
        String result = treesqueries.sortByCity("Oldenzaal&Enschede");
        assertThat(result, CoreMatchers.allOf(
                containsString("\"id\": 3"),
                containsString("\"id\": 4"),
                containsString("\"id\": 1"),
                containsString("\"id\": 2"),
                containsString("\"city\": \"Oldenzaal\""),
                containsString("\"city\": \"Enschede\"")));
    }

    @Test
    public void testFilterByWaterLevel(){
        treesQueries treesqueries = new treesQueries();
        String res = treesqueries.sortByWaterLevel("high");
        assertThat(res, CoreMatchers.allOf(
                containsString("\"id\": 2"),
                containsString("\"waterlevel\": 7")));
    }

    @Test
    public void testFilterByMultiWaterLevel(){
        treesQueries treesqueries = new treesQueries();
        String res = treesqueries.sortByWaterLevel("high&middle");
        assertThat(res, CoreMatchers.allOf(
                containsString("\"id\": 3"),
                containsString("\"id\": 4"),
                containsString("\"id\": 1"),
                containsString("\"id\": 2")));
    }

    @Test
    public void testFilterBytype(){
        treesQueries treesqueries = new treesQueries();
        String res = treesqueries.sortByType("Maple");
        assertThat(res, CoreMatchers.allOf(
                containsString("\"id\": 4"),
                containsString("\"id\": 1"),
                containsString("\"type\": \"Maple\"")));

    }

    @Test
    public void testFilterBymultiType(){
        treesQueries treesqueries = new treesQueries();
        String res = treesqueries.sortByType("Maple&Cherry");
        assertThat(res, CoreMatchers.allOf(
                containsString("\"id\": 4"),
                containsString("\"id\": 1"),
                containsString("\"id\": 2"),
                containsString("\"type\": \"Maple\""),
                containsString("\"type\": \"Cherry\"")));
    }

    @Test
    public void testGetAllTrees(){
        treesQueries treesqueries = new treesQueries();
        String res = treesqueries.getAllTreesInfo();
        assertThat(res, CoreMatchers.allOf(
                containsString("\"id\": 4"),
                containsString("\"id\": 1"),
                containsString("\"id\": 2"),
                containsString("\"id\": 3")));
    }

    @Test
    public void testUpdateWaterlevel(){
        treesQueries treesqueries = new treesQueries();
        assertTrue(treesqueries.UpdateWaterlevel(52.307328,6.928256,5));

    }
    @Test
    public void testSearchByTreeId(){
        treesQueries treesqueries = new treesQueries();
        String res = treesqueries.searchByTreeId(3);
        assertEquals("[{\"id\": 3," +
                " \"lastwatered\": \"2021-06-25 11:11:36.904\", " +
                "\"city\": \"Oldenzaal\", " +
                "\"waterlevel\": 4, " +
                "\"type\": \"Regulier\", " +
                "\"lat\": 52.2858,  " +
                "\"lng\": 6.91746  }]", res);
    }
}
