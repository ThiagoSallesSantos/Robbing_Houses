import java.io.Serializable;

/**
 * Esta classe representa o jogador e contém informações como o nome, a pontuação obtida, as
 * vidas restantes e a fase em que o mesmo se encontra.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */
public class Jogador implements Comparable<Jogador>, Serializable {
	
	private String nome;
	private int pontuacao;
	private int vidasRestantes;
	private int faseAtual;
/**
 * Construtor da classe Jogador. Observa-se que todo jogador sempre começará na fase 1
 * e possuirá 3 vidas.
 */	
	public Jogador(){
		this.nome = null;
		this.pontuacao = 0;
		this.vidasRestantes = 3;
		this.faseAtual = 1;
	}
/**
 * Método responsável por retornar o nome do jogador.
 * @return Retorna uma String contendo o nome do jogador.
 */	
	public String getNome(){
		return this.nome;
	}
/**
 * Método responsável por setar o nome do jogador.
 * @param nome String contendo o nome do jogador.
 */	
	public void setNome(String nome){
		this.nome = nome;
	}
/**
 * Método responsável por retornar a pontuação do jogador.
 * @return Retorna um inteiro contendo a pontuação.
 */	
	public int getPontuacao(){
		return this.pontuacao;
	}
/**
 * Método responsável por adicionar a pontuação.
 * @param pontos inteiro contendo os pontos a serem somados na pontuação.
 */	
	public void adicionaPontuacao(int pontos){
		this.pontuacao += pontos;
	}
/**
 * Método responsável por retornar a quantidade de vidas restantes do jogador.
 * @return Retorna um inteiro contendo a quantidade de vidas restantes.
 */	
	public int getVidasRestantes(){
		return this.vidasRestantes;
	}
/**
 * Método responsável por decrementar as vidas restantes do jogador quando o mesmo for pego.
 */	
	public void reduzirVidasRestantes(){
		this.vidasRestantes -= 1;
	}
/**
 * Método responsável por retornar a fase em que o jogo está.
 * @return retorna um inteiro indicando a fase atual do jogo.
 */	
	public int getFaseAtual(){
		return this.faseAtual;
	}
/**
 * Método responsável por ir para a próxima fase. */	
	public void proximaFase(){
		this.faseAtual += 1;
	}
/**
 * Método que realiza a comparação entre as pontuações do jogador. 
 */	
	@Override
	public int compareTo(Jogador jogador){
		if(this.getPontuacao() > jogador.getPontuacao()){
			return -1;
		}else if(this.getPontuacao() < jogador.getPontuacao()){
			return 1;
		}
		return 0;
	}
/**
 * Método que retorna uma String contendo informações sobre o jogador, como seu nome
 * e sua pontuação. 
 * @return retorna uma String, contendo informações do objeto.
 */	
	@Override
	public String toString(){
		return String.format("%-20s %10s%n", this.getNome(), "R$" + this.getPontuacao());
	}
	
}