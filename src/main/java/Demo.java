import DynamicLoader.DynamicJarLoader;


public class Demo
{
    public static void main(String[] args)
    {
	DynamicJarLoader dynLoader = DynamicJarLoader.createDynamicJarLoader("jars");
	dynLoader.performOperations();
    }
}
