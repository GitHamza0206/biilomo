package eu.dauphine.Personnel;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Time.MyTimer;

import java.util.LinkedList;

/**
 * 
 */
public class ChefBrico extends Chef implements Constructeur {

    int tempsRestantPourFinirLaConstruction;

    public static int identifiant=1;

    public ChefBrico(){
        super();
    }
    /**
     * Default constructor
     */
    public ChefBrico(Personnel ouvrier1, Personnel ouvrier2 , Personnel ouvrier3 , Personnel ouvrier4) {
        super(ouvrier1,ouvrier2,ouvrier3,ouvrier4);
        super.id=identifiant;
        super.nom = "ChefBrico" + identifiant;
        super.Disponible =true;
        identifiant++;
    }

    @Override
    public void monterMeuble(Entrepot entrepot, Meuble meuble) throws CloneNotSupportedException, StockageException, ConstructionException {

        setMeubleEnCoursDeMontage(meuble);
        entrepot.monterLeMeuble(meuble);
        setDisponible(false);
        setTempsRestantPourFinirLaConstruction(meuble.getDureeConstruction());


    }

    @Override
    public void setDisponible(boolean disponible) {
        super.Disponible = disponible;

    }

    @Override
    public void setTempsRestantPourFinirLaConstruction(int tempsRestantPourFinirLaConstruction) {
        this.tempsRestantPourFinirLaConstruction= tempsRestantPourFinirLaConstruction + MyTimer.pasDeTemps;
    }

    @Override
    public boolean getDisponible() {
        return super.Disponible;
    }

    @Override
    public int getTempsRestantPourFinirLaConstruction() {
        return this.tempsRestantPourFinirLaConstruction;
    }

    @Override
    public Meuble getMeubleEnCoursDeMontage() {
        return super.meubleEnCoursDeMontage;
    }

    @Override
    public void setMeubleEnCoursDeMontage(Meuble meuble) {
        super.meubleEnCoursDeMontage = meuble;
    }

    @Override
    public void updateTempsPourEtreDispo() {
        if(tempsRestantPourFinirLaConstruction==MyTimer.pasDeTemps){
            super.Disponible=true;
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

    @Override
    public void setIndisponible() {
        super.Disponible=false;
    }
    /**
     *
     * @return
     */
    @Override
    public void addTachesMonterMeuble(Meuble meuble) {
        super.addTachesMonterMeuble(meuble);
    }
}