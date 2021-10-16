import java.util.Scanner;

public class EntradaUsuario {

	private Scanner entrada;
	
	public EntradaUsuario(){
		this.entrada = new Scanner(System.in);
	}
	
	private String getInputUsuario(){
		System.out.print(">>> ");
		return this.entrada.nextLine();
	}
	
	public Comando getComandoUsuario(){
		String comando = null;
		String atributo = null;
		Scanner divisor = new Scanner(this.getInputUsuario());
		if(divisor.hasNext()){
			comando = divisor.next();
		}
		if(divisor.hasNext()){
			atributo = divisor.next();
		}
		return new Comando(comando, atributo);
	}

}