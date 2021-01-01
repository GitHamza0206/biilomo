package eu.dauphine.Commandes;

import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.RejetException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Personnel.Chef;
import eu.dauphine.Personnel.Ouvrier;
import eu.dauphine.Personnel.Personnel;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Commande {
    private Set<Integer> idCommandeSet = new HashSet<Integer>();
    /**
     *
     */
    private List<Meuble> detailCommande;

    /**
     *
     */
    private boolean isHonore;

    /**
     * represente l'ID unique de la commande
     */
    private int idCommande;

    /**
     *
     */
    private LocalDate dateCommande;




    /**
     * constructor
     */
    public Commande(List<Meuble> detailCommande) throws CloneNotSupportedException, RejetException, ConstructionException {
        if(!idCommandeSet.add(idCommande))
            this.idCommande=idCommande;
        this.detailCommande = detailCommande;
        this.isHonore = false;
        this.dateCommande = LocalDate.now();

    }

    public int getIdCommande() {
        return idCommande;
    }

    public List<Meuble> getDetailCommande() {
        return detailCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public Set<Integer> getIdCommandeSet() {
        return idCommandeSet;
    }
}