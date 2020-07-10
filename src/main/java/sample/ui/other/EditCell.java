package sample.ui.other;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class EditCell<S, T> extends TableCell<S, T> {

    private final TextField textField = new TextField();
    public KeyCombination ctrlPlusC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);

    private final StringConverter<T> converter ;
    public Boolean bStartEdit = false;

    public EditCell(StringConverter<T> converter, boolean bReadOnly) {
        this.converter = converter ;

        itemProperty().addListener((obx, oldItem, newItem) -> {
            if (newItem == null) {
                setText(null);
            } else {
                setText(converter.toString(newItem));
            }
        });
        setGraphic(textField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);

        textField.setOnAction(evt -> {
            commitEdit(this.converter.fromString(textField.getText()));
        });
        textField.setDisable(bReadOnly);
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                commitEdit(this.converter.fromString(textField.getText()));
            }
        });

        this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    bStartEdit = false;
                }
            }
        });
    }

    public static final StringConverter<String> IDENTITY_CONVERTER = new StringConverter<String>() {
        @Override
        public String toString(String object) {
            return object;
        }

        @Override
        public String fromString(String string) {
            return string;
        }

    };

    public static <S> EditCell<S, String> createStringEditCell(boolean bReadonly) {
        return new EditCell<S, String>(IDENTITY_CONVERTER, bReadonly);
    }

    @Override
    public void startEdit() {
        bStartEdit = true;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                       if (bStartEdit) {
                           Platform.runLater(() -> {
                               startEditDm();

                           });
                       }
                    }
                },
                500
        );
    }
    public void startEditDm() {
        super.startEdit();
        textField.setText(converter.toString(getItem()));
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        bStartEdit = false;
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void commitEdit(T item) {
        if (! isEditing() && ! item.equals(getItem())) {
            TableView<S> table = getTableView();
            if (table != null) {
                TableColumn<S, T> column = getTableColumn();
                CellEditEvent<S, T> event = new CellEditEvent<>(table,
                        new TablePosition<S,T>(table, getIndex(), column),
                        TableColumn.editCommitEvent(), item);
                Event.fireEvent(column, event);
            }
            updateItem(item, false);
            if (table != null) {
                table.edit(-1, null);
            }
        }

        super.commitEdit(item);

        setContentDisplay(ContentDisplay.TEXT_ONLY);
        bStartEdit = false;
    }
}