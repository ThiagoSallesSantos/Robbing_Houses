import java.util.ArrayList;

public class Ladrao extends Pessoa{

	private int vidaUtilChaveMestre;
	private ArrayList<Item> listaItensRoubados;
	private boolean esconder;
	
	public Ladrao(Comodo localInicial){
		super(localInicial);
		this.esconder = false;
		this.vidaUtilChaveMestre = 3;
		this.listaItensRoubados = new ArrayList<Item>();
	}
	
	public void adicionaItemRoubado(Item item){
		this.listaItensRoubados.add(item);
	}
	
	public boolean getEscondido(){
		return this.esconder;
	}
	
	public void esconder(){
		this.esconder = true;
	}
	
	public void sairEsconderijo(){
		this.esconder = false;
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
	
	public void usaChaveMestre(){
		this.vidaUtilChaveMestre -= 1;
	}
	
	public ArrayList<Item> getListaItensRoubados(){
		return this.listaItensRoubados;
	}
	
	public int getQtdItens(){
		return this.listaItensRoubados.size();
	}
	
	@Override
	public String toString(){
		String texto = "<b>Chave Mestre:</b> " + this.getVidaUtilChave() + " uso(s) restante<br/>";
		texto += "<br/><b>-LISTA ITENS ROUBADOS-</b><br/>";
		if(this.getQtdItens() == 0){
			return texto + "Nenhum item foi roubado ainda!<br/>";
		}
		for(Item itemRoubado : this.listaItensRoubados){
			texto += itemRoubado + "<br/>";
		}
		return texto;
	}

}