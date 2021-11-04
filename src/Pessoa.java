public class Pessoa{

	private Comodo localAtual;
	
	public Pessoa(Comodo localAtual){
		this.localAtual = localAtual;
	}
	
	public Comodo getLocalAtual(){
		return this.localAtual;
	}
	
	public void movimentar(Comodo comodo){
		this.localAtual = comodo;
	}

}