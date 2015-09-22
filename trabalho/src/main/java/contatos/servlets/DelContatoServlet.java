package contatos.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/excluiContato")
public class DelContatoServlet extends HttpServlet

{
	private static final long	serialVersionUID = 1L;
	Mensagens men = new Mensagens();
	
	@Override
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		PrintWriter writer = resp.getWriter();
		
		String[] id = req.getParameterValues("contato");
		
		
		if( id == null )
		{
			writer.write(men.mostraContatos( (String)req.getSession().getAttribute("usuario") ));
		}
		else
		{					    
			excluirContatos(id);
			writer.write(men.mostraContatos( (String)req.getSession().getAttribute("usuario") ));
		}
	}
	
	private void excluirContatos( String[] id ) throws IOException
	{
		File entrada = new File("C:/Users/aluno.POLILAB/Projetos/trabalho/src/main/webapp/contato.txt");
		FileReader entradaFormatada = new FileReader(entrada);
		BufferedReader entradaString = new BufferedReader(entradaFormatada);
		
		int i = 0;
		
		String linha = entradaString.readLine();
		ArrayList<String> arrayContatos = new ArrayList<String>();
		
		while(linha != null)
		{
			if( i < id.length && linha.contains( "id="+id[i]) ) 
				
			{	
				linha = entradaString.readLine();
				i++;
			}
			else
			{
				arrayContatos.add(linha); 
				linha = entradaString.readLine();
			}
		}
		
		entradaFormatada.close(); 
		entradaString.close(); 
		FileWriter fw = new FileWriter(entrada, true);
		fw.close();
		
		FileWriter fw2 = new FileWriter(entrada);
		BufferedWriter bf = new BufferedWriter(fw2);
		
		for ( i = 0; i < arrayContatos.size(); i++ ) 
		{
			bf.write( arrayContatos.get(i) );
			bf.newLine();
		}
		
		bf.close();
		fw2.close();
	}
}
