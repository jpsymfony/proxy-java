package question2;

import question1.*;
import java.util.*;

/**
 * The type Dynamic proxy filtre test.
 */
public class DynamicProxyFiltreTest extends junit.framework.TestCase
{
    /**
     * Test acces restreint 1.
     *
     * @throws Exception the exception
     */
    public void testAccesRestreint1() throws Exception
    {
        String methodesPermises[] = new String[]{"ajouter", "estPresent", "taille", "toString"};
        Liste<String> liste = DynamicProxyFiltre.getProxy(new ListeImpl("listeA"), methodesPermises);

        try {
            liste.ajouter("test");
            liste.ajouter("test2");
        } catch (Exception e) {
            fail("une exception pour une methode autorisee ???");
        }
        assertTrue(liste.taille() == 2);
        try {
            liste.vider();
            fail("une exception est attendue pour une methode inhibee !");
        } catch (Throwable e) {
            assertTrue("IllegalAccessException est attendue ?", e.getCause() instanceof IllegalAccessException);
        }
    }

    /**
     * Test acces restreint 1.
     *
     * @throws Exception the exception
     */
    public void testAccesRestreint2() throws Exception
    {
        String methodesPermises[] = new String[]{"ajouter"};
        Liste<String> liste = DynamicProxyFiltre.getProxy(new ListeImplUnmodifiable("listeA"), methodesPermises);

        try {
            liste.ajouter("test");
            fail("une exception est attendue pour une methode inhibee !");
        } catch (Exception e) {
            System.out.println(e.getCause());
            assertTrue("IllegalAccessException est attendue ?", e.getCause() instanceof IllegalAccessException);
        }
    }
}
