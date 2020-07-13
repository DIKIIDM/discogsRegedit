package sample.jdbc;

import sample.model.Location;
import sample.model.Vinyl;
import sample.repo.LocationRepository;
import sample.repo.VinylRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBCLocalRepository implements LocationRepository {
    //----------------------------------------------------------------------------------
    public Location getObj(Integer id) {
        return null;
    }
    //----------------------------------------------------------------------------------
    public List<Location> getObjs() {
        return new ArrayList<>(Arrays.asList(
                 new Location(1, "001", "House", null, null, "")
                ,new Location(2, "002", "Garage", null, null, "")
                ,new Location(3, "003", "Rack 1", 1, null, "")
                ,new Location(4, "004", "Shelf 1", 3, null, "")
                ,new Location(5, "005", "Shelf 2", 3, null, "")
        ));
    }
    //----------------------------------------------------------------------------------
    public Location insert(Location location) {
        return null;
    };
    //----------------------------------------------------------------------------------
    public void delete(Location location) {

    }
}
