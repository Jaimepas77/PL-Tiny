package implementacion.procesamientos;

import java.util.Stack;

import implementacion.abstractSintax.ProcesamientoPorDefecto;
import implementacion.abstractSintax.SintaxisAbstracta.*;

public class Etiquetado extends ProcesamientoPorDefecto {
	private int etq = 0;
	private Stack<Dec_Proc> procs = new Stack<>();
	
	@Override
	public void procesa(Prog_ prog) {
		prog.setIni(etq);
		prog.getlIns().procesa(this);
		etq++;
		recolectaProcs(prog.getlDecs());
		while(!procs.empty()) {
			Dec_Proc p = procs.pop();
			p.procesa(this);
		}
	}

	@Override
	public void procesa(Dec_Proc dec) {
		dec.setIni(etq);
		dec.getLIns().procesa(this);
		etq += 2;
		recolectaProcs(dec.getLDecs());
		dec.setSig(etq);
	}

	private void recolectaProcs(LDecs lDecs) {
		if(lDecs instanceof Sin_Decs) {
			//Skip
		}
		else if(lDecs instanceof Una_Dec) {
			recolectaProcs(((Una_Dec) lDecs).getDec());
		}
		else if(lDecs instanceof Muchas_Decs) {
			recolectaProcs(((Muchas_Decs) lDecs).getDecs());
			recolectaProcs(((Muchas_Decs) lDecs).getDec());
		}
	}

	private void recolectaProcs(Dec dec) {
		if(dec instanceof Dec_Proc) {
			procs.push((Dec_Proc) dec);
		}
		else {
			//skip
		}
	}
	
	@Override
	public void procesa(Sin_Ins lIns) {
		lIns.setIni(etq);
		lIns.setSig(etq);
	}

	@Override
	public void procesa(Una_Ins lIns) {
		lIns.setIni(etq);
		lIns.getIns().procesa(this);
		lIns.setSig(etq);
	}

	@Override
	public void procesa(Muchas_Ins lIns) {
		lIns.setIni(etq);
		lIns.getLIns().procesa(this);
		lIns.getIns().procesa(this);
		lIns.setSig(etq);
	}

	@Override
	public void procesa(Asignacion_ ins) {
		ins.setIni(etq);
		ins.getE1().procesa(this);
		ins.getE2().procesa(this);
		if(Util.ref_exc(ins.getE1().getT()) instanceof Real_ && Util.ref_exc(ins.getE2().getT()) instanceof Int_) {
			if(esDesignador(ins.getE2())) {
				etq++;
			}
			etq += 2;
		}
		else if (Util.ref_exc(ins.getE1().getT()) instanceof Array_ &&
				((Array_)Util.ref_exc(ins.getE1().getT())).getT() instanceof Real_ &&
				((Array_)Util.ref_exc(ins.getE2().getT())).getT() instanceof Int_) {
			etiq_inttoreal_array(ins.getE1(), ins.getE2(),
					Integer.parseInt(((Array_)Util.ref_exc(ins.getE1().getT())).getStr()));
		}
		else {
			if(esDesignador(ins.getE2())) {//Sí, es redundante, pero se deja así por claridad
				etq++;
			}
			else {
				etq++;
			}
		}
		ins.setSig(etq);
	}

	@Override
	public void procesa(If_Then ins) {
		ins.setIni(etq);
		ins.getE().procesa(this);
		if(esDesignador(ins.getE())) {
			etq++;
		}
		etq++;
		ins.getLIns().procesa(this);
		ins.setSig(etq);
	}

	@Override
	public void procesa(If_Then_Else ins) {
		ins.setIni(etq);
		ins.getE().procesa(this);
		if(esDesignador(ins.getE())) {
			etq++;
		}
		etq++;
		ins.getLIns1().procesa(this);
		etq++;
		ins.getLIns2().procesa(this);
		ins.setSig(etq);
	}

	@Override
	public void procesa(While_ ins) {
		ins.setIni(etq);
		ins.getE().procesa(this);
		if(esDesignador(ins.getE())) {
			etq++;
		}
		etq++;
		ins.getLIns().procesa(this);
		etq++;
		ins.setSig(etq);
	}

	@Override
	public void procesa(Read_ ins) {
		ins.setIni(etq);
		ins.getE().procesa(this);
		etq += 2;
		ins.setSig(etq);
	}

	@Override
	public void procesa(Write_ ins) {
		ins.setIni(etq);
		ins.getE().procesa(this);
		if(esDesignador(ins.getE())) {
			etq++;
		}
		etq++;
		ins.setSig(etq);
	}

	@Override
	public void procesa(Nl_ ins) {
		ins.setIni(etq);
		etq++;
		ins.setSig(etq);
	}

	@Override
	public void procesa(New_ ins) {
		ins.setIni(etq);
		ins.getE().procesa(this);
		etq += 2;
		ins.setSig(etq);
	}

	@Override
	public void procesa(Delete_ ins) {
		ins.setIni(etq);
		ins.getE().procesa(this);
		ins.setSigStop(etq + 5);
		etq += 6;
		ins.setSig(etq);
	}

	@Override
	public void procesa(Call_Proc ins) {
		ins.setIni(etq);
		etq++;
		//Etiqueta params
		etiquetaParams(( (Dec_Proc) ((Id) ins.getE()).getVinculo()).getLParams(), ins.getLExp());
		etq += 2;
		ins.setSig(etq);
	}

	@Override
	public void procesa(Ins_Compuesta ins) {
		ins.setIni(etq);
		ins.getLIns().procesa(this);
		recolectaProcs(ins.getLDecs());
		ins.setSig(etq);
	}

	@Override
	public void procesa(Sin_Expr lExp) {
		lExp.setIni(etq);
		lExp.setSig(etq);
	}

	@Override
	public void procesa(Una_Expr lExp) {
		lExp.setIni(etq);
		lExp.getE().procesa(this);
		lExp.setSig(etq);
	}

	@Override
	public void procesa(Muchas_Expr lExp) {
		lExp.setIni(etq);
		lExp.getLExp().procesa(this);
		lExp.getE().procesa(this);
		lExp.setSig(etq);
	}

	@Override
	public void procesa(Int e) {
		e.setIni(etq);
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(Real e) {
		e.setIni(etq);
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(True e) {
		e.setIni(etq);
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(False e) {
		e.setIni(etq);
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(Cadena e) {
		e.setIni(etq);
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(Null e) {
		e.setIni(etq);
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(Id e) {
		e.setIni(etq);
		if(e.getVinculo().getNivel() == 0) {
			etq++;
		}
		else {
			etq += 3;
			if(e.getVinculo() instanceof Param_Ref) {
				etq++;
			}
		}
		e.setSig(etq);
	}
	
	//Op relacionales
	private void etiquetaBinRel(EBinario e) {
		e.setIni(etq);
		e.getArg0().procesa(this);
		if(esDesignador(e.getArg0())) {
			etq++;
		}
		if(Util.ref_exc(e.getArg1().getT()) instanceof Real_) {
			if(Util.ref_exc(e.getArg0().getT()) instanceof Int_) {
				etq++;
			}
		}
		e.getArg1().procesa(this);
		if(esDesignador(e.getArg1())) {
			etq++;
		}
		if(Util.ref_exc(e.getArg0().getT()) instanceof Real_) {
			if(Util.ref_exc(e.getArg1().getT()) instanceof Int_) {
				etq++;
			}
		}
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(Blt e) {
		etiquetaBinRel(e);
	}

	@Override
	public void procesa(Ble e) {
		etiquetaBinRel(e);
	}

	@Override
	public void procesa(Bgt e) {
		etiquetaBinRel(e);
	}

	@Override
	public void procesa(Bge e) {
		etiquetaBinRel(e);
	}

	@Override
	public void procesa(Beq e) {
		etiquetaBinRel(e);
	}

	@Override
	public void procesa(Bne e) {
		etiquetaBinRel(e);
	}
	
	//Op aritméticos binarios
	private void etiquetaBinArit(EBinario e) {
		e.setIni(etq);
		e.getArg0().procesa(this);
		if(esDesignador(e.getArg0())) {
			
			etq++;
		}
		if(Util.ref_exc(e.getT()) instanceof Real_) {
			if(Util.ref_exc(e.getArg0().getT()) instanceof Int_) {
				etq++;
			}
		}
		e.getArg1().procesa(this);
		if(esDesignador(e.getArg1())) {
			etq++;
		}
		if(Util.ref_exc(e.getT()) instanceof Real_) {
			if(Util.ref_exc(e.getArg1().getT()) instanceof Int_) {
				etq++;
			}
		}
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(Suma e) {
		etiquetaBinArit(e);
		}

	@Override
	public void procesa(Resta e) {
		etiquetaBinArit(e);
	}
	
	@Override
	public void procesa(Mult e) {
		etiquetaBinArit(e);
	}

	@Override
	public void procesa(Div e) {
		etiquetaBinArit(e);
	}

	@Override
	public void procesa(Mod e) {
		etiquetaBinArit(e);
	}
	
	//Op lógicos binarios
	private void etiquetaBin(EBinario e) {
		e.setIni(etq);
		e.getArg0().procesa(this);
		if(esDesignador(e.getArg0())) {
			etq++;
		}
		e.getArg1().procesa(this);
		if(esDesignador(e.getArg1())) {
			etq++;
		}
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(And e) {
		etiquetaBin(e);
	}

	@Override
	public void procesa(Or e) {
		etiquetaBin(e);
	}
	
	//Op unarios (infijos) [nivel4]
	private void etiquetaUn(EUnario e) {
		e.setIni(etq);
		e.getArg0().procesa(this);
		if(esDesignador(e.getArg0())) {
			etq++;
		}
		etq++;
		e.setSig(etq);
	}

	@Override
	public void procesa(Neg e) {
		etiquetaUn(e);
	}

	@Override
	public void procesa(Not e) {
		etiquetaUn(e);
	}

	@Override
	public void procesa(Index e) {
		e.setIni(etq);
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);
		if(esDesignador(e.getArg1())) {
			etq++;
		}
		etq += 3;
		e.setSig(etq);
	}

	@Override
	public void procesa(Access e) {
		e.setIni(etq);
		e.getArg0().procesa(this);
		etq += 2;
		e.setSig(etq);
	}

	@Override
	public void procesa(Indir e) {
		e.setIni(etq);
		e.getArg0().procesa(this);
		e.setSigStop(etq+6);
		etq += 6;
		e.setSig(etq);
	}

	private void etiquetaParams(LParams lParams, LExp lExp) {
		if(lParams instanceof Sin_Params && lExp instanceof Sin_Expr) {
			//skip
		}
		else if(lParams instanceof Un_Param && lExp instanceof Una_Expr) {
			etiquetaPaso(((Un_Param) lParams).getParam(), ((Una_Expr) lExp).getE());
		}
		else if(lParams instanceof Muchos_Params && lExp instanceof Muchas_Expr) {
			etiquetaParams(((Muchos_Params) lParams).getParams(), ((Muchas_Expr) lExp).getLExp());
			etiquetaPaso(((Muchos_Params) lParams).getParam(), ((Muchas_Expr) lExp).getE());
		}
	}
	
	private void etiquetaPaso(Param p, E e) {
		etq += 3;
		e.procesa(this);
		if(p instanceof Param_Val) {
			if (Util.ref_exc(((Param_Val) p).getT()) instanceof Real_ && Util.ref_exc(e.getT()) instanceof Int_) {
				if(esDesignador(e)) {
					etq++;
				}
				etq += 2;
			}
			else if (Util.ref_exc(p.getT()) instanceof Array_ &&
					((Array_)Util.ref_exc(p.getT())).getT() instanceof Real_ &&
					((Array_)Util.ref_exc(e.getT())).getT() instanceof Int_) {
				etiq_inttoreal_array(p, e,
						Integer.parseInt(((Array_)Util.ref_exc(e.getT())).getStr()));
			}
			else {
				if(esDesignador(e)) {
					etq++;
				}
				else {
					etq++;
				}
			}
		}
		else {
			etq++;
		}
	}
	
	private boolean esDesignador(E e) {
		return Util.es_designador(e);
	}

	private void etiq_inttoreal_array(E e1, E e2, int tam) {
		if(tam == 0) {
			etq += 2;
		}
		else {
			etq += 3;
			for (int i = 1; i < tam; i++) {
				e1.procesa(this);
				etq += 4;
				e2.procesa(this);
				etq += 7;
			}
		}
	}

	private void etiq_inttoreal_array(Param p, E e2, int tam) {
		if(tam == 0) {
			etq += 2;
		}
		else {
			etq += 3;
			for (int i = 1; i < tam; i++) {
				etq += 7;
				e2.procesa(this);
				etq += 7;
			}
		}
	}
}
