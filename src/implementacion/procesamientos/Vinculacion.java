package implementacion.procesamientos;

import java.util.HashMap;

import implementacion.abstractSintax.ProcesamientoPorDefecto;
import implementacion.abstractSintax.SintaxisAbstracta.*;

@SuppressWarnings("serial")
class Vinculos extends HashMap<String, Vinculable> {
	Vinculos padre = null;
}

public class Vinculacion extends ProcesamientoPorDefecto {
	private Vinculos ts; //Clave: id; Valor: nodo con la declaración
	private int pasada;	//Sirve para diferenciar los procesa de la primera y la segunda pasada

	@Override
	public void procesa(Prog_ prog) {
		ts = new Vinculos();
		pasada = 1;
		prog.getlDecs().procesa(this);
		pasada = 2;
		prog.getlDecs().procesa(this);
		prog.getlIns().procesa(this);
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
			if (ts.containsKey(dec.getStr())) {
				throw new RuntimeException("Constante ya definida: " + dec.getStr());
			}
			else {
				ts.put(dec.getStr(), dec);
			}
		}
	}

	@Override
	public void procesa(Dec_Proc dec) {
		if(pasada == 1) {
			if (ts.containsKey(dec.getStr())) {
				throw new RuntimeException("Constante ya definida: " + dec.getStr());
			}
			else {
				ts.put(dec.getStr(), dec);
			}
			Vinculos ant_ts = ts;
			// crea_ambito(ts)
			ts = new Vinculos();
			ts.padre = ant_ts;

			pasada = 1;
			dec.getLParams().procesa(this);
			dec.getLDecs().procesa(this);
			pasada = 2;
			dec.getLParams().procesa(this);
			dec.getLDecs().procesa(this);
			pasada = 1;
			dec.getLIns().procesa(this);
			ts = ts.padre;
		}
	}

	@Override
	public void procesa(Ref_ tipo) {
		if(pasada==1){
			Vinculable v = Util.valor_de_id(ts, tipo.getStr());
			if (v != null) 
				tipo.setVinculo(v);
			else 
				throw new RuntimeException("No existe: " + tipo.getStr());
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
		if(pasada==1){
			if(!(tipo.getT() instanceof Ref_))
				tipo.getT().procesa(this);
		} 
		else if(pasada==2) {
			if(tipo.getT() instanceof Ref_) {
				Vinculable v = Util.valor_de_id(ts, ((Ref_) tipo.getT()).getStr());
				if (v != null) 
					((Ref_) tipo.getT()).setVinculo(v);
				else 
					throw new RuntimeException("No existe: " + ((Ref_) tipo.getT()).getStr());
			}
			else {
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
		if(pasada==1){
			if (ts.containsKey(param.getStr())) {
				throw new RuntimeException("Constante ya definida: " + param.getStr());
			}
			else {
				ts.put(param.getStr(), param);
			}
		}
	}

	@Override
	public void procesa(Param_Val param) {
		param.getT().procesa(this);
		if(pasada==1){
			if (ts.containsKey(param.getStr())) {
				throw new RuntimeException("Constante ya definida: " + param.getStr());
			}
			else {
				ts.put(param.getStr(), param);
			}
		}
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
		Vinculos ant_ts = ts;
		// crea_ambito(ts)
		ts = new Vinculos();
		ts.padre = ant_ts;

		pasada = 1;
		ins.getLDecs().procesa(this);
		pasada = 2;
		ins.getLDecs().procesa(this);
		ins.getLIns().procesa(this);
		ts = ts.padre;
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
	public void procesa(Id e) {
		Vinculable v = Util.valor_de_id(ts, e.getStr());
		if (v != null) 
			e.setVinculo(v);
		else 
			throw new RuntimeException("No existe: " + e.getStr());
	}

	@Override
	public void procesa(Blt e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);
	}

	@Override
	public void procesa(Ble e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Bgt e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Bge e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Beq e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Bne e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Suma e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Resta e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(And e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Or e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Mult e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Div e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Mod e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);		
	}

	@Override
	public void procesa(Neg e) {
		e.getArg0().procesa(this);
	}

	@Override
	public void procesa(Not e) {
		e.getArg0().procesa(this);		
	}

	@Override
	public void procesa(Index e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);			
	}

	@Override
	public void procesa(Access e) {
		e.getArg0().procesa(this);		
	}

	@Override
	public void procesa(Indir e) {
		e.getArg0().procesa(this);		
	}
}