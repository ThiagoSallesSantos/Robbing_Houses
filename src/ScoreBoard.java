import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

/**
 * Esta classe representa a pontuação obtida pelo jogador no jogo. Um objeto de seu tipo
 * será gravado em um arquivo binário.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
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
 * Método responsável por retornar a lista de jogadores da Score.
 * @return retorna uma ArrayList dos jogadores salvo na ScoreBoard.
 */	
	public ArrayList<Jogador> getJogadores(){
		return this.lista;
	}
	
/**
 * Retornará as pontuações obtidas pelos jogadores em ordem decrescente 
 * @return retorna uma String, contendo informações do objeto.
 */	
	@Override
	public String toString(){
		Collections.sort(this.lista);
		String texto = "";
		int cont = 1;
		for(Jogador jogador : this.lista){
			texto += cont + " - " + jogador + "<br/>";
			cont += 1;
		}
		return texto;
	}
	
}
