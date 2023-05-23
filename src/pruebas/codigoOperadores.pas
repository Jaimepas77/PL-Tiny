var a: bool;
var b: ^int;
var c: ^string;
begin
	a = false;
	if a < true then
		write 'false es menor que true';
	else
		write 'false es mayor que true';
	end
	nl;
	new b;
	new c;
	a = c == b;@Comprobar que se pueden comparar punteros de distinto tipo
	if 2 == 2.0 then
		write '2 = 2.0';
		nl;
	end
	
	b^ = 2;
	b^ = -(b^);
	write '0 - ';write -(b^); write ' = ';write b^;nl;@Operador menos unario
	
	a = not a;
end.