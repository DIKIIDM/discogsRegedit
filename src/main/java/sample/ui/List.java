package sample.ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import sample.model.Entity;
import sample.ui.other.EditCell;
import javafx.concurrent.Task;

import java.util.ArrayList;

public class List extends View {
    public TableView tableView;
    private Label title;
    private Entity item;


    public ObservableList<Entity> items = FXCollections.observableArrayList();
    FilteredList<Entity> itemsFiltered = new FilteredList<>(items, p -> true);
    SortedList<Entity> itemsSorted = new SortedList<>(itemsFiltered);
    //----------------------------------------------------------------------------------
    @Override
    public void initPane() {
        node = new BorderPane();
        initTitle();
        initMenu();
        initList();
        initColumns();
        refresh();
    }
    //----------------------------------------------------------------------------------
    public void initTitle() {
        BorderPane vHead = new BorderPane();
        AnchorPane vTitle = new AnchorPane();
        vTitle.getStyleClass().add("list_title");
        title = new Label("");
        AnchorPane.setTopAnchor(title, 0.0);
        AnchorPane.setRightAnchor(title, 0.0);
        AnchorPane.setLeftAnchor(title, 0.0);
        AnchorPane.setBottomAnchor(title, 0.0);
        vTitle.getChildren().add(title);
        vHead.setTop(vTitle);
        ((BorderPane)node).setTop(vHead);
    }
    //----------------------------------------------------------------------------------
    public void initMenu() {
        AnchorPane vMenu = new AnchorPane();
        vMenu.getStyleClass().add("list_menu");
        VBox vMenuContainer = new VBox();
        vMenuContainer.getStyleClass().add("list_menu_container");
        VBox.setVgrow(vMenuContainer, Priority.ALWAYS);
        AnchorPane.setTopAnchor(vMenuContainer, 0.0);
        AnchorPane.setRightAnchor(vMenuContainer, 0.0);
        AnchorPane.setLeftAnchor(vMenuContainer, 0.0);
        AnchorPane.setBottomAnchor(vMenuContainer, 0.0);
        vMenu.getChildren().add(vMenuContainer);
        ((BorderPane)node).setLeft(vMenu);

        Button btInsert = new Button();
        btInsert.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/picture/add.png"))));
        btInsert.getStyleClass().add("list_menu_button");
        btInsert.setOnAction(e-> {
            insert();
        });
        Button btDelete = new Button();
        btDelete.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/picture/delete.png"))));
        btDelete.getStyleClass().add("list_menu_button");
        btDelete.setOnAction(e-> {
            delete();
        });
        Button btRefresh = new Button();
        btRefresh.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/picture/refresh.png"))));
        btRefresh.getStyleClass().add("list_menu_button");
        btRefresh.setOnAction(e-> {
            refresh();
        });

        vMenuContainer.getChildren().addAll(btInsert, btDelete, btRefresh);
    }
    //----------------------------------------------------------------------------------
    public void insert() {

    }
    //----------------------------------------------------------------------------------
    public void initList() {
        tableView = new TableView<>();
        tableView.setItems(items);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    setItem(observable, oldValue, newValue);
                }
        );
        ((BorderPane)node).setCenter(tableView);
    }
    //----------------------------------------------------------------------------------
    public void setItem(ObservableValue observable, Object OldValue, Object NewValue) {
        setItem((Entity)NewValue);
        setItemAfter();
    }
    //----------------------------------------------------------------------------------
    public void setItem(Entity item) {
        this.item = item;
    }
    //----------------------------------------------------------------------------------
    public void setItemAfter() {

    }
    //----------------------------------------------------------------------------------
    public void initColumns() {

    }
    //----------------------------------------------------------------------------------
    public TableColumn createColumn(String sAttr, String sTitle, boolean bReadOnly) {
        TableColumn<Entity, String> tColumn = new TableColumn<>(sTitle);
        tColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tColumn.setCellFactory(column -> EditCell.createStringEditCell(bReadOnly));
        tColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAttrValue(sAttr)));
        tColumn.setOnEditCommit(t -> {
            if (t.getTablePosition() != null) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).set(sAttr, t.getNewValue(), t.getOldValue());
            }
        });

        return tColumn;
    }
    //----------------------------------------------------------------------------------
    public TableColumn createColumn(String sAttr, String sTitle, boolean bReadOnly, Double prefWidth) {
        TableColumn<Entity, String> tColumn = createColumn(sAttr, sTitle, bReadOnly);
        tColumn.setPrefWidth(prefWidth);
        return tColumn;
    }
    //----------------------------------------------------------------------------------
    public void refresh() {
        Task getObjTask = new Task<java.util.List<? extends Entity>>() {
            @Override
            public java.util.List<? extends Entity> call() throws Exception {
                items.clear();
                setCursor(Cursor.WAIT);
                Platform.runLater(() -> {
                    if (tableView != null)
                        tableView.setPlaceholder(new Label("Loading ..."));
                });
                return getObj();
            }
        };
        getObjTask.setOnFailed(e -> {
            setCursor(Cursor.DEFAULT);
            Platform.runLater(() -> {
                if (tableView != null)
                    tableView.setPlaceholder(null);
            });
            getObjTask.getException().printStackTrace();
        });
        getObjTask.setOnSucceeded(e -> {
            setCursor(Cursor.DEFAULT);
            Platform.runLater(() -> {
                if (tableView != null)
                    tableView.setPlaceholder(null);
            });
            refresh((java.util.List)getObjTask.getValue());
        });
        exec.execute(getObjTask);
    }
    //----------------------------------------------------------------------------------
    public void refresh(java.util.List<? extends Entity> lpObject) {
        items.clear();
        items.addAll(lpObject);

        refreshAfter();
    }
    //----------------------------------------------------------------------------------
    public void refreshAfter() {

    }
    //----------------------------------------------------------------------------------
    public java.util.List<? extends Entity> getObj() {
        return new ArrayList<>();
    }
    //----------------------------------------------------------------------------------
    public void deleteObj(java.util.List<? extends Entity> items) {

    }
    //----------------------------------------------------------------------------------
    public java.util.List<? extends Entity> getSelectedItems() {
        return tableView.getSelectionModel().getSelectedItems();
    }
    //----------------------------------------------------------------------------------
    public void delete() {
        java.util.List<? extends Entity> selectedItems = getSelectedItems();
        if (selectedItems.size() > 0) {
            if (mainApp.showDialogMsg("Удаление", "Данные будут удалены.", "Вы уверены?")) {
                setCursor(Cursor.WAIT);
                Task<Boolean> deleteObjTask = new Task<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        deleteObj(selectedItems);
                        return true;
                    }
                };
                deleteObjTask.setOnFailed(e -> {
                    deleteObjTask.getException().printStackTrace();
                    setCursor(Cursor.DEFAULT);
                });
                deleteObjTask.setOnSucceeded(e -> {
                    if (deleteObjTask.getValue()) {
                        delete(selectedItems);
                        deleteAfter();
                    }
                    setCursor(Cursor.DEFAULT);
                });
                exec.execute(deleteObjTask);
            }
        }
    }
    //----------------------------------------------------------------------------------
    public void delete(java.util.List<? extends Entity> deleted) {
        this.items.removeAll(deleted);
    }
    //----------------------------------------------------------------------------------
    public void deleteAfter() {

    }
    //----------------------------------------------------------------------------------
    public void setTitle(String value) {
        this.title.setText(value);
    }
}
