/**
 * 
 */
package veinthrough.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import com.google.common.base.Preconditions;

import veinthrough.test.AbstractUnitTester;

/**
 * Test for Properties.
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * Properties/Preferences:
 *  Properties:
 *      1. 没有标准的为配置文件命名的规则，容易造成配置文件名冲突。
 *      2. （似乎）只能处理字符串类型
 *  Preferences:
 *      1. 可以处理其他类型
 *      2. 提供了一个与平台无关的中心知识库，类似注册表，其实就是用注册表实现的
 *      3. 可以像日志一样每个类可以有一个单独的节点
 *      4. 可以有change listener
 *      5. 不及时清理/维护，会导致中心知识库膨胀
 *  Disadvantage:
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Warning/Error:
 * 1. WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002.
 *  Windows RegCreateKeyEx(...) returned error code 5. 
 *  解决办法：运行注册表regedit.exe，进入HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft，
 *  右击JavaSoft目录，选择新建->项（key），命名为Prefs
 * 
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Constructs/APIs:
 *  Preferences systemNodeForPackage(Class<?> c) 
 *  Preferences userNodeForPackage(Class<?> c)
 *  addPreferenceChangeListener(PreferenceChangeListener pcl)
 *  addNodeChangeListener(NodeChangeListener ncl)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. get/put
 * 2. load/store
 * 3. get时可以给一个默认值来实现默认属性
 * </pre>
 *
 */
public class PreferencesTest extends AbstractUnitTester {
    private static final String DIR_NAME = "preferences";
    private static final String FILE_NAME = "preferences_test.xml";
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
    
    Preferences preference = Preferences.userNodeForPackage(getClass());

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {        
        String userDir = System.getProperty("user.home");
        File propertiesDir = new File(userDir, DIR_NAME);
        if(!propertiesDir.exists()) {
            propertiesDir.mkdir();
        }
        File propertiesFile = new File(propertiesDir, FILE_NAME);
        
        retrieve();
        modify();
        store(propertiesFile);
        load(propertiesFile);
        retrieve();
    }
    
    private void retrieve() {
        x = preference.getInt("left", DEFAULT_LEFT);
        System.out.printf("left: %d\n", x);
        y = preference.getInt("top", DEFAULT_TOP);
        System.out.printf("top: %d\n", y);
        w = preference.getInt("width", DEFAULT_WIDTH);
        System.out.printf("width: %d\n", w);
        h = preference.getInt("height", DEFAULT_HEIGHT);
        System.out.printf("height: %d\n", h);
        t = preference.get("title", DEFAULT_TITLE);
        System.out.printf("title: %s\n", t);
        
    }
    
    private void load(File file) {
        Preconditions.checkArgument(file!=null && file.exists());

        try(FileInputStream fis = new FileInputStream(file)) {
            //load properties
            Preferences.importPreferences(fis);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidPreferencesFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
    
    private void modify() {
        x += 1;     preference.put("left", ""+x);
        y += 1;     preference.put("top", ""+y);
        w += 1;     preference.put("width", ""+w);
        h += 1;     preference.put("height", ""+h);
        t += "-";   preference.put("title", t);
    }

    private void store(File file) {
        Preconditions.checkArgument(file!=null && file.exists());

        try(FileOutputStream fos = new FileOutputStream(file)) {
            preference.exportSubtree(fos);            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BackingStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }

    public static void main(String[] args) {
        new PreferencesTest().test();
    }

}
