package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LisaysController {

    @FXML
    private AnchorPane Anchorpane;

    @FXML
    private Text LisääPelaajiaTxt;

    @FXML
    private TextField TextField;

    @FXML
    private Button LisääBtn;

    @FXML
    private TableView<?> TableView;

    @FXML
    private TableColumn<?, ?> TablecolumPelaajat;

    @FXML
    private TableColumn<?, ?> TableColumnLuku;

    @FXML
    private Button AloitaTurnausBtn;

}

