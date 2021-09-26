import java.util.ArrayList;

public class Comodo{

	private String nome;
	private Tier tier;
	private boolean saida;
	private boolean esconderijo;
	private ArrayList<Porta> portas;

	public Comodo(String nome, Tier tier, boolean saida, boolean esconderijo){
		this.nome = nome;
		this.tier = tier;
		this.saida = saida;
		this.esconderijo = esconderijo;
		this.portas = new ArrayList<Porta>();
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
	
}