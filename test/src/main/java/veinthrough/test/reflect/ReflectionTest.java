/**
 *
 */
package veinthrough.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 *
 * This class conduct a test of printing a class declaration
 */
public class ReflectionTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        // read class name from command line args or user input
        String name;
        String[] args = this.getArgs();
        if (args != null && args.length > 0) name = args[0];
        else
        {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date): ");
            name = in.next();
            in.close();
        }

        try
        {
            // print class name and superclass name (if != Object)
            Class<?> clazz = Class.forName(name);
            Class<?> supercl = clazz.getSuperclass();
            String modifiers = Modifier.toString(clazz.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print("class " + name);
            if (supercl != null && supercl != Object.class) {
                System.out.print(" extends " + supercl.getName());
            }

            System.out.print("\n{\n");
            printConstructors(clazz);
            System.out.println();
            printMethods(clazz);
            System.out.println();
            printFields(clazz);
            System.out.println("}");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        System.exit(0);

    }

    private static void printConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for(Constructor<?> constructor : constructors) {
            String name = constructor.getName();
            System.out.print("    ");
            String modifiers = Modifier.toString(constructor.getModifiers());
            if(modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(name + "(");

            //print parameter types
            Class<?>[] paramTypes = constructor.getParameterTypes();
            int numParams = paramTypes.length;
            for(int j=0; j<numParams; j++) {
                if(j>0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    private static void printMethods(Class<?> clazz)
    {
         Method[] methods = clazz.getDeclaredMethods();

         for (Method m : methods)
         {
             Class<?> retType = m.getReturnType();
             String name = m.getName();

             System.out.print("    ");
             // print modifiers, return type and method name
             String modifiers = Modifier.toString(m.getModifiers());
             if (modifiers.length() > 0) System.out.print(modifiers + " ");
             System.out.print(retType.getName() + " " + name + "(");

             // print parameter types
             Class<?>[] paramTypes = m.getParameterTypes();
             for (int j = 0; j < paramTypes.length; j++)
             {
                 if (j > 0) System.out.print(", ");
                 System.out.print(paramTypes[j].getName());
             }
             System.out.println(");");
         }
    }

    private static void printFields(Class<?> clazz)
    {
         Field[] fields = clazz.getDeclaredFields();

         for (Field field : fields)
         {
             Class<?> type = field.getType();
             String name = field.getName();
             System.out.print("    ");
             String modifiers = Modifier.toString(field.getModifiers());
             if (modifiers.length() > 0) System.out.print(modifiers + " ");
             System.out.println(type.getName() + " " + name + ";");
         }
    }

    public static void main(String[] args) {
        new ReflectionTest().test();
    }

}
