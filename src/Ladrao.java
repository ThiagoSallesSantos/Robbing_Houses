import java.util.ArrayList;
/**
 * Esta classe representa o ladrão do jogo.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */
public class Ladrao extends Personagem{

	private int vidaUtilChaveMestre;
	private ArrayList<Item> listaItensRoubados;
	private boolean esconder;

/**
 * Construtor da classe Ladrao.
 * @param localInicial atributo do tipo Comodo que contém o local inicial em que o ladrão se encontra
 */	
	public Ladrao(Comodo localInicial){
		super(localInicial);
		this.esconder = false;
		this.vidaUtilChaveMestre = 3;
		this.listaItensRoubados = new ArrayList<Item>();
	}
/**
 * Método responsável por adicionar um item à lista de itens roubados pelo ladrão.
 * @param item atributo do tipo Item que contém o item roubado.
 */	
	public void adicionaItemRoubado(Item item){
		this.listaItensRoubados.add(item);
	}
/**
 * Método responsável por verificar se o ladrão está escondido.
 * @return Retorna um valor booleano contendo true(escondido) ou false(não escondido).
 */	
	public boolean getEscondido(){
		return this.esconder;
	}
/**
 * Método responsável por setar o valor true caso o ladrão venha a se esconder. */	
	public void esconder(){
		this.esconder = true;
	}
/**
 * Método responsável por setar o valor false caso o ladrão queira sair do esconderijo. */	
	public void sairEsconderijo(){
		this.esconder = false;
	}
/**
 * Método responsável por calcular e retornar o valor dos itens roubados.
 * @return retorna um inteiro contendo o valor total roubado.
 */	
	public int calculaRoubo(){
		int pontos = 0;
		for(Item itemRoubado : this.listaItensRoubados){
			pontos += itemRoubado.getPontos();
		}
		return pontos;
	}
/**
 * Método responsável por retornar a vida útil da chave-mestre, ou seja, quantas
 * vezes ela poderá ser usada.
 * @return retorna um inteiro contendo a quantidade de vida útil da chave-mestre.
 */	
	public int getVidaUtilChave(){
		return this.vidaUtilChaveMestre;
	}
/**
 * Método responsável por decrementar a vida útil da chave-mestre quando a mesma é usada. */	
	public void usaChaveMestre(){
		this.vidaUtilChaveMestre -= 1;
	}
/**
 * Método responsável por retornar a lista dos itens já roubados.
 * @return retorna uma lista com os itens roubados.
 */	
	public ArrayList<Item> getListaItensRoubados(){
		return this.listaItensRoubados;
	}
/**
 * Método responsável por retornar a quantidade de itens roubados.
 * @return retorna um inteiro contendo a quantidade de itens roubados.
 */	
	public int getQtdItens(){
		return this.listaItensRoubados.size();
	}
/**
 * Retorna uma String contendo as informações sobre a vida útil da chave e a lista
 * de itens roubados.
 * @return retorna uma String, contendo informações do objeto.
*/	
	@Override
	public String toString(){
		String texto = "<b>Chave Mestre:</b> " + this.getVidaUtilChave() + " uso(s) restante<br/>";
		if(this.getEscondido()){
			texto += "<b>VOCE ESTA ESCONDIDO!</b> (<i>sai do esconderijo para realizar outras acoes</i>)<br/>";
		}
		texto += "<br/><b>-LISTA ITENS ROUBADOS-</b><br/>";
		if(this.getQtdItens() == 0){
			return texto + "Nenhum item foi roubado ainda!<br/>";
		}
		for(Item itemRoubado : this.getListaItensRoubados()){
			texto += itemRoubado + "<br/>";
		}
		return texto;
	}

}