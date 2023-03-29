package implementacion.procesamientos;

import implementacion.abstractSintax.Procesamiento;
import implementacion.abstractSintax.SintaxisAbstracta.*;

public class AsignacionEspacio implements Procesamiento {
	private int dir = 0;
	private int nivel = 0;
	private int pasada = 1;//Variable a usar cuando se quiere procesar un nodo en dos pasadas (indica en cual estamos)
	
	@Override
	public void procesa(Prog_ prog) {
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
		dec.setDir(dir);
		dec.setNivel(nivel);
		asignaEspacioTipo(dec.getT());
		dir = dir + dec.getT().getTam();
	}

	@Override
	public void procesa(Dec_Tipo dec) {
		asignaEspacioTipo(dec.getT());
	}

	@Override
	public void procesa(Dec_Proc dec) {
		int antDir = dir;
		nivel++;
		
		dec.setNivel(nivel);
		dir = 0;//Dirs relativas para el procedimiento
		dec.getlParams().procesa(this);
		dec.getlDecs().procesa(this);
		dec.getlIns().procesa(this);
		dec.setTamDatos(dir);//Espacio ocupado
		
		dir = antDir;
		nivel--;
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
		param.setDir(dir);
		param.setNivel(nivel);
		param.getT().procesa(this);
		dir++;
	}

	@Override
	public void procesa(Param_Val param) {
		param.setDir(dir);
		param.setNivel(nivel);
		param.getT().procesa(this);
		dir += param.getT().getTam();
	}

	private void asignaEspacioTipo(Tipo t) {
		if (t.getTam() == -1) {
			pasada = 1;
			t.procesa(this);
			pasada++;
			t.procesa(this);
		}
	}

	@Override
	public void procesa(Int_ tipo) {
		if (pasada == 1)
			tipo.setTam(1);
		else
			;//skip
	}

	@Override
	public void procesa(Real_ tipo) {
		if(pasada == 1)
			tipo.setTam(1);
		else
			;//skip
	}

	@Override
	public void procesa(Bool_ tipo) {
		if(pasada == 1)
			tipo.setTam(1);
		else
			;//skip
	}

	@Override
	public void procesa(String_ tipo) {
		if(pasada == 1)
			tipo.setTam(1);
		else
			;//skip
	}

	@Override
	public void procesa(Ref_ tipo) {
		if(pasada == 1)
			tipo.setTam(tipo.getVinculo().getT().getTam());
		else
			;//skip
	}

	@Override
	public void procesa(Array_ tipo) {
		if(pasada == 1) {
			tipo.getT().procesa(this);
			tipo.setTam(Integer.parseInt(tipo.getStr()) * tipo.getT().getTam());
		}
		else {//pasada = 2
			tipo.getT().procesa(this);
		}
	}

	@Override
	public void procesa(Record_ tipo) {
		if(pasada == 1) {
			if(tipo.getCampos() instanceof Muchos_Campos)
				tipo.setTam(asignaDesplazamientos((Muchos_Campos) tipo.getCampos()));
			else
				tipo.setTam(asignaDesplazamiento((Un_Campo) tipo.getCampos()));
		}
		else
			tipo.getCampos().procesa(this);
	}

	@Override
	public void procesa(Puntero_ tipo) {
		if(pasada == 1) {
			tipo.setTam(1);
			if(!(tipo.getT() instanceof Ref_)) {
				tipo.getT().procesa(this);
			}
		}
		else {
			if(tipo.getT() instanceof Ref_) {
				tipo.getVinculo().getT().procesa(this);
				tipo.setTam(tipo.getVinculo().getT().getTam());
			}
			else 
				tipo.getT().procesa(this);
		}
	}

	private int asignaDesplazamiento(Un_Campo campos) {
		return asignaDesplazamiento(campos.getCampo());
	}

	private int asignaDesplazamientos(Muchos_Campos campos) {
		if(campos.getCampos() instanceof Muchos_Campos)
			campos.getCampo().setDespl(asignaDesplazamientos((Muchos_Campos) campos.getCampos()));
		else if(campos.getCampos() instanceof Un_Campo)
			campos.getCampo().setDespl(asignaDesplazamiento((Un_Campo) campos.getCampos()));
		
		return campos.getCampo().getDespl() + asignaDesplazamiento(campos.getCampo());
	}
	
	private int asignaDesplazamiento(Campo campo) {
		campo.getT().procesa(this);
		return campo.getT().getTam();
	}

	@Override
	public void procesa(Un_Campo campos) {
		campos.getCampo().procesa(this);
	}

	@Override
	public void procesa(Muchos_Campos campos) {
		campos.getCampos().procesa(this);
		campos.getCampo().getT().procesa(this);
	}

	@Override
	public void procesa(Campo campo) {
		//skip
	}

	//Instrucciones
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
		//skip
	}

	@Override
	public void procesa(If_Then ins) {
		ins.getLIns().procesa(this);
	}

	@Override
	public void procesa(If_Then_Else ins) {
		ins.getLIns1().procesa(this);
		ins.getLIns2().procesa(this);
	}

	@Override
	public void procesa(While_ ins) {
		ins.getLIns().procesa(this);
	}

	@Override
	public void procesa(Read_ ins) {
		//skip
	}

	@Override
	public void procesa(Write_ ins) {
		//skip
	}

	@Override
	public void procesa(Nl_ ins) {
		//skip
	}

	@Override
	public void procesa(New_ ins) {
		//skip
	}

	@Override
	public void procesa(Delete_ ins) {
		//skip
	}

	@Override
	public void procesa(Call_Proc ins) {
		//skip
	}

	@Override
	public void procesa(Ins_Compuesta ins) {
		int antDir = dir;
		ins.getLDecs().procesa(this);
		ins.getLIns().procesa(this);
		dir = antDir;
	}

	//Las funciones de aquí en adelante nunca se ejecutan (porque este procesamiento no recorre ese tipo de nodos)
	@Override
	public void procesa(Sin_Expr lExp) {
		//skip
	}

	@Override
	public void procesa(Una_Expr lExp) {
		//skip
	}

	@Override
	public void procesa(Muchas_Expr lExp) {
		//skip
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
		//skip
	}

	@Override
	public void procesa(Null e) {
		//skip
	}

	@Override
	public void procesa(Blt e) {
		//skip
	}

	@Override
	public void procesa(Ble e) {
		//skip
	}

	@Override
	public void procesa(Bgt e) {
		//skip
	}

	@Override
	public void procesa(Bge e) {
		//skip
	}

	@Override
	public void procesa(Beq e) {
		//skip
	}

	@Override
	public void procesa(Bne e) {
		//skip
	}

	@Override
	public void procesa(Suma e) {
		//skip
	}

	@Override
	public void procesa(Resta e) {
		//skip
	}

	@Override
	public void procesa(And e) {
		//skip
	}

	@Override
	public void procesa(Or e) {
		//skip
	}

	@Override
	public void procesa(Mult e) {
		//skip
	}

	@Override
	public void procesa(Div e) {
		//skip
	}

	@Override
	public void procesa(Mod e) {
		//skip
	}

	@Override
	public void procesa(Neg e) {
		//skip
	}

	@Override
	public void procesa(Not e) {
		//skip
	}

	@Override
	public void procesa(Index e) {
		//skip
	}

	@Override
	public void procesa(Access e) {
		//skip
	}

	@Override
	public void procesa(Indir e) {
		//skip
	}

}
