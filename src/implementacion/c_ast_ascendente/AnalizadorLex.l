package c_ast_ascendente;

%%
%cup
%line
%column
%class AnalizadorLex
%unicode
%public

%{
	private ALexOperations ops;
	private GestionErrores errores;
	public String lexema() {return yytext();}
	public int fila() {return yyline+1;}
	public int col()  {return yycolumn+1;}
	public void fijaGestionErrores(GestionErrores errores) {
		this.errores = errores;
	}
%}

%eofval{
	return ops.token(ClaseLexica.EOF);
%eofval}

%init{
	ops = new ALexOperations(this);
%init}


//Defs auxiliares
Letra = ([A-Z]|[a-z])
Dig = [0-9]
DigPos = [1-9]
Signo = [\+\-]
PEntera = Signo?(0|{DigPos}{Dig}*)
PDecimal = \.(0|{Dig}*{DigPos})
PExp = [eE] {PEntera}

//Clases léxicas
Id = {Letra} ({Letra} | {PExp} | {PDecimal}{PExp})

LEntero = PEntera
LReal = PEntera (PDecimal | PExp | PDecimal PExp)
LCadena = \'[^(\'\b\r\n)]*\'

Suma = \+
Resta = \-
Multiplica = \*
Divide = \/
Modulo = \%
BLT = \<
BGT = \>
BLE = \<\=

//Cadenas ignorables
Separadores = [ \n\t\r\b]
Comentarios = @[^\n]*

%%

{Id}           {return ops.token(ClaseLexica.ID);}