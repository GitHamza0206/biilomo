package eu.test.Entrepot;

import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Entrepot.Lot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LotTest {
    @Test
    void CompaisonDuLotDansLentrepotEtLotDuMeubleCommande(){
        Lot vis = new Lot(5,"vis",3.0,3.0);

        Lot visCommande = new Lot(5,"vis");

        assertTrue(vis.equals(visCommande));

    }


}