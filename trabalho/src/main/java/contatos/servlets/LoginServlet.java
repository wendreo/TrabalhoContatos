package contatos.servlets;

import java.io.*;
import contatos.servlets.Mensagens;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/login")

public class LoginServlet extends HttpServlet 
{
	private static final long	serialVersionUID = 1L;
	Mensagens mem = new Mensagens();
	
	@Override
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		PrintWriter writer = resp.getWriter();
		
		String nomeLogin = req.getParameter("login");
		String senhaLogin = req.getParameter("senha");
		
		
		if( nomeLogin == "" || senhaLogin == "" )
		{
			
			writer.write(mem.mensagemLoginInvalido());			
		}
		else
		{		
				
			FileInputStream entrada = new FileInputStream("C:/Users/aluno.POLILAB/Projetos/trabalho/src/main/webapp/login.txt");
			InputStreamReader entradaFormatada = new InputStreamReader(entrada);
			BufferedReader entradaString = new BufferedReader(entradaFormatada);
		    
			boolean bool = false;
			String linha;
			
			do
			{
				linha = entradaString.readLine();
				if( linha.contains(nomeLogin) )
				{
					if( linha.contains(senhaLogin) )
						
					{
						HttpSession session = req.getSession();
						
						session.setAttribute("usuario", nomeLogin);
						writer.write(mem.mostraContatos( nomeLogin ));
						entrada.close();
						bool = true;
						break;
					}
					writer.write(mem.mensagemLoginInvalido());
					bool = true;
					break;
				}
			}
			
			while(linha != null);
			
			if( !bool )
				writer.write(mem.mensagemLoginInvalido());
		}
	}
}