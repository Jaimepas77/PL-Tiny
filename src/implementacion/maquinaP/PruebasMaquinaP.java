package implementacion.maquinaP;

public class PruebasMaquinaP {

	public static void main(String[] args) {
		prog1();
	}
	
	private static void prog1() {
		MaquinaP m = new MaquinaP(1, 3, 0, 1);

		/*
            int x;
            proc store(int v) {
             x = v
            }
           &&
            call store(5)
		 */


		m.ponInstruccion(m.activa(1,1,8));
		m.ponInstruccion(m.dup());
		m.ponInstruccion(m.apilaInt(0));
		m.ponInstruccion(m.suma());
		m.ponInstruccion(m.apilaInt(5));
		m.ponInstruccion(m.desapilaInd());
		m.ponInstruccion(m.desapilad(1));
		m.ponInstruccion(m.irA(9));
		m.ponInstruccion(m.stop());
		m.ponInstruccion(m.apilaInt(0));
		m.ponInstruccion(m.apilad(1));
		m.ponInstruccion(m.apilaInt(0));
		m.ponInstruccion(m.suma());
		m.ponInstruccion(m.mueve(1));
		m.ponInstruccion(m.desactiva(1,1));
		m.ponInstruccion(m.irInd());       
		m.ejecuta();
		m.muestraCodigo();
		System.out.println("-------------");
		m.muestraEstado();
	}
}
