import java.util.ArrayList;
import java.util.Random;

public class Comodo{

	private String nome;
	private boolean saida;
	private boolean esconderijo;
	private ArrayList<Porta> portas;
	private ArrayList<Item> listaItens;

	public Comodo(String nome, Tier tier, boolean saida, boolean esconderijo){
		this.nome = nome;
		this.saida = saida;
		this.esconderijo = esconderijo;
		this.portas = new ArrayList<Porta>();
		this.listaItens = this.selecionaItens(tier);
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public Tier getTier(){
		return this.tier;
	}
	
	public boolean getSaida(){
		return this.saida;
	}
	
	public boolean getEsconderijo(){
		return this.esconderijo;
	}
	
	public void adicionaPorta(Porta porta){
		this.portas.add(porta);
	}
	
	public void selecionaItens(Tier tier){
		Random random = new Random();
		int qtdItens = random.nextInt(4) + 1;
		for(int i=0; i < qtdItens; i++){
			listaItems.add(tier.getItem(random.nextInt(tier.getQtdItens() - 1)))
		}
	}
	
	public ArrayList<Comodo> getComodosAdjacentes(boolean dono){
		ArrayList<Comodo> comodosAdjacentes = new ArrayList<Comodo>();
		for(Porta porta : this.portas){
			if((porta.getTrancada() == false) || (dono == true)){
				comodosAdjacentes.add(porta.getOutroLado(this));
			}
		}
		return comodosAdjacentes;
	}
	
	@Override
	public String toString(){
		return this.getNome();
	}
	
}