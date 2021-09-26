import java.util.ArrayList;

public class Tier{
	
	private String descricao;
	private double preco;
	private ArrayList<Item> itens;
	
	public Tier(String descricao, double preco){
		this.descricao = descricao;
		this.preco = preco;
		this.itens = new ArrayList<Item>();
	}

	public double getPreco(){
		return this.preco;
	}
	
	public String descricao(){
		return this.descricao;
	}
	
	public void adicionaItem(Item item){
		this.itens.add(item);
	}
	
}
