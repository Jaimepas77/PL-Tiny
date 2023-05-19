type tListaNombres: record
					nombres: array [50] of string;
					cont: int;
				end;
proc lee(var nombres: tListaNombres)
var i: int;
begin
	i = 0;
	while i < nombres.cont do
		read nombres.nombres[i];
		i = i + 1;
	end;
end;
var nombres: tListaNombres;
begin
	read nombres.cont;
	lee(nombres);
	write nombres.nombres[0];
end.