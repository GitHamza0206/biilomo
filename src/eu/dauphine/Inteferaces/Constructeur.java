package eu.dauphine.Inteferaces;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.StockageException;

/**
 * 
 */
public interface Constructeur  {
    boolean Disponible = true;
    int tempsRestantPourFinirLaConstruction = 0;
    Meuble meubleEnCoursDeMontage = null;
    boolean getDisponible();
    double salaire=0;
    String nom="";
    double salaireCummuleAPercevoir=0;

    /**
     * @return
     */
    void monterMeuble(Entrepot entrepot , Meuble meuble) throws CloneNotSupportedException, ConstructionException, StockageException;
    void setDisponible(boolean disponible);
    void setTempsRestantPourFinirLaConstruction(int tempsRestantPourFinirLaConstruction);


    int getTempsRestantPourFinirLaConstruction();
    Meuble getMeubleEnCoursDeMontage();
    void setMeubleEnCoursDeMontage(Meuble meuble);
    void updateTempsPourEtreDispo();
    double getSalaire();
    String getNom();
    double getSalaireCummuleAPercevoir();
    void setIndisponible();
    public void addTachesMonterMeuble(Meuble meuble);
    void verifierDisponibilite();
}