package eu.dauphine.Inteferaces;

import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.StockageException;

public interface GestionStock {

    int TempsPourFinirLeStockage=0;
    boolean Disponible = true;

    void retirerLot(Entrepot entrepot, Lot lot) throws StockageException;
    void stockerLot(Entrepot entrepot, Lot lot) throws StockageException;
    void deplacerLot();



}
