import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

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

    public Interface(Jogo jogo){
        montarJanela(jogo);
    }
	
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

	public void atualizaDados(Jogo jogo){
		this.atualizaInfoComodos(jogo);
		this.atualizaInfoJogador(jogo);
		this.atualizaInfoLadrao(jogo);
		this.atualizaInfoCasa(jogo);
		this.atualizaMapa(jogo);
		this.atualizaInfoTerminal(jogo);
	}

	public void limpaJanela(){
		this.informacoesComodos.setText("");
		this.informacoesJogador.setText("");
		this.informacoesLadrao.setText("");
		this.informacoesCasa.setText("");
		this.mapaCasa.setText("");
		this.terminal.setText("");
	}

	public void exibirInformacao(String info){
		this.informacoesCasa.setText(info);
	}

	private void atualizaInfoComodos(Jogo jogo){
		this.informacoesComodos.setText("<html><strong>---INFORMACOES COMODO---</strong><br/>" + jogo.getComodoAtualLadrao() + "</html>");
	}

	private void atualizaInfoJogador(Jogo jogo){
		this.informacoesJogador.setText("<html><strong>---INFORMACOES JOGADOR---</strong><br/>" + jogo.getInfoJogador() + "<br/></html>");
	}
	
	private void atualizaInfoLadrao(Jogo jogo){
		this.informacoesLadrao.setText("<html><strong>---INFORMACOES LADRAO---</strong><br/>" + jogo.getLadrao() + "</html>");
	}

	private void atualizaInfoCasa(Jogo jogo){
		this.informacoesCasa.setText("<html><strong>---INFORMACOES CASA---</strong><br/>" + jogo.getCasaAtual() + "</html>");
	}
	
	private void atualizaMapa(Jogo jogo){
		this.mapaCasa.setText("");
	}
	
	private void atualizaInfoTerminal(Jogo jogo){
		this.terminal.setText("<html><strong>---COMANDOS---</strong><br/>" + jogo.getComandosDisp() + "</html>");
	}
	
	public void enviaMensagem(String mensagem){
		JOptionPane.showMessageDialog(this.janela, mensagem);
	}
	
    public void exibir(){
        this.janela.setVisible(true);
    }
	
}
