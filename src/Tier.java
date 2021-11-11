/**
 * Esta classe representa os tiers (níveis) dos itens do jogo.
 */
import java.util.ArrayList;
import java.util.Collections;

public class Tier{
	
	private String descricao;
	private int bonus;
	private ArrayList<Item> itens;
/**
 * Construtor da classe Tier.
 * @param descricao String contendo a descrição do Tier.
 * @param bonus Inteiro contendo o bônus do tier.
 */	
	public Tier(String descricao, int bonus){
		this.bonus = bonus;
		this.descricao = descricao;
		this.itens = new ArrayList<Item>();
	}
/**
 * Método responsável por retornar o bônus que o tier concede.
 * @return retorna um inteiro contendo o bônus do tier.
 */
	public int getBonus(){
		return this.bonus;
	}
/**
 * Método responsável por retornar a descrição do tier.
 * @return retorna uma String contendo a descrição do tier.
 */	
	public String getDescricao(){
		return this.descricao;
	}
/**
 * Método responsável por adicionar um item ao tier
 * @param item atributo do tipo Item que será adicionado no tier. Também seta o valor
 * do tier no item.
 */	
	public void adicionaItem(Item item){
		this.itens.add(item);
		item.setTier(this);
	}
/**
 * Método responsável por retornar um item a partir de uma determinada posição.
 * @param posicao atributo do tipo inteiro contendo a posição a ser buscada.
 * @return retorna o item da prosicção determinada.
 */	
	public Item getItem(int posicao){
		return this.itens.get(posicao);
	}
/**
 * Método responsável por retornar a quantidade de itens de um tier.
 * @return retorna um inteiro contendo o valor da quantidade de itens.
 */	
	public int getQtdItens(){
		return this.itens.size();
	}
/**
 * Método responsável reodernar de formar aleatória a lista de itens.
 */	
	public void misturarItens(){
		Collections.shuffle(this.itens);
	}
	
/**
 * Retorna um tipo String contendo a descrição do tier. */	
	@Override
	public String toString(){
		return this.getDescricao();
	}
	
}
