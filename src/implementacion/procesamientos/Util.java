package implementacion.procesamientos;

import java.util.HashSet;
import java.util.Set;

import implementacion.abstractSintax.SintaxisAbstracta.*;
import implementacion.abstractSintax.Procesamiento;
import implementacion.abstractSintax.SalidaTipo;

public final class Util {
	
	private Util() {}
	
	public static SalidaTipo ambos_ok(SalidaTipo val1, SalidaTipo val2){
        if (val1==SalidaTipo.OK && val2==SalidaTipo.OK) return SalidaTipo.OK;
        else return SalidaTipo.ERROR;
    }
    

    public static Tipo ref_exc(Tipo t){
        while(t instanceof Ref_){
            t = (((Ref_) t).getVinculo()).getT();
        }
        return t;
    }
    
    public static Tipo tip_relacional1(E e0, E e1, Procesamiento p) {
        e0.procesa(p);
        e1.procesa(p);
        if((ref_exc(e0.getT())instanceof Int_ || ref_exc(e0.getT()) instanceof Real_) 
        		&& (ref_exc(e1.getT()) instanceof Int_ || ref_exc(e1.getT()) instanceof Real_))
        	return new Bool_();
        else if(ref_exc(e0.getT()) instanceof Bool_ && ref_exc(e1.getT()) instanceof Bool_)
        	return new Bool_();
        else{
        	System.out.println("Error en tipado de operador relacional (tip_relacional1).");
            return new Error_();
        }
    }

    public static Tipo tip_relacional2(E e0, E e1, Procesamiento p) {
        e0.procesa(p);
        e1.procesa(p);
        if((ref_exc(e0.getT()) instanceof Puntero_) && (ref_exc(e1.getT()) instanceof Puntero_))
        	return new Bool_();
        else if((ref_exc(e0.getT()) instanceof Int_ || ref_exc(e0.getT())instanceof Real_)
        		&& (ref_exc(e1.getT()) instanceof Int_ || ref_exc(e1.getT())instanceof Real_))
        	return new Bool_();
        else if(ref_exc(e0.getT()) instanceof Bool_ && ref_exc(e1.getT()) instanceof Bool_)
        	return new Bool_();
        else{
            return new Error_();
        }
    }

    public static Tipo tip_arit(E e0, E e1, Procesamiento p) {
        e0.procesa(p);
        e1.procesa(p);
        if((ref_exc(e0.getT()) instanceof Int_) && (ref_exc(e1.getT()) instanceof Int_))
        	return new Int_();
        else if((ref_exc(e0.getT()) instanceof Int_ || ref_exc(e0.getT()) instanceof Real_)
        		&& (ref_exc(e1.getT()) instanceof Int_ || ref_exc(e1.getT()) instanceof Real_))
        	return new Real_();
        else{
            return new Error_();
        }
    }

    public static Tipo tip_mod(E e0, E e1, Procesamiento p) {
        e0.procesa(p);
        e1.procesa(p);
        if((ref_exc(e0.getT()) instanceof Int_) && (ref_exc(e1.getT()) instanceof Int_)) {
        	return new Int_();
        }
        else {
            return new Error_();
        }
    }

    public static Tipo tip_log(E e0, E e1, Procesamiento p) {
        e0.procesa(p);
        e1.procesa(p);
        if((ref_exc(e0.getT()) instanceof Bool_) && (ref_exc(e1.getT()) instanceof Bool_)) {
        	return new Bool_();
        }
        else {
            return new Error_();
        }
    }

    public static boolean es_designador(E e0){
        if(e0 instanceof Id || e0 instanceof Index || e0 instanceof Access || e0 instanceof Indir)
        	return true;
        else 
        	return false;
    }

    public static boolean son_compatibles(Tipo t1, Tipo t2){
        Set<Tipo> st = new HashSet<Tipo>();
        return son_compatibles2(st, t1, t2);
    }

    public static boolean son_compatibles2(Set<Tipo> st, Tipo t1, Tipo t2){
        if(st.contains(t1) && st.contains(t2)){
            return true;
        }
        else {
            st.add(t1);
            st.add(t2);
            if(t1 instanceof Ref_)
            	return son_compatibles2(st, ref_exc(t1), t2);
            else if(t2 instanceof Ref_)
            	return son_compatibles2(st, t1, ref_exc(t2));
            else if(t1 instanceof Int_ && t2 instanceof Int_)
            	return true;
            else if(t1 instanceof Real_ && (t2 instanceof Real_ || t2 instanceof Int_))
            	return true;
            else if(t1 instanceof Bool_ && t2 instanceof Bool_)
            	return true;
            else if(t1 instanceof String_ && t2 instanceof String_)
            	return true;
            else if(t1 instanceof Array_ && t2 instanceof Array_)
            	return son_compatibles2(st, t1, t2);
            else if(t1 instanceof Record_ && t2 instanceof Record_)
            	return campos_compatibles(((Record_)t1).getCampos(), ((Record_)t2).getCampos());
            else if(t1 instanceof Puntero_  && t2 instanceof Puntero_)
            	return ((Puntero_)t2).getT() == null || son_compatibles2(st, ((Puntero_) t1).getT(), ((Puntero_) t2).getT());
            else return false;
        }
    }
    
    private static boolean campos_compatibles(Campos campos1, Campos campos2) {
		if (campos1 instanceof Un_Campo && campos2 instanceof Un_Campo) {
			return son_compatibles(((Un_Campo) campos1).getCampo().getT(), ((Un_Campo) campos2).getCampo().getT());
		}
		else if (campos1 instanceof Muchos_Campos && campos2 instanceof Muchos_Campos) {
			return son_compatibles(((Muchos_Campos) campos1).getCampo().getT(), ((Muchos_Campos) campos2).getCampo().getT())
					&& campos_compatibles(((Muchos_Campos)campos1).getCampos(), ((Muchos_Campos)campos2).getCampos());
		}
		else {
			return false;
		}
	}

	public static Vinculable valor_de_id(Vinculos ts, String id) {
    	while(ts.padre != null) {
    		if (ts.containsKey(id)) 
    			return ts.get(id);
    		ts = ts.padre;
    	}
    	return ts.get(id);
    }

	public static Campo find_campo(Campos campos, String str) {
		while(campos instanceof Muchos_Campos) {
    		if (((Muchos_Campos)campos).getCampo().getStr().equals(str)) 
    			return ((Muchos_Campos)campos).getCampo();
    		campos = ((Muchos_Campos)campos).getCampos();
    	}
		
		if(((Un_Campo)campos).getCampo().getStr().equals(str)) {
			return ((Un_Campo)campos).getCampo();
		}
		else {
			return null;
		}
	}

	public static int num_elems(LParams lParams) {
		LParams l = lParams;
		int cont = 0;
		while(l instanceof Muchos_Params) {
			cont++;
			l = ((Muchos_Params) l).getParams();
		}
		if(l instanceof Un_Param)
			cont++;
		
		return cont;
	}

	public static int num_elems(LExp lExp) {
		LExp l = lExp;
		int cont = 0;
		while(l instanceof Muchas_Expr) {
			cont++;
			l = ((Muchas_Expr) l).getLExp();
		}
		if(l instanceof Una_Expr)
			cont++;
		
		return cont;
	}

	public static SalidaTipo check_params(LExp lExp, LParams lParams, Procesamiento p) {
		if(lExp instanceof Sin_Expr && lParams instanceof Sin_Params) {
			return SalidaTipo.OK;
		}
		else if (lExp instanceof Una_Expr && lParams instanceof Un_Param) {
			return check_param(((Una_Expr)lExp).getE(), ((Un_Param)lParams).getParam(), p);
		}
		else if (lExp instanceof Una_Expr && lParams instanceof Un_Param) {
			return ambos_ok(
					check_params(((Muchas_Expr)lExp).getLExp(), ((Muchos_Params)lParams).getParams(), p),
					check_param(((Muchas_Expr)lExp).getE(), ((Muchos_Params)lParams).getParam(), p));
		}
		else {
			System.err.println("Error en check_params");
			return SalidaTipo.ERROR;
		}
	}

	private static SalidaTipo check_param(E e, Param param, Procesamiento p) {
		e.procesa(p);
		if(param instanceof Param_Val) {
			if(Util.son_compatibles(e.getT(), param.getT()) && Util.es_designador(e) 
				&& (!(param.getT() instanceof Real_) || (e.getT() instanceof Real_ && param.getT() instanceof Real_))) {
				return SalidaTipo.OK;
			}
			else {
				System.err.println("Error en la correspondencia de parámetros por valor");
				return SalidaTipo.ERROR;
			}
		}
		else if (param instanceof Param_Ref) {
			if(Util.son_compatibles(e.getT(), param.getT())) {
				return SalidaTipo.OK;
			}
			else {
				System.err.println("Error en la correspondencia de parámetros por referencia");
				return SalidaTipo.ERROR;
			}
		}
		else {
			System.err.println("Error en check_param");
			return SalidaTipo.ERROR;
		}
	}
}