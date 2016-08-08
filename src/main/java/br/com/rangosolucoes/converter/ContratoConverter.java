package br.com.rangosolucoes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.rangosolucoes.model.TbContrato;
import br.com.rangosolucoes.repository.ContratoRepository;
import br.com.rangosolucoes.util.cdi.CDIServiceLocator;

@FacesConverter(value = "contratoConverter")
public class ContratoConverter implements Converter{
	
	private ContratoRepository contratoRepository;
	
	public ContratoConverter() {
		contratoRepository = CDIServiceLocator.getBean(ContratoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbContrato retorno = null;
		
		if(value != null){
			retorno = contratoRepository.porId(Long.parseLong(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && ((TbContrato)value).getIdContrato() != null){
			return ((TbContrato)value).getIdContrato().toString();
		}
		return null;
	}

}
