package implementacion.procesamientos;

import java.util.HashSet;
import java.util.Set;

import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.abstractSintax.SalidaTipo;

public class FuncionesAuxiliares {
	Tipado t = new Tipado();
	
	public SalidaTipo ambos_ok(SalidaTipo val1, SalidaTipo val2){
        if (val1==SalidaTipo.OK && val2==SalidaTipo.OK) return SalidaTipo.OK;
        else return SalidaTipo.ERROR;
    }
    

    public Tipo ref_exc(Tipo t){
        while(t instanceof Ref_){
            t = t.getVinculo().getT();
        }
        return t;
    }
    
    public Tipo tip_relacional1(E e0, E e1){
        e0.procesa(t);
        e1.procesa(t);
        if((ref_exc(e0.getT())instanceof Int_ || ref_exc(e0.getT()) instanceof Real_) && 
    		(ref_exc(e1.getT()) instanceof Int_ || ref_exc(e1.getT()) instanceof Real_)) return new Ok();
        else if(ref_exc(e0.getT()) instanceof Bool_ && ref_exc(e1.getT()) instanceof Bool_) return new Bool_();
        else{
            return new Error_();
        }
    }

    public Tipo tip_relacional2(E e0, E e1){
        e0.procesa(t);
        e1.procesa(t);
        if((ref_exc(e0.getT()) instanceof Puntero_ || ref_exc(e0.getT()) instanceof Null_) && (ref_exc(e1.getT()) instanceof Puntero_ || ref_exc(e1.getT()) instanceof Null_)) return new Bool_();
        else if((ref_exc(e0.getT()) instanceof Int_ || ref_exc(e0.getT())instanceof Real_) && (ref_exc(e1.getT()) instanceof Int_ || ref_exc(e1.getT())instanceof Real_)) return new Bool_();
        else if(ref_exc(e0.getT()) instanceof Bool_ && ref_exc(e1.getT()) instanceof Bool_) return new Bool_();
        else{
            return new Error_();
        }
    }

    public Tipo tip_arit(E e0, E e1){
        e0.procesa(t);
        e1.procesa(t);
        if((ref_exc(e0.getT()) instanceof Int_)&& (ref_exc(e1.getT()) instanceof Real_)) return new Int_();
        else if((ref_exc(e0.getT()) instanceof Bool_) && (ref_exc(e1.getT()) instanceof Bool_)) return new Real_();
        else{
            return new Error_();
        }
    }

    public Tipo tip_mod(E e0, E e1){
        e0.procesa(t);
        e1.procesa(t);
        if((ref_exc(e0.getT()) instanceof Int_) && (ref_exc(e1.getT()) instanceof Int_)) return new Int_();
        else{
            return new Error_();
        }
    }

    public Tipo tip_log(E e0, E e1){
        e0.procesa(t);
        e1.procesa(t);
        if((ref_exc(e0.getT()) instanceof Bool_) && (ref_exc(e1.getT()) instanceof Bool_)) return new Bool_();
        else{
            return new Error_();
        }
    }

    public Tipo es_designador(E e0){
        if(e0 instanceof Id || e0 instanceof Index || e0 instanceof Access || e0 instanceof Indir) return new Ok();
        else return new Error_();
    }

    // TODO ERROR POR CAMPOS
    /*public boolean campos_compatibles(Un_Campo uc1, Un_Campo uc2){
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
    }*/

    public boolean check_params(Sin_Expr se, Sin_Params sp){
        return true;
    } 
    public boolean check_params(Una_Expr ue, Un_Param up){
        return check_param(ue.getE(), (Param_Ref) up.getParam())==SalidaTipo.OK || check_param(ue.getE(), (Param_Val) up.getParam())==SalidaTipo.OK;
    } 
    public SalidaTipo check_params3(Muchas_Expr me, Muchos_Params mp){
        // TODO NO SÉ SI check_param se refiere al check_param(E e0, Param_Ref paramRef) o al check_param(E e0, Param_Val paramVal) por eso hemos puesto que es uno u otro
    	SalidaTipo res;
    	if((check_param(me.getE(), (Param_Ref)mp.getParam())==SalidaTipo.OK || check_param(me.getE(), (Param_Val)mp.getParam())==SalidaTipo.OK)) {
        	res = SalidaTipo.OK;
        } else res = SalidaTipo.ERROR;
    	return ambos_ok(check_params3((Muchas_Expr)me.getLExp(), (Muchos_Params)mp.getParams()), res);
    }

    public SalidaTipo check_param(E e0, Param_Ref paramRef){
        e0.procesa(t);
        if (son_compatibles(e0.getT(), paramRef.getT()) && es_designador(e0) instanceof Ok &&
        (!(paramRef.getT() instanceof Real_) || (e0.getT() instanceof Real_ && paramRef.getT() instanceof Real_))){
            return SalidaTipo.OK;
         } else{
            return SalidaTipo.ERROR;
        }
    }

    public SalidaTipo check_param(E e0, Param_Val paramVal){
        e0.procesa(t);
        if (son_compatibles(e0.getT(), paramVal.getT())){
            return SalidaTipo.OK;
         } else{
            return SalidaTipo.ERROR;
        }
    }

    public boolean son_compatibles(Tipo t1, Tipo t2){
        Set<Tipo> st = new HashSet<Tipo>();
        return son_compatibles2(st, t1, t2);
    }

    public boolean son_compatibles2(Set<Tipo> st, Tipo t1, Tipo t2){
        if(st.contains(t1) && st.contains(t2)){
            return true;
        } else{
            st.add(t1);
            st.add(t2);
            if(t1 instanceof Ref_) return son_compatibles2(st, ref_exc(t1), t2);
            else if(t2 instanceof Ref_) return son_compatibles2(st, t1, ref_exc(t2));
            else if(t1 instanceof Int_ && t2 instanceof Int_) return true;
            else if(t1 instanceof Real_ && (t2 instanceof Real_ || t2 instanceof Int_)) return true;
            else if(t1 instanceof Bool_ && t2 instanceof Bool_) return true;
            else if(t1 instanceof String_ && t2 instanceof String_) return true;
            else if(t1 instanceof Array_ && t2 instanceof Array_) return son_compatibles2(st, t1, t2);
            // TODO ESTE ELSE IF REHACER CUANDO ESTÉ LO DE CAMPOS BIEN
            //else if(t1 instanceof Record_.getArg0() && t2 instanceof Record_.getArg1()) return campos_compatibles(Record_.getArg0(), Record_.getArg1());
            else if(t1 instanceof Puntero_ && t2 instanceof Null_) return true;
            else if(t1 instanceof Puntero_  && t2 instanceof Puntero_) return true;
            else return false;
        }
    }
}