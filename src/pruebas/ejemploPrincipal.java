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
		
		//AST-ejemplo2.jpg -> todas las listas recursivas de los ASTs dibujados están al revés ERROR!! -> arreglado en el código
		Dec construyeArbol = sa.cDec_Proc("construye_arbol",
				sa.cSin_Params(),
				sa.cMuchas_Decs(
						sa.cUna_Dec(
								sa.cDec_Var("i", sa.cInt_())),
						insertaNombre),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cUna_Ins(
										sa.cAsignacion_(sa.cId("arbol"), sa.cNull())),
								sa.cAsignacion_(sa.cId("i"), sa.cInt("0"))),
						sa.cWhile_(
								sa.cBlt(sa.cId("i"), sa.cAccess(sa.cId("nombres"), "cont")),
								sa.cMuchas_Ins(
										sa.cUna_Ins(
												sa.cCall_Proc(sa.cId("inserta_nombre"),sa.cUna_Expr(sa.cId("arbol")))),
										sa.cAsignacion_(sa.cId("i"), sa.cSuma(sa.cId("i"), sa.cInt("1")))))));
		
		//AST-ejemplo1.jpg -> todas las listas recursivas de los ASTs dibujados están al revés ERROR!! -> arreglado en el código
		Dec leeNombres = sa.cDec_Proc("lee_nombres",
				sa.cUn_Param(
						sa.cParam_Ref("nombres", sa.cRef_("tListaNombres"))),
				sa.cUna_Dec(sa.cDec_Var("i", sa.cInt_())),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cMuchas_Ins(
																sa.cMuchas_Ins(
																		sa.cUna_Ins(
																				sa.cWrite_(sa.cCadena("Introduce el número de nombres a ordenar (max 50): "))),
																		sa.cNl_()),	//write 'Introduce el número de nombres a ordenar (max 50): '; nl;
																sa.cRead_(sa.cAccess(sa.cId("nombres"), "cont"))),	//read nombres.cont;
														sa.cWhile_(
																sa.cOr(sa.cBlt(sa.cAccess(sa.cId("nombres"), "cont"), sa.cInt("0")),	//(nombres.cont < 0) or (nombres.cont > 50)
																		sa.cBgt(sa.cAccess(sa.cId("nombres"), "cont"), sa.cInt("50"))),
																sa.cMuchas_Ins(
																		sa.cMuchas_Ins(
																				sa.cUna_Ins(
																						sa.cWrite_(sa.cCadena("Introduce el número de nombres a ordenar (max 50): "))),
																				sa.cNl_()),	//write 'Introduce el número de nombres a ordenar (max 50): '; nl;
																		sa.cRead_(sa.cAccess(sa.cId("nombres"), "cont"))))),	//read nombres.cont;
												sa.cAsignacion_(sa.cId("i"), sa.cInt("0"))),
										sa.cWrite_(sa.cCadena("Introduce un nombre en cada línea: "))),
								sa.cNl_()),
						sa.cWhile_(
								sa.cBlt(sa.cId("i"), sa.cAccess(sa.cId("nombres"), "cont")),
								sa.cMuchas_Ins(
										sa.cUna_Ins(
												sa.cRead_(sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cId("i")))),
										sa.cAsignacion_(sa.cId("i"), sa.cSuma(sa.cId("i"), sa.cInt("1")))))));

		//AST-ejemplo.jpg -> todas las listas recursivas de los ASTs dibujados están al revés ERROR!! -> arreglado en el código
		Prog prog;
		LDecs progDecs = sa.cMuchas_Decs(	//Declaraciones del programa principal
				sa.cMuchas_Decs(
						sa.cMuchas_Decs(
								sa.cMuchas_Decs(
										sa.cMuchas_Decs(
												sa.cMuchas_Decs(
														sa.cMuchas_Decs(
																sa.cUna_Dec(
																		sa.cDec_Tipo("tArbol", sa.cPuntero_(sa.cRef_("tNodo")))),	//type tArbol: ^tNodo;escribeNombres),	//escribe_nombres(arbol: tArbol)
																sa.cDec_Tipo("tNodo", sa.cRecord_(sa.cMuchos_Campos(sa.cMuchos_Campos(sa.cUn_Campo(	//type tNodo: record
																		sa.cCampo("nombre", sa.cString_())),	//nombre: string;
																		sa.cCampo("izq", sa.cRef_("tArbol"))),	//izq: tArbol;
																		sa.cCampo("der", sa.cRef_("tArbol")))))),	//der: tArbol;
														sa.cDec_Tipo("tListaNombres", sa.cRecord_(sa.cMuchos_Campos(sa.cUn_Campo(	//type tListaNombres: record
																sa.cCampo("nombres", sa.cArray_("50", sa.cString_()))), 	//nombres: array [50] of string;
																sa.cCampo("cont", sa.cInt_()))))),	//cont: int;
												sa.cDec_Var("nombres", sa.cRef_("tListaNombres"))),	//var nombres: tListaNombres;
										sa.cDec_Var("arbol", sa.cRef_("tArbol"))),	//var arbol: tArbol;
								leeNombres),	//proc lee_nombres(var nombres: tListaNombres)
						construyeArbol),	//proc construye_arbol()
				escribeNombres);	//escribe_nombres(arbol: tArbol)
		LIns progIns = sa.cMuchas_Ins(	//Instrucciones del programa principal
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cUna_Ins(
																sa.cCall_Proc(sa.cId("lee_nombres"), sa.cUna_Expr(sa.cId("nombres")))),
														sa.cCall_Proc(sa.cId("construye_arbol"), sa.cSin_Expr())),
												sa.cWrite_(sa.cCadena("Listado de nombres ordenado"))),
										sa.cNl_()),
								sa.cWrite_(sa.cCadena("---------------------------"))),
						sa.cNl_()),
				sa.cCall_Proc(sa.cId("escribe_nombres"), sa.cUna_Expr(sa.cId("arbol"))));

		prog = sa.cProg_(progDecs, progIns);

		return prog;
	}

}
