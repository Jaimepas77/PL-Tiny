type tArbol: ^tNodo;
type tNodo: record
nombre: string;
izq: tArbol;
der: tArbol;
end;
type tListaNombres: record
nombres: array [50] of string;
cont: int;
end;
var nombres: tListaNombres; @ Aquí se guardarán los nombres leídos (max. 50)
var arbol: tArbol; @ Aquí se construirá un árbol de búsqueda que contendrá
@ los nombres leídos, sin duplicados
@ Lee los nombres a ordenar (max. 50 nombres)
proc lee_nombres(var nombres: tListaNombres)
var i: int;
begin
write 'Introduce el número de nombres a ordenar (max 50): ';
nl;
read nombres.cont;
while (nombres.cont < 0) or (nombres.cont > 50) do
write 'Introduce el número de nombres a ordenar (max 50): '; nl;
read nombres.cont;
end;
i=0;
write 'Introduce un nombre en cada línea: '; nl;
while i < nombres.cont do
read nombres.nombres[i];
i = i + 1;
end;
end; @ del procedimiento lee_nombres
@ Construye un árbol de búsqueda sin duplicados con los nombres leídos
@ Hace un uso global de las variables 'nombres’ y 'arbol' declaradas en
@ el programa principal
proc construye_arbol()
var i: int; @ para iterar sobre la lista de nombres
@ Inserta el nombre actual en el árbol de búsqueda que recibe como parámetro.
@ Hace un uso global de la variable 'nombres' declarado en el programa principal,
@ y en del contador 'i' declarado en el subprograma contenedor 'construye_arbol'
proc inserta_nombre(var arbol: tArbol)
begin
if arbol == null then
new arbol;
arbol^.nombre = nombres.nombres[i];
arbol^.izq = null;
arbol^.der = null;
else
seq
var padre: tArbol; @ apuntará al nodo padre del nuevo nodo a insertar
var act: tArbol; @ para recorrer la rama al final de la cuál debe realizarse
@ la inserción.
var fin: bool; @ para controlar el final del recorrido de la rama
begin
fin = false;
padre = null;
act = arbol;
while not fin do
padre = act;
if act^.nombre < nombres.nombres[i] then @insertar en el hijo derecho
act = act^.der;
else
if act^.nombre > nombres.nombres[i] then @insertar en el hijo izquierdo
act = act^.izq;
end;
end;
if act == null then @ se ha alcanzado el punto de inserción
fin = true;
else
if act^.nombre == nombres.nombres[i] then @ el nombre está duplicado
fin = true;
end;
end;
end; @ del while
if act == null then @ se ha alcanzado un punto de inserción
@ hay que insertar un nuevo nodo
if padre^.nombre < nombres.nombres[i] then @ insertar como hijo izquierdo
new padre^.der;
padre^.der^.nombre = nombres.nombres[i];
padre^.der^.izq= null;
padre^.der^.der = null;
else @ insertar como hijo derecho
new padre^.izq;
padre^.izq^.nombre = nombres.nombres[i];
padre^.izq^.izq= null;
padre^.izq^.der = null;
end;
end;
end; @ del seq
end;
end; @ del procedimiento anidado inserta_nombre
begin @ del procedimiento construye_arbol
arbol = null;
i=0;
while i < nombres.cont do
inserta_nombre(arbol);
i = i + 1;
end;
end; @ del procedimiento construye_arbol
@ Escribe los nombres almacenados en el árbol de búsqueda, recorriendo
@ dicho árbol en inorden.
@ Por tanto, los nombres se listan ordenados alfabéticamente,
@ y sin duplicados
proc escribe_nombres(arbol: tArbol)
begin
if arbol != null then
escribe_nombres(arbol^.izq);
write arbol^.nombre; nl;
escribe_nombres(arbol^.der);
end;
end; @ del procedimiento escribe_nombres
begin
@ Programa principal
lee_nombres(nombres);
construye_arbol();
write 'Listado de nombres ordenado'; nl;
write '---------------------------'; nl;
escribe_nombres(arbol);
end.