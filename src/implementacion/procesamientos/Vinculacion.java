package implementacion.procesamientos;

import java.util.HashMap;

import implementacion.abstractSintax.Procesamiento;
import implementacion.abstractSintax.SintaxisAbstracta.*;

class Vinculos extends HashMap<String, Dec> {}

public class Vinculacion implements Procesamiento {

	private Vinculos ts;	//Clave: id; Valor: nodo con la declaración
	private int pasada = 1;	//Sirve para diferenciar los procesa de la primera y la segunda pasada
	
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
			if (valores.containsKey(dec.getStr())) {
				throw new RuntimeException("Constante ya definida: "+dec.getStr());
			}
			else {
				valores.put(dec.getStr(), dec);
			}
		}
	}

	@Override
	public void procesa(Dec_Tipo dec) {
		
	}

	@Override
	public void procesa(Dec_Proc dec) {
		
	}

	@Override
	public void procesa(Int_ tipo) {
		
	}

	@Override
	public void procesa(Real_ tipo) {
		
	}

	@Override
	public void procesa(Bool_ tipo) {
		
	}

	@Override
	public void procesa(String_ tipo) {
		
	}

	@Override
	public void procesa(Ref_ tipo) {
		
	}

	@Override
	public void procesa(Array_ tipo) {
		
	}

	@Override
	public void procesa(Record_ tipo) {
		
	}

	@Override
	public void procesa(Puntero_ tipo) {
		
	}

	@Override
	public void procesa(Un_Campo campos) {
		
	}

	@Override
	public void procesa(Muchos_Campos campos) {
		
	}

	@Override
	public void procesa(Campo campo) {
		
	}

	@Override
	public void procesa(Sin_Params lParams) {
		
	}

	@Override
	public void procesa(Un_Param lParams) {
		
	}

	@Override
	public void procesa(Muchos_Params lParams) {
		
	}

	@Override
	public void procesa(Param_Ref param) {
		
	}

	@Override
	public void procesa(Param_Val param) {
		
	}

	@Override
	public void procesa(Sin_Ins lIns) {
		
	}

	@Override
	public void procesa(Una_Ins lIns) {
		
	}

	@Override
	public void procesa(Muchas_Ins lIns) {
		
	}

	@Override
	public void procesa(Asignacion_ ins) {
		
	}

	@Override
	public void procesa(If_Then ins) {
		
	}

	@Override
	public void procesa(If_Then_Else ins) {
		
	}

	@Override
	public void procesa(While_ ins) {
		
	}

	@Override
	public void procesa(Read_ ins) {
		
	}

	@Override
	public void procesa(Write_ ins) {
		
	}

	@Override
	public void procesa(Nl_ ins) {
		
	}

	@Override
	public void procesa(New_ ins) {
		
	}

	@Override
	public void procesa(Delete_ ins) {
		
	}

	@Override
	public void procesa(Call_Proc ins) {
		
	}

	@Override
	public void procesa(Ins_Compuesta ins) {
		
	}

	@Override
	public void procesa(Sin_Expr lExp) {
		
	}

	@Override
	public void procesa(Una_Expr lExp) {
		
	}

	@Override
	public void procesa(Muchas_Expr lExp) {
		
	}

	@Override
	public void procesa(Int e) {
		
	}

	@Override
	public void procesa(Real e) {
		
	}

	@Override
	public void procesa(True e) {
		
	}

	@Override
	public void procesa(False e) {
		
	}

	@Override
	public void procesa(Cadena e) {
		
	}

	@Override
	public void procesa(Id e) {
		
	}

	@Override
	public void procesa(Null e) {
		
	}

	@Override
	public void procesa(Blt e) {
		
	}

	@Override
	public void procesa(Ble e) {
		
	}

	@Override
	public void procesa(Bgt e) {
		
	}

	@Override
	public void procesa(Bge e) {
		
	}

	@Override
	public void procesa(Beq e) {
		
	}

	@Override
	public void procesa(Bne e) {
		
	}

	@Override
	public void procesa(Suma e) {
		
	}

	@Override
	public void procesa(Resta e) {
		
	}

	@Override
	public void procesa(And e) {
		
	}

	@Override
	public void procesa(Or e) {
		
	}

	@Override
	public void procesa(Mult e) {
		
	}

	@Override
	public void procesa(Div e) {
		
	}

	@Override
	public void procesa(Mod e) {
		
	}

	@Override
	public void procesa(Neg e) {
		
	}

	@Override
	public void procesa(Not e) {
		
	}

	@Override
	public void procesa(Index e) {
		
	}

	@Override
	public void procesa(Access e) {
		
	}

	@Override
	public void procesa(Indir e) {
		
	}

}
