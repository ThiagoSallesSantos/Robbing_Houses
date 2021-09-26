public class Porta{
	
	private Comodo comodoA;
	private Comodo comodoB;
	private boolean trancada;
	
	public Porta(Comodo comodoA, Comodo comodoB, boolean trancada){
		this.comodoA = comodoA;
		this.comodoB = comodoB;
		this.trancada = trancada;
	}
	
	public boolean getTrancada(){
		return this.trancada;
	}
	
	public Comodo getOutroLado(Comodo comodoX){
		if(comodoX.equals(this.comodoA)){
			return this.comodoB;
		}return this.comodoA;
	}
	
}