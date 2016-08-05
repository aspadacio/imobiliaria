package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.rangosolucoes.util.jsf.FacesUtil;
import br.com.rangosolucoes.util.report.ContratoComercialRelatorio;

/**
 * Classe responsável por gerar relatório do tipo Contrato Comercial
 * */
@Named("relatorioContratoComercialBean")
public class RelatorioContratoComercialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> parametros;

	@Inject
	private EntityManager manager;

	public void emitir() {
		parametros = new HashMap<>();
		
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
		parametros.put("contrato_valor_aluguel", "R$ 2.300,00 (DOIS MIL E TREZENTOS REAIS)");
		parametros.put("contrato_dia_vencimento", "10 (DEZ)");
		parametros.put("contrato_desconto_pontualidade", "500,00 (QUINHENTOS REIAS)");
		parametros.put("cidade_data", "Gama, 01 de Fevereiro de 2016");
		
		List<String> filesPaths = new ArrayList<String>();
		filesPaths.add("/relatorios/contrato/comercial/contratoComercial1.jasper");
		filesPaths.add("/relatorios/contrato/comercial/contratoComercial2.jasper");
		filesPaths.add("/relatorios/contrato/comercial/contratoComercial3.jasper");
		filesPaths.add("/relatorios/contrato/comercial/contratoComercial4.jasper");
		
		ContratoComercialRelatorio executor = new ContratoComercialRelatorio(filesPaths,
			(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()
			, parametros, "Contrato Comercial.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if(executor.isRelatorioGerado()){
			FacesContext.getCurrentInstance().responseComplete();
		}else{
			FacesUtil.addErrorMessage("relatorioContratoComercialBean :: A execução do relatório não retornou dados.");
		}
	}
}
