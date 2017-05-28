package br.com.callCenterVendas.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.callCenterVendas.model.dao.ClienteDao;
import br.com.callCenterVendas.model.domain.Cliente;
import util.ValidacaoException;

/**
 * Servlet implementation class ClienteServlet
 */
@WebServlet("/clienteServlet")
public class ClienteServlet extends HttpServlet {

	private ClienteDao clienteDao = new ClienteDao();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClienteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String codigo = request.getParameter("codigo");
		try {
			if (acao != null && acao.equals("excluir")) {
				Integer codCliente = Integer.parseInt(codigo);
				clienteDao.excluir(codCliente);
				request.setAttribute("mensagem", "Cliente excluido");
			} else if (acao != null && acao.equals("editar")) {
				Integer codCliente = Integer.parseInt(codigo);
				Cliente cliente = clienteDao.getClienteId(codCliente);
				request.setAttribute("cliente",cliente);
			}
			request.setAttribute("clientes", clienteDao.getClientes());
		} catch (SQLException e) {
			request.setAttribute("mensagem", "Erro de Banco de Dados: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			request.setAttribute("mensagem", "Erro de Driver: " + e.getMessage());
		} catch (ValidacaoException e) {
			request.setAttribute("mensagem", "Erro de Driver: " + e.getMessage());
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/paginas/clientes.jsp");
		dispatcher.forward(request, response);

		// PrintWriter pw = response.getWriter();
		// pw.write("<html><body>"+clientees.toString()+"</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String percentualDesconto = request.getParameter("percentualDesconto");
		String email = request.getParameter("email");
		String cpf = request.getParameter("cpf");
		String codigo = request.getParameter("codigo");
		Double desconto = 0d;
		if (percentualDesconto != null && !percentualDesconto.equals("")) {
			desconto = Double.parseDouble(percentualDesconto);
		}
		Cliente cliente = new Cliente(null, email, nome, desconto, cpf);
		if (codigo != null && !codigo.equals("")) {
			cliente.setCodigo(Integer.parseInt(codigo));
		}
		try {
			cliente.valida();
			if (cliente.getCodigo() != null) {
				clienteDao.atualizar(cliente);
				request.setAttribute("mensagem", "Cliente atualizado com sucesso");				
			} else {
				clienteDao.salvar(cliente);
				request.setAttribute("mensagem", "Cliente salvo com sucesso");
			}						
		} catch (ValidacaoException e) {
			request.setAttribute("mensagem", "Erro de Validacao dos Campos: " + e.getMessage());
			request.setAttribute("cliente",cliente);
		} catch (SQLException e) {
			request.setAttribute("mensagem", "Erro de Banco de Dados: " + e.getMessage());
			request.setAttribute("cliente",cliente);
		} catch (ClassNotFoundException e) {
			request.setAttribute("mensagem", "Erro de Driver: " + e.getMessage());
			request.setAttribute("cliente",cliente);
		} 
		try {
			request.setAttribute("clientes", clienteDao.getClientes());
		} catch (SQLException e) {
			request.setAttribute("mensagem", "Erro de Banco de Dados: " + e.getMessage());
			request.setAttribute("cliente",cliente);
		} catch (ClassNotFoundException e) {
			request.setAttribute("mensagem", "Erro de Driver: " + e.getMessage());
			request.setAttribute("cliente",cliente);
		} 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/paginas/clientes.jsp");
		dispatcher.forward(request, response);
	}

}
