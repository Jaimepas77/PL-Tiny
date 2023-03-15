package implementacion.maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;




public class MaquinaP {
	//Excepciones
   @SuppressWarnings("serial")
   public static class EAccesoIlegitimo extends RuntimeException {} 
   @SuppressWarnings("serial")
   public static class EAccesoAMemoriaNoInicializada extends RuntimeException {
	   public EAccesoAMemoriaNoInicializada(int pc,int dir) {
		   super("pinst:"+pc+" dir:"+dir); 
	   } 
   }
   @SuppressWarnings("serial")
   public static class EDivisionPorCero extends RuntimeException {
	   public EDivisionPorCero(int pc) {
		   super("pinst:"+pc); 
	   } 
   }
   @SuppressWarnings("serial")
   public static class EAccesoFueraDeRango extends RuntimeException {} 
   
   //Gestores de estructuras de memoria
   private GestorMemoriaDinamica gestorMemoriaDinamica;
   private GestorPilaActivaciones gestorPilaActivaciones;
    
   //Representación de valores en memoria
   private class Valor {
      public int valorInt() {throw new EAccesoIlegitimo();}  
      public double valorReal() {throw new EAccesoIlegitimo();}  
      public boolean valorBool() {throw new EAccesoIlegitimo();} 
      public String valorString() {throw new EAccesoIlegitimo();} 
   } 
   private class ValorInt extends Valor {
      private int valor;
      public ValorInt(int valor) {
         this.valor = valor; 
      }
      public int valorInt() {return valor;}
      public String valorString() {return String.valueOf(valor);}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorReal extends Valor {
	   private double valor;
	   public ValorReal(double valor) {
		   this.valor = valor; 
	   }
	   public int valorInt() {return (int) valor;}
	   public double valorReal() {return valor;}
	   public String valorString() {return String.valueOf(valor);}
	   public String toString() {
		   return String.valueOf(valor);
	   }
   }
   private class ValorBool extends Valor {
      private boolean valor;
      public ValorBool(boolean valor) {
         this.valor = valor; 
      }
      public boolean valorBool() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorString extends Valor {
	   private String valor;
	   public ValorString(String valor) {
		   this.valor = valor; 
	   }
	   public String valorString() {return valor;}
	   public String toString() {
		   return String.valueOf(valor);
	   }
   }

   private List<Instruccion> codigoP;	//Lista de instrucciones en c�digo-p
   private Stack<Valor> pilaEvaluacion;	//Pila de evaluación
   private Valor[] datos; 				//Memoria de datos
   private int pc;						//Contador del programa (para saber por qué instrucción vamos)

   public interface Instruccion {		//Interfaz que representa la funcionalidad que debe tener una instrucción.
      void ejecuta();  
   }
   //Aritméticas
   private ISuma ISUMA;
   private class ISuma implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()+opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "suma";};
   }
   private IResta IRESTA;
   private class IResta implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()-opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "resta";};
   }
   private IMul IMUL;
   private class IMul implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()*opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "mul";};
   }
   private IDiv IDIV;
   private class IDiv implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         if(opnd2.valorInt() == 0)
        	 throw new EDivisionPorCero(pc);
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()/opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "div";};
   }
   private IMod IMOD;
   private class IMod implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         if(opnd2.valorInt() == 0)
        	 throw new EDivisionPorCero(pc);
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()%opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mod";};
   }
   private IAnd IAND;
   private class IAnd implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()&&opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "and";};
   }
   private IOr IOR;
   private class IOr implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()||opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "or";};
   }
   private INot INOT;
   private class INot implements Instruccion {
      public void ejecuta() {
         Valor opnd = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(!opnd.valorBool()));
         pc++;
      } 
      public String toString() {return "not";};
   }
   private INeg INEG;
   private class INeg implements Instruccion {
      public void ejecuta() {
         Valor opnd = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(-opnd.valorReal()));
         pc++;
      } 
      public String toString() {return "neg";};
   }
   //RELACIONALES
   
   
   private IRealToInt IREALTOINT;
   private class IRealToInt implements Instruccion {
      public void ejecuta() {
         Valor opnd = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt((int) opnd.valorReal()));
         pc++;
      } 
      public String toString() {return "realToInt";};
   }
   private IIntToReal IINTTOREAL;
   private class IIntToReal implements Instruccion {
	      public void ejecuta() {
	         Valor opnd = pilaEvaluacion.pop();
	         pilaEvaluacion.push(new ValorReal((int) opnd.valorInt()));
	         pc++;
	      } 
	      public String toString() {return "intToReal";};
	   }
   
   private class IApilaInt implements Instruccion {
      private int valor;
      public IApilaInt(int valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(valor)); 
         pc++;
      } 
      public String toString() {return "apilaInt("+valor+")";};
   }
   //Apila real

   private class IApilaBool implements Instruccion {
      private boolean valor;
      public IApilaBool(boolean valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorBool(valor)); 
         pc++;
      } 
      public String toString() {return "apilaBool("+valor+")";};
   }
   //Apila str

   private class IIrA implements Instruccion {
      private int dir;
      public IIrA(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
            pc=dir;
      } 
      public String toString() {return "ira("+dir+")";};
   }

   private class IIrF implements Instruccion {
      private int dir;
      public IIrF(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         if(! pilaEvaluacion.pop().valorBool()) { 
            pc=dir;
         }   
         else {
            pc++; 
         }
      } 
      public String toString() {return "irf("+dir+")";};
   }
   //Irv
   
   private Instruccion IIRIND;
   private class IIrind implements Instruccion {
       public void ejecuta() {
          pc = pilaEvaluacion.pop().valorInt();  
       }
       public String toString() {
          return "irind";                 
       }
   }
   
   private class IMueve implements Instruccion {
      private int tam;
      public IMueve(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
            int dirOrigen = pilaEvaluacion.pop().valorInt();
            int dirDestino = pilaEvaluacion.pop().valorInt();
            if ((dirOrigen + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            if ((dirDestino + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            for (int i=0; i < tam; i++) 
                datos[dirDestino+i] = datos[dirOrigen+i]; 
            pc++;
      } 
      public String toString() {return "mueve("+tam+")";};
   }
   
   private IApilaind IAPILAIND;
   private class IApilaind implements Instruccion {
      public void ejecuta() {
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        if (datos[dir] == null)  throw new EAccesoAMemoriaNoInicializada(pc,dir);
        pilaEvaluacion.push(datos[dir]);
        pc++;
      } 
      public String toString() {return "apilaind";};
   }

   private IDesapilaind IDESAPILAIND;
   private class IDesapilaind implements Instruccion {
      public void ejecuta() {
        Valor valor = pilaEvaluacion.pop();
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        datos[dir] = valor;
        pc++;
      } 
      public String toString() {return "desapilaind";};
   }

   private class IAlloc implements Instruccion {
      private int tam;
      public IAlloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = gestorMemoriaDinamica.alloc(tam);
        pilaEvaluacion.push(new ValorInt(inicio));
        pc++;
      } 
      public String toString() {return "alloc("+tam+")";};
   }

   private class IDealloc implements Instruccion {
      private int tam;
      public IDealloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = pilaEvaluacion.pop().valorInt();
        gestorMemoriaDinamica.free(inicio,tam);
        pc++;
      } 
      public String toString() {return "dealloc("+tam+")";};
   }
   
   private class IActiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       private int dirretorno;
       public IActiva(int nivel, int tamdatos, int dirretorno) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
           this.dirretorno = dirretorno;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.creaRegistroActivacion(tamdatos);
          datos[base] = new ValorInt(dirretorno);
          datos[base+1] = new ValorInt(gestorPilaActivaciones.display(nivel));
          pilaEvaluacion.push(new ValorInt(base+2));
          pc++;
       }
       public String toString() {
          return "activa("+nivel+","+tamdatos+","+dirretorno+")";                 
       }
   }
   
   private class IDesactiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       public IDesactiva(int nivel, int tamdatos) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.liberaRegistroActivacion(tamdatos);
          gestorPilaActivaciones.fijaDisplay(nivel,datos[base+1].valorInt());
          pilaEvaluacion.push(datos[base]); 
          pc++;
       }
       public String toString() {
          return "desactiva("+nivel+","+tamdatos+")";                 
       }

   }
   
   private class IApilad implements Instruccion {
       private int nivel;
       public IApilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones.display(nivel)));
         pc++;
       }
       public String toString() {
          return "apilad("+nivel+")";                 
       }

   }
   
   private class IDesapilad implements Instruccion {
       private int nivel;
       public IDesapilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         gestorPilaActivaciones.fijaDisplay(nivel,pilaEvaluacion.pop().valorInt());  
         pc++;
       }
       public String toString() {
          return "setd("+nivel+")";                 
       }
   }
   private IDup IDUP;
   private class IDup implements Instruccion {
       public void ejecuta() {
           pilaEvaluacion.push(pilaEvaluacion.peek());
           pc++;
       }
       public String toString() {
          return "dup";                 
       }
   }
   private Instruccion ISTOP;
   private class IStop implements Instruccion {
       public void ejecuta() {
           pc = codigoP.size();
       }
       public String toString() {
          return "stop";                 
       }
   }
   

   public Instruccion suma() {return ISUMA;}
   public Instruccion resta() {return IRESTA;}
   public Instruccion mul() {return IMUL;}
   public Instruccion div() {return IDIV;}
   public Instruccion mod() {return IMOD;}
   public Instruccion and() {return IAND;}
   public Instruccion or() {return IOR;}
   public Instruccion not() {return INOT;}
   public Instruccion neg() {return INEG;}
   //RELACIONALES
   public Instruccion blt() {return IBLT;}
   
   public Instruccion realToInt() {return IREALTOINT;}
   public Instruccion intToReal() {return IINTTOREAL;}
   public Instruccion apilaInt(int val) {return new IApilaInt(val);}
   public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
   public Instruccion apilaInd() {return IAPILAIND;}
   public Instruccion desapilaInd() {return IDESAPILAIND;}
   public Instruccion mueve(int tam) {return new IMueve(tam);}
   public Instruccion irA(int dir) {return new IIrA(dir);}
   public Instruccion irF(int dir) {return new IIrF(dir);}
   public Instruccion irInd() {return IIRIND;}
   public Instruccion alloc(int tam) {return new IAlloc(tam);} 
   public Instruccion dealloc(int tam) {return new IDealloc(tam);} 
   public Instruccion activa(int nivel,int tam, int dirretorno) {return new IActiva(nivel,tam,dirretorno);}
   public Instruccion desactiva(int nivel, int tam) {return new IDesactiva(nivel,tam);}
   public Instruccion apilad(int nivel) {return new IApilad(nivel);}
   public Instruccion desapilad(int nivel) {return new IDesapilad(nivel);}
   public Instruccion dup() {return IDUP;}
   public Instruccion stop() {return ISTOP;}
   public void ponInstruccion(Instruccion i) {
      codigoP.add(i); 
   }

   private int tamdatos;	//Tam de la memoria usada para datos
   private int tamheap;		//Tam de la memoria usada para mem. dinámica
   private int ndisplays;	//Num de displays
   public MaquinaP(int tamdatos, int tampila, int tamheap, int ndisplays) {
      this.tamdatos = tamdatos;
      this.tamheap = tamheap;
      this.ndisplays = ndisplays;
      this.codigoP = new ArrayList<>();  
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tamdatos+tampila+tamheap];
      this.pc = 0;
      //Para las instrucciones en que no se tienen parámetros, se reutiliza el mismo objeto.
      ISUMA = new ISuma();
      IRESTA = new IResta();
      IMUL = new IMul();
      IDIV = new IDiv();
      IMOD = new IMod();
      IAND = new IAnd();
      IOR = new IOr();
      INOT = new INot();
      INEG = new INeg();
      //RELACIONALES
      IBLT = new IBlt();
      
      IREALTOINT = new IRealToInt();
      IINTTOREAL = new IIntToReal();
      IAPILAIND = new IApilaind();
      IDESAPILAIND = new IDesapilaind();
      IIRIND = new IIrind();
      IDUP = new IDup();
      ISTOP = new IStop();
      gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos,(tamdatos+tampila)-1,ndisplays); 
      gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos+tampila,(tamdatos+tampila+tamheap)-1);
   }
   public void ejecuta() {
      while(pc != codigoP.size()) {
          codigoP.get(pc).ejecuta();
      } 
   }
   public void muestraCodigo() {
     System.out.println("CodigoP");
     for(int i=0; i < codigoP.size(); i++) {
        System.out.println(" "+i+":"+codigoP.get(i));
     }
   }
   public void muestraEstado() {
     System.out.println("Tam datos:"+tamdatos);  
     System.out.println("Tam heap:"+tamheap); 
     System.out.println("PP:"+gestorPilaActivaciones.pp());      
     System.out.print("Displays:");
     for (int i=1; i <= ndisplays; i++)
         System.out.print(i+":"+gestorPilaActivaciones.display(i)+" ");
     System.out.println();
     System.out.println("Pila de evaluacion");
     for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
     }
     System.out.println("Datos");
     for(int i=0; i < datos.length; i++) {
        System.out.println(" "+i+":"+datos[i]);
     }
     System.out.println("PC:"+pc);
   }
   
   public static void main(String[] args) {
       MaquinaP m = new MaquinaP(5,10,10,2);
        
          /*
            int x;
            proc store(int v) {
             x = v
            }
           &&
            call store(5)
          */
            
       
       m.ponInstruccion(m.activa(1,1,8));
       m.ponInstruccion(m.dup());
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.suma());
       m.ponInstruccion(m.apilaInt(5));
       m.ponInstruccion(m.desapilaInd());
       m.ponInstruccion(m.desapilad(1));
       m.ponInstruccion(m.irA(9));
       m.ponInstruccion(m.stop());
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.apilad(1));
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.suma());
       m.ponInstruccion(m.mueve(1));
       m.ponInstruccion(m.desactiva(1,1));
       m.ponInstruccion(m.irInd());       
       m.ejecuta();
       m.muestraEstado();
   }
}
