package org.in5bm.octaviocorzo.joseherrera.system;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.in5bm.octaviocorzo.joseherrera.controllers.AlumnosController;
import org.in5bm.octaviocorzo.joseherrera.controllers.MenuPrincipalController;
import org.in5bm.octaviocorzo.joseherrera.controllers.CarrerasTecnicasController;
import org.in5bm.octaviocorzo.joseherrera.controllers.SalonesController;
import org.in5bm.octaviocorzo.joseherrera.controllers.AsignacionesAlumnosController;
import org.in5bm.octaviocorzo.joseherrera.controllers.CursosController;
import org.in5bm.octaviocorzo.joseherrera.controllers.InstructoresController;

/**
 *
 * @author Octavio Alejandro Corzo Reyes Carné: 2021084
 * @author: Jose Pablo Fabian Herrera Campos Carné: 2018183 Grupo 2: Lunes
 * Código Técnico: IN5BM
 */
public class Principal extends Application {

    private Stage escenarioPrincipal;
    private final String PAQUETE_IMAGE = "org/in5bm/octaviocorzo/joseherrera/resources/images/";
    private final String PAQUETE_VIEW = "../views/";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Académico KINAL");
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGE + "icono.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        mostrarEscenaPrincipal();
    }

    public void mostrarEscenaPrincipal() {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 950, 600);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("\nSe produjo en error al intenar mostrar la vista principal");
        }
    }

    public void mostrarEscenaAlumnos() {
        try {
            AlumnosController alumnosController = (AlumnosController) cambiarEscena("AlumnosView.fxml", 950, 600);
            alumnosController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nSe produjo en error al intenar mostrar la vista alumnos");

        }
    }

    public void mostrarEscenaCarrerasTecnicas() {
        try {
            CarrerasTecnicasController carrerastecnicasController = (CarrerasTecnicasController) cambiarEscena("CarrerasTecnicasView.fxml", 950, 600);
            carrerastecnicasController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nSe produjo en error al intenar mostrar la vista Carreras tecnicas");

        }
    }

    public void mostrarEscenaSalones() {
        try {
            SalonesController salonesController = (SalonesController) cambiarEscena("SalonesView.fxml", 950, 600);
            salonesController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nSe produjo en error al intenar mostrar la vista Salones");
            System.out.println("\n" + e.getMessage() + "\n");

        }
    }
    
    public void mostrarEscenaAsignacionAlumnos() {
        try {
            AsignacionesAlumnosController asignacionesController = (AsignacionesAlumnosController) cambiarEscena("AsignacionesAlumnosView.fxml", 950, 600);
            asignacionesController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nSe produjo en error al intenar mostrar la vista Salones");
            System.out.println("\n" + e.getMessage() + "\n");

        }
    }
    
    public void mostrarEscenaInstructores() {
        try {
            InstructoresController instructoresController = (InstructoresController) cambiarEscena("InstructoresView.fxml", 950, 600);
            instructoresController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nSe produjo en error al intenar mostrar la vista Salones");
            System.out.println("\n" + e.getMessage() + "\n");

        }
    }
    
    public void mostrarEscenaCursos(){
        try{
            CursosController cursosController = (CursosController) cambiarEscena("CursosView.fxml", 950, 600);
            cursosController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("\nSe produjo en error al intenar mostrar la cursos");
            System.out.println("\n"+ e.getMessage()+ "\n");

        }
    }
    
    
    /*public Initializable cambiarEscena(String vistaFxml,int ancho, int alto)throws IOException{
        Initializable resultado = null;
        
        
        FXMLLoader cargadorFXML = new FXMLLoader();
        
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VIEW + vistaFxml));
        
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VIEW + vistaFxml);
        
        AnchorPane root = cargadorFXML.load(archivo);
        
        Scene nuevaEscena = new Scene(root, ancho, alto);
        
        this.escenarioPrincipal.setScene(nuevaEscena);
        
        this.escenarioPrincipal.sizeToScene();
        
        //Mostrar escenario
        this.escenarioPrincipal.show();
        
        //Obtener Instancia
        resultado = cargadorFXML.getController();
        
        return resultado;
    }
     */
    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException {
        System.out.println("Cambiando escena" + PAQUETE_VIEW + vistaFxml);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable) cargadorFXML.getController();
    }
}
