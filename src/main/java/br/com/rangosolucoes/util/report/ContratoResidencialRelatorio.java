package br.com.rangosolucoes.util.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.hibernate.jdbc.Work;

/**
 * Classe responsável por gerar o Contrato do tipo Residencial com os campos preenchidos corretamente.
 * @see por conta do tipo de relatório trabalhado aqui, contrato, foi alterada esta classe para receber mas de um .jasper
 * */
public class ContratoResidencialRelatorio implements Work{

	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	private List<String> caminhosRelatorios; //Receberá os caminhos de todosos .jaspers para ser feito o merge e gerar apenas um PDF
	
	private boolean relatorioGerado;
	
	/**
	 * Método responsável por preparar corretamente todos os dados necessários para geração do relatório.
	 * @param caminhosRelatorios em qual path está os .jasper No nosso caso, todos os relatórios ficarão no dir src/main/resources/relatorios/
	 * @param parametros todos os parâmetros Map<key, value> que estarão previamente mapeados no .jasper
	 * @param nomeArquivoSaida nome dado ao arquivo gerado
	 * */
	public ContratoResidencialRelatorio(List<String> caminhosRelatorios, HttpServletResponse response, Map<String, Object> parametros,
			String nomeArquivoSaida) {
		this.caminhosRelatorios = caminhosRelatorios;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
		
		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		try {
			//InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);
			List<InputStream> relatoriosStream = new ArrayList<InputStream>();
			
			//Criando os InputStream
			for(int i=0; i<this.caminhosRelatorios.size(); i++){
				relatoriosStream.add(this.getClass().getResourceAsStream(this.caminhosRelatorios.get(i)));
			}
			
			//Criando a lista de JasperPrint
			List<JasperPrint> prints = new ArrayList<JasperPrint>();
			for(int i=0; i<relatoriosStream.size(); i++){
				prints.add(JasperFillManager.fillReport(relatoriosStream.get(i), parametros, connection));
			}
					
			/*JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, connection);
			this.relatorioGerado = print.getPages().size() > 0;*/
			
			this.relatorioGerado = prints.size() > 0;
			
			if(this.relatorioGerado){
				JRExporter exportador = new JRPdfExporter();
				exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				exportador.setParameter(JRExporterParameter.JASPER_PRINT_LIST, prints);
				
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=\""
					+ this.nomeArquivoSaida + "\"");
				
				exportador.exportReport();
			}
		} catch (Exception e) {
			throw new SQLException("ContratoResidencialRelatorio :: Erro ao executar relatório ", e);
		}
	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}

}
