package vues;
import java.awt.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

import modele.ModeleLampes;

/**
 * MenuView : classe chargee d'afficher le menu (tout ce qui ne fait pas partie de la grille)
 * @author Jules Singer
 *
 */
public class MenuView extends JPanel implements Observer {
	/**
	 * Boutons de menu
	 */
	private JButton config,aleatoire,jouer,quitter,regles;
	
	/**
	 * Affichage du nombre de coups
	 */
	private JLabel nbCoups, compteur;
	
	/**
	 * lien avec le modele
	 */
	private ModeleLampes modele;
	
	/**
	 * ButtonView : constructeur initialisant les attributs et ajoutant les composants au JPanel
	 */
	public MenuView(ModeleLampes m) {
		
		this.modele = m;
		//creation et ajout des composants avec leurs styles
		this.config = new JButton("Configurer");
		config.setFont(new Font("Dialog",0,25));
		config.setBorder(new LineBorder(Color.BLACK, 2));
		
		this.aleatoire = new JButton("Aleatoire");
		aleatoire.setFont(new Font("Dialog",0,25));
		aleatoire.setBorder(new LineBorder(Color.BLACK, 2));
		
		this.jouer = new JButton("Jouer");
		jouer.setFont(new Font("Dialog",0,25));
		jouer.setBorder(new LineBorder(Color.BLACK, 2));
		jouer.setEnabled(false);

		this.quitter = new JButton("Quitter");
		quitter.setFont(new Font("Dialog",0,25));
		quitter.setBorder(new LineBorder(Color.BLACK, 2));
		quitter.setEnabled(false);
		
		this.regles = new JButton("Règles");
		regles.setFont(new Font("Dialog",0,25));
		regles.setBorder(new LineBorder(Color.BLACK, 2));

		this.nbCoups = new JLabel("Nb Deplacements");
		nbCoups.setFont(new Font("Dialog",0,25));
		nbCoups.setHorizontalAlignment(SwingConstants.CENTER);

		this.compteur = new JLabel();
		compteur.setFont(new Font("Dialog",0,50));
		compteur.setHorizontalAlignment(SwingConstants.CENTER);
	
		this.setLayout(new GridLayout(7,1,0,5));
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension(400,500));
		
		// ajout des composants au JPanel
		this.add(config);
		this.add(aleatoire);
		this.add(jouer);
		this.add(nbCoups);
		this.add(compteur);
		this.add(quitter);
		this.add(regles);
	}
	

	/**
	 * getConfig : recupere le bouton de configuration
	 * @return l'attribut config
	 */
	public JButton getConfig() {
		return this.config;
	}
	
	/**
	 * getAleatoire : recupere le bouton d'aleatoire
	 * @return l'attribut aleatoire
	 */
	public JButton getAleatoire() {
		return aleatoire;
	}
	
	/**
	 * getJouer : recupere le bouton de lancement du jeu
	 * @return l'attribut jouer
	 */
	public JButton getJouer() {
		return jouer;
	}
	
	/**
	 * getQuitter : recupere le bouton qui quitte le jeu
	 * @return l'attribut quitter
	 */
	public JButton getQuitter() {
		return quitter;
	}
	
	/**
	 * getRegles : recupere le bouton qui donne les regles
	 * @return l'attribut quitter
	 */
	public JButton getRegles() {
		return this.regles;
	}
	
	/**
	 * paintComponent : affiche le compteur de clics
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.compteur.setText(String.valueOf(this.modele.getNbDeplacements()));
	}
	
	/**
	 * update : met a jour le modele
	 */
	@Override
	public void update(Observable o, Object arg) {
		this.modele = (ModeleLampes) o;
		repaint();
	}	
}
