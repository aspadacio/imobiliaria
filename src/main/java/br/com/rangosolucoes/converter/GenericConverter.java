package br.com.rangosolucoes.converter;

import java.io.Serializable;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.rangosolucoes.util.SampleEntity;

@FacesConverter("generic")
public class GenericConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
            return this.getAttributesFrom(component).get(value);
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null
                && !"".equals(value)) {

            SampleEntity entity = (SampleEntity) value;

            // adiciona item como atributo do componente
            this.addAttribute(component, entity);

            Long codigo = entity.getId();
            if (codigo != null) {
                return String.valueOf(codigo);
            }
        }
        return "";
	}
	
	protected void addAttribute(UIComponent component, SampleEntity o) {
		if(o != null && o.getId() != null){
	        String key = o.getId().toString(); // codigo da empresa como chave neste caso
	        this.getAttributesFrom(component).put(key, o);
		}
    }

    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
