package org.in5bm.octaviocorzo.joseherrera.controllers;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.in5bm.octaviocorzo.joseherrera.system.Principal;
import org.in5bm.octaviocorzo.joseherrera.db.Conexion;
import org.in5bm.octaviocorzo.joseherrera.models.Instructores;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author octav
 */
public class InstructoresController implements Initializable {

    @FXML
    private ImageView clicRegresar;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private ObservableList<Instructores> listaInstructores;

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGE = "org/in5bm/octaviocorzo/joseherrera/resources/images/";

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgReporte;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblInstructores;

    @FXML
    private TableColumn colNombre1;

    @FXML
    private TableColumn colNombre2;

    @FXML
    private TableColumn colNombre3;

    @FXML
    private TableColumn colApellido1;

    @FXML
    private TableColumn colApellido2;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colDireccion;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colFechaNacimiento;

    @FXML
    private ImageView imgRegresar;

    @FXML
    private TextField txtNombre1;

    @FXML
    private TextField txtNombre2;

    @FXML
    private TextField txtNombre3;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtId;

    /*@FXML
    private DatePicker dpkFechaNacimiento;*/
    
    @FXML
    private JFXDatePicker dpkFechaNacimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
    }

    public void cargarDatos() {
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<Instructores, Integer>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Instructores, String>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Instructores, String>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Instructores, String>("telefono"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<Instructores, Date>("fechaNacimiento"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblInstructores.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf((((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getId())));
            txtNombre1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());
            txtDireccion.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());
            txtEmail.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
            dpkFechaNacimiento.setValue(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getFechaNacimiento());
        }
    }

    public ObservableList getInstructores() {

        ArrayList<Instructores> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_read()}");
            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaNacimiento(rs.getDate(10).toLocalDate());

                lista.add(instructor);
                System.out.println(instructor.toString());
            }

            listaInstructores = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la vista de Instructores");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLState:" + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaInstructores;
    }

    private void deshabilitarCampos() {
        //txtId.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);
        //dpkFechaNacimiento.setEditable(false);

        //txtId.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtDireccion.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefono.setDisable(true);
        dpkFechaNacimiento.setDisable(true);

    }

    private void habilitarCampos() {
        
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);
        dpkFechaNacimiento.setEditable(true);

        
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtDireccion.setDisable(false);
        txtEmail.setDisable(false);
        txtTelefono.setDisable(false);
        dpkFechaNacimiento.setDisable(false);

    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
        dpkFechaNacimiento.setValue(null);
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();

                tblInstructores.setDisable(true);

                //txtCarne.setEditable(true);
                //txtCarne.setDisable(false);
                limpiarCampos();

                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "disquete.png"));

                btnEditar.setText("Cancelar");
                imgEditar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);
                imgEliminar.setVisible(false);
                btnReporte.setDisable(true);
                btnReporte.setVisible(false);
                imgReporte.setVisible(false);

                operacion = Operacion.GUARDAR;

                break;
            case GUARDAR:

                if (agregarInstructor()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblInstructores.setDisable(false);
                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar-archivo.png"));

                    btnEditar.setText("Editar");
                    imgEditar.setImage(new Image(PAQUETE_IMAGE + "usuario.png"));

                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));

                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);
                    imgEliminar.setVisible(true);
                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);
                    imgReporte.setVisible(true);

                    operacion = Operacion.NINGUNO;
                }
                break;

        }
    }

    public boolean agregarInstructor() {
        Instructores instructor = new Instructores();

        if (txtNombre1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese el nombre del instructor");
            alert.show();
        } else if (txtApellido1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese el apellido del instructor");
            alert.show();
        } else if (dpkFechaNacimiento.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese la fecha de nacimiento del instructor");
            alert.show();
        } else {

            instructor.setNombre1(txtNombre1.getText());
            instructor.setNombre2(txtNombre2.getText());
            instructor.setNombre3(txtNombre3.getText());
            instructor.setApellido1(txtApellido1.getText());
            instructor.setApellido2(txtApellido2.getText());
            instructor.setDireccion(txtDireccion.getText());
            instructor.setEmail(txtEmail.getText());
            instructor.setTelefono(txtTelefono.getText());
            instructor.setFechaNacimiento(dpkFechaNacimiento.getValue());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_instructores_create(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

                pstmt.setString(1, instructor.getNombre1());
                pstmt.setString(2, instructor.getNombre2());
                pstmt.setString(3, instructor.getNombre3());
                pstmt.setString(4, instructor.getApellido1());
                pstmt.setString(5, instructor.getApellido2());
                pstmt.setString(6, instructor.getDireccion());
                pstmt.setString(7, instructor.getEmail());
                pstmt.setString(8, instructor.getTelefono());
                pstmt.setObject(9, instructor.getFechaNacimiento());

                System.out.println(pstmt.toString());

                pstmt.execute();
                listaInstructores.add(instructor);
                return true;

            } catch (SQLException e) {
                System.err.println("\n se produjo un error al intentar "
                        + "insertar el siguiente registro: " + instructor.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;

    }

    @FXML
    private void clicEditar() {
        switch (operacion) {
            case NINGUNO: //editar una inserción
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);
                    imgNuevo.setVisible(false);

                    btnEditar.setText("Guardar");
                    imgEditar.setImage(new Image(PAQUETE_IMAGE + "disquete.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);
                    imgReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico");
                    alert.setHeaderText(null);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
                    alert.setContentText("Antes de continuar, seleccione un registro.");
                    alert.show();
                }
                break;
            case GUARDAR: //Cancela la inserción
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar-archivo.png"));

                btnEditar.setText("Editar");
                imgEditar.setImage(new Image(PAQUETE_IMAGE + "usuario.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);
                imgEliminar.setVisible(true);
                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
                imgReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblInstructores.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarInstructor()) {
                        cargarDatos();
                        limpiarCampos();

                        tblInstructores.setDisable(false);
                        tblInstructores.getSelectionModel().clearSelection();

                        btnNuevo.setText("Nuevo");
                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);
                        imgNuevo.setVisible(true);
                        imgNuevo.setImage(new Image(PAQUETE_IMAGE + "agregar-archivo.png"));

                        btnEditar.setText("Editar");
                        imgEditar.setImage(new Image(PAQUETE_IMAGE + "usuario.png"));

                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));

                        btnEliminar.setDisable(false);
                        btnEliminar.setVisible(true);
                        imgEliminar.setVisible(true);
                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);
                        imgReporte.setVisible(true);

                        deshabilitarCampos();

                        operacion = Operacion.NINGUNO;
                    }
                }

                break;

        }
    }

    private boolean actualizarInstructor() {
        Instructores instructor = new Instructores();
        instructor.setId(Integer.parseInt(txtId.getText()));
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDireccion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        instructor.setFechaNacimiento(dpkFechaNacimiento.getValue());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setInt(1, instructor.getId());
            pstmt.setString(2, instructor.getNombre1());
            pstmt.setString(3, instructor.getNombre2());
            pstmt.setString(4, instructor.getNombre3());
            pstmt.setString(5, instructor.getApellido1());
            pstmt.setString(6, instructor.getApellido2());
            pstmt.setString(7, instructor.getDireccion());
            pstmt.setString(8, instructor.getEmail());
            pstmt.setString(9, instructor.getTelefono());
            pstmt.setObject(10, instructor.getFechaNacimiento());

            System.out.println(pstmt.toString());

            pstmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println("\n Se produjo un error al intentar actualizar el instructor");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //Cancelar una modificación.
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);
                imgNuevo.setVisible(true);

                btnEditar.setText("Editar");
                imgEditar.setImage(new Image(PAQUETE_IMAGE + "usuario.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
                imgReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblInstructores.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    Alert alertNew = new Alert(Alert.AlertType.CONFIRMATION);
                    alertNew.setHeaderText(null);
                    alertNew.setTitle("KINAL \"CONTROL ACÁDEMICO\"");
                    alertNew.setContentText(null);
                    alertNew.setContentText("¿Desea eliminar el registro?");

                    Stage stage = (Stage) alertNew.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));

                    Optional<ButtonType> result = alertNew.showAndWait();

                    if (result.get().equals(ButtonType.OK)) {

                        if (eliminarInstructor()) {

                            listaInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
                            System.out.println("\n");
                            limpiarCampos();
                            cargarDatos();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("KINAL \"CONTROL ACÁDEMICO\"");
                            alert.setContentText("Registro eliminado exitosamente");
                            Image icon = new Image(PAQUETE_IMAGE + "icono.png");
                            Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                            stageAlert.getIcons().add(icon);
                            alert.show();
                        } else if (result.get().equals(ButtonType.CANCEL)) {
                            System.out.println("\nCancelando Operacion");
                            tblInstructores.getSelectionModel().clearSelection();
                            limpiarCampos();
                        }
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("KINAL \"CONTROL ACÁDEMICO\"");
                    alert.setContentText("Antes de seguir selecciona un registro");
                    Image icon = new Image(PAQUETE_IMAGE + "icono.png");
                    Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                    stageAlert.getIcons().add(icon);
                    alert.show();
                }
                break;
        }
    }

    public boolean eliminarInstructor() {
        if (existeElementoSeleccionado()) {
            Instructores instructor = (Instructores) tblInstructores.getSelectionModel().getSelectedItem();
            System.out.println(instructor.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_delete(?)}");
                pstmt.setInt(1, instructor.getId());
                System.out.println(pstmt.toString());

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar el instructor" + instructor.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @FXML
    private void clicReporte(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("AVISO");
        alert.setContentText("Esta opción solo está disponible para la versión Pro.");
        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image("org/in5bm/octaviocorzo/joseherrera/resources/images/icono.png"));
        alert.showAndWait();
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
