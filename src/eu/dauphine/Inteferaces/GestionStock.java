package eu.dauphine.Inteferaces;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.StockageException;

import java.util.LinkedList;
import java.util.Queue;

public interface GestionStock {


    int tempsPourFinirLeStockage =0;
    void deplacerLot();
    void updateTempsPourEtreDispo();
    public void addTachesRetirerLot(Lot lot);
    void verifierDisponibilite();
    void setIndisponible();
    void setMeubleEnCoursDeMontage(Meuble meuble);
    Meuble getMeubleEnCoursDeMontage();



}
