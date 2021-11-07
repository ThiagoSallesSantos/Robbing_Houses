import java.io.Serializable;

@SuppressWarnings("serial")
public class Jogador implements Serializable{
	
	private String nome;
	private int pontuacao;
	private int vidasRestantes;
	private int faseAtual;
	
	public Jogador(String nome){
		this.nome = nome;
		this.pontuacao = 0;
		this.vidasRestantes = 3;
		this.faseAtual = 0;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public void setNome(){
		this.nome = nome;
	}
	
	public int getPontuacao(){
		return this.pontuacao;
	}
	
	public void adicionaPontuacao(int pontos){
		this.pontuacao += pontos;
	}
	
	public int getVidasRestantes(){
		return this.vidasRestantes;
	}
	
	public void reduzirVidasRestantes(){
		this.vidasRestantes -= 1;
	}
	
	public int getFaseAtual(){
		return this.faseAtual;
	}
	
	public void proximaFase(){
		this.faseAtual += 1;
	}
	
}