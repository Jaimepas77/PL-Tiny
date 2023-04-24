package pruebas;

import implementacion.abstractSintax.SintaxisAbstracta;
import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.maquinaP.MaquinaP;
import implementacion.procesamientos.*;

public class ejemploPrincipal {

	public static void main(String[] args) {
		//Generación del AST
		Prog prog;
		//ASTs de códigos de prueba hardcodeados en el orden en que se ha conseguido que funcionen correctamente
		prog = astCodigoWrite();
		prog = astCodigoSumaInt();
		prog = astCodigoReadWrite();
		prog = astCodigoSumaRealInt();
//		prog = astCodigoDeEjemplo();
		
		//Procesamientos
		prog.procesa(new Vinculacion());
		prog.procesa(new Tipado());
		prog.procesa(new AsignacionEspacio());
		prog.procesa(new Etiquetado());
		MaquinaP m = new MaquinaP(20, 15, 20, 3);
		prog.procesa(new GenCodigo(m));
		
		//Ejecución sobre la máquina-p
		System.out.println("--Resultado de ejecución: ");
//		m.muestraCodigo();	//Para depurar la generación de código
		m.ejecuta();
		System.out.println("--FIN de ejecución.");
	}
	
	private static Prog astCodigoWrite() {
		//Este código puede funcionar tan solo con el etiquetado y la generación de código
		/*
		 * var i: int;
		 * begin
		 * 	if false then
		 * 		nl;
		 * 	else
		 * 		write 'Intel y gentes';
		 * 		nl;
		 * 	end;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(sa.cUna_Dec(sa.cDec_Var("i", sa.cInt_())),
				sa.cUna_Ins(sa.cIf_Then_Else(
						sa.cFalse(),
						sa.cUna_Ins(
								sa.cNl_()),
						sa.cMuchas_Ins(
								sa.cUna_Ins(
										sa.cWrite_(sa.cCadena("Intel y gentes"))),
								sa.cNl_()))));
	}
	
	private static Prog astCodigoSumaInt() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * var i: int;
		 * begin
		 * 	i = 2;
		 *  i = i + 1;
		 *  write i;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(sa.cUna_Dec(sa.cDec_Var("i", sa.cInt_())),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cUna_Ins(
										sa.cAsignacion_(
												sa.cId("i"),
												sa.cInt("2"))),
								sa.cAsignacion_(
										sa.cId("i"),
										sa.cSuma(sa.cId("i"), sa.cInt("1")))),
						sa.cWrite_(sa.cId("i"))));
	}
	
	private static Prog astCodigoReadWrite() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * var text: string;
		 * begin
		 * 	read text;
		 *  write text;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(sa.cUna_Dec(sa.cDec_Var("text", sa.cString_())),
				sa.cMuchas_Ins(
						sa.cUna_Ins(
								sa.cRead_(sa.cId("text"))),
						sa.cWrite_(sa.cId("text"))));
	}
	
	private static Prog astCodigoSumaRealInt() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * var i: int;
		 * var j: real;
		 * begin
		 * 	i = 2;
		 *  j = 4.3;
		 *  j = i + j;
		 *  write j;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cMuchas_Decs(
						sa.cUna_Dec(
								sa.cDec_Var("i", sa.cInt_())),
						sa.cDec_Var("j", sa.cReal_())),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cMuchas_Ins(
										sa.cUna_Ins(
												sa.cAsignacion_(
														sa.cId("i"),
														sa.cInt("2"))),
										sa.cAsignacion_(
												sa.cId("j"),
												sa.cReal("4.3"))),
								sa.cAsignacion_(
										sa.cId("j"),
										sa.cSuma(sa.cId("i"), sa.cId("j")))),
						sa.cWrite_(sa.cId("j"))));
	}
	
	private static Prog astCodigoDeEjemplo() {
		SintaxisAbstracta sa = new SintaxisAbstracta();
		
		//AST-ejemplo3.jpg
		Dec escribeNombres = sa.cDec_Proc("escribe_nombres",
				sa.cUn_Param(sa.cParam_Val("arbol", sa.cRef_("tArbol"))),
				sa.cSin_Decs(),
				sa.cUna_Ins(
						sa.cIf_Then(
								sa.cBne(sa.cId("arbol"), sa.cNull()),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cUna_Ins(
																sa.cCall_Proc(sa.cId("escribe_nombres"), sa.cUna_Expr(sa.cAccess(sa.cIndir(sa.cId("arbol")), "izq")))),
														sa.cWrite_(sa.cAccess(sa.cIndir(sa.cId("arbol")), "nombre"))),
												sa.cNl_()),
										sa.cCall_Proc(sa.cId("escribe_nombres"), sa.cUna_Expr(sa.cAccess(sa.cIndir(sa.cId("arbol")), "der")))))));
		
		//AST-ejemplo22.jpg -> todas las listas recursivas de los ASTs dibujados están al revés -> arreglado en el código
		Ins ifThen = sa.cIf_Then(
				sa.cBeq(sa.cId("act"), sa.cNull()),
				sa.cUna_Ins(
						sa.cIf_Then_Else(
								sa.cBlt(
										sa.cAccess(sa.cIndir(sa.cId("padre")), "nombre"),
										sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cId("i"))),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cUna_Ins(
																sa.cNew_(sa.cAccess(sa.cIndir(sa.cId("padre")), "der"))),
														sa.cAsignacion_(
																sa.cAccess(sa.cIndir(sa.cAccess(sa.cIndir(sa.cId("padre")), "der")), "nombre"),
																sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cId("i")))),
												sa.cAsignacion_(
														sa.cAccess(sa.cIndir(sa.cAccess(sa.cIndir(sa.cId("padre")), "der")), "izq"),
														sa.cNull())),
										sa.cAsignacion_(
												sa.cAccess(sa.cIndir(sa.cAccess(sa.cIndir(sa.cId("padre")), "der")), "der"),
												sa.cNull())),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cUna_Ins(
																sa.cNew_(sa.cAccess(sa.cIndir(sa.cId("padre")), "izq"))),
														sa.cAsignacion_(
																sa.cAccess(sa.cIndir(sa.cAccess(sa.cIndir(sa.cId("padre")), "izq")), "nombre"),
																sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cId("i")))),
												sa.cAsignacion_(
														sa.cAccess(sa.cIndir(sa.cAccess(sa.cIndir(sa.cId("padre")), "izq")), "izq"),
														sa.cNull())),
										sa.cAsignacion_(
												sa.cAccess(sa.cIndir(sa.cAccess(sa.cIndir(sa.cId("padre")), "izq")), "der"),
												sa.cNull())))));
		
		//AST-ejemplo21.jpg -> todas las listas recursivas de los ASTs dibujados están al revés -> arreglado en el código
		Dec insertaNombre = sa.cDec_Proc("inserta_nombre",
				sa.cUn_Param(sa.cParam_Ref("arbol", sa.cRef_("tArbol"))),
				sa.cSin_Decs(),
				sa.cUna_Ins(
						sa.cIf_Then_Else(
								sa.cBeq(sa.cId("arbol"), sa.cNull()),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cUna_Ins(
																sa.cNew_(sa.cId("arbol"))),
														sa.cAsignacion_(
																sa.cAccess(sa.cIndir(sa.cId("arbol")), "nombre"),
																sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cId("i")))),
												sa.cAsignacion_(
														sa.cAccess(sa.cIndir(sa.cId("arbol")), "izq"),
														sa.cNull())),
										sa.cAsignacion_(
												sa.cAccess(sa.cIndir(sa.cId("arbol")), "der"),
												sa.cNull())),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cUna_Ins(
																sa.cAsignacion_(sa.cId("fin"), sa.cFalse())),
														sa.cAsignacion_(sa.cId("act"), sa.cId("arbol"))),
												sa.cWhile_(sa.cNot(sa.cId("fin")),
														sa.cMuchas_Ins(
																sa.cMuchas_Ins(
																		sa.cUna_Ins(
																				sa.cAsignacion_(sa.cId("padre"), sa.cId("act"))),
																		sa.cIf_Then_Else(
																				sa.cBlt(sa.cAccess(sa.cIndir(sa.cId("act")), "nombre"), sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cId("i"))),
																				sa.cUna_Ins(
																						sa.cAsignacion_(sa.cId("act"), sa.cAccess(sa.cIndir(sa.cId("act")), "der"))),
																				sa.cUna_Ins(
																						sa.cIf_Then(
																								sa.cBgt(sa.cAccess(sa.cIndir(sa.cId("act")), "nombre"), sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"),sa.cId("i"))),
																								sa.cUna_Ins(sa.cAsignacion_(sa.cId("act"), sa.cAccess(sa.cIndir(sa.cId("act")), "izq"))))))),
																sa.cIf_Then_Else(
																		sa.cBeq(sa.cId("act"), sa.cNull()),
																		sa.cUna_Ins(sa.cAsignacion_(sa.cId("fin"), sa.cTrue())),
																		sa.cUna_Ins(
																				sa.cIf_Then(
																						sa.cBeq(sa.cAccess(sa.cIndir(sa.cId("act")), "nombre"), sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cId("i"))),
																						sa.cUna_Ins(
																								sa.cAsignacion_(sa.cId("fin"), sa.cTrue())))))))),
										ifThen))));
		
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
