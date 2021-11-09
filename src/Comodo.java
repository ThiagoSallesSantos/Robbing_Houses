import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Comodo{

	private String nome;
	private boolean saida;
	private boolean esconderijo;
	private Map<Comodo, Boolean> portas;
	private ArrayList<Item> listaItens;

	public Comodo(String nome, Tier tier, boolean saida, boolean esconderijo){
		this.nome = nome;
		this.saida = saida;
		this.esconderijo = esconderijo;
		this.portas = new HashMap<Comodo, Boolean>();
		this.listaItens = this.selecionaItens(tier);
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public boolean getSaida(){
		return this.saida;
	}
	
	public boolean getEsconderijo(){
		return this.esconderijo;
	}
	
	public void adicionaPorta(Comodo comodosAdjacente, boolean fechada){
		this.portas.put(comodosAdjacente, fechada);
	}
	
	public Map<Comodo, Boolean> getPortas(){
		return this.portas;
	}
	
	public void abrirPorta(Comodo comodoX){
		this.portas.replace(comodoX, false);
	}
	
	private ArrayList<Item> selecionaItens(Tier tier){
		Random random = new Random();
		ArrayList<Item> auxListaItens = new ArrayList<Item>();
		int qtdItens = random.nextInt(5) + 1;
		for(int i=0; i < qtdItens; i++){
			auxListaItens.add(tier.getItem(random.nextInt(tier.getQtdItens())));
		}
		return auxListaItens;
	}
	
	public ArrayList<Item> getItens(){
		return this.listaItens;
	}
	
	public int getQtdItens(){
		return this.listaItens.size();
	}
	
	public Item roubaItem(int posicaoItem){
		return this.listaItens.remove(posicaoItem);
	}
	
	@Override
	public String toString(){
		int cont = 1;
		String texto = "<b>Comodo Atual:</b> " + this.getNome() + "<br/>";
		texto += "<br/><b>-LISTA PORTAS-</b><br/>Lista de comodos adjacentes:<br/>";
		for(Comodo comodoAdjacente : this.getPortas().keySet()){
			texto += cont + " - " + comodoAdjacente.getNome() + "<br/>";
			cont += 1;
		}
		texto += "<br/><b>-LISTA ITENS-</b><br/>Lista de itens para serem roubados:<br/>";
		if(this.getQtdItens() == 0){
			texto += "Este comodo nao possui itens para ser roubados!<br/>";
		}else{
			cont = 1;
			for(Item item : this.getItens()){
				texto += cont + " - " + item + "<br/>";
			}
		}
		return texto;
	}
	
}