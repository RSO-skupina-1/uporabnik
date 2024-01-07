package si.fri.rso.uporabnik.models.converters;

import si.fri.rso.uporabnik.lib.Uporabnik;
import si.fri.rso.uporabnik.models.entities.UporabnikEntity;

public class UporabnikConverter {

    public static Uporabnik toDto(UporabnikEntity entity) {

        Uporabnik dto = new Uporabnik();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setAdmin(entity.getAdmin());
        dto.setPassword(entity.getPassword());
        dto.setSalt(entity.getSalt());
        dto.setVisitedLocations(parseIntArray(entity.getVisitedLocations()));

        return dto;

    }

    public static UporabnikEntity toEntity(Uporabnik dto) {

        UporabnikEntity entity = new UporabnikEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setAdmin(dto.getAdmin());
        entity.setSalt(dto.getSalt());
        entity.setVisitedLocations(stringify(dto.getVisitedLocations()));

        return entity;

    }

    private static int[] parseIntArray(String str) {
        String[] strArray = str.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
        int[] intArray = new int[strArray.length];
        for(int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }

    private static String stringify(int[] intArray) {
        String str = "[";
        for(int i = 0; i < intArray.length; i++) {
            str += intArray[i];
            if(i != intArray.length - 1) {
                str += ",";
            }
        }
        str += "]";
        return str;
    }

}
