package implementacion.c_ast_ascendente;

public class GestionErrores {
	public void errorLexico(int fila, String lexema) {
	     System.out.println("ERROR fila "+fila+": Caracter inexperado: "+lexema); 
	     System.exit(1);
	   }  
	   public void errorSintactico(UnidadLexica unidadLexica) {
	     System.out.print("ERROR\n Fila: " + unidadLexica.lexema().fila() + "\n Columna: " + unidadLexica.lexema().col() +
	                              "\nElemento inesperado \"" + unidadLexica.value + "\"");
	     System.exit(1);
	   }
}
