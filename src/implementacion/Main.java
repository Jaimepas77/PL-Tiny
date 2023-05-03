package implementacion;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import implementacion.c_ast_ascendente.*;
import implementacion.abstractSintax.SalidaTipo;
import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.c_ast_descendente.ConstructorASTD;
import implementacion.maquinaP.MaquinaP;
import implementacion.procesamientos.*;

public class Main {

	public static void main(String[] args) throws Exception {

		if (args[0].equals("-lex")) {
			ejecuta_lexico(args[1]);
		}
		else if (args[0].equals("-sasc")) {
			ejecuta_ascendente(args[1]);
		}
		else if (args[0].equals("-sdesc")) {
			ejecuta_descendente(args[1]);
		}
		else {
			Prog prog = null;
			if (args[0].equals("-asc"))
				prog = ejecuta_ascendente(args[1]);
			else if (args[0].equals("-desc")) {
				//prog = ejecuta_descendente(args[1]);
				Scanner s = new Scanner(System.in);
				prog = ejecuta_descendente("./pruebas/" + s.nextLine() + ".pas");
			}
			
			//Procesamientos
			prog.procesa(new Vinculacion());
			prog.procesa(new Tipado());
			if(prog.getTipo() == SalidaTipo.OK) {
				prog.procesa(new AsignacionEspacio());
				prog.procesa(new Etiquetado());
				MaquinaP m = new MaquinaP(100, 100, 50, 4);
				prog.procesa(new GenCodigo(m));

				//Ejecución sobre la máquina-p
				System.out.println("--Resultado de ejecución: ");

				m.ejecuta();

				System.out.println("--FIN de ejecución.");
			}
			else {
				System.out.println("Ejecución abortada por error en el tipado.");
			}
		}
	}

	private static void ejecuta_lexico(String in) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(in));
		AnalizadorLex alex = new AnalizadorLex(input);
		GestionErrores errores = new GestionErrores();
		UnidadLexica t = (UnidadLexica) alex.next_token();
		while (t.clase() != ClaseLexica.EOF) {
			System.out.println(t);
			t = (UnidadLexica) alex.next_token();
		}
	}

	@SuppressWarnings("deprecation")
	private static Prog ejecuta_ascendente(String in) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(in));
		AnalizadorLex alex = new AnalizadorLex(input);
		ConstructorAST constructorast = new ConstructorAST(alex);
		return (Prog) constructorast.parse().value;
	}

	private static Prog ejecuta_descendente(String in) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(in));
		ConstructorASTD constructorast = new ConstructorASTD(input);
		return constructorast.Init();
	}

}
