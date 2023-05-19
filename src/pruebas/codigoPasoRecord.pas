var casa: record
			puerta: int;
			tejado: real;
			Fachada: string;
		end;
var num: int;
proc paso(marquesina: record
			puerta: real;
			tejado: real;
			cartel: string;
		end, var marquesina2: record
			puerta: int;
			tejado: real;
			cartel: string;
		end, num: real)
	begin
		write marquesina.tejado * marquesina2.puerta + num;nl;
		write marquesina.cartel;nl;
	end;
begin
	casa.puerta = 3;
	casa.tejado = 2.1;
	casa.fachada = 'Apple green';
	num = 1;
	paso(casa, casa, num);
	write 'The end';nl;
end.