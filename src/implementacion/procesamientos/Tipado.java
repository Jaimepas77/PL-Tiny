package implementacion.procesamientos;

import implementacion.abstractSintax.Procesamiento;
import implementacion.abstractSintax.SintaxisAbstracta.*;

public class Tipado implements Procesamiento {

    @Override
    public void procesa(Prog_ prog) {
        prog.getlDecs().procesa(this);
		prog.getlDecs().procesa(this);
        //TODO LLAMADA FUNC AMBOS OK
        prog.setTipo(false);
    }

    @Override
    public void procesa(Sin_Decs lDecs) {
       lDecs.setTipo(true);
       lDecs.setTipo(true);
    }

    @Override
    public void procesa(Una_Dec lDecs) {
        lDecs.getDec().procesa(this);
        //FIXME: NO SE SI TIENE QUE SER DE ESTE TIPO(DEC)
        lDecs.setTipo(lDecs.getTipo());
    }

    @Override
    public void procesa(Muchas_Decs lDecs) {
        lDecs.getDecs().procesa(this);
		lDecs.getDec().procesa(this);
        //TODO LLAMADA FUNC AMBOS OK
    }

    @Override
    public void procesa(Dec_Var dec) {
        dec.getT().procesa(this);
        dec.setTipo(dec.getT().getTipo());
    }

    @Override
    public void procesa(Dec_Tipo dec) {
        dec.getT().procesa(this);
        dec.setTipo(dec.getT().getTipo());
    }

    @Override
    public void procesa(Int_ tipo) {
        tipo.setTipo(true); 
    }

    @Override
    public void procesa(Real_ tipo) {
        tipo.setTipo(true); 
    }

    @Override
    public void procesa(Bool_ tipo) {
        tipo.setTipo(true); 
    }

    @Override
    public void procesa(String_ tipo) {
        tipo.setTipo(true); 
    }

    @Override
    public void procesa(Ref_ tipo) {
        //TODO: ESPERO A LA RAMA DE VINCULACION
    }

    @Override
    public void procesa(Array_ tipo) {
        tipo.getT().procesa(this);
        //TODO: duda primer if
        if (tipo.getTam() >= 0){
            tipo.setTipo(true);
        }
        else{
            tipo.setTipo(false);
        }
    }

    @Override
    public void procesa(Record_ tipo) {
        tipo.getCampos().procesa(this);
        tipo.setTipo(tipo.getCampos().getTipo());
    }

    @Override
    public void procesa(Puntero_ tipo) {
        
    }

    @Override
    public void procesa(Un_Campo campos) {
       ;
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
       ;
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

    @Override
    public void procesa(Dec_Proc dec) {
        dec.getlParams().procesa(this);
		dec.getlDecs().procesa(this);
		dec.getlIns().procesa(this);
    }
    
}
