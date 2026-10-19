package ni.edu.uam.demologin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ni.edu.uam.demologin.model.Usuario;
import ni.edu.uam.demologin.service.UsuarioService;

import java.io.File;

public class EditarUsuarioController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtRutaFoto;
    @FXML private ImageView imageFoto;

    private final UsuarioService usuarioService = new UsuarioService();
    private Usuario usuarioActual;

    public void mostrarUsuario(Usuario usuario) {
        usuarioActual = usuario;
        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellido());
        txtUsuario.setText(usuario.getUsuario());
        txtCorreo.setText(usuario.getCorreo());
        txtRutaFoto.setText(usuario.getRutafoto());

        String ruta = usuario.getRutafoto();
        if (ruta != null && !ruta.isBlank() && new File(ruta).exists()) {
            imageFoto.setImage(new Image(new File(ruta).toURI().toString()));
        }
    }

    @FXML
    private void seleccionarFoto() {
        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccionar fotografía");
        selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imágenes", "*.jpg", "*.jpeg", "*.png"
                )
        );

        File archivo = selector.showOpenDialog(
                txtNombre.getScene().getWindow()
        );
        if (archivo != null) {
            txtRutaFoto.setText(archivo.getAbsolutePath());
            imageFoto.setImage(new Image(archivo.toURI().toString()));
        }
    }

    @FXML
    private void guardarCambios() {
        if (usuarioActual == null) {
            UtilController.mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "No se pudo editar",
                    "No se cargó el usuario seleccionado."
            );
            return;
        }

        if (txtNombre.getText().isBlank()
                || txtApellido.getText().isBlank()
                || txtUsuario.getText().isBlank()
                || txtCorreo.getText().isBlank()) {
            UtilController.mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Datos incompletos",
                    "Completa nombre, apellido, usuario y correo."
            );
            return;
        }

        usuarioActual.setNombre(txtNombre.getText().trim());
        usuarioActual.setApellido(txtApellido.getText().trim());
        usuarioActual.setUsuario(txtUsuario.getText().trim());
        usuarioActual.setCorreo(txtCorreo.getText().trim());
        usuarioActual.setRutafoto(txtRutaFoto.getText().trim());

        try {
            usuarioService.update(usuarioActual);
            UtilController.mostrarMensaje(
                    Alert.AlertType.INFORMATION,
                    "Edición completada",
                    "Los datos del usuario se actualizaron."
            );
            cerrarVentana();
        } catch (Exception e) {
            UtilController.mostrarException(e);
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage ventana = (Stage) txtNombre.getScene().getWindow();
        ventana.close();
    }
}
