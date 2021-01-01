package eu.test;

import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.GestionStock;
import eu.dauphine.Personnel.ChefBrico;
import eu.dauphine.Personnel.Chef;
import eu.dauphine.Personnel.Ouvrier;
import eu.dauphine.Personnel.Personnel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChefTest {

    Chef equipe;

    @Test
    void personnelDisponiblePourStockage() throws StockageException {

        Ouvrier ouvrier1 = new Ouvrier(PieceMaison.CHAMBRE);


        ChefBrico chef1 = new ChefBrico(ouvrier1,null,null,null);
        GestionStock personnel = chef1.personnelDisponiblePourStockage();




        assertEquals(personnel, ouvrier1);
    }


}