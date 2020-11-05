package br.com.controlePessoa.exceptions;

public class ValidacaoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024247248486696926L;
	
	private String mensagem;
	
	public ValidacaoException(String mensagem) {
		super(mensagem);
	}
	
	public ValidacaoException(Exception e, String mensagem) {
		super(mensagem, e.getCause());
	}
}
