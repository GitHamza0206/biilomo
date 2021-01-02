package eu.dauphine.Personnel;

import eu.dauphine.Commandes.Commande;
import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Inteferaces.GestionStock;

import java.util.*;

/**
 * 
 */
public abstract class Chef extends Personnel {
    protected Personnel ouvrier1;

    /**
     *
     */
    protected Personnel ouvrier2;

    /**
     *
     */
    protected Personnel ouvrier3;

    /**
     *
     */
    protected Personnel ouvrier4;

    protected LinkedList<Personnel> personnelList = new LinkedList<Personnel>();

    public Chef(){
        Ouvrier ouvrier1 = null;
        Ouvrier ouvrier2 = null;
        Ouvrier ouvrier3 = null;
        Ouvrier ouvrier4 = null;

        personnelList.add(ouvrier1);
        personnelList.add(ouvrier2);
        personnelList.add(ouvrier3);
        personnelList.add(ouvrier4);
    }

    /**
     * Default constructor
     */
    public Chef(Personnel ouvrier1, Personnel ouvrier2 , Personnel ouvrier3 , Personnel ouvrier4) {
        this.ouvrier1 = ouvrier1;
        this.ouvrier2 = ouvrier2;
        this.ouvrier3 = ouvrier3;
        this.ouvrier4 = ouvrier4;

        personnelList.add(ouvrier1);
        personnelList.add(ouvrier2);
        personnelList.add(ouvrier3);
        personnelList.add(ouvrier4);

    }

    protected int salaire = 10;
    boolean Disponible;

    /**
     *
     */

    public void recevoirSalaire(){
        double salaireTotal = this.getSalaireCummuleAPercevoir();
        this.setSalaireCummuleAPercevoir(this.salaire + salaireTotal);

        for(int i=0;i<personnelList.size();i++){
            Personnel p = personnelList.get(i);
            if(p != null) {
                if(p instanceof Ouvrier) {
                    Ouvrier ouvrier = (Ouvrier) p;
                    salaireTotal = ouvrier.getSalaireCummuleAPercevoir();
                    ouvrier.setSalaireCummuleAPercevoir(ouvrier.getSalaire() + salaireTotal);
                }


            }
        }

    }

    public Personnel recruterPersonnel(PieceMaison pieceMaison){
        // comme on veut un ouvrier pour stocke, sa spécialité importe peu

        for(int i=0;i<personnelList.size();i++){
            if(personnelList.get(i) == null) {
                Ouvrier ouvrierRecrute = new Ouvrier(pieceMaison);
                personnelList.set(i, ouvrierRecrute);
                System.out.println("Le personnel " + ouvrierRecrute.toString() + " a  été recruté ");

               // break;
                return  ouvrierRecrute;

            }
        }
        return null;
    }


    public void licencierPersonnel(Personnel personnel){
        for(int i=0;i<personnelList.size();i++){
            if(personnelList.get(i) == personnel) {

                personnelList.set(i, null);
                System.out.println("Le personnel " + personnel.toString() + " a  été licencié ");

                break;

            }
        }
    }

    public GestionStock personnelDisponiblePourStockage() throws StockageException {

        if(this instanceof ChefStock && this.Disponible) {
            return (GestionStock) this;
        }
        for(Personnel p : personnelList){
            if(p instanceof Ouvrier && ((Ouvrier) p).Disponible){
                return (GestionStock) p;
            }
        }
        return null;
    }


    public Constructeur personnelDisponiblePourConstruction( PieceMaison pieceMaison) throws ConstructionException {
        if(this instanceof ChefBrico && this.Disponible){
            return (Constructeur) this;
        }
        for(Personnel p : personnelList){
            if(p!=null && p.specialite != null && (p instanceof Ouvrier && ((Ouvrier) p).Disponible
                    &&  p.specialite.equals(pieceMaison))){
                return (Constructeur) p;
            }
        }
        //System.out.println("Il n'y a pas de personnel disponible pour le stockage dans cette equipe");
        //throw new ConstructionException("Il n'ya pas de personnel disponible pour le stockage dans cette equipe");
        //return null;
        //si on trouve pas de peronnel => on recrute
        return (Constructeur)recruterPersonnel(pieceMaison);

    }


    public LinkedList<Personnel> getPersonnelList() {
        return personnelList;
    }

    public double getSalaireCummuleAPercevoir(){
        return super.salaireCummuleAPercevoir;
    }

    public double getSalaire() {
        return salaire;
    }
}