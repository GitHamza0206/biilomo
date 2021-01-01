package eu.dauphine.Personnel;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Inteferaces.GestionStock;
import eu.dauphine.Time.Timer;

import java.util.*;

/**
 * 
 */
public class Ouvrier extends Personnel implements GestionStock , Constructeur {
    boolean Disponible = true;
    int tempsRestantPourFinirLaConstruction;
    int TempsPourFinirLeStockage;
    Meuble meubleEnCoursDeMontage;
    double montantAPercevoir;

    /**
     * Default constructor
     */
    public Ouvrier(PieceMaison pieceMaison) {
        super.specialite = pieceMaison;
    }


    /**
     * 
     */
    private double salaire = 5;

    /**
     * 
     */



    @Override
    public void retirerLot(Entrepot entrepot, Lot lot) throws StockageException {
        try{
            if(this.Disponible){
                entrepot.retirerLot(lot);
                this.TempsPourFinirLeStockage = 1;
            } else {
                throw new StockageException("L'ouvrier est indisponible pour retirer le lot " + lot.getNom());
            }
        } catch (Exception e){
            throw new StockageException("On ne peut pas retirer ");
        }
    }

    @Override
    public void stockerLot(Entrepot entrepot, Lot lot) throws StockageException {
        try {
            if(this.Disponible){
                entrepot.stockerLot(lot);
                this.TempsPourFinirLeStockage = 1;
            } else {
                throw new StockageException("L'ouvrier est indisponible pour stocker le lot " + lot.getNom());
            }

        } catch (Exception e){
            throw new StockageException("On ne peut pas stocker ");
        }
    }

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
             setMontantAPercevoir();

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
        this.tempsRestantPourFinirLaConstruction = tempsRestantPourFinirLaConstruction + Timer.time;
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
        if(tempsRestantPourFinirLaConstruction==Timer.time){
            Disponible=true;
        }
    }

    @Override
    public String toString() {
        return "Ouvrier{" +
                "Disponible=" + Disponible +
                ", tempsRestantPourFinirLaConstruction=" + tempsRestantPourFinirLaConstruction +
                ", TempsPourFinirLeStockage=" + TempsPourFinirLeStockage +
                ", meubleEnCoursDeMontage=" + meubleEnCoursDeMontage +
                ", salaire=" + salaire +
                '}';
    }

    @Override
    public double getMontantAPercevoir() {
        return this.montantAPercevoir;
    }

    @Override
    public void setMontantAPercevoir() {
        double montant = getMontantAPercevoir();
        for(Lot lot: this.getMeubleEnCoursDeMontage().getListeLots()){
            montantAPercevoir += lot.getPrix();
        }
        this.montantAPercevoir = montant;
    }

}