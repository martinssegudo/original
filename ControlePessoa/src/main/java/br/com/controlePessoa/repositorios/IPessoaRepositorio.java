package br.com.controlePessoa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.controlePessoa.entidades.Pessoa;

public interface IPessoaRepositorio extends JpaRepository<Pessoa, Long>{

}
