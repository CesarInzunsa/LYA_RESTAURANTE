
import compilerTools.Token;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public class GeneradorCodigoObjeto {

    private ArrayList<Token> tokens = new ArrayList<>();
    private Compilador compilador;
    private String erroresObjeto = "";
    private String erroresOptimizador = "";
    private String objeto = "";

    GeneradorCodigoObjeto(ArrayList<Token> tokens, Compilador compilador, String erroresOptimizador) {
        this.tokens = tokens;
        this.compilador = compilador;
        this.erroresOptimizador = erroresOptimizador;
    }

    public void realizarCodigoObjeto() {
        //Funcion que comprueba si hay errores, si los hay retorna un true
        //y evita que el optimizador de codigo se ejecute
        /*
        if (hayErrores(erroresOptimizador)) {
            compilador.setTxtOutputConsole(compilador.getTxtOutputConsole() + "\nERROR! Existen errores semanticos, por lo que no se realizo la generacion del codigo intermedio.\n");
            return;
        }
         */

        //Funcion que genera el codigo objeto
        generarObjeto();

        String txt = compilador.txtOutputConsole.getText();
        if (erroresObjeto.isBlank()) {
            compilador.jTextCodigoObjeto.setText(objeto);
            //compilador.txtOutputConsole.setText(txt + "\nOptimizador de codigo: Sin errores");
        } else {
            compilador.txtOutputConsole.setText(txt + "Generador de codigo objeto:\n" + erroresObjeto);
        }

    }

    private void generarObjeto() {
        //Funcion que agrega las librerias y arreglos globales.
        objeto += lib_varGlobales();
        objeto += arreglosGlobales(); //la funcion declararmenu se utiliza aqui
        objeto += setUp();
        objeto += pines();
        objeto += verMesa();
        objeto += iluminarCamino();
        objeto += verMenu();
        //objeto += realizarPedido();
        objeto += solicitarMesero();

        objeto += loop(); //no se usa pero el arduino lo ocupa
        objeto += verMenuFuncion();
        objeto += verMesaFuncion();
        objeto += iluminarCaminoFuncion();
        objeto += solicitarMeseroFuncion();
    }

    private String pines() {
        return "pinMode(ldmesa1,OUTPUT);pinMode(ldmesa2,OUTPUT);pinMode(ldmesa3,OUTPUT);pinMode(ldmesa4,OUTPUT);pinMode(ldmesalow,OUTPUT);pinMode(ldmesaalt, OUTPUT);\n";
    }

    private String setUp() {
        return "void setup() {\n";
    }

    private String loop() {
        String cad = "void loop() {\n}\n";

        return cad;
    }

    private String arreglosGlobales() {

        String PlatillosVegMan = "String PlatillosVegMan[]={";
        String PreciosVegMan = "String PreciosVegMan[]={";

        String PlatillosVegTar = "String PlatillosVegTar[]={";
        String PreciosVegTar = "String PreciosVegTar[]={";

        String PlatillosVegNoc = "String PlatillosVegNoc[]={";
        String PreciosVegNoc = "String PreciosVegNoc[]={";

        //pesqueteriano
        String PlatillosPesMan = "String PlatillosPesMan[]={";
        String PreciosPesMan = "String PreciosPesMan[]={";

        String PlatillosPesTar = "String PlatillosPesTar[]={";
        String PreciosPesTar = "String PreciosPesTar[]={";

        String PlatillosPesNoc = "String PlatillosPesNoc[]={";
        String PreciosPesNoc = "String PreciosPesNoc[]={";

        //regular
        String PlatillosRegMan = "String PlatillosRegMan[]={";
        String PreciosRegMan = "String PreciosRegMan[]={";

        String PlatillosRegTar = "String PlatillosRegTar[]={";
        String PreciosRegTar = "String PreciosRegTar[]={";

        String PlatillosRegNoc = "String PlatillosRegNoc[]={";
        String PreciosRegNoc = "String PreciosRegNoc[]={";

        for (int i = 0; i < compilador.jTableCodigoOptimizado.getRowCount(); i++) {

            String res = compilador.jTableCodigoOptimizado.getValueAt(i, 4).toString(); //resultado
            String arg1 = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString(); //arg1
            String arg2 = compilador.jTableCodigoOptimizado.getValueAt(i, 3).toString(); //arg2

            if (res.equals("declararmenu") && !arg1.isBlank() && !arg2.isBlank() && !arg1.substring(0, 1).equals("$")) {
                String tipo = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 3).toString();
                String horario = compilador.jTableCodigoOptimizado.getValueAt(i + 2, 2).toString();

                if (tipo.equals("vegetariano") && horario.equals("mañana")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosVegMan += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosVegMan += "\"" + precio + "\",";
                }
                if (tipo.equals("vegetariano") && horario.equals("tarde")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosVegTar += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosVegTar += "\"" + precio + "\",";
                }
                if (tipo.equals("vegetariano") && horario.equals("noche")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosVegNoc += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosVegNoc += "\"" + precio + "\",";
                }

                if (tipo.equals("pesqueteriano") && horario.equals("mañana")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosPesMan += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosPesMan += "\"" + precio + "\",";
                }
                if (tipo.equals("pesqueteriano") && horario.equals("tarde")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosPesTar += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosPesTar += "\"" + precio + "\",";
                }
                if (tipo.equals("pesqueteriano") && horario.equals("noche")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosPesNoc += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosPesNoc += "\"" + precio + "\",";
                }

                if (tipo.equals("regular") && horario.equals("mañana")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosRegMan += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosRegMan += "\"" + precio + "\",";
                }
                if (tipo.equals("regular") && horario.equals("tarde")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosRegTar += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosRegTar += "\"" + precio + "\",";
                }
                if (tipo.equals("regular") && horario.equals("noche")) {
                    String nombre = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString();
                    String precio = compilador.jTableCodigoOptimizado.getValueAt(i + 1, 2).toString();

                    PlatillosRegNoc += "\"" + nombre.substring(1, nombre.length() - 1) + "\",";
                    PreciosRegNoc += "\"" + precio + "\",";
                }
            }
        }

        String vegMan = PlatillosVegMan.substring(0, PlatillosVegMan.length() - 1) + "};\n" + PreciosVegMan.substring(0, PreciosVegMan.length() - 1) + "};\n";
        String vegTar = PlatillosVegTar.substring(0, PlatillosVegTar.length() - 1) + "};\n" + PreciosVegTar.substring(0, PreciosVegTar.length() - 1) + "};\n";
        String vegNoc = PlatillosVegNoc.substring(0, PlatillosVegNoc.length() - 1) + "};\n" + PreciosVegNoc.substring(0, PreciosVegNoc.length() - 1) + "};\n";

        String pesMan = PlatillosPesMan.substring(0, PlatillosPesMan.length() - 1) + "};\n" + PreciosPesMan.substring(0, PreciosPesMan.length() - 1) + "};\n";
        String pesTar = PlatillosPesTar.substring(0, PlatillosPesTar.length() - 1) + "};\n" + PreciosPesTar.substring(0, PreciosPesTar.length() - 1) + "};\n";
        String pesNoc = PlatillosPesNoc.substring(0, PlatillosPesNoc.length() - 1) + "};\n" + PreciosPesNoc.substring(0, PreciosPesNoc.length() - 1) + "};\n";

        String regMan = PlatillosRegMan.substring(0, PlatillosRegMan.length() - 1) + "};\n" + PreciosRegMan.substring(0, PreciosRegMan.length() - 1) + "};\n";
        String regTar = PlatillosRegTar.substring(0, PlatillosRegTar.length() - 1) + "};\n" + PreciosRegTar.substring(0, PreciosRegTar.length() - 1) + "};\n";
        String regNoc = PlatillosRegNoc.substring(0, PlatillosRegNoc.length() - 1) + "};\n" + PreciosRegNoc.substring(0, PreciosRegNoc.length() - 1) + "};\n";

        return vegMan + vegTar + vegNoc + pesMan + pesTar + pesNoc + regMan + regTar + regNoc;
    }

    private String lib_varGlobales() {
        String cad1 = "#include <LiquidCrystal.h>\n";
        String cad2 = "LiquidCrystal lcd(7, 6, 5, 4, 3, 2);\n";
        String cad3 = "const int ldmesalow=8, ldmesaalt=9, ldmesa1 = 10, ldmesa2= 11, ldmesa3=13, ldmesa4=12;\n";
        return cad1 + cad2 + cad3;
    }

    private String verMenu() {
        String cad = "";
        for (int i = 0; i < compilador.jTableCodigoOptimizado.getRowCount(); i++) {

            String res = compilador.jTableCodigoOptimizado.getValueAt(i, 4).toString(); //resultado

            if (res.equals("vermenu")) {
                String arg1 = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString(); //arg1
                String arg2 = compilador.jTableCodigoOptimizado.getValueAt(i, 3).toString(); //arg2

                cad += "vermenu(\"" + arg1 + "\"," + "\"" + arg2 + "\");\n";
            }
        }

        return cad;
    }

    private String verMesa() {
        String cad = "";
        for (int i = 0; i < compilador.jTableCodigoOptimizado.getRowCount(); i++) {

            String res = compilador.jTableCodigoOptimizado.getValueAt(i, 4).toString(); //resultado

            if (res.equals("vermesa")) {
                String arg1 = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString(); //arg1

                cad += "verMesa(" + arg1 + ");\n";
            }
        }

        return cad;
    }

    private String iluminarCamino() {
        String cad = "";
        for (int i = 0; i < compilador.jTableCodigoOptimizado.getRowCount(); i++) {

            String res = compilador.jTableCodigoOptimizado.getValueAt(i, 4).toString(); //resultado

            if (res.equals("iluminarcamino")) {
                String arg1 = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString(); //arg1

                cad += "iluminarcamino(" + arg1 + ");\n";
            }
        }

        return cad;
    }

    private String solicitarMesero() {
        String cad = "";
        for (int i = 0; i < compilador.jTableCodigoOptimizado.getRowCount(); i++) {

            String res = compilador.jTableCodigoOptimizado.getValueAt(i, 4).toString(); //resultado

            if (res.equals("solicitarmesero")) {
                String arg1 = compilador.jTableCodigoOptimizado.getValueAt(i, 2).toString(); //arg1

                cad += "solicitarmesero(" + arg1 + ");\n";
            }
        }

        return cad;
    }

    private String solicitarMeseroFuncion() {
        String cad = "void solicitarmesero (int mesa){\n"
                + "  switch(mesa){\n"
                + "    case 1: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesa1, HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa1, LOW);\n"
                + "      delay(1000);}break;\n"
                + "    case 2: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesa2, HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa2, LOW);\n"
                + "      delay(1000);}break;\n"
                + "    case 3: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesa3, HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa3, LOW);\n"
                + "      delay(1000);}break;  \n"
                + "    case 4: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesa4, HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa4, LOW);\n"
                + "      delay(1000);}break;  \n"
                + "  }\n"
                + "}";

        return cad + "\n";
    }

    private String iluminarCaminoFuncion() {
        String cad = "void iluminarcamino(int mesa){\n"
                + "  switch(mesa){\n"
                + "    case 1: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesalow, HIGH);\n"
                + "      digitalWrite(ldmesa1,HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa1, LOW);\n"
                + "      digitalWrite(ldmesalow, LOW);\n"
                + "      delay(1000);}break;\n"
                + "    case 2: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesalow, HIGH);\n"
                + "      digitalWrite(ldmesa2,HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa2, LOW);\n"
                + "      digitalWrite(ldmesalow, LOW);\n"
                + "      delay(1000);}break;\n"
                + "    case 3: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesalow, HIGH);\n"
                + "      digitalWrite(ldmesa3,HIGH);\n"
                + "      digitalWrite(ldmesaalt,HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa3, LOW);\n"
                + "      digitalWrite(ldmesalow, LOW);\n"
                + "      digitalWrite(ldmesaalt,LOW);\n"
                + "      delay(1000);}break; \n"
                + "    case 4: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesalow, HIGH);\n"
                + "      digitalWrite(ldmesa4,HIGH);\n"
                + "      digitalWrite(ldmesaalt,HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa4, LOW);\n"
                + "      digitalWrite(ldmesalow, LOW);\n"
                + "      digitalWrite(ldmesaalt,LOW);\n"
                + "      delay(1000);}break; \n"
                + "  }  \n"
                + "}";

        return cad + "\n";
    }

    private String verMesaFuncion() {
        String cad = "void verMesa(int mesa){\n"
                + "  switch(mesa){\n"
                + "    case 1: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesa1, HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa1, LOW);\n"
                + "      delay(1000);}break;\n"
                + "    case 2: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesa2, HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa2, LOW);\n"
                + "      delay(1000);}break;\n"
                + "    case 3: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesa3, HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa3, LOW);\n"
                + "      delay(1000);}break;  \n"
                + "    case 4: \n"
                + "      for(int i=0; i<3; i++){\n"
                + "      digitalWrite(ldmesa4, HIGH);\n"
                + "      delay(1000);\n"
                + "      digitalWrite(ldmesa4, LOW);\n"
                + "      delay(1000);}break;  \n"
                + "  }\n"
                + "}\n";

        return cad;
    }

    private String verMenuFuncion() {
        String cad1 = "void vermenu (String menu,String tiempo){\n"
                + "  lcd.begin(16, 2);	\n"
                + "  if((menu==\"vegetariano\")&&(tiempo==\"mañana\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosVegMan)/sizeof(PlatillosVegMan[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosVegMan[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosVegMan[i]); \n"
                + "    delay(3000);lcd.clear();\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "  else if((menu==\"vegetariano\")&&(tiempo==\"tarde\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosVegTar)/sizeof(PlatillosVegTar[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosVegTar[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosVegTar[i]); \n"
                + "    delay(3000);\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "  else if((menu==\"vegetariano\")&&(tiempo==\"noche\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosVegNoc)/sizeof(PlatillosVegNoc[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosVegNoc[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosVegNoc[i]); \n"
                + "    delay(3000);\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "  else if((menu==\"regular\")&&(tiempo==\"mañana\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosRegMan)/sizeof(PlatillosRegMan[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosRegMan[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosRegMan[i]); \n"
                + "    delay(3000);\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "  else if((menu==\"regular\")&&(tiempo==\"tarde\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosRegTar)/sizeof(PlatillosRegTar[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosRegTar[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosRegTar[i]); \n"
                + "    delay(3000);\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "  else if((menu==\"regular\")&&(tiempo==\"noche\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosRegNoc)/sizeof(PlatillosRegNoc[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosRegNoc[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosRegNoc[i]); \n"
                + "    delay(3000);\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "  else if((menu==\"pesqueteriano\")&&(tiempo==\"mañana\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosPesMan)/sizeof(PlatillosPesMan[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosPesMan[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosPesMan[i]); \n"
                + "    delay(3000);\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "  else if((menu==\"pesqueteriano\")&&(tiempo==\"tarde\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosPesTar)/sizeof(PlatillosPesTar[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosPesTar[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosPesTar[i]); \n"
                + "    delay(3000);\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "  else if((menu==\"pesqueteriano\")&&(tiempo==\"noche\")){\n"
                + "    int i=0;\n"
                + "    int nelements=(sizeof(PlatillosPesNoc)/sizeof(PlatillosPesNoc[0]));\n"
                + "    do{\n"
                + "    lcd.clear();\n"
                + "    lcd.setCursor(0, 0);\n"
                + "    lcd.print(PlatillosPesNoc[i]);\n"
                + "    lcd.setCursor(0, 1);\n"
                + "    lcd.print(PreciosPesNoc[i]); \n"
                + "    delay(3000);\n"
                + "    i++;}while(i<nelements);\n"
                + "    lcd.clear();\n"
                + "  }\n"
                + "}\n";

        return cad1;
    }

}
