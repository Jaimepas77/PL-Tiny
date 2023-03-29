package implementacion.procesamientos;

import java.util.HashMap;

import implementacion.abstractSintax.Procesamiento;
import implementacion.abstractSintax.SintaxisAbstracta.*;

class Vinculos extends HashMap<String, Dec> {}

public class Vinculacion implements Procesamiento {

	private Vinculos ts, ant_ts;	//Clave: id; Valor: nodo con la declaración
	private int pasada = 1;	//Sirve para diferenciar los procesa de la primera y la segunda pasada
	private Dec vinculo;

	@Override
	public void procesa(Prog_ prog) {
		ts = new Vinculos();
		prog.getlDecs().procesa(this);
		pasada = 2;
		prog.getlDecs().procesa(this);
		prog.getlIns().procesa(this);
	}

	@Override
	public void procesa(Sin_Decs lDecs) {
		//skip
	}

	@Override
	public void procesa(Una_Dec lDecs) {
		lDecs.getDec().procesa(this);
	}

	@Override
	public void procesa(Muchas_Decs lDecs) {
		lDecs.getDecs().procesa(this);
		lDecs.getDec().procesa(this);
	}

	@Override
	public void procesa(Dec_Var dec) {
		dec.getT().procesa(this);
		if(pasada == 1) {
			if (ts.containsKey(dec.getStr())) throw new RuntimeException("Constante ya definida: " + dec.getStr());
			else ts.put(dec.getStr(), dec);
		}
	}

	@Override
	public void procesa(Dec_Tipo dec) {
		dec.getT().procesa(this);
		if(pasada == 1) {
			if (ts.containsKey(dec.getStr())) throw new RuntimeException("Constante ya definida: " + dec.getStr());
			else ts.put(dec.getStr(), dec);
		}
	}

	@Override
	public void procesa(Dec_Proc dec) {
		if(pasada == 1) {
			if (ts.containsKey(dec.getStr())) throw new RuntimeException("Constante ya definida: " + dec.getStr());
			else ts.put(dec.getStr(), dec);
		}
		ant_ts = ts;
		//TODO crea_ambito(ts)
		dec.getlParams().procesa(this);
		dec.getlDecs().procesa(this);
		pasada = 2;
		dec.getlParams().procesa(this);
		dec.getlDecs().procesa(this);
		dec.getlIns().procesa(this);
		ts = ant_ts;
	}

	@Override
	public void procesa(Int_ tipo) {
		//skip
	}

	@Override
	public void procesa(Real_ tipo) {
		//skip
	}

	@Override
	public void procesa(Bool_ tipo) {
		//skip
	}

	@Override
	public void procesa(String_ tipo) {
		//skip
	}

	@Override
	public void procesa(Ref_ tipo) {
		if(pasada==1){
			if (ts.containsKey(tipo.getStr())) vinculo = ts.get(tipo.getStr());
			else throw new RuntimeException("No existe: " + tipo.getStr());
		}
	}

	@Override
	public void procesa(Array_ tipo) {
		tipo.getT().procesa(this);
	}

	@Override
	public void procesa(Record_ tipo) {
		tipo.getCampos().procesa(this);
	}

	@Override
	public void procesa(Puntero_ tipo) {
		// TODO Medio invent
		if(pasada==1){
			if(!(tipo.getT() instanceof Ref_)) tipo.getT().procesa(this);
		} else if(pasada==2){
			// TODO Duda no sabemos de donde cogger ref(id)m de la condición tipo==ref(id)
			if(tipo.getT() instanceof Ref_){
				if (ts.containsKey(tipo.getT())) vinculo = ts.get(tipo.getT());
				else throw new RuntimeException("No existe: " + tipo.getT());
			} else{
				tipo.getT().procesa(this);
			}
		}
	}

	@Override
	public void procesa(Un_Campo campos) {
		campos.getCampo().procesa(this);
	}

	@Override
	public void procesa(Muchos_Campos campos) {
		campos.getCampos().procesa(this);
		campos.getCampo().procesa(this);
	}

	@Override
	public void procesa(Campo campo) {
		campo.getT().procesa(this);
	}

	@Override
	public void procesa(Sin_Params lParams) {
		//skip
	}

	@Override
	public void procesa(Un_Param lParams) {
		lParams.getParam().procesa(this);
	}

	@Override
	public void procesa(Muchos_Params lParams) {
		lParams.getParams().procesa(this);
		lParams.getParam().procesa(this);
	}

	@Override
	public void procesa(Param_Ref param) {
		param.getT().procesa(this);
	}

	@Override
	public void procesa(Param_Val param) {
		param.getT().procesa(this);
		if(pasada==1){
			if (ts.containsKey(param.getStr())) throw new RuntimeException("Constante ya definida: " + param.getStr());
			else ts.put(param.getStr(), vinculo);	//TODO NO ESTAMOS SEGUROS DE "vinculo"
		}
	}
	@Override
    public void procesa(Sin_Ins lIns) {
        //skip
    }
	@Override
	public void procesa(Una_Ins lIns) {
		lIns.getIns().procesa(this);
	}

	@Override
	public void procesa(Muchas_Ins lIns) {
		lIns.getLIns().procesa(this);
		lIns.getIns().procesa(this);
		
	}

	@Override
	public void procesa(Asignacion_ ins) {
		ins.getE1().procesa(this);
		ins.getE2().procesa(this);
	}

	@Override
	public void procesa(If_Then ins) {
		ins.getE().procesa(this);
		ins.getLIns().procesa(this);
	}

	@Override
	public void procesa(If_Then_Else ins) {
		ins.getE().procesa(this);
		ins.getLIns1().procesa(this);
		ins.getLIns2().procesa(this);
	}

	@Override
	public void procesa(While_ ins) {
		ins.getE().procesa(this);
		ins.getLIns().procesa(this);		
	}

	@Override
	public void procesa(Read_ ins) {
		ins.getE().procesa(this);
	}

	@Override
	public void procesa(Write_ ins) {
		ins.getE().procesa(this);
	}

	@Override
	public void procesa(Nl_ ins) {
		//skip
	}

	@Override
	public void procesa(New_ ins) {
		ins.getE().procesa(this);
	}

	@Override
	public void procesa(Delete_ ins) {
		ins.getE().procesa(this);
	}

	@Override
	public void procesa(Call_Proc ins) {
		ins.getE().procesa(this);
		ins.getLExp().procesa(this);
	}

	@Override
	public void procesa(Ins_Compuesta ins) {
		ant_ts = ts;
		//TODO crea_ambito(ts)
		ins.getLDecs().procesa(this);
		pasada = 2;
		ins.getLDecs().procesa(this);
		ins.getLIns().procesa(this);
		ts = ant_ts;
	}

	@Override
	public void procesa(Sin_Expr lExp) {
		//skip
	}

	@Override
	public void procesa(Una_Expr lExp) {
		lExp.getE().procesa(this);
	}

	@Override
	public void procesa(Muchas_Expr lExp) {
		lExp.getLExp().procesa(this);
		lExp.getE().procesa(this);
	}

	@Override
	public void procesa(Int e) {
		//skip
	}

	@Override
	public void procesa(Real e) {
		//skip
	}

	@Override
	public void procesa(True e) {
		//skip
	}

	@Override
	public void procesa(False e) {
		//skip
	}

	@Override
	public void procesa(Cadena e) {
		//skip
	}

	@Override
	public void procesa(Id e) {
		if (ts.containsKey(e.getStr())) vinculo = ts.get(e.getStr());
			else throw new RuntimeException("No existe: " + e.getStr());
	}

	@Override
	public void procesa(Null e) {
		//skip
	}

	@Override
	public void procesa(Blt e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);
	}

	@Override
	public void procesa(Ble e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Bgt e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Bge e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Beq e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Bne e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Suma e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Resta e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(And e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Or e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Mult e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Div e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Mod e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);		
	}

	@Override
	public void procesa(Neg e) {
		e.arg0().procesa(this);
	}

	@Override
	public void procesa(Not e) {
		e.arg0().procesa(this);		
	}

	@Override
	public void procesa(Index e) {
		e.arg0.procesa(this);
		e.arg1.procesa(this);			
	}

	@Override
	public void procesa(Access e) {
		e.arg0().procesa(this);		
	}

	@Override
	public void procesa(Indir e) {
		e.arg0().procesa(this);		
	}
}