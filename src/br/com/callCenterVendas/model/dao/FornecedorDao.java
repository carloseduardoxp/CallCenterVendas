package br.com.callCenterVendas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.callCenterVendas.model.domain.Fornecedor;
import util.ValidacaoException;

public class FornecedorDao {
	
	public List<Fornecedor> getFornecedores() throws SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement ps = conexao.prepareStatement("SELECT CD_FORNECEDOR,NM_FORNECEDOR,"
				+ "DS_RAZAO_SOCIAL,DS_EMAIL,DS_CNPJ FROM TB_FORNECEDOR");
		ResultSet rs = ps.executeQuery();
		List<Fornecedor> fornecedores = new ArrayList<>();
		while(rs.next()) {
			fornecedores.add(new Fornecedor(rs.getInt(1),rs.getString(4),
					rs.getString(2),rs.getString(3),rs.getString(5)));
		}
		return fornecedores;
	}

	public void salvar(Fornecedor fornecedor) throws SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement statement = conexao.prepareStatement(
				"INSERT INTO TB_FORNECEDOR(NM_FORNECEDOR,DS_RAZAO_SOCIAL,DS_CNPJ,"
				+ "DS_EMAIL) VALUES (?,?,?,?)");
		statement.setString(1,fornecedor.getNome());
		statement.setString(2,fornecedor.getRazaoSocial());
		statement.setString(3,fornecedor.getCnpj());
		statement.setString(4,fornecedor.getEmail());
		statement.execute();
	}

	public void excluir(Integer codFornecedor) throws SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement statement = conexao.prepareStatement(
				"DELETE FROM TB_FORNECEDOR WHERE CD_FORNECEDOR = ?");
		statement.setInt(1,codFornecedor);
		statement.execute();		
	}

	public Fornecedor getFornecedorId(Integer codFornecedor) throws ValidacaoException,SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement ps = conexao.prepareStatement("SELECT CD_FORNECEDOR,NM_FORNECEDOR,"
				+ "DS_RAZAO_SOCIAL,DS_EMAIL,DS_CNPJ FROM TB_FORNECEDOR WHERE CD_FORNECEDOR = ?");
		ps.setInt(1, codFornecedor);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return new Fornecedor(rs.getInt(1),rs.getString(4),
					rs.getString(2),rs.getString(3),rs.getString(5));
		}
		throw new ValidacaoException("Nao achou fornecedor para o codigo "+codFornecedor);
	}

	public void atualizar(Fornecedor fornecedor) throws ValidacaoException,SQLException,ClassNotFoundException {
		Connection conexao = ConexaoJDBCFactory.getConexao();
		PreparedStatement statement = conexao.prepareStatement(
				"UPDATE TB_FORNECEDOR SET NM_FORNECEDOR =?,DS_RAZAO_SOCIAL = ?,DS_CNPJ = ?,"
				+ "DS_EMAIL = ? WHERE CD_FORNECEDOR = ?");
		statement.setString(1,fornecedor.getNome());
		statement.setString(2,fornecedor.getRazaoSocial());
		statement.setString(3,fornecedor.getCnpj());
		statement.setString(4,fornecedor.getEmail());
		statement.setInt(5,fornecedor.getCodigo());
		statement.execute();
	}

}
