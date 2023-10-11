package ManejarArchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author chilaquiles
 */
public class Directorio {

    JFrame ventana;
    JTextPane txtSource;
    String title;
    String extension;

    public Directorio(JFrame ventana, JTextPane txtSource, String title, String extension) {
        this.ventana = ventana;
        this.txtSource = txtSource;
        this.title = title;
        this.extension = extension;
    }

    public boolean Open(String rutaDePrueba) {
        String codigo = "";
        File f;
        Scanner entrada = null;
        try {
            f = new File(rutaDePrueba);
            entrada = new Scanner(f);
            while (entrada.hasNext()) {
                codigo += entrada.nextLine() + "\n";
            }
            if (entrada != null) {
                entrada.close();
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        //imprimir el texto en el jTextPane
        this.txtSource.setText(codigo);
        //actualizar el titulo del JFrame con el nombre del archivo y su extension
        this.ventana.setTitle(f.getName());
        return true;
    }

    public boolean Open() {
        String codigo = "";
        File f;
        Scanner entrada = null;
        //crear file chooser
        JFileChooser fileChooser = new JFileChooser();
        //crear filtro
        FileFilter filtro = new FileNameExtensionFilter("Archivos compiladorRestaurante (" + this.extension + ")", this.extension.substring(1));
        //aplicar el filtro
        fileChooser.setFileFilter(filtro);
        //abrir file chooser
        int valor = fileChooser.showOpenDialog(fileChooser);
        //obtener el texto del archivo
        if (valor == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                f = new File(ruta);
                entrada = new Scanner(f);
                while (entrada.hasNext()) {
                    codigo += entrada.nextLine() + "\n";
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (FileNotFoundException e) {
                return false;
            }
            //imprimir el texto en el jTextPane
            this.txtSource.setText(codigo);
            //actualizar el titulo del JFrame con el nombre del archivo y su extension
            this.ventana.setTitle(f.getName());
            
            this.title = f.getName();
            
            return true;
        }
        return false;
    }
    
    public String getTitulo(){
        return title;
    }
    
}
