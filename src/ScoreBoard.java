import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

/**
 * Esta classe representa a pontuação obtida pelo jogador no jogo. Um objeto de seu tipo
 * será gravado em um arquivo binário.
 */
public class ScoreBoard implements Serializable {
	
	private ArrayList<Jogador> lista;
	
/**
 * Construtor da classe ScoreBoard. Nele uma lista de jogadores é criada. */	
	public ScoreBoard(){
		this.lista = new ArrayList<Jogador>();
	}
/**
 * Método responsável por adicionar um jogador na lista de jogadores.
 * @param jogador atributo do tipo Jogador que será adicionado na lista.
 */	
	public void addJogador(Jogador jogador){
		lista.add(jogador);
	}
/**
 * Retornará as pontuações obtidas pelos jogadores em ordem decrescente */	
	@Override
	public String toString(){
		Collections.sort(this.lista);
		String texto = "";
		for(Jogador jogador : this.lista){
			texto += jogador + "<br/>";
		}
		return texto;
	}
	
}
