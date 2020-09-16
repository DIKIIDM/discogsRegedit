package sample.jdbc;

import sample.Core;
import sample.model.Location;
import sample.model.LocationTree;
import sample.repo.LocationRepository;
import sample.repo.LocationTreeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCLocationTreeRepository extends JDBCEntity implements LocationTreeRepository {
    private static String SQL_INSERT = "   " +
            "INSERT INTO pv_locationtree   " +
            "   (   idParent               " +
            "      ,idChild                " +
            "      ,nDepth                 " +
            "      ,sPath                  " +
            "   )                          " +
            " VALUES                       " +
            "   (?, ?, ?, ?); ";
    private static String SQL_GET_ByChild = " " +
            "select t.id                      " +
            "      ,t.idParent                " +
            "      ,t.idChild                 " +
            "      ,t.nDepth                  " +
            "      ,t.sPath                   " +
            "  from pv_locationtree t         " +
            " where 1 = 1                     " +
            "   and t.idChild  = ?            ";
    //----------------------------------------------------------------------------------
    public void insert(List<LocationTree> lLocationTree, Connection con) {
        try {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT);
            for(int i = 0; i< lLocationTree.size(); i++) {
                ps.setInt(1, lLocationTree.get(i).idParent);
                ps.setInt(2, lLocationTree.get(i).idChild);
                ps.setInt(3, lLocationTree.get(i).depth);
                ps.setString(4, lLocationTree.get(i).path);
                ps.addBatch();
            }
            int[] updateCounts = ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //----------------------------------------------------------------------------------
    public List<LocationTree> getObjByLocationId(Integer idLocation, Connection con) {
        List<LocationTree> lObject = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SQL_GET_ByChild);
            ps.setInt(1, idLocation);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lObject.add( new LocationTree(
                         rs.getInt("idParent")
                        ,rs.getInt("idChild")
                        ,rs.getInt("nDepth")
                        ,rs.getString("sPath")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lObject;
    }
}
