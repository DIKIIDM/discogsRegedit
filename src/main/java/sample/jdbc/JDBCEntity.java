package sample.jdbc;

import sample.Core;
import sample.model.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCEntity {
    private static String SQL_DELETE_ONE = "DELETE FROM table WHERE id = ?";
    private static String SQL_UPDATE_ONE = "     " +
            "UPDATE table                        " +
            "   SET attr = COALESCE(? , null)    " +
            " WHERE id = ?                       ";
    //----------------------------------------------------------------------------------
    protected void closeSilently(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ignore) {
        }
    }
    //----------------------------------------------------------------------------------
    public void updateStringValue(Integer idp, String spTable, String spAttr,  String spValue) {
        Connection con = null;
        try {
            con = DBManage.getDBConnect();
            updateStringValue(idp, spTable, spAttr, spValue, con);
        } finally {
            closeSilently(con);
        }
    };
    //----------------------------------------------------------------------------------
    public static void updateStringValue(Integer idp, String spTable, String spAttr,  String spValue, Connection con) {
        try {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_ONE.replace("table", spTable).replace("attr", spAttr));
            if (Core.isNull(spValue)) ps.setNull(1, 1); else ps.setString(1, spValue);
            ps.setInt(2, idp);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    //-------------------------------------------------------------------------
    public void delete(List<? extends Entity> lpEntity, String table) {
        if (lpEntity.size() > 0) {
            try {
                Connection con = DBManage.getDBConnect();
                con.setAutoCommit(false);
                delete(lpEntity, table, con);
                con.commit();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //-------------------------------------------------------------------------
    public void delete(List<? extends Entity> lpEntity, String table, Connection con) {
        if (lpEntity.size() > 0) {
            try {
                PreparedStatement ps = con.prepareStatement(SQL_DELETE_ONE.replace("table", table));
                for (Entity entity : lpEntity) {
                    if (entity.id != null) {
                        ps.setInt(1, entity.id);
                        ps.addBatch();
                    }
                }
                int[] updateCounts = ps.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }




}
