package implementacion.maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
	
	Scanner s;

	private List<Instruccion> codigoP;	//Lista de instrucciones en c�digo-p
	private Stack<Valor> pilaEvaluacion;	//Pila de evaluación
	private Valor[] datos;				//Memoria de datos
	private int pc;						//Contador del programa (para saber por qué instrucción vamos)

	public interface Instruccion {		//Interfaz que representa la funcionalidad que debe tener una instrucción.
		void ejecuta();
	}

	//Operaciones de la máquina-p
	private ISumaInt ISUMAINT;
	private class ISumaInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt()+opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "sumaInt";}
	}
	private ISumaReal ISUMAREAL;
	private class ISumaReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(opnd1.valorReal()+opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "sumaReal";}
	}
	private IRestaInt IRESTAINT;
	private class IRestaInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt()-opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "restaInt";}
	}
	private IRestaReal IRESTAREAL;
	private class IRestaReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(opnd1.valorReal()-opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "restaReal";}
	}
	private IMulInt IMULINT;
	private class IMulInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt()*opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "mulInt";}
	}
	private IMulReal IMULREAL;
	private class IMulReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(opnd1.valorReal()*opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "mulReal";}
	}
	private IDivInt IDIVINT;
	private class IDivInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			if(opnd2.valorInt() == 0)
				throw new EDivisionPorCero(pc);
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt()/opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "divInt";}
	}
	private IDivReal IDIVREAL;
	private class IDivReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			if(opnd2.valorReal() == 0)
				throw new EDivisionPorCero(pc);
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(opnd1.valorReal()/opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "divReal";}
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
		public String toString() {return "mod";}
	}
	private IAnd IAND;
	private class IAnd implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool()&&opnd2.valorBool()));
			pc++;
		} 
		public String toString() {return "and";}
	}
	private IOr IOR;
	private class IOr implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool()||opnd2.valorBool()));
			pc++;
		} 
		public String toString() {return "or";}
	}
	private INot INOT;
	private class INot implements Instruccion {
		public void ejecuta() {
			Valor opnd = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(!opnd.valorBool()));
			pc++;
		} 
		public String toString() {return "not";}
	}
	private INeg INEG;
	private class INeg implements Instruccion {
		public void ejecuta() {
			Valor opnd = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(-opnd.valorReal()));
			pc++;
		} 
		public String toString() {return "neg";}
	}
	private IBltInt IBLTINT;
	private class IBltInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() < opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "blt_int";}
	}
	private IBltReal IBLTREAL;
	private class IBltReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() < opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "blt_real";}
	}
	private IBltString IBLTSTRING;
	private class IBltString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) < 0));
			pc++;
		}
		public String toString() {return "blt_string";}
	}
	private IBltBool IBLTBOOL;
	private class IBltBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool() == false && opnd2.valorBool() == true));
			pc++;
		} 
		public String toString() {return "blt_bool";}
	}
	private IBleInt IBLEINT;
	private class IBleInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() <= opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "ble_int";}
	}
	private IBleReal IBLEREAL;
	private class IBleReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() <= opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "ble_real";}
	}
	private IBleString IBLESTRING;
	private class IBleString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) <= 0));
			pc++;
		} 
		public String toString() {return "ble_string";}
	}
	private IBleBool IBLEBOOL;
	private class IBleBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool() == false || opnd2.valorBool() == true));
			pc++;
		} 
		public String toString() {return "ble_bool";}
	}
	private IBgtInt IBGTINT;
	private class IBgtInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() > opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "bgt_int";}
	}
	private IBgtReal IBGTREAL;
	private class IBgtReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() > opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "bgt_real";}
	}
	private IBgtString IBGTSTRING;
	private class IBgtString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) > 0) );
			pc++;
		} 
		public String toString() {return "bgt_string";}
	}
	private IBgtBool IBGTBOOL;
	private class IBgtBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool() == true && opnd2.valorBool() == false));
			pc++;
		} 
		public String toString() {return "bgt_bool";}
	}
	private IBgeInt IBGEINT;
	private class IBgeInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() >= opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "bge_int";}
	}
	private IBgeReal IBGEREAL;
	private class IBgeReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() >= opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "bge_real";}
	}
	private IBgeString IBGESTRING;
	private class IBgeString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) >= 0) );
			pc++;
		} 
		public String toString() {return "bge_string";}
	}
	private IBgeBool IBGEBOOL;
	private class IBgeBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool() == true || opnd2.valorBool() == false));
			pc++;
		} 
		public String toString() {return "bge_bool";}
	}
	private IBeqInt IBEQINT;
	private class IBeqInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() == opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "beq_int";}
	}
	private IBeqReal IBEQREAL;
	private class IBeqReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() == opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "beq_real";}
	}
	private IBeqString IBEQSTRING;
	private class IBeqString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) == 0) );
			pc++;
		} 
		public String toString() {return "beq_string";}
	}
	private IBeqBool IBEQBOOL;
	private class IBeqBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool() == opnd2.valorBool()));
			pc++;
		} 
		public String toString() {return "beq_bool";}
	}
	private IBneInt IBNEINT;
	private class IBneInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() != opnd2.valorInt()));
			pc++;
		} 
		public String toString() {return "bne_int";}
	}
	private IBneReal IBNEREAL;
	private class IBneReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() != opnd2.valorReal()));
			pc++;
		} 
		public String toString() {return "bne_real";}
	}
	private IBneString IBNESTRING;
	private class IBneString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) != 0) );
			pc++;
		} 
		public String toString() {return "bne_string";}
	}
	private IBneBool IBNEBOOL;
	private class IBneBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool() != opnd2.valorBool()));
			pc++;
		} 
		public String toString() {return "bne_bool";}
	}

	private IRealToInt IREALTOINT;//No se usa en Tiny
	private class IRealToInt implements Instruccion {
		public void ejecuta() {
			Valor opnd = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt((int) opnd.valorReal()));
			pc++;
		} 
		public String toString() {return "realToInt";}
	}
	private IIntToReal IINTTOREAL;
	private class IIntToReal implements Instruccion {
		public void ejecuta() {
			Valor opnd = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal((int) opnd.valorInt()));
			pc++;
		} 
		public String toString() {return "intToReal";}
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
		public String toString() {return "apilaInt("+valor+")";}
	}

	private class IApilaReal implements Instruccion {
		private double valor;
		public IApilaReal(double val) {
			this.valor = val;
		}
		public void ejecuta() {
			pilaEvaluacion.push(new ValorReal(valor));
			pc++;
		} 
		public String toString() {return "apilaReal("+valor+")";}
	}

	private class IApilaBool implements Instruccion {
		private boolean valor;
		public IApilaBool(boolean valor) {
			this.valor = valor;
		}
		public void ejecuta() {
			pilaEvaluacion.push(new ValorBool(valor));
			pc++;
		} 
		public String toString() {return "apilaBool("+valor+")";}
	}
	private class IApilaString implements Instruccion {
		private String valor;
		public IApilaString(String valor) {
			this.valor = valor;
		}
		public void ejecuta() {
			pilaEvaluacion.push(new ValorString(valor));
			pc++;
		} 
		public String toString() {return "apilaString("+valor+")";}
	}
	private IReadString IREADSTRING;
	private class IReadString implements Instruccion {
		public void ejecuta() {
			pilaEvaluacion.push(new ValorString(s.nextLine()));
			pc++;
		} 
		public String toString() {return "readString";}
	}
	private IReadInt IREADINT;
	private class IReadInt implements Instruccion {
		public void ejecuta() {
			pilaEvaluacion.push(new ValorInt(s.nextInt()));
			pc++;
		} 
		public String toString() {return "readInt";}
	}
	private IReadReal IREADREAL;
	private class IReadReal implements Instruccion {
		public void ejecuta() {
			pilaEvaluacion.push(new ValorReal(s.nextDouble()));
			pc++;
		} 
		public String toString() {return "readReal";}
	}
	private IWrite IWRITE;
	private class IWrite implements Instruccion {
		public void ejecuta() {
			System.out.print(pilaEvaluacion.pop());
			pc++;
		} 
		public String toString() {return "write";}
	}
	
	private INl INL;	//New line
	private class INl implements Instruccion {
		public void ejecuta() {
			System.out.print("\n");
			pc++;
		}
		public String toString() {return "nl";}
	}
	
	private class IIrA implements Instruccion {
		private int dir;
		public IIrA(int dir) {
			this.dir = dir;
		}
		public void ejecuta() {
			pc=dir;
		} 
		public String toString() {return "ira("+dir+")";}
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
		public String toString() {return "irf("+dir+")";}
	}

	private class IIrV implements Instruccion {
		private int dir;
		public IIrV(int dir) {
			this.dir = dir;
		}
		public void ejecuta() {
			if(pilaEvaluacion.pop().valorBool()) { 
				pc=dir;
			}   
			else {
				pc++;
			}
		} 
		public String toString() {return "irv("+dir+")";}
	}

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
		public String toString() {return "mueve("+tam+")";}
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
		public String toString() {return "apilaind";}
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
		public String toString() {return "desapilaind";}
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
		public String toString() {return "alloc("+tam+")";}
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
		public String toString() {return "dealloc("+tam+")";}
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


	public Instruccion sumaInt() {return ISUMAINT;}
	public Instruccion sumaReal() {return ISUMAREAL;}
	public Instruccion restaInt() {return IRESTAINT;}
	public Instruccion restaReal() {return IRESTAREAL;}
	public Instruccion mulInt() {return IMULINT;}
	public Instruccion mulReal() {return IMULREAL;}
	public Instruccion divInt() {return IDIVINT;}
	public Instruccion divReal() {return IDIVREAL;}
	public Instruccion mod() {return IMOD;}
	public Instruccion and() {return IAND;}
	public Instruccion or() {return IOR;}
	public Instruccion not() {return INOT;}
	public Instruccion neg() {return INEG;}
	public Instruccion bltInt() {return IBLTINT;}
	public Instruccion bltReal() {return IBLTREAL;}
	public Instruccion bltString() {return IBLTSTRING;}
	public Instruccion bltBool() {return IBLTBOOL;}
	public Instruccion bleInt() {return IBLEINT;}
	public Instruccion bleReal() {return IBLEREAL;}
	public Instruccion bleString() {return IBLESTRING;}
	public Instruccion bleBool() {return IBLEBOOL;}
	public Instruccion bgtInt() {return IBGTINT;}
	public Instruccion bgtReal() {return IBGTREAL;}
	public Instruccion bgtString() {return IBGTSTRING;}
	public Instruccion bgtBool() {return IBGTBOOL;}
	public Instruccion bgeInt() {return IBGEINT;}
	public Instruccion bgeReal() {return IBGEREAL;}
	public Instruccion bgeString() {return IBGESTRING;}
	public Instruccion bgeBool() {return IBGEBOOL;}
	public Instruccion beqInt() {return IBEQINT;}
	public Instruccion beqReal() {return IBEQREAL;}
	public Instruccion beqString() {return IBEQSTRING;}
	public Instruccion beqBool() {return IBEQBOOL;}
	public Instruccion bneInt() {return IBNEINT;}
	public Instruccion bneReal() {return IBNEREAL;}
	public Instruccion bneString() {return IBNESTRING;}
	public Instruccion bneBool() {return IBNEBOOL;}
	public Instruccion realToInt() {return IREALTOINT;}
	public Instruccion intToReal() {return IINTTOREAL;}
	public Instruccion apilaInt(int val) {return new IApilaInt(val);}
	public Instruccion apilaReal(double val) {return new IApilaReal(val);}
	public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
	public Instruccion apilaString(String val) {return new IApilaString(val);}
	public Instruccion readString() {return IREADSTRING;}
	public Instruccion readInt() {return IREADINT;}
	public Instruccion readReal() {return IREADREAL;}
	public Instruccion write() {return IWRITE;}
	public Instruccion nl() {return INL;}
	public Instruccion apilaInd() {return IAPILAIND;}
	public Instruccion desapilaInd() {return IDESAPILAIND;}
	public Instruccion mueve(int tam) {return new IMueve(tam);}
	public Instruccion irA(int dir) {return new IIrA(dir);}
	public Instruccion irF(int dir) {return new IIrF(dir);}
	public Instruccion irV(int dir) {return new IIrV(dir);}
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
		ISUMAINT = new ISumaInt();
		ISUMAREAL = new ISumaReal();
		IRESTAINT = new IRestaInt();
		IRESTAREAL = new IRestaReal();
		IMULINT = new IMulInt();
		IMULREAL = new IMulReal();
		IDIVINT = new IDivInt();
		IDIVREAL = new IDivReal();
		IMOD = new IMod();
		IAND = new IAnd();
		IOR = new IOr();
		INOT = new INot();
		INEG = new INeg();
		//RELACIONALES
		IBLTINT = new IBltInt();
		IBLTREAL = new IBltReal();
		IBLTSTRING = new IBltString();
		IBLTBOOL = new IBltBool();
		IBLEINT = new IBleInt();
		IBLEREAL = new IBleReal();
		IBLESTRING = new IBleString();
		IBLEBOOL = new IBleBool();
		IBGTINT = new IBgtInt();
		IBGTREAL = new IBgtReal();
		IBGTSTRING = new IBgtString();
		IBGTBOOL = new IBgtBool();
		IBGEINT = new IBgeInt();
		IBGEREAL = new IBgeReal();
		IBGESTRING = new IBgeString();
		IBGEBOOL = new IBgeBool();
		IBEQINT = new IBeqInt();
		IBEQREAL = new IBeqReal();
		IBEQSTRING = new IBeqString();
		IBEQBOOL = new IBeqBool();
		IBNEINT = new IBneInt();
		IBNEREAL = new IBneReal();
		IBNESTRING = new IBneString();
		IBNEBOOL = new IBneBool();

		IREALTOINT = new IRealToInt();
		IINTTOREAL = new IIntToReal();
		IREADSTRING = new IReadString();
		IREADINT = new IReadInt();
		IWRITE = new IWrite();
		INL = new INl();
		IREADREAL = new IReadReal();
		IAPILAIND = new IApilaind();
		IDESAPILAIND = new IDesapilaind();
		IIRIND = new IIrind();
		IDUP = new IDup();
		ISTOP = new IStop();
		gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos,(tamdatos+tampila)-1,ndisplays);
		gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos+tampila,(tamdatos+tampila+tamheap)-1);
	}
	public void ejecuta() {
		s = new Scanner(System.in);
		while(pc != codigoP.size()) {
			codigoP.get(pc).ejecuta();
			System.err.println(pc);//TODO - borrar
		} 
		s.close();
	}
	public void muestraCodigo() {
		System.out.println("CodigoP");
		for(int i=0; i < codigoP.size(); i++) {
			System.out.println(" "+i+": "+codigoP.get(i));
		}
	}
	public void muestraEstado() {
		System.out.println("Tam datos: "+tamdatos);  
		System.out.println("Tam heap: "+tamheap);
		System.out.println("PP: "+gestorPilaActivaciones.pp());      
		System.out.print("Displays: ");
		for (int i=1; i <= ndisplays; i++) {
			System.out.print("\n");
			System.out.print(" "+i+": "+gestorPilaActivaciones.display(i));
		}
		System.out.println();
		System.out.println("Pila de evaluacion");
		for(int i=0; i < pilaEvaluacion.size(); i++) {
			System.out.println(" "+i+": "+pilaEvaluacion.get(i));
		}
		System.out.println("Datos");
		for(int i=0; i < datos.length; i++) {
			System.out.println(" "+i+": "+datos[i]);
		}
		System.out.println("PC: "+pc);
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
		m.ponInstruccion(m.sumaInt());
		m.ponInstruccion(m.apilaInt(5));
		m.ponInstruccion(m.desapilaInd());
		m.ponInstruccion(m.desapilad(1));
		m.ponInstruccion(m.irA(9));
		m.ponInstruccion(m.stop());
		m.ponInstruccion(m.apilaInt(0));
		m.ponInstruccion(m.apilad(1));
		m.ponInstruccion(m.apilaInt(0));
		m.ponInstruccion(m.sumaInt());
		m.ponInstruccion(m.mueve(1));
		m.ponInstruccion(m.desactiva(1,1));
		m.ponInstruccion(m.irInd());       
		m.ejecuta();
		m.muestraEstado();
	}
}
