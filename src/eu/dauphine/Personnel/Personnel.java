package eu.dauphine.Personnel;

import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Inteferaces.GestionStock;
import eu.dauphine.Time.Timer;

import java.util.*;

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

    public double getSalaireCummuleAPercevoir(){
        return salaireCummuleAPercevoir;
    }

    public void setSalaireCummuleAPercevoir(double salaireCummuleAPercevoir) {
        this.salaireCummuleAPercevoir = salaireCummuleAPercevoir;
    }


}