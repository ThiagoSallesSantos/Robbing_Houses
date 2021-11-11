import java.io.*;

/**
 * A classe BinFile é responsável por escrever e ler a pontuação do jogador em um arquivo.
 * @version 1.0
 * @author Alvaro Barbosa de Paula
 * @author Ana Luiza Faria Calixto
 * @author Chrystian Arriel Amaral
 * @author Thiago Salles Santos
 */
public class BinFile {
	
	private static String nomeArquivoBin = "SBsave.bin"; // Nome do arquivo na qual a ScoreBoard será salva.
	private static String nomeArquivoTxt = "SBsave.txt"; // Nome do arquivo na qual a ScoreBoard será salva.
	
/**
 * Método responsável por salvar a pontuação do jogador no arquivo binário.
 * @param sb objeto do tipo ScoreBoard que será gravado no arquivo SBsave.bin.
 * @param jogo objeto do tipo Jogo, para que caso de erro possa ser exibido a informação ao usuário.
 */
	public static void saveSBFile(ScoreBoard sb, Jogo jogo){
    	try(ObjectOutputStream arq = new ObjectOutputStream(new FileOutputStream(nomeArquivoBin))){
	    	arq.writeObject(sb);
    	}
    	catch(IOException e){
    		jogo.enviaInformacaoPopUp("Erro - Nao foi possivel escrever no arquivo binário, devido ao um erro de entrada e saida!\nMais informacoes: " + e.getMessage());
    	}catch(Exception e){
			jogo.enviaInformacaoPopUp("Erro - Nao foi possivel escrever no arquivo binário, devido algum erro inesperado!\nMais informacoes: " + e.getMessage());
		}
    }
	
/**
 * Método responsável por ler a pontuação do jogador no arquivo binário.
 * @param jogo objeto do tipo Jogo, para que caso de erro possa ser exibido a informação ao usuário.
 * @return retorna um objeto do tipo ScoreBoard contendo a pontuação do jogador.
 */	
	public static ScoreBoard loadSBFile(Jogo jogo) {
    	ScoreBoard sb = new ScoreBoard();
    	try(ObjectInputStream arq = new ObjectInputStream(new FileInputStream(nomeArquivoBin))){
	    	sb = (ScoreBoard)arq.readObject();
    	}catch(FileNotFoundException e){
    		jogo.enviaInformacaoPopUp("Alerta - Nao foi possivel ler o arquivo binário, devido a arquivo na encontrado!\nMais informacoes: " + e.getMessage());
    	}catch(IOException e){
    		jogo.enviaInformacaoPopUp("Erro - Nao foi possivel ler o arquivo binário, devido ao um erro de entrada e saida!\nMais informacoes: " + e.getMessage());
    	}catch (ClassNotFoundException e){
    		jogo.enviaInformacaoPopUp("Erro - Nao foi possivel ler o arquivo binário, devido ao um erro de classe nao encontrada!\nMais informacoes: " + e.getMessage());
		}
		return sb;
    }
	
/**
 * Método responsável por escrever a pontuação do jogador no arquivo de texto.
 * @param sb objeto do tipo ScoreBoard que será gravado no arquivo SBsave.txt.
 * @param jogo objeto do tipo Jogo, para que caso de erro possa ser exibido a informação ao usuário.
 */	
	public static void saveSBFileTxt(ScoreBoard sb, Jogo jogo){
		try(FileWriter arq = new FileWriter(nomeArquivoTxt)){
			arq.write(String.format("%-10s %-20s %-10s%n", "Posicao", "Nome Jogador", "Pontuacao"));
			int cont = 1;
			for (Jogador jogador : sb.getJogadores()) {
				arq.write(String.format("%-10d %-20s %-10s%n", cont, jogador.getNome(), "R$" + jogador.getPontuacao()));
				cont += 1;
			}
        }catch(IOException e){
    		jogo.enviaInformacaoPopUp("Erro - Nao foi possivel escrever no arquivo de texto, devido ao um erro de entrada e saida!\nMais informacoes: " + e.getMessage());
    	}catch(Exception e){
			jogo.enviaInformacaoPopUp("Erro - Nao foi possivel escrever no arquivo de texto, devido algum erro inesperado!\nMais informacoes: " + e.getMessage());
		}
	}
 
}
