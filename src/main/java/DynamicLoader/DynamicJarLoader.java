package DynamicLoader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;


public class DynamicJarLoader
{
    private File jarFile;
    private URL[] jarURLs;
    
    //Since we're setting the ContextLoader to our custom jarLoader, 
    //we need to revert back the actual default loader for other classes and methods.
    private final ClassLoader defaultLoader = Thread.currentThread().getContextClassLoader();    
    
    /**
     * @param pathToJar Location in resources folder where the jars are present.
     * @return An instance of DynamicJarLoader
     */
    public static DynamicJarLoader createDynamicJarLoader(String pathToJar)
    {
	return new DynamicJarLoader(pathToJar);
    }
    
    private DynamicJarLoader(String pathToJar)
    {
	try 
	{
	    URL jarPath = defaultLoader.getResource(pathToJar);
	    jarFile = new File(jarPath.toURI());
	    File[] jars = jarFile.listFiles();
	    jarURLs = new URL[jars.length];
	    
	    for(int i=0; i<jars.length; i++)
		jarURLs[i] = jars[i].toURI().toURL();
	}
	catch (URISyntaxException e)
	{
	    e.printStackTrace();
	}
	catch (MalformedURLException e)
	{
	    e.printStackTrace();
	}
    }
    
    /**
     * This is only a demonstration on how to use the dynamically loaded classes using reflection.
     * @return The return type can be changed to whatever type we want.
     */
    public Object performOperations()
    {
	URLClassLoader jarLoader = new URLClassLoader(jarURLs);
	//set the context loader to our custom loader.
	Thread.currentThread().setContextClassLoader(jarLoader);
	
	try
	{
	    //load the required class.
	    Class<?> SomeClass = jarLoader.loadClass("com.x.y.SomeClass");
	    
	    //create an instance using it's constructor.
	    Constructor<?> someClassConstructor = SomeClass.getDeclaredConstructor(String.class);
	    Object someClassInstance = someClassConstructor.newInstance("String argument");
	    
	    //to invoke a method.
	    Method methodInSomeClass = SomeClass.getMethod("methodName", String.class);
	    methodInSomeClass.invoke(someClassInstance, "String argument");
	    
	    //to get a variable or field.
	    Field varInSomeClass = SomeClass.getField("var name");
	    String value = (String) varInSomeClass.get(someClassInstance);
	    
	}
	catch (ClassNotFoundException e)
	{
	    e.printStackTrace();
	}
	catch (NoSuchMethodException e)
	{
	    e.printStackTrace();
	}
	catch (SecurityException e)
	{
	    e.printStackTrace();
	}
	catch (InstantiationException e)
	{
	    e.printStackTrace();
	}
	catch (IllegalAccessException e)
	{
	    e.printStackTrace();
	}
	catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	}
	catch (InvocationTargetException e)
	{
	    e.printStackTrace();
	}
	catch (NoSuchFieldException e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    //revert back to the default loader.
	    Thread.currentThread().setContextClassLoader(defaultLoader);
	}
	
	return null;
    }
}
