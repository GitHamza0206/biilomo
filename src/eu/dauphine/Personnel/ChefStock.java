package eu.dauphine.Personnel;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.GestionStock;
import eu.dauphine.Time.MyTimer;


import java.util.*;

/**
 * 
 */
public class ChefStock extends Chef implements GestionStock {

    int tempsPourFinirLeStockage;
    public static int identifiant=1;


    /**
     * Default constructor
     */
    public ChefStock(Personnel ouvrier1, Personnel ouvrier2 , Personnel ouvrier3 , Personnel ouvrier4) {
        super(ouvrier1,ouvrier2,ouvrier3,ouvrier4);
        super.id=identifiant;
        super.nom = "ChefStock" + identifiant;

        identifiant++;
    }

/*
    @Override
    public Lot retirerLot(Entrepot entrepot, Lot lot) throws StockageException {
        try{
            if(super.Disponible){
                this.tempsPourFinirLeStockage = MyTimer.pasDeTemps + lot.getVolume();
                super.Disponible=false;
                return entrepot.retirerLot(lot);
            } else {
                throw new StockageException("L'ouvrier est indisponible pour retirer le lot " + lot.getNom());
            }
        } catch (Exception e){
            throw new StockageException("On ne peut pas retirer ");
        }
    }*/

    /*@Override
    public void stockerLot(Entrepot entrepot, Lot lot) throws StockageException {
        try {
            if(super.Disponible){
                entrepot.stockerLot(lot);
                this.tempsPourFinirLeStockage = 1;

            } else {
                throw new StockageException("L'ouvrier est indisponible pour stocker le lot " + lot.getNom());
            }

        } catch (Exception e){
            throw new StockageException("On ne peut pas stocker ");
        }
    }*/

    @Override
    public void deplacerLot() {

    }

    @Override
    public void updateTempsPourEtreDispo() {
        if(tempsPourFinirLeStockage==MyTimer.pasDeTemps){
            super.Disponible=true;
        }
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return "ChefStock{" +
                "Nom=" + super.nom +
                ", Disponible=" + super.Disponible +
                ", TempsPourFinirLeStockage=" + tempsPourFinirLeStockage +
                '}';
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
    public void setIndisponible() {
        super.Disponible=false;
    }

    @Override
    public void setMeubleEnCoursDeMontage(Meuble meuble) {
        super.meubleEnCoursDeMontage = meuble;
    }

    @Override
    public Meuble getMeubleEnCoursDeMontage() {
        return super.meubleEnCoursDeMontage;
    }


}