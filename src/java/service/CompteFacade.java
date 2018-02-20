/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Compte;
import bean.Operation;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lenovo
 */
@Stateless
public class CompteFacade extends AbstractFacade<Compte> {

    @PersistenceContext(unitName = "JEETestPU")
    private EntityManager em;
    @EJB
    private OperationFacade operationFacade;
    
    
    public int debiter(String rib, Double montant) {
        Compte compte = find(rib);
        if (compte.getSolde() < montant) {
            return -1;
        } else {
            compte.setSolde(compte.getSolde() - montant);
            edit(compte);
            
            Operation operation = new Operation();
            operation.setMontant(montant);
            operation.setType(2);
            operation.setCompte(compte);
            operationFacade.create(operation);
            return 1;
            
            //compte.setSolde(compte.getSolde() - montant);
            //edit(compte);
            
        }

    }
    
    public void remove(String rib){
       operationFacade.removeByCompte(rib);
        super.remove(new Compte(rib));
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompteFacade() {
        super(Compte.class);
    }

}
