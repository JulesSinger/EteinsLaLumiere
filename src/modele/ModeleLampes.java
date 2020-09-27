package modele;

import java.awt.Color;
import java.util.*;

import vues.GraphicView;

/**
 * ModeleLampe : cette classe permet de gerer les donnees du jeu
 * @author Jules Singer
 *
 */
public class ModeleLampes extends Observable{
	
	/*
	 * compteurDeplacements : represente le nombre de coups joues par l'utilisateur.
	 */
	private int compteurDeplacements;
	
	/**
	 * grille : List a deux dimensions representant la grille du jeu
	 */
	private List<ArrayList<Case>> grille;
	
	/**
	 * jeuEnCours : permet de savoir si le bouton jouer a ete clique et donc si la partie a demarree. 
	 */
	private boolean jeuEnCours;
	
	/**
	 * mode : represente le mode choisi (aucun = 0, aleatoire = 1, config = 2)
	 */
	private int modeJeu;
	
	/**
	 * LightModel : constructeur vide initialisant les attributs.
	 */
	public ModeleLampes() {
		this.compteurDeplacements = 0;
		this.jeuEnCours = false;
		this.modeJeu = 0;
		this.grille = new ArrayList<ArrayList<Case>>();
		initialiserGrille();
	}
	
	/**
	 * initialiserGrille : initialise chaque case de la grille avec des objets Case par defaut "vide" et sans position.
	 */
	public void initialiserGrille() {
		for(int i=0; i<GraphicView.NBCASES; i++) {
			ArrayList<Case> ligne = new ArrayList<Case>();
			grille.add(ligne);
			for(int j=0; j<GraphicView.NBCASES; j++) {
				Case caseVide = new Case(0,0,false);
				grille.get(i).add(caseVide);
			}
		}
	}
	
	/**
	 * incrementerCompteur : ajoute 1 au compteur de deplacements si le jeu n'est pas en mode configuration et si la partie a demarree.
	 */
	public void incrementerCompteur() {
		if(modeJeu != 2 && jeuEnCours)
			this.compteurDeplacements++;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * changerEtat : change l'etat de la case en parametre et de ses cases adjacentes si le jeu n'est pas en mode de configuration
	 * @param c case dont on veut modifier l'etat s
	 */
	public void changerEtat(Case c){
		
		// mode configuration, on ne change qu'une lampe
		if(modeJeu == 2) {
			c.changerEtat();
		} else if(jeuEnCours){ // sinon on change aussi les 4 lampes adjacentes
			int x = c.getPosX() ;
			int y = c.getPosY() ;
					
			// on change l'etat de la case cliquee
			c.changerEtat();
			
			// on change l'etat des cases adjacentes à celle cliquee 
			if(x+1 < GraphicView.NBCASES)
				this.grille.get(x+1).get(y).changerEtat();
			if(x-1 >= 0)
				this.grille.get(x-1).get(y).changerEtat();

			if(y+1 < GraphicView.NBCASES)
				this.grille.get(x).get(y+1).changerEtat();

			if(y-1 >=0)
				this.grille.get(x).get(y-1).changerEtat();
		}
	
		// on specifie qu'il y a eu des changements 
		setChanged();
		notifyObservers();
	}
	
	/**
	 * allumerLampesHasard : permet d'allumer un nombre de lampes donnees de la grille au hasard
	 * @param nbLampes nombre de lampes a allumer
	 */
	public void allumerLampesHasard(int nbLampes) {
		int lampesAllumees = 0;
		while(lampesAllumees < nbLampes) {
			
			int ligne = (int) (Math.random()*GraphicView.NBCASES);
			int numCase = (int) (Math.random()*GraphicView.NBCASES);
			if(!(this.grille.get(ligne).get(numCase).estAllumee())) {
				this.grille.get(ligne).get(numCase).changerEtat();
				lampesAllumees++;
			}
		}
		// on met le mode de jeu a 1 (aleatoire)
		modeJeu = 1;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * reinitialiserJeu : remet tout le jeu a 0 pour qu'il soit pret a etre relancer pour une nouvelle partie.
	 */
	public void reinitialiserJeu() {
		this.grille.clear();
		initialiserGrille();
		this.compteurDeplacements = 0;
		this.modeJeu = 0;
		this.jeuEnCours = false;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * detecterGrilleEteinte : parcours la grille et regarde si toute les lampes sont eteintes
	 * @return true si la grille est eteinte, false sinon.
	 */
	public boolean detecterGrilleEteinte() {
		// indice d'arret du parcours de la grille	
		boolean grilleEteinte = true;
		// indices des cases
		int i = 0;
		int j = 0;
		// parcours des lignes
		while(grilleEteinte && i < this.grille.size()) {
			// parcours des colonnes
			while(grilleEteinte && j < this.grille.size()) {
				// si la case est allumee la grille n'est pas eteinte
				if(this.grille.get(i).get(j).estAllumee()) {
					grilleEteinte = false;
				} else {
					j++;
				}
			}
			i++;
			j = 0;
		}
		return grilleEteinte;
	}
	
	/**
	 * detecterVictoire : permet de detecter si la partie est gagnee ou non.
	 * @return le boolean true si la partie est gagnee, false sinon.
	 */
	public boolean detecterVictoire() {
		// si la grille et eteinte et qu'une partie est en cours alors le joueur a gagne
		if(this.detecterGrilleEteinte() && this.jeuEnCours) {
			this.jeuEnCours = false;
			return true;
		} else 
			return false;
	}
	
	/**
	 * getGrille : permet de recuperer la grille du jeu
	 * @return l'attribut grille
	 */
	public List<ArrayList<Case>> getGrille(){
		return this.grille;
	}
	
	/**
	 * getNbDeplacements : permet de recuperer le nombre de fois que l'utilisateur à clique
	 * @return l'attribut compteurDeplacements
	 */
	public int getNbDeplacements() {
		return this.compteurDeplacements;
	}
		
	/**
	 * getJeuEnCours : permet de savoir si une partie est en cours
	 * @return l'attribut jeuEnCours
	 */
	public boolean getJeuEnCours() {
		return this.jeuEnCours;
	}
	
	/**
	 * setJouer : change jouer avec le booleen en parametre
	 * @param b booleen changeant l'attribut jouer
	 */
	public void setJeuEnCours(boolean b) {
		this.jeuEnCours = b;
	}
	
	/**
	 * getModeJeu : permet de savoir quel mode de jeu est choisi 
	 * @return l'attribut modeJeu
	 */
	public int getModeJeu() {
		return this.modeJeu;
	}
	
	/**
	 * setModeJeu : permet de changer le mode de jeu
	 * @param i nouveau mode de jeu 
	 */
	public void setModeJeu(int i) {
		this.modeJeu = i;
	}
	
	/**
	 * toString : affichage formate de toute les lampes
	 * @return res l'affichage de la grille
	 */
	public String toString() {
		String res = "";
		for(int i=0 ; i<5 ; i++) {
			res += "Ligne n°" + i + "\n";
			for(int j=0 ; j<5 ; j++) {
				res += "	Case n°" + j + " " + this.grille.get(i).get(j).toString() + "\n";
			}
		}
		return res;	
	}
}
