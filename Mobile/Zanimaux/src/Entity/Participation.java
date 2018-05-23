/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;



import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author houss
 */

public class Participation  {

    
   
    private int id;
    private User user;
    private Evenement evenement;

    public Participation() {
    }

    public Participation(int id, User user, Evenement evenement) {
        this.id = id;
        this.user = user;
        this.evenement = evenement;
    }

    public Participation(User user, Evenement evenement) {
        this.user = user;
        this.evenement = evenement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }
    
   
   
    
}
