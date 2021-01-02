package eu.test.Entrepot;

import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.RejetException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Personnel.Chef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.jupiter.api.Assertions.*;

class LotTest {
    @Test
    void CompaisonDuLotDansLentrepotEtLotDuMeubleCommande(){
        Lot vis = new Lot(5,"vis",3.0,3.0);

        Lot visCommande = new Lot(5,"vis");

        assertTrue(vis.equals(visCommande));

    }

    @Test
    void creerDesLotEtVerifierLeRecrutemenDuPersonnel() throws StockageException, RejetException, ConstructionException, CloneNotSupportedException {
        Entrepot entrepot = new Entrepot(5,5);
        Lot vis = new Lot(3,"vis",3.0,3.0);
        List<Chef> chefs = entrepot.getChef_equipe();
        entrepot.recevoir(vis);

        double valMasse=0;
        double total=0;
        valMasse = entrepot.getMasseSalariale();
        total= entrepot.getTotalSalaireAPayer();
        chefs = entrepot.getChef_equipe();

        double valMasse2=0;
        double total2=0;
        Lot vis2 = new Lot(3,"vis",3.0,3.0);
        entrepot.recevoir(vis2);
        valMasse2 = entrepot.getMasseSalariale();
        total2= entrepot.getTotalSalaireAPayer();
        chefs = entrepot.getChef_equipe();

        double valMasse3=0;
        double total3=0;
        Lot vis3 = new Lot(3,"vis",3.0,3.0);
        entrepot.recevoir(vis3);
        valMasse3 = entrepot.getMasseSalariale();
        total3= entrepot.getTotalSalaireAPayer();
        chefs = entrepot.getChef_equipe();


        assertEquals(entrepot.getTotalSalaireAPayer(),75);


    }

}