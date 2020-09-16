package sample.jdbc;

import sample.Core;
import sample.model.Location;
import sample.model.LocationTree;
import sample.repo.LocationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBCLocalRepository extends JDBCEntity implements LocationRepository {
    private static String SQL_GET_ALL = " " +
            "SELECT t.id                                              " +
            "      ,t.idParent                                        " +
            "      ,t.sTitle                                          " +
            "  FROM pv_location t                                     " +
            " WHERE 1 = 1                                             ";
    private static String SQL_GET_ONE = SQL_GET_ALL + " AND t.id = ?";
    private static String SQL_INSERT_ONE = " " +
            "INSERT INTO pv_location     " +
            "   (   idParent             " +
            "      ,sTitle               " +
            "   )                        " +
            " VALUES                     " +
            "   (?, ?); ";
    //----------------------------------------------------------------------------------
    public Location getObj(Integer id, Connection con) {
        Location result = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQL_GET_ONE);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = new Location(
                                rs.getInt("id")
                               ,""
                               ,rs.getString("sTitle")
                               ,rs.getInt("idParent")
                               ,null
                               ,""
                         );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            
        }
        return result;
    }
    //----------------------------------------------------------------------------------
    public Location getObj(Integer id) {
        return null;
    }
    //----------------------------------------------------------------------------------
    public List<Location> getObjs() throws RuntimeException {
        List<Location> result = new ArrayList<Location>();
        Connection con = null;
        try {
            con = DBManage.getDBConnect();
            ResultSet rs = con.prepareStatement(SQL_GET_ALL).executeQuery();
            while (rs.next()) {

                Integer id = rs.getInt("id");
                if (rs.wasNull()) id = null;
                String title = rs.getString("sTitle");
                Integer idParent = rs.getInt("idParent");
                if (rs.wasNull()) idParent = null;

                result.add(
                        new Location(
                                 id
                                ,""
                                ,title
                                ,idParent
                                ,null
                                ,""
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeSilently(con);
        }
        return result;
    }
    //----------------------------------------------------------------------------------
    public Location insert(Location location) {
        Location result = null;
        int idvReturnedId = 0;
        Connection con = null;
        try {
            con = DBManage.getDBConnect();
            PreparedStatement ps = con.prepareStatement(SQL_INSERT_ONE, Statement.RETURN_GENERATED_KEYS);
            if (location.idParent == null) ps.setNull(1, Types.INTEGER); else ps.setInt(1, location.idParent);
            if (Core.isNull(location.title)) ps.setNull(2, Types.VARCHAR); else ps.setString(2, location.title);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idvReturnedId = rs.getInt(1);
            }
            result = getObj(idvReturnedId, con);
            rebuildTree(result, con);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeSilently(con);
        }
        return result;
    };
    //----------------------------------------------------------------------------------
    public void delete(List<Location> lObj) {
        delete(lObj, "pv_location");
    }
    //----------------------------------------------------------------------------------
    public void rebuildTree(Location location, Connection con) {
        try {
            List<LocationTree> lTree = new ArrayList<>();
            JDBCLocationTreeRepository jdbcLocationTreeRepository = new JDBCLocationTreeRepository();
            if (location.idParent != null) {
                lTree = jdbcLocationTreeRepository.getObjByLocationId(location.idParent, con);
            }
            List<LocationTree> lTreeForInsert = new ArrayList<>();
            if (lTree.size() > 0) {
                for (LocationTree object: lTree) {
                    lTreeForInsert.add(new LocationTree(
                             object.idParent
                            ,location.id
                            ,object.depth + 1
                            ,object.path + location.title + "/"
                    ));
                }
                lTreeForInsert.add(new LocationTree(
                         location.id
                        ,location.id
                        ,lTreeForInsert.get(0).depth
                        ,lTreeForInsert.get(0).path
                ));
            } else {
                lTreeForInsert.add(new LocationTree(
                         location.id
                        ,location.id
                        ,0
                        ,"/" + location.title + "/"
                ));
            }
            jdbcLocationTreeRepository.insert(lTreeForInsert, con);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {

        }
    }
}
