package si.fri.rso.uporabnik.models.converters;

import si.fri.rso.uporabnik.lib.Uporabnik;
import si.fri.rso.uporabnik.models.entities.UporabnikEntity;

public class UporabnikConverter {

    public static Uporabnik toDto(UporabnikEntity entity) {

        Uporabnik dto = new Uporabnik();
        dto.setId(entity.getId());
        dto.setUstvarjen(entity.getUstvarjen());
        dto.setUporabnik(entity.getKomentar());
        dto.setOcena(entity.getOcena());
        dto.setLokacija_id(entity.getLokacija_id());
        dto.setUser_id(entity.getUser_id());



        return dto;

    }

    public static UporabnikEntity toEntity(Uporabnik dto) {

        UporabnikEntity entity = new UporabnikEntity();
        entity.setUstvarjen(dto.getUstvarjen());
        entity.setKomentar(dto.getUporabnik());
        entity.setOcena(dto.getOcena());
        entity.setLokacija_id(dto.getLokacija_id());
        entity.setUser_id(dto.getUser_id());


        return entity;

    }

}
