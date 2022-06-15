package org.in5bm.octaviocorzo.joseherrera.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.in5bm.octaviocorzo.joseherrera.system.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author Octavio Alejandro Corzo Reyes Carné: 2021084
 * @author: Jose Pablo Fabian Herrera Campos Carné: 2018183 Grupo 2: Lunes
 * Código Técnico: IN5BM
 */
public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAlumnos();
    }

    @FXML
    void clicCarrerasTecnicas(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }

    @FXML
    void clicSalones(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaSalones();
    }
    
    @FXML
    void clicAsignacionAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAsignacionAlumnos();
    }
    
    @FXML
    void clicInstructores(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaInstructores();
    }
    
    @FXML
    public void clicCursos(ActionEvent event){
        escenarioPrincipal.mostrarEscenaCursos();
    }
}
