import java.util.ArrayList;

public class Ladrao extends Pessoa{

	private int vidaUtilChaveMestre;
	private ArrayList<Item> listaItensRoubados;
	
	public Ladrao(){
		this.vidaUtilChaveMestre = 3;
		this.listaItensRoubados = new ArrayList<Item>();
	}
	
	public void adicionaItemRoubado(Item item){
		this.listaItensRoubados.add(item);
	}
	
	public int calculaRoubo(){
		int pontos = 0;
		for(Item itemRoubado : this.listaItensRoubados){
			pontos += itemRoubado.getPontos();
		}
		return pontos;
	}
	
	public int getVidaUtilChave(){
		return this.vidaUtilChaveMestre;
	}
	
	public void abrirPorta(Porta porta){
		if(this.vidaUtilChaveMestre > 0){
			porta.abrirPorta();
			this.vidaUtilChaveMestre -= 1;
		}
	}

}