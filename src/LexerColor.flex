import compilerTools.TextColor;
import java.awt.Color;

%%
%class LexerColor
%type TextColor
%char
%{
    private TextColor textColor(long start, int size, Color color){
        return new TextColor((int) start, size, color);
    }
%}
/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
ContenidoComentario = ( [^*] | \*+ [^/*] )*
ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"

/* Comentario */
Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Identificador */
Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*

/* cadenas */
Espacio = [ ]
cadena = {Letra}({Letra}|{Digito}|{Espacio})*

/* Número */
Numero = 0 | [1-9][0-9]*
%%

/* Comentarios o espacios en blanco */
{Comentario} { return textColor(yychar, yylength(), new Color(146, 146, 146)); }

{EspacioEnBlanco} { /*Ignorar*/ }

int |
string |
float |
boolean |
{Identificador}"["{Identificador}*"]" { return textColor(yychar, yylength(), new Color(0, 0, 255)); }

[1-9][0-9]* { return textColor(yychar, yylength(), new Color(57, 130, 130)); }

"$"([0-9]|[0-9]|[0-9]*) { return textColor(yychar, yylength(), new Color(57, 130, 130)); } 

/* iluminar camino */
iluminarcamino { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

declararmenu { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

verofertas { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

vermenu { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

realizarpedido { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

rotarmenu { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

solicitarmesero { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

vermesa { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

/* estadomesas */
estadomesas { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

/* tiempopedido */
tiempopedido { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

/* Signo Aritmetico */
"+" |
"-" |
"*" |
"/" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

for { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

"<" |
">" |
"<=" |
">=" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

while { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

if { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

var { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

";" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

print { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

horaDelDia { return textColor(yychar, yylength(), new Color(110, 32, 189)); }

tipoComida { return textColor(yychar, yylength(), new Color(110, 32, 189)); }

numeroMesa { return textColor(yychar, yylength(), new Color(110, 32, 189)); }

pedido { return textColor(yychar, yylength(), new Color(110, 32, 189)); }

"=" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

main { return textColor(yychar, yylength(), new Color(153, 0, 76)); }

prepararmesa { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

numeroAsientos { return textColor(yychar, yylength(), new Color(110, 32, 189)); }

"'"{cadena}"'" { return textColor(yychar, yylength(), new Color(255, 128, 0)); }

"(" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

")" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

"{" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

"}" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

"," { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

"};" { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

estado { return textColor(yychar, yylength(), new Color(110, 32, 189)); }

vegetariano |
regular |
pesqueteriano { return textColor(yychar, yylength(), new Color(74, 185, 35)); }

true |
false { return textColor(yychar, yylength(), new Color(35, 65, 185)); }

mañana |
tarde |
noche { return textColor(yychar, yylength(), new Color(35, 65, 185)); }

{Identificador} { return textColor(yychar, yylength(), new Color(0, 143, 57)); }

[A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü][A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]* { return textColor(yychar, yylength(), new Color(255, 0, 0)); }

. { return textColor(yychar, yylength(), new Color(255, 0, 0)); }