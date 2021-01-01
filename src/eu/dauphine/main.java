package eu.dauphine;

import eu.dauphine.Commandes.Commande;
import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Entrepot.*;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.RejetException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Personnel.*;
import eu.dauphine.Time.Timer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws RejetException, StockageException, CloneNotSupportedException, ConstructionException {

        Entrepot entrepot = new Entrepot(5,5);

        /*
        Lot lotVis = new Lot( 3, "Vis", 5.4, 3.5);
        Lot lotBoulon = new Lot( 2, "Boulon", 10.0, 21.0);
        Lot lotPlanche = new Lot( 2, "plance", 12.0, 21.0);
        Lot lotCharniere = new Lot( 2, "charniere", 10.0, 21.0);
        Lot lotCheville = new Lot( 2, "cheville", 10.0, 21.0);

        LinkedList<Lot> lotLinkedList = new LinkedList<>();


        LinkedList ListeLotTable = new LinkedList();
        ListeLotTable.add(lotVis);
        ListeLotTable.add(lotPlanche);

        LinkedList ListeLotLit = new LinkedList();
        ListeLotTable.add(lotCharniere);
        ListeLotTable.add(lotCheville);



        Meuble table= new Meuble(ListeLotTable, 3, "Table", PieceMaison.SALLE_A_MANGER);
        Meuble lit = new Meuble(ListeLotLit, 3, "lit", PieceMaison.SALLE_A_MANGER);

        LinkedList<Meuble> listeMeublec1 = new LinkedList<Meuble>();
        listeMeublec1.add(table);
        listeMeublec1.add(lit);

        Ouvrier ouvrier1 = new Ouvrier(PieceMaison.SALON);
        Ouvrier ouvrier2 = new Ouvrier(PieceMaison.SALON);
        Ouvrier ouvrier3 = new Ouvrier(PieceMaison.SALLE_A_MANGER);
        Ouvrier ouvrier4 = new Ouvrier(PieceMaison.CHAMBRE);
        Chef chef = new ChefBrico(ouvrier1,ouvrier2,ouvrier3,ouvrier4);



        LinkedList<Chef> listChef = new LinkedList<Chef>();
        listChef.add(chef);

        entrepot.setChef_equipe(listChef);
        Commande commande2 = new Commande(listeMeublec1);

        */


        Scanner sc = new Scanner(System.in);
        System.out.println("Choisissez la consigne \n 1 : recevoir un nouveau lot \n 2 : nouvelle commande de meuble \n 3 : rien \n q : to quit the program \n ");
        while(sc.hasNextLine() ) {
            switch (sc.nextLine()){
                case ("1"):
                    System.out.println("Creez le lot que l'entrepot va recevoir : \n ");
                    System.out.println("Nom  : \n ");
                    String nom = sc.nextLine();
                    System.out.println("Volume  : \n ");
                    String volumeString = sc.nextLine();
                    int volume = Integer.parseInt(volumeString);
                    System.out.println("poids  : \n ");
                    String poidsString = sc.nextLine();
                    double poids = Double.parseDouble(poidsString);
                    System.out.println("prix  : \n ");
                    String prixString = sc.nextLine();
                    double prix = Double.parseDouble(prixString);

                    try{
                        Lot lotCree = new Lot( volume, nom, poids, prix);
                        entrepot.recevoir(lotCree);
                    } catch (Exception e){
                        System.out.println(e);
                    } finally {

                    }



                    /*
                    entrepot.recevoir(lotVis);

                    entrepot.recevoir(lotBoulon);

                    entrepot.recevoir(lotPlanche);

                    entrepot.recevoir(lotCharniere);

                    entrepot.recevoir(lotCheville);

                     */
                    Timer.time++;
                    break;
                case ("2"):
                    System.out.println("Créez votre Commande \n ");

                    List<Meuble> meubleList = new LinkedList<Meuble>();
                    while(!sc.nextLine().equals("b")){
                        System.out.println("Creez le meuble (Appuiyez sur b pour quitter ) qui va constituer votre commande : \n ");

                        System.out.println("Nom du meuble   : \n ");
                        String nomMeuble = sc.nextLine();

                        System.out.println("Duree de construction    : \n ");
                        int dureeDeConstruction = Integer.parseInt(sc.nextLine());
                        System.out.println("Piece Maison (en majuscule)  : \n ");
                        String pieceMaisonString = sc.nextLine();
                        PieceMaison pieceMaison = PieceMaison.valueOf(pieceMaisonString);


                        Meuble new_meuble = new Meuble();
                        List<Lot> lotList = new LinkedList<>();
                                while(!sc.nextLine().equals("b")){
                                    //il faut créer une liste des lots
                                    System.out.println("Type du lot (Appuiyez sur b pour arreter de renseigner les lots ) : \n ");

                                    String typeDuLot =sc.nextLine();
                                    System.out.println("Volume : \n ");
                                    String volumeStringLot = sc.nextLine();
                                    int volumeLot = Integer.parseInt(volumeStringLot);
                                    Lot lotMeuble = new Lot(volumeLot,typeDuLot);
                                    lotList.add(lotMeuble);
                                }
                        new_meuble.setListeLots(lotList);
                        new_meuble.setDureeConstruction(dureeDeConstruction);
                        new_meuble.setNom(nomMeuble);
                        new_meuble.setPiecemaison(pieceMaison);
                        meubleList.add(new_meuble);
                    }
                    Commande commande = new Commande(meubleList);
                    System.out.println("Votre commande a été créée");

                    entrepot.recevoir(commande);

                    Timer.time++;
                    break;
                case ("3"):
                    System.out.println("Rien, on attends");
                    Timer.time++;
                    break;
                case ("q"):
                    sc.close();

                default:
                    System.out.println("Veuillez choisir une option parmis celles ci-dessus");
                    break;

            }
            System.out.println("Timer : " + Timer.time + " \n l'entrepot est en attente de recevoir une nouvelle consigne : \n");
            entrepot.traiterLesCommandes();
        }


    }
}
