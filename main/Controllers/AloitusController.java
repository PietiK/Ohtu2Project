package main.Controllers;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AloitusController {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text TervetuloaTxt;

    @FXML
    private Button AloitaPeliBtn;

    @FXML
    private ImageView Kuva;

    @FXML
    private MenuButton ValitsePeliMenuBtn;

    @FXML
    private MenuItem MenuitemKaisa;

    @FXML
    private MenuItem MenuItemSnooker;
    
    @FXML
    public void SiirryPelinLuontiin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/UusiTurnaus.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene UusipeliS = new Scene(AloitusNayttoP);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(UusipeliS);
        window.centerOnScreen();
        window.show();
    }

    @FXML
    public void SiirryTuleviinPeleihin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/TulevatTurnaukset.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene TulevatpelitS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(TulevatpelitS);
        window.show();
    }
}