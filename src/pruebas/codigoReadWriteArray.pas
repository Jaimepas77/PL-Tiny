var num: int;
var v: array [3] of int;
begin
  num = 3;
  while not (num <= 0) do
    num = num - 1;
    read v[num];
	write v[num];
    nl;
  end;
end.
