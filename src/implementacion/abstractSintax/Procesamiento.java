package implementacion.abstractSintax;

import implementacion.abstractSintax.SintaxisAbstracta.*;

public interface Procesamiento { //Interfaz que define los nodos que debe ser capaz de procesar un procesamiento
	void procesa(Prog_ prog);
	//LDecs
	void procesa(Sin_Decs lDecs);
	void procesa(Una_Dec lDecs);
	void procesa(Muchas_Decs lDecs);
	
	void procesa(Dec_Var dec);
	void procesa(Dec_Tipo dec);
	void procesa(Dec_Proc dec);
	
	void procesa(Int_ tipo);
	void procesa(Real_ tipo);
	void procesa(Bool_ tipo);
	void procesa(String_ tipo);
	void procesa(Ref_ tipo);
	void procesa(Array_ tipo);
	void procesa(Record_ tipo);
	void procesa(Puntero_ tipo);
	
	void procesa(Un_Campo campos);
	void procesa(Muchos_Campos campos);
	void procesa(Campo campo);

	void procesa(Sin_Params lParams);
	void procesa(Un_Param lParams);
	void procesa(Muchos_Params lParams);
	void procesa(Param_Ref param);
	void procesa(Param_Val param);
	
	//LIns
	void procesa(Sin_Ins lIns);
	void procesa(Una_Ins lIns);
	void procesa(Muchas_Ins lIns);
	
	void procesa(Asignacion_ ins);
	void procesa(If_Then ins);
	void procesa(If_Then_Else ins);
	void procesa(While_ ins);
	void procesa(Read_ ins);
	void procesa(Write_ ins);
	void procesa(Nl_ ins);
	void procesa(New_ ins);
	void procesa(Delete_ ins);
	void procesa(Call_Proc ins);
	void procesa(Ins_Compuesta ins);
	
	void procesa(Sin_Expr lExp);
	void procesa(Una_Expr lExp);
	void procesa(Muchas_Expr lExp);
	
	void procesa(Int e);
	void procesa(Real e);
	void procesa(True e);
	void procesa(False e);
	void procesa(Cadena e);
	void procesa(Id e);
	void procesa(Null e);
	
	void procesa(Blt e);
	void procesa(Ble e);
	void procesa(Bgt e);
	void procesa(Bge e);
	void procesa(Beq e);
	void procesa(Bne e);
	
	void procesa(Suma e);
	void procesa(Resta e);

	void procesa(And e);
	void procesa(Or e);

	void procesa(Mult e);
	void procesa(Div e);
	void procesa(Mod e);

	void procesa(Neg e);
	void procesa(Not e);

	void procesa(Index e);
	void procesa(Access e);
	void procesa(Indir e);
	void procesa(Ok ok);
    void procesa(Error_ error_);
}
