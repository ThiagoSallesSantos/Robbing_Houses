import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * A classe Comodo representa um cômodo da casa. Nela contém o nome do cômodo, um valor booleano indicando se há 
 * ou não uma saída, um valor também booleano indicando se há ou não um esconderijo, um mapa representando as portas contendo
 * um cômodo adjacente em que este irá interagir e um booleano indicando se a porta ou não está fechada. 
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */
public class Comodo{

	private String nome;
	private boolean saida;
	private boolean esconderijo;
	private Map<Comodo, Boolean> portas;
	private ArrayList<Item> listaItens;

/**
 * Construtor da classe Comodo
 * @param nome uma String contendo o nome do cômodo.
 * @param tier um objeto do tipo Tier indicando o nível dos itens do cômodo.
 * @param saida um valor booleano indicando se há ou não saída.
 * @param esconderijo um valor booleano indicando se há ou não esconderijo.
 */

	public Comodo(String nome, Tier tier, boolean saida, boolean esconderijo){
		this.nome = nome;
		this.saida = saida;
		this.esconderijo = esconderijo;
		this.portas = new HashMap<Comodo, Boolean>();
		this.listaItens = this.selecionaItens(tier);
	}
/**
 * Método que retorna o nome do cômodo.
 * @return Retorna uma String contendo o nome do cômodo.
 */	
	public String getNome(){
		return this.nome;
	}

/**
 * Método que retorna a se há saída ou não.
 * @return Retorna um valor booleano indicando se há ou não saída no cômodo.
 */	

	public boolean getSaida(){
		return this.saida;
	}
/**
 * Método que retorna a se há esconderijo ou não.
 * @return Retorna um valor booleano indicando se há ou não esconderijo no cômodo.
 * @return
 */	
	public boolean getEsconderijo(){
		return this.esconderijo;
	}
/**
 * Método responsável por adicionar uma porta que ligará o cômodo em questão com seu adjacente.
 * @param comodosAdjacente objeto do tipo cômodo que contém o cômodo adjacente.
 * @param fechada objeto com valor booleano que indica se a porta está ou não fechada.
 */	
	public void adicionaPorta(Comodo comodosAdjacente, boolean fechada){
		this.portas.put(comodosAdjacente, fechada);
	}
/**
 * Método que retorna as portas que interagem com o cômodo.
 * @return retorna um mapa contendo as informações das portas.
 */	
	public Map<Comodo, Boolean> getPortas(){
		return this.portas;
	}
	
/**
 * Método que abre uma porta setando um valor false para a porta, indicando que a mesma está aberta.
 * @param comodoX objeto do tipo cômodo que representa o cômodo ajdacente.
 */	
	public void abrirPorta(Comodo comodoX){
		this.portas.replace(comodoX, false);
	}
	
/**
 * Método para selecionar "aleatoriamente" os itens para o comodo apartir de uma lista.
 * @param tier objeto do tipo Tier, é o tier da onde os itens que estarão presente no comodo serão retirados.
 * @return retorna uma lista de itens.
 */	
	private ArrayList<Item> selecionaItens(Tier tier){
		Random random = new Random();
		ArrayList<Item> auxListaItens = new ArrayList<Item>();
		int qtdItens = random.nextInt(5) + 1;
		tier.misturarItens();
		for(int i=0; i < qtdItens; i++){
			auxListaItens.add(tier.getItem(i));
		}
		return auxListaItens;
	}
/**
 * Método responsável por retornar a lista de itens presentes no cômodo.
 * @return retorna um array contendo os itens do cômodo.
 */	
	public ArrayList<Item> getItens(){
		return this.listaItens;
	}
/**
 * Método responsável por retornar a quantidade de itens presentes no cômodo.
 * @return retorna um inteiro contendo a quantidade de itens.
 */	
	public int getQtdItens(){
		return this.listaItens.size();
	}
/**
 * Método responsável por remover um item da lista (roubar).
 * @param posicaoItem um inteiro contendo a posição do item a ser removido.
 * @return retorna o item removido.
 */	
	public Item roubaItem(int posicaoItem){
		return this.listaItens.remove(posicaoItem);
	}
/**
 * Método responsável por retornar uma String contendo as informações do cômodo.
 * @return retorna uma String, contendo as informações do objeto.
 */	
	@Override
	public String toString(){
		int cont = 1;
		String texto = "<b>Comodo Atual:</b> " + this.getNome() + "<br/>";
		texto += "<b>Esconderijo:</b> " + (this.getEsconderijo() ? "Este comodo possui esconderijo" : "Este comodo <b>NAO</b> possui esconderijo") + "<br/>";
		texto += "<b>Rota de Fuga:</b> " + (this.getSaida() ? "Este comodo possui rota de fuga" : "Este comodo <b>NAO</b> possui rota de fuga") + "<br/>";
		texto += "<br/><b>-LISTA PORTAS-</b><br/>Lista de comodos adjacentes:<br/>";
		for(Comodo comodoAdjacente : this.getPortas().keySet()){
			texto += cont + " - " + comodoAdjacente.getNome() + (this.portas.get(comodoAdjacente) ? " - <b>(TRANCADA)</b>" : "") + "<br/>";
			cont += 1;
		}
		texto += "<br/><b>-LISTA ITENS-</b><br/>Lista de itens para serem roubados:<br/>";
		if(this.getQtdItens() == 0){
			texto += "Este comodo nao possui itens para ser roubados!<br/>";
		}else{
			cont = 1;
			for(Item item : this.getItens()){
				texto += cont + " - " + item + "<br/>";
				cont += 1;
			}
		}
		return texto;
	}
	
}