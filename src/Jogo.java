import java.util.ArrayList;
import java.util.Map;
/**
 * Esta classe representa o Jogo, sendo responsável por montar as casas, criar os itens, os
 * tiers, os cômodos e as portas, implementar e gerenciar os comandos dos jogadores.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */

public class Jogo{
	
	private static Jogo jogo;
	private Interface interfaceJogo;
	private Jogador jogador;
	private ArrayList<Tier> listaTier;
	private Ladrao ladrao;
	private Casa casaAtual;
	private boolean status;
/**
 * Construtor da classe Jogo. */	
	private Jogo(){
		this.jogador = new Jogador();
		this.listaTier = this.criarTierItens();
		this.status = false;
	}
/**
 * Método responsável por retornar a instância do jogo. Caso o jogo não exista, ele é criado.
 * @return
 */	
	public static Jogo getInstanciaJogo(){
		if(jogo == null){
			jogo = new Jogo();
		}
		return jogo;
	}
/**
 * Método responsável por iniciar o jogo, iniciando a fase, a interface e a exibindo. */	
	public void start(){
		this.status = true;
		this.iniciaFase();
		this.interfaceJogo = new Interface(this);
		this.interfaceJogo.exibir();
	}
/**
 * Método responsável por retornar o cômodo em que o ladrão se encontra.
 * @return retorna um objeto do tipo Comodo contendo o local em que o ladrão está.
 */	
	public Comodo getComodoAtualLadrao(){
		return this.ladrao.getLocalAtual();
	}
/**
 * Método responsável por retornar o ladrão do jogo.
 * @return retorna o atributo do tipo Ladrao.
 */	
	public Ladrao getLadrao(){
		return this.ladrao;
	}
/**
 * Método responsável por retornar a casa da fase em questão.
 * @return retorna um objeto do tipo Casa que contém a casa da fase atual.
 */	
	public Casa getCasaAtual(){
		return this.casaAtual;
	}
/**
 * Método responsável por retornar o diretório da imagem das casas.
 * @return retorna uma String que contém o diretório das imagens das casas.
 */	
	public String getImagemCasaAtual(){
		return this.casaAtual.getDirImagem();
	}
/**
 * Método responsável por retornar as informações do jogador. 
 * @return retorna uma String contendo as informações do jogador,
 */
	public String getInfoJogador(){
		return "<b>Vidas do Jogador:</b> " + this.jogador.getVidasRestantes() + "<br/>";
	}
/**
 * Método responsável por iniciar a fase em que o ladrão estará. */	
	public void iniciaFase(){
		this.casaAtual = this.criarFases(this.jogador.getFaseAtual());
		this.ladrao = new Ladrao(this.casaAtual.getComodoInicial());
	}
/**
 * Método responsável por retornar os comandos que aparecerão na tela. 
 * @return retornará uma String contendo o texto que será mostrado na tela que
 * vai depender da ação dos jogadores.
 */	
	public String getComandosDisp(){
		String texto = "<b>-LISTA COMANDOS DISPONIVEIS-</b><br/>";
		if(!this.status){
			return texto + "<b>Comando:</b> <i>jogador (nome_jogador)</i><br/>";
		}
		texto += "<b>Comando:</b> <i>esperar</i><br/>";
		if(this.verficarComandosEscSair()){
			return texto + "<b>Comando:</b> <i>sair</i><br/> -<i>OBS: voce precisa sair do esconderijo para realizar outra acoes</i>-";
		}
		texto += "<b>Comando:</b> <i>entrar (numero_da_porta)</i><br/>";
		texto += "<b>Comando:</b> <i>esconder</i><br/>";
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
/**
 * Método responsável por executar o comando dado pelo jogador. Cada comando terá uma acão diferente
 * dentro do jogo. Caso o comando não exista, uma mensagem de erro é exibida.
 * @param comando Atributo da classe Comando que contém o comando escolhido pelo jogador.
 */	
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
				case "esperar":
					this.executaEsperar();
					break;
				default:
					throw new RuntimeException("Erro - Comando Invalido, este comando nao existe!");
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
	
	private void executaEsperar(){
		if(!this.status){
			throw new RuntimeException("Erro - Comando Invalido, jogo entrou em modo de finalizado!");
		}
		this.interfaceJogo.enviaMensagem("Voce ficou parado esperando o movimentos dos Donos!");
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
		if(this.verficarComandosEscSair()){
			throw new RuntimeException("Erro - Comando Invalido, voce esta escondido, precisa sair do esconderijo para realizar esta acao!");
		}
		if(!this.verificarComandoFugir()){
			throw new RuntimeException("Erro - Comando Invalido, o comodo nao possui saida!");
		}
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
		}if(this.verficarComandosEscSair()){
			throw new RuntimeException("Erro - Comando Invalido, voce esta escondido, precisa sair do esconderijo para realizar esta acao!");
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
		}if(this.verficarComandosEscSair()){
			throw new RuntimeException("Erro - Comando Invalido, voce esta escondido, precisa sair do esconderijo para realizar esta acao!");
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
/**
 * Método responsável por criar os itens que estarão na casa e os tiers. Haverão 4 tiers:
 * comuns, incomuns, raros e lendários.
 * @return retorna um array contendo os tiers criados.
 */	
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
				Comodo corredor1C1 = new Comodo("Corredor 1", this.listaTier.get(0), true, true);
				Comodo cozinhaC1 = new Comodo("Cozinha", this.listaTier.get(1), false, true);
				Comodo garagemC1 = new Comodo("Garagem", this.listaTier.get(2), true, true);
				Comodo salaC1 = new Comodo("Sala", this.listaTier.get(1), false, true);
				Comodo corredor2C1 = new Comodo("Corredor 2", this.listaTier.get(0), false, false);
				Comodo quarto1C1 = new Comodo("Quarto 1", this.listaTier.get(2), false, true);
				Comodo quarto2C1 = new Comodo("Quarto 2", this.listaTier.get(1), true, true);
				Comodo banheiro1C1 = new Comodo("Banheiro 1", this.listaTier.get(1), false, true);
				Comodo quarto3C1 = new Comodo("Quarto 3", this.listaTier.get(2), false, true);
				Comodo banheiro2C1 = new Comodo("Banheiro 2", this.listaTier.get(3), true, true);
			
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
				Comodo salaC2 = new Comodo("Sala", this.listaTier.get(0), true, true);
				Comodo garagemC2 = new Comodo("Garagem", this.listaTier.get(2), true, true);
				Comodo depositoC2 = new Comodo("Deposito", this.listaTier.get(0), false, true);
				Comodo corredorC2 = new Comodo("Corredor", this.listaTier.get(0), false, false);
				Comodo cozinhaC2 = new Comodo("Cozinha", this.listaTier.get(1), false, true);
				Comodo quarto1C2 = new Comodo("Quarto 1", this.listaTier.get(2), false, true);
				Comodo salaJantarC2 = new Comodo("Sala de Jantar", this.listaTier.get(1), false, true);
				Comodo banheiro1C2 = new Comodo("Banheiro 1", this.listaTier.get(1), true, false);
				Comodo salaTVC2 = new Comodo("Sala de TV", this.listaTier.get(1), true, false);
				Comodo quarto2C2 = new Comodo("Quarto 2", this.listaTier.get(2), false, true);
				Comodo quarto3C2 = new Comodo("Quarto 3", this.listaTier.get(1), false, true);
				Comodo banheiro2C2 = new Comodo("Banheiro 2", this.listaTier.get(3), false, true);
				Comodo banheiro3C2 = new Comodo("Banheiro 3", this.listaTier.get(3), true, true);
			
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
			
			case 3:
				
				// Casa - 3
				casa = new Casa("Casa Contemporaneo", "Casa mais bonita do novo condominio da cidade!", "resource/casas/Casa 3.png");
				
				// Comodos
				Comodo salaC3 = new Comodo("Sala", this.listaTier.get(0), true, true);
				Comodo garagemC3 = new Comodo("Garagem", this.listaTier.get(1), true, true);
				Comodo depositoC3 = new Comodo("Deposito", this.listaTier.get(0), false, true);
				Comodo corredorC3 = new Comodo("Corredor", this.listaTier.get(0), false, false);
				Comodo quarto1C3 = new Comodo("Quarto 1", this.listaTier.get(3), false, false);
				Comodo cozinhaC3 = new Comodo("Cozinha", this.listaTier.get(1), true, false);
				Comodo banheiro1C3 = new Comodo("Banheiro 1", this.listaTier.get(2), true, false);
				Comodo salaJantarC3 = new Comodo("Sala de Jantar", this.listaTier.get(1), false, true);
				Comodo salaTVC3 = new Comodo("Sala de TV", this.listaTier.get(2), true, false);
				Comodo quarto2C3 = new Comodo("Quarto 2", this.listaTier.get(2), false, true);
				Comodo quarto3C3 = new Comodo("Quarto 3", this.listaTier.get(1), false, true);
				Comodo quarto4C3 = new Comodo("Quarto 4", this.listaTier.get(1), false, true);
				Comodo banheiro2C3 = new Comodo("Banheiro 2", this.listaTier.get(3), true, false);
				Comodo salaJogosC3 = new Comodo("Sala de Jogos", this.listaTier.get(2), true, false);
				Comodo escritorioC3 = new Comodo("Escritorio", this.listaTier.get(3), true, true);
				Comodo quarto5C3 = new Comodo("Quarto 5", this.listaTier.get(2), false, true);
				Comodo banheiro3C3 = new Comodo("Banheiro 3", this.listaTier.get(3), true, false);
				
				// Adiciona Comodo
				casa.adicionaComodo(salaC3);
				casa.adicionaComodo(garagemC3);
				casa.adicionaComodo(depositoC3);
				casa.adicionaComodo(corredorC3);
				casa.adicionaComodo(quarto1C3);
				casa.adicionaComodo(cozinhaC3);
				casa.adicionaComodo(banheiro1C3);
				casa.adicionaComodo(salaJantarC3);
				casa.adicionaComodo(salaTVC3);
				casa.adicionaComodo(quarto2C3);
				casa.adicionaComodo(quarto3C3);
				casa.adicionaComodo(quarto4C3);
				casa.adicionaComodo(banheiro2C3);
				casa.adicionaComodo(salaJogosC3);
				casa.adicionaComodo(escritorioC3);
				casa.adicionaComodo(quarto5C3);
				casa.adicionaComodo(banheiro3C3);
				
				// Adiciona Porta
				casa.adicionaPorta(salaC3, garagemC3, true);
				casa.adicionaPorta(salaC3, depositoC3, false);
				casa.adicionaPorta(salaC3, corredorC3, false);
				casa.adicionaPorta(cozinhaC3, corredorC3, false);
				casa.adicionaPorta(quarto1C3, corredorC3, true);
				casa.adicionaPorta(banheiro1C3, corredorC3, false);
				casa.adicionaPorta(salaJantarC3, corredorC3, false);
				casa.adicionaPorta(quarto2C3, corredorC3, false);
				casa.adicionaPorta(salaTVC3, corredorC3, false);
				casa.adicionaPorta(quarto3C3, corredorC3, false);
				casa.adicionaPorta(quarto4C3, corredorC3, false);
				casa.adicionaPorta(quarto4C3, banheiro2C3, true);
				casa.adicionaPorta(salaJogosC3, corredorC3, false);
				casa.adicionaPorta(salaJogosC3, escritorioC3, true);
				casa.adicionaPorta(quarto5C3, corredorC3, true);
				casa.adicionaPorta(quarto5C3, banheiro3C3, false);

				// Seleciona comodo inicial do ladrão
				casa.setComodoInicial(salaC3);
				
				// Adiciona os Donos
				casa.adicionaDonos(2);
				
				break;
				
			case 4:
			
				// Casa - 4
				casa = new Casa("Casarao Assombrado", "Casarao Assombrado, dizem que donos sao fantasmas, tome cuidado!", "resource/casas/Casa 4.png");
				
				// Comodos
				Comodo sacada1C4 = new Comodo("Sacada 1", this.listaTier.get(0), true, true);
				Comodo garagemC4 = new Comodo("Garagem", this.listaTier.get(2), true, true);
				Comodo despensaC4 = new Comodo("Despensa", this.listaTier.get(2), false, true);
				Comodo escada2C4 = new Comodo("Escada 2", this.listaTier.get(0), false, false);
				Comodo bibliotecaC4 = new Comodo("Biblioteca", this.listaTier.get(1), false, true);
				Comodo cozinhaC4 = new Comodo("Cozinha", this.listaTier.get(1), false, true);
				Comodo corredor1C4 = new Comodo("Corredor 1", this.listaTier.get(0), true, true);
				Comodo banheiro2C4 = new Comodo("Banheiro 2", this.listaTier.get(1), false, true);
				Comodo sala1C4 = new Comodo("Sala 1", this.listaTier.get(1), false, true);
				Comodo salaJantarC4 = new Comodo("Sala de Jantar", this.listaTier.get(1), false, true);
				Comodo quarto1C4 = new Comodo("Quarto 1", this.listaTier.get(1), true, false);
				Comodo banheiro1C4 = new Comodo("Banheiro 1", this.listaTier.get(3), false, true);
				Comodo escritorioC4 = new Comodo("Escritorio", this.listaTier.get(2), false, true);
				Comodo escada1C4 = new Comodo("Escada 1", this.listaTier.get(0), false, false);
				Comodo sala2C4 = new Comodo("Sala 2", this.listaTier.get(1), true, true);
				Comodo corredor2C4 = new Comodo("Corredor 2", this.listaTier.get(1), false, false);
				Comodo sacada2C4 = new Comodo("Sacada 2", this.listaTier.get(2), true, false);
				Comodo quarto2C4 = new Comodo("Quarto 2", this.listaTier.get(2), false, true);
				Comodo banheiro3C4 = new Comodo("Banheiro 3", this.listaTier.get(3), false, false);
				Comodo quarto3C4 = new Comodo("Quarto 3", this.listaTier.get(2), false, true);
				Comodo banheiro4C4 = new Comodo("Banheiro 4", this.listaTier.get(3), false, false);
				
				// Adiciona Comodo
				casa.adicionaComodo(sacada1C4);
				casa.adicionaComodo(garagemC4);
				casa.adicionaComodo(despensaC4);
				casa.adicionaComodo(escada2C4);
				casa.adicionaComodo(bibliotecaC4);
				casa.adicionaComodo(cozinhaC4);
				casa.adicionaComodo(corredor1C4);
				casa.adicionaComodo(banheiro2C4);
				casa.adicionaComodo(sala1C4);
				casa.adicionaComodo(salaJantarC4);
				casa.adicionaComodo(quarto1C4);
				casa.adicionaComodo(banheiro1C4);
				casa.adicionaComodo(escritorioC4);
				casa.adicionaComodo(escada1C4);
				casa.adicionaComodo(sala2C4);
				casa.adicionaComodo(sacada2C4);
				casa.adicionaComodo(quarto2C4);
				casa.adicionaComodo(banheiro3C4);
				casa.adicionaComodo(corredor2C4);
				casa.adicionaComodo(quarto3C4);
				casa.adicionaComodo(banheiro4C4);
				
				// Adiciona Porta
				casa.adicionaPorta(sacada1C4, garagemC4, true);
				casa.adicionaPorta(sacada1C4, escada2C4, false);
				casa.adicionaPorta(corredor1C4, escada2C4, false);
				casa.adicionaPorta(corredor1C4, bibliotecaC4, true);
				casa.adicionaPorta(corredor1C4, cozinhaC4, false);
				casa.adicionaPorta(corredor1C4, banheiro2C4, false);
				casa.adicionaPorta(corredor1C4, quarto1C4, false);
				casa.adicionaPorta(corredor1C4, sala1C4, false);
				casa.adicionaPorta(salaJantarC4, sala1C4, false);
				casa.adicionaPorta(salaJantarC4, corredor1C4, false);
				casa.adicionaPorta(escritorioC4, corredor1C4, true);
				casa.adicionaPorta(escritorioC4, banheiro1C4, false);
				casa.adicionaPorta(sala2C4, corredor1C4, true);
				casa.adicionaPorta(escada1C4, corredor1C4, false);
				casa.adicionaPorta(escada1C4, corredor2C4, false);
				casa.adicionaPorta(corredor2C4, escada2C4, false);
				casa.adicionaPorta(corredor2C4, sacada2C4, true);
				casa.adicionaPorta(corredor2C4, quarto2C4, false);
				casa.adicionaPorta(banheiro3C4, quarto2C4, true);
				casa.adicionaPorta(corredor2C4, quarto3C4, false);
				casa.adicionaPorta(banheiro4C4, quarto3C4, true);
				
				// Seleciona comodo inicial do ladrão
				casa.setComodoInicial(sacada1C4);
				
				// Adiciona os Donos
				casa.adicionaDonos(3);
			
			case 5:
				
				// Casa - 5
				casa = new Casa("Hotel", "Hotel 5 estrelas da cidade!", "resource/casas/Casa 5.png");
				
				// Comodos
				Comodo recepcaoC5 = new Comodo("Recepcao", this.listaTier.get(0), true, false);
				Comodo balcaoC5 = new Comodo("Balcao", this.listaTier.get(2), false, true);
				Comodo escritorioC5 = new Comodo("Escritorio", this.listaTier.get(1), false, false);
				Comodo cozinhaC5 = new Comodo("Cozinha", this.listaTier.get(1), false, true);
				Comodo estacionamentoC5 = new Comodo("Estacionamento", this.listaTier.get(0), true, true);
				Comodo escada1C5 = new Comodo("Escada 1", this.listaTier.get(2), false, false);
				Comodo escada2C5 = new Comodo("Escada 2", this.listaTier.get(2), false, false);
				Comodo elevadorC5 = new Comodo("Elevador", this.listaTier.get(0), false, true);
				Comodo corredor1C5 = new Comodo("Corredor 1", this.listaTier.get(1), true, false);
				Comodo quarto1C5 = new Comodo("Quarto 1", this.listaTier.get(2), false, true);
				Comodo banheiro1C5 = new Comodo("Banheiro 1", this.listaTier.get(3), false, false);
				Comodo quarto2C5 = new Comodo("Quarto 2", this.listaTier.get(2), true, true);
				Comodo banheiro2C5 = new Comodo("Banheiro 2", this.listaTier.get(3), false, false);
				Comodo quarto3C5 = new Comodo("Quarto 3", this.listaTier.get(2), true, true);
				Comodo banheiro3C5 = new Comodo("Banheiro 3", this.listaTier.get(3), false, false);
				Comodo quarto4C5 = new Comodo("Quarto 4", this.listaTier.get(2), false, true);
				Comodo banheiro4C5 = new Comodo("Banheiro 4", this.listaTier.get(3), false, false);
				Comodo corredor2C5 = new Comodo("Corredor 2", this.listaTier.get(1), true, true);
				Comodo quarto5C5 = new Comodo("Quarto 5", this.listaTier.get(2), true, true);
				Comodo banheiro5C5 = new Comodo("Banheiro 5", this.listaTier.get(3), false, false);
				Comodo quarto6C5 = new Comodo("Quarto 6", this.listaTier.get(2), false, true);
				Comodo banheiro6C5 = new Comodo("Banheiro 6", this.listaTier.get(3), false, false);
				Comodo quarto7C5 = new Comodo("Quarto 7", this.listaTier.get(2), true, true);
				Comodo banheiro7C5 = new Comodo("Banheiro 7", this.listaTier.get(3), false, false);
				Comodo quarto8C5 = new Comodo("Quarto 8", this.listaTier.get(2), false, true);
				Comodo banheiro8C5 = new Comodo("Banheiro 8", this.listaTier.get(3), false, false);
			
				// Adiciona Comodo
				casa.adicionaComodo(recepcaoC5);
				casa.adicionaComodo(balcaoC5);
				casa.adicionaComodo(escritorioC5);
				casa.adicionaComodo(estacionamentoC5);
				casa.adicionaComodo(escada1C5);
				casa.adicionaComodo(escada2C5);
				casa.adicionaComodo(elevadorC5);
				casa.adicionaComodo(corredor1C5);
				casa.adicionaComodo(quarto1C5);
				casa.adicionaComodo(banheiro1C5);
				casa.adicionaComodo(quarto2C5);
				casa.adicionaComodo(banheiro2C5);
				casa.adicionaComodo(quarto3C5);
				casa.adicionaComodo(banheiro3C5);
				casa.adicionaComodo(quarto4C5);
				casa.adicionaComodo(banheiro4C5);
				casa.adicionaComodo(corredor2C5);
				casa.adicionaComodo(quarto5C5);
				casa.adicionaComodo(banheiro5C5);
				casa.adicionaComodo(quarto6C5);
				casa.adicionaComodo(banheiro6C5);
				casa.adicionaComodo(quarto7C5);
				casa.adicionaComodo(banheiro7C5);
				casa.adicionaComodo(quarto8C5);
				casa.adicionaComodo(banheiro8C5);
			
				// Adiciona Porta
				casa.adicionaPorta(recepcaoC5, estacionamentoC5, false);
				casa.adicionaPorta(recepcaoC5, cozinhaC5, true);
				casa.adicionaPorta(recepcaoC5, escritorioC5, true);
				casa.adicionaPorta(balcaoC5, escritorioC5, false);
				casa.adicionaPorta(recepcaoC5, escada1C5, false);
				casa.adicionaPorta(corredor1C5, escada1C5, false);
				casa.adicionaPorta(corredor1C5, quarto1C5, false);
				casa.adicionaPorta(banheiro1C5, quarto1C5, true);
				casa.adicionaPorta(corredor1C5, quarto2C5, true);
				casa.adicionaPorta(banheiro2C5, quarto2C5, false);
				casa.adicionaPorta(corredor1C5, quarto3C5, false);
				casa.adicionaPorta(banheiro3C5, quarto3C5, true);
				casa.adicionaPorta(corredor1C5, quarto4C5, true);
				casa.adicionaPorta(banheiro4C5, quarto4C5, false);
				casa.adicionaPorta(corredor1C5, escada2C5, false);
				casa.adicionaPorta(corredor2C5, escada2C5, false);
				casa.adicionaPorta(corredor2C5, quarto5C5, true);
				casa.adicionaPorta(banheiro5C5, quarto5C5, true);
				casa.adicionaPorta(corredor2C5, quarto6C5, false);
				casa.adicionaPorta(banheiro6C5, quarto6C5, true);
				casa.adicionaPorta(corredor2C5, quarto7C5, true);
				casa.adicionaPorta(banheiro7C5, quarto7C5, false);
				casa.adicionaPorta(corredor2C5, quarto8C5, false);
				casa.adicionaPorta(banheiro8C5, quarto8C5, true);
				casa.adicionaPorta(recepcaoC5, elevadorC5, false);
				casa.adicionaPorta(corredor1C5, elevadorC5, false);
				casa.adicionaPorta(corredor2C5, elevadorC5, false);
			
				// Seleciona comodo inicial do ladrão
				casa.setComodoInicial(estacionamentoC5);
				
				// Adiciona os Donos
				casa.adicionaDonos(4);
			
			default:
				throw new RuntimeException("Erro - Casa selecionada nao existe!");
		}
		
		return casa;
		
	}
	
}