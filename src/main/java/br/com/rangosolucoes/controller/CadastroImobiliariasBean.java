package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.model.TbPessoaTelefone;
import br.com.rangosolucoes.service.BairroService;
import br.com.rangosolucoes.service.CadastroImobiliariaService;
import br.com.rangosolucoes.service.EnderecoPessoaService;
import br.com.rangosolucoes.service.MunicipioService;
import br.com.rangosolucoes.service.PessoaService;
import br.com.rangosolucoes.service.PessoaTelefoneService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("cadastroImobiliariasBean")
@SessionScoped
public class CadastroImobiliariasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroImobiliariaService imobiliariaService;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private PessoaTelefoneService pessoaTelefoneService;
	
	@Inject
	private MunicipioService municipioService;
	
	@Inject
	private BairroService bairroService;
	
	@Inject
	private EnderecoPessoaService enderecoPessoaService;
	
	private TbPessoaJuridica pessoaJuridica;
	private TbPessoaTelefone pessoaTelefone;
	private TbPessoa pessoa;
	private TbEnderecoPessoa enderecoPessoa;
	private TbMunicipio municipio;
	private TbBairro bairro;
	
	private String nuTelefoneDdd;
	private String nuTelefone;
	private String tpTelefone;
	private List<TbPessoaTelefone> telefones;

	@PostConstruct
	public void init() {
		limpar();
	}
	
	public boolean isEditando() {
		return this.pessoaJuridica.getNuCnpj() != null;
	}

	public String novoCadastro() {
		limpar();
		return "/imobiliaria/CadastroImobiliaria?faces-redirect=true";
	}

	public String salvar() {
		
		pessoaJuridica.setNoContato("a definir");
		pessoaJuridica = imobiliariaService.salvar(pessoaJuridica);
		
		pessoa = populaPessoa(pessoaJuridica);
		pessoa = pessoaService.salvar(pessoa);
		
		if(telefones.size() > 0){
			for(TbPessoaTelefone pessoaTelefone : telefones){
				pessoaTelefone.setTbPessoa(pessoa);
				this.pessoaTelefone = pessoaTelefoneService.salvar(pessoaTelefone);
			}
		}else{
			FacesUtil.addErrorMessage("É necessário informar ao menos um telefone para contato na imobiliária.");
		}
		
		municipio = municipioService.salvar(municipio);
		
		bairro.setTbMunicipio(municipio);
		bairro = bairroService.salvar(bairro);
		
		enderecoPessoa.setTbPessoa(pessoa);
		enderecoPessoa.setTbMunicipio(municipio);
		enderecoPessoa.setTbBairro(bairro);
		enderecoPessoa = enderecoPessoaService.salvar(enderecoPessoa);
		
		limpar();
		FacesUtil.addInfoMessage("Imobiliária cadastrada com sucesso!");
		return "/imobiliaria/CadastroImobiliaria?faces-redirect=true";
	}

	private TbPessoa populaPessoa(TbPessoaJuridica pessoaJuridica) {
		pessoa.setTbPessoaJuridica(pessoaJuridica);
		pessoa.setDtUltimaAlteracao(new Date());
		return pessoa;
	}

	public void limpar() {
		pessoaJuridica = new TbPessoaJuridica();
		pessoaTelefone = new TbPessoaTelefone();
		pessoa = new TbPessoa();
		enderecoPessoa = new TbEnderecoPessoa();
		municipio = new TbMunicipio();
		bairro = new TbBairro();
		telefones = new ArrayList<>();
	}
	
	public void adicinaTelefoneNaLista(){
		if(nuTelefone != "" && nuTelefone != null && nuTelefoneDdd != "" && nuTelefoneDdd != null &&
				tpTelefone != "" && tpTelefone != null){
			pessoaTelefone.setNuTelefoneDdd(nuTelefoneDdd);
			pessoaTelefone.setNuTelefone(Integer.valueOf(nuTelefone));
			pessoaTelefone.setTpTelefone(tpTelefone.charAt(0));
			telefones.add(pessoaTelefone);
			
			nuTelefoneDdd = "";
			nuTelefone = "";
			tpTelefone = "";
			pessoaTelefone = new TbPessoaTelefone();
		}else{
			FacesUtil.addErrorMessage("É necessário informar todos os dados do telefone.");
		}
	}

	public TbPessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(TbPessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public TbPessoaTelefone getPessoaTelefone() {
		return pessoaTelefone;
	}

	public void setPessoaTelefone(TbPessoaTelefone pessoaTelefone) {
		this.pessoaTelefone = pessoaTelefone;
	}

	public TbPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(TbPessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TbEnderecoPessoa getEnderecoPessoa() {
		return enderecoPessoa;
	}

	public void setEnderecoPessoa(TbEnderecoPessoa enderecoPessoa) {
		this.enderecoPessoa = enderecoPessoa;
	}

	public TbMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(TbMunicipio municipio) {
		this.municipio = municipio;
	}

	public TbBairro getBairro() {
		return bairro;
	}

	public void setBairro(TbBairro bairro) {
		this.bairro = bairro;
	}

	public List<TbPessoaTelefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TbPessoaTelefone> telefones) {
		this.telefones = telefones;
	}

	public CadastroImobiliariaService getImobiliariaService() {
		return imobiliariaService;
	}

	public void setImobiliariaService(CadastroImobiliariaService imobiliariaService) {
		this.imobiliariaService = imobiliariaService;
	}

	public String getNuTelefoneDdd() {
		return nuTelefoneDdd;
	}

	public void setNuTelefoneDdd(String nuTelefoneDdd) {
		this.nuTelefoneDdd = nuTelefoneDdd;
	}

	public String getNuTelefone() {
		return nuTelefone;
	}

	public void setNuTelefone(String nuTelefone) {
		this.nuTelefone = nuTelefone;
	}

	public String getTpTelefone() {
		return tpTelefone;
	}

	public void setTpTelefone(String tpTelefone) {
		this.tpTelefone = tpTelefone;
	}

}
