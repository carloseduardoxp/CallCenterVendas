package br.com.callCenterVendas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.callCenterVendas.model.domain.Cliente;
import util.ValidacaoException;

public class ClienteDao {
	
	public List<Cliente> getClientes() throws SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement ps = conexao.prepareStatement("SELECT CD_CLIENTE,NM_CLIENTE,"
				+ "DS_EMAIL,DS_CPF,PC_DESCONTO FROM TB_CLIENTE");
		ResultSet rs = ps.executeQuery();
		List<Cliente> clientes = new ArrayList<>();
		while(rs.next()) {
			clientes.add(new Cliente(rs.getInt(1),rs.getString(3),
					rs.getString(2),rs.getDouble(5),rs.getString(4)));
		}
		return clientes;
	}

	public void salvar(Cliente cliente) throws SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement statement = conexao.prepareStatement(
				"INSERT INTO TB_CLIENTE(NM_CLIENTE,PC_DESCONTO,DS_CPF,"
				+ "DS_EMAIL) VALUES (?,?,?,?)");
		statement.setString(1,cliente.getNome());
		statement.setDouble(2,cliente.getPercentualDesconto());
		statement.setString(3,cliente.getCpf());
		statement.setString(4,cliente.getEmail());
		statement.execute();
	}

	public void excluir(Integer codCliente) throws SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement statement = conexao.prepareStatement(
				"DELETE FROM TB_CLIENTE WHERE CD_CLIENTE = ?");
		statement.setInt(1,codCliente);
		statement.execute();		
	}

	public Cliente getClienteId(Integer codCliente) throws ValidacaoException,SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement ps = conexao.prepareStatement("SELECT CD_CLIENTE,NM_CLIENTE,"
				+ "DS_EMAIL,DS_CPF,PC_DESCONTO FROM TB_CLIENTE");
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return new Cliente(rs.getInt(1),rs.getString(3),
					rs.getString(2),rs.getDouble(5),rs.getString(4));
		}
		throw new ValidacaoException("Nao achou cliente para o codigo "+codCliente);
	}

	public void atualizar(Cliente cliente) throws ValidacaoException,SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement statement = conexao.prepareStatement(
				"UPDATE TB_CLIENTE SET NM_CLIENTE =?,PC_DESCONTO = ?,DS_CPF = ?,"
				+ "DS_EMAIL = ? WHERE CD_CLIENTE = ?");
		statement.setString(1,cliente.getNome());
		statement.setDouble(2,cliente.getPercentualDesconto());
		statement.setString(3,cliente.getCpf());
		statement.setString(4,cliente.getEmail());
		statement.setInt(5,cliente.getCodigo());
		statement.execute();
	}

}
