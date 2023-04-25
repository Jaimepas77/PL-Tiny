package implementacion.procesamientos;

import implementacion.abstractSintax.ProcesamientoPorDefecto;
import implementacion.abstractSintax.SalidaTipo;
import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.abstractSintax.SintaxisAbstracta.Error_;

public class Tipado extends ProcesamientoPorDefecto {

    @Override
    public void procesa(Prog_ prog) {
        prog.getlDecs().procesa(this);
		prog.getlIns().procesa(this);
        prog.setTipo(Util.ambos_ok(prog.getlDecs().getTipo(), prog.getlIns().getTipo()));
    }

    @Override
    public void procesa(Sin_Decs lDecs) {
       lDecs.setTipo(SalidaTipo.OK);
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
        lDecs.setTipo(Util.ambos_ok(lDecs.getDecs().getTipo(), lDecs.getDec().getTipo()));
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
        tipo.setTipo(SalidaTipo.OK); 
    }

    @Override
    public void procesa(Real_ tipo) {
        tipo.setTipo(SalidaTipo.OK); 
    }

    @Override
    public void procesa(Bool_ tipo) {
        tipo.setTipo(SalidaTipo.OK); 
    }

    @Override
    public void procesa(String_ tipo) {
        tipo.setTipo(SalidaTipo.OK); 
    }

    @Override
    public void procesa(Ref_ tipo) {
        //TODO: ESPERO A LA RAMA DE VINCULACION
        // TODO ALGO ASÍ
        if(tipo.getVinculo() instanceof Dec_Tipo){
            tipo.setTipo(SalidaTipo.OK);
        } 
        else {
            System.err.println("Error en el tipado de ref"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            tipo.setTipo(SalidaTipo.ERROR);
        }
    }

    @Override
    public void procesa(Array_ tipo) {
        tipo.getT().procesa(this);
        if (tipo.getTipo()==SalidaTipo.ERROR) tipo.setTipo(SalidaTipo.ERROR);
        else if(tipo.getTam()>=0) tipo.setTipo(SalidaTipo.OK);
        else{
            System.err.println("Error en el tipado de array_"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            tipo.setTipo(SalidaTipo.ERROR);
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

    // TODO LOS 3 DE ABAJO
    @Override
    public void procesa(Un_Campo campos) {
        /*campos.getCampo().procesa(this);
        campos.setTipo(campos.getCampo().getTipo());*/
    }
    
    //TODO no entiendo muy bien campos 
    @Override
    public void procesa(Muchos_Campos campos) {
        /*campos.getCampo().procesa(this);
        if(campos.getCampo().getTipo()){
            //TODO COMO HACER ESTE IF
            if(!esta_en(Campo.string, Campos)){
                campos.getT().procesa(this);
                campos.setTipo(campos.getCampos().getTipo());
            } else{
                System.err.println("Error en el tipado de Muchos_Campos"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
                campos.setTipo(false);
            }
        } else campos.setTipo(campos.getCampo().getTipo());*/
    }

    @Override
    public void procesa(Campo campo) {
        /*campo.getT().procesa(this);
        campo.setTipo(campo.getT().getTipo());*/
    }

    @Override
    public void procesa(Sin_Params lParams) {
        lParams.setTipo(SalidaTipo.OK);
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
        lParams.setTipo(Util.ambos_ok(lParams.getParams().getT(), lParams.getParam().getTipo()));
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
        lIns.setTipo(SalidaTipo.OK);
    }

    @Override
    public void procesa(Una_Ins lIns) {
        lIns.getIns().procesa(this);
        lIns.setTipo(lIns.getIns().getT());
    }

    @Override
    public void procesa(Muchas_Ins lIns) {
        lIns.getLIns().procesa(this);
        lIns.getIns().procesa(this);
        lIns.setTipo(Util.ambos_ok(lIns.getIns().getT(), lIns.getIns().getT()));
    }

    @Override
    public void procesa(Asignacion_ ins) {
        ins.getE1().procesa(this);
        ins.getE2().procesa(this);
        if(Util.son_compatibles(ins.getE1().getT(), ins.getE2().getT()) && Util.es_designador(ins.getE1())){
            ins.setTipo(SalidaTipo.OK);
        } 
        else {
            if(!(ins.getE1().getT() instanceof Error_) && !(ins.getE2().getT() instanceof Error_)) {
                System.err.println("Error en el tipado de asignación"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            }
            ins.setTipo(SalidaTipo.ERROR);
        }
    }

    @Override
    public void procesa(If_Then ins) {
        ins.getE().procesa(this);
        ins.getLIns().procesa(this);
        if(Util.son_compatibles(ins.getE().getT(), new Bool_()) || Util.son_compatibles(ins.getE().getT(), new Bool_())){
            ins.setTipo(ins.getLIns().getTipo());
        } else{
            ins.setTipo(SalidaTipo.ERROR);
            System.err.println("Error en el tipado de if_then"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(If_Then_Else ins) {
        ins.getE().procesa(this);
        ins.getLIns1().procesa(this);
        ins.getLIns2().procesa(this);
        if(Util.son_compatibles(ins.getE().getT(), new Bool_()) || Util.son_compatibles(ins.getE().getT(), new Bool_())){
            ins.setTipo(Util.ambos_ok(ins.getLIns1().getTipo(), ins.getLIns2().getTipo()));
        } else{
            ins.setTipo(SalidaTipo.ERROR);
            System.err.println("Error en el tipado de if then else"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(While_ ins) {
        ins.getE().procesa(this);
        ins.getLIns().procesa(this);
        if(Util.son_compatibles(ins.getE().getT(), new Bool_()) || Util.son_compatibles(ins.getE().getT(), new Bool_())){
            ins.setTipo(ins.getLIns().getTipo());
        } else{
            ins.setTipo(SalidaTipo.ERROR);
            System.err.println("Error en el tipado del while"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Read_ ins) {
        ins.getE().procesa(this);
        if ((((Util.ref_exc(ins.getE().getT()) instanceof Int_) || (Util.ref_exc(ins.getE().getT()) instanceof Real_) ||
        (Util.ref_exc(ins.getE().getT()) instanceof String_))) && (Util.es_designador(ins.getE()))){
            ins.setTipo(SalidaTipo.OK);
        } else{
            ins.setTipo(SalidaTipo.ERROR);
            System.err.println("Error en el tipado de read"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Write_ ins) {
        ins.getE().procesa(this);
        if((Util.ref_exc(ins.getE().getT()) instanceof Int_) || (Util.ref_exc(ins.getE().getT()) instanceof Real_) ||
        (Util.ref_exc(ins.getE().getT()) instanceof Bool_) || (Util.ref_exc(ins.getE().getT()) instanceof String_)){
            ins.setTipo(SalidaTipo.OK);
        } else{
            ins.setTipo(SalidaTipo.ERROR);
            System.err.println("Error en el tipado de write"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Nl_ ins) {
        ins.setTipo(SalidaTipo.OK);
    }

    @Override
    public void procesa(New_ ins) {
        ins.getE().procesa(this);
        if(Util.ref_exc(ins.getE().getT()) instanceof Puntero_){
            ins.setTipo(SalidaTipo.OK);
        } else{
            ins.setTipo(SalidaTipo.ERROR);
            // TODO PONER OR CON LOS TIPOS?!
            if(!(Util.ref_exc(ins.getE().getT()) instanceof Error_)){
                System.err.println("Error en el tipado de new"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            }
        }
    }

    @Override
    public void procesa(Delete_ ins) {
        ins.getE().procesa(this);
        if(Util.ref_exc(ins.getE().getT()) instanceof Puntero_){
            ins.setTipo(SalidaTipo.OK);
        } else{
            ins.setTipo(SalidaTipo.ERROR);
            // TODO PONER OR CON LOS TIPOS?!
            if(!(Util.ref_exc(ins.getE().getT()) instanceof Error_)){
                System.err.println("Error en el tipado de delete"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            }
           
        }
    }

    // TODO
    @Override
    public void procesa(Call_Proc ins) {
        /*ins.getE().procesa(this);
        ins.getLExp().procesa(this);
        // TODO ESTOS IFS, HACER GETVINCULO() DESDE E??, CÓMO SE HACE ESTO?
        if(E.vinculo instanceof Dec_Proc){
            if(num_elems(ins.getLParams()) == num_elems(ins.getLExp())){
                ins.setTipo(Util.check_params3(ins.getLExp(), ins.getLParams()));
            } else{
                ins.setTipo(SalidaTipo.ERROR);
                System.err.println("Error en el tipado de call_proc (los params no encajan con la def del proc)"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
            }
        } else{
            ins.setTipo(SalidaTipo.ERROR);
            System.err.println("Error en el tipado de call proc (identificador no válido)"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }*/
    }

    @Override
    public void procesa(Ins_Compuesta ins) {
        ins.getLIns().procesa(this);
        ins.setTipo(ins.getLIns().getTipo());
    }


    @Override
    public void procesa(Sin_Expr lExp) {
        lExp.setTipo(SalidaTipo.OK);
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
        //lExp.setTipo(Util.ambos_ok(lExp.getLExp(), lExp.getE().getT()));
    }

    @Override
    public void procesa(Int e) {
       e.setT(new Int_());
    }

    @Override
    public void procesa(Real e) {
        e.setT(new Real_());
    }

    @Override
    public void procesa(True e) {
        e.setT(new Bool_());
    }

    @Override
    public void procesa(False e) {
        e.setT(new Bool_());
    }

    @Override
    public void procesa(Cadena e) {
        e.setT(new String_());
    }

    @Override
    public void procesa(Id e) {
    	if(e.getVinculo() instanceof Vinculable) {
    		e.setT(e.getVinculo().getT());	
    	}
    	else {
    		System.out.println("Error en vinculación de Id");
    		e.setT(new Error_());
    	}
    }

    @Override
    public void procesa(Null e) {
        e.setT(new Puntero_(null));
    }

    @Override
    public void procesa(Blt e) {
        e.setT(Util.tip_relacional1(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Ble e) {
        e.setT(Util.tip_relacional1(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Bgt e) {
        e.setT(Util.tip_relacional1(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Bge e) {
        e.setT(Util.tip_relacional1(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Beq e) {
        e.setT(Util.tip_relacional2(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Bne e) {
        e.setT(Util.tip_relacional2(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Suma e) {
        e.setT(Util.tip_arit(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Resta e) {
        e.setT(Util.tip_arit(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Mult e) {
        e.setT(Util.tip_arit(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Div e) {
        e.setT(Util.tip_arit(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Mod e) {
        e.setT(Util.tip_mod(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(And e) {
        e.setT(Util.tip_log(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Or e) {
        e.setT(Util.tip_log(e.getArg0(), e.getArg1(), this));
    }

    @Override
    public void procesa(Not e) {
    	e.getArg0().procesa(this);
        if(Util.ref_exc(e.getArg0().getT()) instanceof Bool_){
            e.setT(new Bool_());
        } else{
            e.setT(new Error_());
            System.err.println("Error en tipado de not"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Neg e) {
    	e.getArg0().procesa(this);
        if(Util.ref_exc(e.getArg0().getT()) instanceof Int_){
            e.setT(new Int_());
        } else if(Util.ref_exc(e.getArg0().getT()) instanceof Real_ ){
            e.setT(new Real_());
        } else{
            e.setT(new Error_());
            System.err.println("Error en el tipado de neg"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
        }
    }

    @Override
    public void procesa(Index e) {
        e.getArg0().procesa(this);
        e.getArg1().procesa(this);
        if(Util.ref_exc(e.getArg0().getT()) instanceof Array_ && Util.ref_exc(e.getArg1().getT()) instanceof Int_){
            // TODO $.tipo = array(tam, tipo)
            e.setT(new Array_(null, null));
        }
        else if((!(Util.ref_exc(e.getArg0().getT())instanceof Error_)&& !(Util.ref_exc(e.getArg0().getT())instanceof Array_))
            || ( !(Util.ref_exc(e.getArg0().getT()) instanceof Array_)&& !(Util.ref_exc(e.getArg1().getT())  instanceof Error_) 
            && !(Util.ref_exc(e.getArg1().getT()) instanceof Int_))){
            e.setT(new Error_());
            System.err.println("Error en el tipado de index"); // TODO AÑADIR MÁS INFO CON STRINGLOCALIZADO
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
        if(Util.ref_exc(e.getArg0().getT()) instanceof Puntero_){
            e.setT(e.getArg0().getT());
        } else if(Util.ref_exc(e.getArg0().getT()) instanceof Error_){
            e.setT(new Error_());
        }
    }

    @Override
    public void procesa(Dec_Proc dec) {
        dec.getLParams().procesa(this);
		dec.getLDecs().procesa(this);
		dec.getLIns().procesa(this);
    }   
}