package eu.dauphine.Entrepot;

import eu.dauphine.Commandes.Commande;
import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Constants.PieceMaison;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.RejetException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Inteferaces.GestionStock;
import eu.dauphine.Inteferaces.Reception;
import eu.dauphine.Parametrage.Strategie;
import eu.dauphine.Personnel.*;
import eu.dauphine.Time.MyTimer;


import java.util.*;

/**
 * 
 */
public class Entrepot implements Reception {


    /**
     * represente le nombre de rangées
     */
    private static int m;

    /**
     * représente la longueur d'une rangée
     */
    private static int n;

    /**
     * 
     */
    private final int largeur = 1;

    /**
     * 
     */
    private final int hauteur = 1;

    /**
     * 
     */
    private List<Chef> chef_equipe;

    /**
     * 
     */
    private double tresorerie;

    private double masseSalariale;

    private double totalSalairesAPayer;


    private Queue<Meuble> listeAttente = new LinkedList<Meuble>();

    private Queue<Meuble> listeEnCours = new LinkedList<Meuble>();


    /**
     * Les commandes seront sous la forme d'une hashMap.
     * Comme clé on aura l'ID unique de la commande de type Integer
     * COmme valeur on aura la Commande de type Commande
     */
    private HashMap<Integer,Commande> commandes;

    private LinkedList<Integer> e = new LinkedList<>();

    private  Lot[][] matriceLot ;

    /**
     * Constructeur
     * @param nbRangees
     * @param longueurRangee
     */
    public Entrepot(int nbRangees, int longueurRangee){
            if(nbRangees>0 && longueurRangee>0){
                this.m = nbRangees;
                this.n = longueurRangee;
                this.matriceLot = new Lot[m][n];

                /*
                // on instancie dans l'entrepot deux équipes par defaut qui ne contienent aucun ouvrier
                Chef chefBrico = new ChefBrico();
                Chef chefStock = new ChefStock();
                this.chef_equipe = new LinkedList<Chef>();
                this.chef_equipe.add(chefBrico);
                this.chef_equipe.add(chefStock);
                */

            }
        }

    /**
     * calcul de la masse salariale, pour chaque membre du personnel, lui ajoute le salaire a chaque pas d'iteration
      */
    private void calculeMasseSalariale(){
        double totalSalaires = 0;
        double masseSalariale = 0;

        for(Chef chef: this.chef_equipe){
            masseSalariale += chef.getSalaire();
            totalSalaires += chef.getSalaireCummuleAPercevoir();
            for (Personnel p : chef.getPersonnelList()){
                if(p !=null) {
                    if(p instanceof Ouvrier) {
                        Ouvrier ouvrier = (Ouvrier) p;
                        totalSalaires += ouvrier.getSalaireCummuleAPercevoir();
                        masseSalariale += ouvrier.getSalaire();
                    }
                }
            }
        }
        this.totalSalairesAPayer = this.totalSalairesAPayer + masseSalariale;
        this.masseSalariale = masseSalariale;

    }

    /**
     * Chaque personnel recoit son salaire, qui est ajouté au salaire déjà percu
     */
    private void payerLePersonnel(){
        if(this.chef_equipe!=null){
            for(Chef chef: this.chef_equipe){
                chef.recevoirSalaire();
            }
            calculeMasseSalariale();
        }
        System.out.println("Le personnel à été payé, la masse salariale est " + this.masseSalariale + " EURO, le total des salaire est " + this.totalSalairesAPayer + " EURO" );
    }

    /**
     * elle traite toutes les taches que l'entrepot doit executer, elles sont affectés a chaque membre du personnel
     * @throws StockageException
     * @throws CloneNotSupportedException
     * @throws ConstructionException
     */
    public void executerTaches() throws StockageException, CloneNotSupportedException, ConstructionException {
        // on retire les lots qui correspondent à des meubles commandés pour chaque iteration
        for(Chef chef:chef_equipe){
            if(chef.tachesRetirerLot!=null && chef.tachesRetirerLot.size()>0)
                chef.tachesRetirerLot.poll();
                if(chef.getMeubleEnCoursDeMontage() != null && chef.tachesRetirerLot.size()==0 && chef.getMeubleEnCoursDeMontage().getPersonnelMonteurDuMeuble()==null){
                    monterLeMeuble(chef.getMeubleEnCoursDeMontage());

                }

            for(Personnel personnel: chef.getPersonnelList()){
                if(personnel!=null && personnel.tachesRetirerLot != null && personnel.tachesRetirerLot.size()>0)
                    personnel.tachesRetirerLot.poll();
                if(personnel!=null && personnel.getMeubleEnCoursDeMontage()!=null && personnel.tachesRetirerLot.size()==0 && personnel.getMeubleEnCoursDeMontage().getPersonnelMonteurDuMeuble()==null){
                    monterLeMeuble(personnel.getMeubleEnCoursDeMontage());

                }
            }
        }
        // une fois les lots retirés, on se charge de monter le meuble
        Meuble meuble;
        for(Chef chef:chef_equipe){
            if(chef.tachesMonterMeuble!=null && chef.tachesMonterMeuble.size()>0) {

                meuble = chef.tachesMonterMeuble.poll();
                if(chef.tachesMonterMeuble.size()==0){
                    double prixDuMeuble = 0;
                    for(Lot lot:meuble.getListeLotReserve()){


                        for(int i=0;i<lot.getVolume();i++){

                            double prixDuLot = lot.getPrix();
                            lot.setPrix(prixDuLot);
                            prixDuMeuble += prixDuLot;

                        }
                    }
                    meuble.setCoutDeRevient(prixDuMeuble);

                    tresorerie+=prixDuMeuble;

                    System.out.println("Le meuble " + meuble.getNom() + " a bien été construit, sont coût de revient est " + meuble.getCoutDeRevient() + " EURO");

                }
            }

            for(Personnel personnel: chef.getPersonnelList()){
                if(personnel!=null && personnel.tachesMonterMeuble != null && personnel.tachesMonterMeuble.size()>0)
                {
                    meuble = personnel.tachesMonterMeuble.poll();
                    if(personnel.tachesMonterMeuble.size()==0){
                        double prixDuMeuble = 0;
                        for(Lot lot:meuble.getListeLotReserve()){


                            for(int i=0;i<lot.getVolume();i++){

                                double prixDuLot = lot.getPrix();
                                lot.setPrix(prixDuLot);
                                prixDuMeuble += prixDuLot;

                            }
                        }
                        meuble.setCoutDeRevient(prixDuMeuble);

                        tresorerie+=prixDuMeuble;

                        System.out.println("Le meuble " + meuble.getNom() + " a bien été construit, sont coût de revient est " + meuble.getCoutDeRevient() + " EURO");

                    }

                }
            }
        }

        //on paye le personnel a chaque iteration
        payerLePersonnel();
    }

    /**
     * une fois la commande accepte on doit commencer le montage du meuble a commencer par retirer les lots correspondants
     * @param meuble
     * @throws StockageException
     * @throws CloneNotSupportedException
     */
    public void traiter(Meuble meuble) throws StockageException, CloneNotSupportedException {
        for(Lot lot: meuble.getListeLots()){
            retirerLot(lot,meuble);
        }
    }

    /**
     * montage du meuble
     * @param meuble
     * @throws CloneNotSupportedException
     * @throws StockageException
     * @throws ConstructionException
     */
    public void monterLeMeuble(Meuble meuble) throws CloneNotSupportedException, StockageException, ConstructionException {

        Constructeur personnel = null;
        // on parcours les chefs (qui correspondent a des équipes)
        for(Chef chef: chef_equipe){
            // on recupere un personnel qualifie et disponible pour la construction
            personnel = chef.personnelDisponiblePourConstruction(meuble.getPiecemaison());
            // on affecte a un personnel le meuble a construire
            personnel.setMeubleEnCoursDeMontage(meuble);
            meuble.setPersonnelMonteurDuMeuble(personnel);
            if (personnel!=null)
                break;
        }

        if(personnel!=null){
            // on ajoute le meuble comme tache dans la liste liste de taches pour chaque personnel
            // afin de finir la construction apres le nombre correspondant de pas de temps
            for(int i=0;i<meuble.getDureeConstruction();i++){
                personnel.addTachesMonterMeuble(meuble);
            }
            System.out.println("Le meuble " + meuble.getNom() + " est en cours de montage, il lui faut " + meuble.getDureeConstruction() + " pas de temps, il sera monté par " + personnel.getNom());

        }




    }


    public void stockerLot(Lot lot) throws StockageException {

        try {
            // on vérifie qu'il y'a une personne capable de stocker de disponible sinon on la recrute


            recruterSiBesoinPourStockage(null);


            final int volumeLot = lot.getVolume();
            final int[] result = DernierIndexCaseContigue(volumeLot);
            final int i = result[0];
            final int j = result[1];



            int index = j-volumeLot+1;
            while(index<=j){
                Lot newLot = lot.clone();
                newLot.setVolume(1);
                newLot.setId(lot.getId());
                matriceLot[i][index] = newLot;
                index++;
            }

            System.out.println("Le lot " + lot.toString() + " a bien été crée et stocké " );


        } catch (Exception e){
            System.out.println("Impossible de stocker le lot ");
        }

    }

    public Lot retirerLot(Lot lot, Meuble meuble) throws CloneNotSupportedException, StockageException {
        GestionStock personnel = null;
        for(Chef chef: chef_equipe){
            personnel = chef.personnelDisponiblePourStockage();
            if (personnel!=null)
                break;
        }

        if(personnel!=null){
            personnel.setMeubleEnCoursDeMontage(meuble);
            int volumeAretirer = lot.getVolume();
            Lot newLot = lot.clone();
            newLot.setVolume(1);
            newLot.setId(lot.getId());
            Lot lotTrouve = null;
            int lotId=0;

            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    if(matriceLot[i][j]!=null){
                        if(matriceLot[i][j].equals(newLot)){
                            lotTrouve = matriceLot[i][j];
                            matriceLot[i][j]=null;
                            if(lotId != lotTrouve.getId()){
                                personnel.addTachesRetirerLot(lotTrouve);
                                volumeAretirer--;
                                lotId = lotTrouve.getId();
                            }
                        }
                    }

                    if(volumeAretirer==0){
                       return lotTrouve;
                    }
                }
            }
        }
        return null;
    }

    private void recruterSiBesoinPourStockage(Meuble meuble) throws StockageException {
        //L'entrepot doit avoir un chef de stock avec un max de 4 ouvriers.
        //soit on recrute un ouvrier et on l'affecte à un chef ou bien on recrute un chefshock

        if(chef_equipe==null){
            List<Chef> listChefs = new LinkedList<>();
            ChefStock chefStock = new ChefStock(null,null,null,null);
            listChefs.add(chefStock);

            this.setChef_equipe(listChefs);
            System.out.println("Un chef stock à été recruté " + chefStock.toString());
        }else{
            for(Chef chef: chef_equipe){
                if (chef  instanceof ChefStock){
                   if(chef.isDisponible()){
                      break;
                   }
                   else{
                       if(chef.getPersonnelList() !=null && chef.getPersonnelList().size()<4){
                           PieceMaison pieceMaison;
                           if(meuble!=null)
                               pieceMaison = meuble.getPiecemaison();
                           else
                               pieceMaison = Strategie.SpecialiseAleatoire();

                           chef.recruterPersonnel(pieceMaison);
                       }
                       else{
                           List<Chef> listChefs = this.chef_equipe;
                           ChefStock chefStock = new ChefStock(null,null,null,null);
                           listChefs.add(chefStock);
                           this.setChef_equipe(listChefs);
                           System.out.println("Un chef stock à été recruté " + chefStock.toString());
                           break;
                       }
                   }
                }
                else{
                    List<Chef> listChefs = new LinkedList<>();
                    ChefStock chefStock = new ChefStock(null,null,null,null);
                    listChefs.add(chefStock);

                    this.setChef_equipe(listChefs);
                    System.out.println("Un chef stock à été recruté " + chefStock.toString());
                }
            }
        }






    }


    /**
     * fonction qui rajoute la commande recue dans une liste de commande propre à l'entrepot
     * @return
     */
    @Override
    public void recevoir(Object o) throws RejetException, StockageException, CloneNotSupportedException, ConstructionException {
        if(o!=null){
            // l'entrepot peut recevoir des commandes
            if(o instanceof Commande){
                Commande c = (Commande) o;
                try{
                    afficherDetailCommande((Commande) o);

                    for(Meuble meuble:c.getDetailCommande()) {
                        for (Lot lot : meuble.getListeLots()) {
                            /*if (!lot.isDisponible(this)) {
                                rejeter(c);
                            }*/
                             if (!reserverLotPourLeMeuble(lot,meuble)) {
                                rejeter(c);
                            }
                     }

                    }
                    System.out.println("Votre commande A été acceptée");
                    ficheDeStock();

                    for(Meuble meuble:c.getDetailCommande()){
                       // mettreEnAttente(meuble);
                        traiter(meuble);
                    }

                    //traiterLesCommandes();

                    }
                catch (Exception e) {
                    System.out.println(e.getMessage());

                }


            }
            // il peut aussi recevoir des lots
            else if (o instanceof Lot) {
                Lot l = (Lot) o;
                stockerLot(l);
            } else{

                rejeter(o);
            }
        }else {
            rejeter(o);
        }
    }

    /**
     * on affiche la commande en detail  sur la console
     * @param commande
     */
    private void afficherDetailCommande(Commande commande){
        System.out.println("---DETAIL DE LA COMMANDE---");
        String detail="";
        for(Meuble meuble:commande.getDetailCommande()){
            detail+=meuble.getNom() + ", (";
            for(Lot lot:meuble.getListeLots()){
                detail+=lot.getNom()+":"+lot.getVolume() + ", ";
            }
            detail+=")";
            System.out.println(detail);
            detail="";
        }
    }

    /**
     * on rejete soit une commande soit des lots
     * @param o
     * @throws RejetException
     */
    @Override
    public void rejeter(Object o) throws RejetException {
        if(o instanceof Commande){
            if((Commande) o!= null) {

                throw new RejetException("La commande ne peut être acceptée");
            } else {

                throw new RejetException("La commande est vide");
            }
        } else if (o instanceof Lot){
            if((Lot) o!= null){

                throw new RejetException("Il n'ya pas assez de place pour stocker le lot");

            }else{
                throw new RejetException("Le lot ne contient pas de piece");
            }
        } else {
            throw new RejetException("L'entrepot n'accepte que les Commandes ou les Lots");
        }

    }

    @Override
    public void livrer(Object o) {

    }

    /**
     * fonction qui permet d'obtenir les cases contigues qui permetteront de stocker les lots dans l'entrepot
     * @param volume
     * @return
     * @throws StockageException
     */
    public int[] DernierIndexCaseContigue(int volume) throws StockageException {
        int i = 0;
        int j=0;
        int caseVideSuccessives = 0;
        while(i<m){
            while(j<n){
                if(matriceLot[i][j]!=null){
                    caseVideSuccessives=0;
                } else {
                    caseVideSuccessives+=1;

                }
                if(caseVideSuccessives==volume){
                    return new int[]{i, j};
                }
                j+=1;
            }
            caseVideSuccessives=0;
            j=0;
            i+=1;

        }
        throw new StockageException("Il n'ya pas de place pour stocker le lot ");
    }

    public Lot[][] getMatriceLot() {
        return matriceLot;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(largeur, hauteur, chef_equipe, tresorerie, commandes);
        result = 31 * result + Arrays.hashCode(matriceLot);
        return result;
    }

    @Override
    public String toString() {

        return "Entrepot{" +
                "m=" + m +
                ", n=" + n +
                ", largeur=" + largeur +
                ", hauteur=" + hauteur +
                ", chef_equipe=" + chef_equipe +
                ", tresorerie=" + tresorerie +


                '}';


    }

    /**
     * verifie la disponibilité d'un lot dans le stock
     * @param lot
     * @return
     * @throws CloneNotSupportedException
     */
    public boolean isDisponible(Lot lot) throws CloneNotSupportedException {
        int volumeAVerifier = lot.getVolume();
        Lot newLot = lot.clone();
        newLot.setVolume(1);
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matriceLot[i][j]!=null){
                    Lot lotTrouve = matriceLot[i][j];
                    if(lotTrouve.equals(newLot) && !lotTrouve.getReserver()){

                        volumeAVerifier--;
                        lotTrouve.setReserver(true);
                    }
                }
                if(volumeAVerifier==0){
                    return true;
                }
            }
        }
        return  false;
    }

    public boolean reserverLotPourLeMeuble(Lot lot, Meuble meuble) throws CloneNotSupportedException {
        List<Lot> listeLotReserve = meuble.getListeLotReserve();
        int volumeAVerifier = lot.getVolume();
        Lot newLot = lot.clone();
        newLot.setVolume(1);
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matriceLot[i][j]!=null){
                    Lot lotTrouve = matriceLot[i][j];
                    if(lotTrouve.equals(newLot) && !lotTrouve.getReserver()){

                        volumeAVerifier--;
                        lotTrouve.setReserver(true);
                        listeLotReserve.add(lotTrouve);
                    }
                }
                if(volumeAVerifier==0){
                    meuble.setListeLotReserve(listeLotReserve);
                    return true;
                }
            }
        }
        return  false;
    }


    public List<Chef> getChef_equipe() {
        return chef_equipe;
    }

    public void setChef_equipe(List<Chef> chef_equipe) {
        this.chef_equipe = chef_equipe;
    }


    public double getTresorerie() {
        return tresorerie;
    }

    public double getMasseSalariale() {
        return masseSalariale;
    }

    public double getTotalSalaireAPayer() {
        return totalSalairesAPayer;
    }

    public Queue<Meuble> getListeAttente() {
        return listeAttente;
    }

    public Queue<Meuble> getListeEnCours() {
        return listeEnCours;
    }

    public void ficheDeStock(){
        System.out.println("---FICHE DE STOCK---");
        String rangee="";
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                Lot lot = matriceLot[i][j];
                if(lot!=null)
                    rangee += lot.getNom()+" id:"+lot.getId()+ "  |  ";
                else
                    rangee += "vide  |  ";
            }
            System.out.println(rangee);
            rangee="";
        }
        System.out.println("----------------");
    }
}