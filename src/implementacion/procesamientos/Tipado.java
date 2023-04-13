package implementacion.procesamientos;

import implementacion.abstractSintax.ProcesamientoPorDefecto;
import implementacion.abstractSintax.SintaxisAbstracta.*;

public class Tipado extends ProcesamientoPorDefecto {

    @Override
    public void procesa(Prog_ prog) {
        prog.getlDecs().procesa(this);
		prog.getlIns().procesa(this);
        prog.setTipo(prog.getlDecs().getTipo() && prog.getlIns().getTipo());
    }

    @Override
    public void procesa(Sin_Decs lDecs) {
       lDecs.setTipo(true);
    }

    @Override
    public void procesa(Una_Dec lDecs) {
        lDecs.getDec().procesa(this);
        lDecs.setTipo(lDecs.getDec().getTipo());
    }

    @Override
    public void procesa(Muchas_Decs lDecs) {
        lDecs.getDecs().procesa(this);
		lDecs.getDec().procesa(this);
        lDecs.setTipo(lDecs.getDecs().getTipo() && lDecs.getDec().getTipo());
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

    // TODO FALTA HACER decProc

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
        // TODO ALGO ASÍ
        //if(tipo.getVinculo()==tipo.getDec_Tipo().procesa(this)){
            /*tipo.setTipo(true);
        } else{
            // TODO THROW error??
            tipo.setTipo(false);
        }*/
    }

    @Override
    public void procesa(Array_ tipo) {
        tipo.getT().procesa(this);
        if (!tipo.getTipo()) tipo.setTipo(false);
        else if(tipo.getTam()>=0) tipo.setTipo(true);
        else{
            // TODO THROW error??
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
        tipo.getT().procesa(this);
        tipo.setTipo(tipo.getT().getTipo());
    }

    @Override
    public void procesa(Un_Campo campos) {
        campos.getCampo().procesa(this);
        campos.setTipo(campos.getCampo().getTipo());
    }

    @Override
    public void procesa(Muchos_Campos campos) {
        campos.getCampo().procesa(this);
        if(campos.getCampo().getTipo()){
            //TODO COMO HACER ESTE IF
            if(!esta_en(Campo.string, Campos)){
                campos.getT().procesa(this);
                campos.setTipo(campos.getCampos().getTipo());
            } else{
                // TODO THROW error??
                campos.setTipo(false);
            }
        } else campos.setTipo(campos.getCampo().getTipo());
    }

    @Override
    public void procesa(Campo campo) {
        campo.getT().procesa(this);
        campo.setTipo(campo.getT().getTipo());
    }

    @Override
    public void procesa(Sin_Params lParams) {
        lParams.setTipo(true);
    }

    @Override
    public void procesa(Un_Param lParams) {
        lParams.getParam().procesa(this);
        lParams.setTipo(lParams.getParam().getTipo());
    }

    @Override
    public void procesa(Muchos_Params lParams) {
        lParams.getParams().procesa(this);
        lParams.getParam().procesa(this);
        lParams.setTipo(lParams.getParams().getTipo() && lParams.getParam().getTipo());
    }

    @Override
    public void procesa(Param_Ref param) {
        param.getT().procesa(this);
        param.setTipo(param.getT().getTipo());
    }

    @Override
    public void procesa(Param_Val param) {
        param.getT().procesa(this);
        param.setTipo(param.getT().getTipo());
    }

    @Override
    public void procesa(Sin_Ins lIns) {
        lIns.setTipo(true);
    }

    @Override
    public void procesa(Una_Ins lIns) {
        lIns.getIns().procesa(this);
        lIns.setTipo(lIns.getIns().getTipo());
    }

    @Override
    public void procesa(Muchas_Ins lIns) {
        lIns.getLIns().procesa(this);
        lIns.getIns().procesa(this);
        lIns.setTipo(lIns.getIns().getTipo());
    }

    @Override
    public void procesa(Asignacion_ ins) {
        ins.getE1().procesa(this);
        ins.getE2().procesa(this);
        // TODO son_compatibles y es_designador
        if(son_compatibles(ins.getE1().getTipo(), ins.getE2().getTipo()) && es_designador(ins.getE1())){
            ins.setTipo(true);
        } else{
            if(ins.getE1().getTipo() && ins.getE2().getTipo()){
                // TODO THROW error??
            }
            ins.setTipo(false);
        }
    }

    @Override
    public void procesa(If_Then ins) {
        ins.getE().procesa(this);
        ins.getLIns().procesa(this);
        // TODO son_compatibles, bool debe venir de Tipo?
        if(son_compatibles(ins.getE().getTipo(), bool)){
            ins.setTipo(ins.getLIns().getTipo());
        } else{
            ins.setTipo(false);
            // TODO THROW error??
        }
    }

    @Override
    public void procesa(If_Then_Else ins) {
        ins.getE().procesa(this);
        ins.getLIns1().procesa(this);
        ins.getLIns2().procesa(this);
        // TODO son_compatibles, bool debe venir de Tipo?
        if(son_compatibles(ins.getE().getTipo(), bool)){
            ins.setTipo(ins.getLIns1().getTipo() && ins.getLIns2().getTipo());
        } else{
            ins.setTipo(false);
            // TODO THROW error??
        }
    }

    @Override
    public void procesa(While_ ins) {
        ins.getE().procesa(this);
        ins.getLIns().procesa(this);
        // TODO son_compatibles, bool debe venir de Tipo?
        if(son_compatibles(ins.getE().getTipo(), bool)){
            ins.setTipo(ins.getLIns().getTipo());
        } else{
            ins.setTipo(false);
            // TODO THROW error??
        }
    }

    @Override
    public void procesa(Read_ ins) {
        ins.getE().procesa(this);
        // TODO VER CÓMO HACER ESTE ref! y es_designador
        if(((ref!(ins.getE().getTipo()) instanceof Int) || (ref!(ins.getE().getTipo()) instanceof Real) ||
        (ref!(ins.getE().getTipo()) instanceof String)) && es_designador(ins.getE())){
            ins.setTipo(true);
        } else{
            ins.setTipo(false);
            // TODO THROW error??
        }
    }

    @Override
    public void procesa(Write_ ins) {
        ins.getE().procesa(this);
        // TODO VER CÓMO HACER ref!
        if((ref!(ins.getE().getTipo()) instanceof Int) || (ref!(ins.getE().getTipo()) instanceof Real) ||
        (ref!(ins.getE().getTipo()) instanceof Bool) || (ref!(ins.getE().getTipo()) instanceof String)){
            ins.setTipo(true);
        } else{
            ins.setTipo(false);
            // TODO THROW error??
        }
    }

    @Override
    public void procesa(Nl_ ins) {
        ins.setTipo(true);
    }

    @Override
    public void procesa(New_ ins) {
        ins.getE().procesa(this);
        if(ref!(ins.getE().getTipo()) instanceof Puntero){
            ins.setTipo(true);
        } else{
            ins.setTipo(false);
            // TODO PONER OR CON LOS TIPOS?!
            if(ref!(ins.getE().getTipo()) instanceof != Error)
            // TODO THROW error??
        }
    }

    @Override
    public void procesa(Delete_ ins) {
        ins.getE().procesa(this);
        if(ref!(ins.getE().getTipo()) instanceof Puntero){
            ins.setTipo(true);
        } else{
            ins.setTipo(false);
            // TODO PONER OR CON LOS TIPOS?!
            if(ref!(ins.getE().getTipo()) instanceof != Error)
            // TODO THROW error??
        }
    }

    @Override
    public void procesa(Call_Proc ins) {
        ins.getE().procesa(this);
        ins.getLExp().procesa(this);
        // TODO ESTOS IFS
        if(E.vinculo == decProc(id, LParams, LDecs, LIns){
            if(num_elems(ins.getLParams()) == num_elems(ins.getLExp())){
                ins.setTipo(check_params(ins.getLExp(), ins.getLParams()));
            } else{
                ins.setTipo(false);
                // TODO THROW error??
            }
        } else{
            ins.setTipo(false);
            // TODO THROW error??
        }
    }

    @Override
    public void procesa(Ins_Compuesta ins) {
        ins.getLIns().procesa(this);
        ins.setTipo(ins.getLIns().getTipo());
    }

    @Override
    public void procesa(Sin_Expr lExp) {
       // TODO NO LO VEO EN LA MEMORIA
    }

    @Override
    public void procesa(Una_Expr lExp) {
       // TODO NO LO VEO EN LA MEMORIA
    }

    @Override
    public void procesa(Muchas_Expr lExp) {
        // TODO NO LO VEO EN LA MEMORIA
    }

    @Override
    public void procesa(Int e) {
       e.setTipoDatos(TipoDatos.Int);
    }

    @Override
    public void procesa(Real e) {
        e.setTipoDatos(TipoDatos.Real);
    }

    @Override
    public void procesa(True e) {
        e.setTipoDatos(TipoDatos.Bool);
    }

    @Override
    public void procesa(False e) {
        e.setTipoDatos(TipoDatos.Bool);
    }

    @Override
    public void procesa(Cadena e) {
        e.setTipoDatos(TipoDatos.String);
    }

    @Override
    public void procesa(Id e) {
        //TODO
    }

    @Override
    public void procesa(Null e) {
        e.setTipoDatos(TipoDatos.Null);
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
