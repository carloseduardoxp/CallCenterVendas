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

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// CRUD - CREATE RETRIEVE UPDATE DELETE
		try {
			String acao = request.getParameter("acao");
			if (acao != null) {
				if (acao.equals("CREATE")) {
					Cliente cliente = criaCliente(request);
					try {
						cliente.valida();
					} catch (ValidacaoException e) {
						request.setAttribute("mensagem", "Erro de Validacao dos Campos: " + e.getMessage());
						request.setAttribute("cliente", cliente);
					}
					if (cliente.getCodigo() == null) {
						clienteDao.salvar(cliente);
						request.setAttribute("mensagem", "Cliente salvo com sucesso");
					} else {
						clienteDao.atualizar(cliente);
						request.setAttribute("mensagem", "Cliente atualizado com sucesso");
					}
				} else if (acao.equals("RETRIEVE")) {
					String codigo = request.getParameter("codigo");
					Integer codCliente = Integer.parseInt(codigo);
					Cliente cliente = clienteDao.getClienteId(codCliente);
					request.setAttribute("cliente", cliente);
	
				} else if (acao.equals("DELETE")) {
					String codigo = request.getParameter("codigo");
					Integer codCliente = Integer.parseInt(codigo);
					clienteDao.excluir(codCliente);
					request.setAttribute("mensagem", "Cliente excluido");
				}
			}
			request.setAttribute("clientes", clienteDao.getClientes());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/paginas/clientes.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException | ClassNotFoundException | IllegalArgumentException e) {
			request.setAttribute("mensagem", "Erro: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/paginas/erro.jsp");
			dispatcher.forward(request, response);
		}
	}

	private Cliente criaCliente(HttpServletRequest request) {
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
		return cliente;
	}

}
