package modele;

import java.awt.*;
import java.util.Observable;
import javax.swing.JPanel;

/**
 *  Classe Case : cette classe a pour but de representer une case de la grille.
 * @author Jules Singer
 *
 */
public class Case extends Observable{
	
	/**
	 * posX : represente la coordonnee en X de la case
	 */
	private int posX;
	
	/**
	 * posY : represente la coordonnee en Y de la case
	 */	
	private int posY;
	
	/**
	 * etat : represente l'etat de la case : eteinte = false, allumee = true.
	 */
	private boolean etat;
	
	/**
	 * Case : constructeur initialisant les attributs
	 */
	public Case(int x, int y, boolean etat) {
		this.posX = x;
		this.posY = y;
		this.etat = false;
	}
	
	/**
	 * changerEtat : change l'etat de la lampe, s'allume si elle est eteinte, s'eteint si elle est allumee.
	 */
	public void changerEtat() {
		if(etat) 
			etat = false;
		else 
			etat = true;
	}
	
	/**
	 * estAllumee : permet de savoir si la lampe est allumee ou non
	 * @return le booleen true si elle est allumee, false sinon.
	 */
	public boolean estAllumee(){
		return this.etat == true;
	}
	
	/**
	 * getPosX : recupere le numero de la case dans la ligne
	 * @return l'attribut posX
	 */
	public int getPosX() {
		return this.posX;
	}
	
	/**
	 * setPosX : change le numero de la case dans la ligne 
	 * @param x nouvelle position en posX
	 */
	public void setPosX(int x) {
		this.posX = x;
	}
	
	/**
	 * getPosY : recupere le numero de la ligne ou est la case
	 * @return l'attribut posY
	 */
	public int getPosY() {
		return this.posY;
	}
	
	/**
	 * setPosY : change le numero de la ligne de la case
	 * @param y nouvelle position en posY
	 */
	public void setPosY(int y) {
		this.posY = y;
	}
	
	/**
	 * toString : affichage formate d'un objet Case
	 * @return res l'affichage de la case
	 */
	public String toString() {
		String res = "";
		res += "posX : " + this.posX + ", posY : " + this.posY + ",Etat : " + this.etat;
		return res;
	}	
}
