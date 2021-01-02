package eu.dauphine.Parametrage;

import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Personnel.Chef;
import eu.dauphine.Personnel.Personnel;

import java.util.LinkedList;
import java.util.List;
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

    public static boolean LicencierDesLaFInition;

    public  static void LicencierDesLaFinitionDeLaTache(Personnel p){
        // pour eviter de payer le personnel lorsqu'il est inactif on le vire puis on le ré-engage après



    }
    public static void AffinerGestionPersonnel(Entrepot entrepot){
        // ici on licencie mais il faut voir si il n'y a pas de commande en cours ou en attente
        if(entrepot.getListeEnCours().size()==0 && entrepot.getListeAttente().size()==0){
            /*
            for(Chef chef:entrepot.getChef_equipe()){
                for(Personnel p : chef.getPersonnelList()){
                    if(p != null){
                         chef.licencierPersonnel(p);
                    }
                }

            }*/

            //on licencie tout le personnel
            List<Chef> listChefs = new LinkedList<>();
            entrepot.setChef_equipe(listChefs);
            System.out.println("Tous le personnel à été licencié car il n'y a aucune commande en cours ou en attente ");

        }
    }

}
