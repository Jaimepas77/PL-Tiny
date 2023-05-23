@Decs
proc fib(var ret: int, n: int)	@Cálculo recursivo del enésimo número de fibonacci
var a: int;
var b: int;
begin
	ret = 0;
	if (n == 1) or (n == 2) then
		ret = 1;
	else
		if n > 2 then
			fib(a, n - 1);
			fib(b, n - 2);
			ret = a + b;
		end;
	end;
end;

var input: int;
var output: int;

@Ins
begin
	write 'Introduce el número de fibonacci que quieres calcular: ';
	read input;
	fib(output, input);
	write 'Resultado: '; write output; nl;
end.