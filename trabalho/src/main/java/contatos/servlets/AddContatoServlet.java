package contatos.servlets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/novoContato")
public class AddContatoServlet extends HttpServlet
{
	private static final long	serialVersionUID = 1L;
	Mensagens men = new Mensagens();
	
	@Override
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		
		String nomeContato = req.getParameter("nome");
		String numContato = req.getParameter("num");
		PrintWriter writer = resp.getWriter();
		
		if( nomeContato == "" || numContato == "" )
		{
			req.getRequestDispatcher("/addContato.html").forward(req, resp);
		}
		else
		{
		                  
			salvaContato( (String)req.getSession().getAttribute("usuario"), nomeContato, numContato );
			writer.write(men.mostraContatos( (String)req.getSession().getAttribute("usuario") ));
		}
	}
	
	private int contaLinhas() throws IOException
	{	
		int cont = 1;
		FileInputStream entrada = new FileInputStream("C:/Users/aluno.POLILAB/Projetos/trabalho/src/main/webapp/contato.txt");
		InputStreamReader entradaFormatada = new InputStreamReader(entrada);
		BufferedReader entradaString = new BufferedReader(entradaFormatada);
		String linha = entradaString.readLine();
		
		while( linha != null )
		{
			linha = entradaString.readLine();
			cont++;
		}
		
		return cont;
	}
	
	
	private void salvaContato( String nomeUser, String nomeContato, String numContato ) throws IOException
	{
		Path path = Paths.get( "C:/Users/aluno.POLILAB/Projetos/trabalho/src/main/webapp/contato.txt" );
		String conteudo = nomeUser+";"+nomeContato+";"+numContato+";id="+contaLinhas()+"\n";
		Files.write( path, conteudo.getBytes(), StandardOpenOption.APPEND );
	}
}
