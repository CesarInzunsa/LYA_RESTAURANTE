package ManejarArchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *
 * @author cesar
 */
public class ManejoArchivos {

    public static void crearArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        
        try {
            PrintWriter salida = new PrintWriter(archivo);
            salida.close();
            //System.out.println("Se creo el archivo");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /***
     * Funcion que escibe en un archivo eliminando su contenido
     * @param nombreArchivo
     * @param contenido 
     */
    public static void escribirArchivo(String nombreArchivo, String contenido) {
        File archivo = new File(nombreArchivo);
        
        try {
            PrintWriter salida = new PrintWriter(archivo);
            salida.println(contenido);
            salida.close();
            //System.out.println("Se escribio el archivo");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /***
     * Funcion que escribe en un archivo sin eliminar su contenido
     * @param nombreArchivo
     * @param contenido 
     */
    public static void escribirArchivoSinBorrarContenido(String nombreArchivo, String contenido) {
        File archivo = new File(nombreArchivo);
        
        try {
            PrintWriter salida = new PrintWriter(new FileWriter(archivo, true));
            salida.println(contenido);
            salida.close();
            //System.out.println("Se escribio el archivo");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
