
package org.in5bm.octaviocorzo.joseherrera.models;

/**
 *
 * @author Octavio Alejandro Corzo Reyes
 * Carné: 2021084
 * @author: Jose Pablo Fabian Herrera Campos
 * Carné: 2018183
 * Grupo 2: Lunes
 * @date 27/04/2022
 * @time 21:33:17
 *
 * Código Técnico: IN5BM
 * 
 */
public class Salones {
    
    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;
    
    public Salones(){
    }
    
    public Salones(String codigoSalon){
        this.codigoSalon = codigoSalon;
    }
    
    public Salones(String codigoSalon, int capacidadMaxima){
        this.codigoSalon = codigoSalon;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Salones(String codigoSalon, String descripcion, int capacidadMaxima, String edificio, int nivel) {
        this.codigoSalon = codigoSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }

    public String getCodigoSalon() {
        return codigoSalon;
    }

    public void setCodigoSalon(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return codigoSalon;
    }
    
    
}
