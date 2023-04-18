package implementacion.procesamientos;

import java.util.HashSet;
import java.util.Set;

import implementacion.abstractSintax.SintaxisAbstracta.*;

public class FuncionesAuxiliares {
    
    public Tipo ambos_ok(Tipo val1, Tipo val2){
        if val1 instanceof Ok && val2 instanceof Ok{
            return new Ok();
        }
        else{
            return new Error();
        }
    }
    

    public Tipo ref_exc(Tipo t){
        // TODO NO SÉ SI ESTÁ BIEN
        while(t instanceof Id){
            t = t.getVinculo().getT();
        }
        return t;
    }
    
    // TODO NO SÉ QUÉ PONER EN EL RETURN
    public Tipo tip_relacional1(E e0, E e1){
        e0.procesa(Tipado.class);
        e1.procesa(Tipado.class);
        if((ref_exc(e0.getT())instanceof Int_ || ref_exc(e0.getT()) instanceof Real_) && (ref_exc(e1.getT() instanceof Int_) || ref_exc(e1.getT()) instanceof Real_)) return new Ok();
        else if(ref_exc(e0.getT()) instanceof Bool_) && ref_exc(e1.getT()) instanceof Bool_) return new Bool_();
        else{
            return new Error_();
        }
    }

    // TODO NO SÉ QUÉ PONER EN EL RETURN
    public Tipo tip_relacional2(E e0, E e1){
        e0.procesa(Tipado.class);
        e1.procesa(Tipado.class);
        if((ref_exc(e0.getT()) instanceof Puntero_) || ref_exc(e0.getT()) instanceof Null)) && (ref_exc(e1.getT()) instanceof Puntero_) || ref_exc(e1.getT()) instanceof Null))) return new Bool_;
        else if((ref_exc(e0.getT()) instanceof Int_) || ref_exc(e0.getT())instanceof Real_)) && (ref_exc(e1.getT()) instanceof Int) || ref_exc(e1.getT())instanceof Real))) return new Bool_;
        else if(ref_exc(e0.getT()) instanceof Bool_) && ref_exc(e1.getT()) instanceof Bool)) return new Bool_();
        else{
            return new Error_();
        }
    }

    // TODO NO SÉ QUÉ PONER EN EL RETURN NI QUÉ DEVOLVER EN LA FUNCIÓN
    public Tipo tip_arit(E e0, E e1){
        e0.procesa(Tipado.class);
        e1.procesa(Tipado.class);
        if((ref_exc(e0.getT()) instanceof Int_)&& (ref_exc(e1.getT()) instanceof Real_)) return new Int_();
        else if((ref_exc(e0.getT()) instanceof Bool_) && (ref_exc(e1.getT()) instanceof Bool_)) return Real_();
        else{
            // TODO THROW error??
            return Error_();
        }
    }

    // TODO NO SÉ QUÉ PONER EN EL RETURN NI QUÉ DEVOLVER EN LA FUNCIÓN
    public Tipo tip_mod(E e0, E e1){
        e0.procesa(Tipado.class);
        e1.procesa(Tipado.class);
        if((ref_exc(e0.getT()) instanceof Int_) && (ref_exc(e1.getT()) instanceof Int_)) return new Int_();
        else{
            // TODO THROW error??
            return Error_();
        }
    }

    // TODO NO SÉ QUÉ PONER EN EL RETURN NI QUÉ DEVOLVER EN LA FUNCIÓN
    public Tipo tip_log(E e0, E e1){
        e0.procesa(Tipado.class);
        e1.procesa(Tipado.class);
        if((ref_exc(e0.getT()) instanceof Bool) && (ref_exc(e1.getT()) instanceof Bool_)) return new Bool_();
        else{
            // TODO THROW error??
            return Error_();
        }
    }

    public Tipo es_designador(E e0){
        // TODO EL GETT() NO ESTÁ DEVOLVIENDO LO QUE YO QUIERO YO CREO, ENTONCES NO VA A SER INSTANCEOF DE NADA
        if(e0.getT() instanceof Id || e0.getT() instanceof Index || e0.getT() instanceof Access || e0.getT() instanceof Indir) return new Ok();
        else return new Error_();
    }

    public boolean campos_compatibles(Un_Campo uc1, Un_Campo uc2){
        return son_compatibles(uc1.getCampo().getT(), uc2.getCampo().getT());
    }
    public boolean campos_compatibles(Muchos_Campos mc1, Muchos_Campos mc2){
        return son_compatibles(mc1.getCampo().getT(), mc2.getCampo().getT()) && campos_compatibles(mc1.getCampos(), mc2.getCampos());
    }
    public boolean campos_compatibles(Muchos_Campos mc, Un_Campo uc){
        return false;
    }
    public boolean campos_compatibles(Un_Campo uc, Muchos_Campos mc){
        return false;
    }

    public boolean check_params(Sin_Expr se, Sin_Params sp){
        return true;
    } 
    public boolean check_params(Una_Expr ue, Un_Param up){
        return check_param(ue.getE(), up.getParam());
    } 
    public boolean check_params3(Muchas_Expr me, Muchos_Params mp){
        // TODO NO SÉ SI check_param se refiere al check_param(E e0, Param_Ref paramRef) o al check_param(E e0, Param_Val paramVal)
        return ambos_ok(check_params3(me.getLExp(), mp.getParams()), check_param(me.getE(), mp.getParams()));
    }

    public boolean check_param(E e0, Param_Ref paramRef){
        e0.procesa(this);
        if (son_compatibles(e0.getT(), paramRef.getT()) && es_designador(e0) &&
        (!paramRef.getT() instanceof Real || (e0.getT() instanceof Real && paramRef.getT() instanceof Real))){
            return true;
         } else{
            // TODO THROW error??
            return false;
        }
    }

    public boolean check_param(E e0, Param_Val paramVal){
        e0.procesa(this);
        if (son_compatibles(e0.getT(), paramVal.getT())){
            return true;
         } else{
            // TODO THROW error??
            return false;
        }
    }

    public boolean son_compatibles(Tipo t1, Tipo t2){
        Set<Tipo> st = new HashSet<Tipo>();
        return son_compatibles2(st, t1, t2);
    }

    public boolean son_compatibles2(Set<Tipo> st, Tipo t1, Tipo t2){
        // TODO DEBERÍA HACER GETTIPODATOS() Y QUE ME DEVUELVA LA CLASE? CÓMO LO HAGO?
        if(st.contains(t1) && st.contains(t2)){
            return true;
        } else{
            st.add(t1);
            st.add(t2);
            // TODO no de debería ser t1, porque no es una clase
            if(t1 instanceof Ref_) return son_compatibles2(st, ref_exc(t1), t2);
            else if(t2.getT() instanceof Ref_) return son_compatibles2(st, t1, ref_exc(t2));
            else if(t1.getT() instanceof Int && t2.getT() instanceof Int) return true;
            else if(t1 instanceof Real && (t2 instanceof Real || t2 instanceof Int)) return true;
            else if(t1 instanceof Bool_ && t2 instanceof Bool_) return true;
            else if(t1 instanceof String_ && t2 instanceof String_) return true;
            else if(t1 instanceof Array_ && t2 instanceof Array_) return son_compatibles2(st, t1, t2);
            else if(t1 instanceof Record.getArg0() && t2 instanceof Record.getArg1()) return campos_compatibles(Record.getArg0(), Record.getArg1());
            else if(t1 instanceof Puntero.getTipo() && t2 instanceof Null) return true;
            else if(t1 instanceof Puntero.getTipo1() && t2 instanceof Puntero.getTipo2()) return true;
            else return false;
        }
    }
}