@Types
type tTablero: array [3] of array [3] of int;@0 vacío; 1-player1; 2-player2;
type tJuego: record
			tablero: tTablero;
			turno: int;
		end;
type tFicha: record
			x: int; @fila
			y: int; @columna
		end;

@---
@Procs
proc inicializa(var juego: tJuego)
var i: int;
var j:int;
begin
	i = 0;
	while i<3 do
		j = 0;
		while j < 3 do
			juego.tablero[i][j] = 0;
			j = j + 1;
		end;
		i = i + 1;
	end;
	juego.turno = 0;
end;

proc correcto(fila: int, col: int, var ret: bool, var juego: tJuego)
begin
	ret = false;
	if ((fila > 0) and (col > 0)) and ((fila <= 3) and (col <= 3)) then
		if juego.tablero[fila - 1][col - 1] == 0 then
			ret = true;
		end;
	end;
end;

proc print(board: tTablero)
var player1: string;
var player2: string;
proc icono(n: int)
begin
	if n == 1 then
		write player1;
	else
		if n == 2 then
			write player2;
		else
			write ' ';
		end;
	end;
end;
var i: int;
var j: int;
begin
	player1 = 'X';
	player2 = 'O';
	
	write ' ';
	i = 1;
	while i <= 3 do
		write ' ';
		write i;
		i = i + 1;
	end;
	nl;
	
	i = 0;
	while i < 3 do
		j = 0;
		write ' ------';nl;
		write i + 1;
		while j < 3 do
			write '|';
			icono(board[i][j]);
			j = j + 1;
		end;
		write '|'; nl;
		i = i + 1;
	end;
	write ' ------';nl;
	
end;

@---
@Vars globales
var juego: tJuego;
var player1: string;
var player2: string;

@---
begin
	write '¡Bienvenidos al tres en raya!'; nl;
	write 'Este juego es para dos jugadores.'; nl;
	nl;
	write 'Pulsa intro para continuar...'; nl;
	seq
		var aux: string;
	begin
		read aux;
	end;
	
	write 'Introduce el nombre del primer jugador: ';
	read player1;
	write '¡Bienvenido, '; write player1; write '!'; nl;
	write 'Introduce el nombre del segundo jugador: ';
	read player2;
	write '¡Bienvenido seas, '; write player2; write '!'; nl;
	
	@Inicializar juego
	inicializa(juego);
	print(juego.tablero);
	
	@Juego
	seq
		proc ponFicha()
		var fila: int;
		var columna: int;
		var valido: bool;
		begin
			nl;write '---'; nl;
			write 'Turno de ';
			if (juego.turno%2) == 1 then
				write player1;
			else
				write player2;
			end;
			nl;
			write 'fila: ';
			read fila;
			write 'columna: ';
			read columna;nl;
			
			@Comprobar datos correctos
			correcto(fila, columna, valido, juego);
			while not valido do
				write 'Datos no válidos, inténtelo de nuevo.'; nl;
				write 'fila: ';
				read fila;
				write 'columna: ';
				read columna;nl;
				correcto(fila, columna, valido, juego);
			end;
			
			fila = fila - 1;
			columna = columna - 1;
			juego.tablero[fila][columna] = ((juego.turno + 1) % 2) + 1;
		end;
		
		proc checkVictoria(var victoria: bool)
		var i: int;
		begin
			victoria = false;
			@Comprobar filas
			i = 0;
			while i < 3 do
				if ((juego.tablero[i][0] == juego.tablero[i][1])
				and (juego.tablero[i][1] == juego.tablero[i][2]))
				and (juego.tablero[i][1] != 0) then
					victoria = true;
				end;
				i = i + 1;
			end;
			
			@Comprobar columnas
			i = 0;
			while i < 3 do
				if ((juego.tablero[0][i] == juego.tablero[1][i])
				and (juego.tablero[1][i] == juego.tablero[2][i]))
				and (juego.tablero[1][i] != 0) then
					victoria = true;
				end;
				i = i + 1;
			end;
			
			@Comprobar diagonales
			i = 0;
			while i < 3 do
				if ((juego.tablero[0+i][0] == juego.tablero[2-i][2])
				and (juego.tablero[0+i][0] == juego.tablero[1][1]))
				and (juego.tablero[1][1] != 0) then
					victoria = true;
				end;
				i = i + 2;
			end;
		end;
		
		var victoria: bool;
	begin
		victoria = false;
		while (victoria == false) AND (juego.turno < 9) do
			@Echar ficha
			juego.turno = juego.turno + 1;
			ponFicha();@Usa la variable juego de ámbito más externo
			
			@Imprimir tablero
			print(juego.tablero);
			
			@Comprobar victoria (actualizar variable)
			checkVictoria(victoria);
		end;
		
		@Felicitar al ganador (si lo hay)
		if victoria == TRUE then
			write '¡Ganó ';
			if (juego.turno % 2) == 1 then
				write player1;
			else
				write player2;
			end;
			write '! ¡Felicidades!'; nl;
		else
			write '¡¡Empateeeee!! ¡Emocionante hasta el final!'; nl;
		end;
	end;
	
	@Despedida
	nl;
	write 'Espero que hayan disfrutado de su maravillosa partida.'; nl;
	write '¡Hasta la próxima!'; nl;
end.