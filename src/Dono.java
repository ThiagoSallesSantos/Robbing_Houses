import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
/**
 * A classe Dono é responsável por representar o dono contendo informações sobre sua localização
 * na casa a cada rodada.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */
public class Dono extends Personagem {

	private Comodo proximoComodo;
/**
 * Construtor da classe Dono.
 * @param localAtual objeto do tipo Comodo indicando o cômodo em que o dono se encontra.
 */
	public Dono(Comodo localAtual){
		super(localAtual);
	}
/**
 * Método que retorna o próximo cômodo em que o dono estará.
 * @return retorna um objeto do tipo Comodo contendo o cômodo em que o dono estará.
 */	
	public Comodo getProximoComodo(){
		return this.proximoComodo;
	}
	
	private void setProximoComodo(Comodo comodo){
		this.proximoComodo = comodo;
	}
/**
 * Método responsável por selecionar aleatoriamente o próximo cômodo em que o dono estará.
 */
	public void selecionaProximoComodo(){
		Random random = new Random();
		Comodo comodoAtual = super.getLocalAtual();
		Map<Comodo, Boolean> portas = comodoAtual.getPortas();
		ArrayList<Comodo> comodosAdjacentes = new ArrayList<>(portas.keySet());
		this.setProximoComodo(comodosAdjacentes.get(random.nextInt(comodosAdjacentes.size())));
	}
/**
 * Retorna uma String contendo informações sobre a localização do dono na casa.
 * @return retorna uma String, contendo informações do objeto.
 */	
	@Override
	public String toString(){
		return "<b>Local Atual:</b> " + super.getLocalAtual().getNome() + " <b>Proximo Local:</b> " + (this.getProximoComodo() == null ? "<b>NAO</b> definido" : this.getProximoComodo().getNome());
	}

}