/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Compte;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import service.CompteFacade;
import service.OperationFacade;

/**
 *
 * @author lenovo
 */
@Named(value = "compteController")
@SessionScoped
public class CompteController implements Serializable {
    
    private Compte selected;
    private List<Compte> items;
    @EJB
    private CompteFacade ejbFacade;
    @EJB
    private OperationFacade operationFacade;
    private Double montant;
    
    public String debiter(){
        ejbFacade.debiter(selected.getId(), montant);
        selected = null; //réitialiser mon view.
        return "List";
        
    }
    public String create(){
      ejbFacade.create(selected);
      selected = null;
      return "List";
      
    }
    public void remove(Compte item){
        ejbFacade.remove(item.getId());
        items.remove(items.indexOf(item));
    }
    public void detail(Compte item){
        getSelected().setOperations(operationFacade.findByCompte(item.getId()));
    }
    

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    
    public Compte getSelected() {
        if(selected == null){
            selected = new Compte();
        }
        return selected;
    }

    public void setSelected(Compte selected) {
        this.selected = selected;
    }

    public List<Compte> getItems() {
        items=ejbFacade.findAll(); //recharger automatiquement les items.
        return items;
    }

    public void setItems(List<Compte> items) {
        this.items = items;
    }

    public CompteFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CompteFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    
    
    
    

    /**
     * Creates a new instance of CompteController
     */
    public CompteController() {
    }
    
}
