import java.util.ArrayList;
import java.util.Map;
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
		Random random = new Random();
		Comodo comodoAtual = super.getLocalAtual();
		Map<Comodo, Boolean> portas = comodoAtual.getPortas();
		ArrayList<Comodo> comodosAdjacentes = new ArrayList<>(portas.keySet());
		this.setProximoComodo(comodosAdjacentes.get(random.nextInt(comodosAdjacentes.size())));
	}
	
	@Override
	public String toString(){
		return "<b>Local Atual:</b> " + super.getLocalAtual().getNome() + " <b>Proximo Local:</b> " + (this.getProximoComodo() == null ? "<b>NAO</b> definido" : this.getProximoComodo().getNome());
	}

}