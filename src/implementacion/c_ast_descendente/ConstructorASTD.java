/* ConstructorASTD.java */
/* Generated By:JavaCC: Do not edit this line. ConstructorASTD.java */
package implementacion.c_ast_descendente;

import implementacion.abstractSintax.SintaxisAbstracta;
import implementacion.abstractSintax.SintaxisAbstracta.*;

public class ConstructorASTD implements ConstructorASTDConstants {
        private SintaxisAbstracta sa = new SintaxisAbstracta();

//Sintaxis
  final public         Prog Init() throws ParseException {Prog prog;
    prog = Prog();
    jj_consume_token(0);
{if ("" != null) return prog;}
    throw new Error("Missing return statement in function");
}

  final public Prog Prog() throws ParseException {LDecs sDecs; LIns sIns;
    sDecs = SDecs();
    sIns = SIns();
    jj_consume_token(Punto);
{if ("" != null) return sa.cProg_(sDecs, sIns);}
    throw new Error("Missing return statement in function");
}

  final public LDecs SDecs() throws ParseException {LDecs rSDecs;
    rSDecs = RSDecs(sa.cSin_Decs());
{if ("" != null) return rSDecs;}
    throw new Error("Missing return statement in function");
}

  final public LDecs RSDecs(LDecs h) throws ParseException {Dec decT; LDecs rSDecs;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Proc:
    case Var:
    case Type:{
      decT = DecT();
      rSDecs = RSDecs(sa.cMuchas_Decs(h, decT));
{if ("" != null) return rSDecs;}
      break;
      }
    default:
      jj_la1[0] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public Dec DecT() throws ParseException {Dec dec;
    dec = Dec();
    jj_consume_token(PuntoComa);
{if ("" != null) return dec;}
    throw new Error("Missing return statement in function");
}

  final public Dec Dec() throws ParseException {Dec dec;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Var:{
      dec = DecVar();
{if ("" != null) return dec;}
      break;
      }
    case Type:{
      dec = DecTipo();
{if ("" != null) return dec;}
      break;
      }
    case Proc:{
      dec = DecProc();
{if ("" != null) return dec;}
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Dec DecVar() throws ParseException {Tipo tipo; Token t;
    jj_consume_token(Var);
    t = jj_consume_token(Id);
    jj_consume_token(DosPuntos);
    tipo = Tipo();
{if ("" != null) return sa.cDec_Var(sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn), tipo);}
    throw new Error("Missing return statement in function");
}

  final public Dec DecTipo() throws ParseException {Tipo tipo; Token t;
    jj_consume_token(Type);
    t = jj_consume_token(Id);
    jj_consume_token(DosPuntos);
    tipo = Tipo();
{if ("" != null) return sa.cDec_Tipo(sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn), tipo);}
    throw new Error("Missing return statement in function");
}

  final public Dec DecProc() throws ParseException {LParams params; LDecs sDecs; LIns sIns; Token t;
    jj_consume_token(Proc);
    t = jj_consume_token(Id);
    params = ParamsF();
    sDecs = SDecs();
    sIns = SIns();
{if ("" != null) return sa.cDec_Proc(sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn), params, sDecs, sIns);}
    throw new Error("Missing return statement in function");
}

//Tipos
  final public     Tipo Tipo() throws ParseException {Tipo tipo; Campos campos; Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Int:{
      jj_consume_token(Int);
{if ("" != null) return sa.cInt_();}
      break;
      }
    case Real:{
      jj_consume_token(Real);
{if ("" != null) return sa.cReal_();}
      break;
      }
    case Bool:{
      jj_consume_token(Bool);
{if ("" != null) return sa.cBool_();}
      break;
      }
    case String:{
      jj_consume_token(String);
{if ("" != null) return sa.cString_();}
      break;
      }
    case Id:{
      t = jj_consume_token(Id);
{if ("" != null) return sa.cRef_(sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn));}
      break;
      }
    case Array:{
      jj_consume_token(Array);
      jj_consume_token(CApertura);
      t = jj_consume_token(LEntero);
      jj_consume_token(CCierre);
      jj_consume_token(Of);
      tipo = Tipo();
{if ("" != null) return sa.cArray_(sa.str(t.image, t.beginLine, t.beginColumn), tipo);}
      break;
      }
    case Record:{
      jj_consume_token(Record);
      campos = Campos();
      jj_consume_token(End);
{if ("" != null) return sa.cRecord_(campos);}
      break;
      }
    case Circunflejo:{
      jj_consume_token(Circunflejo);
      tipo = Tipo();
{if ("" != null) return sa.cPuntero_(tipo);}
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Campos Campos() throws ParseException {Campo campoT; Campos rCampos;
    campoT = CampoT();
    rCampos = RCampos(sa.cUn_Campo(campoT));
{if ("" != null) return rCampos;}
    throw new Error("Missing return statement in function");
}

  final public Campos RCampos(Campos h) throws ParseException {Campo campoT; Campos rCampos;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Id:{
      campoT = CampoT();
      rCampos = RCampos(sa.cMuchos_Campos(h, campoT));
{if ("" != null) return rCampos;}
      break;
      }
    default:
      jj_la1[3] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public Campo CampoT() throws ParseException {Tipo tipo; Token t;
    t = jj_consume_token(Id);
    jj_consume_token(DosPuntos);
    tipo = Tipo();
    jj_consume_token(PuntoComa);
{if ("" != null) return sa.cCampo(sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn), tipo);}
    throw new Error("Missing return statement in function");
}

  final public LParams ParamsF() throws ParseException {LParams lParams;
    jj_consume_token(PApertura);
    lParams = LParams();
    jj_consume_token(PCierre);
{if ("" != null) return lParams;}
    throw new Error("Missing return statement in function");
}

  final public LParams LParams() throws ParseException {LParams lParams;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Var:
    case Id:{
      lParams = LParamsAux();
{if ("" != null) return lParams;}
      break;
      }
    default:
      jj_la1[4] = jj_gen;
{if ("" != null) return sa.cSin_Params();}
    }
    throw new Error("Missing return statement in function");
}

  final public LParams LParamsAux() throws ParseException {Param paramF; LParams rLParams;
    paramF = ParamF();
    rLParams = RLParamsAux(sa.cUn_Param(paramF));
{if ("" != null) return rLParams;}
    throw new Error("Missing return statement in function");
}

  final public LParams RLParamsAux(LParams h) throws ParseException {Param paramF; LParams rLParams;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Coma:{
      jj_consume_token(Coma);
      paramF = ParamF();
      rLParams = RLParamsAux(sa.cMuchos_Params(h, paramF));
{if ("" != null) return rLParams;}
      break;
      }
    default:
      jj_la1[5] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public Param ParamF() throws ParseException {Param param;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Var:{
      param = ParamRef();
{if ("" != null) return param;}
      break;
      }
    case Id:{
      param = ParamVal();
{if ("" != null) return param;}
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Param ParamRef() throws ParseException {Tipo tipo; Token t;
    jj_consume_token(Var);
    t = jj_consume_token(Id);
    jj_consume_token(DosPuntos);
    tipo = Tipo();
{if ("" != null) return sa.cParam_Ref(sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn), tipo);}
    throw new Error("Missing return statement in function");
}

  final public Param ParamVal() throws ParseException {Tipo tipo; Token t;
    t = jj_consume_token(Id);
    jj_consume_token(DosPuntos);
    tipo = Tipo();
{if ("" != null) return sa.cParam_Val(sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn), tipo);}
    throw new Error("Missing return statement in function");
}

//Instrucciones
  final public     LIns SIns() throws ParseException {LIns lIns;
    jj_consume_token(Begin);
    lIns = LIns();
    jj_consume_token(End);
{if ("" != null) return lIns;}
    throw new Error("Missing return statement in function");
}

  final public LIns LIns() throws ParseException {LIns rLIns;
    rLIns = RLIns(sa.cSin_Ins());
{if ("" != null) return rLIns;}
    throw new Error("Missing return statement in function");
}

  final public LIns RLIns(LIns h) throws ParseException {Ins ins; LIns rLIns;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Resta:
    case PApertura:
    case Not:
    case Null:
    case True:
    case False:
    case If:
    case While:
    case Seq:
    case New:
    case Delete:
    case Read:
    case Write:
    case Nl:
    case Id:
    case LEntero:
    case LReal:
    case LCadena:{
      ins = Ins();
      rLIns = RLIns(sa.cMuchas_Ins(h, ins));
{if ("" != null) return rLIns;}
      break;
      }
    default:
      jj_la1[7] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public Ins Ins() throws ParseException {Ins ins;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Resta:
    case PApertura:
    case Not:
    case Null:
    case True:
    case False:
    case Id:
    case LEntero:
    case LReal:
    case LCadena:{
      ins = IAltp();
{if ("" != null) return ins;}
      break;
      }
    case If:{
      ins = IAlt();
{if ("" != null) return ins;}
      break;
      }
    case While:{
      ins = IWhile();
{if ("" != null) return ins;}
      break;
      }
    case Read:{
      ins = IRead();
{if ("" != null) return ins;}
      break;
      }
    case Write:{
      ins = IWrite();
{if ("" != null) return ins;}
      break;
      }
    case Nl:{
      ins = INl();
{if ("" != null) return ins;}
      break;
      }
    case New:{
      ins = INew();
{if ("" != null) return ins;}
      break;
      }
    case Delete:{
      ins = IDelete();
{if ("" != null) return ins;}
      break;
      }
    case Seq:{
      ins = IComp();
{if ("" != null) return ins;}
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Ins IAltp() throws ParseException {E e; Ins rIAltp;
    e = E();
    rIAltp = RIAltp(e);
{if ("" != null) return rIAltp;}
    throw new Error("Missing return statement in function");
}

  final public Ins RIAltp(E h) throws ParseException {E e; LExp lExp;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Igual:{
      jj_consume_token(Igual);
      e = E();
      jj_consume_token(PuntoComa);
{if ("" != null) return sa.cAsignacion_(h, e);}
      break;
      }
    case PApertura:{
      lExp = ParamsR();
{if ("" != null) return sa.cCall_Proc(h, lExp);}
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Ins IAlt() throws ParseException {E e; LIns lIns; Ins ins;
    jj_consume_token(If);
    e = E();
    jj_consume_token(Then);
    lIns = LIns();
    ins = RIAlt(e, lIns);
{if ("" != null) return ins;}
    throw new Error("Missing return statement in function");
}

  final public Ins RIAlt(E eh, LIns lh) throws ParseException {LIns lIns;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Else:{
      jj_consume_token(Else);
      lIns = LIns();
      jj_consume_token(End);
      TO();
{if ("" != null) return sa.cIf_Then_Else(eh, lh, lIns);}
      break;
      }
    case End:{
      jj_consume_token(End);
      TO();
{if ("" != null) return sa.cIf_Then(eh, lh);}
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Ins IWhile() throws ParseException {E e; LIns lIns;
    jj_consume_token(While);
    e = E();
    jj_consume_token(Do);
    lIns = LIns();
    jj_consume_token(End);
    TO();
{if ("" != null) return sa.cWhile_(e, lIns);}
    throw new Error("Missing return statement in function");
}

  final public Ins IRead() throws ParseException {E e;
    jj_consume_token(Read);
    e = E();
    jj_consume_token(PuntoComa);
{if ("" != null) return sa.cRead_(e);}
    throw new Error("Missing return statement in function");
}

  final public Ins IWrite() throws ParseException {E e;
    jj_consume_token(Write);
    e = E();
    jj_consume_token(PuntoComa);
{if ("" != null) return sa.cWrite_(e);}
    throw new Error("Missing return statement in function");
}

  final public Ins INl() throws ParseException {
    jj_consume_token(Nl);
    jj_consume_token(PuntoComa);
{if ("" != null) return sa.cNl_();}
    throw new Error("Missing return statement in function");
}

  final public Ins INew() throws ParseException {E e;
    jj_consume_token(New);
    e = E();
    jj_consume_token(PuntoComa);
{if ("" != null) return sa.cNew_(e);}
    throw new Error("Missing return statement in function");
}

  final public Ins IDelete() throws ParseException {E e;
    jj_consume_token(Delete);
    e = E();
    jj_consume_token(PuntoComa);
{if ("" != null) return sa.cDelete_(e);}
    throw new Error("Missing return statement in function");
}

  final public LExp ParamsR() throws ParseException {LExp lExp;
    jj_consume_token(PApertura);
    lExp = LExp();
    jj_consume_token(PCierre);
    jj_consume_token(PuntoComa);
{if ("" != null) return lExp;}
    throw new Error("Missing return statement in function");
}

  final public Ins IComp() throws ParseException {LDecs lDecs; LIns lIns;
    jj_consume_token(Seq);
    lDecs = SDecs();
    lIns = SIns();
    TO();
{if ("" != null) return sa.cIns_Compuesta(lDecs, lIns);}
    throw new Error("Missing return statement in function");
}

  final public void TO() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PuntoComa:{
      jj_consume_token(PuntoComa);

      break;
      }
    default:
      jj_la1[11] = jj_gen;

    }
}

  final public LExp LExp() throws ParseException {LExp lExp;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Resta:
    case PApertura:
    case Not:
    case Null:
    case True:
    case False:
    case Id:
    case LEntero:
    case LReal:
    case LCadena:{
      lExp = LExpAux();
{if ("" != null) return lExp;}
      break;
      }
    default:
      jj_la1[12] = jj_gen;
{if ("" != null) return sa.cSin_Expr();}
    }
    throw new Error("Missing return statement in function");
}

  final public LExp LExpAux() throws ParseException {E e; LExp lExp;
    e = E();
    lExp = RLExpAux(sa.cUna_Expr(e));
{if ("" != null) return lExp;}
    throw new Error("Missing return statement in function");
}

  final public LExp RLExpAux(LExp h) throws ParseException {E e; LExp lExp;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Coma:{
      jj_consume_token(Coma);
      e = E();
      lExp = RLExpAux(sa.cMuchas_Expr(h, e));
{if ("" != null) return lExp;}
      break;
      }
    default:
      jj_la1[13] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public E E() throws ParseException {E e; E re;
    e = E1();
    re = RE(e);
{if ("" != null) return re;}
    throw new Error("Missing return statement in function");
}

  final public E RE(E h) throws ParseException {E e; String op;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BLT:
    case BGT:
    case BLE:
    case BGE:
    case BEQ:
    case BNE:{
      op = OpRel();
      e = E1();
{if ("" != null) return sa.cExpBin(h, op, e);}
      break;
      }
    default:
      jj_la1[14] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public E E1() throws ParseException {E e2; E rE1; E rE1p;
    e2 = E2();
    rE1 = RE1(e2);
    rE1p = RE1p(rE1);
{if ("" != null) return rE1p;}
    throw new Error("Missing return statement in function");
}

  final public E RE1p(E h) throws ParseException {E e; E rE1p;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Resta:{
      jj_consume_token(Resta);
      e = E2();
      rE1p = RE1p(sa.cResta(h, e));
{if ("" != null) return rE1p;}
      break;
      }
    default:
      jj_la1[15] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public E RE1(E h) throws ParseException {E e;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Suma:{
      jj_consume_token(Suma);
      e = E2();
{if ("" != null) return sa.cSuma(h, e);}
      break;
      }
    default:
      jj_la1[16] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public E E2() throws ParseException {E e; E rE2;
    e = E3();
    rE2 = RE2(e);
{if ("" != null) return rE2;}
    throw new Error("Missing return statement in function");
}

  final public E RE2(E h) throws ParseException {E e;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case And:{
      jj_consume_token(And);
      e = E3();
{if ("" != null) return sa.cAnd(h, e);}
      break;
      }
    case Or:{
      jj_consume_token(Or);
      e = E2();
{if ("" != null) return sa.cOr(h, e);}
      break;
      }
    default:
      jj_la1[17] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public E E3() throws ParseException {E e; E rE3;
    e = E4();
    rE3 = RE3(e);
{if ("" != null) return rE3;}
    throw new Error("Missing return statement in function");
}

  final public E RE3(E h) throws ParseException {E e; String op; E rE3;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Multiplica:
    case Divide:
    case Modulo:{
      op = Op3();
      e = E4();
      rE3 = RE3(sa.cExpBin(h, op, e));
{if ("" != null) return rE3;}
      break;
      }
    default:
      jj_la1[18] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public E E4() throws ParseException {String op; E e;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Resta:
    case Not:{
      op = Op4();
      e = E4();
{if ("" != null) return sa.cExpUnPref(op, e);}
      break;
      }
    case PApertura:
    case Null:
    case True:
    case False:
    case Id:
    case LEntero:
    case LReal:
    case LCadena:{
      e = E5();
{if ("" != null) return e;}
      break;
      }
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public E E5() throws ParseException {E e; E rE5;
    e = EBasic();
    rE5 = RE5(e);
{if ("" != null) return rE5;}
    throw new Error("Missing return statement in function");
}

  final public E RE5(E h) throws ParseException {E e1; Token t; E e2;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CApertura:{
      jj_consume_token(CApertura);
      e1 = E();
      jj_consume_token(CCierre);
      e2 = RE5(sa.cIndex(h, e1));
{if ("" != null) return e2;}
      break;
      }
    case Punto:{
      jj_consume_token(Punto);
      t = jj_consume_token(Id);
      e2 = RE5(sa.cAccess(h, sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn)));
{if ("" != null) return e2;}
      break;
      }
    case Circunflejo:{
      jj_consume_token(Circunflejo);
      e2 = RE5(sa.cIndir(h));
{if ("" != null) return e2;}
      break;
      }
    default:
      jj_la1[20] = jj_gen;
{if ("" != null) return h;}
    }
    throw new Error("Missing return statement in function");
}

  final public E EBasic() throws ParseException {E e; Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LEntero:{
      t = jj_consume_token(LEntero);
{if ("" != null) return sa.cInt(sa.str(t.image, t.beginLine, t.beginColumn));}
      break;
      }
    case LReal:{
      t = jj_consume_token(LReal);
{if ("" != null) return sa.cReal(sa.str(t.image, t.beginLine, t.beginColumn));}
      break;
      }
    case True:{
      jj_consume_token(True);
{if ("" != null) return sa.cTrue();}
      break;
      }
    case False:{
      jj_consume_token(False);
{if ("" != null) return sa.cFalse();}
      break;
      }
    case LCadena:{
      t = jj_consume_token(LCadena);
{if ("" != null) return sa.cCadena(sa.str(t.image, t.beginLine, t.beginColumn));}
      break;
      }
    case Id:{
      t = jj_consume_token(Id);
{if ("" != null) return sa.cId(sa.str(t.image.toLowerCase(), t.beginLine, t.beginColumn));}
      break;
      }
    case Null:{
      jj_consume_token(Null);
{if ("" != null) return sa.cNull();}
      break;
      }
    case PApertura:{
      jj_consume_token(PApertura);
      e = E();
      jj_consume_token(PCierre);
{if ("" != null) return e;}
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public String OpRel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BLT:{
      jj_consume_token(BLT);
{if ("" != null) return "<";}
      break;
      }
    case BGT:{
      jj_consume_token(BGT);
{if ("" != null) return ">";}
      break;
      }
    case BLE:{
      jj_consume_token(BLE);
{if ("" != null) return "<=";}
      break;
      }
    case BGE:{
      jj_consume_token(BGE);
{if ("" != null) return ">=";}
      break;
      }
    case BNE:{
      jj_consume_token(BNE);
{if ("" != null) return "!=";}
      break;
      }
    case BEQ:{
      jj_consume_token(BEQ);
{if ("" != null) return "==";}
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public String Op3() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Multiplica:{
      jj_consume_token(Multiplica);
{if ("" != null) return "*";}
      break;
      }
    case Divide:{
      jj_consume_token(Divide);
{if ("" != null) return "/";}
      break;
      }
    case Modulo:{
      jj_consume_token(Modulo);
{if ("" != null) return "%";}
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public String Op4() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case Resta:{
      jj_consume_token(Resta);
{if ("" != null) return "-";}
      break;
      }
    case Not:{
      jj_consume_token(Not);
{if ("" != null) return "not";}
      break;
      }
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  /** Generated Token Manager. */
  public ConstructorASTDTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[25];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x0,0x0,0xc8000000,0x0,0x0,0x10000000,0x0,0x100400,0x100400,0x900000,0x0,0x400000,0x100400,0x10000000,0xfc000,0x400,0x200,0x0,0x3800,0x100400,0xd000000,0x100000,0xfc000,0x3800,0x400,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x6000100,0x6000100,0x8060003,0x8000000,0xa000000,0x0,0xa000000,0x79f052f0,0x79f052f0,0x0,0x10800,0x0,0x780000f0,0x0,0x0,0x0,0x0,0xc,0x0,0x780000f0,0x0,0x780000e0,0x0,0x0,0x10,};
	}

  /** Constructor with InputStream. */
  public ConstructorASTD(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ConstructorASTD(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ConstructorASTDTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ConstructorASTD(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ConstructorASTDTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ConstructorASTDTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ConstructorASTD(ConstructorASTDTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ConstructorASTDTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[63];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 25; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 63; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
