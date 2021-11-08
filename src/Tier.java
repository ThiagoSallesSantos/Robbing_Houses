import java.util.ArrayList;

public class Tier{
	
	private String descricao;
	private int bonus;
	private ArrayList<Item> itens;
	
	public Tier(String descricao, int bonus){
		this.bonus = bonus;
		this.descricao = descricao;
		this.itens = new ArrayList<Item>();
	}

	public int getBonus(){
		return this.bonus;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
	
	public void adicionaItem(Item item){
		this.itens.add(item);
		item.setTier(this);
	}
	
	public Item getItem(int posicao){
		return this.itens.get(posicao);
	}
	
	public int getQtdItens(){
		return this.itens.size();
	}
	
	@Override
	public String toString(){
		return this.getDescricao();
	}
	
}
