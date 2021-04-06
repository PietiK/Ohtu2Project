package main.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class TulosController {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text KierrosTxt;

    @FXML
    private TableView<?> TableView;

    @FXML
    private TableColumn<?, ?> TableColumn1;

    @FXML
    private TableColumn<?, ?> TableColumnPelaaja1;

    @FXML
    private TableColumn<?, ?> TableColumnP1pisteet;

    @FXML
    private TableColumn<?, ?> TableColumnPelaaja2;

    @FXML
    private TableColumn<?, ?> TableColumnP2pisteet;

    @FXML
    private ScrollBar ScrollBar;

    @FXML
    private Button EdellinenBtn;

    @FXML
    private Button KeskeytäTurnausBtn;

    @FXML
    private Button PäätäTurnausBtn;

    @FXML
    private Button SeuraavaBtn;

}
