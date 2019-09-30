/**
 *
 */
package veinthrough.test._class.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import veinthrough.test.AbstractUnitTester;

/**
 * This class give a implementation of printing all fields and coordinated values of a object
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * [Class]        native boolean isArray()
 * [Class]        native Class<?> getComponentType():only be called by a array type
 * [Class]        native boolean isPrimitive():是否是基本类型
 * [Class]        Field[] getDeclaredFields()
 * [Class]        native Class<? super T> getSuperclass()
 * [Field]        Object get(Object obj):获取对象obj的field对应的域值
 * [AccessibleObject][static]setAccessible(AccessibleObject[] array, boolean flag)
 * </pre>
 */
public class ObjectAnalyzer extends AbstractUnitTester {

    private final List<Object> visited = new ArrayList<>();

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        ArrayList<Integer> squares = new ArrayList<>();
        for(int i=1; i<5; i++) {
            squares.add(i*i);
        }
        System.out.println(toString(squares));

    }

    /*
     * analyze a object to a string.
     */
    public String toString(Object obj) {
        if(obj == null) return "null";

        // have already visited to get rid of recursive refering
        if(visited.contains(obj)) return "...";
        visited.add(obj);

        Class<?> clazz = obj.getClass();

        // string type
        if(clazz == String.class) return (String)obj;

        String result = "";
        // array type
        if(clazz.isArray()) {
            result += clazz.getComponentType() + "[]{";
            int length = Array.getLength(obj);
            for(int i=0; i<length; i++) {
                if(i>0) result += ",";
                Object value = Array.get(obj, i);
                if(clazz.getComponentType().isPrimitive()) result += value;
                else result += toString(value);
            }
            result += "}";
        }

        // other type
        do {
            result += clazz.getName() + "[";

            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for(Field field : fields) {
                if(!Modifier.isStatic(field.getModifiers())) {
                    //not the first field
                    if(!result.endsWith("[")) result += ",";
                    result += field.getName() + "=";
                    try {
                        Class<?> field_clazz = field.getType();
                        Object value = field.get(obj);
                        if(field_clazz.isPrimitive()) result += value;
                        //recursion of the value
                        else result += toString(value);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            result += "]";

            //iterate fileds of super class
            clazz = clazz.getSuperclass();
        } while(clazz != null);

        return result;
    }

    public static void main(String[] args) {
        new ObjectAnalyzer().test();
    }

}
