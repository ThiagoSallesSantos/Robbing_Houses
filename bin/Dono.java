import java.util.ArrayList;
import java.util.Random;

public class Dono extends Pessoa {

	private Comodo proximoComodo;
	
	public Dono(Comodo localAtual){
		super(localAtual);
	}
	
	public Comodo getProximoComodo(){
		return this.proximoComodo;
	}
	
	private void setProximoComodo(Comodo comodo){
		this.proximoComodo = comodo;
	}

	public void selecionaProximoComodo(){
		Comodo comodoAtual = super.getLocalAtual();
		ArrayList<Comodo> comodosAdjacentes = comodoAtual.getComodosAdjacentes(true);
		Random random = new Random();
		this.setProximoComodo(comodosAdjacentes.get(random.nextInt(comodosAdjacentes.size() - 1)));
	}
	
	@Override
	public String toString(){
		return "Local Atual: " + super.getLocalAtual() + " Proximo Local: " + this.getProximoComodo();
	}

}