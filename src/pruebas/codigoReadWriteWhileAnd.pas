var num: int;
begin
	read num;
	while (not (num <= 0)) and (num%5 != 4) do
		num = num - 1;
		write num;
		nl;
	end;
end.