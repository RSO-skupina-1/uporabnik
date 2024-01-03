package si.fri.rso.uporabnik.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import si.fri.rso.uporabnik.lib.Uporabnik;
import si.fri.rso.uporabnik.models.entities.UporabnikEntity;
import si.fri.rso.uporabnik.models.converters.UporabnikConverter;


@RequestScoped
public class UporabnikBean {

    private Logger log = Logger.getLogger(UporabnikBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Uporabnik> getUporabnik() {

        TypedQuery<UporabnikEntity> query = em.createNamedQuery(
                "UporabnikEntity.getAll", UporabnikEntity.class);

        List<UporabnikEntity> resultList = query.getResultList();

        return resultList.stream().map(UporabnikConverter::toDto).collect(Collectors.toList());

    }

    public List<Uporabnik> getUporabnikFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, UporabnikEntity.class, queryParameters).stream()
                .map(UporabnikConverter::toDto).collect(Collectors.toList());
    }

    public Uporabnik getUporabnik(Integer id) {

        UporabnikEntity uporabnikEntity = em.find(UporabnikEntity.class, id);

        if (uporabnikEntity == null) {
            throw new NotFoundException();
        }

        Uporabnik uporabnik = UporabnikConverter.toDto(uporabnikEntity);

        return uporabnik;
    }
    public List<Uporabnik> getUporabnikByDestinacija(Integer id) {

        TypedQuery<UporabnikEntity> query = em.createNamedQuery(
                "UporabnikEntity.getByLokacijaId", UporabnikEntity.class).setParameter("lokacija_id", id);

        List<UporabnikEntity> resultList = query.getResultList();

        return resultList.stream().map(UporabnikConverter::toDto).collect(Collectors.toList());

    }
    public List<Uporabnik> getUporabnikByUser(Integer id) {

        TypedQuery<UporabnikEntity> query = em.createNamedQuery(
                "UporabnikEntity.getByUserId", UporabnikEntity.class).setParameter("user_id", id);

        List<UporabnikEntity> resultList = query.getResultList();

        return resultList.stream().map(UporabnikConverter::toDto).collect(Collectors.toList());

    }
    public Uporabnik createUporabnik(Uporabnik uporabnik) {

        UporabnikEntity uporabnikEntity = UporabnikConverter.toEntity(uporabnik);

        try {
            beginTx();
            em.persist(uporabnikEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (uporabnikEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return UporabnikConverter.toDto(uporabnikEntity);
    }

    public Uporabnik putUporabnik(Integer id, Uporabnik uporabnik) {

        UporabnikEntity c = em.find(UporabnikEntity.class, id);

        if (c == null) {
            return null;
        }

        UporabnikEntity updatedUporabnikEntity = UporabnikConverter.toEntity(uporabnik);

        try {
            beginTx();
            updatedUporabnikEntity.setId(c.getId());
            updatedUporabnikEntity = em.merge(updatedUporabnikEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return UporabnikConverter.toDto(updatedUporabnikEntity);
    }

    public boolean deleteUporabnik(Integer id) {

        UporabnikEntity imageMetadata = em.find(UporabnikEntity.class, id);

        if (imageMetadata != null) {
            try {
                beginTx();
                em.remove(imageMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
