proc suma(var ret: int, a: int)
begin
	write 'Sumando...';
	ret = ret + a;
	nl;
end;
var a: int;
var b: int;
begin
	read a;
	read b;
	suma(a, b);
	write(a);
end.