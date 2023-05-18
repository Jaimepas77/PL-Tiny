package implementacion.c_ast_ascendente;

import implementacion.abstractSintax.SintaxisAbstracta.StringLocalizado;
import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol {
	public UnidadLexica(int fila, int col, int clase, String lexema) {
		super(clase,null);
		value = new StringLocalizado(lexema,fila,col);
	}
	public int clase () {return sym;}
	public StringLocalizado lexema() {return (StringLocalizado)value;}
	public String toString() {
		StringLocalizado lexema = (StringLocalizado)value;
		return "[clase="+clase2string(sym)+",lexema="+lexema+",fila="+lexema.fila()+",col="+lexema.col()+"]";
	}
	private String clase2string(int clase) {
		return ClaseLexica.terminalNames[clase];
	}
}
