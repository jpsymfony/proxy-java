package question1;

import java.util.Iterator;


/**
 * Une gestion de listes d'elements.
 *
 * @param <E> the type parameter
 * @author NSY102
 * @version Fevrier 2008
 */
public interface Liste<E> extends Iterable<E>
{
    // les operations

    /**
     * Ajout d'un element a la liste.
     *
     * @param elt l'element a ajouter
     * @return true si la liste a ete modifiee par cet ajout, false sinon, (notamment en cas d'exception)
     */
    public boolean ajouter(E elt);

    /**
     * Retrait de toutes les occurrences de cet element de la liste
     *
     * @param elt l'element a retirer
     * @return true si au moins un retrait a eu lieu, false sinon
     */
    public boolean retirer(E elt);

    /**
     * Vider cette liste (ou le retrait de tous les elements).
     *
     * @return true si la liste a ete modifiee, false sinon
     */
    public boolean vider();

    /**
     * Restauration de cette liste. implementation optionnelle.
     *
     * @return true si la restauration a eu lieu, false sinon
     */
    public boolean restaurer();

    // les lectures ou accesseurs ou encore getter...

    /**
     * Test de la presence d'un element.
     *
     * @param elt l'element a comparer
     * @return true si elt est present, false autrement
     */
    public boolean estPresent(E elt);

    /**
     * Obtention du nombre d'elements.
     *
     * @return le nombre d'elements de la liste
     */
    public int taille();

    /**
     * Parcours de la liste.
     *
     * @return un iterateur
     */
    public Iterator<E> iterator();

    /**
     * Obtention d'une liste sous ce format "[elt1, elt2, ...]",  format habituel des API predefinies.
     * Une liste vide est notee [].
     *
     * @return la liste sous un format "lisible"
     */
    public String toString();

}
