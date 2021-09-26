public class Comando{
	
	private String comando;
	private String atributo;
	
	public Comando(String comando, String atributo){
		this.comando = comando;
		this.atributo = atributo;
	}
	
	public String getComando(){
		return this.comando;
	}
	
	public String getAtributo(){
		return this.atributo;
	}
	
}