import compilerTools.Token;

%%
%class Lexer
%type Token
%line
%column
%{
    private Token token(String lexeme, String lexicalComp, int line, int column){
        return new Token(lexeme, lexicalComp, line+1, column+1);
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
{Comentario} { /* Ignorar */ }

{EspacioEnBlanco} { /* Ignorar */ }

int |
string |
float |
boolean |
{Identificador}"["{Identificador}*"]" { return token(yytext(), "tipoDato", yyline, yycolumn); }

[1-9][0-9]* { return token(yytext(), "numero", yyline, yycolumn); }

"$"([0-9]|[0-9]*) { return token(yytext(), "dinero", yyline, yycolumn); } 

/* iluminar camino */
iluminarcamino { return token(yytext(), "iluminarcamino", yyline, yycolumn); }

declararmenu { return token(yytext(), "declararmenu", yyline, yycolumn); }

verofertas { return token(yytext(), "verofertas", yyline, yycolumn); }

vermenu { return token(yytext(), "vermenu", yyline, yycolumn); }

realizarpedido { return token(yytext(), "realizarpedido", yyline, yycolumn); }

rotarmenu { return token(yytext(), "rotarmenu", yyline, yycolumn); }

solicitarmesero { return token(yytext(), "solicitarmesero", yyline, yycolumn); }

vermesa { return token(yytext(), "vermesa", yyline, yycolumn); }

/* estadomesas */
estadomesas { return token(yytext(), "estadomesas", yyline, yycolumn); }

/* tiempopedido */
tiempopedido { return token(yytext(), "tiempopedido", yyline, yycolumn); }

/* Signo Aritmetico */
"+" |
"-" |
"*" |
"/" { return token(yytext(), "SignoAritmetico", yyline, yycolumn); }

for { return token(yytext(), "for", yyline, yycolumn); }

"<" |
">" |
"<=" |
">=" { return token(yytext(), "operadorLogico", yyline, yycolumn); }

while { return token(yytext(), "while", yyline, yycolumn); }

if { return token(yytext(), "if", yyline, yycolumn); }

var { return token(yytext(), "variable", yyline, yycolumn); }

";" { return token(yytext(), "finlinea", yyline, yycolumn); }

print { return token(yytext(), "pantalla", yyline, yycolumn); }

horaDelDia { return token(yytext(), "hDia", yyline, yycolumn); }

tipoComida { return token(yytext(), "tcomida", yyline, yycolumn); }

numeroMesa { return token(yytext(), "numeroMesa", yyline, yycolumn); }

pedido { return token(yytext(), "pedido", yyline, yycolumn); }

"=" { return token(yytext(), "asignacion", yyline, yycolumn); }

main { return token(yytext(), "inicio", yyline, yycolumn); }

prepararmesa { return token(yytext(), "prepmesa", yyline, yycolumn); }

numeroAsientos { return token(yytext(), "numasientos", yyline, yycolumn); }

"'"{cadena}"'" { return token(yytext(), "Cadenas", yyline, yycolumn); }

"(" { return token(yytext(), "parentecisA", yyline, yycolumn); }

")" { return token(yytext(), "parentecisC", yyline, yycolumn); }

"{" { return token(yytext(), "CorcheteA", yyline, yycolumn); }

"}" { return token(yytext(), "CorcheteC", yyline, yycolumn); }

"," { return token(yytext(), "coma", yyline, yycolumn); }

"};" { return token(yytext(), "fin", yyline, yycolumn);  }

estado { return token(yytext(), "estado", yyline, yycolumn);  }

vegetariano |
regular |
pesqueteriano { return token(yytext(), "tipo", yyline, yycolumn); }

true |
false { return token(yytext(), "booleano", yyline, yycolumn);  }

mañana |
tarde |
noche { return token(yytext(), "tiempo", yyline, yycolumn); }

{Identificador} { return token(yytext(), "Identificadores", yyline, yycolumn); }

[A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü][A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]* { return token(yytext(), "ERROR_LEX", yyline, yycolumn); }

. { return token(yytext(), "ERROR_LEX", yyline, yycolumn); }