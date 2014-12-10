package fluxR;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

//création de la JFrame FenetreRss afin d'afficher le flux
public class Fenetre extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel container = new JPanel();

	public Fenetre()
	{
		container.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setTitle("Sport.fr | Flux RSS");
		this.setSize(800,400);
		this.setLocationRelativeTo(null);
	}

	 //Récupère le flux RSS de sport.fr puis l'affiche en console
    public static void main(String[] args) 
    {
        //Fenetre f = new Fenetre();
        LecteurRSS.afficherRss();
    }
}