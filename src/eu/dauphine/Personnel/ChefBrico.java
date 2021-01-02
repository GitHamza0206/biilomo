package eu.dauphine.Personnel;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Time.Timer;

import java.util.LinkedList;

/**
 * 
 */
public class ChefBrico extends Chef implements Constructeur {
    boolean Disponible = true;
    int tempsRestantPourFinirLaConstruction;
    Meuble meubleEnCoursDeMontage;
    public static int identifiant=1;

    public ChefBrico(){
        super();
    }
    /**
     * Default constructor
     */
    public ChefBrico(Personnel ouvrier1, Personnel ouvrier2 , Personnel ouvrier3 , Personnel ouvrier4) {
        super(ouvrier1,ouvrier2,ouvrier3,ouvrier4);
        super.nom = "ChefBrico" + identifiant;
        identifiant++;
    }

    @Override
    public void monterMeuble(Entrepot entrepot, Meuble meuble) throws CloneNotSupportedException, StockageException {

        setMeubleEnCoursDeMontage(meuble);
        entrepot.monterLeMeuble(meuble);
        setDisponible(false);
        setTempsRestantPourFinirLaConstruction(meuble.getDureeConstruction());


    }




    @Override
    public void setDisponible(boolean disponible) {
        this.Disponible = disponible;

    }

    @Override
    public void setTempsRestantPourFinirLaConstruction(int tempsRestantPourFinirLaConstruction) {
        this.tempsRestantPourFinirLaConstruction= tempsRestantPourFinirLaConstruction + Timer.time;
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
        return meubleEnCoursDeMontage;
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
    public String getNom() {
        return super.nom;
    }

    @Override
    public double getSalaireCummuleAPercevoir() {
        return super.salaireCummuleAPercevoir;
    }


    /**
     *
     * @return
     */


}