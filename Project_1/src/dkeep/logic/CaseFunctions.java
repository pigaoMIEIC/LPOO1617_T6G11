package dkeep.logic;

public class CaseFunctions {
	protected void caseFunction(char input,int i[]){
		switch (input) {
		case 's':i[1]++;break;
		case 'w':i[1]--;break;
		case 'd':i[0]++;break;
		case 'a':i[0]--;break;
		default: System.out.println("default input");
		}
	}
	
	protected void caseFunctionEntidade(char input,Entidade g){
		switch (input) {
		case 's':g.y++;break;
		case 'w':g.y--;break;
		case 'd':g.x++;break;
		case 'a':g.x--;break;
		default: System.out.println("default input");
		}
	}
}
