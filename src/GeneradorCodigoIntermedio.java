
import compilerTools.Token;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class GeneradorCodigoIntermedio {

    private ArrayList<Token> tokens = new ArrayList<>();
    private Compilador compilador;
    private String erroresSemanticos = "";
    private String erroresIntermedio = "";

    GeneradorCodigoIntermedio(ArrayList<Token> tokens, Compilador compilador, String erroresSemanticos) {
        this.tokens = tokens;
        this.compilador = compilador;
        this.erroresSemanticos = erroresSemanticos;
    }

    public void realizarCodigoIntermedio() {
        //Funcion que comprueba si hay errores, si los hay retorna un true
        //y evita que el generador de codigo intermedio se ejecute
        /*
        if (hayErrores(erroresSemanticos)) {
            compilador.setTxtOutputConsole(compilador.getTxtOutputConsole() + "\nERROR! Existen errores semanticos, por lo que no se realizo la generacion del codigo intermedio.\n");
            return;
        }
        */

        //Funcion que genera los cuadruplos
        generarCuadruplos();

        String txt = compilador.txtOutputConsole.getText();
        if (erroresIntermedio.isBlank()) {
            //compilador.txtOutputConsole.setText(txt + "\nGenerador de codigo intermedio: Sin errores");
        } else {
            compilador.txtOutputConsole.setText(txt + "Generador de codigo intermedio:\n"+erroresIntermedio);
        }
    }

    private void generarCuadruplos() {

        DefaultTableModel modelo = generarColumnas();

        ArrayList a = new ArrayList();

        for (int i = 0, j = 0; i < tokens.size(); i++) {

            switch (tokens.get(i).getLexicalComp()) {
                case "variable":
                    //Funcion que retorna true si es una variable inicializada
                    if (estructuraVariablesInicializadas(i)) {
                        modelo.addRow(cuadruploVariableInicializada(i, j).toArray());
                        a.clear();
                        j++;
                    } else {
                        //Si llega a esta parte sginifica que la variable no esta inicializada
                        modelo.addRow(cuadruploVariable(i, j).toArray());
                        a.clear();
                        j++;
                    }
                    break;
                case "prepmesa":
                    modelo.addRow(cuadruploPrepMesa(i, j).toArray());
                    a.clear();
                    j++;
                    break;
                case "vermesa":
                    modelo.addRow(cuadruploVerMesa(i, j).toArray());
                    a.clear();
                    j++;
                    break;
                case "solicitarmesero":
                    modelo.addRow(cuadruploSolicitarMesero(i, j).toArray());
                    a.clear();
                    j++;
                    break;
                case "vermenu":
                    modelo.addRow(cuadruploVerMenu(i, j).toArray());
                    a.clear();
                    j++;
                    break;
                case "realizarpedido":
                    modelo.addRow(cuadruploRealizarPedido(i, j, "realizarpedido").toArray());
                    a.clear();
                    j++;
                    modelo.addRow(cuadruploRealizarPedido(i + 4, j, "realizarpedido").toArray());
                    a.clear();
                    j++;
                    break;
                case "declararmenu":
                    modelo.addRow(cuadruploDeclararMenu(i, j, "declararmenu").toArray());
                    a.clear();
                    j++;
                    modelo.addRow(cuadruploDeclararMenu(i + 4, j, "declararmenu").toArray());
                    a.clear();
                    j++;
                    modelo.addRow(cuadruploDeclararMenu2(i + 8, j, "declararmenu").toArray());
                    a.clear();
                    j++;
                    break;
                case "iluminarcamino":
                    modelo.addRow(cuadruploIluminarCamino(i, j).toArray());
                    a.clear();
                    j++;
                    break;
            }
        }

        compilador.jTableCodigoIntermedio.setModel(modelo);
        compilador.jTableCodigoIntermedio.setFont(new Font(compilador.txtOutputConsole.getFont().getName(), Font.PLAIN, 18));
    }

    private ArrayList cuadruploIluminarCamino(int i, int j) {
        String nombreFun = tokens.get(i).getLexeme();
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el token de parentesis
        a.add(j); //contador
        a.add("parametro"); //operador
        i++; //pasamos al primer parametro
        a.add(tokens.get(i).getLexeme()); //el argumento 1
        a.add("");
        //
        a.add(nombreFun);
        return a;
    }

    private DefaultTableModel generarColumnas() {
        String columns[] = {"Contador", "Operador", "Argumento1", "Argumento2", "Resultado"};

        DefaultTableModel modelo = new DefaultTableModel();

        for (String column : columns) {
            modelo.addColumn(column);
        }

        return modelo;
    }

    private ArrayList cuadruploRealizarPedido(int i, int j, String nombreFun) {
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el token de parentesis
        a.add(j); //contador
        a.add("parametro"); //operador
        i++; //obviamos al primer parametro
        a.add(tokens.get(i).getLexeme()); //el argumento 1
        i++; //obviamos la coma
        i++; //pasamos al segundo argumento
        a.add(tokens.get(i).getLexeme()); //el argumento 2
        //
        a.add(nombreFun);
        return a;
    }

    private ArrayList cuadruploDeclararMenu(int i, int j, String nombreFun) {
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el token de parentesis
        a.add(j); //contador
        a.add("parametro"); //operador
        i++; //obviamos al primer parametro
        a.add(tokens.get(i).getLexeme()); //el argumento 1
        i++; //obviamos la coma
        i++; //pasamos al segundo argumento
        a.add(tokens.get(i).getLexeme()); //el argumento 2
        //
        a.add(nombreFun);
        return a;
    }

    private ArrayList cuadruploDeclararMenu2(int i, int j, String nombreFun) {
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el token de parentesis
        a.add(j); //contador
        a.add("parametro"); //operador
        i++; //obviamos al primer parametro
        a.add(tokens.get(i).getLexeme()); //el argumento 1
        a.add("");
        //
        a.add(nombreFun);
        return a;
    }

    private ArrayList cuadruploVariableInicializada(int i, int j) {
        i++; //pasamos al siguiente token (identificador)
        String identificador = tokens.get(i).getLexeme();
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el tipo de dato
        a.add(j); //Contador
        i++; //Para ir al de operador
        a.add(tokens.get(i).getLexeme()); //Operador
        i++;
        a.add(tokens.get(i).getLexeme()); //Argumento 1
        a.add(""); //Argumento 2
        i++;
        //
        a.add(identificador); //Resultado
        return a;
    }

    private ArrayList cuadruploVariable(int i, int j) {
        i++; //pasamos al siguiente token (identificador)
        String identificador = tokens.get(i).getLexeme();
        ArrayList a = new ArrayList();
        a.add(j); //Contador
        a.add(""); //Operador
        a.add(""); //Argumento 1
        a.add(""); //Argumento 2
        //
        a.add(identificador); //Resultado
        return a;
    }

    private ArrayList cuadruploPrepMesa(int i, int j) {
        String nombreFun = tokens.get(i).getLexeme();
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el token de parentesis
        a.add(j); //contador
        a.add("parametro"); //operador
        i++; //obviamos al primer parametro
        a.add(tokens.get(i).getLexeme()); //el argumento 1
        i++; //obviamos la coma
        i++; //pasamos al segundo argumento
        a.add(tokens.get(i).getLexeme()); //el argumento 2
        //
        a.add(nombreFun);
        return a;
    }

    private ArrayList cuadruploVerMenu(int i, int j) {
        String nombreFun = tokens.get(i).getLexeme();
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el token de parentesis
        a.add(j); //contador
        a.add("parametro"); //operador
        i++; //obviamos al primer parametro
        a.add(tokens.get(i).getLexeme()); //el argumento 1
        i++; //obviamos la coma
        i++; //pasamos al segundo argumento
        a.add(tokens.get(i).getLexeme()); //el argumento 2
        //
        a.add(nombreFun);
        return a;
    }

    private ArrayList cuadruploVerMesa(int i, int j) {
        String nombreFun = tokens.get(i).getLexeme();
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el token de parentesis
        a.add(j); //contador
        a.add("parametro"); //operador
        i++; //pasamos al primer parametro
        a.add(tokens.get(i).getLexeme()); //el argumento 1
        a.add("");
        //
        a.add(nombreFun);
        return a;
    }

    private ArrayList cuadruploSolicitarMesero(int i, int j) {
        String nombreFun = tokens.get(i).getLexeme();
        ArrayList a = new ArrayList();
        //
        i++; //obviamos el token de parentesis
        a.add(j); //contador
        a.add("parametro"); //operador
        i++; //pasamos al primer parametro
        a.add(tokens.get(i).getLexeme()); //el argumento 1
        a.add("");
        //
        a.add(nombreFun);
        return a;
    }

    private boolean estructuraVariablesInicializadas(int i) {
        return (tokens.get(i).getLexicalComp().equals("variable") && tokens.get(i + 3).getLexicalComp().equals("asignacion") && (tokens.get(i + 4).getLexicalComp().equals("Cadenas") || tokens.get(i + 4).getLexicalComp().equals("numero") || tokens.get(i + 4).getLexicalComp().equals("dinero")));
    }

    private boolean hayErrores(String errores) {
        return errores.isBlank();
    }

}
