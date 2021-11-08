/**
* Classe que representa item que pode ser roubado pelo ladrão.
*/

public class Item{

	private String nome;
	private int valor;
	private Tier tier;
	
	public Item(String nome, int valor){
		this.valor = valor;
		this.nome = nome;
		this.tier = null;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public int getValor(){
		return this.valor;
	}
	
	public Tier getTier(){
		return this.tier;
	}
	
	public void setTier(Tier tier){
		this.tier = tier;
	}
	
	public int getPontos(){
		return this.getValor() * this.tier.getBonus();
	}
	
	@Override
	public String toString(){
		return this.getNome() + " - R$ " + this.getValor() + " - " + this.getTier(); 
	}
	
}