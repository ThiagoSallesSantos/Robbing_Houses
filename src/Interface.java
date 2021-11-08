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
    private Casa casaAtual;
    private Jogador jogador;

    public Interface(Jogo jogo){
        this.jogo = jogo;
        this.jogador = jogo.getJogador();
        this.casaAtual = jogo.getCasaAtual();

        montarJanela();
        inicializarFase();
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

        JLabel tentativasRestantesLabel = new JLabel("Número de Tentativas Restantes:");
        tentativasRestantesLabel.setFont(new Font("Ariel", Font.PLAIN, 17));
        panelEsquerda.add(tentativasRestantesLabel);

        tentativasRestantes = new JLabel("0");
        tentativasRestantes.setFont(new Font("Ariel", Font.PLAIN, 17));
        panelEsquerda.add(tentativasRestantes);

        JLabel durabilidadeChaveMestreLabel = new JLabel("Durabilidade da chave mestra:");
        durabilidadeChaveMestreLabel.setFont(new Font("Ariel", Font.PLAIN, 17));
        panelEsquerda.add(durabilidadeChaveMestreLabel);

        durabilidadeChaveMestre = new JLabel("0");
        durabilidadeChaveMestre.setFont(new Font("Ariel", Font.PLAIN, 17));
        panelEsquerda.add(durabilidadeChaveMestre);

        janela.add(panelEsquerda, BorderLayout.WEST);

        // Configuração painel direito
        JPanel panelDireita = new JPanel();
        panelDireita.setLayout(new BoxLayout(panelDireita, BoxLayout.Y_AXIS));
        panelDireita.setPreferredSize(new Dimension(280, 350));

//        JLabel dicasEncontradasLabel = new JLabel("Dicas encontradas:");
//        dicasEncontradasLabel.setFont(new Font("Ariel", Font.PLAIN, 17));
//        panelDireita.add(dicasEncontradasLabel);
//
//        dicasEncontradasPanel = new JPanel();
//        dicasEncontradasPanel.setLayout(new BoxLayout(dicasEncontradasPanel, BoxLayout.Y_AXIS));
//        dicasEncontradasPanel.setPreferredSize(new Dimension(280, 340));
//        panelDireita.add(dicasEncontradasPanel);


        janela.add(panelDireita, BorderLayout.EAST);

        // Configuração painel central
        panelCentral = new JPanel();
        panelCentral.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setPreferredSize(new Dimension(460, 350));

        mapa = new JLabel(new ImageIcon("src/Casa_2.png"));
        panelCentral.add(mapa);

        portaAtual = new JLabel("Porta atual:");
        portaAtual.setFont(new Font("Ariel", Font.PLAIN, 17));
        panelCentral.add(portaAtual);

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

    public void inicializarFase(){

        this.casaAtual = jogo.getCasaAtual();
    }

    public void executaComando(String input){
        this.jogo.executaComandoInterface(input);
        if((this.jogador.getVidasRestantes() > 0) && (this.casaAtual != null)){

        }
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
            String input = terminalInput.getText();
            executaComando(input);
            terminalInput.setText("");
        }
    }
}
