@Decs
type tNodo: record
			ant: ^tNodo;
			sig: ^tNodo;
			valor: real;
		end;
		
type tLista: record
			nodoIni: ^tNodo;
			tam: int;
		end;
		
proc newLista(var l: tLista)
begin
	l.nodoIni = null;
	l.tam = 0;
end;

proc addElem(var l: tLista, elem: real)
begin
	write 'pendiente';
end;

proc delElem(var l: tLista, index: int)
begin
	write 'pendiente';
end;

proc showList(var l: tLista)
var act: tNodo;
begin
	write 'pendiente';
	act = tLista.nodoIni^;	@DEBE DAR ERROR AQUÍ
end;

@Variables
var lista: tLista;
var op: int;

@Ins
begin
	op = -1;
	newLista(lista);
	while op != 0 do
		write '---'; nl;
		write '0. Salir'; nl;
		write '1. Mostrar lista'; nl;
		write '2. Añadir elemento al final de la lista'; nl;
		write '3. Eliminar elemento de la lista'; nl;
		write '4. Mostrar tamaño de la lista'; nl;
		write 'Introduzca el num de operación que desea realizar sobre la lista: ';
		read op;
		
		@Realizar op elegida
		if op == 1 then
			showList(lista);
		end;
		if op == 2 then
			seq
			var aux: real;
			begin
				write 'Valor del elemento (real): ';
				read aux;
				addElem(lista, aux);
			end;
		end;
		if op == 3 then
			seq
			var aux: int;
			begin
				write 'Índice del elemento a borrar (empezando en 0): ';
				read aux;
				delElem(lista, aux);
			end;
		end;
		if op == 4 then
			write 'Tamaño: '; write lista.tam; nl;
		end;
	end;
end.