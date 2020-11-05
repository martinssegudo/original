package br.com.controlePessoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "br.com.controlePessoa")
public class ControlePessoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlePessoaApplication.class, args);
	}

}
