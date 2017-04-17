package question1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * The type Liste.
 */
public class ListeImplUnmodifiable extends ListeImpl implements Liste<String>
{
    private List<String> liste;
    private String nom;

    /**
     * Creation d'une liste.
     *
     * @param nom le nom de cette liste
     */
    public ListeImplUnmodifiable(String nom)
    {
        super(nom);
        liste = Collections.unmodifiableList(new ArrayList<String>());
    }

    /**
     * Ajout d'un element a la liste.
     *
     * @param elt l'element a ajouter
     * @return true si la liste a ete modifiee par cet ajout, false sinon, (notamment en cas d'exception)
     */
    public boolean ajouter(String elt)
    {
        return liste.add(elt);
    }
}
