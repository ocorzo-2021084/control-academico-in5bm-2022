package org.in5bm.octaviocorzo.joseherrera.db;

/**
 *
 * @author Octavio Alejandro Corzo Reyes
 * @date 3/05/2022
 * @time 11:09:01
 *
 * Código Técnico: IN5BM
 *
 */
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class Conexion {

    private final String URL;
    private final String IP_SERVER;
    private final String PORT;
    private final String DB;
    private final String USER;
    private final String PASSWORD;
    private Connection conexion;

    private static Conexion instancia;

    public static Conexion getInstance() {

        if (instancia == null) {
            instancia = new Conexion();
        }

        return new Conexion();
    }

    private Conexion() {
        IP_SERVER = "127.0.0.1";
        PORT = "3306";
        DB = "db_control_academico_in5bm";
        USER = "kinal";
        PASSWORD = "admin";

        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión Exitosa");

            DatabaseMetaData dma = conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion() + "\n");

        } catch (ClassNotFoundException e) {
            System.err.println("No se encuentra ninguna definición para la clase");
            e.printStackTrace();
            /*
        }catch(InstantiationException e){  
            System.err.println("No se puede crear una instancia del objeto");
            e.printStackTrace();
        
        
        }catch(IllegalAccessException e){
            System.err.println("");
            e.printStackTrace();
             */
        } catch (CommunicationsException e) {
            System.err.println("No se puede establecer comunicación con el server de MYSQL");
            System.err.println("Verifique que el servicio de MSQL este levantado,"
                    + "que la IP_SERVER y el puerto estén correctos");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Se produjo un error de tipo SQLException");
            System.out.println("SQLSate: " + e.getSQLState());
            System.out.println("ErrorCode:" + e.getErrorCode());
            System.out.println("Message:" + e.getMessage());
            System.out.println("\n");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Se produjo un error al intentar establecer una conexión con la bd");
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}
