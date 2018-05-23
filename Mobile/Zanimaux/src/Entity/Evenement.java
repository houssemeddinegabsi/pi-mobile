/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;
/**
 *
 * @author houss
 */

public class Evenement  {
  
    private int id;
    private User id_membre;
    private String nom;

    private Date date;
    private String lieu;
    private String description;
    private int nbr_max_participant;
    private String createur;
    private String image;
    private int nbr_participant;

    public Evenement() {
    }

    public Evenement(User id_membre, String nom, String lieu, String description, int nbr_max_participant, String image) {
        this.id_membre = id_membre;
        this.nom = nom;
        this.lieu = lieu;
        this.description = description;
        this.nbr_max_participant = nbr_max_participant;
        this.image = image;
    }
    
    public Evenement(User id_membre, String nom, Date date, String lieu, String description, int nbr_max_participant, String createur, String image, int nbr_participant) {
        this.id_membre = id_membre;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.nbr_max_participant = nbr_max_participant;
        this.createur = createur;
        this.image = image;
        this.nbr_participant = nbr_participant;
    }

    public Evenement(String nom, Date date, String lieu, String description, int nbr_max_participant, String createur, String image, int nbr_participant) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.nbr_max_participant = nbr_max_participant;
        this.createur = createur;
        this.image = image;
        this.nbr_participant = nbr_participant;
    }
    

    public Evenement(int id, String nom, Date date, String lieu, String description, int nbr_max_participant, String image) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.nbr_max_participant = nbr_max_participant;
        this.image = image;
    }
    
    
    public Evenement(String nom, Date date, String lieu, String description, int nbr_max_participant,String image) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.nbr_max_participant = nbr_max_participant;
        this.image = image;
        
    }

    
    
    public Evenement(int id, User id_membre, String nom, Date date, String lieu, String description, int nbr_max_participant, String createur, String image, int nbr_participant) {
        this.id = id;
        this.id_membre = id_membre;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.nbr_max_participant = nbr_max_participant;
        this.createur = createur;
        this.image = image;
        this.nbr_participant = nbr_participant;
    }

    public Evenement(User id_membre, String nom, Date date, String lieu, String description, int nbr_max_participant, String image) {
        this.id_membre = id_membre;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
        this.nbr_max_participant = nbr_max_participant;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getId_membre() {
        return id_membre;
    }

    public void setId_membre(User id_membre) {
        this.id_membre = id_membre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbr_max_participant() {
        return nbr_max_participant;
    }

    public void setNbr_max_participant(int nbr_max_participant) {
        this.nbr_max_participant = nbr_max_participant;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNbr_participant() {
        return nbr_participant;
    }

    public void setNbr_participant(int nbr_participant) {
        this.nbr_participant = nbr_participant;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
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
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", id_membre=" + id_membre + ", nom=" + nom + ", date=" + date + ", lieu=" + lieu + ", description=" + description + ", nbr_max_participant=" + nbr_max_participant + ", createur=" + createur + ", image=" + image + ", nbr_participant=" + nbr_participant + "\n"+'}';
    }
    
   
    
}
