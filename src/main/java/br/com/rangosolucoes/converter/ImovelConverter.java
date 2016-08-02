package br.com.rangosolucoes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.repository.ImovelRepository;
import br.com.rangosolucoes.util.cdi.CDIServiceLocator;

@FacesConverter(value = "imovelConverter")
public class ImovelConverter implements Converter{
	
	private ImovelRepository imovelRepository;
	
	public ImovelConverter() {
		imovelRepository = CDIServiceLocator.getBean(ImovelRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbImovel retorno = null;
		
		if(value != null){
			retorno = imovelRepository.porId(Long.parseLong(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && ((TbImovel)value).getIdImovel() != null){
			return ((TbImovel)value).getIdImovel().toString();
		}
		return null;
	}

}
