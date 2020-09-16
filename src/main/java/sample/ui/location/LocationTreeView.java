package sample.ui.location;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Main;
import sample.jdbc.JDBCLocalRepository;
import sample.model.Entity;
import sample.model.Location;
import sample.ui.Tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationTreeView extends Tree {
    //----------------------------------------------------------------------------------
    public LocationTreeView(Main mainApp) {
        this.controller = new LocationTreeController(new JDBCLocalRepository());
        this.mainApp = mainApp;
        initialize();
        setTitle("Location");
    }
    //----------------------------------------------------------------------------------
    @Override
    public void initColumns() {
        treeTableView.getColumns().addAll(
                createTableColumn("title", "sTitle", "Title", false)
        );
    }
    //----------------------------------------------------------------------------------
    @Override
    public java.util.List<? extends Entity> getObj() {
        return ((LocationTreeController)this.controller).getObjs();
    }
    //----------------------------------------------------------------------------------
    public void refresh(List<? extends Entity> lpObject) {
        Map<Integer, TreeItem<Entity>> locationById = new HashMap<>();
        treeTableRoot = new TreeItem<>(new Location(-1, "ROOT", "ROOT", null, null, null));
        treeTableRoot.setExpanded(true);
        treeTableView.setRoot(treeTableRoot);
        treeTableView.setShowRoot(false);
        for (int i = 0; i < lpObject.size(); i++) {
            TreeItem<Entity> treeItem = new TreeItem<>(((List<Location>)lpObject).get(i));
            treeItem.setExpanded(true);
            locationById.put(treeItem.getValue().id, treeItem);
        }
        for (Map.Entry<Integer, TreeItem<Entity>> entry : locationById.entrySet()) {
            Integer idParent = ((Location)entry.getValue().getValue()).idParent;
            if (idParent == null) {
                treeTableRoot.getChildren().add(entry.getValue());
            } else {
                locationById.get(idParent).getChildren().add(entry.getValue());
            }
        }
    }
    //----------------------------------------------------------------------------------
    @Override
    public void initRowFactory() {
        treeTableView.setRowFactory(e -> {
            final TreeTableRow<Entity> row = new TreeTableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem addItem = new MenuItem("Add");
            addItem.setOnAction(event -> {
                add();
            });
            MenuItem addChildItem = new MenuItem("Add child");
            addChildItem.setOnAction(event -> {
                addChild();
            });
            MenuItem removeItem = new MenuItem("Delete");
            removeItem.setOnAction(event -> {
                delete();
            });
            rowMenu.getItems().addAll(addItem, addChildItem, removeItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null)
            );
            return row;
        });
    }
    //----------------------------------------------------------------------------------
    @Override
    public Entity insert(Entity object) {
        return ((LocationTreeController)this.controller).insert(object);
    }
    //----------------------------------------------------------------------------------
    @Override
    public void insert(TreeItem<Entity> parent) {
        Location obj = new Location();
        if (parent != null)
            obj.idParent = parent.getValue().id;
        insert(parent, obj);
    }
    //----------------------------------------------------------------------------------
    @Override
    public void deleteObj(java.util.List<? extends Entity> items) {
        ((LocationTreeController)this.controller).delete((List<Location>)items);
    }
    //----------------------------------------------------------------------------------
    @Override
    public void initListContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem miAdd = new MenuItem("Add");
        miAdd.setOnAction(event-> add());

        MenuItem miRefresh = new MenuItem("Refresh");
        miRefresh.setOnAction(event-> refresh());

        contextMenu.getItems().addAll(miAdd, miRefresh);
        treeTableView.setContextMenu(contextMenu);
    }
}
