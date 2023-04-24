package implementacion.abstractSintax;

public class SintaxisAbstracta {
	
	//Nodo vinculable
	public interface Vinculable {
		Tipo getT();
	}
	
	// Prog
	public static abstract class Prog { // Estática porque no hace nada por sí misma
		public Prog() {
		}

		public abstract void procesa(Procesamiento p);
	}

	public static class Prog_ extends Prog {
		private LDecs lDecs;
		private LIns lIns;
        private SalidaTipo tipo;
		private int ini;
		
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
		public SalidaTipo getTipo() {
			return tipo;
		}
		public void setTipo(SalidaTipo tipo){
			this.tipo = tipo; 
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public int getIni() {
			return ini;
		}

		public void setIni(int ini) {
			this.ini = ini;
		}
	}

	// LDecs
	public static abstract class LDecs {
		private SalidaTipo tipo;
        public LDecs() {
		}

		public abstract void procesa(Procesamiento p);
		public SalidaTipo getTipo() {
			return tipo;
		}
		public void setTipo(SalidaTipo tipo) {
			this.tipo = tipo;
		}
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

	// Dec
	public static abstract class Dec implements Vinculable {
		private int nivel;
		public Dec() {
		}

		public abstract void procesa(Procesamiento p);
		public abstract SalidaTipo getTipo();
		public abstract Tipo getT();

		public int getNivel() {
			return nivel;
		}

		public void setNivel(int nivel) {
			this.nivel = nivel;
		}
	}

	public static class Dec_Var extends Dec {
		private String str;
		private Tipo t;
		private int dir;
		private SalidaTipo tipo;
		
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
		public int getDir() {
			return dir;
		}

		public void setDir(int dir) {
			this.dir = dir;
		}
		
		public SalidaTipo getTipo() {
			return tipo;
		}
		public void setTipo(SalidaTipo tipo) {
			this.tipo = tipo;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Dec_Tipo extends Dec {
		private String str;
		private Tipo t;
		private SalidaTipo tipo;
		
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
		public SalidaTipo getTipo() {
			return tipo;
		}
		public void setTipo(SalidaTipo tipo) {
			this.tipo = tipo;
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
		private int tamDatos;
		private Tipo t;
		private SalidaTipo tipo;
		private int ini;
		private int sig;

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

		public LParams getLParams() {
			return lParams;
		}

		public LDecs getLDecs() {
			return lDecs;
		}

		public LIns getLIns() {
			return lIns;
		}

		public int getTamDatos() {
			return tamDatos;
		}

		public void setTamDatos(int tamDatos) {
			this.tamDatos = tamDatos;
		}
		public Tipo getT() {
			return t;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		@Override
		public SalidaTipo getTipo() {
			return tipo;
		}
		public int getIni() {
			return ini;
		}

		public void setIni(int ini) {
			this.ini = ini;
		}

		public int getSig() {
			return sig;
		}

		public void setSig(int sig) {
			this.sig = sig;
		}
	}

	// Tipo
	public static abstract class Tipo {
		private int tam = -1;//-1 = null
		private SalidaTipo tipo;

		public Tipo() {}

		public abstract void procesa(Procesamiento p);

		public int getTam() {
			return tam;
		}
		public void setTam(int tam) {
			this.tam = tam;
		}
		
		public SalidaTipo getTipo() {
			return tipo;
		}
		public void setTipo(SalidaTipo tipo) {
			this.tipo= tipo; 
		}
	}

	public static class Error_ extends Tipo {
        public Error_() {
			super();
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	public static class Ok extends Tipo {
        public Ok() {
			super();
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
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
		private Tipo t;
		private Vinculable vinculo;

		public Ref_(String str) {
			super();
			this.str = str;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public void setVinculo(Vinculable vinculo) {
			this.vinculo = vinculo;
		}
		public Vinculable getVinculo(){
			return vinculo; 
		}

		public Tipo getT() {
			return t;
		}
		public void setT(Tipo t) {
			this.t = t;
		}
		
		public String getStr() {
			return str;
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

	// Campos
	public static abstract class Campos {
		private SalidaTipo tipo; 
		
		public Campos() {}
		
		public abstract void procesa(Procesamiento p);

		public SalidaTipo getTipo() {
			return tipo;
		}
		
		public void setTipo(SalidaTipo tipo) {
			this.tipo= tipo; 
		}	
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

	public static class Campo {
		private String str;
		private Tipo t;
		private int despl = 0;//Por defecto es 0

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

		public int getDespl() {
			return despl;
		}

		public void setDespl(int despl) {
			this.despl = despl;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	// LParams
	public static abstract class LParams {
		private SalidaTipo t;
		public LParams() {
		}

		public abstract void procesa(Procesamiento p);
		public SalidaTipo getT() {
			return t;
		}
		public void setTipo(SalidaTipo tipo) {
			this.t = tipo;
		}
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

		public Param getParam() {
			return param;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	// Param
	public static abstract class Param implements Vinculable {
		private Tipo t;
		private SalidaTipo tipo;
		private int dir;
		private int nivel;
		public Param() {
		}

		public abstract void procesa(Procesamiento p);
		
        public Tipo getT() {
			return t;
		}
        
		public SalidaTipo getTipo(){
			return tipo; 
		}
		public void setTipo(SalidaTipo tipo) {
			this.tipo = tipo;
		}
		
		public int getDir() {
			return dir;
		}
		public void setDir(int dir) {
			this.dir = dir;
		}

		public int getNivel() {
			return nivel;
		}

		public void setNivel(int nivel) {
			this.nivel = nivel;
		}
	}

	public static class Param_Ref extends Param {
		private String str;

		public Param_Ref(String str, Tipo t) {
			super();
			this.str = str;
			super.t = t;
		}

		public String getStr() {
			return str;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Param_Val extends Param {
		private String str;

		public Param_Val(String str, Tipo t) {
			super();
			this.str = str;
			super.t = t;
		}

		public String getStr() {
			return str;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	// LIns
	public static abstract class LIns {
		private SalidaTipo tipo;
		private int ini;
		private int sig;

		public LIns() {
		}

		public abstract void procesa(Procesamiento p);
		
		public SalidaTipo getTipo() {
			return tipo;
		}
		public void setTipo(SalidaTipo tipo) {
			this.tipo = tipo;
		}
		
		public int getIni() {
			return ini;
		}
		public void setIni(int ini) {
			this.ini = ini;
		}

		public int getSig() {
			return sig;
		}
		public void setSig(int sig) {
			this.sig = sig;
		}
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

	// Ins
	public static abstract class Ins {
		private SalidaTipo tipo;
		private int ini;
		private int sig;

		public Ins() {
		}

		public abstract void procesa(Procesamiento p);
		
		public SalidaTipo getT() {
			return tipo;
		}
		
		public void setTipo(SalidaTipo tipo) {
			this.tipo = tipo;
		}

		public int getIni() {
			return ini;
		}
		public void setIni(int ini) {
			this.ini = ini;
		}

		public int getSig() {
			return sig;
		}
		public void setSig(int sig) {
			this.sig = sig;
		}
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

		public Read_(E e) {
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

	public static class Write_ extends Ins {
		private E e;

		public Write_(E e) {
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

	public static class Nl_ extends Ins {
		public Nl_() {
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
		private int sigStop;

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

		public int getSigStop() {
			return sigStop;
		}

		public void setSigStop(int sigStop) {
			this.sigStop = sigStop;
		}
	}

	public static class Call_Proc extends Ins {
		private E e;
		private LExp lExp;
		private LParams lParams;

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
		public LParams getLParams() {
			return lParams;
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Ins_Compuesta extends Ins {
		private LDecs lDecs;
		private LIns lIns;

		public Ins_Compuesta(LDecs lDecs, LIns lIns) {
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

	// LExp
	public static abstract class LExp {
		private SalidaTipo tipo;

		private int ini;
		private int sig;

		public LExp() {
		}

		public abstract void procesa(Procesamiento p);
		
		public void setTipo(SalidaTipo tipo) {
			this.tipo = tipo;
		}
		public SalidaTipo getTipo() {
			return tipo;
		}
		
		public int getIni() {
			return ini;
		}
		public void setIni(int ini) {
			this.ini = ini;
		}
		
		public int getSig() {
			return sig;
		}
		public void setSig(int sig) {
			this.sig = sig;
		}
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

		public Muchas_Expr(LExp lExp, E e) {
			super();
			this.lExp = lExp;
			this.e = e;
		}

		public LExp getLExp() {
			return lExp;
		}

		public E getE() {
			return e;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	// E - Expresión
	public static abstract class E {
		private Tipo tipo;
		private int ini;
		private int sig;

		public E() {
		}

		public abstract void procesa(Procesamiento p);
		
		public void setT(Tipo tipo) {
			this.tipo = tipo;
		}
		public Tipo getT() {
			return tipo;
		}
		
		public int getIni() {
			return ini;
		}
		public void setIni(int ini) {
			this.ini = ini;
		}

		public int getSig() {
			return sig;
		}
		public void setSig(int sig) {
			this.sig = sig;
		}
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
		public False() {
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
		private Vinculable vinculo;

		public Id(String str) {
			super();
			this.str = str;
		}

		public String getStr() {
			return str;
		}

		public Vinculable getVinculo() {
			return vinculo;
		}

		public void setVinculo(Vinculable vinculo) {
			this.vinculo = vinculo;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Null extends E {
		public Null() {
			super();
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static abstract class EBinario extends E {
		public E arg0;
		public E arg1;

		public EBinario(E arg0, E arg1) {
			super();
			this.arg0 = arg0;
			this.arg1 = arg1;
		}

		public E getArg0() {
			return arg0;
		}

		public E getArg1() {
			return arg1;
		}
	}

	public static abstract class EUnario extends E {
		public E arg0;

		public EUnario(E arg0) {
			super();
			this.arg0 = arg0;
		}

		public E getArg0() {
			return arg0;
		}
	}

	// Nivel 0
	private static abstract class ENivel0 extends EBinario {
		public ENivel0(E arg0, E arg1) {
			super(arg0, arg1);
		}
	}

	public static class Blt extends ENivel0 {
		public Blt(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Ble extends ENivel0 {
		public Ble(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Bgt extends ENivel0 {
		public Bgt(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Bge extends ENivel0 {
		public Bge(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Beq extends ENivel0 {
		public Beq(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Bne extends ENivel0 {
		public Bne(E arg0, E arg1) {
			super(arg0, arg1);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	// Nivel 1
	private static abstract class ENivel1 extends EBinario {
		public ENivel1(E arg0, E arg1) {
			super(arg0, arg1);
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

	// Nivel 2
	private static abstract class ENivel2 extends EBinario {
		public ENivel2(E arg0, E arg1) {
			super(arg0, arg1);
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

	// Nivel 3
	private static abstract class ENivel3 extends EBinario {
		public ENivel3(E arg0, E arg1) {
			super(arg0, arg1);
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

	// Nivel 4
	private static abstract class ENivel4 extends EUnario {
		public ENivel4(E arg0) {
			super(arg0);
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

	// Nivel 5
	public static class Index extends EBinario {
		public Index(E arg0, E arg1) {
			super(arg0, arg1);
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
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
	}

	public static class Indir extends EUnario {
		private int sigStop;

		public Indir(E arg0) {
			super(arg0);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public int getSigStop() {
			return sigStop;
		}

		public void setSigStop(int sigStop) {
			this.sigStop = sigStop;
		}
	}

	// Constructores
	public Prog cProg_(LDecs lDecs, LIns lIns) {
		return new Prog_(lDecs, lIns);
	}

	// LDecs
	public LDecs cSin_Decs() {
		return new Sin_Decs();
	}

	public LDecs cUna_Dec(Dec dec) {
		return new Una_Dec(dec);
	}

	public LDecs cMuchas_Decs(LDecs decs, Dec dec) {
		return new Muchas_Decs(decs, dec);
	}

	// Dec
	public Dec cDec_Var(String str, Tipo t) {
		return new Dec_Var(str, t);
	}

	public Dec cDec_Tipo(String str, Tipo t) {
		return new Dec_Tipo(str, t);
	}

	public Dec cDec_Proc(String str, LParams lParams, LDecs lDecs, LIns lIns) {
		return new Dec_Proc(str, lParams, lDecs, lIns);
	}

	// Tipo
	public Tipo cInt_() {
		return new Int_();
	}

	public Tipo cReal_() {
		return new Real_();
	}

	public Tipo cBool_() {
		return new Bool_();
	}

	public Tipo cString_() {
		return new String_();
	}

	public Tipo cRef_(String str) {
		return new Ref_(str);
	}

	public Tipo cArray_(String str, Tipo t) {
		return new Array_(str, t);
	}

	public Tipo cRecord_(Campos campos) {
		return new Record_(campos);
	}

	public Tipo cPuntero_(Tipo t) {
		return new Puntero_(t);
	}

	// Campos
	public Campos cUn_Campo(Campo campo) {
		return new Un_Campo(campo);
	}

	public Campos cMuchos_Campos(Campos campos, Campo campo) {
		return new Muchos_Campos(campos, campo);
	}

	public Campo cCampo(String str, Tipo t) {
		return new Campo(str, t);
	}

	// LParams
	public LParams cSin_Params() {
		return new Sin_Params();
	}

	public LParams cUn_Param(Param param) {
		return new Un_Param(param);
	}

	public LParams cMuchos_Params(LParams lParams, Param param) {
		return new Muchos_Params(lParams, param);
	}

	// Param
	public Param cParam_Ref(String str, Tipo t) {
		return new Param_Ref(str, t);
	}

	public Param cParam_Val(String str, Tipo t) {
		return new Param_Val(str, t);
	}

	// LIns
	public LIns cSin_Ins() {
		return new Sin_Ins();
	}

	public LIns cUna_Ins(Ins ins) {
		return new Una_Ins(ins);
	}

	public LIns cMuchas_Ins(LIns lIns, Ins ins) {
		return new Muchas_Ins(lIns, ins);
	}

	// Ins
	public Ins cAsignacion_(E e1, E e2) {
		return new Asignacion_(e1, e2);
	}

	public Ins cIf_Then(E e, LIns lIns) {
		return new If_Then(e, lIns);
	}

	public Ins cIf_Then_Else(E e, LIns lIns1, LIns lIns2) {
		return new If_Then_Else(e, lIns1, lIns2);
	}

	public Ins cWhile_(E e, LIns lIns) {
		return new While_(e, lIns);
	}

	public Ins cRead_(E e) {
		return new Read_(e);
	}

	public Ins cWrite_(E e) {
		return new Write_(e);
	}

	public Ins cNl_() {
		return new Nl_();
	}

	public Ins cNew_(E e) {
		return new New_(e);
	}

	public Ins cDelete_(E e) {
		return new Delete_(e);
	}

	public Ins cCall_Proc(E e, LExp lExp) {
		return new Call_Proc(e, lExp);
	}

	public Ins cIns_Compuesta(LDecs lDecs, LIns lIns) {
		return new Ins_Compuesta(lDecs, lIns);
	}

	// LExp
	public LExp cSin_Expr() {
		return new Sin_Expr();
	}

	public LExp cUna_Expr(E e) {
		return new Una_Expr(e);
	}

	public LExp cMuchas_Expr(LExp lExp, E e) {
		return new Muchas_Expr(lExp, e);
	}

	// E - Expresiones
	public E cInt(String str) {
		return new Int(str);
	}

	public E cReal(String str) {
		return new Real(str);
	}

	public E cTrue() {
		return new True();
	}

	public E cFalse() {
		return new False();
	}

	public E cCadena(String str) {
		return new Cadena(str);
	}

	public E cId(String str) {
		return new Id(str);
	}

	public E cNull() {
		return new Null();
	}

	public E cBlt(E arg0, E arg1) {
		return new Blt(arg0, arg1);
	}

	public E cBle(E arg0, E arg1) {
		return new Ble(arg0, arg1);
	}

	public E cBgt(E arg0, E arg1) {
		return new Bgt(arg0, arg1);
	}

	public E cBge(E arg0, E arg1) {
		return new Bge(arg0, arg1);
	}

	public E cBeq(E arg0, E arg1) {
		return new Beq(arg0, arg1);
	}

	public E cBne(E arg0, E arg1) {
		return new Bne(arg0, arg1);
	}

	public E cSuma(E arg0, E arg1) {
		return new Suma(arg0, arg1);
	}

	public E cResta(E arg0, E arg1) {
		return new Resta(arg0, arg1);
	}

	public E cAnd(E arg0, E arg1) {
		return new And(arg0, arg1);
	}

	public E cOr(E arg0, E arg1) {
		return new Or(arg0, arg1);
	}

	public E cMult(E arg0, E arg1) {
		return new Mult(arg0, arg1);
	}

	public E cDiv(E arg0, E arg1) {
		return new Div(arg0, arg1);
	}

	public E cMod(E arg0, E arg1) {
		return new Mod(arg0, arg1);
	}

	public E cNeg(E arg0) {
		return new Neg(arg0);
	}

	public E cNot(E arg0) {
		return new Not(arg0);
	}

	public E cIndex(E arg0, E arg1) {
		return new Index(arg0, arg1);
	}

	public E cAccess(E arg0, String str) {
		return new Access(arg0, str);
	}

	public E cIndir(E arg0) {
		return new Indir(arg0);
	}
}