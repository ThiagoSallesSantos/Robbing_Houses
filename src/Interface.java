import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * A classe Interface tem como objetivo criar a interface do jogo onde o usuário irá interagir
 * Fornece uma janela que será composta por seis labels contendo o mapa da casa; informações
 * sobre os cômodos, o ladrão, o jogador e a casa, além do terminal. A janela também possuirá um botão
 * que garante o envio do input dos comandos dos usuários.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */

public class Interface {
    private JFrame janela;
	private JLabel informacoesComodos;
	private JLabel informacoesLadrao;
	private JLabel informacoesJogador;
	private JLabel mapaCasa;
	private JLabel informacoesCasa;
	private JLabel terminal;
	private JTextArea terminalInput;

/**
 * Construtor da classe Interface.
 * @param jogo atributo do tipo Jogo passado por parâmetro para montar a interface do jogo a partir de suas informações
 */

    public Interface(Jogo jogo){
        montarJanela(jogo);
    }

/**
 * Método para montar a janela.
 * @param jogo atributo do tipo Jogo passado por parâmetro para montar a interface do jogo a partir de suas informações
 */
    private void montarJanela(Jogo jogo){
		
        // Configuração janela geral
        this.janela = new JFrame("Robbing Houses");
		this.janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.janela.setSize(new Dimension(1200, 600));
        this.janela.setLayout(new BorderLayout());
	
        // Configuração painel esquerdo
        JPanel painelEsquerda = new JPanel();
        painelEsquerda.setLayout(new BoxLayout(painelEsquerda, BoxLayout.Y_AXIS));
        painelEsquerda.setPreferredSize(new Dimension(280, 450));

        this.informacoesComodos = new JLabel();
        this.informacoesComodos.setFont(new Font("Ariel", Font.PLAIN, 10));
        painelEsquerda.add(this.informacoesComodos);

        this.janela.add(painelEsquerda, BorderLayout.WEST);

        // Configuração painel direito
		JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));
        painelDireita.setPreferredSize(new Dimension(280, 450));

		this.informacoesJogador = new JLabel();
		this.informacoesJogador.setFont(new Font("Ariel", Font.PLAIN, 10));
		painelDireita.add(this.informacoesJogador);

		this.informacoesLadrao = new JLabel();
		this.informacoesLadrao.setFont(new Font("Ariel", Font.PLAIN, 10));
		painelDireita.add(this.informacoesLadrao);

        this.janela.add(painelDireita, BorderLayout.EAST);

        // Configuração painel central
		JPanel painelCentral = new JPanel();
        painelCentral.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setPreferredSize(new Dimension(460, 450));

        this.mapaCasa = new JLabel(new ImageIcon(jogo.getImagemCasaAtual()));
        painelCentral.add(this.mapaCasa);

        this.informacoesCasa = new JLabel();
        this.informacoesCasa.setFont(new Font("Ariel", Font.PLAIN, 10));
        painelCentral.add(this.informacoesCasa);

        this.janela.add(painelCentral, BorderLayout.CENTER);

        // Configuração painel terminal
		JPanel painelTerminal = new JPanel();
        painelTerminal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelTerminal.setLayout(new BoxLayout(painelTerminal, BoxLayout.Y_AXIS));
        painelTerminal.setPreferredSize(new Dimension(1200, 150));

        this.terminal = new JLabel();
        this.terminal.setFont(new Font("Ariel", Font.BOLD, 8));
        this.terminal.setPreferredSize(new Dimension(1200, 120));
		this.terminal.setMaximumSize(new Dimension(400, 120));
		this.terminal.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        painelTerminal.add(this.terminal);

		// Configuração painel terminal input e botão
		JPanel terminalInputEBotao = new JPanel();
        terminalInputEBotao.setLayout(new BoxLayout(terminalInputEBotao, BoxLayout.X_AXIS));
        terminalInputEBotao.setPreferredSize(new Dimension(1200, 30));

        this.terminalInput = new JTextArea();
        this.terminalInput.setPreferredSize(new Dimension(800, 30));
        terminalInputEBotao.add(this.terminalInput);

        JButton botao = new JButton("Enviar");
        botao.setPreferredSize(new Dimension(200, 30));
        terminalInputEBotao.add(botao);

		botao.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String input = terminalInput.getText();
					terminalInput.setText("");
					String comandoStr = null;
					String atributo = null;
					Scanner entrada = new Scanner(input);
					if(entrada.hasNext()){
						comandoStr = entrada.next();
					}
					if(entrada.hasNext()){
						atributo = entrada.next();
					}
					Comando comando = new Comando(comandoStr, atributo);
					Jogo jogo = Jogo.getInstanciaJogo();
					jogo.executaComando(comando);
					entrada.close();
				}
			});

        painelTerminal.add(terminalInputEBotao);

        this.janela.add(painelTerminal, BorderLayout.SOUTH);
		
		this.janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.atualizaDados(jogo);
    }

/**
 * Método que atualiza dados.
 * Este método é responsável por atualizar os dados do jogo, incluindo cômodos, jogador, ladrão, casa, mapa e terminal.
 * @param jogo atributo do tipo Jogo em que será atualizado.
 */	
	public void atualizaDados(Jogo jogo){
		if(jogo.getStatus()){
			this.atualizaInfoComodos(jogo);
			this.atualizaInfoJogador(jogo);
			this.atualizaInfoLadrao(jogo);
			this.atualizaInfoCasa(jogo);
			this.atualizaMapa(jogo);
		}
		this.atualizaInfoTerminal(jogo);
	}

/**
 * Método de limpar a janela.
 * Este método é responsável por limpar a janela, adicionando uma String vazia.
 */
	public void limpaJanela(){
		this.informacoesComodos.setText("");
		this.informacoesJogador.setText("");
		this.informacoesLadrao.setText("");
		this.informacoesCasa.setText("");
		this.mapaCasa.setIcon(new ImageIcon());
		this.terminal.setText("");
	}

	/**
	 * Método para exibir informação
	 * Este método é responsável por exibir as informações da casa.
	 * @param info este atributo do tipo String contém a informação pertinente a ser exibida na label central.
	 */
	public void exibirInformacao(String info){
		this.informacoesCasa.setText(info);
	}
	
	/**
	 * Método para atualizar as informações do JLabel do informacoesComodos.
	 * Este método é responsável por atualizar as informações do comodo atual em que o ladrão se encontra, informando ao jogador sobre itens, comodos adjacentes, sobre esconderijo ou rota de fuga.
	 * @param jogo objeto do tipo Jogo, para que possa pegar as informações atualizadas do estado atual do jogo.
	 */
	private void atualizaInfoComodos(Jogo jogo){
		this.informacoesComodos.setText("<html><strong>---INFORMACOES COMODO---</strong><br/>" + jogo.getComodoAtualLadrao() + "</html>");
	}
	
	/**
	 * Método para atualizar as informações do JLabel do informacoesJogador.
	 * Este método é responsável por atualizar as informações do jogador, para informar ao jogador, sobre os atributos dele na jogatina.
	 * @param jogo objeto do tipo Jogo, para que possa pegar as informações atualizadas do estado atual do jogo.
	 */
	private void atualizaInfoJogador(Jogo jogo){
		this.informacoesJogador.setText("<html><strong>---INFORMACOES JOGADOR---</strong><br/>" + jogo.getInfoJogador() + "<br/></html>");
	}

	/**
	 * Método para atualizar as informações do JLabel do informacoesLadrao.
	 * Este método é responsável por atualizar as informações do ladrão, para informar ao jogador, sobre os atributos do ladrão e os itens roubados pelo mesmo.
	 * @param jogo objeto do tipo Jogo, para que possa pegar as informações atualizadas do estado atual do jogo.
	 */
	private void atualizaInfoLadrao(Jogo jogo){
		this.informacoesLadrao.setText("<html><strong>---INFORMACOES LADRAO---</strong><br/>" + jogo.getLadrao() + "</html>");
	}

	/**
	 * Método para atualizar as informações do JLabel do informacoesCasa.
	 * Este método é responsável por atualizar as informações da casa, para informar ao jogador, as posições atuais dos Donos além de nome e descrição da casa atual.
	 * @param jogo objeto do tipo Jogo, para que possa pegar as informações atualizadas do estado atual do jogo.
	 */
	private void atualizaInfoCasa(Jogo jogo){
		this.informacoesCasa.setText("<html><strong>---INFORMACOES CASA---</strong><br/>" + jogo.getCasaAtual() + "</html>");
	}
	
	/**
	 * Método para atualizar as informações do JLabel de mapaCasa.
	 * Este método é responsável por atualizar o mapa, para informar ao jogador o mapa atual da casa em que o jogador se encontra.
	 * @param jogo objeto do tipo Jogo, para que possa pegar as informações atualizadas do estado atual do jogo.
	 */
	private void atualizaMapa(Jogo jogo){
		this.mapaCasa.setIcon(new ImageIcon(jogo.getImagemCasaAtual()));
	}
	
	/**
	 * Método para atualizar as informações do JLabel do terminal.
	 * Este método é responsável por atualizar os comandos, para informar ao jogador os comandos permitidos no comodo atual.
	 * @param jogo objeto do tipo Jogo, para que possa pegar as informações atualizadas do estado atual do jogo.
	 */
	private void atualizaInfoTerminal(Jogo jogo){
		this.terminal.setText("<html><strong>---COMANDOS---</strong><br/>" + jogo.getComandosDisp() + "</html>");
	}
	
	/**
	 * Método de enviar mensagem.
	 * Este método é responsável por enviar as mensagens ao jogador, através de um pop-up.
	 * @param mensagem este atributo recebe um parâmetro que contém a mensagem a ser exibida ao jogador.
	 */
	public void enviaMensagem(String mensagem){
		JOptionPane.showMessageDialog(this.janela, mensagem);
	}
	
	/**
	 * Método de exibir janela.
	 * Este método é responsável por exibir a janela. Em setVisible recebe-se um valor booleano.
	 */
    public void exibir(){
        this.janela.setVisible(true);
    }
	
}
