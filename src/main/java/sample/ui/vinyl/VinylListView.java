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
                 createColumn("title", "Название", false)
                ,createColumn("artist", "Артист", false)
                ,createColumn("label", "Лейбл", false)
                ,createColumn("catno", "Catno", false)
                ,createColumn("year", "Год", false)
                ,createColumn("price", "Цена", false)
        );
    }
    //----------------------------------------------------------------------------------
    @Override
    public java.util.List<? extends Entity> getObj() {
        return ((VinylListController)this.controller).getObjs();
    }
}
