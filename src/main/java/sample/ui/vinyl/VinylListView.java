package sample.ui.vinyl;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.jdbc.JDBCVinylRepository;
import sample.model.Entity;
import sample.model.Vinyl;
import sample.ui.List;

import java.util.ArrayList;

public class VinylListView extends List {
    //----------------------------------------------------------------------------------
    public VinylListView(Main mainApp) {
        this.controller = new VinylListController(new JDBCVinylRepository());
        this.mainApp = mainApp;
        initialize();
        setTitle("Пластинки");
    }
    //----------------------------------------------------------------------------------
    @Override
    public void initColumns() {
        tableView.getColumns().addAll(
                 createColumn("title", "title", "Название", false)
                ,createColumn("artist", "artist", "Артист", false)
                ,createColumn("label", "label", "Лейбл", false)
                ,createColumn("catno", "catno", "Catno", false)
                ,createColumn("year", "year", "Год", false)
                ,createColumn("price", "price", "Цена", false)
        );
    }
    //----------------------------------------------------------------------------------
    @Override
    public java.util.List<? extends Entity> getObj() {
        return ((VinylListController)this.controller).getObjs();
    }
}
