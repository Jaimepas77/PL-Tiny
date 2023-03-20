package implementacion.abstractSintax;

public class SintaxisAbstracta {
	public static abstract class Plantilla {//Borrar (sirve de plantilla)
		public Plantilla() {
		}
		public abstract void procesa(Procesamiento p);
	}

	//Prog
	public static abstract class Prog  { // Estática porque no hace nada por sí misma
		public Prog() {
		}   
		public abstract void procesa(Procesamiento p); 
	}

	public static class Prog_ extends Prog {
		private LDecs lDecs;
		private LIns lIns;
		public Prog_(LDecs lDecs, LIns lIns) {
			super();
			this.lDecs = lDecs;
			this.lIns = lIns;
		}

		public LDecs getlDecs() {
			return lDecs;
		}
		public LIns getlIns() {
			return lIns;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this); 
		}
	}

	//LDecs
	public static abstract class LDecs {
		public LDecs() {
		}
		public abstract void procesa(Procesamiento p);
	}
	
	public static class Sin_Decs extends LDecs {
		public Sin_Decs() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Una_Dec extends LDecs {
		private Dec dec;
		public Una_Dec(Dec dec) {
			super();
			this.dec = dec;
		}
		public Dec getDec() {
			return dec;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Muchas_Decs extends LDecs {
		private Dec dec;
		private LDecs decs;
		public Muchas_Decs(LDecs decs, Dec dec) {
			super();
			this.decs = decs;
			this.dec = dec;
		}
		public LDecs getDecs() {
			return decs;
		}
		public Dec getDec() {
			return dec;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	//Dec
	public static abstract class Dec {
		public Dec() {
		}
		public abstract void procesa(Procesamiento p);
	}
	
	public static class Dec_Var extends Dec {
		private String str;
		private Tipo t;
		public Dec_Var(String str, Tipo t) {
			super();
			this.str = str;
			this.t = t;
		}
		public String getStr() {
			return str;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Dec_Tipo extends Dec {
		private String str;
		private Tipo t;
		public Dec_Tipo(String str, Tipo t) {
			super();
			this.str = str;
			this.t = t;
		}
		public String getStr() {
			return str;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Dec_Proc extends Dec {
		private String str;
		private LParams lParams;
		private LDecs lDecs;
		private LIns lIns;
		public Dec_Proc(String str, LParams lParams, LDecs lDecs, LIns lIns) {
			super();
			this.str = str;
			this.lParams = lParams;
			this.lDecs = lDecs;
			this.lIns = lIns;
		}
		public String getStr() {
			return str;
		}
		public LParams getlParams() {
			return lParams;
		}
		public LDecs getlDecs() {
			return lDecs;
		}
		public LIns getlIns() {
			return lIns;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	//Tipo
	public static abstract class Tipo {
		public Tipo() {
		}
		public abstract void procesa(Procesamiento p);
	}

	public static class Int_ extends Tipo {
		public Int_() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Real_ extends Tipo {
		public Real_() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Bool_ extends Tipo {
		public Bool_() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class String_ extends Tipo {
		public String_() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Ref_ extends Tipo {
		public String str;
		public Ref_(String str) {
			super();
			this.str = str;
		}
		public String getStr() {
			return str;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Array_ extends Tipo {
		public String str;
		public Tipo t;
		public Array_(String str, Tipo t) {
			super();
			this.str = str;
			this.t = t;
		}
		public String getStr() {
			return str;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Record_ extends Tipo {
		public Campos campos;
		public Record_(Campos campos) {
			super();
			this.campos = campos;
		}
		public Campos getCampos() {
			return campos;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Puntero_ extends Tipo {
		public Tipo t;
		public Puntero_(Tipo t) {
			super();
			this.t = t;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}	
	
	//Campos
	public static abstract class Campos {
		public Campos() {
		}
		public abstract void procesa(Procesamiento p);
	}

	public static class Un_Campo extends Campos {
		private Campo campo;
		public Un_Campo(Campo campo) {
			super();
			this.campo = campo;
		}
		public Campo getCampo() {
			return campo;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Muchos_Campos extends Campos {
		private Campo campo;
		private Campos campos;
		public Muchos_Campos(Campos campos, Campo campo) {
			super();
			this.campos = campos;
			this.campo = campo;
		}
		public Campos getCampos() {
			return campos;
		}
		public Campo getCampo() {
			return campo;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Campo extends Campos {
		private String str;
		private Tipo t;
		public Campo(String str, Tipo t) {
			super();
			this.str = str;
			this.t = t;
		}
		public String getStr() {
			return str;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	//LParams
	public static abstract class LParams {
		public LParams() {
		}
		public abstract void procesa(Procesamiento p);
	}

	public static class Sin_Params extends LParams {
		public Sin_Params() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Un_Param extends LParams {
		private Param param;
		public Un_Param(Param param) {
			super();
			this.param = param;
		}
		public Param getParam() {
			return param;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Muchos_Params extends LParams {
		private LParams lParams;
		private Param param;
		public Muchos_Params(LParams lParams, Param param) {
			super();
			this.lParams = lParams;
			this.param = param;
		}
		public LParams getParams() {
			return lParams;
		}
		public Campo getParam() {
			return param;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	//Param
	public static abstract class Param {
		public Param() {
		}
		public abstract void procesa(Procesamiento p);
	}

	public static class Param_Ref extends Param {
		private String str;
		private Tipo t;
		public Param_Ref(String str, Tipo t) {
			super();
			this.str = str;
			this.t = t;
		}
		public String getStr() {
			return str;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}	

	public static class Param_Val extends Param {
		private String str;
		private Tipo t;
		public Param_Val(String str, Tipo t) {
			super();
			this.str = str;
			this.t = t;
		}
		public String getStr() {
			return str;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	//LIns
	public static abstract class LIns {
		public LIns() {
		}
		public abstract void procesa(Procesamiento p);
	}

	public static class Sin_Ins extends LIns {
		public Sin_Ins() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}	

	public static class Una_Ins extends LIns {
		private Ins ins;
		public Una_Ins(Ins ins) {
			super();
			this.ins = ins;
		}
		public Ins getIns() {
			return ins;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Muchas_Ins extends LIns {
		private LIns lIns;
		private Ins ins;
		public Muchas_Ins(LIns lIns, Ins ins) {
			super();
			this.lIns = lIns;
			this.ins = ins;
		}
		public LIns getLIns() {
			return lIns;
		}
		public Ins getIns() {
			return ins;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}	

	//Ins
	public static abstract class Ins {
		public Ins() {
		}
		public abstract void procesa(Procesamiento p);
	}	

	public static class Asignacion_ extends Ins {
		private E e1;
		private E e2;
		public Asignacion_(E e1, E e2) {
			super();
			this.e1 = e1;
			this.e2 = e2;
		}
		public E getE1() {
			return e1;
		}
		public E getE2() {
			return e2;
		}		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	public static class If_Then extends Ins {
		private E e;
		private LIns lIns;
		public If_Then(E e, LIns lIns) {
			super();
			this.e = e;
			this.lIns = lIns;
		}
		public E getE() {
			return e;
		}
		public LIns getLIns() {
			return lIns;
		}		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class If_Then_Else extends Ins {
		private E e;
		private LIns lIns1;
		private LIns lIns2;
		public If_Then_Else(E e, LIns lIns1, LIns lIns2) {
			super();
			this.e = e;
			this.lIns1 = lIns1;
			this.lIns2 = lIns2;
		}
		public E getE() {
			return e;
		}
		public LIns getLIns1() {
			return lIns1;
		}		
		public LIns getLIns2() {
			return lIns2;
		}	
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}	

	public static class While_ extends Ins {
		private E e;
		private LIns lIns;
		public While_(E e, LIns lIns) {
			super();
			this.e = e;
			this.lIns = lIns;
		}
		public E getE() {
			return e;
		}
		public LIns getLIns() {
			return lIns;
		}		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	public static class Read_ extends Ins {
		private E e;
		private Ins ins;
		public Read_(E e, Ins ins) {
			super();
			this.e = e;
			this.ins = ins;
		}
		public E getE() {
			return e;
		}
		public Ins getIns() {
			return ins;
		}		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Write_ extends Ins {
		private E e;
		private Ins ins;
		public Write_(E e, Ins ins) {
			super();
			this.e = e;
			this.ins = ins;
		}
		public E getE() {
			return e;
		}
		public Ins getIns() {
			return ins;
		}		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Nl_ extends Ins {
		public Nl_(E e, Ins ins) {
			super();
		}	
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class New_ extends Ins {
		private E e;
		public New_(E e) {
			super();
			this.e = e;
		}
		public E getE() {
			return e;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Delete_ extends Ins {
		private E e;
		public Delete_(E e) {
			super();
			this.e = e;
		}
		public E getE() {
			return e;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Call_Proc extends Ins {
		private E e;
		private LExp lExp;
		public Call_Proc(E e, LExp lExp) {
			super();
			this.e = e;
			this.lExp = lExp;
		}
		public E getE() {
			return e;
		}
		public LExp getLExp() {
			return lExp;
		}		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Ins_Compuesta extends Ins {
		private LDecs lDecs;
		private LIns lIns;
		public Call_Proc(LDecs lDecs, LIns lIns) {
			super();
			this.lDecs = lDecs;
			this.lIns = lIns;
		}
		public LDecs getLDecs() {
			return lDecs;
		}
		public LIns getLIns() {
			return lIns;
		}		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	//LExp
	public static abstract class LExp {
		public LExp() {
		}
		public abstract void procesa(Procesamiento p);
	}
	
	public static class Sin_Expr extends LExp {
		public Sin_Expr() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Una_Expr extends LExp {
		public E e;
		public Una_Expr(E e) {
			super();
			this.e = e;
		}
		public E getE() {
			return e;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Muchas_Expr extends LExp {
		public LExp lExp;
		public E e;
		public Una_Expr(LExp lExp, E e) {
			super();
			this.lExp = lExp
			this.e = e;
		}
		public E getLExp() {
			return lExp;
		}
		public E getE() {
			return e;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	//E
	public static abstract class E {
		public E() {
		}
		public abstract void procesa(Procesamiento p);
	}

	public static class Int extends E {
		public String str;
		public Int(String str) {
			super();
			this.str = str;
		}
		public String getStr() {
			return str;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Real extends E {
		public String str;
		public Real(String str) {
			super();
			this.str = str;
		}
		public String getStr() {
			return str;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class True extends E {
		public True() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class False extends E {
		public True() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Cadena extends E {
		public String str;
		public Cadena(String str) {
			super();
			this.str = str;
		}
		public String getStr() {
			return str;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Id extends E {
		public String str;
		public Id(String str) {
			super();
			this.str = str;
		}
		public String getStr() {
			return str;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Null extends E {
		public String str;
		public Null(String str) {
			super();
			this.str = str;
		}
		public String getStr() {
			return str;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static abstract class EBinario extends E {
        public E arg0;
        public E arg1;
		public EBinario(Exp arg0, Exp arg1) {
            super();
            this.arg0 = arg0;
            this.arg1 = arg1;
        }
        public E arg0() {
			return arg0;
		}
        public E arg1() {
			return arg1;
		}
    }

	public static abstract class EUnario extends E {
        public E arg0;
		public EUnario(Exp arg0) {
            super();
            this.arg0 = arg0;
        }
        public E arg0() {
			return arg0;
		}
    }

	//Nivel 0 
	private static abstract class ENivel0 extends EBinario {
		public ENivel0(E arg0, E arg1) {
			super(arg0, arg1);
		}

		public final int prioridad() {
			return 0;
		}
	}

	private static class Blt extends ENivel0 {
		public Blt(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}		
	}
	
	private static class Ble extends ENivel0 {
		public Ble(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}		
	}

	private static class Bgt extends ENivel0 {
		public Bgt(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}		
	}

	private static class Bge extends ENivel0 {
		public Bge(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}		
	}

	private static class Beq extends ENivel0 {
		public Beq(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}		
	}

	private static class Bne extends ENivel0 {
		public Bne(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}		
	}

	//Nivel 1
	public static abstract class ENivel1 extends EBinario {
        public ENivel1(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public final int prioridad() {
            return 1;
        }
    }

	public static class Suma extends ENivel1 {
        public Suma(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	public static class Resta extends ENivel1 {
        public Resta(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	//Nivel 2
	public static abstract class ENivel2 extends EBinario {
        public ENivel2(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public final int prioridad() {
            return 2;
        }
    }

	public static class And extends ENivel2 {
        public And(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	public static class Or extends ENivel2 {
        public Or(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	//Nivel 3
	public static abstract class ENivel3 extends EBinario {
        public ENivel3(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public final int prioridad() {
            return 3;
        }
    }

	public static class Mult extends ENivel3 {
        public Mult(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	public static class Div extends ENivel3 {
        public Div(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	public static class Mod extends ENivel3 {
        public Mod(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	//Nivel 4
	public static abstract class ENivel4 extends EUnario {
        public ENivel4(E arg0) {
            super(arg0);
        }
        public final int prioridad() {
            return 4;
        }
    }

	public static class Neg extends ENivel4 {
        public Neg(E arg0) {
            super(arg0);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	public static class Not extends ENivel4 {
        public Not(E arg0) {
            super(arg0);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

	//Nivel 5
	public static class Index extends EBinario {
        public Index(E arg0, E arg1) {
            super(arg0, arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this);
        }
		public final int prioridad() {
            return 5;
        }
    }

	public static class Access extends EUnario {
		public String str;
		public Access(E arg0, String str) {
			super(arg0);
			this.str = str;
		}
		public String getStr() {
			return str;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		public final int prioridad() {
            return 5;
        }
	}

	public static class Indir extends EUnario {
        public Indir(E arg0) {
            super(arg0);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }
		public final int prioridad() {
            return 5;
        }
    }	

}
