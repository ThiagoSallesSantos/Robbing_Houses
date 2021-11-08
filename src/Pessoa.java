public class Pessoa{

	private Comodo localAtual;
	
	public Pessoa(Comodo localInicial){
		this.localAtual = localInicial;
	}
	
	public Comodo getLocalAtual(){
		return this.localAtual;
	}
	
	public void movimentar(Comodo comodo){
		this.localAtual = comodo;
	}

}