import java.util.ArrayList;

public class Casa{

	private String nome;
	private String descricao;
	private Comodo comodoInicial;
	private ArrayList<Comodo> comodos;
	
	public Casa(String nome, String descricao){
		this.nome = nome;
		this.descricao = descricao;
		this.comodos = new ArrayList<Comodo>();
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public String getDescricao(){
		return this.descricao;
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

}