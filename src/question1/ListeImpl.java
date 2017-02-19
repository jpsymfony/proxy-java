package question1;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

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

    public boolean ajouter(String elt)
    {
        return liste.add(elt);
    }

    public boolean retirer(String elt)
    {
        boolean retraitEffectif = liste.remove(elt); // au moins un
        while (retraitEffectif && liste.remove(elt)) ; // tous les autres
        return retraitEffectif;
    }

    public boolean vider()
    {
        boolean resultat = taille() > 0;
        liste.clear();
        return resultat;
    }

    /**
     * optionnel ...
     */
    public boolean restaurer()
    {
        throw new UnsupportedOperationException(" pas de restauration");
    }

    public boolean estPresent(String elt)
    {
        return liste.contains(elt);
    }

    public int taille()
    {
        return liste.size();
    }

    public Iterator<String> iterator()
    {
        return liste.iterator();
    }

    public String toString()
    {
        return liste.toString();
    }

}
