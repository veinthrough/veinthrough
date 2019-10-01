/**
 *
 */
package veinthrough.test._class.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import veinthrough.test.AbstractUnitTester;

/**
 * This class give a implementation of printing a class declaration by reflect
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * [Class][static]Class<?> ClassforName(String className)
 * [Class]        Class<? super T> getSuperclass()
 * [Class]        String getName()/getSimpleName()
 * [Class]        [native]int getModifiers()
 * [Class]        Constructor<?>[] getDeclaredConstructors()/
 *                Method[] getDeclaredMethods()
 *                Field[] getDeclaredFields()
 * [Constructor]        String getName()
 * [Constructor]        int getModifiers()
 * [Constructor]        Class<?>[] getParameterTypes()
 * [Method]        String getName()
 * [Method]        Class<?> getReturnType()
 * [Method]        Class<?>[] getParameterTypes()
 * [Method]        int getModifiers()
 * [Field]        String getName()
 * [Field]        Class<?> getType()
 * [Field]        int getModifiers()
 * [Modifier][static]String toString(int mod)
 * </pre>
 */
public class ClassAnalyzer extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        System.out.println(analyze(ClassAnalyzer.class));
    }

    public String analyze(String className) {
        String result = "";
        try {
            // print class name and superclass name (if != Object)
            Class<?> clazz = Class.forName(className);
            result = analyze(clazz);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String analyze(Class<?> clazz) {
        String result = "";
        // print class name and superclass name (if != Object)
        Class<?> supercl = clazz.getSuperclass();
        String modifiers = Modifier.toString(clazz.getModifiers());
        if (modifiers.length() > 0) result += modifiers + " ";
        result += "class " + clazz.getName();
        if (supercl != null && supercl != Object.class) {
            result += " extends " + supercl.getSimpleName();
        }

        result += "\n{\n";
        result += analyzeConstructors(clazz);
        result += "\n";
        result += analyzeMethods(clazz);
        result += "\n";
        result += analyzeFields(clazz);
        result += "}\n";
        return result;
    }

    private String analyzeConstructors(Class<?> clazz) {
        String result = "";
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for(Constructor<?> constructor : constructors) {
            String name = constructor.getName();
            result += "    ";
            String modifiers = Modifier.toString(constructor.getModifiers());
            if(modifiers.length() > 0) result += modifiers + " ";
            result += name + "(";

            //print parameter types
            Class<?>[] paramTypes = constructor.getParameterTypes();
            int numParams = paramTypes.length;
            for(int j=0; j<numParams; j++) {
                if(j>0) result += ", ";
                result += paramTypes[j].getSimpleName();
            }
            result += ");\n";
        }
        return result;
    }

    private String analyzeMethods(Class<?> clazz) {
        String result = "";
        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods) {
            Class<?> retType = m.getReturnType();
            String name = m.getName();

            result += "    ";
            // print modifiers, return type and method name
            String modifiers = Modifier.toString(m.getModifiers());
            if (modifiers.length() > 0) result += modifiers + " ";
            result += retType.getSimpleName() + " " + name + "(";

            // print parameter types
            Class<?>[] paramTypes = m.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) result += ", ";
                result += paramTypes[j].getSimpleName();
            }
            result += ");\n";
        }
        return result;
    }

    private String analyzeFields(Class<?> clazz) {
        String result = "";
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Class<?> type = field.getType();
            String name = field.getName();
            result += "    ";
            String modifiers = Modifier.toString(field.getModifiers());
            if (modifiers.length() > 0) result += modifiers + " ";
            result += type.getSimpleName() + " " + name + ";\n";
        }
        return result;
    }

    public static void main(String[] args) {
        new ClassAnalyzer().test();
    }

}
