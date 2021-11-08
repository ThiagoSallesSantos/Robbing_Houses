public class Inicializar{

	public static void main(String[] args){
		
		Jogador jogador = new Jogador();
		Jogo jogo = new Jogo(jogador);
		jogo.executaJogo();
		
	}

}