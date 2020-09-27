package controleur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import modele.*;
import vues.*;
/**
 * ControleurMenu : permet de gerer le menu(les boutons) et leurs actions.
 * @author Jules Singer
 *
 */
public class ControleurMenu extends JPanel {
	
	/**
	 * menu : fait le lien avec la vue des boutons
	 */
	private MenuView menu;
	
	/**
	 * modele : fait le lien avec le modele
	 */
	private ModeleLampes modele;
	
	/**
	 * nbLampes : represente le nombre de lampes qui est allume lors du choix du mode aleatoire
	 */
	private int nbLampes;
	
	/**
	 * ControleurMenu : permet de definir les actions des boutons.
	 * @param m modele 
	 * @param b vue des boutons
	 */
	public ControleurMenu(ModeleLampes m,MenuView b) {
		this.menu = b;
		this.modele = m;
		this.nbLampes = 8;
		
		//ajout d'un actionListener sur le bouton aleatoire
		menu.getAleatoire().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// si aucun mode n'a encore ete choisi on demande le nombre de cases a allumees
				if(modele.getModeJeu() == 0) {
					menu.getJouer().setEnabled(true);
					JDialog dialAleatoire = new JDialog();
					
					JLabel demanderNb = new JLabel("Veuillez entrer le nombre de cases à allumées : (entre 1 et " + GraphicView.NBCASES*GraphicView.NBCASES + ")" );
					demanderNb.setHorizontalAlignment(SwingConstants.CENTER);
					demanderNb.setFont(new Font("Dialog",0,20));
					
					JLabel defaut =  new JLabel("(Si votre valeur est invalide, le nombre de cases sera de 8 par defaut)");
					defaut.setHorizontalAlignment(SwingConstants.CENTER);
					defaut.setFont(new Font("Dialog",0,15));
					
					JTextField choix = new JTextField();
					choix.setPreferredSize(new Dimension(400,30));
					choix.setHorizontalAlignment(JTextField.CENTER);
					
					JButton valider = new JButton("Valider");
					valider.setFont(new Font("Dialog",0,15));
					valider.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								int nb = Integer.parseInt(choix.getText());
								if(nb > 0 && nb <= GraphicView.NBCASES * GraphicView.NBCASES) 
									nbLampes = nb;
							} catch(NumberFormatException e1) {}
							modele.setModeJeu(1);
							dialAleatoire.dispose();
							creerFenetreDialogue("Appuyez sur Jouer pour commencer",370);
						}
					});
					
					dialAleatoire.setLayout(new FlowLayout());
					dialAleatoire.setPreferredSize(new Dimension(650,150));
					dialAleatoire.setResizable(false);
					dialAleatoire.pack();
					dialAleatoire.setVisible(true);
					dialAleatoire.setLocationRelativeTo(null);
					
					// ajout des composants
					dialAleatoire.add(demanderNb);
					dialAleatoire.add(defaut);
					dialAleatoire.add(choix);
					dialAleatoire.add(valider);
				
				} else { // sinon on envoit un message d'information qui signale qu'un mode a deja ete choisi
					creerFenetreDialogue("Vous avez déjà choisi un mode de jeu, veuillez appuyer sur \"Jouer\" pour commencer.", 800);
				}
			
			}
		});
		
		// ajout d'un actionListener sur le bouton de configuration
		menu.getConfig().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// si aucun mode de jeu n'a deja ete choisi on met le jeu en mode configuration
				if(modele.getModeJeu() == 0) {
				menu.getJouer().setEnabled(true);
				modele.setModeJeu(2);
				creerFenetreDialogue("Veuillez choisir les lampes que vous voulez allumées en cliquant dessus, et cliquez ensuite sur Jouer quand vous serez pret !",1150);

				} else { //sinon on envoit un message d'info
					creerFenetreDialogue("Vous avez déjà choisi un mode de jeu, veuillez appuyer sur \"Jouer\" pour commencer.", 800);
				}
			}
		});
		
		// ajout d'un actionListener sur le bouton jouer
		menu.getJouer().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// en fonction du mode de jeu on fait differentes actions
				switch(modele.getModeJeu()) {
					//si aucun mode choisi, on envoit un message d'erreur
					case 0 : 
						creerFenetreDialogue("Vous n'avez pas choisi de mode de jeu, veuillez appuyer sur \"Aleatoire\" ou sur \"Configurer\" pour continuer", 1000);
						break;
					//mode 1 = aleatoire, on allume le bon nombre de lampes
					case 1 :
						modele.allumerLampesHasard(nbLampes);
						changerEtatBoutons(true);
						modele.setModeJeu(0);
						modele.setJeuEnCours(true);
						break;
					//mode 2 = configuration, on permet a l'utilisateur de changer les lampes qu'il veut
					case 2 : 
						changerEtatBoutons(true);
						modele.setModeJeu(0);
						modele.setJeuEnCours(true);
						break;
				default:
					break;
				}		
			}	
		});	 
		
		// ajout d'un actionListener sur le bouton quitter
		menu.getQuitter().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialQuitter = new JDialog();
				
				// demande d'une confirmation
				JLabel confirmation = new JLabel("Etes-vous sur de vouloir reinitialiser le jeu ?");
				confirmation.setHorizontalAlignment(SwingConstants.CENTER);
				confirmation.setFont(new Font("Dialog",0,20));
				
				JButton oui = new JButton("Oui");
				oui.setFont(new Font("Dialog",0,15));
				// reinitialisation du jeu 
				oui.addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						modele.reinitialiserJeu();
						changerEtatBoutons(false);
						dialQuitter.dispose();
					}
				});
				
				JButton non = new JButton("Non");
				non.setFont(new Font("Dialog",0,15));
				non.addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						dialQuitter.dispose();
					}
				});
				
				dialQuitter.setLayout(new FlowLayout());
				dialQuitter.setPreferredSize(new Dimension(450,110));
				dialQuitter.setResizable(false);
				dialQuitter.pack();
				dialQuitter.setVisible(true);
				dialQuitter.setLocationRelativeTo(null);
				
				// ajout des composants
				dialQuitter.add(confirmation);
				dialQuitter.add(oui);
				dialQuitter.add(non);
			}
		});	
		
		// ajout d'un actionListener pour les regles
		menu.getRegles().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame fenetreBvn = new JFrame("Règles du jeu");
				JLabel title = new JLabel("REGLES DU JEU", JLabel.CENTER);
				title.setFont(new Font("Dialog",0,40));
				
				//explications des regles
				JLabel explication1 = new JLabel("Le but du jeu est simple : Eteindre toutes les lumières.", JLabel.CENTER);
				explication1.setFont(new Font("Dialog",0,20));
				JLabel explication2 = new JLabel("Pour cela, il faut d'abord choisir un mode de jeu, il en existe deux a votre disposition :", JLabel.CENTER);
				explication2.setFont(new Font("Dialog",0,18));
				JLabel explication3 = new JLabel("                       - Configuration : défini ton propre modèle ; ", JLabel.LEFT);
				explication3.setFont(new Font("Dialog",0,18));
				JLabel explication4 = new JLabel("                       - Aléatoire : allume aléatoirement le nombre de cases que tu souhaites ; ", JLabel.LEFT);
				explication4.setFont(new Font("Dialog",0,18));
				JLabel explication5 = new JLabel("Un compteur de clics est disposé pour que tu puisses battre tes records.", JLabel.CENTER);
				explication5.setFont(new Font("Dialog",0,18));
				JLabel explication6 = new JLabel("Il ne te reste plus qu'à appuyer sur Jouer et a cliquer sur les lumières !", JLabel.CENTER);
				explication6.setFont(new Font("Dialog",0,18));
				
				JButton ok = new JButton("J'ai compris");
				ok.setFont(new Font("Dialog",0,21));
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						fenetreBvn.dispose();		
					}
				});
				
				fenetreBvn.setLayout(new GridLayout(8,1,0,0));
				fenetreBvn.setPreferredSize(new Dimension(720,500));
				fenetreBvn.setResizable(false);
				fenetreBvn.pack();
				fenetreBvn.setVisible(true);
				fenetreBvn.setLocationRelativeTo(null);
				fenetreBvn.add(title);
				fenetreBvn.add(explication1);
				fenetreBvn.add(explication2);
				fenetreBvn.add(explication3);
				fenetreBvn.add(explication4);
				fenetreBvn.add(explication5);
				fenetreBvn.add(explication6);
				fenetreBvn.add(ok);
			}
		
		});
	}
	
	/**
	 * creerFenetreDialogue : permet de creer une fenetre avec un message a l'interieur
	 * @param message message personnalise
	 * @param largeur pour regler la largeur de la fenetre selon la longueur du message
	 */
	public void creerFenetreDialogue(String message, int largeur) {
		JDialog dialInfo = new JDialog();
		JLabel info = new JLabel(message);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setFont(new Font("Dialog",0,20));
		
		JButton ok = new JButton("Ok");
		ok.setFont(new Font("Dialog",0,15));
		ok.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialInfo.dispose();
			}
		});
		
		dialInfo.setLayout(new FlowLayout());
		dialInfo.setPreferredSize(new Dimension(largeur,125));
		dialInfo.setResizable(false);
		dialInfo.pack();
		dialInfo.setVisible(true);
		dialInfo.setLocationRelativeTo(null);
		
		// ajout des composants
		dialInfo.add(info);
		dialInfo.add(ok);
		
	}
	
	/**
	 * changerEtatBoutons : permet de changer l'etat des boutons selon le boolean.
	 * @param b, true : met les boutons en mode partie, false : met les boutons en mode hors partie
	 */
	public void changerEtatBoutons(boolean b) {
		if(b) {
			menu.getAleatoire().setEnabled(false);
			menu.getConfig().setEnabled(false);
			menu.getJouer().setEnabled(false);
			menu.getQuitter().setEnabled(true);
		} else {
			menu.getAleatoire().setEnabled(true);
			menu.getConfig().setEnabled(true);
			menu.getJouer().setEnabled(true);
			menu.getQuitter().setEnabled(false);
		}
	}
}
