/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import model.CoopMember;
import model.CoopOrgUnit;
import model.CoopProspect;

/**
 *
 * @author vic
 */
@ManagedBean
@SessionScoped
public class returnObject implements Serializable {

    @PersistenceUnit
    EntityManagerFactory emf;

    public returnObject() {
        //CoopProspect q = (CoopProspect) emf.createEntityManager().createQuery("SELECT c FROM CoopProspect c").getResultList().get(0);
    }

    public List<CoopProspect> prospectList() {
        Query prospectList = emf.createEntityManager().createQuery("SELECT c FROM CoopProspect c "
                + "WHERE c.prospectNo NOT IN (SELECT x.prospectNo.prospectNo FROM CoopApplicant x)");
        return prospectList.getResultList();
    }

    public List<CoopMember> memberList() {
        Query memberList = emf.createEntityManager().createQuery("SELECT c FROM CoopMember c WHERE c.memStatus <> 'W'");
        return memberList.getResultList();
    }

    public List<CoopOrgUnit> orgUnitList() {
        Query OuList = emf.createEntityManager().createQuery("SELECT c FROM CoopOrgUnit c WHERE c.ouShortName LIKE 'PT%'");
        return OuList.getResultList();
    }

    public String memStat(Character status) {
        try {
            return (String) emf.createEntityManager().createQuery("SELECT c.statusName "
                    + "FROM CoopMemberStatus c "
                    + "WHERE c.memStatus ='" + status + "'").getResultList().get(0);
        } catch (Exception e) {
            return "Undefined";
        }
    }

    public String prosStat(Character status) {
        String name = "Unspecified";
        if (status == 'N') {
            name = "New";
        } else if (status == 'F') {
            name = "For-evaluation";
        } else if (status == 'R') {
            name = "Recommended";
        } else if (status == 'A') {
            name = "Approved";
        } else if (status == 'D') {
            name = "Dropped";
        }

        return name;
    }

}
