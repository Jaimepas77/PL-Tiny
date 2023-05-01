package implementacion;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.c_ast_descendente.ConstructorAST;
import implementacion.maquinaP.MaquinaP;
import implementacion.procesamientos.AsignacionEspacio;
import implementacion.procesamientos.Etiquetado;
import implementacion.procesamientos.GenCodigo;
import implementacion.procesamientos.Tipado;
import implementacion.procesamientos.Vinculacion;

public class Main {

	public static void main(String[] args) throws Exception {
	    
      Prog prog = null;
	      /*if (args[0].equals("-lex")) {
	      ejecuta_lexico(args[1]);
	    } else {
	      if (args[0].equals("-asc"))
	        prog = ejecuta_ascendente(args[1]);
	      else */if (args[0].equals("-desc"))
	        prog = ejecuta_descendente(args[1]);
	    //Procesamientos
			prog.procesa(new Vinculacion());
			prog.procesa(new Tipado());
			prog.procesa(new AsignacionEspacio());
			prog.procesa(new Etiquetado());
			MaquinaP m = new MaquinaP(100, 100, 50, 4);
			prog.procesa(new GenCodigo(m));
			
			//Ejecución sobre la máquina-p
			System.out.println("--Resultado de ejecución: ");
			
			m.ejecuta();
			
			System.out.println("--FIN de ejecución.");
	    //}
	  }

//	  private static void ejecuta_lexico(String in) throws Exception {
//	    Reader input = new InputStreamReader(new FileInputStream(in));
//	    AnalizadorLexico alex = new AnalizadorLexico(input);
//	    GestionErrores errores = new GestionErrores();
//	    UnidadLexica t = (UnidadLexica) alex.next_token();
//	    while (t.clase() != ClaseLexica.EOF) {
//	      System.out.println(t);
//	      t = (UnidadLexica) alex.next_token();
//	    }
//	  }

//	  private static Prog ejecuta_ascendente(String in) throws Exception {
//	    Reader input = new InputStreamReader(new FileInputStream(in));
//	    AnalizadorLexico alex = new AnalizadorLexico(input);
//	    ConstructorAST constructorast = new ConstructorAST(alex);
//	    return (Prog) constructorast.parse().value;
//	  }

	  private static Prog ejecuta_descendente(String in) throws Exception {
	    Reader input = new InputStreamReader(new FileInputStream(in));
	    ConstructorAST constructorast = new ConstructorAST(input);
	    return constructorast.Init();
	  }

}
