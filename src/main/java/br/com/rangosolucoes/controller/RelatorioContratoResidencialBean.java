package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.rangosolucoes.util.jsf.FacesUtil;
import br.com.rangosolucoes.util.report.ContratoResidencialRelatorio;

/**
 * Classe responsável por gerar relatório do tipo Contrato Residencial
 * */
@Named("relatorioContratoResidencialBean")
public class RelatorioContratoResidencialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> parametros;

	@Inject
	private EntityManager manager;

	public void emitir() {
		parametros = new HashMap<>();
		
		parametros.put("contrato_numero", "123456879");
		parametros.put("locador_cnpj", "21.012.412/0001-11");
		parametros.put("locador_endereco_completo", "quadra 201 lote C1 sala 06 A 09 comércio local Santa Maria Sul Brasília DF");
		parametros.put("locador_razao_social", "IMOBILIARIA TAVARES LTDA-EPP");
		parametros.put("locatario_cpf", "036.751.351-01");
		parametros.put("locatario_endereco_completo", "QUADRA 8 LOTE 19 APARTAMENTO 103 SETOR OESTE GAMA-DF");
		parametros.put("locatario_estado_civil", "casado");
		parametros.put("locatario_nacionalidade", "brasileiro");
		parametros.put("locatario_nome", "PHYLLIPE DE ACYPRESTE CARVALHO ");
		parametros.put("locatario_profissao", "auxiliar de manutenção");
		parametros.put("locatario_rg", "2714393");
		parametros.put("imovel_endereco_completo", "QUADRA 8 LOTE 19 APARTAMENTO 103 SETOR OESTE GAMA-DF");
		parametros.put("contrato_duracao", "12");
		parametros.put("contrato_inicio", "01 de fevereiro de 2016");
		parametros.put("contrato_fim", "01 de fevereiro de 2017");
		
		ContratoResidencialRelatorio executor = new ContratoResidencialRelatorio("/relatorios/contratoResidencialMain.jasper",
			(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()
			, parametros, "Contrato Residencial.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if(executor.isRelatorioGerado()){
			FacesContext.getCurrentInstance().responseComplete();
		}else{
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}
}