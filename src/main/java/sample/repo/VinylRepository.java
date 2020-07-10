package sample.repo;

import sample.model.Vinyl;

import java.util.List;

public interface VinylRepository {
    Vinyl getObj(Integer id);
    List<Vinyl> getObjs();
    List<Vinyl> getObjsByLocation(Integer idLocation);
    Vinyl insert(Vinyl vinyl);
    void delete(Vinyl vinyl);
}
