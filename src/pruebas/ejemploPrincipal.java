package pruebas;

import implementacion.abstractSintax.SintaxisAbstracta;
import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.procesamientos.*;

public class ejemploPrincipal {

	public static void main(String[] args) {
		//Generación del AST
		Prog prog;
		prog = astCodigoDeEjemplo();
		
		//Procesamientos
		prog.procesa(new Vinculacion());
//		prog.procesa(new Tipado());
//		prog.procesa(new AsignacionEspacio());
//		prog.procesa(new Etiquetado());
//		prog.procesa(new GenCodigo());
		
		//Ejecución sobre la máquina-p
	}

	private static Prog astCodigoDeEjemplo() {
		SintaxisAbstracta sa = new SintaxisAbstracta();
		
		//AST-ejemplo3.jpg
		Dec escribeNombres = null;
		
		//AST-ejemplo22.jpg
		Ins ifThen = null;
		
		//AST-ejemplo21.jpg
		Dec insertaNombre = null;
		
		//AST-ejemplo2.jpg
		Dec construyeArbol = null;
		
		//AST-ejemplo1.jpg
		Dec leeNombres = null;
		
		//AST-ejemplo.jpg
		Prog prog;
		LDecs progDecs = sa.cMuchas_Decs(	//Declaraciones del programa principal
				sa.cMuchas_Decs(
						sa.cMuchas_Decs(
								sa.cMuchas_Decs(
										sa.cMuchas_Decs(
												sa.cMuchas_Decs(
														sa.cMuchas_Decs(
																sa.cUna_Dec(escribeNombres),	//escribe_nombres(arbol: tArbol)
																construyeArbol),	//proc construye_arbol()
														leeNombres),	//proc lee_nombres(var nombres: tListaNombres)
												
												sa.cDec_Var("arbol", sa.cRef_("tArbol"))),	//var arbol: tArbol;
										sa.cDec_Var("nombres", sa.cRef_("tListaNombres"))),	//var nombres: tListaNombres;
								
								sa.cDec_Tipo("tListaNombres", sa.cRecord_(sa.cMuchos_Campos(sa.cUn_Campo(	//type tListaNombres: record
										sa.cCampo("cont", sa.cInt_())), 							//cont: int;
										sa.cCampo("nombres", sa.cArray_("50", sa.cString_())))))),	//nombres: array [50] of string;
						sa.cDec_Tipo("tNodo", sa.cRecord_(sa.cMuchos_Campos(sa.cMuchos_Campos(sa.cUn_Campo(	//type tNodo: record
								sa.cCampo("der", sa.cRef_("tArbol"))),	//der: tArbol;
								sa.cCampo("izq", sa.cRef_("tArbol"))),	//izq: tArbol;
								sa.cCampo("nombre", sa.cString_()))))),	//nombre: string;
				sa.cDec_Tipo("tArbol", sa.cPuntero_(sa.cRef_("tNodo"))));	//type tArbol: ^tNodo;
		LIns progIns = sa.cSin_Ins();//PENDIENTE de completar
		
		prog = sa.cProg_(progDecs, progIns);
		
		return prog;
	}

}
