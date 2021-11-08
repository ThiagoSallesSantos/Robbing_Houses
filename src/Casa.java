import java.util.ArrayList;
import java.util.Random;

public class Casa{

	private String nome;
	private String descricao;
	private ArrayList<Dono> donos;
	private Comodo comodoInicial;
	private ArrayList<Comodo> comodos;
	
	public Casa(String nome, String descricao){
		this.nome = nome;
		this.descricao = descricao;
		this.comodoInicial = null;
		this.comodos = new ArrayList<Comodo>();
		this.donos =  new ArrayList<Dono>();
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public String getDescricao(){
		return this.descricao;
	}

	public ArrayList<Dono> getDonos(){
		return this.donos;
	}

	public Comodo getComodoInicial(){
		return this.comodoInicial;
	}
	
	public void setComodoInicial(Comodo comodoInicial){
		this.comodoInicial = comodoInicial;
	}

	public void adicionaComodo(Comodo comodo){
		this.comodos.add(comodo);
	}
	
	public void adicionaPorta(Comodo comodoA, Comodo comodoB, boolean trancada){
		comodoA.adicionaPorta(comodoB, trancada);
		comodoB.adicionaPorta(comodoA, trancada);
	}
	
	public void adicionaDonos(int qtdDonos){
		for(int i=0; i < qtdDonos; i++){
			this.donos.add(new Dono(this.selecionaComodoDono()));
		}
	}
	
	public Comodo selecionaComodoDono(){
		Random random = new Random();
		Comodo comodoDono;
		while(true){
			comodoDono = this.comodos.get(random.nextInt(this.comodos.size()));
			if(!comodoDono.equals(this.comodoInicial)){
				break;
			}
		}
		return comodoDono;
	}

}