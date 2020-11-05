package br.com.controlePessoa.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.controlePessoa.entidades.Pessoa;
import br.com.controlePessoa.exceptions.ValidacaoException;
import br.com.controlePessoa.servicos.ServicoPessoa;
import io.restassured.http.ContentType;

@WebMvcTest
class ControlePessoaTestUnit {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	

	@Autowired
	private ControladorPessoa controladorPessoa;
	
	@MockBean
	private ServicoPessoa servicoPessoa;
	
	@BeforeEach
	public void setupControladorTestado() {
		standaloneSetup(this.controladorPessoa);
	}
	
	@Test
	public void retornaSucesso_quandoCriandoUmaPessoa() throws JsonProcessingException {
		Pessoa pessoa = new Pessoa("Luiz Segundo", "00000000000");		
		
		try {
			when(this.servicoPessoa.salvar(pessoa))
				.thenReturn(new Pessoa(1L,"Luiz Segundo", "00000000000"));
		} catch (ValidacaoException e) {
			e.printStackTrace();
		}
		
		given()	    
	    .contentType("application/json")
	    .body(pessoa)
	    .post("/pessoa")
	    .then()
	    .statusCode(HttpStatus.CREATED.value()).log().all();
			
	}
	
	@Test
	public void retornaFalha_quandoCriandoUmaPessoa() throws JsonProcessingException {
		Pessoa pessoa = new Pessoa("Luiz Segundo", "00000000000");		
		
		try {
			when(this.servicoPessoa.salvar(pessoa))
				.thenReturn(null);
		} catch (ValidacaoException e) {
			e.printStackTrace();
		}
		
		given()	    
	    .contentType("application/json")
	    .body(pessoa)
	    .post("/pessoa")
	    .then()
	    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).log().all();
			
	}
	
	@Test
	public void retornaSucesso_quandoAlteraUmaPessoa() throws JsonProcessingException {
		Pessoa pessoa = new Pessoa(1L,"Luiz Segundo", "00000000001");		
		
		try {
			when(this.servicoPessoa.alterar(pessoa))
				.thenReturn(new Pessoa(1L,"Luiz Segundo", "00000000001"));
		} catch (ValidacaoException e) {
			e.printStackTrace();
		}
		
		given()	    
	    .contentType("application/json")
	    .body(pessoa)
	    .put("/pessoa")
	    .then()
	    .statusCode(HttpStatus.ACCEPTED.value()).log().all();
			
	}
	
	@Test
	public void retornaFalha_quandoAlteraUmaPessoa() throws JsonProcessingException {
		Pessoa pessoa = new Pessoa(1L,"Luiz Segundo", "00000000001");		
		
		try {
			when(this.servicoPessoa.alterar(pessoa))
				.thenReturn(null);
		} catch (ValidacaoException e) {
			e.printStackTrace();
		}
		
		given()	    
	    .contentType("application/json")
	    .body(pessoa)
	    .put("/pessoa")
	    .then()
	    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).log().all();
			
	}
	
	
	@Test
	public void retornaSucesso_quandoSolicatadoPessoaPorID() {
		Pessoa pessoa = new Pessoa("Luiz Segundo", "00000000000");		
		
		when(this.servicoPessoa.pesqusiarPessoaPorId(1L))
			.thenReturn(new Pessoa(1L,"Luiz Segundo", "00000000000"));
				
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/pessoa/{id}", 1L)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void retornaFalha_quandoSolicatadoPessoaPorID() {		
		Pessoa pessoa = new Pessoa("Luiz Segundo", "00000000000");		
		
		when(this.servicoPessoa.pesqusiarPessoaPorId(1L))
			.thenReturn(null);
				
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/pessoa/{id}", 1L)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}	
	
	@Test
	public void retornaSucesso_quandoSolicatadoUmaLista() {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		pessoas.add(new Pessoa(1L,"Luiz Segundo", "00000000000"));
		pessoas.add(new Pessoa(2L,"Isis Muryelle", "00000000001"));
		pessoas.add(new Pessoa(2L,"Maria Rozilda", "00000000003"));
		
		when(this.servicoPessoa.listarTodas())
			.thenReturn(pessoas);
				
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/pessoa/list/all")
		.then()
			.statusCode(HttpStatus.OK.value());
	}	
	
	@Test
	public void retornaSucesso_quandoDeletaUmaPessoa() {
		
		when(this.servicoPessoa.pesqusiarPessoaPorId(1L))
		.thenReturn(new Pessoa(1L,"Luiz Segundo", "00000000000"));
		
		when(this.servicoPessoa.deletar(1L))
			.thenReturn(true);
				
		given()
			.accept(ContentType.JSON)
		.when()
			.delete("/pessoa/delete/{id}", 1L)
		.then()
			.statusCode(HttpStatus.OK.value());
	}

}
