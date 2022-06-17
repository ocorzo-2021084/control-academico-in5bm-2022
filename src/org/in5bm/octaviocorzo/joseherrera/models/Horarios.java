package org.in5bm.octaviocorzo.joseherrera.models;

import javafx.beans.property.IntegerProperty;

/**
 *
 * @author informatica
 */
import java.time.LocalTime;

public class Horarios {

    private int id;
    private LocalTime horarioInicio;
    private LocalTime horarioSalida;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;

    public Horarios() {

    }

    public Horarios(int id, LocalTime horarioInicio, LocalTime horarioSalida) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horarioSalida = horarioSalida;
    }

    public Horarios(int id, LocalTime horarioInicio, LocalTime horarioSalida, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horarioSalida = horarioSalida;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
    }

    public Horarios(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(LocalTime horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    public boolean getLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean getMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean getJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean getViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    @Override
    public String toString() {
        return id + " | " + horarioInicio + " | " + horarioSalida;
    }
}
