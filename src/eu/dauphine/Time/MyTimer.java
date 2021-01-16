package eu.dauphine.Time;

import eu.dauphine.Entrepot.Entrepot;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.StockageException;

public class MyTimer {
    public static  int pasDeTemps ;
    public static void nextPasDeTemps(Entrepot entrepot) throws StockageException, CloneNotSupportedException, ConstructionException {
        pasDeTemps++;
        entrepot.executerTaches();
    }
}
