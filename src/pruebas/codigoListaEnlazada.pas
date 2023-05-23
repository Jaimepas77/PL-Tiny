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

proc showList(var l: tLista)
var act: ^tNodo;
begin
	write '[';
	act = l.nodoIni;
	if act != null then
		while act^.sig != null do
			write act^.valor; write ', ';
			act = act^.sig;
		end;
		write act^.valor;
	end;
	
	write ']'; nl;
end;

proc addElem(var l: tLista, elem: real, index: int)
var nuevo: ^tNodo;
var post: ^tNodo;@Nodo posterior
var ant: ^tNodo;@Nodo anterior
var cont: int;
begin
	if (index <= l.tam) and (index >= 0) then
		write 'añadiendo...'; nl;
		l.tam = l.tam + 1;
		@Busca el índice
		cont = 0;
		post = l.nodoIni;
		ant = null;
		while cont < index do
			cont = cont + 1;
			ant = post;
			post = post^.sig;
		end;
		
		@Añade elem antes de post
		new nuevo;
		if ant != null then @Si no es el primero
			ant^.sig = nuevo;
		else
			l.nodoIni = nuevo;
		end;
		if post != null then @Si no es el último
			post^.ant = nuevo;
		end;
		
		nuevo^.valor = elem;
		nuevo^.sig = post;
		nuevo^.ant = ant;
	else
		write 'Índice fuera de rango, no se puede añadir el elemento.'; nl;
	end;
end;

proc delElem(var l: tLista, index: int)
var antiguo: ^tNodo;
var act: ^tNodo;@Nodo posterior
var ant: ^tNodo;@Nodo anterior
var cont: int;
begin
	if (index < l.tam) and (index >= 0) then
		write 'eliminando...'; nl;
		l.tam = l.tam - 1;
		@Busca el índice
		cont = 0;
		act = l.nodoIni;
		ant = null;
		while cont < index do
			cont = cont + 1;
			ant = act;
			act = act^.sig;
		end;
		
		@Elimina elem act
		antiguo = act;
		act = antiguo^.sig;
		if ant != null then @Si no es el primero
			ant^.sig = act;
		else
			l.nodoIni = act;
		end;
		if act != null then
			act^.ant = ant;
		end;
		
		delete antiguo;
	else
		write 'Índice fuera de rango, no se puede eliminar el elemento.'; nl;
	end;
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
		write '2. Añadir elemento a la lista'; nl;
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
			var aux1: real;
			var aux2: int;
			begin
				write 'Valor del elemento (real): ';
				read aux1;
				write 'Índice del elemento a añadir (empezando en 0): ';
				read aux2;
				addElem(lista, aux1, aux2);
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