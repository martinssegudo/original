package br.com.controlePessoa.util.validadores;

import br.com.controlePessoa.entidades.Pessoa;
import br.com.controlePessoa.exceptions.ValidacaoException;

public class PessoaValidator {
	
	public static void validaPessoa(Pessoa pessoa) throws ValidacaoException {
		if(pessoa.getNome() == null 
				|| pessoa.getNome().isEmpty()) {
			throw new ValidacaoException("O campo nome não esta preenchido");
		}
		if(pessoa.getCpf() == null 
				|| pessoa.getCpf().isEmpty()) {
			throw new ValidacaoException("O campo cpf não esta preenchido");
		}
	}
}
