package eu.dauphine.Inteferaces;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Personnel.Personnel;
import eu.dauphine.Time.Timer;

import java.util.*;

/**
 * 
 */
public interface Constructeur  {
    boolean Disponible = true;
    int tempsRestantPourFinirLaConstruction = 0;
    Meuble meubleEnCoursDeMontage = null;
    double montantAPercevoir = 0;


    /**
     * @return
     */
    void monterMeuble(Entrepot entrepot , Meuble meuble) throws CloneNotSupportedException, ConstructionException, StockageException;
    void setDisponible(boolean disponible);
    void setTempsRestantPourFinirLaConstruction(int tempsRestantPourFinirLaConstruction);

    boolean getDisponible();
    int getTempsRestantPourFinirLaConstruction();
    Meuble getMeubleEnCoursDeMontage();
    void setMeubleEnCoursDeMontage(Meuble meuble);
    void updateTempsPourEtreDispo();
    double getMontantAPercevoir();
    void setMontantAPercevoir();

}