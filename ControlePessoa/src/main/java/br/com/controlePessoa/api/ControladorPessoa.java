package br.com.controlePessoa.api;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.controlePessoa.entidades.Pessoa;
import br.com.controlePessoa.exceptions.ValidacaoException;
import br.com.controlePessoa.servicos.ServicoPessoa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/pessoa")
@CrossOrigin(origins = "*")
@Api(value="API REST Pessoas!")
public class ControladorPessoa {
	
	private ServicoPessoa servicoPessoa;
	
	@Autowired
	public ControladorPessoa(ServicoPessoa servicoPessoa) {
		this.servicoPessoa = servicoPessoa;
	}
	
	@ApiOperation(value="Grava uma nova pessoa na base")
	@PostMapping
	public ResponseEntity<Serializable> salvar(@RequestBody Pessoa pessoa) {
		try {
			pessoa = servicoPessoa.salvar(pessoa);
		} catch (ValidacaoException validacaoPessoa) {
			validacaoPessoa.printStackTrace();
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR).body(validacaoPessoa.getMessage());
		} 
		if(pessoa == null) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Pessoa não cadastrada");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}
	
	@ApiOperation(value="Altera uma nova pessoa na base")
	@PutMapping
	public ResponseEntity<Serializable> alterar(@RequestBody Pessoa pessoa) {
		try {
			pessoa = servicoPessoa.alterar(pessoa);
		} catch (ValidacaoException validacaoPessoa) {			
			validacaoPessoa.printStackTrace();
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR).body(validacaoPessoa.getMessage());
		}
		if(pessoa == null) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Pessoa não Alterada");
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(pessoa);
	}
	
	
	@ApiOperation(value="Pesquisa uma pessoa pelo id")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Serializable> salvar(@PathVariable(value="id") Long id) {
		Pessoa pessoaRetorno = servicoPessoa.pesqusiarPessoaPorId(id);
		if(pessoaRetorno == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pessoaRetorno);
	}
	
	@ApiOperation(value="Retorna a lista de pessoas cadastradas até o momento")
	@GetMapping(path = "/list/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Serializable> listarTodasAsPessoa() {
		ArrayList<Pessoa> listaPessoas = (ArrayList<Pessoa>) servicoPessoa.listarTodas();
		if(listaPessoas == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(listaPessoas);
	}
	
	
	@ApiOperation(value="Deleta uma pessoa pelo id")
	@DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Serializable> deletaPessoaPorID(@PathVariable(value="id") Long id) {		
		if(!servicoPessoa.deletar(id)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(true);
	}
	
	
}
