
import compilerTools.Token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author cesar
 */
public class ArbolesDeExpresion {

    private ArrayList<Token> tokens = new ArrayList<>();
    private Compilador compilador;

    public ArbolesDeExpresion(ArrayList<Token> tokens, Compilador compilador) {
        this.tokens = tokens;
        buscarOperacion();
        this.compilador = compilador;
    }

    private List operacion() {

        List expresionInfija = new ArrayList();

        int i = 4;
        boolean algunError = false;

        while (!tokens.get(i).getLexicalComp().equals("pantalla")) {
            if (tokens.size() == i) {
                break;
            }
            i++;
        }
        i++;
        if (tokens.get(i).getLexicalComp().equals("parentecisA") && algunError == false) {
            //expresionInfija.add(tokens.get(i).getLexeme().charAt(0));
            i++;
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero")) && algunError == false) {
            expresionInfija.add(tokens.get(i).getLexeme().charAt(0));
            i++;
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("operadorLogico") || tokens.get(i).getLexicalComp().equals("SignoAritmetico")) && algunError == false) {
            expresionInfija.add(tokens.get(i).getLexeme().charAt(0));
            i++;
        } else {
            algunError = true;
        }
        if ((tokens.get(i).getLexicalComp().equals("numero")) && algunError == false) {
            expresionInfija.add(tokens.get(i).getLexeme().charAt(0));
            i++;
        } else {
            algunError = true;
        }

        return expresionInfija;
    }

    private void buscarOperacion() {
        //NOTA: Solo funciona con numeros de un solo digito :c
        //List expresionInfija = Arrays.asList('5', '+', '5');

        //Limpia la consola de netbeans, para que solo se vea los resultados
        limpiarConsola();

        //operacion retorna una lista con la primera expresion infija (dos numeros con un operador en medio) que encuentra
        List expresionInfija = operacion();
        //La mostramos por consola
        System.out.println("Sufijo" + expresionInfija);

        //ConvertirPrefijo recibela expresion infija y retorna una lista con la notacion en prefijo
        //Internamente maneja stacks
        List notacionPrefijo = ConvertirPrefijo(expresionInfija);
        System.out.println("Prefijo" + notacionPrefijo);

        //EvaluarPrefijo recibe la operacion en notacion prefija y la evalua (resuelve)
        List resultado = EvaluarPrefijo(notacionPrefijo);
        System.out.println(resultado.toString());
    }

    /**
     * Funcion para convertir una expresion infija a prefija
     *
     * @param expresionInfija
     * @return un stack con la expresion infija convertida a prefija
     */
    public List ConvertirPrefijo(List expresionInfija) {
        Stack pilaDeOperadores = new Stack();
        Stack pilaDeSalida = new Stack();

        //Invertir la expresion infija
        List expresionInfijaInvertida = invertirExpresionInfija(expresionInfija);

        //Recorrer la expresion infija invertida para guardando los operandos y operadores en su
        //respectiva pila
        for (int i = 0; i < expresionInfijaInvertida.size(); i++) {
            //esOperador retorna true si el elemento de la pila que le enviamos es un operador
            if (esOperador(expresionInfijaInvertida.get(i))) {
                pilaDeOperadores.push(expresionInfijaInvertida.get(i));

                //Cuando detecta que hay un parentesis de apertura, lo elimina de la lista y
                //va vaciando los operadores que se encuentran en la pila de operadores en la pila de salida,
                //hasta que encuentra un parentesis de cierre. Ese ultimo lo elimina.
                if (pilaDeOperadores.peek().equals('(')) {
                    pilaDeOperadores.pop();
                    while (!pilaDeOperadores.peek().equals(')')) {
                        pilaDeSalida.push(pilaDeOperadores.pop());
                    }
                    //Elimina el parentesis de cierre
                    pilaDeOperadores.pop();
                }
                //Si no es un operador guardamos en la pila de salida        
            } else {
                pilaDeSalida.push(expresionInfijaInvertida.get(i));
            }
        }

        //Cuando terina de recorrer la expresionInfijaInvertida; vacia todo lo que se encuentra en
        //la pila de operadores en la de salida.
        while (!pilaDeOperadores.empty()) {
            pilaDeSalida.push(pilaDeOperadores.pop());
        }

        //Retorna una lista invertida con la expresion prefija
        //Ya que es mas facil de leer asi en la consola de netbeans
        return invertirExpresionInfija(pilaDeSalida);
    }

    /**
     * Funcion para invertir una lista
     *
     * @param expresionInfija
     * @return lista invertidad
     */
    private List invertirExpresionInfija(List expresionInfija) {
        Collections.reverse(expresionInfija);
        return expresionInfija;
    }

    /**
     * Funcion para determinar si un elemento de una lista es un operador
     * aritmetico o un parentesis * + / ( )
     *
     * @param expresionInfijaInvertida
     * @return true si es un operador aritmetico o un parentesis
     */
    private boolean esOperador(Object expresionInfijaInvertida) {

        List operadores = Arrays.asList('^', '*', '/', '+', '-', '(', ')');

        for (int i = 0; i < 7; i++) {
            if (expresionInfijaInvertida.equals(operadores.get(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Funcion para convertir una expresion infija a posfija
     *
     * @param expresionInfija
     * @return un stack con la expresion infija convertida a posfija
     */
    public List ConvertirPosfijo(List expresionInfija) {
        Stack pilaDeOperadores = new Stack();
        Stack pilaDeSalida = new Stack();

        for (int i = 0; i < expresionInfija.size(); i++) {
            if (esOperador(expresionInfija.get(i))) {
                pilaDeOperadores.push(expresionInfija.get(i));

                //Vacia todo lo que se encuentra entre parentesis de apertura y cierre y lo mueve
                //a la pila de salida.
                if (pilaDeOperadores.peek().equals(')')) {
                    pilaDeOperadores.pop();
                    while (!pilaDeOperadores.peek().equals('(')) {

                        pilaDeSalida.push(pilaDeOperadores.pop());
                    }
                    pilaDeOperadores.pop();
                }

            } else {
                pilaDeSalida.push(expresionInfija.get(i));
            }
        }

        while (!pilaDeOperadores.empty()) {
            pilaDeSalida.push(pilaDeOperadores.pop());
        }

        return pilaDeSalida;

    }

    /**
     * Evalua la precedencia de un operador
     *
     * @param pilaDeOperadores
     * @return el nivel de precedencia del operador enviado
     */
    private Object precedencia(Object operador) {
        switch (operador.toString()) {
            case "^":
                return 3;
            case "*":
                return 2;
            case "/":
                return 2;
            case "+":
                return 1;
            case "-":
                return 1;
            default:
                return 0;
        }

    }

    public List EvaluarPrefijo(List expresionPrefija) {
        Stack pilaDeSalida = new Stack();
        double a, b, c;

        //Invertir la expresion prefija porque recibe una lista no una pila
        //Si recibira una pila no tendria que hacer ese paso
        List expresionPrefijaInvertida = invertirExpresionInfija(expresionPrefija);

        //Realiza el recorrido de expresionPrefijaInvertida
        for (int i = 0; i < expresionPrefijaInvertida.size(); i++) {

            //Ingresa el primer elemento de la lista en la pila de salida
            pilaDeSalida.push(expresionPrefijaInvertida.get(i));

            //Cuando encuentra un operador, realiza la operacion correspondiente
            //con los dos operandos proximos en la pila de salida.
            //Guarda el resultado en la pila de salida.
            switch (expresionPrefijaInvertida.get(i).toString()) {
                case "/":
                    pilaDeSalida.pop();
                    //Realizar operacion
                    a = Double.parseDouble(pilaDeSalida.pop().toString());
                    b = Double.parseDouble(pilaDeSalida.pop().toString());

                    c = a / b;

                    pilaDeSalida.push(c);

                    break;

                case "*":
                    pilaDeSalida.pop();
                    //Realizar operacion
                    a = Double.parseDouble(pilaDeSalida.pop().toString());
                    b = Double.parseDouble(pilaDeSalida.pop().toString());

                    c = a * b;

                    pilaDeSalida.push(c);

                    break;
                case "-":
                    pilaDeSalida.pop();
                    //Realizar operacion
                    a = Double.parseDouble(pilaDeSalida.pop().toString());
                    b = Double.parseDouble(pilaDeSalida.pop().toString());

                    c = a - b;

                    pilaDeSalida.push(c);

                    break;
                case "+":
                    pilaDeSalida.pop();

                    //Realizar operacion
                    a = Double.parseDouble(pilaDeSalida.pop().toString());
                    b = Double.parseDouble(pilaDeSalida.pop().toString());

                    c = a + b;

                    pilaDeSalida.push(c);

                    break;
            }

        }

        //Retorna la pila de salida pero como lista
        return pilaDeSalida;

    }

    private void limpiarConsola() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

}
