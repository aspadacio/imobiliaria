package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.hibernate.Session;

import br.com.rangosolucoes.model.TbContrato;
import br.com.rangosolucoes.model.TbContratoModificador;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.model.TbLocador;
import br.com.rangosolucoes.model.TbLocatario;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaFisica;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.service.ContratoModificadorService;
import br.com.rangosolucoes.service.ImovelService;
import br.com.rangosolucoes.service.LocadorService;
import br.com.rangosolucoes.service.LocatarioService;
import br.com.rangosolucoes.service.PessoaService;
import br.com.rangosolucoes.util.jsf.FacesUtil;
import br.com.rangosolucoes.util.report.ContratoComercialRelatorio;

/**
 * Classe responsável por gerar relatório do tipo Contrato Comercial
 * */
@Named("relatorioContratoComercialBean")
public class RelatorioContratoComercialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	//Objetos usados para transmissão de dados.
	private Map<String, Object> parametros;
	private TbContratoModificador contratoModificador;
	private TbPessoa locador;
	private TbPessoa locatario;
	private TbImovel imovel;
	
	//Atributos que serão usados para retornar dados das tabelas
	@Getter @Setter @NonNull private String locadorId; //ID_LOCADOR
	@Getter @Setter @NonNull private String locatarioId; //Poderá ser o número do CNPJ ou do CPF
	@Getter @Setter @NonNull private String imovelId; //id do imóvel

	@Inject
	private EntityManager manager;
	
	@Inject
	private LocadorService locadorService;
	
	@Inject
	private LocatarioService locatarioService;
	
	@Inject
	private ImovelService imovelService;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private ContratoModificadorService contratoModificadorService;

	public void emitir(TbContrato contrato) {
		String dataDia;
		String dataMes;
		String dataAno;
		
		parametros = new HashMap<>();
		
		//Preparando IDs
		locadorId = String.valueOf(contrato.getTbLocador().getIdLocador());
		locatarioId = String.valueOf(contrato.getTbLocatario().getIdLocatario());
		
		//--Buscar informações para serem inseridas no 'parametros'
		//Locador
		TbPessoaJuridica locadorPJ = null;
		TbLocador locadorAux = locadorService.locadorPorId(Long.parseLong(locadorId));
		
		locador = pessoaService.porId(locadorAux.getTbPessoa().getIdPessoa());
		locadorPJ = locadorService.findPessoaJuridicaById(locador.getTbPessoaJuridica().getNuCnpj());
		
		//Locatário
		TbPessoaFisica locatarioPF = null;
		TbLocatario locatarioAux = locatarioService.porId(Long.parseLong(locatarioId));
		
		locatario = pessoaService.porId(locatarioAux.getTbPessoa().getIdPessoa());
		locatarioPF = locatarioService.findPessoaFisicaById(locatario.getTbPessoaFisica().getNuCpf());
		//END--Buscar informações para serem inseridas no 'parametros'
		
		//--Preencher HashMap de parametros
		//Locador
		//Mask para CNPJ
		MaskFormatter maskCnpj;
		try {
			maskCnpj = new MaskFormatter("##.###.###/####-##");
			maskCnpj.setValueContainsLiteralCharacters(false);
			parametros.put("locador_cnpj", maskCnpj.valueToString(locadorPJ.getNuCnpj())); //"21.012.412/0001-11"
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Endereco -> "quadra 201 lote C1 sala 06 A 09 comércio local Santa Maria Sul Brasília DF"
		TbEnderecoPessoa locadorEndereco = null;
		
		locadorEndereco = locadorService.findEnderecoById(locador.getIdPessoa());
		
		//"quadra 201 lote C1 sala 06 A 09 comércio local Santa Maria Sul Brasília DF"
		parametros.put("locador_endereco_completo", locadorEndereco.getDsEndereco() + ", " //Rua, Avenida, Quadra, etc. 
				+ locadorEndereco.getNuEndereco() +  //Número
				(locadorEndereco.getDsComplemento() != "" ? ", " + locadorEndereco.getDsComplemento() : ", ") //Complemento
				+ ( locadorEndereco.getTbBairro().getNoBairro() != "" ? ", " + locadorEndereco.getTbBairro().getNoBairro() : ", " )  
				+ locadorEndereco.getTbMunicipio().getNoMunicipio() + " - " + locadorEndereco.getTbMunicipio().getSgUf()); //Cidade + UF
		parametros.put("locador_razao_social", locadorPJ.getNoRazaoSocial()); //"IMOBILIARIA TAVARES LTDA-EPP"		
		
		//Locatário
		try {
			MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
			maskCpf.setValueContainsLiteralCharacters(false);
			parametros.put("locatario_cpf", maskCpf.valueToString(locatarioPF.getNuCpf())); //"036.751.351-01"
		} catch (ParseException e) {
			e.getStackTrace();
		}
		try {
			MaskFormatter maskRg = new MaskFormatter("##.###.###-#");
			maskRg.setValueContainsLiteralCharacters(false);
			parametros.put("locatario_rg", maskRg.valueToString(locatarioPF.getNuRg()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		parametros.put("locatario_estado_civil", locatarioPF.getDsEstadoCivil());
		parametros.put("locatario_nacionalidade", locatarioPF.getDsNacionalidade());
		parametros.put("locatario_nome", locatarioPF.getNoPessoaFisica());
		parametros.put("locatario_profissao", locatarioPF.getDsProfissao());
		
		parametros.put("locatario_endereco_completo", "QUADRA 8 LOTE 19 APARTAMENTO 103 SETOR OESTE GAMA-DF");
		
		//Locatário -> Endereço : "QUADRA 8 LOTE 19 APARTAMENTO 103 SETOR OESTE GAMA-DF"
		TbEnderecoPessoa locatarioEndereco = null;
		
		try {
			locatarioEndereco = locatarioService.findEnderecosById(locatario.getIdPessoa()).get(0);			
		}catch(Exception e){
			FacesUtil.addErrorMessage("Não foi possível gerar o relatório. Não há Endereço do Locatário.");
			e.getStackTrace();
		}
		
		parametros.put("locatario_endereco_completo", locatarioEndereco.getDsEndereco() + ", "
				+ locatarioEndereco.getNuEndereco()
				+ (locatarioEndereco.getDsComplemento() != "" ? ", " + locatarioEndereco.getDsComplemento() : "")
				+ (locatarioEndereco.getTbBairro().getNoBairro() != "" ? ", " + locatarioEndereco.getTbBairro().getNoBairro() : "")
				+ (locatarioEndereco.getTbMunicipio().getNoMunicipio() != "" ? ", " + locatarioEndereco.getTbMunicipio().getNoMunicipio() : "") );
		

		//Contrato
		try{
			contratoModificador = contratoModificadorService.findByContratoId(contrato.getIdContrato());			
		}catch(Exception e){
			FacesUtil.addErrorMessage("Não foi possível gerar o relatório. Não há Contrato Modificador.");
			e.getStackTrace();
		}
		
		parametros.put("contrato_valor_aluguel", contratoModificador.getVlValor().toString()); //TB_CONTRATO_MODIFICADOR - VL_VALOR
		
		parametros.put("contrato_numero", contrato.getIdContrato().toString());
		
		dataDia = new SimpleDateFormat("dd").format(contrato.getDtInicio());
		dataMes = new SimpleDateFormat("MMMM").format(contrato.getDtInicio());
		dataAno = new SimpleDateFormat("yyyy").format(contrato.getDtInicio());
		
		parametros.put("contrato_inicio", dataDia + " de " + dataMes + " de " + dataAno); //"01 de fevereiro de 2016"
		parametros.put("contrato_duracao", String.valueOf(contrato.getNuDuracao()));
		parametros.put("contrato_dia_vencimento", String.valueOf(contrato.getNuDiaVencimento()));
		
		//Contrato -> Contrato Data Fim
		String dataFimDia = null, dataFimMes = null, dataFimAno = null;
		
		//menos de 1 ano
		if(contrato.getNuDuracao() < 12){
			dataFimDia = dataDia;
			dataFimMes = String.valueOf((Integer.parseInt(dataMes) + contrato.getNuDuracao()));
			dataFimAno = dataAno;
		}else{
			//mais que 1 ano 
			dataFimDia = dataDia;
			dataFimMes = dataMes;
			dataFimAno = String.valueOf(Integer.parseInt(dataAno) + contrato.getNuDuracao()); //engessado em 1 ano apenas
			//TODO fazer calculo preciso de qtd de meses e anos			
		}
		
		parametros.put("contrato_fim", dataFimDia + " de " + dataFimMes + " de " + dataFimAno); //"01 de fevereiro de 2017"
		
		//TODO
		parametros.put("contrato_desconto_pontualidade", "300,00 (TREZENTOS REIAS)");

		//Imovel & Data
		dataDia = new SimpleDateFormat("dd").format(new Date());
		dataMes = new SimpleDateFormat("MMMM").format(new Date());
		dataAno = new SimpleDateFormat("yyyy").format(new Date());
		
		try{
			imovel = imovelService.porId(contrato.getTbImovel().getIdImovel());			
		}catch(Exception e){
			FacesUtil.addErrorMessage("Não foi possível gerar o relatório. Não há Imóvel.");
			e.getStackTrace();
		}
		
		parametros.put("imovel_endereco_completo", imovel.getDsEndereco() + ", " 
				+ imovel.getNuEndereco()
				+ (imovel.getDsComplemento() != "" ? ", " + imovel.getDsComplemento() : "")
				+ (imovel.getTbBairro().getNoBairro() != "" ? ", " + imovel.getTbBairro().getNoBairro() : "")
				+ (imovel.getTbMunicipio().getNoMunicipio() != "" ? ", " + imovel.getTbMunicipio().getNoMunicipio() : "") ); //"QUADRA 8 LOTE 19 APARTAMENTO 103 SETOR OESTE GAMA-DF"
		parametros.put("cidade_data", "Gama, " + dataDia + " de " + dataMes + " de " + dataAno); //"Gama, 01 de Fevereiro de 2016"
		
		//Apicando os paths dos relatorios
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
