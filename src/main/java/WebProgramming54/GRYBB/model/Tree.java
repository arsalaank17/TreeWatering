package WebProgramming54.GRYBB.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
/** Represents an instance of a tree
 * @author GRYBB Team 4
 */
@XmlRootElement
public class Tree {
    private String id;
    private Timestamp lastwatered;
    private String city;
    private int waterlevel;
    private String type;
    private double lat;
    private double lng;
    /** Creates a tree with the specified attributes.
     * @param id The tree’s id.
     * @param lastwatered The timestamp of when the tree was last watered.
     * @param city Which city the tree is located in
     * @param waterlevel The waterlevel of the tree
     * @param type type of the tree
     * @param lat latitude of the tree
     * @param lng longitude of the tree
     */
    public Tree(String id, Timestamp lastwatered, String city, int waterlevel, String type,
                double lat, double lng) {
        this.id = id;
        this.lastwatered = lastwatered;
        this.city = city;
        this.waterlevel = waterlevel;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
    }
    public Tree(double lat, double lng, int waterlevel) {
        this.waterlevel = waterlevel;
        this.lat = lat;
        this.lng = lng;
    }


    /** Gets the information of when the tree was last watered.
     * @return A Timestamp representing when the tree was last watered.
     *
     */
    public Timestamp getLastwatered() {
        return lastwatered;
    }
    /** Sets the information of when the tree was last watered.
     * @param lastwatered representing when the tree was last watered.
     *
     */

    public void setLastwatered(Timestamp lastwatered) {
        this.lastwatered = lastwatered;
    }

    /** Gets the information of the city of the tree.
     * @return A String city representing when the city of the tree.
     *
     */
    public String getCity() {
        return city;
    }
    /** Sets the information of the city of the tree.
     * @param  city representing the city of the tree.
     *
     */
    public void setCity(String city) {
        this.city = city;
    }
    /** Gets the information of the water level of the tree.
     * @return An integer representing the water level of the tree
     *
     */
    public int getWaterlevel() {
        return waterlevel;
    }
    /** Sets the information of the water level of the tree
     * @param   waterlevel representing the water level of the tree
     *
     */

    public void setWaterlevel(int waterlevel) {
        this.waterlevel = waterlevel;
    }
    /** Gets the information of the type of the tree
     * @return A String type representing the type of the tree
     *
     */
    public String getType() {
        return type;
    }
    /** Sets the information of the type of the tree
     * @param type A String type representing the type of the tree
     *
     */
    public void setType(String type) {
        this.type = type;
    }
    /** Gets the information of the latitude of the tree
     * @return double lat representing the latitude of the tree
     *
     */
    public double getLat() {
        return lat;
    }
    /** Sets the information of the latitude of the tree
     * @param  lat A double representing the latitude of the tree
     *
     */
    public void setLat(double lat) {
        this.lat = lat;
    }
    /** Gets the information of the longitude of the tree
     * @return A double lng representing the longitude of the tree
     *
     */
    public double getLng() {
        return lng;
    }
    /** Sets the information of the longitude of the tree
     * @param  lng A double representing the longitude of the tree
     *
     */
    public void setLng(double lng) {
        this.lng = lng;
    }


    /** Gets the information of the id of the tree
     * @return A String representing the id of the tree
     *
     */
    public String getId() {
        return id;
    }
    /** Sets the information of the id of the tree
     * @param  id representing the id of the tree
     *
     */
    public void setId(String id) {
        this.id = id;
    }
    /** Converts a string to stringJSON
     * @return String of all attributes of a tree in the form of JSON
     *
     */
    public String toStringJSON() {
       return "{\"id\": " + id + ", \"lastwatered\": \""+lastwatered+"\", \"city\": \""+city+"\", " +
           "\"waterlevel\": " +waterlevel+", \"type\": \""+type+"\", \"lat\": "+lat+",  \"lng\": "+lng+"  }";
    }
}
