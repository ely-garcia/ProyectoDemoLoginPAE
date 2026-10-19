package ni.edu.uam.demologin.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ni.edu.uam.demologin.model.Usuario;
import ni.edu.uam.demologin.service.UsuarioService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ni.edu.uam.demologin.Main;

import java.io.IOException;

public class UsuariosController {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colApellido;
    @FXML private TableColumn<Usuario, String> colUsuario;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, String> colFoto;

    private final UsuarioService service = new UsuarioService();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(c ->
                new SimpleStringProperty(String.valueOf(c.getValue().getId())));
        colNombre.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNombre()));
        colApellido.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getApellido()));
        colUsuario.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getUsuario()));
        colCorreo.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCorreo()));
        colFoto.setCellValueFactory(c ->
                new SimpleStringProperty(String.valueOf(c.getValue().getRutafoto())));

        cargarUsuarios();
    }

    @FXML
    private void cargarUsuarios() {
        try {
            tablaUsuarios.setItems(
                    FXCollections.observableArrayList(service.findAll())
            );
        } catch (Exception e) {
            UtilController.mostrarMensaje(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error al cargar usuarios",
                    e.getMessage() == null ? e.toString() : e.getMessage()
            );
        }
    }

    @FXML
    private void eliminarUsuario() {
        Usuario seleccionado =
                tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            UtilController.mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Selecciona un usuario",
                    "Primero selecciona el usuario que deseas eliminar."
            );
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar usuario");
        confirmacion.setContentText(
                "¿Deseas eliminar al usuario "
                        + seleccionado.getUsuario() + "?"
        );

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL)
                != ButtonType.OK) {
            return;
        }

        try {
            service.deleteById(seleccionado.getId());
            cargarUsuarios();
            UtilController.mostrarMensaje(
                    Alert.AlertType.INFORMATION,
                    "Usuario eliminado",
                    "El usuario se eliminó correctamente."
            );
        } catch (Exception e) {
            UtilController.mostrarException(e);
        }
    }

    @FXML
    private void abrirVentanaEdicion() {
        Usuario seleccionado =
                tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            UtilController.mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Selecciona un usuario",
                    "Primero selecciona una fila de la tabla."
            );
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource(
                            "/ni/edu/uam/demologin/editar-usuario.fxml"
                    )
            );

            Parent root = loader.load(); // Aquí se declara root

            EditarUsuarioController controller = loader.getController();
            controller.mostrarUsuario(seleccionado);

            Stage ventana = new Stage();
            ventana.setTitle("Editar usuario");
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setResizable(false);
            ventana.setScene(new Scene(root)); // Se usa aquí, dentro del mismo método
            ventana.showAndWait();
            cargarUsuarios();

        } catch (Exception e) {
            e.printStackTrace(); // Agrega esto
            UtilController.mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "Error",
                    e.getMessage() == null ? e.toString() : e.getMessage()
            );
        }
    }

    
    @FXML
    private void agregarUsuario() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("registro.fxml"));
        Parent root = loader.load();

        RegistroController controller = loader.getController();
        controller.setAbiertoList(true);

        Stage ventana = new Stage();
        ventana.setTitle("Agregar usuario");
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setResizable(false);
        ventana.setScene(new Scene(root));
        ventana.showAndWait();
        cargarUsuarios();
    }
}
