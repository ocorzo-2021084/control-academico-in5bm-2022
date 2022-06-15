
package org.in5bm.octaviocorzo.joseherrera.models;

/**
 *
 * @author Octavio Alejandro Corzo Reyes
 * Carné: 2021084
 * @author: Jose Pablo Fabian Herrera Campos
 * Carné: 2018183
 * Grupo 2: Lunes
 * @date 27/04/2022
 * @time 21:30:55
 *
 * Código Técnico: IN5BM
 * 
 */
public class Alumnos {
    
    private String carne;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    
    public Alumnos(){
        
    }
    
    public Alumnos(String carne){
        this.carne = carne;
    }
    
    public Alumnos(String carne, String nombre1, String apellido1){
        this.carne = carne;
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
    }

    public Alumnos(String carne, String nombre1, String nombre2, String nombre3, String apellido1, String apellido2) {
        this.carne = carne;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }
    
    public  String getCarne(){
        return carne;
    }
    
    public void setCarne(String carne){
        this.carne = carne;
    }
    
    public String getNombre1(){
        return nombre1;
    }
    
    public void setNombre1(String nombre1){
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombre3() {
        return nombre3;
    }

    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    @Override
    public String toString() {
        return carne + " | " + nombre1;
    }

    
    
    
    
}

