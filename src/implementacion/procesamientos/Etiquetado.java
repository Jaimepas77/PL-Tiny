package implementacion.procesamientos;

import java.util.Stack;

import implementacion.abstractSintax.Procesamiento;
import implementacion.abstractSintax.ProcesamientoPorDefecto;
import implementacion.abstractSintax.SintaxisAbstracta.*;

public class Etiquetado extends ProcesamientoPorDefecto {
	private int etq = 0;
	private Stack<Dec_Proc> procs = new Stack<>();
	
	@Override
	public void procesa(Prog_ prog) {
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
		dec.ini = etq;
		dec.getlIns().procesa(this);
		etq += 2;
		recolectaProcs(dec.getlDecs());
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
			recolectaProcs(((Una_Dec) lDecs).getDec());
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
		if(ins.getE1().getTipo() instanceof Real_ && ins.getE2().getTipo() instanceof Int_) {
			if(ins.getE2() instanceof Id) {
				etq++;
			}
			etq += 2;
		}
		else {
			if(ins.getE2() instanceof Id) {//Sí, es redundante, pero se deja así por claridad
				etq++;
			}
			else {
				etq++;
			}
		}
	}

	@Override
	public void procesa(If_Then ins) {
		ins.getE().procesa(this);
		if(ins.getE() instanceof Id) {
			etq++;
		}
		etq++;
		ins.getLIns().procesa(this);
		ins.sig = etq;
	}

	@Override
	public void procesa(If_Then_Else ins) {
		ins.getE().procesa(this);
		if(ins.getE() instanceof Id) {
			etq++;
		}
		etq++;
		ins.getLIns1().procesa(this);
		etq++;
		ins.getLIns2().ini = etq;
		ins.getLIns2().procesa(this);
		ins.sig = etq;
	}

	@Override
	public void procesa(While_ ins) {
		ins.ini = etq;
		ins.getE().procesa(this);
		if(ins.getE() instanceof Id) {
			etq++;
		}
		etq++;
		ins.getLIns().procesa(this);
		etq++;
		ins.sig = etq;
	}

	@Override
	public void procesa(Read_ ins) {
		ins.getE().procesa(this);
		etq += 2;
	}

	@Override
	public void procesa(Write_ ins) {
		ins.getE().procesa(this);
		if(ins.getE() instanceof Id) {
			etq++;
		}
		etq++;
	}

	@Override
	public void procesa(Nl_ ins) {
		etq++;
	}

	@Override
	public void procesa(New_ ins) {
		ins.getE().procesa(this);
		etq += 2;
	}

	@Override
	public void procesa(Delete_ ins) {
		ins.getE().procesa(this);
		ins.sigStop = etq + 4;
		etq += 5;
	}

	@Override
	public void procesa(Call_Proc ins) {
		etq++;
		//Etiqueta params
		etiquetaParams(ins.getE().getVinculo().getLParams(), ins.getE().getVinculo().getLExpr());
		etq += 2;
		ins.sig = etq;
	}

	@Override
	public void procesa(Ins_Compuesta ins) {
		ins.getLIns().procesa(this);
		recolectaProcs(ins.getLDecs());
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
		etq++;
	}

	@Override
	public void procesa(Real e) {
		etq++;
	}

	@Override
	public void procesa(True e) {
		etq++;
	}

	@Override
	public void procesa(False e) {
		etq++;
	}

	@Override
	public void procesa(Cadena e) {
		etq++;
	}

	@Override
	public void procesa(Null e) {
		etq++;
	}

	@Override
	public void procesa(Id e) {
		if(e.getVinculo().nivel == 0) {
			etq++;
		}
		else {
			etq += 3;
			if(e.getVinculo() instanceof Param_Ref) {
				etq++;
			}
		}
	}
	
	//Op relacionales
	private void etiquetaBinRel(EBinario e) {
		e.getArg0().procesa(this);
		if(e.getArg0() instanceof Id) {
			etq++;
		}
		e.getArg1().procesa(this);
		if(e.getArg1() instanceof Id) {
			etq++;
		}
		etq++;
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
		e.getArg0().procesa(this);
		if(e.getArg0().getTipo() instanceof Id) {
			etq++;
		}
		if(e.getTipo() instanceof Real_) {
			if(e.getArg0().getTipo() instanceof Int_) {
				etq++;
			}
		}
		e.getArg1().procesa(this);
		if(e.getArg1() instanceof Id) {
			etq++;
		}
		if(e.getTipo() instanceof Real_) {
			if(e.getArg1().getTipo() instanceof Int_) {
				etq++;
			}
		}
		etq++;
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
		e.getArg0().procesa(this);
		if(e.getArg0().getTipo() instanceof Id) {
			etq++;
		}
		e.getArg1().procesa(this);
		if(e.getArg1() instanceof Id) {
			etq++;
		}
		etq++;
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
		e.getArg0().procesa(this);
		if(e.getArg0().getTipo() instanceof Id) {
			etq++;
		}
		etq++;
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
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);
		if(e.getArg1().getTipo() instanceof Id) {
			etq++;
		}
		etq += 3;
	}

	@Override
	public void procesa(Access e) {
		e.getArg0().procesa(this);
		etq += 2;
	}

	@Override
	public void procesa(Indir e) {
		e.getArg0().procesa(this);
		e.sigStop = etq+4;
		etq += 5;
	}

	private void etiquetaParams(LParams lParams, LExp lExp) {
		if(lParams instanceof Sin_Params && lExp instanceof Sin_Expr) {
			//skip
		}
		else if(lParams instanceof Un_Param && lExp instanceof Una_Expr) {
			etiquetaPaso(((Un_Param) lParams).getParam(), ((Una_Expr) lExp).getE());
		}
		else if(lParams instanceof Muchos_Params && lExp instanceof Muchas_Expr) {
			etiquetaParams(lParams, lExp);
			etiquetaPaso(((Muchos_Params) lParams).getParam(), ((Una_Expr) lExp).getE());
		}
	}
	
	private void etiquetaPaso(Param p, E e) {
		etq += 3;
		e.procesa(this);
		if(p instanceof Param_Val) {
			if (((Param_Val) p).getT() instanceof Real_ && e.getTipo() instanceof Int_) {
				if(e instanceof Id) {
					etq++;
				}
				etq += 2;
			}
			else {
				if(e instanceof Id) {
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
}
