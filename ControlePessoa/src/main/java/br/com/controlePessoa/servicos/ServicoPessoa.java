package br.com.controlePessoa.servicos;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.controlePessoa.entidades.Pessoa;
import br.com.controlePessoa.exceptions.ValidacaoException;
import br.com.controlePessoa.repositorios.IPessoaRepositorio;
import br.com.controlePessoa.util.validadores.PessoaValidator;

@Service
@Transactional
public class ServicoPessoa {
	
	private IPessoaRepositorio repositorioPessoa;
	
	@Autowired
	public ServicoPessoa(IPessoaRepositorio repositorioPessoa) {
		this.repositorioPessoa = repositorioPessoa;
	}
	
	
	public Pessoa salvar(Pessoa pessoa) throws ValidacaoException {
		PessoaValidator.validaPessoa(pessoa);
		return repositorioPessoa.save(pessoa);
		
	}
	
	public Pessoa alterar(Pessoa pessoa) throws ValidacaoException {
		Pessoa pessoaBanco = repositorioPessoa.getOne(pessoa.getId());
		pessoaBanco.setCpf(pessoa.getCpf());
		pessoaBanco.setNome(pessoa.getNome());		
		PessoaValidator.validaPessoa(pessoaBanco);
		return repositorioPessoa.save(pessoaBanco);
	}
	
	
	public Pessoa pesqusiarPessoaPorId(Long id) {
		return repositorioPessoa.getOne(id);
	}
	
	public List<Pessoa> listarTodas(){
		return repositorioPessoa.findAll();
	}
	
	
	public boolean deletar(Long id) {
		Pessoa pessoaDeletada = pesqusiarPessoaPorId(id);
		if(pessoaDeletada == null) {
			return false;
		}
		repositorioPessoa.deleteById(id);
		return true;
	}
}
