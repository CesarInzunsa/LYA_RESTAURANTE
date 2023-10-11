
import compilerTools.Token;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author cesar
 */
public class AnalisisSemantico {

    private ArrayList<Token> tokens = new ArrayList<>();
    private Stack pilaTokensFunciones = new Stack();
    private String erroresSintactico = "";
    private Compilador compilador;
    private String erroresSemanticos = "";
    private List funcionesBase = new ArrayList<>();
    private ArrayList<Token> parametrosDeFunciones = new ArrayList<>();
    private Stack pilaParametros = new Stack();
    //private Stack pilaVariables = new Stack();
    ArrayList<Token> tokensAuxiliarVar = new ArrayList<>();
    ArrayList<Token> identConTipoDato = new ArrayList<>();

    Stack pilaAuxParametrosIden = new Stack();

    Stack pilaVariables = new Stack();
    Stack pilaTipoDato = new Stack();
    Stack pilaDato = new Stack();
    ArrayList<Token> arregloTokensVar = new ArrayList();

    public AnalisisSemantico(ArrayList<Token> tokens, Compilador compilador, String erroresSintactico) {
        this.tokens = tokens;
        this.compilador = compilador;
        this.erroresSintactico = erroresSintactico;

        inicializarFuncionesBase();

    }

    //Inicializar con componentes lexicos de las funciones que no dependen de ninguna otra funcion para funcionar.
    private void inicializarFuncionesBase() {
        funcionesBase.add("declararmenu");
        funcionesBase.add("prepmesa");
    }

    public void realizarAnalisisSemantico() {
        //Funcion que comprueba si hay errores, si los hay retorna un true
        //y evita que el analisis semantico se ejecute
        /*
        System.out.println(erroresSintactico);
        if (erroresSintactico.isBlank()) {
            compilador.setTxtOutputConsole(erroresSintactico+"\nERROR! Existen errores sintacticos, por lo que no se realizo el analisis semantico");
            return;
        }
         */

        //Función que guarda los tokens en una pila y va relizando diversos analisis
        //conforme van ingresando los tokens
        automataPila();

        //Esta funcion valida que el tipo de dato de las variables que recibe como parametro
        //la funcion print sean los correctos
        //revisarPrint();
        String txt = compilador.txtOutputConsole.getText();
        if (erroresSemanticos.isBlank()) {
            //compilador.txtOutputConsole.setText(txt + "\nAnalisis semantico: Sin errores");
        } else {
            compilador.txtOutputConsole.setText(txt + "Analisis semantico:\n" + erroresSemanticos);
        }
    }

    private void automataPila() {

        /*
        (P,x,s; q,t)
        
        P = Estado inicial
        q = Estado al que llega
        x = Simbolo de la cadena de entrada
        s = Simbolo que se desapila
        t = Simbolo que se apila
         */
        //Con el ciclo for vamos obteniendo los tokens del arreglo y los vamos ingresando en la pila "pilaTokens"
        //Es decir, tokens seria la cadena de entrada y token seria el cabezal de lectura
        //que se situa en la primera posicion de tokens
        for (Token token : tokens) {
            //Aquí va el mecanismo de control que se situa en el primer estado
            //Dentro del mecanismo de control hay una cadena de lectura para la pila,
            //que empieza siempre en el tope de la pila
            //
            //CONTROL DE ORDEN DE FUNCIONES O CONTROL DE FLUJO
            validarOrdenDeFunciones(token);
        }

        //Esta funcion valida si existen identificadores de variables que esten duplicados
        revisarIdentificadoresDeVariables();

        //Esta funcion valida si los parametros son variables del tipo correcto
        //validarParametrosDeFuncionesTipo(tokensAuxiliarVar);
        //Esta funcion valida si las variables declaradas inicializadas son del tipo correcto
        //Si el dato incializado corresponde al tipo de dato de la variable
        validarVarIniTipo();

        //Esta funcion valida si los parametros de las funciones son variables que ya han sido declaradas
        validarParametrosDeFunciones();

        //Ahora guardamos todos los parametros en una pila
        //guardarTokensEnLaPila(pilaParametros, parametrosDeFunciones);
        //Limpiamos el JtextArea del semantico
        compilador.jTextAreaSemantico.setText("");

        //Si la pila no esta vacia, significa que existe un error semantico
        if (!pilaTokensFunciones.empty()) {

            //Antes de enviar el mensaje de error, debemos de averiguar si en la pila estan las funciones base
            //Son funciones que no dependen de ninguna otra funcion, por lo que pueden estar solas.
            if (!buscarEnLaPila(funcionesBase, this.pilaTokensFunciones)) {
                erroresSemanticos += "ERROR EN LA PILA: QUEDARON ELEMENTOS [" + pilaTokensFunciones.size() + "]; MAS INFORMACIÓN EN LA VENTANA ANALISIS SEMANTICO\n";
            }
        }

        String linea = "+------------------------------------------------+";

        //PILA DE FUNCIONES
        String titulo1 = espacios(14) + "Pila de funciones";
        imprimirPila(this.pilaTokensFunciones, titulo1, 45, compilador.jTextAreaSemantico.getText(), linea);

        //compilador.jTextAreaSemantico.setText(compilador.jTextAreaSemantico.getText() + "\n");
        //PILA DE PARAMETROS DE FUNCIONES
        String titulo2 = espacios(14) + "Pila de parametros";
        imprimirPila(pilaParametros, titulo2, 45, compilador.jTextAreaSemantico.getText(), linea);

        //compilador.jTextAreaSemantico.setText(compilador.jTextAreaSemantico.getText() + "\n");
        //PILA DE IDENTIFICADORES
        String titulo3 = espacios(14) + "Pila de variables";
        imprimirPila(pilaVariables, titulo3, 45, compilador.jTextAreaSemantico.getText(), linea);

        //PILA DE TIPO DATO
        String titulo4 = espacios(14) + "Pila de tipoDato";
        imprimirPila(pilaTipoDato, titulo4, 45, compilador.jTextAreaSemantico.getText(), linea);

        //PILA DE DATO
        String titulo5 = espacios(14) + "Pila de dato";
        imprimirPila(pilaDato, titulo5, 45, compilador.jTextAreaSemantico.getText(), linea);

    }

    /**
     * Funcion para conocer si los parametros de las funciones son variables que
     * ya fueron declaradas
     */
    private void validarParametrosDeFunciones() {
        Stack pilaParametrosAux1 = new Stack();
        Stack pilaParametrosAux2 = new Stack();

        pilaParametrosAux1.addAll(pilaParametros);
        pilaParametros.clear();

        //Ciclo para vaciar la pilaParametrosAux y que solo queden los identificadores
        for (Object pilaParametrosAux11 : pilaParametrosAux1) {
            Token t = (Token) pilaParametrosAux11;

            if (t.getLexicalComp().equals("Identificadores")) {
                pilaParametrosAux2.push(t);
                pilaParametros.push(t);
            }

        }
        //System.out.println(pilaParametrosAux2);
        for (Object pilaParametrosAuxi : pilaParametrosAux2) {
            Token tok = (Token) pilaParametrosAuxi;
            //System.out.println(pilaParametrosAuxi);
            if (!buscarTokenProximo(arregloTokensVar, tok.getLexeme())) {
                erroresSemanticos += "ERROR EN: [ " + tok.getLine() + ", " + tok.getColumn() + " ]; Se intento usar la variable: " + tok.getLexeme() + " sin antes declararla.\n";
                return;
            } else {
                pilaParametros.pop();
            }
        }

    }

    private void llenarPilas2() {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getLexicalComp().equals("variable") && tokens.get(i + 3).getLexicalComp().equals("asignacion") && (tokens.get(i + 4).getLexicalComp().equals("Cadenas") || tokens.get(i + 4).getLexicalComp().equals("numero") || tokens.get(i + 4).getLexicalComp().equals("dinero"))) {
                i++; //para saltarnos directo al token de identificadores
                pilaVariables.push(tokens.get(i));
                i++;
                pilaTipoDato.push(tokens.get(i));
                i++; //para ir al token de asignacion
                i++; //Para ir al dato
                pilaDato.push(tokens.get(i));
            }
        }
    }

    private void validarVarIniTipo() {

        llenarPilas2();

        Token tok;
        Token va;

        while (!pilaTipoDato.empty()) {

            tok = (Token) pilaTipoDato.pop();

            switch (tok.getLexeme()) {
                case "string":
                    va  = (Token) pilaDato.pop();
                    if (va.getLexicalComp().equals("Cadenas")) {
                        pilaVariables.pop();
                    } else {
                        erroresSemanticos += "ERROR EN: [ " + tok.getLine() + ", " + tok.getColumn() + " ]; Declaracion incorrecta, posible solucion: var nombre string = 'cadena'; donde 'cadena' es una cadena y nombre es el nombre de la variable.\n";
                        return;
                    }
                    break;
                case "float":
                    va  = (Token) pilaDato.pop();
                    if (va.getLexicalComp().equals("dinero")) {
                        pilaVariables.pop();
                    } else {
                        erroresSemanticos += "ERROR EN: [ " + tok.getLine() + ", " + tok.getColumn() + " ]; Declaracion incorrecta, posible solucion: var nombre float = $580; donde $580 puede ser cualquier valor monetario y nombre es el nombre de la variable.\n";
                        return;
                    }
                    break;
                case "int":
                    va  = (Token) pilaDato.pop();
                    if (va.getLexicalComp().equals("numero")) {
                        pilaVariables.pop();
                    } else {
                        erroresSemanticos += "ERROR EN: [ " + tok.getLine() + ", " + tok.getColumn() + " ]; Declaracion incorrecta, posible solucion: var nombre int = 8; donde 8 puede ser cualquier numero y nombre es el nombre de la variable.\n";
                        return;
                    }
                    break;
                case "boolean":
                    va  = (Token) pilaDato.pop();
                    if (va.getLexicalComp().equals("booleano")) {
                        pilaVariables.pop();
                    } else {
                        erroresSemanticos += "ERROR EN: [ " + tok.getLine() + ", " + tok.getColumn() + " ]; Declaracion incorrecta, posible solucion: var nombre boolean = booleano; donde booleano puede ser false o true y nombre es el nombre de la variable.\n";
                        return;
                    }
                    break;
            }

        }

    }

    private boolean tipoCorrecto(ArrayList<Token> t, Token a) {

        //FALTA QUE REVISE SI EL IDENTIFICADOR QUE RECIBE COMO PARAMETRO ES DEL TIPO CORRECTO
        //FALTA QUE REVISE SI LA VARIABLE
        ArrayList estructura_declararmenu = new ArrayList();
        estructura_declararmenu.add("cadena");
        estructura_declararmenu.add("cadena");
        estructura_declararmenu.add("float");
        estructura_declararmenu.add("cadena");
        estructura_declararmenu.add("tipo");

        //Primero buscamos el token de la variable y en que linea esta
        //int i = tokens.indexOf(a);
        //t tiene variables declaradas
        //a es un parametro de una funcion
        int i = 0;
        //pilaAuxParametrosIden tiene el nombre de la funcion y sus parametros
        switch (((Token) pilaAuxParametrosIden.get(i)).getLexeme()) {
            case "declararmenu":
                i++;
                if (pilaAuxParametrosIden.empty()) {

                }
                break;
        }

        for (int j = 0; j < identConTipoDato.size(); j++) {
            //este arreglo tiene el idenficadr y el tipo de dato
            identConTipoDato.get(j);
        }

        //un arreglo con el nomrbe de la variable y el tipo de dato
        return true;
    }

    private void validarParametrosDeFunciones(ArrayList<Token> t) {
        //Guardar en una pila las funciones con sus parametros.
        //Para eso busca el nombre de la funcion y empieza a guardar todos los tokens hasta que encuentre un punto y coma.
        //Solo guarda los tokens que son parametros

        //Crear una pila y que guarde todos los identificadores de variables cuando encuentre un "var"
        //Ir eliminando los identificadores de la pila de variables cuando haya una coincidencia
        //con algun identificador de un parametro de alguna funcion.
        //
        //pilaParametros tiene los parametros de las funciones
        //t tiene las variables declaradas
        //Eliminar parametros de la pila de parametros que no sean Identificadores
        while (!pilaParametros.isEmpty()) {

            Token a = (Token) pilaParametros.peek();

            if (a.getLexicalComp().equals("Identificadores")) {

                //Obtener el indice del token del identificador en el arreglo e tokens
                int i = tokens.indexOf(a);

                //buscar en el arreglo de tokens de variables declaras si existe la variable del parametro
                if (buscarTokenProximo(t, a.getLexeme())) {
                    pilaParametros.pop();
                } else {
                    erroresSemanticos += "ERROR EN: [ " + a.getLine() + ", " + a.getColumn() + " ]; Error en la pila de parametros, se intento usar " + a.getLexeme() + " antes de declararla\n";
                    break;
                }
            }
            pilaParametros.pop();
        }

    }

    private void imprimirPila(Stack pila, String titulo, int espacios, String txt, String linea) {

        String cad = "";
        cad += txt + "\n" + linea + "\n| ";

        cad += espacios(titulo, espacios) + " |\n" + linea + "\n| ";
        int i = pila.size();
        while (!pila.empty()) {
            cad += espacios((i + " - " + ((Token) pila.pop()).getLexeme()), espacios) + " |\n" + linea + "\n| ";
            i--;
        }

        compilador.jTextAreaSemantico.setText(cad.substring(0, cad.length() - 2));

    }

    /**
     * Recibe una cadena y le agrega x cantidad de espacios al final
     *
     * @param cad
     * @param cantidad
     * @return
     */
    private String espacios(String cad, int cantidad) {

        if (cad.length() < cantidad) {
            while (cad.length() <= cantidad) {
                cad += " ";
            }
        }

        return cad;
    }

    /**
     * Retonna una cadena con la cantidad de espacios que quieres
     *
     * @param cantidadEspacios
     * @return
     */
    private String espacios(int cantidadEspacios) {
        String cad = "";

        for (int i = 0; i < cantidadEspacios; i++) {
            cad += " ";
        }

        return cad;
    }

    /**
     * Función que valida cuando queda un token en la pila pero se trata de una
     * funcion base. La función base es una función que no depende de ninguna
     * otra. EJEMPLO: Se puede declarar el menu sin obligatoriamente ver lo
     * despues.
     *
     * @param cad
     * @return true cuando el String enviado como parametro es una función base
     */
    private boolean validarEnLaPilaCasoBase(String cad) {
        return true;
    }

    private void guardarTokensEnLaPila(Stack pila, ArrayList<Token> parametrosDeFunciones) {
        for (Token token : parametrosDeFunciones) {
            pila.add(token);
        }
    }

    private void guardarPilaParametro(Token token) {

        pilaAuxParametrosIden.push(token);

        for (Iterator<Token> it = tokens.iterator(); it.hasNext();) {
            Token t = it.next();

            if (t.getLexicalComp().equals(token.getLexicalComp())) {

                while (!t.getLexicalComp().equals("finlinea")) {
                    if (t.getLexicalComp().equals("Identificadores") || t.getLexicalComp().equals("Cadenas") || t.getLexicalComp().equals("dinero") || t.getLexicalComp().equals("numero") || t.getLexicalComp().equals("tipo")) {
                        pilaParametros.add(t);

                        if (t.getLexicalComp().equals("Identificadores")) {
                            pilaAuxParametrosIden.push(t);
                        }

                    }
                    t = it.next();
                }
            }
        }

    }

    /**
     * CONTROL DE FLUJO Función que verifica si el token a ingresar en la pila
     * cumple con un orden logico Ejemplo: vermenu no se puede ingresar en la
     * pila a menos que declararmenu exista en la pila
     *
     * @param token
     * @return true si existe orden logico, false en caso contrario.
     */
    private void validarOrdenDeFunciones(Token token) {

        switch (token.getLexicalComp()) {
            case "vermenu":
                //Insertar vermenu
                pilaTokensFunciones.push(token);
                //Guardar parametros en la pila de: parametrosDeFunciones
                guardarPilaParametro(token);
                //Ver si se encuentra declararmenu
                if (buscarEnLaPila("declararmenu", this.pilaTokensFunciones)) {
                    //Si encuentra solicitarmesero aun no elimina nada
                    if (buscarTokenProximo(this.tokens, "solicitarmesero")) {
                        break;
                    }
                    //Eliminar vermenu
                    pilaTokensFunciones.pop();
                    //Eliminar declararmenu
                    eliminarEnLaPila("declararmenu", this.pilaTokensFunciones);
                } else {
                    erroresSemanticos += mensajeErrorSemantico(token, "vermenu", "declararmenu");
                }
                break;
            case "declararmenu":
                pilaTokensFunciones.add(token);
                //Guardar parametros en la pila de: parametrosDeFunciones
                guardarPilaParametro(token);
                break;
            case "prepmesa":
                pilaTokensFunciones.add(token);
                //Guardar parametros en la pila de: parametrosDeFunciones
                guardarPilaParametro(token);
                break;
            case "vermesa":
                //Insertar vermesas
                pilaTokensFunciones.push(token);
                //Guardar parametros en la pila de: parametrosDeFunciones
                guardarPilaParametro(token);
                //Ver si se encuentra prepmesa
                if (buscarEnLaPila("prepmesa", this.pilaTokensFunciones)) {

                    //Si encuentra iluminarcamino aun no elimina nada
                    if (buscarTokenProximo(this.tokens, "iluminarcamino") || buscarTokenProximo(this.tokens, "solicitarmesero")) {
                        break;
                    }

                    //Eliminar vermesas
                    pilaTokensFunciones.pop();
                    //Eliminar declararmesas
                    eliminarEnLaPila("prepmesa", this.pilaTokensFunciones);
                } else {
                    erroresSemanticos += mensajeErrorSemantico(token, "vermesas", "prepmesas");
                }
                break;
            case "iluminarcamino":
                pilaTokensFunciones.add(token);
                //Guardar parametros en la pila de: parametrosDeFunciones
                guardarPilaParametro(token);
                //Ver si se encuentra prepmesa
                if (buscarEnLaPila("vermesa", this.pilaTokensFunciones) && buscarEnLaPila("prepmesa", this.pilaTokensFunciones)) {

                    //Si encuentra solicitarmesero aun no elimina nada
                    if (buscarTokenProximo(this.tokens, "solicitarmesero")) {
                        break;
                    }

                    //Eliminar iluminarcamino
                    pilaTokensFunciones.pop();
                    //Eliminar vermesa
                    eliminarEnLaPila("vermesa", this.pilaTokensFunciones);
                    //Eliminar prepmesa
                    eliminarEnLaPila("prepmesa", this.pilaTokensFunciones);
                } else {
                    erroresSemanticos += mensajeErrorSemantico(token, "iluminarcamino", "vermesas");
                }
                break;
            case "solicitarmesero":
                pilaTokensFunciones.add(token);
                //Guardar parametros en la pila de: parametrosDeFunciones
                guardarPilaParametro(token);
                //Ver si se encuentra prepmesa
                if (buscarEnLaPila("prepmesa", this.pilaTokensFunciones) && buscarEnLaPila("declararmenu", this.pilaTokensFunciones)) {
                    //Eliminar solicitarmesero
                    pilaTokensFunciones.pop();
                    //Eliminar iluminarcamino
                    if (buscarEnLaPila("iluminarcamino", this.pilaTokensFunciones)) {
                        eliminarEnLaPila("iluminarcamino", this.pilaTokensFunciones);
                    }
                    //Eliminar declararmesas
                    if (buscarEnLaPila("vermesa", this.pilaTokensFunciones)) {
                        eliminarEnLaPila("vermesa", this.pilaTokensFunciones);
                    }
                    //Eliminar declararmesas
                    eliminarEnLaPila("declararmenu", this.pilaTokensFunciones);
                    eliminarEnLaPila("prepmesa", this.pilaTokensFunciones);
                    eliminarEnLaPila("vermenu", this.pilaTokensFunciones);
                } else {
                    if (!buscarEnLaPila("prepmesa", this.pilaTokensFunciones)) {
                        erroresSemanticos += mensajeErrorSemantico(token, "solicitarmesero", "prepmesa");
                    }
                    if (!buscarEnLaPila("declararmenu", this.pilaTokensFunciones)) {
                        erroresSemanticos += mensajeErrorSemantico(token, "solicitarmesero", "declararmenu");
                    }
                }
                break;
        }
    }

    private String mensajeErrorSemantico(Token tok, String token1, String token2) {
        return "ERROR EN: [ " + tok.getLine() + ", " + tok.getColumn() + " ]; Se intento [" + token1 + "] sin antes [" + token2 + "] primero; Falta función [" + token2 + "] antes de [" + token1 + "]\n";
    }

    /**
     * Recibe como parametro el componente lexico que se quiere buscar y en cual
     * pila
     *
     * @param cad
     * @return true si lo encuentra, en cualquier otro caso false.
     */
    private boolean buscarEnLaPila(String cad, Stack pila) {
        Stack pilaAux = new Stack();
        pilaAux.addAll(pila);
        while (!pilaAux.empty()) {
            if (((Token) pilaAux.pop()).getLexicalComp().equals(cad)) {
                return true;
            }
        }
        return false;
    }

    private boolean buscarEnLaPila(List lista, Stack pila) {
        Stack pilaAux = new Stack();
        pilaAux.addAll(pila);
        /*
        List listAux1 = new ArrayList<>();
        List listAux2 = new ArrayList<>();
        int i = 0;
        
        //for (int i = 0; i < lista.size(); i++) {
        while (!pilaAux.empty() && i != lista.size() - 1) {
            if (((Token) pilaAux.peek()).getLexicalComp().equals(lista.get(i))) {
                pilaAux.pop();
                i = 0;
            }
            i++;
        }
        //}
         */
        while (!pila.empty()) {
            if ((((Token) pila.peek()).getLexicalComp().equals("declararmenu") || ((Token) pila.peek()).getLexicalComp().equals("prepmesa"))) {
                pila.pop();
            } else {
                break;
            }
        }
        
        return pila.empty();

    }

    private void eliminarEnLaPila(String cad, Stack pila) {
        Stack pilaAux = new Stack();
        while (!pila.empty()) {
            if (((Token) pila.peek()).getLexicalComp().equals(cad)) {
                pila.pop();
                break;
            } else {
                pilaAux.add(pila.pop());
            }
        }

        while (!pilaAux.empty()) {
            pila.add(pilaAux.pop());
        }
    }

    private void eliminarEnLaPilaTodosLos(String cad, Stack pila) {
        Stack pilaAux = new Stack();
        while (!pila.empty()) {
            if (((Token) pila.peek()).getLexicalComp().equals(cad)) {
                pila.pop();
            } else {
                pilaAux.add(pila.pop());
            }
        }

        while (!pilaAux.empty()) {
            pila.add(pilaAux.pop());
        }
    }

    /**
     *
     * @param errores
     * @return true si hay texto en la cadena enviada como parametro, false si
     * la cadena esta vacia.
     */
    private boolean hayErrores(String errores) {
        return errores.isBlank();
    }

    private void revisarIdentificadoresDeVariables() {

        ArrayList<Token> tokensAuxiliar = new ArrayList<>();
        Token tAux;
        for (Iterator<Token> it = tokens.iterator(); it.hasNext();) {
            Token token = it.next();

            if (token.getLexicalComp().equals("variable")) {
                token = it.next();
                //Primero preguntar si el token es un identificador
                if (token.getLexicalComp().equals("Identificadores")) {

                    //Buscar si ya existe este identificador
                    tAux = buscarDuplicado(tokensAuxiliar, token);

                    //si esta vacio entonces significa que no esta duplicado
                    //Por lo que se guarda el identificadoe en tokensAuxiliar
                    //Si esta duplicado entonces
                    //envia por consola el identificador "original", el duplicado y su renglon.
                    if (!(tAux == null)) {
                        erroresSemanticos += "IDENTIFICADOR DUPLICADO!\n";
                        erroresSemanticos += "Identificador original: " + tAux.getLexeme() + " en la linea: " + tAux.getLine() + "\n";
                        erroresSemanticos += "Identificador duplicado: " + token.getLexeme() + " en la linea: " + token.getLine() + "\n";
                        //break;
                    } else {
                        tokensAuxiliar.add(token);
                        arregloTokensVar.add(token);
                    }
                }
            }

        }

        //validarParametrosDeFunciones(tokensAuxiliar);
    }

    private void revisarPrint() {
        for (Iterator<Token> it = tokens.iterator(); it.hasNext();) {
            Token token = it.next();

            if (token.getLexicalComp().equals("pantalla")) {
                token = it.next();
                token = it.next();
                //Primero preguntar si el token es un identificador
                if (token.getLexicalComp().equals("Identificadores")) {

                    //Buscar la primera declaracion del identificador
                    //System.out.println(tokens.get(tokens.indexOf(token)).getLexeme() + " En la linea: " + tokens.get(tokens.indexOf(token)).getLine());
                    //Preguntar el tipo de dato de ese identificador
                    //Si no es de tipo string entonces enviamos por pantalla un mensaje de error
                    if (!token.getLexeme().equals("string")) {
                        erroresSemanticos += "IDENTIFICADOR TIPO DE DATO INCORRECTO!\n";
                        erroresSemanticos += "El identificador: " + token.getLexeme() + " en la linea: " + token.getLine() + ", deberia ser de tipo string pero esta declarado como: \n";
                    }
                }
            }
        }
    }

    private void revisarMenu() {
        Stack pila = new Stack();

        for (Iterator<Token> it = tokens.iterator(); it.hasNext();) {
            Token token = it.next();

            if (token.getLexicalComp().equals("declararmenu")) {
                while (token.getLexicalComp() != "finlinea") {
                    pila.push(token.getLexicalComp());
                }
            }
        }
    }

    /**
     * *
     * Buscar un token en un arreglo en especifico, si lo encuentra retorna true
     * caso contrario false
     *
     * @param tokensAuxiliar
     * @param token
     * @return
     */
    private boolean buscarToken(ArrayList<Token> tokensAuxiliar, Token token) {
        for (int i = 0; i < tokensAuxiliar.size(); i++) {
            if (tokensAuxiliar.get(i).getLexeme().equals(token.getLexeme())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param tokensAuxiliar
     * @param token
     * @return el token que esta duplicado en tokensAuxiliar con respecto al
     * token enviado
     */
    private Token buscarDuplicado(ArrayList<Token> tokensAuxiliar, Token token) {
        for (int i = 0; i < tokensAuxiliar.size(); i++) {
            if (tokensAuxiliar.get(i).getLexeme().equals(token.getLexeme())) {
                return tokensAuxiliar.get(i);
            }
        }
        return null;
    }

    /**
     * Funcion que busca un lexema en un arraylist
     *
     * @param tokens
     * @param token
     * @return true si lo encuentra, false en otro caso.
     */
    private boolean buscarTokenProximo(ArrayList<Token> tokens, String lexema) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getLexeme().equals(lexema)) {
                return true;
            }
        }
        return false;
    }
}
