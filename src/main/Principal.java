package main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controleur.*;
import modele.ModeleLampes;
import vues.*;

/**
 * Principal : classe permettant d'executer tout le programme.
 * @author Jules Singer
 *
 */
public class Principal {
	
	public static void main(String[] args) {		
		// le modele
		ModeleLampes modele = new ModeleLampes();
		
		// les vues
		GraphicView vueGrille = new GraphicView(modele);
		MenuView menu = new MenuView(modele);
		
		// les controleurs
		ControleurGrille controleur = new ControleurGrille(modele,vueGrille, menu);
		ControleurMenu controleurMenu = new ControleurMenu(modele, menu);
		
		// on ajoute les observers
		modele.addObserver(vueGrille);
		modele.addObserver(menu);
		
		// ajout du controleur sur la vue de la grille
		vueGrille.addMouseListener(controleur);
		
		// fenetre principale
		JFrame fenetre = new JFrame("Eteins la lumière !");

		//ajout des composants
		fenetre.add(vueGrille, BorderLayout.CENTER);
		fenetre.add(menu, BorderLayout.WEST);
	
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setLocationRelativeTo(null);
	}
}

