package question2;

import question1.*;
import java.util.*;

public class DynamicProxyFiltreTest extends junit.framework.TestCase
{

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
}
