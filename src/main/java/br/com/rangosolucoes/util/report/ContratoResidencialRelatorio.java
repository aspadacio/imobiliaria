package br.com.rangosolucoes.util.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * Classe responsável por gerar o Contrato do tipo Residencial com os campos preenchidos corretamente.
 * 
 * */
public class ContratoResidencialRelatorio implements Work{

	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	
	private boolean relatorioGerado;
	
	/**
	 * Método responsável por preparar corretamente todos os dados necessários para geração do relatório.
	 * @param caminhoRelatorio em qual path está o .jasper No nosso caso, todos os relatórios ficarão no dir src/main/resources/relatorios/
	 * @param parametros todos os parâmetros Map<key, value> que estarão previamente mapeados no .jasper
	 * @param nomeArquivoSaida nome dado ao arquivo gerado
	 * */
	public ContratoResidencialRelatorio(String caminhoRelatorio, HttpServletResponse response, Map<String, Object> parametros,
			String nomeArquivoSaida) {
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
		
		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		try {
			InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);
			
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, connection);
			this.relatorioGerado = print.getPages().size() > 0;
			
			if(this.relatorioGerado){
				JRExporter exportador = new JRPdfExporter();
				exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);
				
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=\""
					+ this.nomeArquivoSaida + "\"");
				
				exportador.exportReport();
			}
		} catch (Exception e) {
			throw new SQLException("Erro ao executar relatório " + this.caminhoRelatorio, e);
		}
	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}

}