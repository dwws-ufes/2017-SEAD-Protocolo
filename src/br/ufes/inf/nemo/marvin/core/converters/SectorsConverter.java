package br.ufes.inf.nemo.marvin.core.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.ufes.inf.nemo.marvin.core.controller.ManageSectorsController;
import br.ufes.inf.nemo.marvin.core.domain.Sector;

@FacesConverter(value = "sectorsConverter")
public class SectorsConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		
		if(value == null && value.isEmpty()) return null;
		
		ManageSectorsController controller = context.getApplication().evaluateExpressionGet(context, "#{manageSectorsController}", ManageSectorsController.class); 
		
		for(Sector sector : controller.getSectors()){
			if(sector.getName().equals(value)){
				return sector;
			}
		}
		
		throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to Sector", value)));
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null || arg2.equals("")) {
	        return "";
	    }
	    if (arg0 == null) {
	        throw new NullPointerException("context");
	    }
	    if (arg1 == null) {
	        throw new NullPointerException("component");
	    }
	    return ((Sector) arg2).getName();
	}

}
