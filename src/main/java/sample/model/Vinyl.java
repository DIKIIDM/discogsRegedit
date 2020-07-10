package sample.model;

public class Vinyl extends Entity {
    public String title;
    public String artist;
    public String label;
    public String catno;
    public Integer year;
    public Double price;
    public Integer idDiscogs;
    public Integer idArea;
    public String note;
    //----------------------------------------------------------------------------------
    public Vinyl() {}
    //----------------------------------------------------------------------------------
    public Vinyl(Integer id, String title, String artist, String label, String catno, Integer year, Double price) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.label = label;
        this.catno = catno;
        this.year = year;
        this.price = price;
    }
}
