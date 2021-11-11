/**
* Classe que representa item que pode ser roubado pelo ladrão.
* Possui um nome, um valor e um nível.
*/

public class Item{

	private String nome;
	private int valor;
	private Tier tier;
/**
 * Construtor da classe Item
 * @param nome atributo do tipo String que contém o nome do item.
 * @param valor atributo do tipo inteiro que contém o valor do item.
 */	
	public Item(String nome, int valor){
		this.valor = valor;
		this.nome = nome;
		this.tier = null;
	}
/**
 * Método responsável por retornar o nome do Item.
 * @return retorna uma String contendo o nome do item.
 */	
	public String getNome(){
		return this.nome;
	}
/**
 * Método responsável por retornar o valor do item.
 * @return retorna um inteiro contendo o valor do item.
 */	
	public int getValor(){
		return this.valor;
	}
/**
 * Método responsável por retornar o tier (nível) do item
 * @return retorna um atributo do tipo Tier contendo o nível do item.
 */	
	public Tier getTier(){
		return this.tier;
	}
/**
 * Método responsável por setar o valor do tier do item.
 * @param tier atributo do tipo Tier contendo o valor do tier do item.
 */	
	public void setTier(Tier tier){
		this.tier = tier;
	}
/**
 * Método responsável por calcular e retornar os pontos fornecidos pelo item.
 * @return retorna um inteiro contendo o valor do item multiplicado com o valor do bônus.
 */	
	public int getPontos(){
		return this.getValor() * this.tier.getBonus();
	}
/**
 * Retorna uma String contendo as informações do item. */	
	@Override
	public String toString(){
		return this.getNome() + " - R$ " + this.getValor() + " - " + this.getTier(); 
	}
	
}