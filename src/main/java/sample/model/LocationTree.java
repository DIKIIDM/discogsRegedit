package sample.model;

public class LocationTree extends Entity {
    public int idParent;
    public int idChild;
    public int depth;
    public String path;

    public LocationTree(int idParent, int idChild, int depth, String path) {
        this.idParent = idParent;
        this.idChild = idChild;
        this.depth = depth;
        this.path = path;
    }
}
