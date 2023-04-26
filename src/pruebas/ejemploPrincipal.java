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
		prog = astCodigoReadWriteResta();
		prog = astCodigoBool();
		prog = astCodigoReadWriteWhile();
		prog = astCodigoReadWriteWhileAnd();
		prog = astCodigoReadWriteNegDivMul();
		prog = astCodigoWriteTypeSeq();
		prog = astCodigoReadWriteArray();
		prog = astCodigoReadWriteRecord();
		prog = astCodigoReadWritePuntero();
		prog = astCodigoAsignaRecord();//De momento falla
		prog = astCodigoDecProc();
		prog = astCodigoProcSuma();
		prog = astCodigoProcSumaPlus();
		prog = astCodigoProcType();
		prog = astCodigoDeEjemplo();
		
		//Procesamientos
		prog.procesa(new Vinculacion());
		prog.procesa(new Tipado());
		prog.procesa(new AsignacionEspacio());
		prog.procesa(new Etiquetado());
		MaquinaP m = new MaquinaP(100, 100, 50, 4);
		prog.procesa(new GenCodigo(m));
		
		//Ejecución sobre la máquina-p
		System.out.println("--Resultado de ejecución: ");
		
		depura(m);
//		m.ejecuta();
		
		System.out.println("--FIN de ejecución.");
	}
	
	private static void depura(MaquinaP m) {
		m.muestraCodigo();	//Para depurar la generación de código
		while(m.getPc() < m.getCodigoPSize()) {
			m.ejecutaPaso();
			System.err.println(m.getPc());
//			m.muestraEstado();
		}
	}

	private static Prog astCodigoWrite() {
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
	
	private static Prog astCodigoReadWriteResta() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * var num: real;
		 * begin
		 * 	read num;
		 *  write num-1;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(sa.cUna_Dec(sa.cDec_Var("num", sa.cReal_())),
				sa.cMuchas_Ins(
						sa.cUna_Ins(
								sa.cRead_(sa.cId("num"))),
						sa.cWrite_(sa.cResta(sa.cId("num"), sa.cInt("1")))));
	}
	
	private static Prog astCodigoBool() {
		/*
		 * var a: bool;
		 * begin
		 * 	a = 3 != 3.0
		 * 	if a then
		 * 		write 'a es true';
		 * 	else
		 * 		write 'a es false';
		 * 		nl;
		 * 	end;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(sa.cUna_Dec(sa.cDec_Var("a", sa.cBool_())),
				sa.cMuchas_Ins(
						sa.cUna_Ins(
								sa.cAsignacion_(
										sa.cId("a"),
										sa.cBne(sa.cInt("3"), sa.cReal("3.0")))),
						sa.cIf_Then_Else(
								sa.cId("a"),
								sa.cUna_Ins(
										sa.cWrite_(sa.cCadena("a es true"))),
								sa.cMuchas_Ins(
										sa.cUna_Ins(
												sa.cWrite_(sa.cCadena("a es false"))),
										sa.cNl_()))));
	}
	
	private static Prog astCodigoReadWriteWhile() {
		/*
		 * var num: real;
		 * begin
		 * 	read num;
		 * 	while not (num < 0) then
		 * 		num = num - 1;
		 *  	write num;
		 *  	nl;
		 *  end;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(sa.cUna_Dec(sa.cDec_Var("num", sa.cReal_())),
				sa.cMuchas_Ins(
						sa.cUna_Ins(
								sa.cRead_(sa.cId("num"))),
						sa.cWhile_(
								sa.cNot(sa.cBlt(sa.cId("num"), sa.cInt("0"))),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cUna_Ins(
														sa.cAsignacion_(sa.cId("num"), sa.cResta(sa.cId("num"), sa.cInt("1")))),
												sa.cWrite_(sa.cId("num"))),
										sa.cNl_()))));
	}
	
	private static Prog astCodigoReadWriteWhileAnd() {
		/*
		 * var num: int;
		 * begin
		 * 	read num;
		 * 	while (not (num <= 0)) and num%5 != 4 then
		 * 		num = num - 1;
		 *  	write num;
		 *  	nl;
		 *  end;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(sa.cUna_Dec(sa.cDec_Var("num", sa.cInt_())),
				sa.cMuchas_Ins(
						sa.cUna_Ins(
								sa.cRead_(sa.cId("num"))),
						sa.cWhile_(
								sa.cAnd(
										sa.cNot(sa.cBle(sa.cId("num"), sa.cInt("0"))),
										sa.cBne(sa.cMod(sa.cId("num"), sa.cInt("5")), sa.cInt("4"))),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cUna_Ins(
														sa.cAsignacion_(sa.cId("num"), sa.cResta(sa.cId("num"), sa.cInt("1")))),
												sa.cWrite_(sa.cId("num"))),
										sa.cNl_()))));
	}
	
	private static Prog astCodigoReadWriteNegDivMul() {
		/*
		 * var num: real;
		 * begin
		 * 	read num;
		 *  write ((-num)/2)*(-num);
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cMuchas_Decs(
						sa.cUna_Dec(
								sa.cDec_Tipo("tNum", sa.cReal_())),
						sa.cDec_Var("num", sa.cRef_("tNum"))),
				sa.cMuchas_Ins(
						sa.cUna_Ins(
								sa.cRead_(sa.cId("num"))),
						sa.cWrite_(
								sa.cMult(
										sa.cDiv(
												sa.cNeg(sa.cId("num")),
												sa.cInt("2")),
										sa.cNeg(sa.cId("num"))
										)
								)
						)
				);
	}
	
	private static Prog astCodigoWriteTypeSeq() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * type tNum: real;
		 * begin
		 * 	seq
		 * 		var num: tNum;
		 * 		var ey: string;
		 * 	begin
		 * 		num = 3;
		 *  	write num;
		 *  end;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cUna_Dec(
						sa.cDec_Tipo("tNum", sa.cReal_())),
				sa.cUna_Ins(
						sa.cIns_Compuesta(
								sa.cMuchas_Decs(
										sa.cUna_Dec(
												sa.cDec_Var("num", sa.cRef_("tNum"))), 
										sa.cDec_Var("ey", sa.cString_())),
								sa.cMuchas_Ins(
										sa.cUna_Ins(
												sa.cAsignacion_(sa.cId("num"), sa.cInt("3"))),
										sa.cWrite_(sa.cId("num"))))));
	}
	
	private static Prog astCodigoReadWriteArray() {
		/*
		 * var num: real;
		 * var v: array [3] of int;
		 * begin
		 * 	num = 3;
		 * 	while not (num <= 0) then
		 * 		num = num - 1;
		 *  	write num;
		 *  	nl;
		 *  end;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cMuchas_Decs(
						sa.cUna_Dec(
								sa.cDec_Var("num", sa.cInt_())),
						sa.cDec_Var("v", sa.cArray_("3", sa.cInt_()))),
				sa.cMuchas_Ins(
						sa.cUna_Ins(
								sa.cAsignacion_(sa.cId("num"), sa.cInt("3"))),
						sa.cWhile_(
								sa.cNot(sa.cBle(sa.cId("num"), sa.cInt("0"))),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cUna_Ins(
																sa.cAsignacion_(sa.cId("num"), sa.cResta(sa.cId("num"), sa.cInt("1")))),
														sa.cRead_(sa.cIndex(sa.cId("v"), sa.cId("num")))),
												sa.cWrite_(sa.cIndex(sa.cId("v"), sa.cId("num")))),
										sa.cNl_()))));
	}
	
	private static Prog astCodigoReadWriteRecord() {
		/*
		 * var casa: record
		 * 				puerta: int;
		 * 				tejado: real;
	 * 				end;
		 * begin
		 * 	read casa.puerta;
		 *  write casa.puerta + 1;
		 *  read casa.tejado;
		 *  write casa.tejado * casa.puerta;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cUna_Dec(sa.cDec_Var(
						"casa", 
						sa.cRecord_(
								sa.cMuchos_Campos(
										sa.cUn_Campo(
												sa.cCampo("puerta", sa.cInt_())),
										sa.cCampo("tejado", sa.cReal_()))))),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cMuchas_Ins(
										sa.cUna_Ins(
												sa.cRead_(sa.cAccess(sa.cId("casa"), "puerta"))),
										sa.cWrite_(sa.cSuma(sa.cAccess(sa.cId("casa"), "puerta"), sa.cInt("1")))), 
								sa.cRead_(sa.cAccess(sa.cId("casa"), "tejado"))), 
						sa.cWrite_(sa.cMult(sa.cAccess(sa.cId("casa"), "tejado"), sa.cAccess(sa.cId("casa"), "puerta"))))
				);
	}
	
	private static Prog astCodigoReadWritePuntero() {//Tiene que dar error de ejecución
		/*
		 * var p: ^string;
		 * begin
		 * 	new p;
		 * 	read p^;
		 *  write p^;
		 *  delete p;
		 *  p = null;
		 *  write p^;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cUna_Dec(
						sa.cDec_Var("p", sa.cPuntero_(sa.cString_()))),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cMuchas_Ins(
														sa.cUna_Ins(
																sa.cNew_(sa.cId("p"))),
														sa.cRead_(sa.cIndir(sa.cId("p")))),
												sa.cWrite_(sa.cIndir(sa.cId("p")))),
										sa.cDelete_(sa.cId("p"))), 
								sa.cAsignacion_(sa.cId("p"), sa.cNull())),
						sa.cWrite_(sa.cIndir(sa.cId("p"))))
				);
	}
	
	private static Prog astCodigoAsignaRecord() {//Pendiente de arreglar (conversión inttoreal en asignación de records)
		/*
		 * var casa: record
		 * 				puerta: int;
		 * 				tejado: real;
		 * 			end;
		 * var marquesina: record
		 * 				puerta: real;
		 * 				tejado: real;
		 * 			end;
		 * begin
		 * 	read casa.puerta;
		 *  write casa.puerta + 1;
		 *  read casa.tejado;
		 *  marquesina = casa;
		 *  write marquesina.tejado * marquesina.puerta;
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cMuchas_Decs(
						sa.cUna_Dec(sa.cDec_Var(
								"casa", 
								sa.cRecord_(
										sa.cMuchos_Campos(
												sa.cUn_Campo(
														sa.cCampo("puerta", sa.cInt_())),
												sa.cCampo("tejado", sa.cReal_()))))), 
						sa.cDec_Var(
								"marquesina", 
								sa.cRecord_(
										sa.cMuchos_Campos(
												sa.cUn_Campo(
														sa.cCampo("puerta", sa.cReal_())),
												sa.cCampo("tejado", sa.cReal_()))))
						),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cUna_Ins(
														sa.cRead_(sa.cAccess(sa.cId("casa"), "puerta"))),
												sa.cWrite_(sa.cSuma(sa.cAccess(sa.cId("casa"), "puerta"), sa.cInt("1")))), 
										sa.cRead_(sa.cAccess(sa.cId("casa"), "tejado"))), 
								sa.cAsignacion_(sa.cId("marquesina"), sa.cId("casa"))), 
						sa.cWrite_(sa.cMult(sa.cAccess(sa.cId("marquesina"), "tejado"), sa.cAccess(sa.cId("marquesina"), "puerta"))))
				);
	}
	
	private static Prog astCodigoDecProc() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * proc saluda(nombre: string)
		 * begin
		 * 	write 'saludos ';
		 * 	write nombre;
		 * 	nl;
		 * end;
		 * begin
		 * 	saluda('JL');
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cUna_Dec(
						sa.cDec_Proc("saluda", sa.cUn_Param(sa.cParam_Val("nombre", sa.cString_())), sa.cSin_Decs(),
								sa.cMuchas_Ins(
										sa.cMuchas_Ins(
												sa.cUna_Ins(
														sa.cWrite_(sa.cCadena("saludos "))),
												sa.cWrite_(sa.cId("nombre"))),
										sa.cNl_())
								)
						),
				sa.cUna_Ins(
						sa.cCall_Proc(sa.cId("saluda"), sa.cUna_Expr(sa.cCadena("JL")))));
	}
	
	private static Prog astCodigoProcSuma() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * proc suma(var ret: int, a: int)
		 * begin
		 * 	write 'Sumando...";
		 * 	ret = ret + a;
		 * 	nl;
		 * end;
		 * var a: int;
		 * var b: int;
		 * begin
		 * 	read a;
		 * 	read b;
		 * 	suma(a, b);
		 * 	write(a);
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cMuchas_Decs(
						sa.cMuchas_Decs(
								sa.cUna_Dec(
										sa.cDec_Proc("suma", 
												sa.cMuchos_Params(
														sa.cUn_Param(sa.cParam_Ref("ret", sa.cInt_())),
														sa.cParam_Val("a", sa.cInt_())) , sa.cSin_Decs(),
												sa.cMuchas_Ins(
														sa.cMuchas_Ins(
																sa.cUna_Ins(
																		sa.cWrite_(sa.cCadena("Sumando..."))),
																sa.cAsignacion_(sa.cId("ret"), sa.cSuma(sa.cId("ret"), sa.cId("a")))),
														sa.cNl_())
												)
										), 
								sa.cDec_Var("a", sa.cInt_())),
						sa.cDec_Var("b", sa.cInt_())),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cMuchas_Ins(
										sa.cUna_Ins(
												sa.cRead_(sa.cId("a"))),
										sa.cRead_(sa.cId("b"))),
								sa.cCall_Proc(sa.cId("suma"), sa.cMuchas_Expr(sa.cUna_Expr(sa.cId("a")), sa.cId("b")))),
						sa.cWrite_(sa.cId("a")))
				);
	}
	
	private static Prog astCodigoProcSumaPlus() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * proc suma(var ret: int, a: int)
		 * 	var b: int;
		 * begin
		 * 	write 'Sumando...";
		 * 	b = 1;
		 * 	ret = (ret + a) + b;
		 * end;
		 * var a: int;
		 * var b: int;
		 * begin
		 * 	read a;
		 * 	read b;
		 * 	suma(a, b);
		 * 	write(a);
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cMuchas_Decs(
						sa.cMuchas_Decs(
								sa.cUna_Dec(
										sa.cDec_Proc("suma", 
												sa.cMuchos_Params(
														sa.cUn_Param(sa.cParam_Ref("ret", sa.cInt_())),
														sa.cParam_Val("a", sa.cInt_())),
												sa.cUna_Dec(sa.cDec_Var("b", sa.cInt_())),
												sa.cMuchas_Ins(
														sa.cMuchas_Ins(
																sa.cUna_Ins(
																		sa.cWrite_(sa.cCadena("Sumando..."))),
																sa.cAsignacion_(sa.cId("b"), sa.cInt("1"))),
														sa.cAsignacion_(sa.cId("ret"), sa.cSuma(sa.cId("ret"), sa.cSuma(sa.cId("a"), sa.cId("b")))))
												)
										), 
								sa.cDec_Var("a", sa.cInt_())),
						sa.cDec_Var("b", sa.cInt_())),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cMuchas_Ins(
										sa.cUna_Ins(
												sa.cRead_(sa.cId("a"))),
										sa.cRead_(sa.cId("b"))),
								sa.cCall_Proc(sa.cId("suma"), sa.cMuchas_Expr(sa.cUna_Expr(sa.cId("a")), sa.cId("b")))),
						sa.cWrite_(sa.cId("a")))
				);
	}
	
	private static Prog astCodigoProcType() {
		//Este código precisa de todos los procesamientos excepto el etiquetado
		/*
		 * type tListaNombres: record
		 * 						nombres: array [50] of string;
		 * 						cont: int;
		 * 					end;
		 * proc lee(var nombres: tListaNombres)
		 * 	var i: int;
		 * begin
		 * 	i = 0;
		 * 	while i < nombres.cont do
		 * 		read nombres.nombres[i]
		 * 		i = i+1;
		 * 	end;
		 * end;
		 * var nombres: tListaNombres;
		 * begin
		 * 	read nombres.cont;
		 * 	lee(nombres);
		 * 	write nombres.nombres[0];
		 * end.
		 * */
		SintaxisAbstracta sa = new SintaxisAbstracta();
		return sa.cProg_(
				sa.cMuchas_Decs(
						sa.cMuchas_Decs(
								sa.cUna_Dec(
										sa.cDec_Tipo("tListaNombres",
												sa.cRecord_(
														sa.cMuchos_Campos(
																sa.cUn_Campo(
																		sa.cCampo("nombres", sa.cArray_("40", sa.cString_()))),
																sa.cCampo("cont", sa.cInt_()))))),
								sa.cDec_Proc("lee",
										sa.cUn_Param(sa.cParam_Ref("nombres", sa.cRef_("tListaNombres"))),
										sa.cUna_Dec(sa.cDec_Var("i", sa.cInt_())),
										sa.cMuchas_Ins(
												sa.cUna_Ins(
														sa.cAsignacion_(sa.cId("i"), sa.cInt("0"))),
												sa.cWhile_(
														sa.cBlt(sa.cId("i"), sa.cAccess(sa.cId("nombres"), "cont")),
														sa.cMuchas_Ins(
																sa.cUna_Ins(
																		sa.cRead_(sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cId("i")))),
																sa.cAsignacion_(sa.cId("i"), sa.cSuma(sa.cId("i"), sa.cInt("1"))))
														)
												)
										)
								),
						sa.cDec_Var("nombres", sa.cRef_("tListaNombres"))),
				sa.cMuchas_Ins(
						sa.cMuchas_Ins(
								sa.cUna_Ins(
										sa.cRead_(sa.cAccess(sa.cId("nombres"), "cont"))),
								sa.cCall_Proc(sa.cId("lee"), sa.cUna_Expr(sa.cId("nombres")))),
						sa.cWrite_(sa.cIndex(sa.cAccess(sa.cId("nombres"), "nombres"), sa.cInt("0")))
						)
				);
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
								sa.cUna_Ins(
										sa.cIns_Compuesta(
												sa.cMuchas_Decs(
														sa.cMuchas_Decs(
																sa.cUna_Dec(
																		sa.cDec_Var("padre", sa.cRef_("tArbol"))),
																sa.cDec_Var("act", sa.cRef_("tArbol"))),
														sa.cDec_Var("fin", sa.cBool_())),
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
														ifThen))
										))));
		
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
