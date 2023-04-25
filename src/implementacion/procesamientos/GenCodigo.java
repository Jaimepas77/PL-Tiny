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
		dec.getLIns().procesa(this);
		m.ponInstruccion(m.desactiva(dec.getNivel(), dec.getTamDatos()));
		m.ponInstruccion(m.irInd());
		recolectaProcs(dec.getLDecs());
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
		if(Util.ref_exc(ins.getE1().getT()) instanceof Real_ && Util.ref_exc(ins.getE2().getT()) instanceof Int_) {
			if(esDesignador(ins.getE2())) {
				m.ponInstruccion(m.apilaInd());
			}
			m.ponInstruccion(m.intToReal());
			m.ponInstruccion(m.desapilaInd());
		}
		else {
			if(esDesignador(ins.getE2())) {//Sí, es redundante, pero se deja así por claridad
				m.ponInstruccion(m.mueve(ins.getE2().getT().getTam()));
			}
			else {
				m.ponInstruccion(m.desapilaInd());
			}
		}
	}

	@Override
	public void procesa(If_Then ins) {
		ins.getE().procesa(this);
		if(esDesignador(ins.getE())) {
			m.ponInstruccion(m.apilaInd());
		}
		m.ponInstruccion(m.irF(ins.getSig()));
		ins.getLIns().procesa(this);
	}

	@Override
	public void procesa(If_Then_Else ins) {
		ins.getE().procesa(this);
		if(esDesignador(ins.getE())) {
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
		if(esDesignador(ins.getE())) {
			m.ponInstruccion(m.apilaInd());
		}
		m.ponInstruccion(m.irF(ins.getSig()));
		ins.getLIns().procesa(this);
		m.ponInstruccion(m.irA(ins.getIni()));
	}

	@Override
	public void procesa(Read_ ins) {
		ins.getE().procesa(this);
		if (Util.ref_exc(ins.getE().getT()) instanceof String_) {
			m.ponInstruccion(m.readString());
		}
		else if (Util.ref_exc(ins.getE().getT()) instanceof Int_) {
			m.ponInstruccion(m.readInt());
		}
		else if (Util.ref_exc(ins.getE().getT()) instanceof Real_) {
			m.ponInstruccion(m.readReal());
		}
		else
			System.out.println("Error inesperado en el tipo de la lectura; error de generación de instrucciones");
		m.ponInstruccion(m.desapilaInd());
	}

	@Override
	public void procesa(Write_ ins) {
		ins.getE().procesa(this);
		if(esDesignador(ins.getE())) {
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
		m.ponInstruccion(m.alloc(ins.getE().getT().getTam()));
		m.ponInstruccion(m.desapilaInd());
	}

	@Override
	public void procesa(Delete_ ins) {
		ins.getE().procesa(this);
		m.ponInstruccion(m.dup());
		m.ponInstruccion(m.apilaInt(-1));
		m.ponInstruccion(m.beqInt());
		m.ponInstruccion(m.irF(ins.getSigStop()));
		m.ponInstruccion(m.stop());
		m.ponInstruccion(m.dealloc(ins.getE().getT().getTam()));
	}

	@Override
	public void procesa(Call_Proc ins) {
		m.ponInstruccion(m.activa(((Dec_Proc)((Id) ins.getE()).getVinculo()).getNivel(), 
				((Dec_Proc)((Id) ins.getE()).getVinculo()).getTamDatos(), ins.getSig()));
		//Etiqueta params
		genCodParams(((Dec_Proc) ((Id) ins.getE()).getVinculo()).getLParams(),
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
			if (e.getVinculo() instanceof Dec_Var)
				m.ponInstruccion(m.apilaInt(((Dec_Var) e.getVinculo()).getDir()));
			else
				m.ponInstruccion(m.apilaInt(((Param) e.getVinculo()).getDir()));
			m.ponInstruccion(m.sumaInt());
			if(e.getVinculo() instanceof Param_Ref) {
				m.ponInstruccion(m.apilaInd());
			}
		}
	}
	
	//Op relacionales
	private void genCodBinRel(EBinario e) {
		e.getArg0().procesa(this);
		if(esDesignador(e.getArg0())) {
			m.ponInstruccion(m.apilaInd());
		}
		if(Util.ref_exc(e.getArg1().getT()) instanceof Real_) {
			if(Util.ref_exc(e.getArg0().getT()) instanceof Int_) {
				m.ponInstruccion(m.intToReal());
			}
		}
		e.getArg1().procesa(this);
		if(esDesignador(e.getArg1())) {
			m.ponInstruccion(m.apilaInd());
		}
		if(Util.ref_exc(e.getArg0().getT()) instanceof Real_) {
			if(Util.ref_exc(e.getArg1().getT()) instanceof Int_) {
				m.ponInstruccion(m.intToReal());
			}
		}
	}

	@Override
	public void procesa(Blt e) {
		genCodBinRel(e);
		if(Util.ref_exc(e.getArg0().getT()) instanceof Real_ || Util.ref_exc(e.getArg1().getT()) instanceof Real_)
			m.ponInstruccion(m.bltReal());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Int_)
			m.ponInstruccion(m.bltInt());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof String_)
			m.ponInstruccion(m.bltString());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Bool_)
			m.ponInstruccion(m.bltBool());
		else
			System.out.println("Error de ejecución en operador relativo <: tipo no reconocido.");
	}

	@Override
	public void procesa(Ble e) {
		genCodBinRel(e);
		if(Util.ref_exc(e.getArg0().getT()) instanceof Real_ || Util.ref_exc(e.getArg1().getT()) instanceof Real_)
			m.ponInstruccion(m.bleReal());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Int_)
			m.ponInstruccion(m.bleInt());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof String_)
			m.ponInstruccion(m.bleString());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Bool_)
			m.ponInstruccion(m.bleBool());
		else
			System.out.println("Error de ejecución en operador relativo <=: tipo no reconocido.");
	}

	@Override
	public void procesa(Bgt e) {
		genCodBinRel(e);
		if(Util.ref_exc(e.getArg0().getT()) instanceof Real_ || Util.ref_exc(e.getArg1().getT()) instanceof Real_)
			m.ponInstruccion(m.bgtReal());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Int_)
			m.ponInstruccion(m.bgtInt());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof String_)
			m.ponInstruccion(m.bgtString());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Bool_)
			m.ponInstruccion(m.bgtBool());
		else
			System.out.println("Error de ejecución en operador relativo >: tipo no reconocido.");	}

	@Override
	public void procesa(Bge e) {
		genCodBinRel(e);
		if(Util.ref_exc(e.getArg0().getT()) instanceof Real_ || Util.ref_exc(e.getArg1().getT()) instanceof Real_)
			m.ponInstruccion(m.bgeReal());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Int_)
			m.ponInstruccion(m.bgeInt());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof String_)
			m.ponInstruccion(m.bgeString());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Bool_)
			m.ponInstruccion(m.bgeBool());
		else
			System.out.println("Error de ejecución en operador relativo >=: tipo no reconocido.");
	}

	@Override
	public void procesa(Beq e) {
		genCodBinRel(e);
		if(Util.ref_exc(e.getArg0().getT()) instanceof Real_ || Util.ref_exc(e.getArg1().getT()) instanceof Real_)
			m.ponInstruccion(m.beqReal());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Int_)
			m.ponInstruccion(m.beqInt());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof String_)
			m.ponInstruccion(m.beqString());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Bool_)
			m.ponInstruccion(m.beqBool());
		else
			System.out.println("Error de ejecución en operador relativo ==: tipo no reconocido.");
	}

	@Override
	public void procesa(Bne e) {
		genCodBinRel(e);
		if(Util.ref_exc(e.getArg0().getT()) instanceof Real_ || Util.ref_exc(e.getArg1().getT()) instanceof Real_)
			m.ponInstruccion(m.bneReal());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Int_)
			m.ponInstruccion(m.bneInt());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof String_)
			m.ponInstruccion(m.bneString());
		else if(Util.ref_exc(e.getArg0().getT()) instanceof Bool_)
			m.ponInstruccion(m.bneBool());
		else
			System.out.println("Error de ejecución en operador relativo !=: tipo no reconocido.");
	}
	
	//Op aritméticos binarios
	private void genCodBinArit(EBinario e) {
		e.getArg0().procesa(this);
		if(esDesignador(e.getArg0())) {
			m.ponInstruccion(m.apilaInd());
		}
		if(Util.ref_exc(e.getT()) instanceof Real_) {
			if(Util.ref_exc(e.getArg0().getT()) instanceof Int_) {
				m.ponInstruccion(m.intToReal());
			}
		}
		e.getArg1().procesa(this);
		if(esDesignador(e.getArg1())) {
			m.ponInstruccion(m.apilaInd());
		}
		if(Util.ref_exc(e.getT()) instanceof Real_) {
			if(Util.ref_exc(e.getArg1().getT()) instanceof Int_) {
				m.ponInstruccion(m.intToReal());
			}
		}
	}

	@Override
	public void procesa(Suma e) {
		genCodBinArit(e);
		if(Util.ref_exc(e.getT()) instanceof Real_) {
			m.ponInstruccion(m.sumaReal());
		}
		else {
			m.ponInstruccion(m.sumaInt());
		}
	}

	@Override
	public void procesa(Resta e) {
		genCodBinArit(e);
		if(Util.ref_exc(e.getT()) instanceof Real_) {
			m.ponInstruccion(m.restaReal());
		}
		else {
			m.ponInstruccion(m.restaInt());
		}
	}
	
	@Override
	public void procesa(Mult e) {
		genCodBinArit(e);
		if(Util.ref_exc(e.getT()) instanceof Real_) {
			m.ponInstruccion(m.mulReal());
		}
		else {
			m.ponInstruccion(m.mulInt());
		}
	}

	@Override
	public void procesa(Div e) {
		genCodBinArit(e);
		if(Util.ref_exc(e.getT()) instanceof Real_) {
			m.ponInstruccion(m.divReal());
		}
		else {
			m.ponInstruccion(m.divInt());
		}
	}

	@Override
	public void procesa(Mod e) {
		genCodBinArit(e);
		m.ponInstruccion(m.mod());
	}
	
	//Op lógicos binarios
	private void etiquetaBin(EBinario e) {
		e.getArg0().procesa(this);
		if(esDesignador(e.getArg0())) {
			m.ponInstruccion(m.apilaInd());
		}
		e.getArg1().procesa(this);
		if(esDesignador(e.getArg1())) {
			m.ponInstruccion(m.apilaInd());
		}
	}

	@Override
	public void procesa(And e) {
		etiquetaBin(e);
		m.ponInstruccion(m.and());
	}

	@Override
	public void procesa(Or e) {
		etiquetaBin(e);
		m.ponInstruccion(m.or());
	}
	
	//Op unarios (infijos) [nivel4]
	private void etiquetaUn(EUnario e) {
		e.getArg0().procesa(this);
		if(esDesignador(e.getArg0())) {
			m.ponInstruccion(m.apilaInd());
		}
	}

	@Override
	public void procesa(Neg e) {
		etiquetaUn(e);
		m.ponInstruccion(m.neg());
	}

	@Override
	public void procesa(Not e) {
		etiquetaUn(e);
		m.ponInstruccion(m.not());
	}

	//Operadores unarios (sufijos)
	@Override
	public void procesa(Index e) {
		e.getArg0().procesa(this);
		e.getArg1().procesa(this);
		if(esDesignador(e.getArg1())) {
			m.ponInstruccion(m.apilaInd());
		}
		m.ponInstruccion(m.apilaInt(e.getArg0().getT().getTam()));
		m.ponInstruccion(m.mulInt());
		m.ponInstruccion(m.sumaInt());
	}

	@Override
	public void procesa(Access e) {
		e.getArg0().procesa(this);
		m.ponInstruccion(m.apilaInt(buscaDespl((Record_) e.getT(), e.getStr())));
		m.ponInstruccion(m.sumaInt());
	}

	private int buscaDespl(Record_ t, String str) {
		Campos c = t.getCampos();
		while(c instanceof Muchos_Campos && !((Muchos_Campos) c).getCampo().getStr().equals(str)) {
			c = ((Muchos_Campos) c).getCampos();
		}
		
		if (c instanceof Muchos_Campos)
			return ((Muchos_Campos) c).getCampo().getDespl();
		else
			return ((Un_Campo) c).getCampo().getDespl();
	}

	@Override
	public void procesa(Indir e) {
		e.getArg0().procesa(this);
		m.ponInstruccion(m.dup());
		m.ponInstruccion(m.apilaInt(-1));
		m.ponInstruccion(m.beqInt());
		m.ponInstruccion(m.irF(e.getSigStop()));
		m.ponInstruccion(m.stop());
		m.ponInstruccion(m.apilaInd());
	}

	private void genCodParams(LParams lParams, LExp lExp) {
		if(lParams instanceof Sin_Params && lExp instanceof Sin_Expr) {
			//skip
		}
		else if(lParams instanceof Un_Param && lExp instanceof Una_Expr) {
			genCodPaso(((Un_Param) lParams).getParam(), ((Una_Expr) lExp).getE());
		}
		else if(lParams instanceof Muchos_Params && lExp instanceof Muchas_Expr) {
			genCodParams(lParams, lExp);
			genCodPaso(((Muchos_Params) lParams).getParam(), ((Una_Expr) lExp).getE());
		}
	}
	
	private void genCodPaso(Param p, E e) {
		m.ponInstruccion(m.dup());
		m.ponInstruccion(m.apilaInt(p.getDir()));
		m.ponInstruccion(m.sumaInt());
		e.procesa(this);
		if(p instanceof Param_Val) {
			if (Util.ref_exc(((Param_Val) p).getT()) instanceof Real_ && Util.ref_exc(e.getT()) instanceof Int_) {
				if(esDesignador(e)) {
					m.ponInstruccion(m.apilaInd());
				}
				m.ponInstruccion(m.intToReal());
				m.ponInstruccion(m.desapilaInd());
			}
			else {
				if(esDesignador(e)) {
					m.ponInstruccion(m.mueve(e.getT().getTam()));
				}
				else {
					m.ponInstruccion(m.desapilaInd());
				}
			}
		}
		else {
			m.ponInstruccion(m.desapilaInd());
		}
	}
	
	private boolean esDesignador(E e) {
		return Util.es_designador(e);
	}
}
