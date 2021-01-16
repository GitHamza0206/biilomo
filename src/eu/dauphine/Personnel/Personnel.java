package eu.dauphine.Personnel;

import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Entrepot.Lot;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 */
public abstract class Personnel {

    /**
     * Default constructor
     */
    public Personnel() {


    }

    /**
     * 
     */
    protected String nom;

    /**
     * 
     */
    protected String prenom;

    /**
     * 
     */
    protected int id;


    protected PieceMaison specialite;


    public double salaireCummuleAPercevoir;

    public boolean Disponible=true;
    Meuble meubleEnCoursDeMontage;


    public double getSalaireCummuleAPercevoir(){
        return salaireCummuleAPercevoir;
    }

    public void setSalaireCummuleAPercevoir(double salaireCummuleAPercevoir) {
        this.salaireCummuleAPercevoir = salaireCummuleAPercevoir;
    }
    public Queue<Meuble> tachesMonterMeuble=new LinkedList<>();
    public Queue<Lot> tachesRetirerLot = new LinkedList<>();

    public void setTachesRetirerLot(Queue<Lot> tachesRetirerLot) {
        this.tachesRetirerLot = tachesRetirerLot;
    }

    public Queue<Lot> getTachesRetirerLot() {
        return tachesRetirerLot;
    }

    public void addTachesRetirerLot(Lot lot) {
        this.tachesRetirerLot.add(lot);
    }

    public void addTachesMonterMeuble(Meuble meuble){
        this.tachesMonterMeuble.add(meuble);
    }


    public void verifierDisponibilite(){
        if(this.tachesRetirerLot.size()==0 && this.tachesMonterMeuble.size()==0)
            this.Disponible=true;
        else
            this.Disponible=false;
    }

    public void setIndisponible(){
        this.Disponible = false;
    }

    public Meuble getMeubleEnCoursDeMontage() {
        return meubleEnCoursDeMontage;
    }

    public void setMeubleEnCoursDeMontage(Meuble meubleEnCoursDeMontage) {
        this.meubleEnCoursDeMontage = meubleEnCoursDeMontage;
    }
}