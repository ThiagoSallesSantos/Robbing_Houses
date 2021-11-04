import java.util.ArrayList;
import java.util.Map;

public class Jogo{
	
	private Jogador jogador;
	private ArrayList<Casa> listaFases;
	private EntradaUsuario entradaUsr;
	private SaidaUsuario saidaUsr;
	private ArrayList<Tier> listaTier;
	
	public Jogo(Jogador jogador){
		this.jogador = jogador;
		this.listaTier = this.criaTierItens();
		this.listaFases = this.criaFases();
		this.entradaUsr = new EntradaUsuario();
		this.saidaUsr = new SaidaUsuario();
	}
	
	public void executaJogo(){
		Casa casaAtual = this.getFase(this.jogador.getFaseAtual());
		Ladra ladrao = new Ladrao(casa.getComodoInicial());
		while((this.jogador.getVidasRestantes() > 0) && (casaAtual != null)){
			this.preMoveDonos(casaAtual);
			this.saidaUsr.outputUsr("\n----------------------Comodo Infomacoes----------------------\n");
			this.saidaUsr.outputUsr(this.getInfoComodo(ladrao.getLocalAtual()));
			this.saidaUsr.outputUsr("\n----------------------Dono Infomacoes----------------------\n");
			this.saidaUsr.outputUsr(this.getInfoDono());
			this.saidaUsr.outputUsr("\n----------------------Ladrao Infomacoes----------------------\n");
			this.moveDonos(casaAtual);
		}
	}
	
	private String getInfoDono(Casa casa){
		String texto = "Acao dos dono(s):";
		int cont = 1;
		for(Dono dono : casa.getDonos()){
			texto += "Dono-" + (String)cont + ": " + dono; 
		}
		return texto;
	}
	
	private String getInfoComodo(Comodo comodo){
		return comodo + "\nPorta(s):\n" + this.getPortasComodo(comodo) + "\nItens:\n" + this.getItensComodo(comodo) + "\nEsconderijo:\n" + this.getEsconderijoComodo(comodo) + "\nSaida:\n" + this.getSaidaComodo(comodo);
	}
	
	private String getPortasComodo(Comodo comodo){
		String texto = "entrar <nome_comodo> \n";
		Map<Comodo, Boolean> portas = comodo.getPortas();
		for(Comodo comodoAdjacente : portas.keySet()){
			texto += comodoAdjacente + ((portas.get(comodoAdjacente)) ? " - (Trancada)" : "");
		}
		return texto + "\n";
	}
	
	private String getItensComodo(Comodo comodo){
		String texto = "roubar <nome_item> \n";
		if(comodo.getQtdItens() <= 0){
			return texto + "Neste comodo nao possui item para ser roubado! \n";
		}
		texto += "Lista itens para serem roubados: \n";
		for(Item item : comodo.getItens()){
			texto += item + "\n";
		}
		return texto + "\n";
	}
	
	private String getEsconderijoComodo(Comodo comodo){
		return (comodo.getEsconderijo()) ? "esconder\n" : "Nao possui local para esconder!\n";
	}
	
	private String getSaidaComodo(Comodo comodo){
		return (comodo.getSaida()) ? "fugir\n" : "Nao possui saida para fugir!\n";
	}
	
	private void preMoveDonos(Casa casa){
		for(Dono dono : casa.getDonos()){
			dono.selecionaProximoComodo();
		}
	}
	
	private void moveDonos(Casa casa){
		for(Dono dono : casa.getDonos()){
			dono.movimentar(dono.getProximoComodo());
		}
	}
	
	private Casa getFase(int faseAtual){
		if(faseAtual < this.listaFases.size()){
			return this.listaFases.get(faseAtual);
		} return null;
	}
	
	private ArrayList<Tier> criaTierItens(){
		
		ArrayList<Tier> auxListaTier = new ArrayList<Tier>();
		
		// Itens
		// Itens comuns
		Item item1 = new Item("Anel de plástico", 10);
		Item item2 = new Item("Colhar de plástico", 10);
		Item item3 = new Item("Pulseira da amizadade", 5);
		Item item4 = new Item("Bracelete de plástico", 15);
		Item item5 = new Item("Pregador de cabelo de plástico", 5);
		
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
	
	private ArrayList<Casa> criaFases(){
		// Casa - 1
		Casa casa1 = new Casa("Casa de Paulo", "Paulo é um jovem que mora sozinho.", 1)
		
		//Comodos
		Comodo corredor1 = new Comodo("Corredor-1", this.listaTier.get(0), true, false);
		Comodo cozinha = new Comodo("Cozinha", this.listaTier.get(0), false, true);
		Comodo garagem = new Comodo("Garagem", this.listaTier.get(2), true, true);
		Comodo sala = new Comodo("Sala", this.listaTier.get(1), false, true);
		Comodo corredor2 = new Comodo("Corredor-2", this.listaTier.get(0), false, false);
		Comodo quarto1 = new Comodo("Quarto-1", this.listaTier.get(3), false, true);
		Comodo quarto2 = new Comodo("Quarto-2", this.listaTier.get(1), false, true);
		Comodo banheiro1 = new Comodo("Banheiro-1", this.listaTier.get(2), false, false);
		Comodo quarto3 = new Comodo("Quarto-3", this.listaTier.get(2), false, true);
		Comodo banheiro2 = new Comodo("Banheiro-2", this.listaTier.get(3), false, false);
		
		//Portas
		Porta porta1 = new Porta(corredor1, cozinha, false);
		Porta porta2 = new Porta(corredor1, sala, false);
		Porta porta3 = new Porta(cozinha, garagem, true);
		Porta porta4 = new Porta(cozinha, corredor2, false);
		Porta porta5 = new Porta(sala, corredor2, false);
		Porta porta6 = new Porta(corredor2, quarto1, true);
		Porta porta7 = new Porta(corredor2, quarto2, false);
		Porta porta8 = new Porta(corredor2, banheiro1, true);
		Porta porta9 = new Porta(corredor2, quarto3, false);
		Porta porta10 = new Porta(banheiro2, quarto3, false);
	
		// Criando a casa
		casa1.adicionaComodo(corredor1);
		casa1.adicionaComodo(cozinha);
		casa1.adicionaComodo(garagem);
		casa1.adicionaComodo(sala);
		casa1.adicionaComodo(corredor2);
		casa1.adicionaComodo(quarto1);
		casa1.adicionaComodo(quarto2);
		casa1.adicionaComodo(banheiro1);
		casa1.adicionaComodo(quarto3);
		casa1.adicionaComodo(banheiro2);
		
		// Seleciona comodo inicial do ladrão
		casa1.setComodoInicial(corredor1);
		
		// Adiciona os Donos
		casa1.adicionaDonos(1);
		
	}
	
}