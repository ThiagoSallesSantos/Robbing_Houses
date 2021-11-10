import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A classe Interface tem como objetivo criar a interface do jogo onde o usuário irá interagir
 * Fornece uma janela que será composta por seis labels contendo o mapa da casa; informações
 * sobre os cômodos, o ladrão, o jogador e a casa, além do terminal. A janela também possuirá um botão
 * que garante o envio do input dos comandos dos usuários.
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
	private JButton botao;

/**
 * Construtor da classe Interface.
 * @param jogo atributo do tipo Jogo passado por parâmetro para montar a interface do jogo a partir de suas informações
 */

    public Interface(Jogo jogo){
        montarJanela(jogo);
    }

/**
 * Método para montar a janela do jogo.
 * Neste método, cria-se uma janela de dimensões 1200x600 px. Nela, será criada um painel esquerdo
 * de dimensões 280x450 em que serão inseridas as informações dos cômodos. Também será criado um painel
 * direito de mesma dimensão contendo as informações do jogador e do ladrão. Já o painel central conterá
 * o mapa da casa e suas informações. E por fim, é criado um terminal de dimensões 1200x150 onde conterá
 * os comandos disponíveis, além de possuir um campo para o imput do comando e um botão de enviar.
 * @param jogo atributo do tipo Jogo passado por parâmetro para montar a interface do jogo  a partir de suas informações
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

        this.informacoesComodos = new JLabel("<html><strong>---INFORMACOES COMODO---</strong><br/>" + jogo.getComodoAtualLadrao() + "</html>");
        this.informacoesComodos.setFont(new Font("Ariel", Font.PLAIN, 10));
        painelEsquerda.add(this.informacoesComodos);

        this.janela.add(painelEsquerda, BorderLayout.WEST);

        // Configuração painel direito
		JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));
        painelDireita.setPreferredSize(new Dimension(280, 450));

		this.informacoesJogador = new JLabel("<html><strong>---INFORMACOES JOGADOR---</strong><br/>" + jogo.getInfoJogador() + "<br/></html>");
		this.informacoesJogador.setFont(new Font("Ariel", Font.PLAIN, 10));
		painelDireita.add(this.informacoesJogador);

		this.informacoesLadrao = new JLabel("<html><strong>---INFORMACOES LADRAO---</strong><br/>" + jogo.getLadrao() + "</html>");
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

        this.informacoesCasa = new JLabel("<html><strong>---INFORMACOES CASA---</strong><br/>" + jogo.getCasaAtual() + "</html>");
        this.informacoesCasa.setFont(new Font("Ariel", Font.PLAIN, 10));
        painelCentral.add(this.informacoesCasa);

        this.janela.add(painelCentral, BorderLayout.CENTER);

        // Configuração painel terminal
		JPanel painelTerminal = new JPanel();
        painelTerminal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelTerminal.setLayout(new BoxLayout(painelTerminal, BoxLayout.Y_AXIS));
        painelTerminal.setPreferredSize(new Dimension(1200, 150));

        this.terminal = new JLabel("<html><strong>---COMANDOS---</strong><br/>" + jogo.getComandosDisp() + "</html>");
        this.terminal.setFont(new Font("Ariel", Font.BOLD, 10));
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

        this.botao = new JButton("Enviar");
        this.botao.setPreferredSize(new Dimension(200, 30));
        terminalInputEBotao.add(this.botao);

		this.botao.addActionListener(
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
				}
			});

        painelTerminal.add(terminalInputEBotao);

        this.janela.add(painelTerminal, BorderLayout.SOUTH);
    }

/**
 * Método que atualiza dados.
 * Este método é responsável por atualizar os dados do jogo, incluindo cômodos, jogador, ladrão, casa, mapa e terminal.
 * @param jogo atributo do tipo Jogo em que será atualizado.
 */	
	public void atualizaDados(Jogo jogo){
		this.atualizaInfoComodos(jogo);
		this.atualizaInfoJogador(jogo);
		this.atualizaInfoLadrao(jogo);
		this.atualizaInfoCasa(jogo);
		this.atualizaMapa(jogo);
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
		this.mapaCasa.setText("");
		this.terminal.setText("");
	}

	/**
	 * Método para exibir informação
	 * Este método é responsável por exibir as informações da casa.
	 * @param info este atributo do tipo String contém a informação da casa a ser exibida.
	 */
	public void exibirInformacao(String info){
		this.informacoesCasa.setText(info);
	}

	/**
	 * Método para atualizar informação dos cômodos
	 * Este método é responsável por setar o cômodo que o jogador está no momento.
	 * @param jogo este atributo do tipo Jogo passado por parâmetro possui um método que retorna o cômodo atual do ladrão.
	 */
	
	private void atualizaInfoComodos(Jogo jogo){
		this.informacoesComodos.setText("<html><strong>---INFORMACOES COMODO---</strong><br/>" + jogo.getComodoAtualLadrao() + "</html>");
	}

	/**
	 * Método para atualizar informações dos jogadores
	 * Este método é responsável por setar as informações do jogadore, ou seja, a quantidade de vidas restantes.
	 * @param jogo este atributo do tipo Jogo passado por parâmetro possui um método que retorna as informações do jogador.
	 */
	private void atualizaInfoJogador(Jogo jogo){
		this.informacoesJogador.setText("<html><strong>---INFORMACOES JOGADOR---</strong><br/>" + jogo.getInfoJogador() + "<br/></html>");
	}
	/**
	 * Método para atualizar as informações do ladrão
	 * Este método é responsável por setar as informações do ladrão, ou seja, a quantidade de itens já roubados e a quantidade de chaves-mestre que ele possui.
	 * @param jogo este atributo do tipo Jogo passado por parâmetro possui um método que retorna as informações do ladrão.
	 */

	private void atualizaInfoLadrao(Jogo jogo){
		this.informacoesLadrao.setText("<html><strong>---INFORMACOES LADRAO---</strong><br/>" + jogo.getLadrao() + "</html>");
	}
	/**
	 *Método que atualiza a informação da casa.
	 Este método é responsável por setar as informações da casa contendo nome, descrição e a lista de donos.
	 * @param jogo este atributo do tipo Jogo passado por parâmetro possui um método que retorna a casa atual.
	 */
	private void atualizaInfoCasa(Jogo jogo){
		this.informacoesCasa.setText("<html><strong>---INFORMACOES CASA---</strong><br/>" + jogo.getCasaAtual() + "</html>");
	}
	
	/**
	 * Método de atualizar o mapa.
	 * Este método é responsável por trocar e atualizar o mapa da casa.
	 * @param jogo este atributo do tipo Jogo passado por parâmetro possui um método para pegar a imagem da casa atual.
	 */
	private void atualizaMapa(Jogo jogo){
		this.mapaCasa.setIcon(new ImageIcon(jogo.getImagemCasaAtual()));
	}
	
	/**
	 * Método de atualizar informações do terminal 
	 * Este método é responsável por setar as informações atualizadas dos comandos disponíveis para o jogador.
	 * @param jogo este atributo do tipo Jogo passado por parâmetro será utilizado para setar as informações dos comandos disponíveis.
	 */
	private void atualizaInfoTerminal(Jogo jogo){
		this.terminal.setText("<html><strong>---COMANDOS---</strong><br/>" + jogo.getComandosDisp() + "</html>");
	}
	
	/**
	 * Método de enviar mensagem.
	 * Este método é responsável por enviar as mensagens escritas pelo jogador.
	 * @param mensagem este atributo recebe um parâmetro que contém a mensagem escrita pelo jogador.
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
