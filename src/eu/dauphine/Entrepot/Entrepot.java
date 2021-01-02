package eu.dauphine.Entrepot;

import eu.dauphine.Commandes.Commande;
import eu.dauphine.Commandes.Meuble;
import eu.dauphine.Exceptions.ConstructionException;
import eu.dauphine.Exceptions.RejetException;
import eu.dauphine.Exceptions.StockageException;
import eu.dauphine.Inteferaces.Constructeur;
import eu.dauphine.Inteferaces.GestionStock;
import eu.dauphine.Inteferaces.Reception;
import eu.dauphine.Parametrage.Strategie;
import eu.dauphine.Personnel.*;
import eu.dauphine.Time.Timer;

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

    /**
     *
     */
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

    private void payerLePersonnel(){
        if(this.chef_equipe!=null){
            for(Chef chef: this.chef_equipe){
                chef.recevoirSalaire();
            }
            calculeMasseSalariale();
        }

    }
    public void traiterLesCommandes() throws ConstructionException, CloneNotSupportedException, StockageException {


        //on traite les commandes en cours pour vérifier si un meuble à été terminé
        for(Meuble meuble:this.listeEnCours){

            if(meuble.getPersonnelMonteurDuMeuble().getTempsRestantPourFinirLaConstruction() == Timer.time){

                Constructeur p = meuble.getPersonnelMonteurDuMeuble();

                System.out.println("Le meuble " + meuble.getNom() + " a bien été construit, sont coût de revient est " + meuble.getCoutDeRevient() + " EURO");
                System.out.println("Le personnel "+ p.getNom() + " dont le salaire est " + p.getSalaire() + " qui a construit le " + meuble.getNom() + " en " + meuble.getDureeConstruction() + " pas de temps, a accumulé un total de " + p.getSalaireCummuleAPercevoir() + " EURO");



                meuble.getPersonnelMonteurDuMeuble().setTempsRestantPourFinirLaConstruction(0);
                meuble.getPersonnelMonteurDuMeuble().setDisponible(true);
                meuble.setPersonnelMonteurDuMeuble(null);
                listeEnCours.poll();

            }
            else
            {
                int tempsRestant = meuble.getPersonnelMonteurDuMeuble().getTempsRestantPourFinirLaConstruction() - Timer.time;
                System.out.println("Le meuble " + meuble.getNom() + " est encore en cours de construction, il lui reste  " + tempsRestant + " pas de temps pour être terminé ");
            }
        }

        //on traite la file d'attente en cas d'indisponibilité d'un personnel
        for(Meuble meuble: this.listeAttente) {
            for(Chef chef: this.chef_equipe) {

                Constructeur monteurDuMeuble = chef.personnelDisponiblePourConstruction(meuble.getPiecemaison());
                //Constructeur monteurDuMeuble =  chef.personnelQualifiePourConstruction(meuble.getPiecemaison());

                if (monteurDuMeuble != null) {

                    if (meuble.getPersonnelMonteurDuMeuble() == null){
                        monteurDuMeuble.monterMeuble(this, meuble);
                        listeAttente.poll();
                        listeEnCours.add(meuble);
                        meuble.setPersonnelMonteurDuMeuble(monteurDuMeuble);

                        System.out.println("Le meuble " + meuble.getNom() + " à débuté la construction , il sera finalisé dans " + meuble.getDureeConstruction() + " pas de temps ");

                    }
                    // else
                    //{
                    //   System.out.println("ERREUR ! Le peronnel choisi pour monter le meuble " + meuble.getNom() + " ne peut pas monter ce meuble, car il est déja occupé");

                    //}

                } else {
                    System.out.println("Il ny a pas de personnel disponible pour la construction du meuble " + meuble.getNom() + " nous devons attendre " + meuble.getDureeConstruction() + " Pas de temps ");
                }

            }

        }

        //on met à jours les données des equipes
        if(this.chef_equipe !=null){
            for(Chef chef: this.chef_equipe){
                for (Personnel p : chef.getPersonnelList()){
                    if(p!=null){
                        //mise à jour de la disponibilité
                        Constructeur constructeur = (Constructeur) p;
                        if(constructeur!=null){
                            constructeur.updateTempsPourEtreDispo();
                        }
                    }
                }
            }
        }

        payerLePersonnel();
        Strategie.AffinerGestionPersonnel(this);
    }

    public void monterLeMeuble(Meuble meuble) throws CloneNotSupportedException, StockageException {
        //on doit monter le meuble et calculer le chiffre d'affaire généré

        double prixDuMeuble = 0;
        for(Lot lot:meuble.getListeLots()){
            //on fragmente le volume du lot en 1 unité * qte necessaire
            //on retire le lot et on recupère les données du prix du lot qui à été rétiré
            Lot lotARetirer = lot.clone();
            lotARetirer.setVolume(1);
            for(int i=0;i<lot.getVolume();i++){
                Lot lotRetire = retirerLot(lotARetirer);
                double prixDuLot = lotRetire.getPrix();
                lot.setPrix(prixDuLot);
                prixDuMeuble += prixDuLot;

            }
        }
        meuble.setCoutDeRevient(prixDuMeuble);

        tresorerie+=prixDuMeuble;



    }

    public void stockerLot(Lot lot) throws StockageException {

        try {
            final int volumeLot = lot.getVolume();
            final int[] result = DernierIndexCaseContigue(volumeLot);
            final int i = result[0];
            final int j = result[1];

            Lot newLot = lot.clone();
            newLot.setVolume(1);

            int index = j-volumeLot+1;
            while(index<=j){
                matriceLot[i][index] = newLot;
                index++;
            }

            // on vérifie qu'il y'a une personne capable de stocker de disponible sinon on la recrute
            recruterSiBesoinPourStockage();

            System.out.println("Le lot " + lot.toString() + " a bien été crée et stocké " );


        } catch (Exception e){
            System.out.println("Impossible de stocker le lot ");
        }

    }

    public Lot retirerLot(Lot lot) throws CloneNotSupportedException, StockageException {
        recruterSiBesoinPourStockage();
        int volumeAretirer = lot.getVolume();
        Lot newLot = lot.clone();
        newLot.setVolume(1);
        Lot lotTrouve = null;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matriceLot[i][j]!=null){
                    if(matriceLot[i][j].equals(newLot)){
                        lotTrouve = matriceLot[i][j];
                        matriceLot[i][j]=null;
                        volumeAretirer--;
                    }
                }
                if(volumeAretirer==0){
                    return lotTrouve;
                }
            }
        }
        return  null;
    }

    public void mettreEnAttente (Meuble meuble) {
        this.listeAttente.add(meuble);
    }

    private void recruterSiBesoinPourStockage() throws StockageException {
        if(this.chef_equipe ==null || this.chef_equipe.size() == 0){
            List<Chef> listChefs = new LinkedList<>();
            ChefStock chefStock1 = new ChefStock(null,null,null,null);
            listChefs.add(chefStock1);

            this.setChef_equipe(listChefs);
            System.out.println("Un chef stock à été recruté " + chefStock1.toString());
        }else{
            for (Chef chef: this.chef_equipe){
                GestionStock p = chef.personnelDisponiblePourStockage();
                if(p == null ) {
                    chef.recruterPersonnel(Strategie.recrutemenOuvrierSpecialiseAleatoire());
                    break;
                }
                else{
                    break;
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
            if(o instanceof Commande){
                Commande c = (Commande) o;

                for(Meuble meuble:c.getDetailCommande()) {
                    for (Lot lot : meuble.getListeLots()) {
                        if (!lot.isDisponible(this)) {
                            rejeter(c);
                        }
                    }
                }
                System.out.println("Votre commande A été acceptée");

                for(Meuble meuble:c.getDetailCommande()){
                    mettreEnAttente(meuble);
                }

                traiterLesCommandes();



            }
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


    public boolean isDisponible(Lot lot) throws CloneNotSupportedException {
        int volumeAVerifier = lot.getVolume();
        Lot newLot = lot.clone();
        newLot.setVolume(1);
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matriceLot[i][j]!=null){
                    if(matriceLot[i][j].equals(newLot)){
                        //matriceLot[i][j]=null;
                        volumeAVerifier--;
                    }
                }
                if(volumeAVerifier==0){
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
}