package sample.jdbc;

import sample.model.Vinyl;
import sample.repo.VinylRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBCVinylRepository implements VinylRepository {
    public Vinyl getObj(Integer id) {
        return null;
    }

    public List<Vinyl> getObjs() {
        return new ArrayList<>(Arrays.asList(
                 new Vinyl(1, "Like A Virgin", "Madonna", "Sire", "WX 20", 1985, 100.)
                ,new Vinyl(1, "Super Trouper", "ABBA", "Polar", "POLS 322", 1980, 59.)
        ));
    }

    public List<Vinyl> getObjsByLocation(Integer idLocation) {
        return null;
    };

    public Vinyl insert(Vinyl vinyl) {
        return null;
    };

    public void delete(Vinyl vinyl) {

    }
}
