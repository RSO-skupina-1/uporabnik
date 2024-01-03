package si.fri.rso.uporabnik.lib;

import java.time.Instant;

public class Uporabnik {

    /* same attributes as in Uporabnik Entity */
    private Integer id;
    private String uporabnik;
    private Integer user_id;
    private Integer lokacija_id;
    private Integer ocena;
    private Instant ustvarjen;

    /* getter and setter methods for the entity's attributes */
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUporabnik() {
        return uporabnik;
    }
    public void setUporabnik(String uporabnik) {
        this.uporabnik = uporabnik;
    }
    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id= user_id;
    }
    public Integer getLokacija_id() {
        return lokacija_id;
    }
    public void setLokacija_id(Integer lokacija_id) {
        this.lokacija_id = lokacija_id;
    }
    public Integer getOcena() {
        return ocena;
    }
    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }
    public Instant getUstvarjen() {
        return ustvarjen;
    }
    public void setUstvarjen(Instant ustvarjen) {
        this.ustvarjen = ustvarjen;
    }
}
