/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.liferay.portal.service.OrganizationLocalServiceUtil;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import model.Organization;
import service.CoopApplicantFacadeREST;
import service.CoopMemberFacadeREST;
import service.CoopMemberUserFacadeREST;
import service.CoopOrgUnitFacadeREST;
import service.CoopProspectFacadeREST;

/**
 *
 * @author vic
 */
@ManagedBean
@SessionScoped
public class moveProspectBean implements Serializable {

    @PersistenceUnit
    EntityManagerFactory emf;

    @EJB
    private CoopOrgUnitFacadeREST coopOrgUnitFacadeREST;
    @EJB
    private CoopProspectFacadeREST coopProspectFacadeREST;
    @EJB
    private CoopApplicantFacadeREST coopApplicantFacadeREST;
    @EJB
    private CoopMemberFacadeREST coopMemberFacadeREST;
    @EJB
    private CoopMemberUserFacadeREST coopMemberUserFacadeREST;

    private prospectListModel prospect;
    private memberListModel member;
    private String PTp;
    private String PTm;

    public moveProspectBean() {

    }

    public void editProspectPT() {
        try {
            for (int i = 0; i != getProspect().getProspectList().size(); i++) {
                getProspect().getProspectList().get(i).setOuCode(coopOrgUnitFacadeREST.find(getPTp()));
                coopProspectFacadeREST.edit(getProspect().getProspectList().get(i));
            }
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", getProspect().getProspectList().size() + " Person(s) has been moved to " + getPTp());
            FacesContext.getCurrentInstance().addMessage(null, message);
            getProspect().setProspectList(null);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failed", getProspect().getProspectList().size() + " Person(s) failed to moved to " + getPTp());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void editMemberPT() {
        try {
            Organization q = (Organization) emf.createEntityManager().createQuery("SELECT c FROM Organization c "
                    + "WHERE c.name ='" + coopOrgUnitFacadeREST.find(getPTm()).getOuShortName() + "'").getResultList().get(0);
            try {
                for (int i = 0; i != getMember().getMemberList().size(); i++) {
                    //Change Member PT
                    getMember().getMemberList().get(i).setOuCode(coopOrgUnitFacadeREST.find(getPTm()));
                    coopMemberFacadeREST.edit(getMember().getMemberList().get(i));

                    //Change Applicant PT
                    //getMember().getMemberList().get(i).getApplicantNo().setOuCode(coopOrgUnitFacadeREST.find(getPTm()));
                    //coopApplicantFacadeREST.edit(getMember().getMemberList().get(i).getApplicantNo());
                    //Change Prospect PT
                    //getMember().getMemberList().get(i).getApplicantNo().getProspectNo().setOuCode(coopOrgUnitFacadeREST.find(getPTm()));
                    //coopProspectFacadeREST.edit(getMember().getMemberList().get(i).getApplicantNo().getProspectNo());
                    //Change Member Organization
                    //System.out.println("size: " + coopMemberUserFacadeREST.find(getMember().getMemberList().get(i).getMemNo()).getUserId().longValue()
                    //        + " : " + OrganizationLocalServiceUtil.getUserOrganizations(
                    //                coopMemberUserFacadeREST.find(getMember().getMemberList().get(i).getMemNo()).getUserId().longValue()).size());
                    try {
                        for (Integer x = 0; x != OrganizationLocalServiceUtil.getUserOrganizations(
                                coopMemberUserFacadeREST.find(getMember().getMemberList().get(i).getMemNo()).getUserId().longValue()).size(); x++) {
                            //Delete organization with name that starts with "PT" 
                            if (OrganizationLocalServiceUtil.getOrganization(OrganizationLocalServiceUtil.getUserOrganizations(coopMemberUserFacadeREST.find(
                                    getMember().getMemberList().get(i).getMemNo()).getUserId().longValue()).get(x).getOrganizationId()).getName().startsWith("PT")) {
                                OrganizationLocalServiceUtil.deleteUserOrganization(
                                        coopMemberUserFacadeREST.find(getMember().getMemberList().get(i).getMemNo()).getUserId().longValue(),
                                        OrganizationLocalServiceUtil.getUserOrganizations(
                                                coopMemberUserFacadeREST.find(getMember().getMemberList().get(i).getMemNo()).getUserId().longValue()).get(x).getOrganizationId());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Delete Organization: " + e.getMessage());
                    }
                    OrganizationLocalServiceUtil.addUserOrganization(
                            coopMemberUserFacadeREST.find(getMember().getMemberList().get(i).getMemNo()).getUserId().longValue(), q.getOrganizationid());
                }
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", getMember().getMemberList().size() + " Person(s) has been moved to " + coopOrgUnitFacadeREST.find(getPTm()).getOuShortName());
                FacesContext.getCurrentInstance().addMessage(null, message);
                getMember().setMemberList(null);
            } catch (Exception e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful with error(s)", getMember().getMemberList().size() + " Person(s) has been moved to " + coopOrgUnitFacadeREST.find(getPTm()).getOuShortName() + " but failed to move liferay organization unit");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception e) {
            //System.out.println("Add Organization: " + e.getMessage());
            try {
                for (int i = 0; i != getMember().getMemberList().size(); i++) {
                    //Change Member PT
                    getMember().getMemberList().get(i).setOuCode(coopOrgUnitFacadeREST.find(getPTm()));
                    coopMemberFacadeREST.edit(getMember().getMemberList().get(i));
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", getMember().getMemberList().size() + " Person(s) has been moved to " + coopOrgUnitFacadeREST.find(getPTm()).getOuShortName());
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } catch (Exception x) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failed", getMember().getMemberList().size() + " Person(s) failed to moved to " + coopOrgUnitFacadeREST.find(getPTm()).getOuShortName());
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public prospectListModel getProspect() {
        if (prospect == null) {
            prospect = new prospectListModel();
        }
        return prospect;
    }

    public void setProspect(prospectListModel prospect) {
        this.prospect = prospect;
    }

    public memberListModel getMember() {
        if (member == null) {
            member = new memberListModel();
        }
        return member;
    }

    public void setMember(memberListModel member) {
        this.member = member;
    }

    public String getPTp() {
        return PTp;
    }

    public void setPTp(String PTp) {
        this.PTp = PTp;
    }

    public String getPTm() {
        return PTm;
    }

    public void setPTm(String PTm) {
        this.PTm = PTm;
    }
}
