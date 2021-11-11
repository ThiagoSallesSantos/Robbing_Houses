import java.util.ArrayList;
import java.util.Random;

/**
 * A classe Casa será responsável por representar a casa em que o jogador irá roubar. Nela, conterá
 * algumas informações importantes como o nome, a descrição, os donos que nela moram, o cômodo inicial, uma
 * lista de cômodos e a imagem do mapa.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */
public class Casa{

	private String nome;
	private String descricao;
	private ArrayList<Dono> donos;
	private Comodo comodoInicial;
	private ArrayList<Comodo> comodos;
	private String dirImagem;
	
/**
 * Construtor da classe Casa.
 * @param nome atributo do tipo String que contém o nome da casa.
 * @param descricao atributo do tipo String que contém a descrição.
 * @param dirImagem atributo do tipo String que contém o diretório das imagens das casas no projeto.
 */
	public Casa(String nome, String descricao, String dirImagem){
		this.nome = nome;
		this.descricao = descricao;
		this.comodoInicial = null;
		this.comodos = new ArrayList<Comodo>();
		this.donos =  new ArrayList<Dono>();
		this.dirImagem = dirImagem;
	}

/**
 * Método para retornar o nome da casa.
 * @return Retorna uma String contendo o nome da casa.
 */
	public String getNome(){
		return this.nome;
	}
/**
 * Método para retornar a descrição da casa.
 * @return Retorna uma String contendo a descrição da casa.
 */	
	public String getDescricao(){
		return this.descricao;
	}
/**
 * Método para retornar a lista de donos residentes na casa.
 * @return Retorna um Array do tipo Dono contendo os todos os donos da casa.
 */
	public ArrayList<Dono> getDonos(){
		return this.donos;
	}
/**
 * Método para retornar o cômodo em que o jogador se encontra ao iniciar a fase.
 * @return Retorna um objeto do tipo Comodo contendo o cômodo inicial.
 */
	public Comodo getComodoInicial(){
		return this.comodoInicial;
	}
/**
 * Método para retornar o diretório da s imagens das casas.
 * @return Retorna uma String contendo o diretório das imagem.
 */	
	public String getDirImagem(){
		return this.dirImagem;
	}
	
/**
 * Método para setar o cômodo em que o jogador estará ao iniciar a fase.
 * @param comodoInicial objeto do tipo Comodo que contém o cômodo inicial.
 */
	public void setComodoInicial(Comodo comodoInicial){
		this.comodoInicial = comodoInicial;
	}

/**
 * Método responsável por adicionar um cômodo à lista de cômodos.
 * @param comodo objeto do tipo Comodo que será adicionado na lista.
 */
	public void adicionaComodo(Comodo comodo){
		this.comodos.add(comodo);
	}
/**
 * Método responsável por adicionar uma porta entre dois cômodos.
 * @param comodoA objeto do tipo Comodo contendo um dos cômodos a interagir com a porta.
 * @param comodoB objeto do tipo Comodo contendo um dos cômodos a interagir com a porta.
 * @param trancada valor booleano que indicará se a porta está ou não trancada.
 */	
	public void adicionaPorta(Comodo comodoA, Comodo comodoB, boolean trancada){
		comodoA.adicionaPorta(comodoB, trancada);
		comodoB.adicionaPorta(comodoA, trancada);
	}

/**
 * Método que adiciona uma quantidade pré-definida de donos em uma lista.
 * @param qtdDonos objeto do tipo inteiro que contém a quantidade de donos da casa.
 */	
	public void adicionaDonos(int qtdDonos){
		for(int i=0; i < qtdDonos; i++){
			this.donos.add(new Dono(this.selecionaComodoDono()));
		}
	}
/**
 * Método responsável por selecionar em qual cômodo o Dono se encontra.
 * @return retorna um objeto do tipo Comodo que indica em qual cômodo o dono está.
 */	
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
/**
 * Método que retorna uma string contendo todas as informações da casa.
 */	
	@Override
	public String toString(){
		int cont = 1;
		String texto = "<b>Casa:</b> " + this.getNome() + "<br/><b>Descricao:</b> " + this.getDescricao() + "<br/><br/><b>-LISTA DONOS-</b><br/>";
		for(Dono dono : this.getDonos()){
			texto += "Dono " + cont + " - " + dono + "<br/>";
			cont += 1;
		}
		return texto;
	}

}