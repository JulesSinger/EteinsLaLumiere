package controleur;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.event.MouseInputAdapter;

import modele.ModeleLampes;
import vues.*;

/**
 * ControleurGrille : permet de gerer la grille du jeu lorsque l'utilisateur  clique dessus
 * @author Jules Singer
 *
 */
public class ControleurGrille extends MouseInputAdapter{
	
	/**
	 * modele : fait le lien avec le modele
	 */
	private ModeleLampes modele;
	
	/**
	 * grille : fait le lien avec la vue de la grille
	 */
	private GraphicView grille;
	
	/**
	 * vueBoutons : fait le lien avec la vue des boutons
	 */
	private MenuView vueBoutons; 
	
	/**
	 * Controleurgrille : constructeur initialisant les attributs 
	 * @param m le modele
	 * @param g la vue
	 */
	public ControleurGrille(ModeleLampes m, GraphicView g,MenuView b) {
		this.modele = m;
		this.grille = g;
		this.vueBoutons = b;
	}
	
	/**
	 * mousePressed : recupere la position de la case ou l'utilisateur a clique, incremente le compteur de coups, 
	 * et fait evoluer le jeu.
	 */
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		int numLigne= (int) Math.floor(e.getY() / GraphicView.HAUTEURCASE);
		int numCase= (int) Math.floor(e.getX() / GraphicView.LARGEURCASE);

		modele.incrementerCompteur();
		modele.getGrille().get(numCase).get(numLigne).setPosX(numCase);
		modele.getGrille().get(numCase).get(numLigne).setPosY(numLigne);
		modele.changerEtat(modele.getGrille().get(numCase).get(numLigne));		
	}
	
}
