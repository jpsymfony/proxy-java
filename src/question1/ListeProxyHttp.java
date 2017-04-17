package question1;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * The type Liste proxy http.
 */
public class ListeProxyHttp implements Liste<String>
{
    private final static String URL_CNAM = "http://jfod.cnam.fr/jnews/tests/tp_proxy.html";

    private Liste<String> liste;
    private String nom;
    private String url;

    /**
     * Instantiates a new Liste proxy http.
     *
     * @param url the url
     * @param nom the nom
     */
    public ListeProxyHttp(String url, String nom)
    {
        this.liste = new ListeImpl(nom);
        this.url = url;
        this.nom = nom;
    }

    /**
     * Instantiates a new Liste proxy http.
     *
     * @param nom the nom
     */
    public ListeProxyHttp(String nom)
    {
        this(URL_CNAM, nom);
    }

    /**
     * Ajout d'un element a la liste.
     *
     * @param elt l'element a ajouter
     * @return true si la liste a ete modifiee par cet ajout, false sinon, (notamment en cas d'exception)
     */
    public boolean ajouter(String elt)
    {
        // a completer par une connexion HTTP avec les parametres suivants "nom=" + this.nom + "&commande=ajouter&elt="+ elt);
        String parametres = "nom=" + this.nom + "&commande=ajouter&elt=" + elt;
        Connexion connexion = new Connexion(this.url, parametres); // a completer , voir la classe interne Connexion en fin de listing

        Boolean resultatLocal = liste.ajouter(elt);
        String resultatDistant = connexion.result(); // le resultat de la connexion

        //si le resultatLocal est different du  resultatDistant alors levee de CoherenceListeException("ajouter");
        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("ajouter");
        }
        return resultatLocal;
    }

    /**
     * Retrait de toutes les occurrences de cet element de la liste
     *
     * @param elt l'element a retirer
     * @return true si au moins un retrait a eu lieu, false sinon
     */
    public boolean retirer(String elt)
    {
        // a completer par une connexion HTTP avec les parametres suivants "nom=" + this.nom + "&commande=retirer&elt="+ elt
        String parametres = "nom=" + this.nom + "&commande=retirer&elt=" + elt;
        Connexion connexion = new Connexion(this.url, parametres);

        Boolean resultatLocal = liste.retirer(elt);
        String resultatDistant = connexion.result(); // le resultat de la connexion

        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("retirer");
        }
        return resultatLocal;
    }

    /**
     * Vider cette liste (ou le retrait de tous les elements).
     *
     * @return true si la liste a ete modifiee, false sinon
     */
    public boolean vider()
    {
        // a completer par une connexion HTTP avec les parametres suivants "nom=" + this.nom + "&commande=vider"
        String parametres = "nom=" + this.nom + "&commande=vider";
        Connexion connexion = new Connexion(this.url, parametres);

        Boolean resultatLocal = liste.vider();
        String resultatDistant = connexion.result(); // le resultat de la connexion

        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("vider");
        }
        return resultatLocal;
    }

    /**
     * Restauration de cette liste.
     *
     * @return true si la restauration a eu lieu, false sinon
     */
    public boolean restaurer()
    {
        String parametres = "nom=" + this.nom + "&commande=restaurer";
        Connexion connexion = new Connexion(this.url, parametres);
        connexion.result(); // execution à distance de la restauration

        // on appelle la liste distante
        parametres = "nom=" + this.nom + "&commande=toString";
        connexion = new Connexion(this.url, parametres);
        String resultatDistant = connexion.result(); // le resultat de la connexion

        // execution locale de la restauration
        liste.restaurer();

        // Si la liste distante restauree est differente de la liste locale restauree, on renvoie false
        if (!resultatDistant.equals(liste.toString())) {
            return false;
        }

        // sinon true
        return true;
    }

    /**
     * Test de la presence d'un element.
     *
     * @param elt l'element a comparer
     * @return true si elt est present, false autrement
     */
    public boolean estPresent(String elt)
    {
        String parametres = "nom=" + this.nom + "&commande=estPresent&elt=" + elt;
        Connexion connexion = new Connexion(this.url, parametres);

        Boolean resultatLocal = liste.estPresent(elt);
        String resultatDistant = connexion.result(); // le resultat de la connexion

        if (Boolean.parseBoolean(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("estPresent");
        }

        return resultatLocal;
    }

    /**
     * Obtention du nombre d'elements.
     *
     * @return le nombre d'elements de la liste
     */
    public int taille()
    {
        String parametres = "nom=" + this.nom + "&commande=taille";
        Connexion connexion = new Connexion(this.url, parametres);

        int resultatLocal = liste.taille();
        String resultatDistant = connexion.result(); // le resultat de la connexion

        if (Integer.parseInt(resultatDistant) != resultatLocal) {
            throw new CoherenceListeException("taille");
        }

        return resultatLocal;
    }

    /**
     * Obtention d'une liste sous ce format "[elt1, elt2, ...]",  format habituel des API predefinies.
     * Une liste vide est notee [].
     *
     * @return la liste sous un format "lisible"
     */
    public String toString()
    {
        String parametres = "nom=" + this.nom + "&commande=toString";
        Connexion connexion = new Connexion(this.url, parametres);

        String resultatLocal = liste.toString();
        String resultatDistant = connexion.result(); // le resultat de la connexion

        if (!resultatDistant.equals(resultatLocal)) {
            throw new CoherenceListeException("toString");
        }

        return resultatLocal;
    }

    /**
     * Parcours de la liste.
     *
     * @return un iterateur
     */
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

        /**
         * Instantiates a new Connexion.
         *
         * @param url the url
         */
        public Connexion(String url)
        {
            this(url, null);
        }

        /**
         * Instantiates a new Connexion.
         *
         * @param url        the url
         * @param parametres the parametres
         */
        public Connexion(String url, String parametres)
        {
            this.url = url;
            this.parametres = parametres.trim();
            this.result = new String("");
            this.start();
        }

        /**
         * Result string.
         *
         * @return the string
         */
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

