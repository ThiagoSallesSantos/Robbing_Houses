public class Porta{
	
	private Comodo comodoA;
	private Comodo comodoB;
	private boolean trancada;
	
	public Porta(Comodo comodoA, Comodo comodoB, boolean trancada){
		this.comodoA = comodoA;
		this.comodoA.adicionaPorta(this);
		this.comodoB = comodoB;
		this.comodoB.adicionaPorta(this);
		this.trancada = trancada;
	}
	
	public boolean getTrancada(){
		return this.trancada;
	}
	
	public void abrirPorta(){
		this.trancada = false;
	}
	
	public Comodo getOutroLado(Comodo comodoX){
		if(comodoX.equals(this.comodoA)){
			return this.comodoB;
		}return this.comodoA;
	}
	
}