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
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.repository.filter.LocadorFilter;
import br.com.rangosolucoes.repository.filter.LocatarioFilter;
import br.com.rangosolucoes.service.LocadorService;
import br.com.rangosolucoes.service.LocatarioService;
import br.com.rangosolucoes.service.MunicipioService;
import br.com.rangosolucoes.util.jsf.FacesUtil;
import br.com.rangosolucoes.util.report.ContratoResidencialRelatorio;

/**
 * Classe responsável por gerar relatório do tipo Contrato Residencial
 * */
@Named("relatorioContratoResidencialBean")
public class RelatorioContratoResidencialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Inject
	private LocadorService locadorService;
	
	@Inject
	private LocatarioService locatarioService;
	
	@Inject
	private MunicipioService municipioService;
	
	//Atributos que serão usados para retornar dados das tabelas
	@Getter @Setter @NonNull private String locadorId; //Número do CNPJ
	@Getter @Setter @NonNull private String locatarioId; //Poderá ser o número do CNPJ ou do CPF
	@Getter @Setter @NonNull private String imovelId; //id do imóvel

	//Objetos usados para transmissão de dados.
	private Map<String, Object> parametros;
	private TbPessoa locador;
	private TbPessoa locatario;
	private TbContrato contrato;
	private TbImovel imovel;
	
	public void emitir() {
		parametros = new HashMap<>();
		
		//Buscar informações para serem inseridas no 'parametros'
		//Locador
		LocadorFilter locadorFiltro = new LocadorFilter();
		locadorFiltro.setCnpj(locadorId);
		locador = (TbPessoa) locadorService.buscaPessoas(locadorFiltro);
		
		//Locatário
		LocatarioFilter locatarioFiltro = new LocatarioFilter();
		Boolean isPessoaFisica = false;
		if(!locatarioId.trim().isEmpty()){
			if(locatarioId.trim().length() <= 11){
				//CPF
				locatarioFiltro.setCpf(locatarioId);
				isPessoaFisica = true;
			}else{
				//CNPJ
				locatarioFiltro.setCnpj(locatarioId);
			}
		}
		locatario = (TbPessoa) locatarioService.buscaPessoas(locatarioFiltro);
		
		
		//Preencher HashMap de parametros
		//Locador
		//Mask para CNPJ
		MaskFormatter maskCnpj;
		try {
			maskCnpj = new MaskFormatter("##.###.###/####-##");
			maskCnpj.setValueContainsLiteralCharacters(false);
			parametros.put("locador_cnpj", maskCnpj.valueToString(locador.getTbPessoaJuridica().getNuCnpj()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Endereco -> "quadra 201 lote C1 sala 06 A 09 comércio local Santa Maria Sul Brasília DF"
		TbEnderecoPessoa locadorEndereco = null;
		//TbMunicipio locadorMunicipio = null;
		
		locadorEndereco = locadorService.findEnderecoById(locador.getIdPessoa());
		//locadorMunicipio = municipioService.findByEnderecoPessoaId(locadorEndereco.getTbMunicipio().getIdMunicipio());
		
		parametros.put("locador_endereco_completo", locadorEndereco.getDsEndereco() + ", " //Rua, Avenida, Quadra, etc. 
				+ locadorEndereco.getNuEndereco() +  //Número
				(locadorEndereco.getDsComplemento() != "" ? ", " + locadorEndereco.getDsComplemento() : ", ") //Complemento
				+ ( locadorEndereco.getTbBairro().getNoBairro() != "" ? ", " + locadorEndereco.getTbBairro().getNoBairro() : ", " )  
				+ locadorEndereco.getTbMunicipio().getNoMunicipio() + " - " + locadorEndereco.getTbMunicipio().getSgUf()); //Cidade + UF
		parametros.put("locador_razao_social", locador.getTbPessoaJuridica().getNoRazaoSocial());
		
		//Locatário
		try {
			MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
			parametros.put("locatario_cpf", maskCpf.valueToString(locatario.getTbPessoaFisica().getNuCpf())); //"036.751.351-01"
		} catch (ParseException e) {
			e.getStackTrace();
		}
		try {
			MaskFormatter maskRg = new MaskFormatter("##.###.###-#");
			parametros.put("locatario_rg", maskRg.valueToString(locatario.getTbPessoaFisica().getNuRg()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		parametros.put("locatario_endereco_completo", "QUADRA 8 LOTE 19 APARTAMENTO 103 SETOR OESTE GAMA-DF");
		parametros.put("locatario_estado_civil", locatario.getTbPessoaFisica().getDsEstadoCivil());
		parametros.put("locatario_nacionalidade", locatario.getTbPessoaFisica().getDsNacionalidade());
		parametros.put("locatario_nome", locatario.getTbPessoaFisica().getNoPessoaFisica());
		parametros.put("locatario_profissao", locatario.getTbPessoaFisica().getDsProfissao());
		
		//Contrato
		parametros.put("contrato_numero", contrato.getIdContrato());
		parametros.put("contrato_duracao", contrato.getNuDuracao());
		parametros.put("contrato_inicio", contrato.getDtInicio()); //"01 de fevereiro de 2016"
		parametros.put("contrato_fim", "01 de fevereiro de 2017");
		
		//Imovel & Data
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		parametros.put("imovel_endereco_completo", imovel.getDsEndereco() 
				+ ", " + imovel.getNuEndereco()
				+ imovel.getDsComplemento() != "" ? ", " + imovel.getDsComplemento() : ""
				+ imovel.getTbBairro().getNoBairro() != "" ? ", " + imovel.getTbBairro().getNoBairro() : ""
				+ imovel.getTbMunicipio().getNoMunicipio() != "" ? ", " + imovel.getTbMunicipio().getNoMunicipio() : "" ); //"QUADRA 8 LOTE 19 APARTAMENTO 103 SETOR OESTE GAMA-DF"
		parametros.put("cidade_data", "Gama, " + dataFormatada); //"Gama, 01 de Fevereiro de 2016"
		
		//Apicando os paths dos relatorios
		List<String> filesPaths = new ArrayList<String>();
		filesPaths.add("/relatorios/contrato/residencial/contratoResidencial1.jasper");
		filesPaths.add("/relatorios/contrato/residencial/contratoResidencial2.jasper");
		filesPaths.add("/relatorios/contrato/residencial/contratoResidencial3.jasper");
		filesPaths.add("/relatorios/contrato/residencial/contratoResidencial4.jasper");
		
		ContratoResidencialRelatorio executor = new ContratoResidencialRelatorio(filesPaths,
			(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()
			, parametros, "Contrato Residencial.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if(executor.isRelatorioGerado()){
			FacesContext.getCurrentInstance().responseComplete();
		}else{
			FacesUtil.addErrorMessage("RelatorioContratoResidencialBean :: A execução do relatório não retornou dados.");
		}
	}
}
