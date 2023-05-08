@Archivo con errores de ejemlo (comentar los que no se quieran probar)

@var v: array [-1] of string; @Array de long negativa

@Variables duplicadas (pq no se distingue entre mayúsculas y minúsculas)
@var a: int;
var A: real;

var b: int;

var tipo: tInventado;@Error de vinculación del tipo

begin
	@b = 8 + 3 + 2; @La suma no asocia -> (error)
	write 'hola'; nl;
	write B;
end.