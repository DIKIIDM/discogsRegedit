package sample.ui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import sample.Core;
import sample.model.Entity;
import sample.model.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tree extends List {
    public TreeTableView<Entity> treeTableView;
    public TreeItem<Entity> treeTableRoot;
    //----------------------------------------------------------------------------------
    @Override
    public void initList() {
        treeTableView = new TreeTableView<>();
        treeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        treeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                  //  setItem(observable, oldValue, newValue);
                }
        );
        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeTableView.setEditable(true);
        treeTableView.setRoot(treeTableRoot);
        ((BorderPane)node).setCenter(treeTableView);
    }
    //----------------------------------------------------------------------------------
    public TreeTableColumn createTableColumn(String sAttr, String sAttrRepo, String sTitle, boolean bReadOnly) {
        TreeTableColumn<Entity, String> column = new TreeTableColumn<>(sTitle);
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>(sAttr));
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        column.setEditable(!bReadOnly);

        column.setOnEditCommit(event -> {
            String sOld = Core.nvl(event.getOldValue(), "");
            String sNew = Core.nvl(event.getNewValue(), "");
            if (!sNew.equals(sOld)) {
                int rowIndx = event.getTreeTablePosition().getRow();
                Entity entity = event.getTreeTableView().getTreeItem(rowIndx).getValue();
                entity.set(sAttr, sAttrRepo, sNew, sOld);
            }
        });

        return column;
    }
    //----------------------------------------------------------------------------------
    public TreeTableColumn createTableColumn(String sAttr, String sAttrRepo, String sTitle, boolean bReadOnly, Double prefWidth) {
        TreeTableColumn<Entity, String> column = createTableColumn(sAttr, sAttrRepo, sTitle, bReadOnly);
        if (prefWidth != null) column.setPrefWidth(prefWidth);
        return column;
    }
    //----------------------------------------------------------------------------------
    public void add() {
        TreeItem<Entity> selected = treeTableView.getSelectionModel().getSelectedItem();
        TreeItem<Entity> parent = null;
        if (selected != null)
            parent = selected.getParent();
        insert(parent);
    }
    //----------------------------------------------------------------------------------
    public void insert(TreeItem<Entity> parent) {

    }
    //----------------------------------------------------------------------------------
    public void addChild() {
        insert(treeTableView.getSelectionModel().getSelectedItem());
    }
    //----------------------------------------------------------------------------------
    public void insert(TreeItem<Entity> parent, Entity object) {
        Task insertTask = new Task<Entity>() {
            @Override
            public Entity call() throws Exception {
                setCursor(Cursor.WAIT);
                return insert(object);
            }
        };
        insertTask.setOnFailed(e -> {
            setCursor(Cursor.DEFAULT);
            Platform.runLater(() -> {
                if (treeTableView != null)
                    treeTableView.setPlaceholder(null);
            });
            insertTask.getException().printStackTrace();
        });
        insertTask.setOnSucceeded(e -> {
            setCursor(Cursor.DEFAULT);
            Platform.runLater(() -> {
                if (treeTableView != null)
                    treeTableView.setPlaceholder(null);
            });
            insertAfter(parent, (Entity)insertTask.getValue());
        });
        exec.execute(insertTask);
    }
    //----------------------------------------------------------------------------------
    public void insertAfter(TreeItem<Entity> parent, Entity object) {
        TreeItem<Entity> treeItem = new TreeItem<>(object);
        if (parent != null)
            parent.getChildren().add(treeItem);
        else
            treeTableRoot.getChildren().add(treeItem);
    }
    //----------------------------------------------------------------------------------
    @Override
    public void refresh() {
        Task getObjTask = new Task<java.util.List<? extends Entity>>() {
            @Override
            public java.util.List<? extends Entity> call() throws Exception {
                setCursor(Cursor.WAIT);
                Platform.runLater(() -> {
                    if (treeTableView != null) {
                        treeTableView.setRoot(null);
                        treeTableView.setPlaceholder(new Label("Loading ..."));
                    }
                });
                return getObj();
            }
        };
        getObjTask.setOnFailed(e -> {
            setCursor(Cursor.DEFAULT);
            Platform.runLater(() -> {
                if (treeTableView != null)
                    treeTableView.setPlaceholder(null);
            });
            getObjTask.getException().printStackTrace();
        });
        getObjTask.setOnSucceeded(e -> {
            setCursor(Cursor.DEFAULT);
            Platform.runLater(() -> {
                if (treeTableView != null)
                    treeTableView.setPlaceholder(null);
            });
            refresh((java.util.List)getObjTask.getValue());
        });
        exec.execute(getObjTask);
    }
    //----------------------------------------------------------------------------------
    @Override
    public void delete() {
        java.util.List<TreeItem<Entity>> selectedTreeItems = getSelectedTreeItems();
        if (selectedTreeItems.size() > 0) {
            if (mainApp.showDialogMsg("Удаление", "Данные будут удалены.", "Вы уверены?")) {
                setCursor(Cursor.WAIT);
                Task<Boolean> deleteObjTask = new Task<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        java.util.List<Entity> objects = new ArrayList<>();
                        for (TreeItem entity : selectedTreeItems) {
                            objects.add((Entity)entity.getValue());
                        }
                        deleteObj(objects);
                        return true;
                    }
                };
                deleteObjTask.setOnFailed(e -> {
                    //deleteObjTask.getException().printStackTrace();
                    setCursor(Cursor.DEFAULT);
                    mainApp.alertError("Error", "Error", deleteObjTask.getException().getMessage().toString());
                });
                deleteObjTask.setOnSucceeded(e -> {
                    if (deleteObjTask.getValue()) {
                        deleteTreeItems(selectedTreeItems);
                        deleteAfter();
                    }
                    setCursor(Cursor.DEFAULT);
                });
                exec.execute(deleteObjTask);
            }
        }
    }
    //----------------------------------------------------------------------------------
    public java.util.List<TreeItem<Entity>> getSelectedTreeItems() {
        return treeTableView.getSelectionModel().getSelectedItems();
    }
    //----------------------------------------------------------------------------------
    public void deleteTreeItems(java.util.List<TreeItem<Entity>> deleted) {
        HashMap<TreeItem<Entity>, java.util.List<TreeItem<Entity>>> treeItemsByParent = new HashMap<>();
        for(TreeItem<Entity> treeItem : deleted) {
            if (treeItemsByParent.containsKey(treeItem.getParent())) {
                treeItemsByParent.get(treeItem.getParent()).add(treeItem);
            } else {
                treeItemsByParent.put(treeItem.getParent(), new ArrayList<>(Arrays.asList(treeItem)));
            }
        }
        for (Map.Entry<TreeItem<Entity>, java.util.List<TreeItem<Entity>>> entry : treeItemsByParent.entrySet()) {
            if (entry.getKey() != null)
                entry.getKey().getChildren().removeAll(entry.getValue());
            else
                treeTableView.setRoot(null);
        }
    }
    //----------------------------------------------------------------------------------
    @Override
    public void insert() {
        add();
    }
}
