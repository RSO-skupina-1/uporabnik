package si.fri.rso.uporabnik.models.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "uporabnik_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "UporabnikEntity.getAll",
                        query = "SELECT kd FROM UporabnikEntity kd"),
                @NamedQuery(name = "UporabnikEntity.getByUserId",
                        query = "SELECT kd FROM UporabnikEntity kd WHERE kd.id = " +
                                ":id"),
                @NamedQuery(name = "UporabnikEntity.getByUsername",
                        query = "SELECT kd FROM UporabnikEntity kd WHERE kd.usrename = " +
                                ":username"),
                @NamedQuery(name = "UporabnikEntity.getVisitedLocations",
                        query = "SELECT kd.visitedLocations FROM UporabnikEntity kd WHERE kd.id = " +
                                ":id")
        })



public class UporabnikEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String usrename;

    @Column(name = "password")
    private String password;

    @Column(name = "admin")
    private Boolean admin;
    @Column(name = "salt")
    private String salt;

    @Column(name = "visitedLocations")
    private String visitedLocations;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return usrename;
    }
    public void setUsername(String username) {
        this.usrename = username;
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
    public void setSalt(String salt) { this.salt = salt; }
    public String getVisitedLocations() {
        return visitedLocations;
    }

    public void setVisitedLocations(String visitedLocations) {
        this.visitedLocations = visitedLocations;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}