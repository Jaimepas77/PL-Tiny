package implementacion.abstractSintax;

import implementacion.abstractSintax.SintaxisAbstracta.*;

public interface Procesamiento {
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
	
	//LIns (pendiente)
}
