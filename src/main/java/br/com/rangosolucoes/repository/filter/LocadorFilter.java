package br.com.rangosolucoes.repository.filter;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que apresenta todos os parâmetros possíveis de filtro para buscar o locatário.
 * 
 * */
public class LocadorFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter private String nomeObs; //representa o conteúdo dentro de tbPessoa.dsObservaca
	@Getter @Setter private String cnpj;
	@Getter @Setter private String municipio;
	@Getter @Setter private String uf;

}
