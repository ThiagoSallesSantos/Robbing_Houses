import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class ScoreBoard implements Serializable {
	
	private ArrayList<Jogador> lista;
	
	public ScoreBoard(){
		this.lista = new ArrayList<Jogador>();
	}
	
	public void addJogador(Jogador jogador){
		lista.add(jogador);
	}
	
	@Override
	public String toString(){
		Collections.sort(this.lista);
		String texto = "";
		for(Jogador jogador : this.lista){
			texto += jogador + "<br/>";
		}
		return texto;
	}
	
}
