import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreBoard {
	private ArrayList<Jogador> lista;
	
	public ScoreBoard()
	{
		this.lista = new ArrayList<Jogador>();
	}
	
	public void addJogador(Jogador tmp)
	{
		lista.add(tmp);
	}
	
	public Integer getTamanho()
	{
		return lista.size();
	}
	
	public Integer getPontuacaoFinal(Jogador jgd)
	{
		return (jgd.getPontuacao() + (jgd.getPontuacao()*jgd.getVidasRestantes()/30));
	}
	
	public String getPontuacao(Integer pos)
	{
		Jogador jgd = lista.get(pos);
		return (pos+1) + "# - Jogador: " + jgd.getNome() + " - Pontuacao Final: " + getPontuacaoFinal(jgd); 
	}
	
	public void ordenarSB()
	{
		
		Collections.sort(lista , new Comparator<Jogador>() {
			@Override
			public int compare(Jogador j1, Jogador j2) {
				return getPontuacaoFinal(j2).compareTo(getPontuacaoFinal(j1));
			}
		});
	}
	
	public void listarSB()//para testar
	{
		for(int i=0; i<lista.size(); i++)
		{
			System.out.println(getPontuacao(i));
			
		}
	}
	
	public void salvarSB()
	{
		BinFile.saveSBFile(lista);
	}
	
	public void carregarSB()
	{
		lista = BinFile.loadSBFile();
	}
}
