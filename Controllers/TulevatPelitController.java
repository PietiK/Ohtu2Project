import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class TulevatPelitController {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text TulevatPelitTxt;

    @FXML
    private TableView<?> TableView;

    @FXML
    private TableColumn<?, ?> TableColumnPelityyppi;

    @FXML
    private TableColumn<?, ?> TableColumnPäivämäärä;

    @FXML
    private Button PelaaBtn;

    @FXML
    private Button MuokkaaBtn;

    @FXML
    private Button PeruPeliBtn;

    @FXML
    private Button TakaisinBtn;

}
