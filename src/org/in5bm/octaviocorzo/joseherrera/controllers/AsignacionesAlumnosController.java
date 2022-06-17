package org.in5bm.octaviocorzo.joseherrera.controllers;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.octaviocorzo.joseherrera.db.Conexion;
import org.in5bm.octaviocorzo.joseherrera.system.Principal;
import org.in5bm.octaviocorzo.joseherrera.models.Alumnos;
import org.in5bm.octaviocorzo.joseherrera.models.AsignacionesAlumnos;
import org.in5bm.octaviocorzo.joseherrera.models.Cursos;

/**
 *
 * @author Octavio Alejandro Corzo Reyes
 */
public class AsignacionesAlumnosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGE = "org/in5bm/octaviocorzo/joseherrera/resources/images/";

    private ObservableList<Alumnos> listaAlumnos;
    private ObservableList<Cursos> listaCursos;
    private ObservableList<AsignacionesAlumnos> listaAsignaciones;

    //Inyección de objetos
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
    private TableView<AsignacionesAlumnos> tblAsignacionesAlumnos;

    @FXML
    private TableColumn<AsignacionesAlumnos, Integer> colId;

    @FXML
    private TableColumn<AsignacionesAlumnos, String> colCarne;

    @FXML
    private TableColumn<Cursos, Integer> colCursoId;

    @FXML
    private TableColumn<AsignacionesAlumnos, LocalDateTime> colFechaAsignacion;

    @FXML
    private ImageView imgRegresar;

    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<Alumnos> cmbAlumno;

    /*@FXML
    private DatePicker dpkFechaAsignacion;*/
    
    @FXML
    private JFXDatePicker dpkFechaAsignacion;

    @FXML
    private ComboBox<Cursos> cmbCurso;

    @Override
    public void initialize(URL arg0, ResourceBundle rb) {
        cargarDatos();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);

        //dpkFechaAsignacion.setEditable(false);

        txtId.setDisable(true);
        cmbAlumno.setDisable(true);
        cmbCurso.setDisable(true);
        dpkFechaAsignacion.setDisable(true);

    }

    private void habilitarCampos() {
        txtId.setEditable(true);

        dpkFechaAsignacion.setEditable(true);

        txtId.setDisable(false);
        cmbAlumno.setDisable(false);
        cmbCurso.setDisable(false);
        dpkFechaAsignacion.setDisable(false);
    }

    private void limpiarCampos() {
        txtId.clear();

        cmbAlumno.valueProperty().set(null);
        cmbCurso.valueProperty().set(null);
        dpkFechaAsignacion.getEditor().clear();

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

    //read - lista todos los registros.
    public ObservableList getAsignacionesAlumnos() {
        ArrayList<AsignacionesAlumnos> arrayListAsignaciones = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
                asignacion.setId(rs.getInt("id"));
                asignacion.setAlumnoId(rs.getString("alumno_id"));
                asignacion.setCursoId(rs.getInt("curso_id"));
                asignacion.setFechaAsignacion(rs.getTimestamp("fecha_asignacion").toLocalDateTime());

                System.out.println(asignacion);

                arrayListAsignaciones.add(asignacion);
            }

            listaAsignaciones = FXCollections.observableArrayList(arrayListAsignaciones);

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

        return listaAsignaciones;
    }

    private ObservableList getCursos() {
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt("id"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setInstructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));

                System.out.println(curso.toString());

                arrayListCursos.add(curso);
            }

            listaCursos = FXCollections.observableArrayList(arrayListCursos);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return listaCursos;
    }

    public void cargarDatos() {
        tblAsignacionesAlumnos.setItems(getAsignacionesAlumnos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCarne.setCellValueFactory(new PropertyValueFactory<>("alumnoId"));
        colCursoId.setCellValueFactory(new PropertyValueFactory<>("cursoId"));
        colFechaAsignacion.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        cmbAlumno.setItems(getAlumnos());
        cmbCurso.setItems(getCursos());

    }

    public boolean existeElementoSeleccionado() {
        return (tblAsignacionesAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    public boolean validacionDeDatos() {
        return (cmbAlumno.getSelectionModel() == null || cmbCurso.getSelectionModel() == null 
                || dpkFechaAsignacion.getValue() == null);
    }

    private Alumnos buscarAlumnos(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Alumnos alumno = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read_by_id(?)}");

            pstmt.setString(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                alumno = new Alumnos(
                        rs.getString("carne"),
                        rs.getString("nombre1"),
                        rs.getString("nombre2"),
                        rs.getString("nombre3"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2")
                );

                System.out.println(alumno.toString());
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar al Alumno con el id: " + id);
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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
        return alumno;
    }

    private Cursos buscarCurso(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read_by_id(?)}");
            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                curso = new Cursos();
                curso.setId(rs.getInt("id"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setInstructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));

                System.out.println(curso.toString());
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return curso;
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(
                    String.valueOf(
                            ((AsignacionesAlumnos) tblAsignacionesAlumnos
                                    .getSelectionModel().getSelectedItem()).getId()
                    )
            );

            cmbAlumno.getSelectionModel().select(
                    buscarAlumnos(
                            ((AsignacionesAlumnos) tblAsignacionesAlumnos
                                    .getSelectionModel().getSelectedItem()).getAlumnoId()
                    )
            );

            cmbCurso.getSelectionModel().select(
                    buscarCurso(
                            ((AsignacionesAlumnos) tblAsignacionesAlumnos
                                    .getSelectionModel().getSelectedItem()).getCursoId()
                    )
            );

            dpkFechaAsignacion.setValue(((AsignacionesAlumnos) tblAsignacionesAlumnos.getSelectionModel().getSelectedItem()).getFechaAsignacion().toLocalDate());

        }
    }

    private boolean actualizarAsignacion() {
        AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
        asignacion.setId(Integer.parseInt(txtId.getText()));
        asignacion.setAlumnoId(((Alumnos) cmbAlumno.getSelectionModel().getSelectedItem()).getCarne());
        asignacion.setCursoId(((Cursos) cmbCurso.getSelectionModel().getSelectedItem()).getId());
        asignacion.setFechaAsignacion(dpkFechaAsignacion.getValue().atStartOfDay());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_update(?, ?, ?, ?)}");
            pstmt.setInt(1, asignacion.getId());
            pstmt.setString(2, asignacion.getAlumnoId());
            pstmt.setInt(3, asignacion.getCursoId());
            pstmt.setTimestamp(4, Timestamp.valueOf(asignacion.getFechaAsignacion()));
            System.out.println("\n" + pstmt.toString());
            pstmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("A ocurrido un error al momento de intentar actualizar el siguiente registro: "
                    + asignacion.toString());
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
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

    public boolean eliminarAsignacion() {

        AsignacionesAlumnos asignacion = (AsignacionesAlumnos) tblAsignacionesAlumnos
                .getSelectionModel().getSelectedItem();

        System.out.println(asignacion.toString());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_delete(?)}");

            pstmt.setInt(1, asignacion.getId());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaAsignaciones.remove(tblAsignacionesAlumnos
                    .getSelectionModel().getFocusedIndex()
            );

            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + asignacion.toString());
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

    private boolean agregarAsignacion() {
        AsignacionesAlumnos asignacion = new AsignacionesAlumnos();

        asignacion.setAlumnoId(((Alumnos) cmbAlumno.getSelectionModel().getSelectedItem()).getCarne());

        asignacion.setCursoId(((Cursos) cmbCurso.getSelectionModel().getSelectedItem()).getId());

        asignacion.setFechaAsignacion(dpkFechaAsignacion.getValue().atStartOfDay());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_create(?, ?, ?)}");

            pstmt.setString(1, asignacion.getAlumnoId());
            pstmt.setInt(2, asignacion.getCursoId());
            pstmt.setTimestamp(3, Timestamp.valueOf(asignacion.getFechaAsignacion()));

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaAsignaciones.add(asignacion);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar "
                    + "el siguiente registro: " + asignacion.toString());
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
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();

                tblAsignacionesAlumnos.setDisable(true);

                //clave primaria
                txtId.setEditable(false);
                txtId.setDisable(true);

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
                if (validacionDeDatos()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Control Académico");
                    alert.setContentText("Antes de finalizar la inserción de datos rellene los campos obligatorios");
                    Image icon = new Image(PAQUETE_IMAGE + "logo.png");
                    Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                    stageAlert.getIcons().add(icon);
                    alert.showAndWait();
                } else {
                    if (agregarAsignacion()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();
                        tblAsignacionesAlumnos.setDisable(false);
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
                }
                break;
        }
    }

    @FXML
    private void clicEditar() {
        switch (operacion) {
            case NINGUNO: //editar una inserción
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    txtId.setEditable(false);
                    txtId.setDisable(true);

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
                    alert.setTitle("Control Académico");
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

                tblAsignacionesAlumnos.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:

                if (existeElementoSeleccionado()) {
                    if (validacionDeDatos()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Control Académico");
                        alert.setContentText("Antes de finalizar la actualización de datos, rellene los campos");
                        Image icon = new Image(PAQUETE_IMAGE + "logo.png");
                        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                        stageAlert.getIcons().add(icon);
                        alert.showAndWait();
                    } else {

                        if (actualizarAsignacion()) {
                            cargarDatos();
                            limpiarCampos();

                            tblAsignacionesAlumnos.setDisable(false);
                            tblAsignacionesAlumnos.getSelectionModel().clearSelection();

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
                }

                break;

        }
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

                tblAsignacionesAlumnos.getSelectionModel().clearSelection();

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

                        if (eliminarAsignacion()) {

                            listaAsignaciones.remove(tblAsignacionesAlumnos.getSelectionModel().getFocusedIndex());
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
                            tblAsignacionesAlumnos.getSelectionModel().clearSelection();
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

    @FXML
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    @FXML
    void clicReporte(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("AVISO");
        alert.setContentText("Esta opción solo está disponible para la versión Pro.");
        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
        alert.showAndWait();
    }

}
