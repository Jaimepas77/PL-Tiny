package implementacion.c_ast_ascendente;

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
		switch(clase) {
		case ClaseLexica.SUMA: return "SUMA";
		case ClaseLexica.RESTA: return "RESTA";
		case ClaseLexica.MULTIPLICA: return "MULTIPLICA";
		case ClaseLexica.DIVIDE: return "DIVIDE";
		case ClaseLexica.MODULO: return "MODULO";
		case ClaseLexica.BLT: return "BLT";
		case ClaseLexica.BGT: return "BGT";
		case ClaseLexica.BLE: return "BLE";
		case ClaseLexica.BGE: return "BGE";
		case ClaseLexica.BEQ: return "BEQ";
		case ClaseLexica.BNE: return "BNE";
		case ClaseLexica.PAPERTURA: return "PAPERTURA";
		case ClaseLexica.PCIERRE: return "PCIERRE";
		case ClaseLexica.PUNTOCOMA: return "PUNTOCOMA";
		case ClaseLexica.IGUAL: return "IGUAL";
		case ClaseLexica.CAPERTURA: return "CAPERTURA";
		case ClaseLexica.CCIERRE: return "CCIERRA";
		case ClaseLexica.PUNTO: return "PUNTO";
		case ClaseLexica.CIRCUNFLEJO: return "CIRCUNFLEJO";
		case ClaseLexica.COMA: return "COMA";
		case ClaseLexica.DOSPUNTOS: return "DOSPUNTOS";
		case ClaseLexica.INT: return "INT";
		case ClaseLexica.REAL: return "REAL";
		case ClaseLexica.BOOL: return "BOOL";
		case ClaseLexica.STRING: return "STRING";
		case ClaseLexica.AND: return "AND";
		case ClaseLexica.OR: return "OR";
		case ClaseLexica.NOT: return "NOT";
		case ClaseLexica.NULL: return "NULL";
		case ClaseLexica.TRUE: return "TRUE";
		case ClaseLexica.FALSE: return "FALSE";
		case ClaseLexica.PROC: return "PROC";
		case ClaseLexica.IF: return "IF";
		case ClaseLexica.THEN: return "THEN";
		case ClaseLexica.ELSE: return "ELSE";
		case ClaseLexica.WHILE: return "WHILE";
		case ClaseLexica.DO: return "DO";
		case ClaseLexica.SEQ: return "SEQ";
		case ClaseLexica.BEGIN: return "BEGIN";
		case ClaseLexica.END: return "END";
		case ClaseLexica.RECORD: return "RECORD";
		case ClaseLexica.ARRAY: return "ARRAY";
		case ClaseLexica.OF: return "OF";
		case ClaseLexica.NEW: return "NEW";
		case ClaseLexica.DELETE: return "DELETE";
		case ClaseLexica.READ: return "READ";
		case ClaseLexica.WRITE: return "WRITE";
		case ClaseLexica.NL: return "NL";
		case ClaseLexica.VAR: return "VAR";
		case ClaseLexica.TYPE: return "TYPE";
		case ClaseLexica.ID: return "ID";
		case ClaseLexica.LENTERO: return "LENTERO";
		case ClaseLexica.LREAL: return "LREAL";
		case ClaseLexica.LCADENA: return "LCADENA";
		default: return "?";               
		}
	}
}
