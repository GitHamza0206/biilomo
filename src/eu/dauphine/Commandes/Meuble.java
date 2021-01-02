package eu.dauphine.Commandes;

import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Personnel.Personnel;

import java.util.*;

/**
 * 
 */
public class Meuble   {

    /**
     * Default constructor
     */
    public Meuble(List<Lot> listeLots, int dureeConstruction, String nom, PieceMaison piecemaison) {
        this.ListeLots = listeLots;
        this.dureeConstruction = dureeConstruction;
        this.nom = nom;
        this.piecemaison =piecemaison;
    }

    public Meuble(){

    }
    /**
     * 
     */
    private List<Lot> ListeLots;

    /**
     * 
     */
    private int dureeConstruction;

    /**
     * 
     */
    private String nom;

    private PieceMaison piecemaison;

    private Constructeur personnelMonteurDuMeuble;

    private  double coutDeRevient;

    public List<Lot> getListeLots() {
        return ListeLots;
    }

    public int getDureeConstruction() {
        return dureeConstruction;
    }

    public PieceMaison getPiecemaison() {
        return piecemaison;
    }

    public String getNom() {
        return nom;
    }

    public Constructeur getPersonnelMonteurDuMeuble() {
        return personnelMonteurDuMeuble;
    }

    public double getCoutDeRevient() {
        return coutDeRevient;
    }

    public void setDureeConstruction(int dureeConstruction) {
        this.dureeConstruction = dureeConstruction;
    }

    public void setListeLots(List<Lot> listeLots) {
        ListeLots = listeLots;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPiecemaison(PieceMaison piecemaison) {
        this.piecemaison = piecemaison;
    }

    public void setPersonnelMonteurDuMeuble(Constructeur personnelMonteurDuMeuble) {
        this.personnelMonteurDuMeuble = personnelMonteurDuMeuble;
    }

    public void setCoutDeRevient(double coutDeRevient) {
        this.coutDeRevient = coutDeRevient;
    }
}