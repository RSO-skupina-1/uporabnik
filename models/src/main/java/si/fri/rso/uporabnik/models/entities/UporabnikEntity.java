package si.fri.rso.uporabnik.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "uporabnik_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "UporabnikEntity.getAll",
                        query = "SELECT kd FROM UporabnikEntity kd"),
                @NamedQuery(name = "UporabnikEntity.getByUserId",
                        query = "SELECT kd FROM UporabnikEntity kd WHERE kd.user_id = " +
                                ":user_id"),
                @NamedQuery(name = "UporabnikEntity.getByLokacijaId",
                        query = "SELECT kd FROM UporabnikEntity kd WHERE kd.lokacija_id = " +
                                ":lokacija_id")
        })



public class UporabnikEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "lokacija_id")
    private Integer lokacija_id;
    @Column(name = "uporabnik")
    private String uporabnik;

    @Column(name = "ocena")
    private Integer ocena;

    @Column(name = "ustvarjen")
    private Instant ustvarjen;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public Integer getLokacija_id() {
        return lokacija_id;
    }
    public void setLokacija_id(Integer lokacija_id) {
        this.lokacija_id = lokacija_id;
    }
    public String getKomentar() {
        return uporabnik;
    }
    public void setKomentar(String uporabnik) {
        this.uporabnik = uporabnik;
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