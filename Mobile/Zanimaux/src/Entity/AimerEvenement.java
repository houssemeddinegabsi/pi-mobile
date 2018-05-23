/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author houss
 */
public class AimerEvenement {
    private int id;
    private int id_user;
    private int id_evenement;

    public AimerEvenement() {
    }

    public AimerEvenement(int id_user, int id_evenement) {
        this.id_user = id_user;
        this.id_evenement = id_evenement;
    }

    public AimerEvenement(int id, int id_user, int id_evenement) {
        this.id = id;
        this.id_user = id_user;
        this.id_evenement = id_evenement;
    }

    public int getId() {
        return id;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.id;
        hash = 13 * hash + this.id_user;
        hash = 13 * hash + this.id_evenement;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AimerEvenement other = (AimerEvenement) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.id_user != other.id_user) {
            return false;
        }
        if (this.id_evenement != other.id_evenement) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AimerEvenement{" + "id=" + id + ", id_user=" + id_user + ", id_evenement=" + id_evenement + '}';
    }
    
    
}
