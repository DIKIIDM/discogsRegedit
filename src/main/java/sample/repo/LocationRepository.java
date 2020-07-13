package sample.repo;

import sample.model.Location;
import sample.model.Vinyl;

import java.util.List;

public interface LocationRepository {
    Location getObj(Integer id);
    List<Location> getObjs();
    Location insert(Location location);
    void delete(Location location);
}
