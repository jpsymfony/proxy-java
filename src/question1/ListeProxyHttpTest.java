package question1;

import java.util.Random;

/**
 * The type Liste proxy http test.
 */
public class ListeProxyHttpTest extends junit.framework.TestCase
{
    /**
     * Test ajouter.
     */
    public void testAjouter()
    {
        String nomDeLaListe = "L_" + new Random().nextInt(1000000);
        Liste<String> listeHTTP = new question1.ListeProxyHttp(nomDeLaListe);
        assertEquals(true, listeHTTP.ajouter("un"));
        assertEquals(true, listeHTTP.ajouter("deux"));
        assertEquals(true, listeHTTP.ajouter("trois"));
        assertEquals(true, listeHTTP.ajouter("quatre"));
        assertEquals(true, listeHTTP.ajouter("cinq"));
        System.out.println(" verifiez maintenant depuis votre navigateur : http://jfod.cnam.fr/jnews/tests/tp_proxy.html?commande=toString&nom=" + nomDeLaListe);
    }

    /**
     * Test restaurer.
     */
    public void testRestaurer()
    {
        String nomDeLaListe = "L_" + new Random().nextInt(1000000);

        Liste<String> l = new question1.ListeProxyHttp(nomDeLaListe);
        assertEquals(true, l.ajouter("I"));
        assertEquals(true, l.ajouter("II"));
        assertEquals(true, l.ajouter("III"));
        assertEquals(true, l.ajouter("IV"));
        assertEquals(true, l.ajouter("V"));

        l = null; // perte de tous ses elements !

        l = new question1.ListeProxyHttp(nomDeLaListe);
        l.restaurer();
        System.out.println(nomDeLaListe + " restauree : " + l);
        assertEquals(true, l.ajouter("VI"));
        System.out.println(" depuis votre navigateur, cette liste : http://jfod.cnam.fr/jnews/tests/tp_proxy.html?commande=toString&nom=" + nomDeLaListe);
    }
}