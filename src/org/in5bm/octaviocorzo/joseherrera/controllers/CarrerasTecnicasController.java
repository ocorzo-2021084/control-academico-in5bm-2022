package org.in5bm.octaviocorzo.joseherrera.controllers;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.octaviocorzo.joseherrera.db.Conexion;
import org.in5bm.octaviocorzo.joseherrera.models.CarrerasTecnicas;
import org.in5bm.octaviocorzo.joseherrera.system.Principal;


public class CarrerasTecnicasController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private ObservableList<CarrerasTecnicas> listaCarrerrasTecnicas;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtCarrera;
    @FXML
    private TextField txtGrado;
    @FXML
    private TextField txtSeccion;
    @FXML
    private TextField txtJornada;
    @FXML
    private TextField txtCodigoTecnico;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private TableView tblCarrerasTecnicas;
    @FXML
    private TableColumn colCarrera;
    @FXML
    private TableColumn colGrado;
    @FXML
    private TableColumn colSeccion;
    @FXML
    private TableColumn colJornada;
    @FXML
    private TableColumn colCodigoTecnico;
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
        //getCarrerasTecnicas();
        cargarDatos();
    }

    public void cargarDatos() {
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, Character>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
    }
    public boolean existeElementoSeleccionado() {
        return (tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null);
    }
    
    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {
            txtCodigoTecnico.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            txtSeccion.setText(String.valueOf(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion()));
            txtJornada.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
        }
    }
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public boolean eliminarCarreraTecnica() {
        if (existeElementoSeleccionado()) {
            CarrerasTecnicas carreratecnica = (CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem();
            System.out.println(carreratecnica.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_delete(?)}");
                pstmt.setString(1, carreratecnica.getCodigoTecnico());
                System.out.println(pstmt.toString());

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar la carrera con el codigo" + carreratecnica.toString());
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
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    private ObservableList<CarrerasTecnicas> ListaCarrerasTecnicas;

    //table
    public ObservableList getCarrerasTecnicas() {
        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CarrerasTecnicas carreratecnica = new CarrerasTecnicas();
                carreratecnica.setCodigoTecnico(rs.getString(1));
                carreratecnica.setCarrera(rs.getString(2));
                carreratecnica.setGrado(rs.getString(3));
                carreratecnica.setSeccion(rs.getString(4).charAt(0));
                carreratecnica.setJornada(rs.getString(5));

                lista.add(carreratecnica);
                System.out.println(carreratecnica.toString());
            }
            ListaCarrerasTecnicas = FXCollections.observableArrayList(lista);
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar consultar la vista de Carreras tecnicas");
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
        return ListaCarrerasTecnicas;
    }

    private void deshabilitarCampos() {
        txtCarrera.setEditable(false);
        txtGrado.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);
        txtCodigoTecnico.setEditable(false);

        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);
        txtCodigoTecnico.setDisable(true);
    }

    private void habilitarCampos() {
        txtCarrera.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);
        txtCodigoTecnico.setEditable(true);

        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
        txtCodigoTecnico.setDisable(false);
    }

    private void limpiarCampos() {
        txtCarrera.clear();
        txtGrado.clear();
        txtSeccion.clear();
        txtJornada.clear();
        txtCodigoTecnico.clear();
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();

                tblCarrerasTecnicas.setDisable(true);

                txtCodigoTecnico.setEditable(true);
                txtCodigoTecnico.setDisable(false);

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
                if (agregarCarreraTecnica()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblCarrerasTecnicas.setDisable(false);
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

    private boolean agregarCarreraTecnica() {
        CarrerasTecnicas carrerasTecnicas = new CarrerasTecnicas();

        if (txtCodigoTecnico.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el codigo de la carrera tecnica! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            conf.show();

        } else if (txtCarrera.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el nombre de la carrera! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            conf.show();

        } else if (txtGrado.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el Grado al que corresponde la carrera! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            conf.show();

        } else if (txtSeccion.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese la seccion del grado! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            conf.show();

        } else if (txtJornada.getText().isEmpty()) {
            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese la Jornada! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
            conf.show();
        } else {
            carrerasTecnicas.setCodigoTecnico(txtCodigoTecnico.getText());
            carrerasTecnicas.setCarrera(txtCarrera.getText());
            carrerasTecnicas.setGrado(txtGrado.getText());
            carrerasTecnicas.setSeccion(txtSeccion.getText().charAt(0));
            carrerasTecnicas.setJornada(txtJornada.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_carreras_tecnicas_create(?, ?, ?, ?, ?)}");
                pstmt.setString(1, carrerasTecnicas.getCodigoTecnico());
                pstmt.setString(2, carrerasTecnicas.getCarrera());
                pstmt.setString(3, carrerasTecnicas.getGrado());
                pstmt.setString(4, String.valueOf(carrerasTecnicas.getSeccion()));
                pstmt.setString(5, carrerasTecnicas.getJornada());

                System.out.println(pstmt.toString());

                pstmt.execute();

                ListaCarrerasTecnicas.add(carrerasTecnicas);

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR"
                        + " INGRESAR EL SIGUIENTE REGISTRO: " + carrerasTecnicas.toString());
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
                    
                    txtCodigoTecnico.setEditable(false);
                    txtCodigoTecnico.setDisable(true);
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

                tblCarrerasTecnicas.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:

                if (existeElementoSeleccionado()) {

                    if (actualizarCarrerasTecnicas()) {
                        cargarDatos();
                        limpiarCampos();

                        tblCarrerasTecnicas.setDisable(false);
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();

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

     public boolean actualizarCarrerasTecnicas() {
        CarrerasTecnicas carrerasTecnicas = new CarrerasTecnicas();

        carrerasTecnicas.setCodigoTecnico(txtCodigoTecnico.getText());
        carrerasTecnicas.setCarrera(txtCarrera.getText());
        carrerasTecnicas.setGrado(txtGrado.getText());
        carrerasTecnicas.setSeccion(txtSeccion.getText().charAt(0));
        carrerasTecnicas.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_update(?, ?, ?, ?, ?)}");

            pstmt.setString(1, carrerasTecnicas.getCodigoTecnico());
            pstmt.setString(2, carrerasTecnicas.getCarrera());
            pstmt.setString(3, carrerasTecnicas.getGrado());
            pstmt.setString(4, String.valueOf(carrerasTecnicas.getSeccion()));
            pstmt.setString(5, carrerasTecnicas.getJornada());

            System.out.println(pstmt.toString());

            pstmt.execute();

            ListaCarrerasTecnicas.add(carrerasTecnicas);

            return true;

        } catch (SQLException e) {
            System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR MODIFICAR EL SIGUIENTE REGISTRO: " + carrerasTecnicas.toString());
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

                tblCarrerasTecnicas.getSelectionModel().clearSelection();

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

                        if (eliminarCarreraTecnica()) {

                            ListaCarrerasTecnicas.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
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
                            tblCarrerasTecnicas.getSelectionModel().clearSelection();
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
