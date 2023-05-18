var casa: array [3] of int;
var edificio: array [3] of real;
begin
	casa[0] = 1;
	casa[1] = 2;
	casa[2] = 3;
	edificio = casa;
	write edificio[1] + 3.5;
end.