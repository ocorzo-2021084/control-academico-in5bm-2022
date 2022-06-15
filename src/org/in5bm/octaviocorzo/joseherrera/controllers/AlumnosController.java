package org.in5bm.octaviocorzo.joseherrera.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.octaviocorzo.joseherrera.system.Principal;
import org.in5bm.octaviocorzo.joseherrera.models.Alumnos;
import org.in5bm.octaviocorzo.joseherrera.db.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 *
 * @author Octavio Alejandro Corzo Reyes Carné: 2021084
 * @author: Jose Pablo Fabian Herrera Campos Carné: 2018183 Grupo 2: Lunes
 * @date 27/04/2022
 * @time 20:32:21
 *
 * Código Técnico: IN5BM
 *
 */
public class AlumnosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private ObservableList<Alumnos> listaAlumnos;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtCarne;
    @FXML
    private TextField txtNombre1;
    @FXML
    private TextField txtNombre2;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtApellido2;
    @FXML
    private TextField txtNombre3;
    @FXML
    private TableView tblAlumnos;
    @FXML
    private TableColumn colCarne;
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
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgRegresar;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;
    private final String PAQUETE_IMAGE = "org/in5bm/octaviocorzo/joseherrera/resources/images/";

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        //getAlumnos();
        cargarDatos();
    }

    public void cargarDatos() {
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }
    }

    public boolean eliminarAlumno() {
        if (existeElementoSeleccionado()) {
            Alumnos alumno = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(alumno.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_delete(?)}");
                pstmt.setString(1, alumno.getCarne());
                System.out.println(pstmt.toString());

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el alumno con el carné" + alumno.toString());
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

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public ObservableList getAlumnos() {

        ArrayList<Alumnos> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read()}");
            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                lista.add(alumno);
                System.out.println(alumno.toString());
            }

            listaAlumnos = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la vista de alumnos");
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
        return listaAlumnos;
    }

    private void deshabilitarCampos() {
        txtCarne.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);

    }

    private void habilitarCampos() {
        txtCarne.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    private void limpiarCampos() {
        txtCarne.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();

                tblAlumnos.setDisable(true);

                txtCarne.setEditable(true);
                txtCarne.setDisable(false);

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
                if (agregarAlumno()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblAlumnos.setDisable(false);
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

    private boolean agregarAlumno() {
        Alumnos alumno = new Alumnos();

        if (txtCarne.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese el carné");
            alert.show();
        } else if (txtNombre1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese el primer nombre");
            alert.show();
        } else if (txtApellido1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese el primer apellido");
            alert.show();
        } else {

            alumno.setCarne(txtCarne.getText());
            alumno.setNombre1(txtNombre1.getText());
            alumno.setNombre2(txtNombre2.getText());
            alumno.setNombre3(txtNombre3.getText());
            alumno.setApellido1(txtApellido1.getText());
            alumno.setApellido2(txtApellido2.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_alumnos_create(?, ?, ?, ?, ?, ?)}");

                pstmt.setString(1, alumno.getCarne());
                pstmt.setString(2, alumno.getNombre1());
                pstmt.setString(3, alumno.getNombre2());
                pstmt.setString(4, alumno.getNombre3());
                pstmt.setString(5, alumno.getApellido1());
                pstmt.setString(6, alumno.getApellido2());

                System.out.println(pstmt.toString());

                pstmt.execute();
                listaAlumnos.add(alumno);
                return true;
            } catch (SQLException e) {
                System.err.println("\n se produjo un error al intentar "
                        + "insertar el siguiente registro: " + alumno.toString());
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

                tblAlumnos.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:

                if (existeElementoSeleccionado()) {

                    if (actualizarAlumno()) {
                        cargarDatos();
                        limpiarCampos();

                        tblAlumnos.setDisable(false);
                        tblAlumnos.getSelectionModel().clearSelection();

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

    private boolean actualizarAlumno() {
        Alumnos alumno = new Alumnos();
        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_update(?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());

            System.out.println(pstmt.toString());

            pstmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println("\n Se produjo un error al intentar actualizar el alumno");
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
    private void clicReporte() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("AVISO");
        alert.setContentText("Esta opción solo está disponible para la versión Pro.");
        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image("org/in5bm/octaviocorzo/joseherrera/resources/images/icono.png"));
        alert.showAndWait();
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

                tblAlumnos.getSelectionModel().clearSelection();

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

                        if (eliminarAlumno()) {

                            listaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
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
                            tblAlumnos.getSelectionModel().clearSelection();
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

}
