package eu.dauphine.Personnel;

import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.GestionStock;

import java.util.*;

/**
 * 
 */
public class ChefStock extends Chef implements GestionStock {
    boolean Disponible;
    int TempsPourFinirLeStockage;

    public ChefStock(){
        super();
    }

    /**
     * Default constructor
     */
    public ChefStock(Personnel ouvrier1, Personnel ouvrier2 , Personnel ouvrier3 , Personnel ouvrier4) {
        super(ouvrier1,ouvrier2,ouvrier3,ouvrier4);
    }


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

    @Override
    public void deplacerLot() {

    }

    /**
     * 
     */




}