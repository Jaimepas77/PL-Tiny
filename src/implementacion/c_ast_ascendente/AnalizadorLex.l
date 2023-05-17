package implementacion.c_ast_ascendente;

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
PEntera = [\+\-]?(0|({DigPos}{Dig}*))
PDecimal = (\.(0|({Dig}*{DigPos})))
PExp = [eE] {PEntera}

//Clases léxicas
Id = {Letra} ({Letra}|{Dig}|\_)*

LEntero = {PEntera}
LReal = {PEntera} ({PDecimal}|{PExp}|({PDecimal}{PExp}))
LCadena = \'[^[\'\b\r\n]]*\'

Suma = \+
Resta = \-
Multiplica = \*
Divide = \/
Modulo = \%
BLT = \<
BGT = \>
BLE = \<\=
BGE = \>\=
BEQ = \=\=
BNE = \!\=
PApertura = \(
PCierre = \)
PuntoComa = \;
Igual = \=
CApertura = \[
CCierre = \]
Punto = \.
Circunflejo = \^
Coma = \,
DosPuntos = \:

Int = (I|i)(N|n)(T|t)
Real = (R|r)(E|e)(A|a)(L|l)
Bool = (B|b)(O|o)(O|o)(L|l)
String = (S|s)(T|t)(R|r)(I|i)(N|n)(G|g)
And = (A|a)(N|n)(D|d)
Or = (O|o)(R|r)
Not = (N|n)(O|o)(T|t)
Null = (N|n)(U|u)(L|l)(L|l)
True = (T|t)(R|r)(U|u)(E|e)
False = (F|f)(A|a)(L|l)(S|s)(E|e)
Proc = (P|p)(R|r)(O|o)(C|c)
If = (I|i)(F|f)
Then = (T|t)(H|h)(E|e)(N|n)
Else = (E|e)(L|l)(S|s)(E|e)
While = (W|w)(H|h)(I|i)(L|l)(E|e)
Do = (D|d)(O|o)
Seq = (S|s)(E|e)(Q|q)
Begin = (B|b)(E|e)(G|g)(I|i)(N|n)
End = (E|e)(N|n)(D|d)
Record = (R|r)(E|e)(C|c)(O|o)(R|r)(D|d)
Array = (A|a)(R|r)(R|r)(A|a)(Y|y)
Of = (O|o)(F|f)
New = (N|n)(E|e)(W|w)
Delete = (D|d)(E|e)(L|l)(E|e)(T|t)(E|e)
Read = (R|r)(E|e)(A|a)(D|d)
Write = (W|w)(R|r)(I|i)(T|t)(E|e)
Nl = (N|n)(L|l)
Var = (V|v)(A|a)(R|r)
Type = (T|t)(Y|y)(P|p)(E|e)

//Cadenas ignorables
Separadores = [ \n\t\r\b]
Comentarios = @[^\n]*

%%

{Separadores}   {}
{Comentarios}   {}

{Int}       {return ops.token(ClaseLexica.INT);}
{Real}      {return ops.token(ClaseLexica.REAL);}
{Bool}      {return ops.token(ClaseLexica.BOOL);}
{String}    {return ops.token(ClaseLexica.STRING);}
{And}       {return ops.token(ClaseLexica.AND);}
{Or}        {return ops.token(ClaseLexica.OR);}
{Not}       {return ops.token(ClaseLexica.NOT);}
{Null}      {return ops.token(ClaseLexica.NULL);}
{True}      {return ops.token(ClaseLexica.TRUE);}
{False}     {return ops.token(ClaseLexica.FALSE);}
{Proc}      {return ops.token(ClaseLexica.PROC);}
{If}        {return ops.token(ClaseLexica.IF);}
{Then}      {return ops.token(ClaseLexica.THEN);}
{Else}      {return ops.token(ClaseLexica.ELSE);}
{While}     {return ops.token(ClaseLexica.WHILE);}
{Do}        {return ops.token(ClaseLexica.DO);}
{Seq}       {return ops.token(ClaseLexica.SEQ);}
{Begin}     {return ops.token(ClaseLexica.BEGIN);}
{End}       {return ops.token(ClaseLexica.END);}
{Record}    {return ops.token(ClaseLexica.RECORD);}
{Array}     {return ops.token(ClaseLexica.ARRAY);}
{Of}        {return ops.token(ClaseLexica.OF);}
{New}       {return ops.token(ClaseLexica.NEW);}
{Delete}    {return ops.token(ClaseLexica.DELETE);}
{Read}      {return ops.token(ClaseLexica.READ);}
{Write}     {return ops.token(ClaseLexica.WRITE);}
{Nl}        {return ops.token(ClaseLexica.NL);}
{Var}       {return ops.token(ClaseLexica.VAR);}
{Type}      {return ops.token(ClaseLexica.TYPE);}

{LEntero}       {return ops.token(ClaseLexica.LENTERO);}
{LReal}         {return ops.token(ClaseLexica.LREAL);}
{LCadena}       {return ops.token(ClaseLexica.LCADENA);}

{Suma}          {return ops.token(ClaseLexica.SUMA);}
{Resta}         {return ops.token(ClaseLexica.RESTA);}
{Multiplica}    {return ops.token(ClaseLexica.MULTIPLICA);}
{Divide}        {return ops.token(ClaseLexica.DIVIDE);}
{Modulo}        {return ops.token(ClaseLexica.MODULO);}
{BLT}           {return ops.token(ClaseLexica.BLT);}
{BGT}           {return ops.token(ClaseLexica.BGT);}
{BLE}           {return ops.token(ClaseLexica.BLE);}
{BGE}           {return ops.token(ClaseLexica.BGE);}
{BEQ}           {return ops.token(ClaseLexica.BEQ);}
{BNE}           {return ops.token(ClaseLexica.BNE);}
{PApertura}     {return ops.token(ClaseLexica.PAPERTURA);}
{PCierre}       {return ops.token(ClaseLexica.PCIERRE);}
{PuntoComa}     {return ops.token(ClaseLexica.PUNTOCOMA);}
{Igual}         {return ops.token(ClaseLexica.IGUAL);}
{CApertura}     {return ops.token(ClaseLexica.CAPERTURA);}
{CCierre}       {return ops.token(ClaseLexica.CCIERRE);}
{Punto}         {return ops.token(ClaseLexica.PUNTO);}
{Circunflejo}   {return ops.token(ClaseLexica.CIRCUNFLEJO);}
{Coma}          {return ops.token(ClaseLexica.COMA);}
{DosPuntos}     {return ops.token(ClaseLexica.DOSPUNTOS);}

{Id}            {return ops.token(ClaseLexica.ID);}