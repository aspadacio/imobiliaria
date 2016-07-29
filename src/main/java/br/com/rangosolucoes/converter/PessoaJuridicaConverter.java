package br.com.rangosolucoes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.repository.ImobiliariaRepository;
import br.com.rangosolucoes.util.cdi.CDIServiceLocator;

@FacesConverter(value = "pessoaJuridicaConverter", forClass = TbPessoaJuridica.class)
public class PessoaJuridicaConverter implements Converter{
	
	private ImobiliariaRepository imobiliariaRepository;

	public PessoaJuridicaConverter() {
		imobiliariaRepository = CDIServiceLocator.getBean(ImobiliariaRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbPessoaJuridica retorno = null;
		
		if(value != null){
			retorno = imobiliariaRepository.porCNPJ(value);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && ((TbPessoaJuridica)value).getNuCnpj() != null){
			return ((TbPessoaJuridica)value).getNuCnpj();
		}
		return "";
	}

}
