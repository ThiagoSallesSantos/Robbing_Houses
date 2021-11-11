/**
 * Esta classe representa o comando que o jogador irá escolher.
 * Ela irá fornecer o nome do comando e o atributo de cada atributo. *
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */
public class Comando{
	
	private String comando;
	private String atributo;

/**
 * Construtor da classe Comando.
 * @param comando objeto do tipo String que contém o nome do comando.
 * @param atributo objeto do tipo String que contém o atributo do comando.
 */
	public Comando(String comando, String atributo){
		this.comando = comando;
		this.atributo = atributo;
	}

/**
 * Método responsável por retornar o nome do comando.
 * @return retorna objeto tipo String contendo o nome do comando.
 */	
	public String getComando(){
		return this.comando;
	}
/**
 * Método responsável por retornar o atributo do comando.
 * @return retorna um tipo String contendo o atributo do comando.
 */	
	public String getAtributo(){
		return this.atributo;
	}
	
}