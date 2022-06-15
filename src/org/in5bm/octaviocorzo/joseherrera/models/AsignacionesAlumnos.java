
package org.in5bm.octaviocorzo.joseherrera.models;


import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDateTime;


/**
 *
 * @author Octavio Alejandro Corzo Reyes
 */
public class AsignacionesAlumnos {
    
    private IntegerProperty id;
    private StringProperty alumnoId;
    private IntegerProperty cursoId;
    private ObjectProperty<LocalDateTime> fechaAsignacion;

    public AsignacionesAlumnos() {
        this.id = new SimpleIntegerProperty();
        this.alumnoId = new SimpleStringProperty();
        this.cursoId = new SimpleIntegerProperty();
        this.fechaAsignacion = new SimpleObjectProperty<>();
    }

    public AsignacionesAlumnos(int id, String alumnoId, int cursoId, LocalDateTime fechaAsignacion){
        this.id = new SimpleIntegerProperty(id);
        this.alumnoId = new SimpleStringProperty(alumnoId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }
    
    public IntegerProperty id(){
        return id;
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public StringProperty alumnoId(){
        return alumnoId;
    }
    
    public String getAlumnoId(){
        return alumnoId.get();
    }
    
    public void setAlumnoId(String alumnoId){
        this.alumnoId.set(alumnoId);
    }
    
    public IntegerProperty cursoId(){
        return cursoId;
    }
    
    public int getCursoId(){
        return cursoId.get();
    }
    
    public void setCursoId(int cursoId){
        this.cursoId.set(cursoId);
    }
    
    public ObjectProperty<LocalDateTime> fechaAsignacion(){
        return fechaAsignacion;
    }
    
    public LocalDateTime getFechaAsignacion(){
        return fechaAsignacion.get();
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion){
        this.fechaAsignacion.set(fechaAsignacion);
    }

    @Override
    public String toString() {
        return id + " | " + fechaAsignacion;
    }
    
    
    
}
