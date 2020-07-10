package sample.model;

public class LocationTree extends Entity {
    private int idParent;
    private int idChild;
    private int depth;
    private String path;

    public LocationTree(int idParent, int idChild, int depth, String path) {
        this.idParent = idParent;
        this.idChild = idChild;
        this.depth = depth;
        this.path = path;
    }
}
