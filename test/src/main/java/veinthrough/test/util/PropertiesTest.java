/**
 * 
 */
package veinthrough.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.google.common.base.Preconditions;

import veinthrough.test.AbstractUnitTester;

/**
 * Test for Properties.
 * @author veinthrough
 * <p>---------------------------------------------------------
 * </pre>
 * Disadvantage:
 *  1. 没有标准的为配置文件命名的规则，容易造成配置文件名冲突。
 *  2. （似乎）只能处理字符串类型
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Constructs:
 *  Properties()
 *  Properties(Properties defaults)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. get/put
 * 2. load/store
 * 3. 使用二级属性映射来实现默认属性；也可以get时给一个默认值
 * </pre>
 *
 */
public class PropertiesTest extends AbstractUnitTester {
    private static final String DIR_NAME = "properties";
    private static final String FILE_NAME = "properties_test.xml";
    private static final int DEFAULT_LEFT = 0;
    private static final int DEFAULT_TOP = 0;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private static final String DEFAULT_TITLE = "";

    private int x;
    private int y;
    private int w;
    private int h;
    private String t;
    
    Properties properties;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {        
        //使用二级属性映射来实现默认属性
        //Properties(Properties defaults)
        properties = new Properties(defaultProperties());
        
        String userDir = System.getProperty("user.home");
        File propertiesDir = new File(userDir, DIR_NAME);
        if(!propertiesDir.exists()) {
            propertiesDir.mkdir();
        }
        File propertiesFile = new File(propertiesDir, FILE_NAME);
        
        load(propertiesFile);
        modify();
        store(propertiesFile);        
    }
    
    private Properties defaultProperties() {
        Properties defaultProperties = new Properties();
        //只能put字符串类型
        defaultProperties.put("left", ""+DEFAULT_LEFT);
        defaultProperties.put("top", ""+DEFAULT_TOP);
        defaultProperties.put("width", ""+DEFAULT_WIDTH);
        defaultProperties.put("height", ""+DEFAULT_HEIGHT);
        defaultProperties.put("title", DEFAULT_TITLE);
        return defaultProperties;
    }
    
    private void load(File file) {
        Preconditions.checkArgument(file!=null && file.exists());

        try(FileInputStream fis = new FileInputStream(file)) {
            //load properties
            properties.load(fis);
            
            //if property isn't loaded, it will get from defaults
            //只能get字符串类型然后转换
            x = Integer.parseInt(properties.getProperty("left"));
            System.out.printf("left: %d\n", x);
            y = Integer.parseInt(properties.getProperty("top"));
            System.out.printf("top: %d\n", y);
            w = Integer.parseInt(properties.getProperty("width"));
            System.out.printf("width: %d\n", w);
            h = Integer.parseInt(properties.getProperty("height"));
            System.out.printf("height: %d\n", h);
            t = properties.getProperty("title");
            System.out.printf("title: %s\n", t);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
    
    private void modify() {
        x += 1;
        y += 1;
        w += 1;
        h += 1;
        t += "-";       
    }
    
    private void store(File file) {
        Preconditions.checkArgument(file!=null && file.exists());

        try(FileOutputStream fos = new FileOutputStream(file)) {
            properties.put("left", ""+x);
            properties.put("top", ""+y);
            properties.put("width", ""+w);
            properties.put("height", ""+h);
            properties.put("title", t);
            //store properties
            properties.store(fos, "Frame configuration");
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }

    public static void main(String[] args) {
        new PropertiesTest().test();
    }

}
