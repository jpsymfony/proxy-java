package question1;

import java.util.Iterator;
import java.util.Properties;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class ListeProxyHttp implements Liste<String>
{
    private final static String URL_CNAM = "http://jfod.cnam.fr/jnews/tests/tp_proxy.html";

    private Liste<String> liste;
    private String nom;
    private String url;

    public ListeProxyHttp(String url, String nom)
    {
        this.liste = new ListeImpl(nom);
        this.url = url;
        this.nom = nom;
    }

    public ListeProxyHttp(String nom)
    {
        this(URL_CNAM, nom);
    }

    public boolean ajouter(String elt)
    {
        // a completer par une connexion HTTP avec les parametres suivants "nom=" + this.nom + "&commande=ajouter&elt="+ elt);
        String parametres = "nom=" + this.nom + "&commande=ajouter&elt=" + elt;
        Connexion connexion = new Connexion(this.url, parametres); // a completer , voir la classe interne Connexion en fin de listing

        Boolean resultatLocal = liste.ajouter(elt);
        String resultatDistant = connexion.result(); // le résultat de la connexion

        //si le resultatLocal est différent du  resultatDistant alors levée de CoherenceListeException("ajouter");
        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("ajouter");
        }
        return resultatLocal;
    }

    public boolean retirer(String elt)
    {
        // a completer par une connexion HTTP avec les parametres suivants "nom=" + this.nom + "&commande=retirer&elt="+ elt
        String parametres = "nom=" + this.nom + "&commande=retirer&elt=" + elt;
        Connexion connexion = new Connexion(this.url, parametres);

        Boolean resultatLocal = liste.retirer(elt);
        String resultatDistant = connexion.result(); // le résultat de la connexion

        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("retirer");
        }
        return resultatLocal;
    }

    public boolean vider()
    {
        // a completer par une connexion HTTP avec les parametres suivants "nom=" + this.nom + "&commande=vider"
        String parametres = "nom=" + this.nom + "&commande=vider";
        Connexion connexion = new Connexion(this.url, parametres);

        Boolean resultatLocal = liste.vider();
        String resultatDistant = connexion.result(); // le résultat de la connexion

        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("vider");
        }
        return resultatLocal;
    }

    /**
     * restauration complete de la liste reeelle a partir de la derniere sauvegarde
     */
    public boolean restaurer()
    {
        String parametres = "nom=" + this.nom + "&commande=restaurer";
        Connexion connexion = new Connexion(this.url, parametres); // a completer

        Boolean resultatLocal = liste.restaurer();
        String resultatDistant = connexion.result(); // le résultat de la connexion

        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("restaurer");
        }

        return resultatLocal;
    }


    public boolean estPresent(String elt)
    {
        String parametres = "nom=" + this.nom + "&commande=estPresent&elt=" + elt;
        Connexion connexion = new Connexion(this.url, parametres); // a completer

        Boolean resultatLocal = liste.estPresent(elt);
        String resultatDistant = connexion.result(); // le résultat de la connexion

        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("estPresent");
        }

        return resultatLocal;
    }

    public int taille()
    {
        String parametres = "nom=" + this.nom + "&commande=taille";
        Connexion connexion = new Connexion(this.url, parametres); // a completer

        int resultatLocal = liste.taille();
        String resultatDistant = connexion.result(); // le résultat de la connexion

        if (Integer.parseInt(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("taille");
        }

        return resultatLocal;
    }

    public String toString()
    {
        String parametres = "nom=" + this.nom + "&commande=toString";
        Connexion connexion = new Connexion(this.url, parametres); // a completer

        String resultatLocal = liste.toString();
        String resultatDistant = connexion.result(); // le résultat de la connexion

        if (!resultatDistant.equals(resultatLocal)) {
            throw new CoherenceListeException("toString");
        }

        return resultatLocal;
    }


    public Iterator<String> iterator()
    { // cette methode est complete
        return liste.iterator();
    }

    /**
     * Classe complete d'emission d'une requete au protocole HTTP.
     */
    private static class Connexion extends Thread
    {
        private String url;
        private String parametres;
        private String result;

        public Connexion(String url)
        {
            this(url, null);
        }

        public Connexion(String url, String parametres)
        {
            this.url = url;
            this.parametres = parametres.trim();
            this.result = new String("");
            this.start();
        }

        public String result()
        {
            try {
                this.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            return result;
        }

        public void run()
        {
            try {
                URL urlConnection = new URL(url);
                URLConnection connection = urlConnection.openConnection();
                connection.setDoOutput(true);

                PrintWriter out = new PrintWriter(connection.getOutputStream());
                out.println(parametres);
                out.flush();
                out.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = in.readLine();
                while (inputLine != null) {
                    result += inputLine;
                    inputLine = in.readLine();
                }
                in.close();
            } catch (Exception e) {
                this.result = "";
                e.printStackTrace();
            }
        }
    }


    /**
     * Mise en place du proxy si necessaire
     * attention, aucune verification de la validite de l'URL transmise n'est effectuee
     * en wifi au Cnam
     *
     * @param proxyHost adresse du proxy
     * @param proxyPort le port du proxy
     */
    public static void setHttpProxy(String proxyHost, int proxyPort)
    {
        Properties prop = System.getProperties();
        prop.put("proxySet", "true");
        prop.put("http.proxyHost", proxyHost);
        prop.put("http.proxyPort", Integer.toString(proxyPort));
    }

}

