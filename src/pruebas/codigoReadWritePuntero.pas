var p: ^string;
begin
	new p;
	read p^;
	write p^;
	delete p;
	p = null;
	write p^;
end.