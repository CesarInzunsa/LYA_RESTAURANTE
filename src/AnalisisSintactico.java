
import VentanasSecundarias.VentanaAutomata;
import compilerTools.Token;
import java.util.ArrayList;

public class AnalisisSintactico {

    private ArrayList<Token> tokens = new ArrayList<>();
    private VentanaAutomata vAutomata;
    private String cadena = "";
    private String errores = "";
    private Compilador compilador;
    public String gramatica = "";
    private String erroresLexicos = "";

    public AnalisisSintactico(ArrayList<Token> tokens, VentanaAutomata vAutomata, Compilador compilador, String erroresLexicos) {
        this.tokens = tokens;
        this.vAutomata = vAutomata;
        this.compilador = compilador;
        //this.compilador.setTxtOutputConsole(errores);
        this.erroresLexicos = erroresLexicos;
    }

    public String getErroresSintactico() {
        return errores;
    }

    public void setErroresSintactico(String errores) {
        this.errores = errores;
    }
    
    

    public void realizarAnalisisSintactico() {
        /*
        if (hayErrores()) {
            compilador.setTxtOutputConsole("ERROR! Existen errores lexicos, por lo que no se realizo el analisis sintactico");
            return;
        }
        */
        
        /*
        if (tokens.isEmpty()) {
            compilador.setTxtOutputConsole("ERROR! NO HAY CODIGO PARA COMPILAR");
            return;
        }
        */

        q0Main(0);

        if (!errores.isBlank()) {
            compilador.setTxtOutputConsole(errores);
            vAutomata.setTxtEstados(cadena);
            return;
        }

        vAutomata.setTxtEstados(cadena);

        for (int i = 4; i < tokens.size(); i++) {
            //Se compara el componente lexico
            switch (tokens.get(i).getLexicalComp()) {
                case "vermenu":
                    validarVerMenu(i + 1);
                    break;
                case "variable":
                    validarVariable(i + 1);
                    break;
                case "realizarpedido":
                    validarRealizarPedido(i + 1);
                    break;
                case "solicitarmesero":
                    validarSolicitarMesero(i + 1);
                    break;
                case "iluminarcamino":
                    validarIluminarCamino(i + 1);
                    break;
                case "declararmenu":
                    validarDeclararMenu(i + 1);
                    break;
                case "prepmesa":
                    validarDeclararMesas(i + 1);
                    break;
                case "if":
                    validarIf(i + 1);
                    break;
                case "for":
                    validarFor(i + 1);
                    break;
                case "pantalla":
                    validarPrint(i + 1);
                    break;
                case "while":
                    validarWhile(i + 1);
                    break;
            }
            //System.out.println(tokens.get(i));
        }
        if (errores.isBlank()) {
            //compilador.txtOutputConsole.setText(compilador.txtOutputConsole.getText()+"Analisis lexico: Sin errores");
            //compilador.txtOutputConsole.setText("Analisis sintactico: Sin errores");

        } else {
            String txt = compilador.txtOutputConsole.getText();
            compilador.txtOutputConsole.setText(txt+"Analisis sintactico:\n"+errores);

        }
        compilador.gramaticaUtilizada.setGramaticaUtilizada(gramatica);
    }

    /**
     * @return true si hay un error en el lexico
     */
    private boolean hayErrores() {

        for (Token token : tokens) {
            if (token.getLexicalComp().equals("ERROR_LEX")) {
                return true;
            }
        }

        return false;

    }

    private void validarFor(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("asignacion") || tokens.get(i).getLexicalComp().equals("operadorLogico")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("asignacion") || tokens.get(i).getLexicalComp().equals("operadorLogico")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("Identificadores") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("SignoAritmetico") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("SignoAritmetico") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("CorcheteA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        while (!tokens.get(i).getLexicalComp().equals("CorcheteC") && algunError == false && !tokens.get(i).getLexicalComp().equals("fin")) {
            i++;
        }
        if (tokens.get(i).getLexicalComp().equals("CorcheteC")) {
            gramatica += " " + tokens.get(i).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: for(comparacion, comparacion, aumente o decremento){}{//El codigo} Donde los la comparaciones puede ser entre dos numeros o variables de tipo int, y los operadores logicos pueden ser: <, >, <=, >=. El aumento o decremento puede ser de un identificador y un ++ o --\n";
        }
    }

    public void validarWhile(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("operadorLogico") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("CorcheteA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        while (!tokens.get(i).getLexicalComp().equals("CorcheteC") && algunError == false && !tokens.get(i).getLexicalComp().equals("fin")) {
            i++;
        }
        if (tokens.get(i).getLexicalComp().equals("CorcheteC")) {
            gramatica += " " + tokens.get(i).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: while(condicion){//El codigo} Donde los la condicion puede ser entre dos numeros o variables de tipo int, y los operadores logicos pueden ser: <, >, <=, >=.\n";
        }
    }

    private void validarPrint(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();

        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }

        if (algunError == false) {
            switch (tokens.get(i).getLexicalComp()) {
                case "Cadenas":
                    gramatica += " " + tokens.get(i).getLexicalComp();
                    validarPrintCadenaIdentificador(i + 1);
                    break;
                case "Identificadores":
                    gramatica += " " + tokens.get(i).getLexicalComp();
                    validarPrintCadenaIdentificador(i + 1);
                    break;
                case "numero":
                    gramatica += " " + tokens.get(i).getLexicalComp();
                    validarPrintNumero(i + 1);
                    break;
            }
        }
        /*
        if ((tokens.get(i).getLexicalComp().equals("Cadenas") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        */
    }
    
    private void validarPrintNumero(int i){
        boolean algunError = false;
        if ((tokens.get(i).getLexicalComp().equals("operadorLogico") || tokens.get(i).getLexicalComp().equals("SignoAritmetico")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: print(numero operador numero);\n";
        }
    }
    
    private void validarPrintCadenaIdentificador(int i){
        boolean algunError = false;
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: print(Cadena); Donde el parametro puede ser una cadena o un identificador.\n";
        }
    }

    private void validarIf(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("operadorLogico") || tokens.get(i).getLexicalComp().equals("SignoAritmetico")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("CorcheteA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        while (!tokens.get(i).getLexicalComp().equals("CorcheteC") && algunError == false && !tokens.get(i).getLexicalComp().equals("fin")) {
            i++;
        }
        if (tokens.get(i).getLexicalComp().equals("CorcheteC")) {
            gramatica += " " + tokens.get(i).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: if(condicion){//El codigo} Donde los la condicion puede ser entre dos numeros o variables de tipo int, y los operadores logicos pueden ser: <, >, <=, >=. Ó tambien puede ser un operador aritmetico como + - * /\n";
        }
    }

    private void intVariables(int i) {
        //primero variable, segundo identificador, tercero tipo de dato, cuarto ; o igual
        //si es igual mandar a una funcion que valide ver ofertas o mesas o Tiempo pedido
        //la función debe de tener un switch dependiendo del siguiente componente lexico si es ver ofertas o ver mesas o tiempo pedido mandara la función correspondiedo 
        switch (tokens.get(i).getLexicalComp()) {
            case "verofertas":
                validarVof(i + 1);
                break;
            case "vermesa":
                validarVme(i + 1);
                break;
            case "tiempopedido":
                validarVti(i + 1);
                break;
            case "numero":
                validarAsignacionVariable(i + 1);
                break;
            case "Identificadores":
                validarAsignacionVariable(i + 1);
                break;
        }
    }

    private void validarAsignacionVariable(int i) {
        boolean algunError = false;
        gramatica += " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }

        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: Variable es un var nombre tipo de dato; donde tipo de dato puede ser un int string booleano string[].\n";
        }
    }

    private void validarVof(int i) {
        boolean algunError = false;
        gramatica += " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }        //Donde se realiza la comparacion
        if ((tokens.get(i).getLexicalComp().equals("Cadenas") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("tipo") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: verofertas(horadelDia, tipoComida); Donde todos los parametros pueden ser cadenas o identificadores.\n";
        }

    }

    private void validarVme(int i) {
        boolean algunError = false;
        gramatica += " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: vermesa(numer); Donde todos los parametros pueden ser un numero entero o un identificador.\n";
        }
    }

    private void validarVti(int i) {
        boolean algunError = false;
        gramatica += " " + tokens.get(i - 1).getLexicalComp();
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: Tiempopedido(numeroMesa); Donde todos los parametros pueden ser un numero entero o un identificador.\n";
        }

    }

    private void validarDeclararMesas(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }

        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: prepararmesa(numeroMesa, numeroAsientos); Donde todos los parametros pueden ser variables o numeros enteros.\n";
        }
    }

    private void validarDeclararMenu(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Cadenas") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Cadenas") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("dinero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("tipo") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Cadenas") || tokens.get(i).getLexicalComp().equals("Identificadores") || tokens.get(i).getLexicalComp().equals("tiempo")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }

        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: declararmenu(nombrePlatillo, descripcionPlatillo, precioPlatillo, tipoPlatillo, ingredientesPlatillo); Donde todos los parametros pueden ser variables o en el caso de nombrePlatillo y descripcionPlatillo puden recibir una cadena, precioPlatillo un numero con la estructura de un numero decimal, tipoPlatillo puede recibir vegetariano, regular ó pesqueteriano, y ingredientesPlatillo puede recibir un arreglo de tipo String.\n";
        }
    }

    private void validarIluminarCamino(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }

        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: iluminarcamino(numeroMesa); Donde numeroMesa es un entero o una variable\n";
        }
    }

    private void validarSolicitarMesero(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("Identificadores")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }

        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: solicitarmesero(numeroMesa); Donde numeroMesa es un entero o una variable\n";
        }
    }

    private void validarRealizarPedido(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Identificadores") || tokens.get(i).getLexicalComp().equals("numero") || tokens.get(i).getLexicalComp().equals("tiempo")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Identificadores") || tokens.get(i).getLexicalComp().equals("Cadenas") || tokens.get(i).getLexicalComp().equals("numero")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Identificadores") || tokens.get(i).getLexicalComp().equals("Cadenas")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Identificadores") || tokens.get(i).getLexicalComp().equals("booleano")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }

        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: realizarpedido(horaDelDia, numeroMesa, Pedido, true | false); Donde la horaDelDia es una variable o una hora en formato de 24 horas, numeroMesa es una variable o un entero, Pedido es una variable o una cadena, y el utlimo campo es una variable o un booleano.\n";
        }
    }

    private void validarVariable(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("Identificadores") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("tipoDato") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("asignacion") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
            intVariables(i);
        } else {
            if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
                i++;
                gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
            } else {
                algunError = true;
            }
        }
        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: var nombreVariable tipoDato finlinea.\n";
        }

    }

    private void validarVerMenu(int i) {
        boolean algunError = false;
        gramatica += "Linea: " + tokens.get(i).getLine() + " " + tokens.get(i - 1).getLexicalComp();
        //Donde se realiza la comparacion
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Identificadores") || tokens.get(i).getLexicalComp().equals("Cadenas") || tokens.get(i).getLexicalComp().equals("tiempo")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("coma") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("Identificadores") || tokens.get(i).getLexicalComp().equals("tipo")) && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("parentecisC") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp();
        } else {
            algunError = true;
        }
        if (tokens.get(i).getLexicalComp().equals("finlinea") && algunError == false) {
            i++;
            gramatica += " " + tokens.get(i - 1).getLexicalComp() + "\n";
        } else {
            algunError = true;
        }

        if (algunError) {
            errores += "ERROR EN: [ " + tokens.get(i).getLine() + ", " + tokens.get(i).getColumn() + " ]; Tal vez quiso escribir: vermenu(HoraDelDia, tipoComida); Donde: HoraDelDia es una hora en formato de 24 horas y tipoComida puede ser vegetariano, regular ó pesqueteriano.\n";
        }
    }

    private void q0Main(int i) {
        if (tokens.get(i).getLexeme().equals("main")) {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] AVANZA DESDE EL ESTADO q0 HACIA EL ESTADO q1\n";
        } else {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] NO PERTENCE A LA TRANSICION DE q0 HACIA q1; SE ESPERABA [ main ]\n";
            errores += "ERROR EN LA TRANSICION DE q0 HACIA q1; MAS INFORMACION EN: OPCIONES > AUTOMATA\n";
            vAutomata.mostrarAutomata("Error1");
            return;
        }
        q1Main((i + 1));
        //vAutomata.setTxtEstados(cadena);
        //compilador.setTxtOutputConsole(errores);
    }

    private void q1Main(int i) {
        if (tokens.get(i).getLexeme().equals("(")) {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] AVANZA DESDE EL ESTADO q1 HACIA EL ESTADO q2\n";
        } else {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] NO PERTENCE A LA TRANSICION DE q1 HACIA q2; SE ESPERABA [ ( ]\n";
            errores += "ERROR EN LA TRANSICION DE q1 HACIA q2; MAS INFORMACION EN: OPCIONES > AUTOMATA\n";
            vAutomata.mostrarAutomata("Error2");
            return;
        }
        q2Main((i + 1));
        //vAutomata.setTxtEstados(cadena);
        //compilador.setTxtOutputConsole(errores);
    }

    private void q2Main(int i) {
        if (tokens.get(i).getLexeme().equals(")")) {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] AVANZA DESDE EL ESTADO q2 HACIA EL ESTADO q3\n";
        } else {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] NO PERTENCE A LA TRANSICION DE q2 HACIA q3; SE ESPERABA [ ) ]\n";
            errores += "ERROR EN LA TRANSICION DE q2 HACIA q3; MAS INFORMACION EN: OPCIONES > AUTOMATA\n";
            vAutomata.mostrarAutomata("Error3");
            return;
        }
        q3Main((i + 1));
        //vAutomata.setTxtEstados(cadena);
        //compilador.setTxtOutputConsole(errores);
    }

    private void q3Main(int i) {
        if (tokens.get(i).getLexeme().equals("{")) {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] AVANZA DESDE EL ESTADO q3 HACIA EL ESTADO q4\n";
        } else {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] NO PERTENCE A LA TRANSICION DE q3 HACIA q4; SE ESPERABA [ { ]\n";
            errores += "ERROR EN LA TRANSICION DE q3 HACIA q4; MAS INFORMACION EN: OPCIONES > AUTOMATA\n";
            vAutomata.mostrarAutomata("Error4");
            return;
        }
        q4Main(tokens.size() - 1);
        //vAutomata.setTxtEstados(cadena);
        //compilador.setTxtOutputConsole(errores);
    }

    private void q4Main(int i) {

        cadena += "LA CANTIDAD DE [ " + (tokens.size() - 5) + " ] TOKENS AVANZAN DESDE EL ESTADO q4 HACIA EL ESTADO q4\n";
        if (tokens.get(i).getLexeme().equals("};")) {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] AVANZA DESDE EL ESTADO q4 HACIA EL ESTADO q5\n";
            vAutomata.mostrarAutomata("TodoBien");
        } else {
            cadena += "EL TOKEN [ " + tokens.get(i).getLexeme() + " ] NO PERTENCE A LA TRANSICION DE q4 HACIA q5; SE ESPERABA [ }; ]\n";
            errores += "ERROR EN LA TRANSICION DE q4 HACIA q5; MAS INFORMACION EN: OPCIONES > AUTOMATA\n";
            vAutomata.mostrarAutomata("Error6");
        }
        //vAutomata.setTxtEstados(cadena);
        //compilador.setTxtOutputConsole(errores);
    }
}
