
package org.in5bm.octaviocorzo.joseherrera.models;

/**
 *
 * @author Octavio Alejandro Corzo Reyes
 * @date 8/06/2022
 * @time 21:32:16
 *
 * Código Técnico: IN5BM
 * 
 */
import java.sql.Date;
import java.time.LocalDate;

public class Instructores {

    private int id;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;

    public Instructores() {

    }

    public Instructores(int id, String nombre1, String apellido1, String email, String telefono) {
        this.id = id;
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
        this.email = email;
        this.telefono = telefono;
    }

    public Instructores(int id, String nombre1, String nombre2, String nombre3, String apellido1, String apellido2, String direccion, String email, String telefono, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Instructores(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return  id + " | " + nombre1;
                
    }
}