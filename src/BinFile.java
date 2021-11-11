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
	
	private static String nomeArquivo = "SBsave.bin";
	
/**
 * Método responsável por salvar a pontuação do jogador no arquivo.
 * @param sb objeto do tipo ScoreBoard que será gravado no arquivo SBsave.bin.
 */
	public static void saveSBFile(ScoreBoard sb){
    	try(ObjectOutputStream arq = new ObjectOutputStream(new FileOutputStream(nomeArquivo))){
	    	arq.writeObject(sb);
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}catch(Exception e){
			System.out.println("Erro: " + e.getMessage());
		}
    }
/**
 * Método responsável por ler a pontuação do jogador no arquivo.
 * @return retorna um objeto do tipo ScoreBoard contendo a pontuação do jogador.
 */	
	public static ScoreBoard loadSBFile(){
    	ScoreBoard sb = new ScoreBoard();
    	try(ObjectInputStream arq = new ObjectInputStream(new FileInputStream(nomeArquivo))){
	    	sb = (ScoreBoard)arq.readObject();
    	}catch(FileNotFoundException e){
    		System.out.println(e.getMessage());
    	}catch(IOException e){
    		System.out.println(e.getMessage());
    	}catch (ClassNotFoundException e){
    		System.out.println(e.getMessage());
		}
		return sb;
    }
}
