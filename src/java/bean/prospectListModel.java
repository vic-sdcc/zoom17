package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.CoopProspect;
import org.primefaces.model.DualListModel;
import service.CoopProspectFacadeREST;

@ManagedBean
@SessionScoped
public class prospectListModel implements Serializable {

    @EJB
    private CoopProspectFacadeREST coopProspectFacadeREST;
    private List<CoopProspect> prospectList;
    private ArrayList<CoopProspect> prospectArrList;
    private DataModel<CoopProspect> prospectModel;
    private DualListModel<CoopProspect> prospectDual;
    private CoopProspect prospect;
    private CoopProspect selectedProspect;

    public prospectListModel() {

    }

    public void beanclear() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("prospectListModel", null);
    }

    public List<CoopProspect> getProspectList() {
        if (prospectList == null) {
            prospectList = new ArrayList<>();
        }
        return prospectList;
    }

    public void setProspectList(List<CoopProspect> prospectList) {
        this.prospectList = prospectList;
    }

    public ArrayList<CoopProspect> getProspectArrList() {
        if (prospectArrList == null) {
            prospectArrList = new ArrayList<>();
        }
        return prospectArrList;
    }

    public void setProspectArrList(ArrayList<CoopProspect> prospectArrList) {
        this.prospectArrList = prospectArrList;
    }

    public DataModel<CoopProspect> getProspectModel() {
        if (prospectModel == null) {
            prospectModel = new ListDataModel<>(getProspectList());
        }
        return prospectModel;
    }

    public void setProspectModel(DataModel<CoopProspect> prospectModel) {
        this.prospectModel = prospectModel;
    }

    public DualListModel<CoopProspect> getProspectDual() {
        if (prospectDual == null) {
            prospectDual = new DualListModel<CoopProspect>(getProspectList(), getProspectArrList());
        }
        return prospectDual;
    }

    public void setProspectDual(DualListModel<CoopProspect> prospectDual) {
        this.prospectDual = prospectDual;
    }

    public CoopProspect getProspect() {
        if (prospect == null) {
            prospect = new CoopProspect();
        }
        return prospect;
    }

    public void setProspect(CoopProspect prospect) {
        this.prospect = prospect;
    }

    public CoopProspect getSelectedProspect() {
        if (selectedProspect == null) {
            selectedProspect = new CoopProspect();
        }
        return selectedProspect;
    }

    public void setSelectedProspect(CoopProspect selectedProspect) {
        this.selectedProspect = selectedProspect;
    }

}
