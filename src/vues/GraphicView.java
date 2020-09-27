package vues;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import modele.ModeleLampes;

/**
 * GraphicView : classe qui est chargee d'afficher la grille
 * @author Jules Singer
 *
 */
public class GraphicView extends JPanel implements Observer {
	
	// on peut changer les valeurs static suivantes, le jeu s'adaptera et marchera(meme le taille de la grille avec le NBCASES)
	
	/**
	 * LARGEURCASE : represente la largeur de chaque case de la grille.
	 */
	public static int LARGEURCASE = 175; 
	
	/**
	 * HAUTEURCASE : represente la hauteur de chaque case de la grille.
	 */
	public static int HAUTEURCASE = 175; 
	
	/**
	 * nbCases : represente le nombre de cases par longueur et hauteur.
	 */
	public static final int NBCASES = 5;
	
	/**
	 * modele : fait le lien avec le modele
	 */
	private ModeleLampes modele;
		
	/**
	 * GraphicView : constructeur initialisant les attributs de l'objet
	 * @param t taille d'une case
	 * @param nb nombre de cases 
	 */
	public GraphicView(ModeleLampes mod) {
		this.modele = mod;
		setPreferredSize(new Dimension(NBCASES*LARGEURCASE, NBCASES*HAUTEURCASE));
	}
	
	/**
	 * paintComponent : dessine la grille et fait apparaitre une image de victoire quand l'utilisateur a gagne
	 * @param g Graphics
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// si le joueur a gagne on affiche une image de victoire
		if(modele.detecterVictoire() ) {
			BufferedImage imgVictoire;
			try {
				imgVictoire = ImageIO.read(getClass().getResourceAsStream("/vict.jpg"));
				g.drawImage(imgVictoire,-230,0, this.getWidth()+500, this.getHeight(), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else{ // sinon on dessine les ampoules selon leurs etats en adaptant leurs taille
			
			LARGEURCASE = this.getWidth()/NBCASES;
			HAUTEURCASE = this.getHeight() /NBCASES;
			
			//dessin de la grille (les cases)
			for(int i=0; i<NBCASES; i++) {
				for(int j=0; j<NBCASES; j++) {
					BufferedImage ampoule;
					try {
						if(modele.getGrille().get(i).get(j).estAllumee()) {
							ampoule = ImageIO.read(getClass().getResourceAsStream("/ampouleAllumee.jpg"));
						} else {
							ampoule = ImageIO.read(getClass().getResourceAsStream("/ampouleEteinte.jpg"));
						}
						g.drawImage(ampoule,i*LARGEURCASE,j*HAUTEURCASE, LARGEURCASE,HAUTEURCASE , null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			// on dessin les lignes de la grille
			for(int i=0; i<NBCASES; i++) {
				//dessin des lignes horizontales
				g.setColor(Color.BLACK);
				g.drawLine(0, i*HAUTEURCASE, LARGEURCASE*NBCASES, i*HAUTEURCASE);
				//dessin des lignes verticales
				g.setColor(Color.BLACK);
				g.drawLine(i*LARGEURCASE, 0, i*LARGEURCASE, HAUTEURCASE*NBCASES);
			}
		}
	}
	
	/**
	 * update : on met a jour le modele
	 */
	@Override
	public void update(Observable o, Object arg) {
		this.modele = (ModeleLampes) o;
		repaint();
	}
	
}
