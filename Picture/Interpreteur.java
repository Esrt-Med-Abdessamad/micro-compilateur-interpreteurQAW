package Picture;

import java.util.Stack;
import java.util.Vector;

import Picture.Instr_Cible.Type;

public class Interpreteur {
	Vector<Instr_Cible>  VIC; // vecteur des instr. cibles
	Stack<Integer> stack = new Stack<Integer>();
	int S; //sommet de la pile
	int CO=0; // pointe sur la prochaine instr. à exécuter
	Instr_Cible instr_Cour;
	public Interpreteur(Vector<Instr_Cible> VIC) {
		this.VIC=VIC;
	}
	
	public void Interpreter(){
		while(VIC.elementAt(CO).getOperation()!=Type.END){
			instr_Cour = VIC.elementAt(CO);
			CO++;
			
			switch (instr_Cour.getOperation()) {
			case LOAD:
				stack.add(stack.push(instr_Cour.getOperant()));
			case LOADC:
				stack.add(instr_Cour.getOperant());
			case STORE:
				//stack[instr_Cour.getOperant()] = stack.pop();
			case ADD:
				
			case SUB:
				
			case DIV:
				
			case EQUAL:
				
			case INF:
				
			case SUP:
				
			case INFE:
				
			case SUPE:
				
			case JZERO:
				
			case JUMP:
				
			case READ:
				
			case WRITEC:
				
			case WRITE:
				
			case END:
				
			case NEQUAL:
				
				break;

			default:
				break;
			}
		}
	}

}
