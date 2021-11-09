import java.util.ArrayList;
import java.util.Random;

public class Casa{

	private String nome;
	private String descricao;
	private ArrayList<Dono> donos;
	private Comodo comodoInicial;
	private ArrayList<Comodo> comodos;
	private String dirImagem;
	
	public Casa(String nome, String descricao, String dirImagem){
		this.nome = nome;
		this.descricao = descricao;
		this.comodoInicial = null;
		this.comodos = new ArrayList<Comodo>();
		this.donos =  new ArrayList<Dono>();
		this.dirImagem = dirImagem;
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
	
	public String getDirImagem(){
		return this.dirImagem;
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
	
	@Override
	public String toString(){
		int cont = 1;
		String texto = "<b>Casa:</b> " + this.getNome() + "<br/><b>Descricao:</b> " + this.getDescricao() + "<br/><b>-LISTA DONOS-</b><br/>";
		for(Dono dono : this.getDonos()){
			texto += "Dono " + cont + " - " + dono + "<br/>";
			cont += 1;
		}
		return texto;
	}

}