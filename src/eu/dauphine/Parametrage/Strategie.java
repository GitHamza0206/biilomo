package eu.dauphine.Parametrage;

import eu.dauphine.Constants.PieceMaison;

import java.util.LinkedList;
import java.util.Random;

public class Strategie {
    public static PieceMaison recrutemenOuvrierSpecialiseAleatoire(){
        LinkedList<PieceMaison> list = new LinkedList<PieceMaison>();
        for(PieceMaison p : PieceMaison.values()){
            list.add(p);
        }
        Random rd = new Random();
        int randomSpecialite = rd.nextInt(list.size());
        PieceMaison specialite  = list.get(randomSpecialite);
        return  specialite;
    }
}
