package implementacion.procesamientos;

import java.util.HashSet;
import java.util.Set;

import implementacion.abstractSintax.SintaxisAbstracta.*;

public class FuncionesAuxiliares {
    public boolean ambos_ok(boolean val1, boolean val2){
        return val1 && val2;
    }

    public E ref_exc(E t){
        // TODO NO SÉ SI ESTÁ BIEN
        while(t instanceof Id){
            t = t.getVinculo();
        }
        return t.getT();
    }
    
    // TODO NO SÉ QUÉ PONER EN EL RETURN
    public boolean tip_relacional1(E e0, E e1){
        e0.procesa(this);
        e1.procesa(this);
        if((ref_exc(e0.getT() instanceof Int) || ref_exc(e0.getT() instanceof Real)) && (ref_exc(e1.getT() instanceof Int) || ref_exc(e1.getT() instanceof Real))) return Bool;
        else if(ref_exc(e0.getT() instanceof Bool) && ref_exc(e1.getT() instanceof Bool)) return Bool;
        else{
            // TODO THROW error??
            return false;
        }
    }

    // TODO NO SÉ QUÉ PONER EN EL RETURN
    public boolean tip_relacional2(E e0, E e1){
        e0.procesa(this);
        e1.procesa(this);
        if((ref_exc(e0.getT()==Puntero) || ref_exc(e0.getT()==Null)) && (ref_exc(e1.getT()==Puntero) || ref_exc(e1.getT()==Null))) return Bool;
        else if((ref_exc(e0.getT()==Int) || ref_exc(e0.getT()==Real)) && (ref_exc(e1.getT()==Int) || ref_exc(e1.getT()==Real))) return Bool;
        else if(ref_exc(e0.getT()==Bool) && ref_exc(e1.getT()==Bool)) return Bool;
        else{
            // TODO THROW error??
            return false;
        }
    }

    // TODO NO SÉ QUÉ PONER EN EL RETURN NI QUÉ DEVOLVER EN LA FUNCIÓN
    public boolean tip_arit(E e0, E e1){
        e0.procesa(this);
        e1.procesa(this);
        if(ref_exc(e0.getT()==Int) && ref_exc(e1.getT()==Real)) return Int;
        else if(ref_exc(e0.getT()==Bool) && ref_exc(e1.getT()==Bool)) return Real;
        else{
            // TODO THROW error??
            return false;
        }
    }

    // TODO NO SÉ QUÉ PONER EN EL RETURN NI QUÉ DEVOLVER EN LA FUNCIÓN
    public boolean tip_mod(E e0, E e1){
        e0.procesa(this);
        e1.procesa(this);
        if(ref_exc(e0.getT()==Int) && ref_exc(e1.getT()==Int)) return Int;
        else{
            // TODO THROW error??
            return false;
        }
    }

    // TODO NO SÉ QUÉ PONER EN EL RETURN NI QUÉ DEVOLVER EN LA FUNCIÓN
    public boolean tip_log(E e0, E e1){
        e0.procesa(this);
        e1.procesa(this);
        if(ref_exc(e0.getT()==Bool) && ref_exc(e1.getT()==Bool)) return Bool;
        else{
            // TODO THROW error??
            return false;
        }
    }

    public boolean es_designador(E e0){
        // TODO EL GETT() NO ESTÁ DEVOLVIENDO LO QUE YO QUIERO YO CREO, ENTONCES NO VA A SER INSTANCEOF DE NADA
        if(e0.getT() instanceof Id || e0.getT() instanceof Index || e0.getT() instanceof Access || e0.getT() instanceof Indir) return true;
        else return false;
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

    public boolean son_compatibles(E t1, E t2){
        Set<E> st = new HashSet<E>();
        return son_compatibles2(st, t1, t2);
    }

    public boolean son_compatibles2(Set<E> st, E t1, E t2){
        // TODO DEBERÍA HACER GETTIPODATOS() Y QUE ME DEVUELVA LA CLASE? CÓMO LO HAGO?
        if(st.contains(t1) && st.contains(t2)){
            return true;
        } else{
            st.add(t1);
            st.add(t2);
            // TODO no de debería ser t1, porque no es una clase
            if(t1 instanceof Ref) return son_compatibles2(st, ref_exc(t1), t2);
            else if(t2.getT() instanceof Ref) return son_compatibles2(st, t1, ref_exc(t2));
            else if(t1.getT() instanceof Int && t2.getT() instanceof Int) return true;
            else if(t1 instanceof Real && (t2 instanceof Real || t2 instanceof Int)) return true;
            else if(t1 instanceof Bool && t2 instanceof Bool) return true;
            else if(t1 instanceof String && t2 instanceof String) return true;
            else if(t1 instanceof Array && t2 instanceof Array) return son_compatibles2(st, t1, t2);
            else if(t1 instanceof Record.getArg0() && t2 instanceof Record.getArg1()) return campos_compatibles(Record.getArg0(), Record.getArg1());
            else if(t1 instanceof Puntero.getTipo() && t2 instanceof Null) return true;
            else if(t1 instanceof Puntero.getTipo1() && t2 instanceof Puntero.getTipo2()) return true;
            else return false;
        }
    }
}