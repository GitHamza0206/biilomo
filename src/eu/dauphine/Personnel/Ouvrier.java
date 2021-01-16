package eu.dauphine.Personnel;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Inteferaces.GestionStock;
import eu.dauphine.Time.MyTimer;

import java.util.*;

/**
 * 
 */
public class Ouvrier extends Personnel implements GestionStock , Constructeur {
    boolean Disponible = true;
    int tempsRestantPourFinirLaConstruction;
    int TempsPourFinirLeStockage;
    Meuble meubleEnCoursDeMontage;


    /**
     * Default constructor
     */
    public Ouvrier(PieceMaison pieceMaison) {
        super.nom = "Ouvrier" + identifiant;
        super.specialite = pieceMaison;
        identifiant++;
    }


    /**
     * 
     */
    private double salaire = 5;

    /**
     * 
     */

    public static int identifiant=1;


    /**
     * @return
     */
    public void deplacerLot() {
        // TODO implement here

    }

    @Override
    public void monterMeuble(Entrepot entrepot, Meuble meuble) throws CloneNotSupportedException, ConstructionException, StockageException {
        if(this.specialite.equals(meuble.getPiecemaison())){
            setMeubleEnCoursDeMontage(meuble);
            entrepot.monterLeMeuble(meuble);
            setDisponible(false);
            setTempsRestantPourFinirLaConstruction(meuble.getDureeConstruction());


        } else {
            throw new ConstructionException("L'ouvrier n'est pas spécialisé dans la construction du meuble " + meuble.getNom());
        }
    }



    @Override
    public void setDisponible(boolean disponible) {
       this.Disponible = disponible;

    }

    @Override
    public void setTempsRestantPourFinirLaConstruction(int tempsRestantPourFinirLaConstruction) {
        this.tempsRestantPourFinirLaConstruction = tempsRestantPourFinirLaConstruction + MyTimer.pasDeTemps;
    }

    @Override
    public boolean getDisponible() {
        return this.Disponible;
    }

    @Override
    public int getTempsRestantPourFinirLaConstruction() {
        return this.tempsRestantPourFinirLaConstruction;
    }

    @Override
    public Meuble getMeubleEnCoursDeMontage() {
        return this.meubleEnCoursDeMontage;
    }

    @Override
    public void setMeubleEnCoursDeMontage(Meuble meuble) {
        meubleEnCoursDeMontage = meuble;
    }

    @Override
    public void updateTempsPourEtreDispo() {
        if(tempsRestantPourFinirLaConstruction==MyTimer.pasDeTemps){
            Disponible=true;
        }
        if(TempsPourFinirLeStockage==MyTimer.pasDeTemps){
            Disponible=true;
        }
    }

    @Override
    public double getSalaire() {
        return salaire;
    }

    @Override
    public String getNom() {
        return super.nom;
    }

    @Override
    public double getSalaireCummuleAPercevoir() {
        return super.salaireCummuleAPercevoir;
    }

    @Override
    public void setSalaireCummuleAPercevoir(double salaireCummuleAPercevoir) {
        super.setSalaireCummuleAPercevoir(salaireCummuleAPercevoir);
    }

    @Override
    public void setTachesRetirerLot(Queue<Lot> tachesRetirerLot) {
        super.setTachesRetirerLot(tachesRetirerLot);
    }

    @Override
    public Queue<Lot> getTachesRetirerLot() {
        return super.getTachesRetirerLot();
    }

    @Override
    public void addTachesRetirerLot(Lot lot) {
        super.addTachesRetirerLot(lot);
    }

    @Override
    public void addTachesMonterMeuble(Meuble meuble) {
        super.addTachesMonterMeuble(meuble);
    }


    @Override
    public void verifierDisponibilite() {
        super.verifierDisponibilite();
    }

    @Override
    public void setIndisponible() {
        super.Disponible=false;
    }


    @Override
    public String toString() {
        return "Ouvrier{" +
                "Nom='" + super.nom + '\'' +
                ", Disponible=" + Disponible +
                ", tempsRestantPourFinirLaConstruction=" + tempsRestantPourFinirLaConstruction +
                ", TempsPourFinirLeStockage=" + TempsPourFinirLeStockage +
                ", meubleEnCoursDeMontage=" + meubleEnCoursDeMontage +
                ", salaire=" + salaire +
                ", specialite=" + specialite +
                '}';
    }
}