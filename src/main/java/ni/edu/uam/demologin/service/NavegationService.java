package ni.edu.uam.demologin.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ni.edu.uam.demologin.Main;
import javafx.stage.Stage;

import java.io.IOException;

public class NavegationService {

    public void cambiarVista(String fxml,Node node) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(fxml)
        );
        Parent root = loader.load();
        Scene scene = node.getScene();
        scene.setRoot(root);
        if (fxml.equals("usuarios.fxml")) {
            Stage stage = (Stage) scene.getWindow();
            stage.setResizable(true);
            stage.setWidth(980);
            stage.setHeight(650);
        }
    }
}
