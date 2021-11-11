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
	
	private static String nomeArquivo = "SBsave.bin"; // Nome do arquivo na qual a ScoreBoard será salva.
	
/**
 * Método responsável por salvar a pontuação do jogador no arquivo.
 * @param sb objeto do tipo ScoreBoard que será gravado no arquivo SBsave.bin.
 * @param jogo objeto do tipo Jogo, para que caso de erro possa ser exibido a informação ao usuário.
 */
	public static void saveSBFile(ScoreBoard sb, Jogo jogo){
    	try(ObjectOutputStream arq = new ObjectOutputStream(new FileOutputStream(nomeArquivo))){
	    	arq.writeObject(sb);
    	}
    	catch(IOException e){
    		jogo.enviaInformacaoPopUp("Erro - Nao foi possivel escrever no arquivo, devido ao um erro de entrada e saida!\nMais informacoes: " + e.getMessage());
    	}catch(Exception e){
			jogo.enviaInformacaoPopUp("Erro - Nao foi possivel escrever no arquivo, devido algum erro inesperado!\nMais informacoes: " + e.getMessage());
		}
    }
/**
 * Método responsável por ler a pontuação do jogador no arquivo.
 * @param jogo objeto do tipo Jogo, para que caso de erro possa ser exibido a informação ao usuário.
 * @return retorna um objeto do tipo ScoreBoard contendo a pontuação do jogador.
 */	
	public static ScoreBoard loadSBFile(Jogo jogo) {
    	ScoreBoard sb = new ScoreBoard();
    	try(ObjectInputStream arq = new ObjectInputStream(new FileInputStream(nomeArquivo))){
	    	sb = (ScoreBoard)arq.readObject();
    	}catch(FileNotFoundException e){
    		jogo.enviaInformacaoPopUp("Alerta - Nao foi possivel ler o arquivo, devido a arquivo na encontrado!\nMais informacoes: " + e.getMessage());
    	}catch(IOException e){
    		jogo.enviaInformacaoPopUp("Erro - Nao foi possivel ler o arquivo, devido ao um erro de entrada e saida!\nMais informacoes: " + e.getMessage());
    	}catch (ClassNotFoundException e){
    		jogo.enviaInformacaoPopUp("Erro - Nao foi possivel ler o arquivo, devido ao um erro de classe nao encontrada!\nMais informacoes: " + e.getMessage());
		}
		return sb;
    }
}
