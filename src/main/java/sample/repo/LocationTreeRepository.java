package sample.repo;

import sample.model.Location;
import sample.model.LocationTree;

import java.sql.Connection;
import java.util.List;

public interface LocationTreeRepository {
    List<LocationTree> getObjByLocationId(Integer idLocation, Connection con);
    void insert(List<LocationTree> lLocationTree, Connection con);
}
