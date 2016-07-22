package br.com.rangosolucoes.converter;

//@FacesConverter(value = "categoriaConverter" ,forClass = Categoria.class)
public class CategoriaConverter {//implements Converter{

	/*	//@Inject
	private CategoriaRepository categoriaRepository;
	
	public CategoriaConverter() {
		categoriaRepository = CDIServiceLocator.getBean(CategoriaRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria retorno = null;
		
		if(value != null){
			Long id = new Long(value);
			retorno = categoriaRepository.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && ((Categoria)value).getId() != null){
			return ((Categoria)value).getId().toString();
		}
		return "";
	}*/

}
