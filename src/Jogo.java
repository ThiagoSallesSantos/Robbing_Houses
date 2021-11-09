import java.util.ArrayList;
import java.util.Map;

public class Jogo{
	
	private Jogador jogador;
	private ArrayList<Tier> listaTier;
	private Ladrao ladrao;
	private Casa casaAtual;
	
	public Jogo(){
		this.jogador = new Jogador();
		this.listaTier = this.criarTierItens();
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
	
	private void executaComando(Comando comando, Ladrao ladrao){
		
		switch(comando.getComando()){
			case "roubar":
				this.executaRoubar(ladrao, comando.getAtributo());
				break;
			case "entrar":
				this.executaEntrar(ladrao, comando.getAtributo());
				break;
			case "esconder":
				this.executaEsconder(ladrao);
				break;
			case "sair":
				this.executaSair(ladrao);
				break;
			case "fugir":
				System.out.println("Fugiu cagao");
				this.executaFugir(ladrao);
				break;
			default:
				System.out.println("AAAA");
		}
		
	}
	
	private void executaFugir(Ladrao ladrao){
		this.jogador.proximaFase();
		this.jogador.adicionaPontuacao(ladrao.calculaRoubo());
	}
	
	private void executaRoubar(Ladrao ladrao, String atributo){
		int atributoInt = Integer.parseInt(atributo);
		Comodo comodoAtual = ladrao.getLocalAtual();
		ladrao.adicionaItemRoubado(comodoAtual.roubaItem(atributoInt-1));
	}
	
	private void executaEntrar(Ladrao ladrao, String atributo){
		int atributoInt = Integer.parseInt(atributo);
		Comodo comodoOrigem = ladrao.getLocalAtual();
		Map<Comodo, Boolean> portas = comodoOrigem.getPortas();
		ArrayList<Comodo> comodosAdjacentes = new ArrayList<>(portas.keySet());
		Comodo comodoDestino  = comodosAdjacentes.get(atributoInt-1);
		if(portas.get(comodoDestino)){
			if(ladrao.getVidaUtilChave() > 0){
				comodoOrigem.abrirPorta(comodoDestino);
				comodoDestino.abrirPorta(comodoOrigem);
				ladrao.usaChaveMestre();
			}
		}
		ladrao.movimentar(comodoDestino);
	}
	
	private void executaEsconder(Ladrao ladrao){
		ladrao.esconder();
	}
	
	private void executaSair(Ladrao ladrao){
		ladrao.sairEsconderijo();
	}
	
	private boolean verficarEncontro(Ladrao ladrao, Casa casa){
		if(!ladrao.getEscondido()){
			for(Dono dono : casa.getDonos()){
				if((ladrao.getLocalAtual() == dono.getLocalAtual()) || (ladrao.getLocalAtual() == dono.getProximoComodo())){
					return true;
				}
			}
		}
		return false;
	}
	
	private String getInfoLadrao(Ladrao ladrao){
		String texto = "Chave Mestre: " + ladrao.getVidaUtilChave() + " uso(s) restante\n";
		texto += "Itens roubados:\n";
		if(ladrao.getQtdItens() == 0){
			return texto + "Nenhum foi roubado ainda!\n";
		}
		for(Item itemRoubado : ladrao.getListaItensRoubados()){
			texto += itemRoubado + "\n";
		}
		return texto + "\n";
	}
	
	private String getInfoDonos(Casa casa){
		String texto = "Acao dos dono(s):\n";
		int cont = 1;
		for(Dono dono : casa.getDonos()){
			texto += cont + " - Dono" + ": " + dono + "\n"; 
		}
		return texto;
	}
	
	private String getInfoComodo(Comodo comodo){
		return "\nPorta(s):\n" + this.getPortasComodo(comodo) + "\nItens:\n" + this.getItensComodo(comodo) + "\nEsconderijo:\n" + this.getEsconderijoComodo(comodo) + "\nSaida:\n" + this.getSaidaComodo(comodo);
	}
	
	private String getPortasComodo(Comodo comodo){
		String texto = "Lista dos comodos adjacentes:\n";
		Map<Comodo, Boolean> portas = comodo.getPortas();
		int cont = 1;
		for(Comodo comodoAdjacente : portas.keySet()){
			texto += cont + " - " + comodoAdjacente + ((portas.get(comodoAdjacente)) ? " - (Trancada)" : "") + "\n";
			cont += 1;
		}
		return texto + "\n";
	}
	
	private String getItensComodo(Comodo comodo){
		String texto = "Lista dos itens para serem roubados:\n";
		if(comodo.getQtdItens() <= 0){
			return texto + "Neste comodo nao possui item para ser roubado!\n";
		}
		int cont = 1;
		for(Item item : comodo.getItens()){
			texto += cont + " - " + item + "\n";
			cont += 1;
		}
		return texto + "\n";
	}
	
	private String getEsconderijoComodo(Comodo comodo){
		return (comodo.getEsconderijo()) ? "Este comodo possui local para esconder!\n" : "Este comodo NAO possui local para esconder!\n";
	}
	
	private String getSaidaComodo(Comodo comodo){
		return (comodo.getSaida()) ? "Este comodo possui rota de fuga!\n" : "NAO possui rota de fuga!\n";
	}
	
	private void preMoveDonos(Casa casa){
		for(Dono dono : casa.getDonos()){
			dono.selecionaProximoComodo();
		}
	}
	
	private void moveDonos(Casa casa){
		for(Dono dono : casa.getDonos()){
			if(dono.getProximoComodo() != null){
				dono.movimentar(dono.getProximoComodo());
			}
		}
	}
	
	public ArrayList<Tier> criarTierItens(){
		
		ArrayList<Tier> auxListaTier = new ArrayList<Tier>();
		
		// Itens
		// Itens comuns
		Item item1 = new Item("Anel de plastico", 10);
		Item item2 = new Item("Colhar de plastico", 10);
		Item item3 = new Item("Pulseira da amizadade", 5);
		Item item4 = new Item("Bracelete de plástico", 15);
		Item item5 = new Item("Pregador de cabelo de plastico", 5);
		
		Tier tier1 = new Tier("Comum", 1);
		tier1.adicionaItem(item1);
		tier1.adicionaItem(item2);
		tier1.adicionaItem(item3);
		tier1.adicionaItem(item4);
		tier1.adicionaItem(item5);
		auxListaTier.add(tier1);
		
		// Itens incomuns
		Item item6 = new Item("Anel de ferro", 10);
		Item item7 = new Item("Colhar de ferro", 10);
		Item item8 = new Item("Pulseira de ferro", 5);
		Item item9 = new Item("Bracelete de ferro", 15);
		Item item10 = new Item("Pregador de cabelo de ferro", 5);
		
		Tier tier2 = new Tier("Incomum", 2);
		tier2.adicionaItem(item6);
		tier2.adicionaItem(item7);
		tier2.adicionaItem(item8);
		tier2.adicionaItem(item9);
		tier2.adicionaItem(item10);
		auxListaTier.add(tier2);
		
		// Itens raros
		Item item11 = new Item("Anel de prata", 10);
		Item item12 = new Item("Colhar de prata", 10);
		Item item13 = new Item("Pulseira de prata", 5);
		Item item14 = new Item("Bracelete de prata", 15);
		Item item15 = new Item("Pregador de cabelo de prata", 5);
		
		Tier tier3 = new Tier("Raro", 5);
		tier3.adicionaItem(item11);
		tier3.adicionaItem(item12);
		tier3.adicionaItem(item13);
		tier3.adicionaItem(item14);
		tier3.adicionaItem(item15);
		auxListaTier.add(tier3);
		
		// Itens lendários
		Item item16 = new Item("Anel de ouro", 10);
		Item item17 = new Item("Colhar de ouro", 10);
		Item item18 = new Item("Pulseira de ouro", 5);
		Item item19 = new Item("Bracelete de ouro", 15);
		Item item20 = new Item("Pregador de cabelo de ouro", 5);
		
		Tier tier4 = new Tier("Lendário", 5);
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