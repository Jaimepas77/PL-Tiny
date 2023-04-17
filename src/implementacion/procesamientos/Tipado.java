package implementacion.procesamientos;

import implementacion.abstractSintax.ProcesamientoPorDefecto;
import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.procesamientos.FuncionesAuxiliares;

public class Tipado extends ProcesamientoPorDefecto {
    FuncionesAuxiliares aux = new FuncionesAuxiliares();

    @Override
    public void procesa(Prog_ prog) {
        prog.getlDecs().procesa(this);
		prog.getlIns().procesa(this);
        prog.setTipo(aux.ambos_ok(prog.getlDecs().getTipo(), prog.getlIns().getTipo()));
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
        lDecs.setTipo(aux.ambos_ok(lDecs.getDecs().getTipo(), lDecs.getDec().getTipo()));
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
    /*tipado(decProc(id, LParams, LDecs, LIns)) =
    tipado(LParams)
    tipado(LDecs)
    tipado(LIns)
    $.tipo = ambos_ok(ambos_ok(LParams.tipo, LDecs.tipo), LIns.tipo)
    */

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
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            tipo.setTipo(false);
        }*/
    }

    @Override
    public void procesa(Array_ tipo) {
        tipo.getT().procesa(this);
        if (!tipo.getTipo()) tipo.setTipo(false);
        else if(tipo.getTam()>=0) tipo.setTipo(true);
        else{
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
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
    
    //TODO no entiendo muy bien campos 
    @Override
    public void procesa(Muchos_Campos campos) {
        campos.getCampo().procesa(this);
        if(campos.getCampo().getTipo()){
            //TODO COMO HACER ESTE IF
            if(!esta_en(Campo.string, Campos)){
                campos.getT().procesa(this);
                campos.setTipo(campos.getCampos().getTipo());
            } else{
                System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
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
        lParams.setTipo(lParams.getParam().getT().getTipo());
    }

    @Override
    public void procesa(Muchos_Params lParams) {
        lParams.getParams().procesa(this);
        lParams.getParam().procesa(this);
        lParams.setTipo(aux.ambos_ok(lParams.getParams().getT().getTipo(), lParams.getParam().getT().getTipo()));
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
        lIns.setTipo(lIns.getIns().getT().getTipo());
    }

    @Override
    public void procesa(Muchas_Ins lIns) {
        lIns.getLIns().procesa(this);
        lIns.getIns().procesa(this);
        lIns.setTipo(aux.ambos_ok(lIns.getLIns().getT().getTipo(), lIns.getIns().getT().getTipo()));
    }

    @Override
    public void procesa(Asignacion_ ins) {
        ins.getE1().procesa(this);
        ins.getE2().procesa(this);
        if(aux.son_compatibles(ins.getE1().getT(), ins.getE2().getT()) && aux.es_designador(ins.getE1())){
            ins.setTipo(true);
        } else{
            if(ins.getE1().getT().getTipo() && ins.getE2().getT().getTipo()){
                System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            }
            ins.setTipo(false);
        }
    }

    @Override
    public void procesa(If_Then ins) {
        ins.getE().procesa(this);
        ins.getLIns().procesa(this);
        if(aux.son_compatibles(ins.getE().getT(), new True()) || aux.son_compatibles(ins.getE().getT(), new False())){
            ins.setTipo(ins.getLIns().getT().getTipo());
        } else{
            ins.setTipo(false);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(If_Then_Else ins) {
        ins.getE().procesa(this);
        ins.getLIns1().procesa(this);
        ins.getLIns2().procesa(this);
        if(aux.son_compatibles(ins.getE().getT(), new True()) || aux.son_compatibles(ins.getE().getT(), new False())){
            ins.setTipo(aux.ambos_ok(ins.getLIns1().getT().getTipo(), ins.getLIns2().getT().getTipo()));
        } else{
            ins.setTipo(false);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(While_ ins) {
        ins.getE().procesa(this);
        ins.getLIns().procesa(this);
        if(aux.son_compatibles(ins.getE().getT(), new True()) || aux.son_compatibles(ins.getE().getT(), new False())){
            ins.setTipo(ins.getLIns().getT().getTipo());
        } else{
            ins.setTipo(false);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Read_ ins) {
        ins.getE().procesa(this);
        if(((aux.ref_exc(ins.getE().getT()) instanceof Int) || (aux.ref_exc(ins.getE().getT()) instanceof Real) ||
        (aux.ref_exc(ins.getE().getT()) instanceof Cadena)) && aux.es_designador(ins.getE())){
            ins.setTipo(true);
        } else{
            ins.setTipo(false);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Write_ ins) {
        ins.getE().procesa(this);
        if((aux.ref_exc(ins.getE().getT()) instanceof Int) || (aux.ref_exc(ins.getE().getT()) instanceof Real) ||
        (aux.ref_exc(ins.getE().getT()) instanceof True) || (aux.ref_exc(ins.getE().getT()) instanceof False) ||
        (aux.ref_exc(ins.getE().getT()) instanceof Cadena)){
            ins.setTipo(true);
        } else{
            ins.setTipo(false);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Nl_ ins) {
        ins.setTipo(true);
    }

    @Override
    public void procesa(New_ ins) {
        ins.getE().procesa(this);
        if(aux.ref_exc(ins.getE().getTipo()) instanceof Puntero){
            ins.setTipo(true);
        } else{
            ins.setTipo(false);
            // TODO PONER OR CON LOS TIPOS?!
            if(aux.ref_exc(ins.getE().getTipo()) instanceof != Error){
                System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            }
        }
    }

    @Override
    public void procesa(Delete_ ins) {
        ins.getE().procesa(this);
        if(aux.ref_exc(ins.getE().getTipo()) instanceof Puntero){
            ins.setTipo(true);
        } else{
            ins.setTipo(false);
            // TODO PONER OR CON LOS TIPOS?!
            if(aux.ref_exc(ins.getE().getTipo()) instanceof != Error)
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Call_Proc ins) {
        ins.getE().procesa(this);
        ins.getLExp().procesa(this);
        // TODO ESTOS IFS, HACER GETVINCULO() DESDE E??, CÓMO SE HACE ESTO?
        if(E.vinculo instanceof Dec_Proc){
            if(num_elems(ins.getLParams()) == num_elems(ins.getLExp())){
                ins.setTipo(aux.check_params3(ins.getLExp(), ins.getLParams()));
            } else{
                ins.setTipo(false);
                System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            }
        } else{
            ins.setTipo(false);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Ins_Compuesta ins) {
        ins.getLIns().procesa(this);
        ins.setTipo(ins.getLIns().getT().getTipo());
    }


    @Override
    public void procesa(Sin_Expr lExp) {
        lExp.setTipo(true);
    }

    @Override
    public void procesa(Una_Expr lExp) {
        lExp.getE().procesa(this);
        //TODO VER CÓMO HACER ESTO
        lExp.setTipo(lExp.getE().getT().getTipo());
    }

    @Override
    public void procesa(Muchas_Expr lExp) {
        lExp.getLExp().procesa(this);
        lExp.getE().procesa(this);
        //TODO VER CÓMO HACER ESTO
        lExp.setTipo(aux.ambos_ok(lExp.getLExp().getT().getTipo(), lExp.getE().getT().getTipo()));
    }

    @Override
    public void procesa(Int e) {
       e.setT(new Int(e.getStr()));
    }

    @Override
    public void procesa(Real e) {
        e.setT(new Real(e.getStr()));
    }

    @Override
    public void procesa(True e) {
        e.setT(new True());
    }

    @Override
    public void procesa(False e) {
        e.setT(new False());
    }

    @Override
    public void procesa(Cadena e) {
        e.setT(new Cadena(e.getStr()));
    }

    @Override
    public void procesa(Id e) {
        //TODO
    }

    @Override
    public void procesa(Null e) {
        e.setT(new Null());
    }

    @Override
    public void procesa(Blt e) {
        e.setTipoDatos(aux.tip_relacional1(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Ble e) {
        e.setTipoDatos(aux.tip_relacional1(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Bgt e) {
        e.setTipoDatos(aux.tip_relacional1(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Bge e) {
        e.setTipoDatos(aux.tip_relacional1(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Beq e) {
        e.setTipoDatos(aux.tip_relacional2(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Bne e) {
        e.setTipoDatos(aux.tip_relacional2(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Suma e) {
        e.setTipoDatos(aux.tip_arit(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Resta e) {
        e.setTipoDatos(aux.tip_arit(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Mult e) {
        e.setTipoDatos(aux.tip_arit(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Div e) {
        e.setTipoDatos(aux.tip_arit(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Mod e) {
        e.setTipoDatos(aux.tip_mod(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(And e) {
        e.setTipoDatos(aux.tip_log(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Or e) {
        e.setTipoDatos(aux.tip_log(e.getArg0(), e.getArg1()));
    }

    @Override
    public void procesa(Not e) {
        if(aux.ref_exc(e.getArg0()) instanceof Bool){
            e.setTipoDatos(TipoDatos.Bool);
        } else{
            e.setTipoDatos(TipoDatos.Error);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Neg e) {
        if(aux.ref_exc(e.getTipoDatos())==TipoDatos.Int){
            e.setTipoDatos(TipoDatos.Int);
        } else if(aux.ref_exc(e.getTipoDatos())==TipoDatos.Real){
            e.setTipoDatos(TipoDatos.Real);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        } else{
            e.setTipoDatos(TipoDatos.Error);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Index e) {
        e.getArg0().procesa(this);
        e.getArg1().procesa(this);
        if(aux.ref_exc(e.getArg0())==TipoDatos.Array && aux.ref_exc(e.getArg1())==TipoDatos.Int){
            // TODO $.tipo = array(tam, tipo)
            e.setTipoDatos(TipoDatos.Array);
        } else if((aux.ref_exc(e.getArg0().getTipoDatos())!=TipoDatos.Error && aux.ref_exc(e.getArg0().getTipoDatos())!=TipoDatos.Array)
            || (aux.ref_exc(e.getArg0().getTipoDatos())==TipoDatos.Array && aux.ref_exc(e.getArg1().getTipoDatos())!=TipoDatos.Error 
            && aux.ref_exc(e.getArg1().getTipoDatos())!=TipoDatos.Int)){
            e.setTipoDatos(TipoDatos.Error);
            System.err.println("Error"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Access e) {
        e.getArg0().procesa(this);
        // TODO
    }

    @Override
    public void procesa(Indir e) {
        e.getArg0().procesa(this);
        if(aux.ref_exc(e.getArg0().getTipoDatos())==TipoDatos.Puntero){
            e.setTipoDatos(e.getArg0().getTipoDatos());
        } else if(aux.ref_exc(e.getArg0().getTipoDatos())==TipoDatos.Error){
            e.setTipoDatos(TipoDatos.Error);
        }
    }

    @Override
    public void procesa(Dec_Proc dec) {
        dec.getlParams().procesa(this);
		dec.getlDecs().procesa(this);
		dec.getlIns().procesa(this);
    }   
}