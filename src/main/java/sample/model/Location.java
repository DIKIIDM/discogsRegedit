package sample.model;

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
}

