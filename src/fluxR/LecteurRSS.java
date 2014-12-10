package fluxR;

//import des librairies necessaires à l'affichage du flux rss
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//Parser le fichier XML pour lecture sur la console

public class LecteurRSS 
{
	//Récupère tous les éléments dans un tableau de noeuds et récupère ce qu'il contient (string)
    public String recupNoeud(Node noeud, String chemin) 
    {

        String[] chemins = chemin.split("\\|");
        Node node = null;

        if (chemins != null && chemins.length > 0) 
        {
            node = noeud;

            for (int i = 0; i < chemins.length; i++) 
            {
                node = getChildName(node, chemins[i].trim());
            }
        }

        if (node != null) 
        {
            return node.getTextContent();
        } else {
            return "";
        }
    }

    //Recupère les noeuds enfants afin d'afficher toutes les infos et n'affiche pas si child n'existe pas
    public Node getChildName(Node noeud, String nom) 
    {
        if (noeud == null) 
        {
            return null;
        }
        NodeList listChild = noeud.getChildNodes();

        if (listChild != null) {
            for (int i = 0; i < listChild.getLength(); i++) 
            {
                Node child = listChild.item(i);
                if (child != null) 
                {
                    if ((child.getNodeName() != null && (nom.equals(child.getNodeName()))) || (child.getLocalName() != null && (nom.equals(child.getLocalName())))) 
                    {
                        return child;
                    }
                }
            }
        }
        return null;
    }
    
    //Parser avec URL du flux RSS avec parse(@param envoiUrl)
    public void parse(String envoiUrl) 
    {
        try 
        {
            DocumentBuilder document = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL url = new URL(envoiUrl);
            Document doc = document.parse(url.openStream());
            NodeList nodes = null;
            Element element = null;
            
            //Recupère le titre et la date de la dernière publication du flux
            nodes = doc.getElementsByTagName("title");
            Node contenu = doc.getDocumentElement();
            System.out.println("Flux RSS: " + this.recupNoeud(contenu, "channel|title"));
            System.out.println("Date de la dernière publication: " + this.recupNoeud(contenu, "channel|lastBuildDate"));
            System.out.println();
            
            
            //Recupère chaque élement du flux RSS et l'affiche
            nodes = doc.getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); i++) 
            {
                element = (Element) nodes.item(i);
                System.out.println("Titre: " + recupNoeud(element, "title"));
                System.out.println("Lien: " + recupNoeud(element, "link"));
                System.out.println("Date: " + recupNoeud(element, "pubDate"));
                System.out.println("Description: " + recupNoeud(element, "description"));
                System.out.println();
            }
          
        } catch (SAXException ex) 
          {
            Logger.getLogger(LecteurRSS.class.getName()).log(Level.SEVERE, null, ex);
          } 
          catch (IOException ex) 
          {
            Logger.getLogger(LecteurRSS.class.getName()).log(Level.SEVERE, null, ex);
          } 
          catch (ParserConfigurationException ex) 
          {
            Logger.getLogger(LecteurRSS.class.getName()).log(Level.SEVERE, null, ex);
          }
    }

    //méthode retournant le xml parsé et prêt à l'emploi
    public static void afficherRss()
    {
    	LecteurRSS reader = new LecteurRSS();
        reader.parse("http://www.sport.fr/RSS/sport1.xml");
    }
}