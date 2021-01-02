package eu.dauphine.Entrepot;

import java.util.*;

/**
 * 
 */
public class Lot {
    /**
     *
     */
    private int id;

    /**
     *
     */
    private int volume;

    private String nom;

    private double poids;

    private double prix;

    public static int identifiant=0;

    /**
     * Default constructor
     */
    public Lot(int volume, String nom, Double poids , Double prix) {
        identifiant++;
        this.id = identifiant;
        this.volume = volume;
        this.nom = nom  ;
        this.poids=poids;
        this.prix=prix  ;

    }
    public Lot(int volume, String nom){
        this.volume = volume;
        this.nom = nom;
    }




    public int getId() {
        return id;
    }

    public int getVolume() {
        return volume;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;

    }

    public double getPoids() {
        return poids;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    protected Lot clone() throws CloneNotSupportedException {
        return new Lot(this.volume,this.nom,this.poids,this.prix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lot lot = (Lot) o;
        return volume == lot.volume &&
                Objects.equals(nom, lot.nom) ;

    }




    @Override
    public int hashCode() {
        return Objects.hash(id, volume, nom, poids, prix);
    }

    public boolean isDisponible(Entrepot entrepot) throws CloneNotSupportedException {
       return entrepot.isDisponible(this);
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", volume=" + volume +
                ", nom='" + nom + '\'' +
                ", poids=" + poids +
                ", prix=" + prix +
                '}';
    }
}