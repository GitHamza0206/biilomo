package eu.test.Entrepot;

import eu.dauphine.Commandes.Commande;
import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.RejetException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Personnel.*;
import eu.dauphine.Time.MyTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntrepotTest {
    Entrepot entrepot;
    @BeforeEach
    void initEntrepot(){

        entrepot = new Entrepot(5,5);


    }


    @Test
    void InvalideObjectOuNullThrowsRejectException() throws RejetException {
        Commande nouvelleCommande = null;
        entrepot.rejeter(nouvelleCommande);

    }

    @Test
    void ReceptionObjectValidAjouteObjectDansListeAppropriee() throws RejetException{


        //assert(nouvelleCommande, entrepot.getCommandes(4));

    }

    @Test
    void YatildeLaPlacePourLotReturnLaDerniereCaseAPartirDeLaquelleOnStock() throws StockageException {
        //Entrepot entrepot = new Entrepot(5,5);

        Lot lotVis = new Lot(1, "Vis", 5.4, 3.5);
        Lot lotBoulon = new Lot( 1, "Boulon", 10.0, 21.0);
        Lot lotPlanche = new Lot( 1, "plance", 12.0, 21.0);

        //entrepot.setMatriceLot(0,0,lotBoulon);
        //entrepot.setMatriceLot(0,1,lotVis);
        //entrepot.setMatriceLot(0,2,lotPlanche);
        //entrepot.setMatriceLot(0,4,lotPlanche);

        int[] result = entrepot.DernierIndexCaseContigue(2);

        assertEquals(new int[] {3,4},result);

    }

    @Test
    void StockerLotDoitAjouterLeLotDansLaMatrice() throws StockageException, RejetException, CloneNotSupportedException, ConstructionException {
        Lot lotVis = new Lot( 4, "Vis", 5.4, 3.5);
        Lot lotBoulon = new Lot( 1, "Boulon", 10.0, 21.0);
        Lot lotVis2 = new Lot(3, "Vis", 5.4, 3.5);
        Lot lotBois = new Lot(5, "Bois", 5.4, 3.5);

        entrepot.recevoir(lotBoulon);

        Lot premiereCase = entrepot.getMatriceLot()[0][0];

        assertEquals(premiereCase, lotBoulon );
    }

    @Test
    void RecevoirLaCommandeDeTableDoitEtreAccepteEtHonoree() throws ConstructionException, CloneNotSupportedException, RejetException, StockageException {
        Lot lotVis = new Lot( 4, "vis", 5.4, 3.5);

        entrepot.recevoir(lotVis);

        Lot visCommande = new Lot(3,"vis");
        List<Lot> lotList = new LinkedList<>();
        lotList.add(visCommande);
        Meuble m = new Meuble(lotList,2,"table", PieceMaison.CUISINE);
        List<Meuble> listmeuble = new LinkedList<>();
        listmeuble.add(m);
        Commande c = new Commande(listmeuble);
        entrepot.recevoir(c);
    }


    @Test
    void calculMasseSalariale() throws StockageException {
        Ouvrier ouvrier1 = new Ouvrier(PieceMaison.CUISINE);
        Ouvrier ouvrierStock1 = new Ouvrier(null);

        ChefBrico chefBrico1 = new ChefBrico(ouvrier1,null,null,null);
        ChefStock chefStock1 = new ChefStock(ouvrierStock1,null,null,null);

        List<Chef> listChefs = new LinkedList<>();
        listChefs.add(chefBrico1);
        listChefs.add(chefStock1);

        entrepot.setChef_equipe(listChefs);

        for(int i=0;i<2;i++){
            for(Chef chef: entrepot.getChef_equipe()){
                chef.recevoirSalaire();
            }
        }


        assertEquals(entrepot.getMasseSalariale(), 30);
        assertEquals(entrepot.getTotalSalaireAPayer(), 60);

    }

    @Test
    void calculLeCoutDeRevient() throws StockageException, RejetException, ConstructionException, CloneNotSupportedException {
        List<Chef> chefs = entrepot.getChef_equipe();
        Entrepot entrepot = new Entrepot(5,5);

        Lot vis = new Lot(3,"vis",3.0,3.0);
        entrepot.recevoir(vis);

        Lot vis2 = new Lot(3,"vis",3.0,100.0);
        entrepot.recevoir(vis2);

        Lot lotTable = new Lot(4,"vis");
        List<Lot> listLotMeubleTable = new LinkedList()   ;
        listLotMeubleTable.add(lotTable);
        Meuble meubleTable = new Meuble(listLotMeubleTable,3,"table",PieceMaison.CUISINE)     ;

        List<Meuble> listMeube = new LinkedList<>();
        listMeube.add((meubleTable));

        Commande commande = new Commande(listMeube);
        entrepot.recevoir(commande);

        assertEquals(entrepot.getTresorerie(),109);

    }

    @Test
    void verifierSiLicencimentEnCasDeFinDeCommande() throws StockageException, RejetException, ConstructionException, CloneNotSupportedException {

        Entrepot entrepot = new Entrepot(5,5);

        Ouvrier ouvrier1 = new Ouvrier(PieceMaison.CUISINE);
        Ouvrier ouvrierStock1 = new Ouvrier(null);

        ChefBrico chefBrico1 = new ChefBrico(ouvrier1,null,null,null);
        ChefStock chefStock1 = new ChefStock(ouvrierStock1,null,null,null);

        List<Chef> listChefs = new LinkedList<>();
        listChefs.add(chefBrico1);
        listChefs.add(chefStock1);

        entrepot.setChef_equipe(listChefs);

        Lot vis = new Lot(3,"vis",3.0,3.0);
        entrepot.recevoir(vis);

        Lot vis2 = new Lot(3,"vis",3.0,100.0);
        entrepot.recevoir(vis2);

        Lot lotTable = new Lot(4,"vis");
        List<Lot> listLotMeubleTable = new LinkedList()   ;
        listLotMeubleTable.add(lotTable);
        Meuble meubleTable = new Meuble(listLotMeubleTable,3,"table",PieceMaison.CUISINE)     ;

        List<Meuble> listMeube = new LinkedList<>();
        listMeube.add((meubleTable));

        Commande commande = new Commande(listMeube);
        entrepot.recevoir(commande);

        assertEquals(entrepot.getChef_equipe().size(),2);

    }

    @Test
    void verifierSiLeRectrutementEstCorrect() throws StockageException, RejetException, ConstructionException, CloneNotSupportedException {

        Entrepot entrepot = new Entrepot(2,8);

        Lot vis1 = new Lot(2,"vis",3.0,3.0);
        Lot vis2 = new Lot(2,"vis",3.0,23.0);
        Lot vis3 = new Lot(1,"vis",3.0,30.0);
        Lot boulon1 = new Lot(5,"boulon",3.0,100.0);
        Lot planche1 = new Lot(2,"planche",3.0,100.0);
        Lot boulon2 = new Lot(1,"boulon",3.0,100.0);
        Lot boulon3 = new Lot(1,"boulon",3.0,100.0);
        Lot planche2 = new Lot(1,"planche",3.0,100.0);
        Lot charnier1 = new Lot(1,"charniere",3.0,100.0);




        Lot lotvis = new Lot(4,"vis");
        Lot lotplanche = new Lot(3,"planche");
        Lot lotvis2 = new Lot(1,"vis");
        Lot lotplanche2 = new Lot(3,"planche");
        Lot lotpboulon2 = new Lot(5,"boulon");

        List<Lot> listLotMeubleTable = new LinkedList()   ;
        List<Lot> listLotMeubleSDB = new LinkedList()   ;


        listLotMeubleTable.add(lotvis);
        listLotMeubleTable.add(lotplanche);

        listLotMeubleSDB.add(lotvis2);
        listLotMeubleSDB.add(lotpboulon2);

        Meuble meubleTable = new Meuble(listLotMeubleTable,1,"table",PieceMaison.CUISINE)     ;
        Meuble meubleSDB = new Meuble(listLotMeubleSDB,1,"sdb",PieceMaison.SALLE_DE_BAIN)     ;

        List<Meuble> listMeube = new LinkedList<>();
        listMeube.add(meubleTable);
        listMeube.add(meubleSDB);


        entrepot.recevoir(vis1);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.recevoir(vis2);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.recevoir(boulon1);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.recevoir(planche1);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.recevoir(vis3);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.recevoir(boulon2);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.recevoir(boulon3);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.recevoir(planche2);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.recevoir(charnier1);
        MyTimer.nextPasDeTemps(entrepot);

        entrepot.ficheDeStock();





        /*entrepot.retirerLot(lotvis,meubleTable);
        MyTimer.nextPasDeTemps(entrepot);
        entrepot.retirerLot(lotplanche,meubleTable);
        MyTimer.nextPasDeTemps(entrepot);
        MyTimer.nextPasDeTemps(entrepot);
        MyTimer.nextPasDeTemps(entrepot);
        MyTimer.nextPasDeTemps(entrepot);
        MyTimer.nextPasDeTemps(entrepot);

        entrepot.ficheDeStock();*/




        Commande commande = new Commande(listMeube);

        entrepot.recevoir(commande);
        MyTimer.nextPasDeTemps(entrepot);
        MyTimer.nextPasDeTemps(entrepot);
        MyTimer.nextPasDeTemps(entrepot);


        assertEquals(entrepot.getChef_equipe().size(),2);

    }

    @Test
    void verifierDonneeConsole() throws StockageException, RejetException, ConstructionException, CloneNotSupportedException {
        Entrepot entrepot = new Entrepot(2,8);

        Lot vis1 = new Lot(3,"vis",3.0,3.0);
        entrepot.recevoir(vis1);
        MyTimer.nextPasDeTemps(entrepot);



        List<Lot> listLotMeubleTable = new LinkedList()   ;

        Lot lotvis = new Lot(1,"vis");
        listLotMeubleTable.add(lotvis);


        Meuble meubleTable = new Meuble(listLotMeubleTable,3,"table",PieceMaison.CUISINE)     ;

        List<Meuble> listMeube = new LinkedList<>();
        listMeube.add(meubleTable);


        Commande commande = new Commande(listMeube);
        entrepot.recevoir(commande);
        MyTimer.nextPasDeTemps(entrepot);
        MyTimer.nextPasDeTemps(entrepot);
        MyTimer.nextPasDeTemps(entrepot);

    }


    }