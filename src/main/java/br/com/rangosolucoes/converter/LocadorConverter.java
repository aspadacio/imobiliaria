package br.com.rangosolucoes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.rangosolucoes.model.TbLocador;
import br.com.rangosolucoes.repository.LocadorRepository;
import br.com.rangosolucoes.util.cdi.CDIServiceLocator;

@FacesConverter(value = "locadorConverter")
public class LocadorConverter implements Converter{
	
	private LocadorRepository locadorRepository;
	
	public LocadorConverter() {
		locadorRepository = CDIServiceLocator.getBean(LocadorRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbLocador retorno = null;
		
		if(value != null){
			retorno = locadorRepository.findLocadorById(Long.parseLong(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && ((TbLocador)value).getIdLocador() != null){
			return ((TbLocador)value).getIdLocador().toString();
		}
		return null;
	}

}
