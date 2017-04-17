package question2;

import question1.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * The type Dynamic proxy filtre.
 *
 * @param <T> the type parameter
 */
public class DynamicProxyFiltre<T> implements InvocationHandler
{
    private Liste<T> liste;
    private String[] methodesPermises;

    /**
     * Instantiates a new Dynamic proxy filtre.
     *
     * @param liste            the liste
     * @param methodesPermises the methodes permises
     */
    public DynamicProxyFiltre(Liste<T> liste, String[] methodesPermises)
    {
        this.liste = liste;
        this.methodesPermises = methodesPermises;
    }

    /**
     * Gets proxy.
     *
     * @param <T>              the type parameter
     * @param liste            the liste
     * @param methodesPermises the methodes permises
     * @return the proxy
     */
    public static <T> Liste<T> getProxy(Liste<T> liste, String[] methodesPermises)
    {
        return (Liste<T>) Proxy.newProxyInstance(
                liste.getClass().getClassLoader(),
                liste.getClass().getInterfaces(),
                new DynamicProxyFiltre<T>(liste, methodesPermises));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException
    {
        try {
            for (String nom : methodesPermises) {
                if (method.getName().equals(nom)) {
                    return method.invoke(liste, args);
                }
            }
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException");
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " +
                    e.getMessage());
        }

        throw new IllegalAccessException(method.getName() + " est une methode inhibee ");
    }
}
