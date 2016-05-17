/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.CoopProspect;
import service.CoopProspectFacadeREST;

/**
 *
 * @author vic
 */
@ManagedBean
@SessionScoped
@FacesConverter(value = "pCnvtr")
public class prospectCnvrtr implements Converter{
    @EJB
    private CoopProspectFacadeREST coopProspectFacadeREST;
    
    public prospectCnvrtr() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return coopProspectFacadeREST.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((CoopProspect) value).getProspectNo().toString();
    }
    
}
