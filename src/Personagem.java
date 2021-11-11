/**
 * Esta classe representa uma personagem, que gerencia a movimentação.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */
public class Personagem{

	private Comodo localAtual;
/**
 * Construtor da classe Personagem.
 * @param localInicial atributo da classe Comodo que contém o local inicial.
 */	
	public Personagem(Comodo localInicial){
		this.localAtual = localInicial;
	}
/**
 * Método responsável por retornar o cômodo em que a personagem se encontra.
 * @return retorna um objeto do tipo Comodo que contém o valor do local atual.
 */	
	public Comodo getLocalAtual(){
		return this.localAtual;
	}
/**
 * Método responśavel pela movimentação da personagem em questão.
 * @param comodo atributo do tipo Comodo contendo o valor do cômodo em que a personagem entrará.
 */	
	public void movimentar(Comodo comodo){
		this.localAtual = comodo;
	}

}