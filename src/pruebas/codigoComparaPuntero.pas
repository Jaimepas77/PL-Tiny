var p: ^string;
begin
	p = null;
	if p == null then
		new p;
		read p^;
	end;
	if p != null then
		write p^;
		delete p;
	end;
end.