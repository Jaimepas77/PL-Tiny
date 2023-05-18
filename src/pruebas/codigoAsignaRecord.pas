var casa: record
			puerta: int;
			tejado: real;
		end;
var marquesina: record
			puerta: real;
			tejado: real;
		end;
begin
	read casa.puerta;
	write casa.puerta + 1;
	read casa.tejado;
	marquesina = casa;
	write marquesina.tejado * marquesina.puerta;
end.