package si.fri.rso.uporabnik.lib;

import java.time.Instant;
import java.util.List;

public class Uporabnik {

    /* same attributes as in Uporabnik Entity */
    private Integer id;
    private String username;
    private String password;
    private Boolean admin;

    private String salt;

    private int[] visitedLocations;

    /* getter and setter methods for the entity's attributes */
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int[] getVisitedLocations() {
        return visitedLocations;
    }
    public void setVisitedLocations(int[] visitedLocations) {
        this.visitedLocations = visitedLocations;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
