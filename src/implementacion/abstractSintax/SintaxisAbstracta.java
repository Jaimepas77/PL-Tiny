package implementacion.abstractSintax;

public class SintaxisAbstracta {
	public static abstract class Plantilla {//Borrar (sirve de plantilla)
		public Plantilla() {
		}
		public abstract void procesa(Procesamiento p);
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
			this.str = str;
			this.t = t;
		}
		public String getStr() {
			return str;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(implementacion.abstractSintax.Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Dec_Tipo extends Dec {
		private String str;
		private Tipo t;
		public Dec_Tipo(String str, Tipo t) {
			this.str = str;
			this.t = t;
		}
		public String getStr() {
			return str;
		}
		public Tipo getT() {
			return t;
		}
		public void procesa(implementacion.abstractSintax.Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Dec_Proc extends Dec {
		private String str;
		private LParams lParams;
		private LDecs lDecs;
		private LIns lIns;
		public Dec_Proc(String str, LParams lParams, LDecs lDecs, LInsl Ins) {
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
		public void procesa(implementacion.abstractSintax.Procesamiento p) {
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
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Una_Dec extends LDecs {
		private Dec dec;
		public Una_Dec(Dec dec) {
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
}
