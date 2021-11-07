import java.io.*;
import java.util.ArrayList;

public class BinTest {
	
	public static void saveSBFile(ArrayList<Jogador> lista) 
    {
    	String arquivoNome = "SBsave.bin";
    	try
    	{
	    	ObjectOutputStream arquivo = new ObjectOutputStream( new FileOutputStream(arquivoNome)); 
	    	arquivo.writeObject(lista);
	    	arquivo.close();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    }
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Jogador> loadSBFile()
    {
    	String arquivoNome = "SBsave.bin";
    	ArrayList<Jogador> lista = new ArrayList<Jogador>();
    	try
    	{
    		ObjectInputStream arquivo = new ObjectInputStream( new FileInputStream(arquivoNome)); 
	    	lista = (ArrayList<Jogador>)arquivo.readObject();
	    	arquivo.close();
    	}
    	catch(FileNotFoundException e)
    	{
    		System.out.println(e.getMessage());
    	}
    	catch(IOException e)
    	{
    		System.out.println(e.getMessage());
    	}
    	catch (ClassNotFoundException e) 
    	{
    		System.out.println(e.getMessage());
		}
		return lista;
    }
}
