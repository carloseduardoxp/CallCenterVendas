package br.com.callCenterVendas.model.domain;

import util.ValidacaoException;

public class Cliente extends Pessoa {
	
	private Double percentualDesconto;
	
	private String cpf;
	
	public Cliente(Integer codigo, String email, String nome, Double percentualDesconto, String cpf) {
		super(codigo, email, nome);
		if (percentualDesconto == null) {
			this.percentualDesconto = 0d;
		} else {
			this.percentualDesconto = percentualDesconto;	
		}		
		this.cpf = cpf;
	}
	
	public Cliente() {
		this.percentualDesconto = 0d;
	}

	public void valida() throws ValidacaoException {
		super.valida();
		if (cpf == null || cpf.equals("")) {
			throw new ValidacaoException("O campo cpf eh obrigatorio");
		}
	}

	public Double getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(Double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}	
	

}
