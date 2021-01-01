package eu.dauphine.Inteferaces;

import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.RejetException;
import eu.dauphine.Exceptions.StockageException;

public interface Reception {

    void recevoir(Object o) throws RejetException, StockageException, CloneNotSupportedException, ConstructionException;
    void rejeter(Object o) throws RejetException;
    void livrer(Object o) ;


}
