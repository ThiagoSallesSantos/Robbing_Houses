import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Interface {
    private JFrame janela;
    private JLabel terminal;
    private JTextArea terminalInput;
    private JLabel tentativasRestantes;
    private JLabel mapa;
    private JLabel durabilidadeChaveMestre;
    private JLabel portaAtual;
    private JPanel dicasEncontradasPanel;
    private JPanel panelCentral;
    private Jogo jogo;

    public Interface(){
        this.jogo = new Jogo();
		this.jogo.iniciaFase();
        montarJanela();
    }
	
    private void montarJanela(){
		
        // Configuração janela geral
        janela = new JFrame("Robbing Houses");
        janela.setSize(1000, 500);
        janela.setLayout(new BorderLayout());
	
        // Configuração painel esquerdo
        JPanel panelEsquerda = new JPanel();
        panelEsquerda.setLayout(new BoxLayout(panelEsquerda, BoxLayout.Y_AXIS));
        panelEsquerda.setPreferredSize(new Dimension(280, 350));

        JLabel informacoesComodos = new JLabel("<html><b>---INFORMACOES COMODO---</b><br/>" + this.jogo.getComodoAtualLadrao() + "</html>");
        informacoesComodos.setFont(new Font("Ariel", Font.PLAIN, 10));
        panelEsquerda.add(informacoesComodos);

        janela.add(panelEsquerda, BorderLayout.WEST);

        // Configuração painel direito
        JPanel panelDireita = new JPanel();
        panelDireita.setLayout(new BoxLayout(panelDireita, BoxLayout.Y_AXIS));
        panelDireita.setPreferredSize(new Dimension(280, 350));

		JLabel informacoesJogador = new JLabel("<html><b>---INFORMACOES JOGADOR---</b><br/>" + this.jogo.getInfoJogador() + "<br/></html>");
		informacoesJogador.setFont(new Font("Ariel", Font.PLAIN, 10));
		panelDireita.add(informacoesJogador);

		JLabel informacoesLadrao = new JLabel("<html><b>---INFORMACOES LADRAO---</b><br/>" + this.jogo.getLadrao() + "</html>");
		informacoesLadrao.setFont(new Font("Ariel", Font.PLAIN, 10));
		panelDireita.add(informacoesLadrao);

        janela.add(panelDireita, BorderLayout.EAST);

        // Configuração painel central
        panelCentral = new JPanel();
        panelCentral.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setPreferredSize(new Dimension(460, 350));

        mapa = new JLabel(new ImageIcon(this.jogo.getImagemCasaAtual()));
        panelCentral.add(mapa);

        JLabel informacoesCasa = new JLabel("<html><b>---INFORMACOES CASA---</b><br/>" + this.jogo.getCasaAtual() + "</html>");
        informacoesCasa.setFont(new Font("Ariel", Font.PLAIN, 10));
        panelCentral.add(informacoesCasa);

        janela.add(panelCentral, BorderLayout.CENTER);

        // Configuração painel terminal
        JPanel panelTerminal = new JPanel();
        panelTerminal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTerminal.setLayout(new BoxLayout(panelTerminal, BoxLayout.Y_AXIS));
        panelTerminal.setPreferredSize(new Dimension(1000, 150));

        terminal = new JLabel();
        terminal.setFont(new Font("Ariel", Font.BOLD, 17));
        terminal.setPreferredSize(new Dimension(1000, 120));
        panelTerminal.add(terminal);

        JPanel terminalInputEBotao = new JPanel();
        terminalInputEBotao.setLayout(new BoxLayout(terminalInputEBotao, BoxLayout.X_AXIS));
        terminalInputEBotao.setPreferredSize(new Dimension(1000, 30));

        terminalInput = new JTextArea();
        terminalInput.setPreferredSize(new Dimension(800, 30));
        terminalInputEBotao.add(terminalInput);

        EventTerminal eventTerminal = new EventTerminal();

        JButton botao = new JButton("OK");
        botao.addActionListener(eventTerminal);
        botao.setPreferredSize(new Dimension(200, 30));
        terminalInputEBotao.add(botao);

        panelTerminal.add(terminalInputEBotao);

        janela.add(panelTerminal, BorderLayout.SOUTH);
    }

    public void updateTentativasRestantes(String tentativasRestantesValue){
        tentativasRestantes.setText(tentativasRestantesValue);
    }

    public void updateDurabilidadeChaveMestre(String durabilidadeChaveMestreValue){
        durabilidadeChaveMestre.setText(durabilidadeChaveMestreValue);
    }

    public void updateDicasEncontradas(ArrayList<String> dicasEncontradasPanelValues){
        dicasEncontradasPanel.removeAll();
        for (String dicaEncontrada: dicasEncontradasPanelValues) {
            dicasEncontradasPanel.add(new JLabel("-> " + dicaEncontrada));
        }
        terminalInput.setText("");
    }

    public void updatePortaAtual(String novaPortaAtual){
        portaAtual.setText("Porta atual: " + novaPortaAtual);
    }

    public void updateMapa(String imagemMapaPath){
        panelCentral.removeAll();

        mapa = new JLabel(new ImageIcon(imagemMapaPath));
        panelCentral.add(mapa);
        panelCentral.add(portaAtual);

        terminalInput.setText("");
    }

    public void updateTerminal(String texto){
        terminal.setText(texto);
    }

    public void exibir(){
        janela.setVisible(true);
    }

    public void fechar(){
        janela.setVisible(false);
    }

    private class EventTerminal implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            terminalInput.setText("");
        }
    }
}
