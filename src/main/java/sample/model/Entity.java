package sample.model;

import java.lang.reflect.Field;

public abstract class Entity {
    public Integer id;

    public String getAttrValue(String sAttr) {
        Object value = new Object();
        try {
            Field field = this.getClass().getField(sAttr);
            field.setAccessible(true);
            value = field.get(this);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        return (value == null? "": value.toString());
    }

    public boolean set(String fieldName, Object fieldValueNew, Object fieldValueOld) {
        if (fieldValueNew.toString().equals(fieldValueOld.toString())) return false;
        Class<?> clazz = this.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(this, fieldValueNew);
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
