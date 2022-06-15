package org.in5bm.octaviocorzo.joseherrera.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.octaviocorzo.joseherrera.db.Conexion;

import org.in5bm.octaviocorzo.joseherrera.models.Salones;
import org.in5bm.octaviocorzo.joseherrera.system.Principal;

/**
 *
 * @author Octavio Alejandro Corzo Reyes Carné: 2021084
 * @author: Jose Pablo Fabian Herrera Campos Carné: 2018183 Grupo 2: Lunes
 * @date 27/04/2022
 * @time 21:06:35
 *
 * Código Técnico: IN5BM
 *
 */
public class SalonesController implements Initializable {

    private Principal escenarioPrincipal;

    private ObservableList<Salones> listaSalones;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    @FXML
    private TextField txtCodigoSalon;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtCapacidadMaxima;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtEdificio;

    @FXML
    private Spinner<Integer> spnNivel;

    @FXML
    private TableView tblSalones;

    @FXML
    private TableColumn colCodigoSalon;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCapacidad;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;

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

    private SpinnerValueFactory<Integer> valueFactoryNivel;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        //getSalones();
        valueFactoryNivel = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0);
        spnNivel.setValueFactory(valueFactoryNivel);
        cargarDatos();
        deshabilitarCampos();
    }

    public void cargarDatos() {
        tblSalones.setItems(getSalones());
        colCodigoSalon.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblSalones.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtCodigoSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            txtCapacidadMaxima.setText(String.valueOf((((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima())));
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            spnNivel.getValueFactory().setValue(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel());
        }
    }

    public boolean eliminarSalon() {
        if (existeElementoSeleccionado()) {
            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            System.out.println(salon.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_delete(?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                System.out.println(pstmt);

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el alumno con el carné" + salon.toString());
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

    //table
    public ObservableList getSalones() {
        ArrayList<Salones> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_read()}");
            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));

                lista.add(salon);
                System.out.println(salon.toString());
            }

            listaSalones = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar consultar la vista de Salones");
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
        return listaSalones;
    }

    private void deshabilitarCampos() {
        txtCodigoSalon.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCapacidadMaxima.setEditable(false);
        txtEdificio.setEditable(false);
        spnNivel.setEditable(false);

        txtCodigoSalon.setDisable(true);
        txtDescripcion.setDisable(true);
        txtCapacidadMaxima.setDisable(true);
        txtEdificio.setDisable(true);
        spnNivel.setDisable(true);

    }

    private void habilitarCampos() {
        txtCodigoSalon.setEditable(false);
        txtDescripcion.setEditable(true);
        txtCapacidadMaxima.setEditable(true);
        txtEdificio.setEditable(true);
        spnNivel.setEditable(true);

        txtCodigoSalon.setDisable(true);
        txtDescripcion.setDisable(false);
        txtCapacidadMaxima.setDisable(false);
        txtEdificio.setDisable(false);
        spnNivel.setDisable(false);
    }

    private void limpiarCampos() {
        txtCodigoSalon.clear();
        txtDescripcion.clear();
        txtCapacidadMaxima.clear();
        txtEdificio.clear();
        spnNivel.getValueFactory().setValue(0);
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();

                tblSalones.setDisable(true);

                txtCodigoSalon.setEditable(true);
                txtCodigoSalon.setDisable(false);

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
                if (agregarSalon()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblSalones.setDisable(false);

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

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

    private boolean agregarSalon() {
        Salones salon = new Salones();

        if (txtCodigoSalon.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese el código de salón");
            alert.show();

        } else if (txtCapacidadMaxima.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese la capacidad máxima del curso.");
            alert.show();
        } else if (txtEdificio.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese el número de edificio.");
            alert.show();
        } else if (spnNivel.getValue() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese el número del nivel.");
            alert.show();
        } else {

            salon.setCodigoSalon(txtCodigoSalon.getText());
            salon.setDescripcion(txtDescripcion.getText());
            salon.setCapacidadMaxima(Integer.parseInt(txtCapacidadMaxima.getText()));
            salon.setEdificio(txtEdificio.getText());
            salon.setNivel((int) spnNivel.getValue());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_salones_create(?, ?, ?, ?, ?)}");

                pstmt.setString(1, salon.getCodigoSalon());
                pstmt.setString(2, salon.getDescripcion());
                pstmt.setInt(3, salon.getCapacidadMaxima());
                pstmt.setString(4, salon.getEdificio());
                pstmt.setInt(5, salon.getNivel());

                System.out.println(pstmt.toString());

                pstmt.execute();
                listaSalones.add(salon);
                return true;
            } catch (SQLException e) {
                System.err.println("\n se produjo un error al intentar "
                        + "insertar el siguiente registro: " + salon.toString());
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

                tblSalones.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;

            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarSalon()) {
                        cargarDatos();
                        limpiarCampos();

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

    private boolean actualizarSalon() {
        Salones salon = new Salones();
        salon.setCodigoSalon(txtCodigoSalon.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setCapacidadMaxima(Integer.parseInt(txtCapacidadMaxima.getText()));
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel((int) spnNivel.getValue());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_update(?, ?, ?, ?, ?)}");

            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

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

                tblSalones.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    Alert alertNew = new Alert(Alert.AlertType.CONFIRMATION);
                    alertNew.setHeaderText(null);
                    alertNew.setTitle("KINAL \"CONTROL ACÁDEMICO\"");
                    Stage stage = (Stage) alertNew.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
                    alertNew.setContentText("¿Desea eliminar el registro?");
                    Optional<ButtonType> result = alertNew.showAndWait();

                    if (result.get().equals(ButtonType.OK)) {

                        if (eliminarSalon()) {

                            listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
                            System.out.println("\n");
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
                            tblSalones.getSelectionModel().clearSelection();
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
