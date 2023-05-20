type tA: ^tB;
type tB: tA;
var p: tA;@Punteros de punteros de punteros de punteros...
var p2: ^tB;
begin
	p = null;
	new p2;
	if p == null then
		new p;
		p = p2;
	end;
	if p != null then
		delete p;
	end;
end.