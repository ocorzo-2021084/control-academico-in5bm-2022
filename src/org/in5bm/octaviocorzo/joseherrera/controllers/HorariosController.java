package org.in5bm.octaviocorzo.joseherrera.controllers;

import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import java.sql.Time;
import java.sql.ResultSet;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.octaviocorzo.joseherrera.db.Conexion;
import org.in5bm.octaviocorzo.joseherrera.models.Horarios;
import org.in5bm.octaviocorzo.joseherrera.system.Principal;

/**
 *
 * @author Jose Pablo Fabian Herrera Garcia
 * @date 14/06/2022
 * @time 16:11:51
 */
public class HorariosController implements Initializable {

    private final String PAQUETE_IMAGE = "org/in5bm/octaviocorzo/joseherrera/resources/images/";

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtId;
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
    private TableView<Horarios> tblHorarios;
    @FXML
    private TableColumn<Horarios, Integer> colId;
    @FXML
    private TableColumn<Horarios, Time> colHoraInicio;
    @FXML
    private TableColumn<Horarios, Time> colHoraSalida;
    @FXML
    private TableColumn<Horarios, Boolean> colLunes;
    @FXML
    private TableColumn<Horarios, Boolean> colMartes;
    @FXML
    private TableColumn<Horarios, Boolean> colMiercoles;
    @FXML
    private TableColumn<Horarios, Boolean> colJueves;
    @FXML
    private TableColumn<Horarios, Boolean> colViernes;
    @FXML
    private CheckBox ckbLunes;
    @FXML
    private CheckBox ckbMartes;
    @FXML
    private CheckBox ckbMiercoles;
    @FXML
    private CheckBox ckbJueves;
    @FXML
    private CheckBox ckbViernes;
    @FXML
    private JFXTimePicker tpkHoraInicio;
    @FXML
    private JFXTimePicker tpkHoraSalida;

    private ObservableList<Horarios> listaHorarios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        
    }

    public void cargarDatos() {
        tblHorarios.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horarioInicio"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<>("horarioSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory<>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<>("Martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<>("Miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<>("Jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<>("Viernes"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblHorarios.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getId()));
            tpkHoraSalida.setValue((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioSalida()));
            tpkHoraInicio.setValue((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioInicio()));
            ckbLunes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getLunes());
            ckbMartes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getMartes());
            ckbMiercoles.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getMiercoles());
            ckbJueves.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getJueves());
            ckbViernes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getViernes());
        }
    }

    public boolean eliminarHorarios() {
        if (existeElementoSeleccionado()) {
            Horarios horarios = (Horarios) tblHorarios.getSelectionModel().getSelectedItem();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Control AcadÃƒÂ©mico Kinal");
            confirm.setHeaderText(null);
            confirm.setContentText("Esta apunto de eliminar el registro con los siguientes datos: "
                    + "\n" + horarios.getId()
                    + "\nEsta seguro?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.get().equals(ButtonType.OK)) {

                PreparedStatement pstmt = null;
                try {
                    pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_horarios_delete(?)}");
                    pstmt.setInt(1, horarios.getId());
                    System.out.println(pstmt.toString());
                    pstmt.execute();
                    return true;
                } catch (SQLException e) {
                    System.err.println("\nSe produjo un error al intentar eliminar el alumno siguiente registro: " + horarios.toString());
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
            } else {
                tblHorarios.getSelectionModel().clearSelection();
            }
        }
        return false;
    }

    

    public ObservableList getHorarios() {

        ArrayList<Horarios> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_horarios_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setHorarioInicio(rs.getTime(2).toLocalTime());
                horario.setHorarioSalida(rs.getTime(3).toLocalTime());
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                System.out.println(horario.toString());
                lista.add(horario);

            }
            listaHorarios = FXCollections.observableArrayList(lista);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar conmsultar la lista de horarios");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
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
        return listaHorarios;
    }


    private void habilitarCampos() {
        txtId.setEditable(false);

        txtId.setDisable(true);
        tpkHoraInicio.setDisable(false);
        tpkHoraSalida.setDisable(false);
        ckbLunes.setDisable(false);
        ckbMartes.setDisable(false);
        ckbMiercoles.setDisable(false);
        ckbJueves.setDisable(false);
        ckbViernes.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtId.setDisable(true);
        tpkHoraInicio.setDisable(true);
        tpkHoraSalida.setDisable(true);
        ckbLunes.setDisable(true);
        ckbMartes.setDisable(true);
        ckbMiercoles.setDisable(true);
        ckbJueves.setDisable(true);
        ckbViernes.setDisable(true);
    }

    private void limpiarCampos() {
        txtId.clear();
        tpkHoraInicio.getEditor().clear();
        tpkHoraSalida.getEditor().clear();
        ckbLunes.setSelected(false);
        ckbMartes.setSelected(false);
        ckbMiercoles.setSelected(false);
        ckbJueves.setSelected(false);
        ckbViernes.setSelected(false);
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();

                tblHorarios.setDisable(true);

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

                if (agregarHorario()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblHorarios.setDisable(false);
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

    public boolean agregarHorario() {
        Horarios horario = new Horarios();

        if (tpkHoraInicio == null || tpkHoraSalida == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control Academico");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            alert.setContentText("Antes de continuar, ingrese los horarios de inicio y salida");
            alert.show();

        } else {

            horario.setHorarioInicio(tpkHoraInicio.getValue());
            horario.setHorarioSalida(tpkHoraSalida.getValue());
            horario.setLunes(ckbLunes.isSelected());
            horario.setMartes(ckbMartes.isSelected());
            horario.setMiercoles(ckbMiercoles.isSelected());
            horario.setJueves(ckbJueves.isSelected());
            horario.setViernes(ckbViernes.isSelected());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance()
                        .getConexion().prepareCall("CALL sp_horarios_create(?,?,?,?,?,?,?)");
                pstmt.setObject(1, horario.getHorarioInicio());
                pstmt.setObject(2, horario.getHorarioSalida());
                pstmt.setBoolean(3, horario.getLunes());
                pstmt.setBoolean(4, horario.getMartes());
                pstmt.setBoolean(5, horario.getMiercoles());
                pstmt.setBoolean(6, horario.getJueves());
                pstmt.setBoolean(7, horario.getViernes());

                System.out.println(pstmt.toString());

                pstmt.execute();
                listaHorarios.add(horario);
                return true;

            } catch (SQLException e) {
                System.err.println("\n se produjo un error al intentar "
                        + "insertar el siguiente registro: " + horario.toString());
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
            case NINGUNO: //editar una inserciÃ³n
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
            case GUARDAR: //Cancela la inserciÃ³n
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

                tblHorarios.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarHorario()) {
                        cargarDatos();
                        limpiarCampos();

                        tblHorarios.setDisable(false);
                        tblHorarios.getSelectionModel().clearSelection();

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

    private boolean actualizarHorario() {
        if (!(tpkHoraSalida.getEditor().getText().equals("") || tpkHoraInicio.getEditor().getText().equals(""))) {

            LocalTime horaInicio = tpkHoraInicio.getValue();
            LocalTime horarioSalida = tpkHoraSalida.getValue();
            Horarios horarios = new Horarios();
            horarios.setId(Integer.parseInt(txtId.getText()));
            horarios.setHorarioInicio(tpkHoraInicio.getValue());
            horarios.setHorarioSalida(tpkHoraSalida.getValue());
            horarios.setLunes(ckbLunes.isSelected());
            horarios.setMartes(ckbMartes.isSelected());
            horarios.setMiercoles(ckbMiercoles.isSelected());
            horarios.setJueves(ckbJueves.isSelected());
            horarios.setViernes(ckbViernes.isSelected());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_horarios_update(?, ?, ?, ?, ?, ?, ?, ?)}");
                pstmt.setInt(1, horarios.getId());
                pstmt.setObject(2, horarios.getHorarioInicio());
                pstmt.setObject(3, horarios.getHorarioSalida());
                pstmt.setBoolean(4, horarios.getLunes());
                pstmt.setBoolean(5, horarios.getMartes());
                pstmt.setBoolean(6, horarios.getMiercoles());
                pstmt.setBoolean(7, horarios.getJueves());
                pstmt.setBoolean(8, horarios.getViernes());

                System.out.println(pstmt);

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.out.println("Se produjo un error al intentar actualizar el siguiente registro: " + horarios.toString());
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
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control academico");
            alert.setHeaderText(null);
            alert.setContentText("Antes de continuar rellene todos los campos");
            alert.show();
        }
        return false;
    }

    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //Cancelar una modificaciÃ³n.
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

                tblHorarios.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    Alert alertNew = new Alert(Alert.AlertType.CONFIRMATION);
                    alertNew.setHeaderText(null);
                    alertNew.setTitle("KINAL \"CONTROL ACÃDEMICO\"");
                    alertNew.setContentText(null);
                    alertNew.setContentText("Â¿Desea eliminar el registro?");

                    Stage stage = (Stage) alertNew.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));

                    Optional<ButtonType> result = alertNew.showAndWait();

                    if (result.get().equals(ButtonType.OK)) {

                        if (eliminarHorarios()) {

                            listaHorarios.remove(tblHorarios.getSelectionModel().getFocusedIndex());
                            System.out.println("\n");
                            limpiarCampos();
                            cargarDatos();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("KINAL \"CONTROL ACÃDEMICO\"");
                            alert.setContentText("Registro eliminado exitosamente");
                            Image icon = new Image(PAQUETE_IMAGE + "icono.png");
                            Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                            stageAlert.getIcons().add(icon);
                            alert.show();
                        } else if (result.get().equals(ButtonType.CANCEL)) {
                            System.out.println("\nCancelando Operacion");
                            tblHorarios.getSelectionModel().clearSelection();
                            limpiarCampos();
                        }
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("KINAL \"CONTROL ACÃDEMICO\"");
                    alert.setContentText("Antes de seguir selecciona un registro");
                    Image icon = new Image(PAQUETE_IMAGE + "icono.png");
                    Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                    stageAlert.getIcons().add(icon);
                    alert.show();
                }
                break;
        }
    }

    @FXML
    private void clicReporte() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("AVISO");
        alert.setContentText("Esta opciÃ³n solo estÃ¡ disponible para la versiÃ³n Pro.");
        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image("org/in5bm/octaviocorzo/joseherrera/resources/images/icono.png"));
        alert.showAndWait();
    }

    @FXML
    public void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
