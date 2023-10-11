
import compilerTools.Token;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class OptimizadorCodigo {

    private ArrayList<Token> tokens = new ArrayList<>();
    private Compilador compilador;
    private String erroresOptimizador = "";
    private String erroresIntermedio = "";

    OptimizadorCodigo(ArrayList<Token> tokens, Compilador compilador, String erroresIntermedio) {
        this.tokens = tokens;
        this.compilador = compilador;
        this.erroresIntermedio = erroresIntermedio;
    }

    public void realizarOptimizacionCodigo() {
        //Funcion que comprueba si hay errores, si los hay retorna un true
        //y evita que el optimizador de codigo se ejecute
        /*
        if (hayErrores(erroresIntermedio)) {
            compilador.setTxtOutputConsole(compilador.getTxtOutputConsole() + "\nERROR! Existen errores semanticos, por lo que no se realizo la generacion del codigo intermedio.\n");
            return;
        }
        */

        //Funcion que genera la optimizacion del codigo
        generarOptimizacion();

        String txt = compilador.txtOutputConsole.getText();
        if (erroresOptimizador.isBlank()) {
            //compilador.txtOutputConsole.setText(txt + "\nOptimizador de codigo: Sin errores");
        } else {
            compilador.txtOutputConsole.setText(txt + "Optimizador de codigo:\n"+erroresOptimizador);
        }

    }

    private void generarOptimizacion() {
        //String cad = obtenerCuadruplosTabla();
        DefaultTableModel modelo = generarColumnas();
        ArrayList a = new ArrayList();

        for (int i = 0, j = 0; i < compilador.jTableCodigoIntermedio.getRowCount(); i++) {
            //Si op esta vacio significa que es una variable no inicializada
            //Si el operador es = significa que es una variable
            if (esVariable(i)) {

                //Revisar si la variable esta siendo utilizada
                String cad = compilador.jTableCodigoIntermedio.getValueAt(i, 4).toString();
                if (estaSiendoUsada(cad)) {
                    modelo.addRow(generarCuadruplos(i, j));
                    j++;
                }
            }
            if (esFuncion(i)) {
                modelo.addRow(generarCuadruplos(i, j));
                j++;
            }
        }

        compilador.jTableCodigoOptimizado.setModel(modelo);
        compilador.jTableCodigoOptimizado.setFont(new Font(compilador.txtOutputConsole.getFont().getName(), Font.PLAIN, 18));
    }

    private Object[] generarCuadruplos(int i, int j) {
        ArrayList a = new ArrayList();
        a.add(compilador.jTableCodigoIntermedio.getValueAt(j, 0).toString()); //contador
        a.add(compilador.jTableCodigoIntermedio.getValueAt(i, 1).toString()); //operador
        a.add(compilador.jTableCodigoIntermedio.getValueAt(i, 2).toString()); //arg1
        a.add(compilador.jTableCodigoIntermedio.getValueAt(i, 3).toString()); //arg2
        a.add(compilador.jTableCodigoIntermedio.getValueAt(i, 4).toString()); //resultado
        return a.toArray();
    }

    private DefaultTableModel generarColumnas() {
        String columns[] = {"Contador", "Operador", "Argumento1", "Argumento2", "Resultado"};

        DefaultTableModel modelo = new DefaultTableModel();

        for (String column : columns) {
            modelo.addColumn(column);
        }

        return modelo;
    }

    private boolean esFuncion(int i) {
        return compilador.jTableCodigoIntermedio.getValueAt(i, 1).toString().equals("parametro");
    }

    private boolean esVariable(int j) {
        return opVacio(j) || opIgual(j);
    }

    private boolean estaSiendoUsada(String cad) {

        for (int i = 0; i < compilador.jTableCodigoIntermedio.getRowCount(); i++) {
            if (compilador.jTableCodigoIntermedio.getValueAt(i, 1).toString().equals("parametro") && (compilador.jTableCodigoIntermedio.getValueAt(i, 2).toString().equals(cad) || compilador.jTableCodigoIntermedio.getValueAt(i, 3).toString().equals(cad))) {
                return true;
            }
        }

        return false;
    }

    private boolean opVacio(int i) {

        String op = compilador.jTableCodigoIntermedio.getValueAt(i, 1).toString();
        //String arg1 = compilador.jTableCodigoIntermedio.getValueAt(i, 2).toString();
        //String arg2 = compilador.jTableCodigoIntermedio.getValueAt(i, 3).toString();

        return op.isBlank();
    }

    private boolean opIgual(int i) {
        return compilador.jTableCodigoIntermedio.getValueAt(i, 1).equals("=");
    }

    private String obtenerCuadruplosTabla() {
        String cad = compilador.jTableCodigoIntermedio.getColumnName(0) + "," + compilador.jTableCodigoIntermedio.getColumnName(1) + "," + compilador.jTableCodigoIntermedio.getColumnName(2) + "," + compilador.jTableCodigoIntermedio.getColumnName(3) + "," + compilador.jTableCodigoIntermedio.getColumnName(4) + "\n";
        for (int i = 0; i < compilador.jTableCodigoIntermedio.getRowCount(); i++) {
            cad += compilador.jTableCodigoIntermedio.getValueAt(i, 0) + "," + compilador.jTableCodigoIntermedio.getValueAt(i, 1) + "," + compilador.jTableCodigoIntermedio.getValueAt(i, 2) + "," + compilador.jTableCodigoIntermedio.getValueAt(i, 3) + "," + compilador.jTableCodigoIntermedio.getValueAt(i, 4) + "\n";
        }
        return cad;
    }

    private boolean hayErrores(String errores) {
        return errores.isBlank();
    }
}
