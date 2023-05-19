proc suma(var ret: int, a: int)
	var b: int;
	begin
		write 'Sumando...';
		b = 1;
		ret = (ret + a) + b;
	end;
var a: int;
var b: int;
begin
	read a;
	read b;
	suma(a, b);
	write(a);
end.