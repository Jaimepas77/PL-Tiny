package implementacion.c_ast_ascendente;

public class ALexOperations {

	  private AnalizadorLex alex;
	  public ALexOperations(AnalizadorLex alex) {
	   this.alex = alex;   
	  }
	  public UnidadLexica token(int clase) {
	     UnidadLexica t = new UnidadLexica(alex.fila(), alex.col(), clase, alex.lexema());
//	     alex.incCol();
	     return t;     
	  }
}
