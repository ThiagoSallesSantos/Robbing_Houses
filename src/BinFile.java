import java.io.*;

public class BinFile {
	
	private static String nomeArquivo = "SBsave.bin";
	
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
