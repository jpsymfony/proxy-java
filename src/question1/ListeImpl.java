package question1;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The type Liste.
 */
public class ListeImpl implements Liste<String>
{
    private List<String> liste;
    private String nom;

    /**
     * Creation d'une liste.
     *
     * @param nom le nom de cette liste
     */
    public ListeImpl(String nom)
    {
        liste = new ArrayList<String>();
    }

    /**
     * Ajout d'un element a la liste.
     *
     * @param elt l'element a ajouter
     * @return true si la liste a ete modifiee par cet ajout, false sinon, (notamment en cas d'exception)
     */
    public boolean ajouter(String elt)
    {
        boolean result = liste.add(elt);

        try {
            this.sauvegarder("liste.ser");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Retrait de toutes les occurrences de cet element de la liste
     *
     * @param elt l'element a retirer
     * @return true si au moins un retrait a eu lieu, false sinon
     */
    public boolean retirer(String elt)
    {
        boolean retraitEffectif = liste.remove(elt); // au moins un
        while (retraitEffectif && liste.remove(elt)) ; // tous les autres

        try {
            this.sauvegarder("liste.ser");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return retraitEffectif;
    }

    /**
     * Vider cette liste (ou le retrait de tous les elements).
     *
     * @return true si la liste a ete modifiee, false sinon
     */
    public boolean vider()
    {
        boolean resultat = taille() > 0;
        liste.clear();

        try {
            this.sauvegarder("liste.ser");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return resultat;
    }

    /**
     * Restauration de cette liste.
     *
     * @return true si la restauration a eu lieu, false sinon
     */
    public boolean restaurer()
    {
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream("liste.ser"));
            liste = (List<String>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Sauvegarder.
     *
     * @param nomfichier the nomfichier
     * @throws Exception the exception
     */
    public void sauvegarder(String nomfichier) throws Exception
    {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(nomfichier));
            oos.writeObject(liste);
            oos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                } catch (IOException ioe) {
                }
                try {
                    oos.close();
                } catch (IOException ioe) {
                }
            }
        }
    }

    /**
     * Test de la presence d'un element.
     *
     * @param elt l'element a comparer
     * @return true si elt est present, false autrement
     */
    public boolean estPresent(String elt)
    {
        return liste.contains(elt);
    }

    /**
     * Obtention du nombre d'elements.
     *
     * @return le nombre d'elements de la liste
     */
    public int taille()
    {
        return liste.size();
    }

    /**
     * Parcours de la liste.
     *
     * @return un iterateur
     */
    public Iterator<String> iterator()
    {
        return liste.iterator();
    }

    /**
     * Obtention d'une liste sous ce format "[elt1, elt2, ...]",  format habituel des API predefinies.
     * Une liste vide est notee [].
     *
     * @return la liste sous un format "lisible"
     */
    public String toString()
    {
        return liste.toString();
    }

}
