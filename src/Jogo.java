import java.util.ArrayList;
import java.util.Map;

public class Jogo{
	
	private static Jogo jogo;
	private Interface interfaceJogo;
	private Jogador jogador;
	private ArrayList<Tier> listaTier;
	private Ladrao ladrao;
	private Casa casaAtual;
	private boolean status;
	
	private Jogo(){
		this.jogador = new Jogador();
		this.listaTier = this.criarTierItens();
		this.status = false;
	}
	
	public static Jogo getInstanciaJogo(){
		if(jogo == null){
			jogo = new Jogo();
		}
		return jogo;
	}
	
	public void start(){
		this.status = true;
		this.iniciaFase();
		this.interfaceJogo = new Interface(this);
		this.interfaceJogo.exibir();
	}
	
	public Comodo getComodoAtualLadrao(){
		return this.ladrao.getLocalAtual();
	}
	
	public Ladrao getLadrao(){
		return this.ladrao;
	}
	
	public Casa getCasaAtual(){
		return this.casaAtual;
	}
	
	public String getImagemCasaAtual(){
		return this.casaAtual.getDirImagem();
	}
	
	public String getInfoJogador(){
		return "<b>Vidas do Jogador:</b> " + this.jogador.getVidasRestantes() + "<br/>";
	}
	
	public void iniciaFase(){
		this.casaAtual = this.criarFases(this.jogador.getFaseAtual());
		this.ladrao = new Ladrao(this.casaAtual.getComodoInicial());
	}
	
	public String getComandosDisp(){
		String texto = "<b>-LISTA COMANDOS DISPONIVEIS-</b><br/>";
		if(!this.status){
			return texto + "<b>Comando:</b> <i>jogador (nome_jogador)</i><br/>";
		}
		if(this.verficarComandosEscSair()){
			return texto + "<b>Comando:</b> <i>sair</i><br/> -<i>voce precisa sair do esconderijo para realizar outra acoes</i>-";
		}
		texto += "<b>Comando:</b> <i>esconder</i><br/>";
		texto += "<b>Comando:</b> <i>entrar (numero_da_porta)</i><br/>";
		if(this.verificarComandoFugir()){
			texto += "<b>Comando:</b> <i>fugir</i><br/>";
		}
		if(verificarComandoRoubar()){
			texto += "<b>Comando:</b> <i>roubar (numero_do_item)</i><br/>";
		}
		return texto;
	}
	
	private boolean verificarComandoRoubar(){
		return (this.ladrao.getLocalAtual().getQtdItens() > 0);
	}
	
	private boolean verficarComandosEscSair(){
		return (this.ladrao.getEscondido());
	}
	
	private boolean verificarComandoFugir(){
		return (this.ladrao.getLocalAtual().getSaida());
	}
	
	public void executaComando(Comando comando){
		try{
			switch(comando.getComando()){
				case "roubar":
					this.executaRoubar(comando.getAtributo());
					break;
				case "entrar":
					this.executaEntrar(comando.getAtributo());
					break;
				case "esconder":
					this.executaEsconder();
					break;
				case "sair":
					this.executaSair();
					break;
				case "fugir":
					this.executaFugir();
					break;
				case "jogador":
					this.executaJogador(comando.getAtributo());
					break;
				default:
					break;
			}
			
			if(this.verficarEncontro()){
				this.interfaceJogo.enviaMensagem("Voce foi capturado!");
				this.jogador.reduzirVidasRestantes();
				if(this.jogador.getVidasRestantes() == 0){
					this.interfaceJogo.enviaMensagem("GAME OVER - Voce perdeu todas suas vidas!");
					this.finalizaJogo();
				}else{
					this.interfaceJogo.enviaMensagem("Casa " + this.casaAtual.getNome() + " foi reiniciada!");
					this.iniciaFase();
				}
			}else{
				this.moveDonos();
				this.preMoveDonos();
			}
			this.interfaceJogo.atualizaDados(this);
		}
		catch(NumberFormatException erroNumberFormat){
			this.interfaceJogo.enviaMensagem("Erro - Comando Invalido, o parametro do atributo nao e um numero!");
		}
		catch(RuntimeException erroRunTime){
			this.interfaceJogo.enviaMensagem(erroRunTime.getMessage());
		}catch(Exception erro){
			this.interfaceJogo.enviaMensagem("Erro - Ocorreu um erro nao esperado. \nErro foi: " + erro.getMessage());
		}
	}
	
	private void finalizaJogo(){
		this.interfaceJogo.enviaMensagem("Sua pontuação final foi de: " + this.jogador.getPontuacao() + " pontos.");
		this.interfaceJogo.enviaMensagem("Por favor use o comando jogador para registrar seu nome e pontuação em nossa Score Board!");
		this.interfaceJogo.limpaJanela();
		this.status = false;
	}
	
	private void executaJogador(String atributo){
		if(this.jogador != null){
			this.jogador.setNome(atributo);
			ScoreBoard sb = BinFile.loadSBFile();
			sb.addJogador(this.jogador);
			BinFile.saveSBFile(sb);
			this.interfaceJogo.limpaJanela();
			this.interfaceJogo.exibirInformacao("<html><strong>---SCOREBOARD---</strong><br/>" + sb + "</html>");
			this.jogador = null;
		}else{
			throw new RuntimeException("Erro - Comando Invalido, este comando ja foi utilizado, este comando e unico por jogatina!");
		}
	}
	
	private void executaFugir(){
		this.interfaceJogo.enviaMensagem("Voce fugiu da casa " + this.casaAtual.getNome() + "!");
		this.jogador.adicionaPontuacao(this.ladrao.calculaRoubo());
		this.jogador.proximaFase();
		if(this.jogador.getFaseAtual() > 5){
			this.interfaceJogo.enviaMensagem("Voce finalizou todas as casas! Parabens!");
			this.finalizaJogo();
		}else{
			this.iniciaFase();
		}
	}
	
	private void executaRoubar(String atributo) throws NumberFormatException{
		if(!this.status){
			throw new RuntimeException("Erro - Comando Invalido, jogo entrou em modo de finalizado!");
		}if(!this.verificarComandoRoubar()){
			throw new RuntimeException("Erro - Comando Invalido, nao tem item pra ser roubado neste comodo!");
		}
		int atributoInt = Integer.parseInt(atributo);
		Comodo comodoAtual = this.ladrao.getLocalAtual();
		if(!((atributoInt > 0) && (atributoInt <= comodoAtual.getQtdItens()))){
			throw new RuntimeException("Erro - Comando Invalido, parametro passado nao e valido!");
		}
		this.ladrao.adicionaItemRoubado(comodoAtual.roubaItem(atributoInt-1));
	}
	
	private void executaEntrar(String atributo) throws NumberFormatException{
		if(!this.status){
			throw new RuntimeException("Erro - Comando Invalido, jogo entrou em modo de finalizado!");
		}
		int atributoInt = Integer.parseInt(atributo);
		Comodo comodoOrigem = this.ladrao.getLocalAtual();
		Map<Comodo, Boolean> portas = comodoOrigem.getPortas();
		ArrayList<Comodo> comodosAdjacentes = new ArrayList<>(portas.keySet());
		if(!((atributoInt > 0) && (atributoInt <= comodosAdjacentes.size()))){
			throw new RuntimeException("Erro - Comando Invalido, parametro passado nao e valido!");
		}
		Comodo comodoDestino  = comodosAdjacentes.get(atributoInt-1);
		if(portas.get(comodoDestino)){
			if(this.ladrao.getVidaUtilChave() > 0){
				comodoOrigem.abrirPorta(comodoDestino);
				comodoDestino.abrirPorta(comodoOrigem);
				this.ladrao.usaChaveMestre();
			}else{
				throw new RuntimeException("Alerta - Voce nao pode mais abrir portas, devido que sua chave mestre estragou!");
			}
		}
		this.ladrao.movimentar(comodoDestino);
	}
	
	private void executaEsconder(){
		if(this.verficarComandosEscSair()){
			throw new RuntimeException("Erro - Comando Invalido, voce ja esta escondido!");
		}
		this.ladrao.esconder();
	}
	
	private void executaSair(){
		if(!this.verficarComandosEscSair()){
			throw new RuntimeException("Erro - Comando Invalido, voce precisa esta escondido, para usar este comando!");
		}
		this.ladrao.sairEsconderijo();
	}
	
	private boolean verficarEncontro(){
		if(!this.ladrao.getEscondido()){
			for(Dono dono : this.casaAtual.getDonos()){
				if((this.ladrao.getLocalAtual() == dono.getLocalAtual()) || (this.ladrao.getLocalAtual() == dono.getProximoComodo())){
					return true;
				}
			}
		}
		return false;
	}
	
	private void preMoveDonos(){
		for(Dono dono : this.casaAtual.getDonos()){
			dono.selecionaProximoComodo();
		}
	}
	
	private void moveDonos(){
		for(Dono dono : this.casaAtual.getDonos()){
			if(dono.getProximoComodo() != null){
				dono.movimentar(dono.getProximoComodo());
			}
		}
	}
	
	public ArrayList<Tier> criarTierItens(){
		
		ArrayList<Tier> auxListaTier = new ArrayList<Tier>();
		
		// Itens
		// Itens comuns
		Item item1 = new Item("Anel de plastico", 2);
		Item item2 = new Item("Colhar de plastico", 4);
		Item item3 = new Item("Pulseira de plastico", 2);
		Item item4 = new Item("Bracelete de plastico", 4);
		Item item5 = new Item("Pregador de cabelo de plastico", 1);
		
		Tier tier1 = new Tier("Comum", 1);
		tier1.adicionaItem(item1);
		tier1.adicionaItem(item2);
		tier1.adicionaItem(item3);
		tier1.adicionaItem(item4);
		tier1.adicionaItem(item5);
		auxListaTier.add(tier1);
		
		// Itens incomuns
		Item item6 = new Item("Anel de ferro", 5);
		Item item7 = new Item("Colhar de ferro", 7);
		Item item8 = new Item("Pulseira de ferro", 6);
		Item item9 = new Item("Bracelete de ferro", 7);
		Item item10 = new Item("Pregador de cabelo de ferro", 5);
		
		Tier tier2 = new Tier("Incomum", 2);
		tier2.adicionaItem(item6);
		tier2.adicionaItem(item7);
		tier2.adicionaItem(item8);
		tier2.adicionaItem(item9);
		tier2.adicionaItem(item10);
		auxListaTier.add(tier2);
		
		// Itens raros
		Item item11 = new Item("Anel de prata", 15);
		Item item12 = new Item("Colhar de prata", 20);
		Item item13 = new Item("Pulseira de prata", 17);
		Item item14 = new Item("Bracelete de prata", 20);
		Item item15 = new Item("Pregador de cabelo de prata", 14);
		
		Tier tier3 = new Tier("Raro", 5);
		tier3.adicionaItem(item11);
		tier3.adicionaItem(item12);
		tier3.adicionaItem(item13);
		tier3.adicionaItem(item14);
		tier3.adicionaItem(item15);
		auxListaTier.add(tier3);
		
		// Itens lendários
		Item item16 = new Item("Anel de ouro", 30);
		Item item17 = new Item("Colhar de ouro", 50);
		Item item18 = new Item("Pulseira de ouro", 35);
		Item item19 = new Item("Bracelete de ouro", 40);
		Item item20 = new Item("Pregador de cabelo de ouro", 25);
		
		Tier tier4 = new Tier("Lendario", 5);
		tier4.adicionaItem(item16);
		tier4.adicionaItem(item17);
		tier4.adicionaItem(item18);
		tier4.adicionaItem(item19);
		tier4.adicionaItem(item20);
		auxListaTier.add(tier4);
		
		return auxListaTier;
		
	}
	
	private Casa criarFases(int nivelCasa){
		
		Casa casa = null;
		
		switch(nivelCasa){
		
			case 1:
			
				// Casa - 1
				casa = new Casa(
						"Casa Rustica",
						"Casa bem aparentada, que se localiza em uma regiao nobre da cidade!",
						"resource/casas/Casa 1.png");
				
				//Comodos
				Comodo corredor1C1 = new Comodo("Corredor 1", this.listaTier.get(0), true, false);
				Comodo cozinhaC1 = new Comodo("Cozinha", this.listaTier.get(0), false, true);
				Comodo garagemC1 = new Comodo("Garagem", this.listaTier.get(2), true, true);
				Comodo salaC1 = new Comodo("Sala", this.listaTier.get(1), false, true);
				Comodo corredor2C1 = new Comodo("Corredor 2", this.listaTier.get(0), false, false);
				Comodo quarto1C1 = new Comodo("Quarto 1", this.listaTier.get(2), false, true);
				Comodo quarto2C1 = new Comodo("Quarto 2", this.listaTier.get(1), false, true);
				Comodo banheiro1C1 = new Comodo("Banheiro 1", this.listaTier.get(1), false, false);
				Comodo quarto3C1 = new Comodo("Quarto 3", this.listaTier.get(2), false, true);
				Comodo banheiro2C1 = new Comodo("Banheiro 2", this.listaTier.get(3), false, false);
			
				// Adicionando comodos a casa
				casa.adicionaComodo(corredor1C1);
				casa.adicionaComodo(cozinhaC1);
				casa.adicionaComodo(garagemC1);
				casa.adicionaComodo(salaC1);
				casa.adicionaComodo(corredor2C1);
				casa.adicionaComodo(quarto1C1);
				casa.adicionaComodo(quarto2C1);
				casa.adicionaComodo(banheiro1C1);
				casa.adicionaComodo(quarto3C1);
				casa.adicionaComodo(banheiro2C1);
				
				//Portas
				casa.adicionaPorta(corredor1C1, cozinhaC1, false);
				casa.adicionaPorta(corredor1C1, salaC1, false);
				casa.adicionaPorta(garagemC1, cozinhaC1, true);
				casa.adicionaPorta(corredor2C1, cozinhaC1, false);
				casa.adicionaPorta(corredor2C1, salaC1, false);
				casa.adicionaPorta(corredor2C1, quarto1C1, true);
				casa.adicionaPorta(corredor2C1, quarto2C1, false);
				casa.adicionaPorta(corredor2C1, banheiro1C1, false);
				casa.adicionaPorta(corredor2C1, quarto3C1, false);
				casa.adicionaPorta(banheiro2C1, quarto3C1, false);

				// Seleciona comodo inicial do ladrão
				casa.setComodoInicial(corredor1C1);
				
				// Adiciona os Donos
				casa.adicionaDonos(1);
				
				break;
			
			case 2:
			
				// Casa - 2
				casa = new Casa(
						"Casa Moderna",
						"Casa nova, bem localizada!",
						"resource/casas/Casa 2.png");
				
				// Comodos
				Comodo salaC2 = new Comodo("Sala", this.listaTier.get(0), true, false);
				Comodo garagemC2 = new Comodo("Garagem", this.listaTier.get(1), true, true);
				Comodo depositoC2 = new Comodo("Deposito", this.listaTier.get(0), false, true);
				Comodo corredorC2 = new Comodo("Corredor", this.listaTier.get(0), false, false);
				Comodo cozinhaC2 = new Comodo("Cozinha", this.listaTier.get(0), false, true);
				Comodo quarto1C2 = new Comodo("Quarto 1", this.listaTier.get(2), false, true);
				Comodo salaJantarC2 = new Comodo("Sala de Jantar", this.listaTier.get(1), false, false);
				Comodo banheiro1C2 = new Comodo("Banheiro 1", this.listaTier.get(1), false, false);
				Comodo salaTVC2 = new Comodo("Sala de TV", this.listaTier.get(1), true, false);
				Comodo quarto2C2 = new Comodo("Quarto 2", this.listaTier.get(2), false, true);
				Comodo quarto3C2 = new Comodo("Quarto 3", this.listaTier.get(1), false, true);
				Comodo banheiro2C2 = new Comodo("Banheiro 2", this.listaTier.get(3), false, false);
				Comodo banheiro3C2 = new Comodo("Banheiro 3", this.listaTier.get(3), false, false);
			
				// Adicionando comodos a casa
				casa.adicionaComodo(salaC2);
				casa.adicionaComodo(garagemC2);
				casa.adicionaComodo(depositoC2);
				casa.adicionaComodo(corredorC2);
				casa.adicionaComodo(cozinhaC2);
				casa.adicionaComodo(quarto1C2);
				casa.adicionaComodo(salaJantarC2);
				casa.adicionaComodo(banheiro1C2);
				casa.adicionaComodo(salaTVC2);
				casa.adicionaComodo(quarto2C2);
				casa.adicionaComodo(quarto3C2);
				casa.adicionaComodo(banheiro2C2);
				casa.adicionaComodo(banheiro3C2);
				
				// Portas
				casa.adicionaPorta(salaC2, garagemC2, true);
				casa.adicionaPorta(salaC2, depositoC2, false);
				casa.adicionaPorta(salaC2, corredorC2, false);
				casa.adicionaPorta(quarto1C2, corredorC2, true);
				casa.adicionaPorta(cozinhaC2, corredorC2, false);
				casa.adicionaPorta(cozinhaC2, salaJantarC2, false);
				casa.adicionaPorta(salaJantarC2, corredorC2, false);
				casa.adicionaPorta(banheiro1C2, corredorC2, false);
				casa.adicionaPorta(salaTVC2, corredorC2, false);
				casa.adicionaPorta(quarto2C2, corredorC2, false);
				casa.adicionaPorta(quarto2C2, banheiro2C2, true);
				casa.adicionaPorta(quarto3C2, corredorC2, true);
				casa.adicionaPorta(quarto3C2, banheiro3C2, false);
			
				// Seleciona comodo inicial do ladrão
				casa.setComodoInicial(salaC2);
				
				// Adiciona os Donos
				casa.adicionaDonos(2);
			
			default:
				break;
		}
		
		return casa;
		
	}
	
}