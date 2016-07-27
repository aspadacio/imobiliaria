package br.com.rangosolucoes.repository.filter;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que apresenta todos os parâmetros possíveis de filtro para buscar o locatário.
 * 
 * */
public class LocatarioFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter private String nome;
	@Getter @Setter private String cpf;
	@Getter @Setter private String cnpj;
	@Getter @Setter private String municipio;
	@Getter @Setter private String uf;

}
