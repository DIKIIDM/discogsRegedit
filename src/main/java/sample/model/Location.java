package sample.model;

import sample.jdbc.JDBCEntity;

import java.lang.reflect.Field;

public class Location extends Entity {
    public String code;
    public String title;
    public Integer idParent;
    public Integer idLocationType;
    public String icon;
    //----------------------------------------------------------------------------------
    public Location() {

    }
    //----------------------------------------------------------------------------------
    public Location(Integer id, String code, String title, Integer idParent,
                    Integer idLocationType, String icon) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.idParent = idParent;
        this.idLocationType = idLocationType;
        this.icon = icon;
    }
    //----------------------------------------------------------------------------------
    public String getTitle() {
        return this.title;
    }
    //----------------------------------------------------------------------------------
    public String getCode() {
        return this.code;
    }
    //----------------------------------------------------------------------------------
    public Integer getIdParent() {
        return this.idParent;
    }
    //----------------------------------------------------------------------------------
    public Integer getId() {
        return this.id;
    }
    //----------------------------------------------------------------------------------
    @Override
    public boolean set(String fieldName, String fieldNameRepo, Object fieldValueNew, Object fieldValueOld) {
        if (fieldValueNew.toString().equals(fieldValueOld.toString())) return false;
        Class<?> clazz = this.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                String sClass = field.getType().getCanonicalName();
                if (sClass.equals("java.lang.String")) {
                    if (fieldValueNew.toString().equals(fieldValueOld.toString())) return false;
                    new JDBCEntity().updateStringValue(this.id, "pv_location", fieldNameRepo, fieldValueNew.toString());
                    field.setAccessible(true);
                    field.set(this, fieldValueNew);
                }
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
}

