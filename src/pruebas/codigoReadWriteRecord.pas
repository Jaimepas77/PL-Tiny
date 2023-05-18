var casa: record
 				puerta: int;
 				tejado: real;
			end;
begin
	read casa.puerta;
	write casa.puerta + 1;
	read casa.tejado;
	write casa.tejado * casa.puerta;
end.