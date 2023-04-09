package implementacion.procesamientos;

import java.util.Stack;

import implementacion.abstractSintax.ProcesamientoPorDefecto;
import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.maquinaP.MaquinaP;

public class GenCodigo extends ProcesamientoPorDefecto {
	private Stack<Dec_Proc> procs = new Stack<Dec_Proc>();
	private MaquinaP m;
	
	public GenCodigo(MaquinaP m) {
		this.m = m;
	}

	@Override
	public void procesa(Prog_ prog) {
		prog.getlIns().procesa(this);
		m.ponInstruccion(m.stop());
		recolectaProcs(prog.getlDecs());
		while(!procs.empty()) {
			Dec_Proc p = procs.pop();
			p.procesa(this);
		}
	}

	@Override
	public void procesa(Dec_Proc dec) {
		dec.getlIns().procesa(this);
		m.ponInstruccion(m.desactiva(dec.getNivel(), dec.getTamDatos()));
		m.ponInstruccion(m.irInd());
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
	
	//Instrucciones
	@Override
	public void procesa(Sin_Ins lIns) {
		//Skip()
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
				m.ponInstruccion(m.apilaInd());
			}
			m.ponInstruccion(m.intToReal());
			m.ponInstruccion(m.desapilaInd());
		}
		else {
			if(ins.getE2() instanceof Id) {//Sí, es redundante, pero se deja así por claridad
				m.ponInstruccion(m.mueve(ins.getE2().getTipo().getTam()));
			}
			else {
				m.ponInstruccion(m.desapilaInd());
			}
		}
	}

	@Override
	public void procesa(If_Then ins) {
		ins.getE().procesa(this);
		if(ins.getE() instanceof Id) {
			m.ponInstruccion(m.apilaInd());
		}
		m.ponInstruccion(m.irF(ins.getSig()));
		ins.getLIns().procesa(this);
	}

	@Override
	public void procesa(If_Then_Else ins) {
		ins.getE().procesa(this);
		if(ins.getE() instanceof Id) {
			m.ponInstruccion(m.apilaInd());
		}
		m.ponInstruccion(m.irF(ins.getLIns2().getIni()));
		ins.getLIns1().procesa(this);
		m.ponInstruccion(m.irA(ins.getSig()));
		ins.getLIns2().procesa(this);
	}

	@Override
	public void procesa(While_ ins) {
		ins.getE().procesa(this);
		if(ins.getE() instanceof Id) {
			m.ponInstruccion(m.apilaInd());
		}
		m.ponInstruccion(m.irF(ins.getSig()));
		ins.getLIns().procesa(this);
		m.ponInstruccion(m.irA(ins.getIni()));
	}

	@Override
	public void procesa(Read_ ins) {
		ins.getE().procesa(this);
		if (ins.getE().getTipo() instanceof String_) {
			m.ponInstruccion(m.readString());
		}
		else if (ins.getE().getTipo() instanceof Int_) {
			m.ponInstruccion(m.readInt());
		}
		else if (ins.getE().getTipo() instanceof Real_) {
			m.ponInstruccion(m.readReal());
		}
		else
			System.out.println("Error inesperado en el tipo de la lectura; error de generación de instrucciones");
		m.ponInstruccion(m.desapilaInd());
	}

	@Override
	public void procesa(Write_ ins) {
		ins.getE().procesa(this);
		if(ins.getE() instanceof Id) {
			m.ponInstruccion(m.apilaInd());
		}
		m.ponInstruccion(m.write());
	}

	@Override
	public void procesa(Nl_ ins) {
		m.ponInstruccion(m.nl());
	}

	@Override
	public void procesa(New_ ins) {
		ins.getE().procesa(this);
		m.ponInstruccion(m.alloc(ins.getE().getTipo().getTam()));
		m.ponInstruccion(m.desapilaInd());
	}

	@Override
	public void procesa(Delete_ ins) {
		ins.getE().procesa(this);
		m.ponInstruccion(m.apilaInt(-1));
		m.ponInstruccion(m.beq());
		m.ponInstruccion(m.irF(ins.getSigStop()));
		m.ponInstruccion(m.stop());
		m.ponInstruccion(m.dealloc(ins.getE().getTipo().getTam()));
	}

	@Override
	public void procesa(Call_Proc ins) {
		m.ponInstruccion(m.activa(((Dec_Proc)((Id) ins.getE()).getVinculo()).getNivel(), 
				((Dec_Proc)((Id) ins.getE()).getVinculo()).getTamDatos(), ins.getSig()));
		//Etiqueta params
		genCodParams(((Dec_Proc) ((Id) ins.getE()).getVinculo()).getlParams(),
				ins.getLExp());
		m.ponInstruccion(m.desapilad(((Dec_Proc)((Id) ins.getE()).getVinculo()).getNivel()));
		m.ponInstruccion(m.irA((((Dec_Proc)((Id) ins.getE()).getVinculo()).getIni())));
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
		m.ponInstruccion(m.apilaInt(Integer.parseInt(e.getStr())));
	}

	@Override
	public void procesa(Real e) {
		m.ponInstruccion(m.apilaReal(Double.parseDouble(e.getStr())));
	}

	@Override
	public void procesa(True e) {
		m.ponInstruccion(m.apilaBool(true));
	}

	@Override
	public void procesa(False e) {
		m.ponInstruccion(m.apilaBool(false));
	}

	@Override
	public void procesa(Cadena e) {
		m.ponInstruccion(m.apilaString(e.getStr()));
	}

	@Override
	public void procesa(Null e) {
		m.ponInstruccion(m.apilaInt(-1));
	}

	@Override
	public void procesa(Id e) {
		if(e.getVinculo().getNivel() == 0) {
			m.ponInstruccion(m.apilaInt(((Dec_Var) e.getVinculo()).getDir()));
		}
		else {
			m.ponInstruccion(m.apilad( e.getVinculo().getNivel()));
			m.ponInstruccion(m.apilaInt( e.getVinculo().getDir()));
			m.ponInstruccion(m.sumaInt());
			if(e.getVinculo() instanceof Param_Ref) {
				m.ponInstruccion(m.apilaInd());
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
		e.setSigStop(etq+4);
		etq += 5;
	}

	private void genCodParams(LParams lParams, LExp lExp) {
		if(lParams instanceof Sin_Params && lExp instanceof Sin_Expr) {
			//skip
		}
		else if(lParams instanceof Un_Param && lExp instanceof Una_Expr) {
			etiquetaPaso(((Un_Param) lParams).getParam(), ((Una_Expr) lExp).getE());
		}
		else if(lParams instanceof Muchos_Params && lExp instanceof Muchas_Expr) {
			genCodParams(lParams, lExp);
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
