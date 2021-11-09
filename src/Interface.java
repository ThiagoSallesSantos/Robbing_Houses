import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    private JFrame janela;
	private JPanel painelEsquerda;
	private JPanel painelDireita;
	private JPanel painelCentral;
	private JPanel painelTerminal;
	private JPanel terminalInputEBotao;
	private JLabel informacoesComodos;
	private JLabel informacoesLadrao;
	private JLabel informacoesJogador;
	private JLabel mapaCasa;
	private JLabel informacoesCasa;
	private JLabel terminal;
	private JTextArea terminalInput;
	private JButton botao;

    public Interface(){
        montarJanela();
    }
	
    private void montarJanela(){
		
		Jogo jogo = Jogo.getInstanciaJogo();
		
        // Configuração janela geral
        this.janela = new JFrame("Robbing Houses");
        this.janela.setSize(1000, 500);
        this.janela.setLayout(new BorderLayout());
	
        // Configuração painel esquerdo
        this.painelEsquerda = new JPanel();
        this.painelEsquerda.setLayout(new BoxLayout(this.painelEsquerda, BoxLayout.Y_AXIS));
        this.painelEsquerda.setPreferredSize(new Dimension(280, 350));

        this.informacoesComodos = new JLabel("<html><strong>---INFORMACOES COMODO---</strong><br/>" + jogo.getComodoAtualLadrao() + "</html>");
        this.informacoesComodos.setFont(new Font("Ariel", Font.PLAIN, 10));
        this.painelEsquerda.add(this.informacoesComodos);

        this.janela.add(this.painelEsquerda, BorderLayout.WEST);

        // Configuração painel direito
        this.painelDireita = new JPanel();
        this.painelDireita.setLayout(new BoxLayout(this.painelDireita, BoxLayout.Y_AXIS));
        this.painelDireita.setPreferredSize(new Dimension(280, 350));

		this.informacoesJogador = new JLabel("<html><strong>---INFORMACOES JOGADOR---</strong><br/>" + jogo.getInfoJogador() + "<br/></html>");
		this.informacoesJogador.setFont(new Font("Ariel", Font.PLAIN, 10));
		this.painelDireita.add(this.informacoesJogador);

		this.informacoesLadrao = new JLabel("<html><strong>---INFORMACOES LADRAO---</strong><br/>" + jogo.getLadrao() + "</html>");
		this.informacoesLadrao.setFont(new Font("Ariel", Font.PLAIN, 10));
		this.painelDireita.add(this.informacoesLadrao);

        this.janela.add(this.painelDireita, BorderLayout.EAST);

        // Configuração painel central
        this.painelCentral = new JPanel();
        this.painelCentral.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        this.painelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.painelCentral.setLayout(new BoxLayout(this.painelCentral, BoxLayout.Y_AXIS));
        this.painelCentral.setPreferredSize(new Dimension(460, 350));

        // this.mapaCasa = new JLabel(new ImageIcon(jogo.getImagemCasaAtual()));
        // this.painelCentral.add(this.mapaCasa);

        this.informacoesCasa = new JLabel("<html><strong>---INFORMACOES CASA---</strong><br/>" + jogo.getCasaAtual() + "</html>");
        this.informacoesCasa.setFont(new Font("Ariel", Font.PLAIN, 10));
        this.painelCentral.add(this.informacoesCasa);

        this.janela.add(this.painelCentral, BorderLayout.CENTER);

        // Configuração painel terminal
		this.painelTerminal = new JPanel();
        this.painelTerminal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        this.painelTerminal.setLayout(new BoxLayout(this.painelTerminal, BoxLayout.Y_AXIS));
        this.painelTerminal.setPreferredSize(new Dimension(1000, 150));

        this.terminal = new JLabel("<html><strong>---COMANDOS---</strong><br/>" + jogo.getComandosDisp() + "</html>");
        this.terminal.setFont(new Font("Ariel", Font.BOLD, 12));
        this.terminal.setPreferredSize(new Dimension(1000, 120));
        this.painelTerminal.add(this.terminal);

		// Configuração painel terminal input e botão
        this.terminalInputEBotao = new JPanel();
        this.terminalInputEBotao.setLayout(new BoxLayout(this.terminalInputEBotao, BoxLayout.X_AXIS));
        this.terminalInputEBotao.setPreferredSize(new Dimension(1000, 30));

        this.terminalInput = new JTextArea();
        this.terminalInput.setPreferredSize(new Dimension(800, 30));
        this.terminalInputEBotao.add(this.terminalInput);

        this.botao = new JButton("Enviar");
        this.botao.setPreferredSize(new Dimension(200, 30));
        this.terminalInputEBotao.add(this.botao);

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

        this.painelTerminal.add(this.terminalInputEBotao);

        this.janela.add(this.painelTerminal, BorderLayout.SOUTH);
    }

	public void atualizaDados(){
		Jogo jogo = Jogo.getInstanciaJogo();
		this.atualizaInfoComodos(jogo);
		this.atualizaInfoJogador(jogo);
		this.atualizaInfoLadrao(jogo);
		this.atualizaInfoCasa(jogo);
		this.atualizaInfoTerminal(jogo);
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
	
	private void atualizaInfoTerminal(Jogo jogo){
		this.terminal.setText("<html><strong>---COMANDOS---</strong><br/>" + jogo.getComandosDisp() + "</html>");
	}
	
	public void janelaMensagem(String mensagem){
		JOptionPane.showMessageDialog(this.janela, mensagem);
	}
	
    public void exibir(){
        this.janela.setVisible(true);
    }

    public void fechar(){
        this.janela.setVisible(false);
    }
	
}
