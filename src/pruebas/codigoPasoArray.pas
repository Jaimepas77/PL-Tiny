var casa: array [3] of int;
proc demole(cosa: array [3] of real)
	begin
		cosa[0] = 0;
		cosa[1] = 0.1;
		cosa[2] = 0;
		write cosa[1];nl;
		casa[1] = 0;
	end;
var vacio1: array [0] of int;
var vacio2: array [0] of real;
begin
	vacio2 = vacio1;
	casa[0] = 1;
	casa[1] = 2;
	casa[2] = 3;
	demole(casa);
	write casa[1];nl;
end.