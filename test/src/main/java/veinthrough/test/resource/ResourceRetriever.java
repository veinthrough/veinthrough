/**
 *
 */
package veinthrough.test.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>
 * Test contains:
 * <p>
 * 1. get content from resource file.
 * <p>
 * 2. get content from resource file by stream
 */
public class ResourceRetriever extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        //get content from resource file
        test1();
        //get content from resource file by stream
        test2();
    }

    private void test1() {
        try {
            System.out.println(
                    getStringFromResource("resource_ResourceReader.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void test2() {
        try {
            System.out.println(
                    getStringFromResourceByStream("resource_ResourceReader.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // get file from classpath, resources folder
    public String getExactFileNameOfResource(String resourceName) throws IOException {

        ClassLoader classLoader = ResourceRetriever.class.getClassLoader();

        URL resource = classLoader.getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            String path = resource.getFile();
            //if path contains chinese/ space, it will be encoded in UTF8,
            //then it will contains %
            if(path.contains("%")) {
                //.../target/classes/resource_ResourceReader.properties
                path = java.net.URLDecoder.decode(path,Charsets.UTF_8.name());
            }
            return path;
        }

    }

    public String getStringFromResource(String resourceName) throws IOException {
        Preconditions.checkNotNull(resourceName);

        String fileName = getExactFileNameOfResource(resourceName);
        File file = new File(fileName);
        String result = "";
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        }
        return result;

    }

    // get file from classpath, resources folder
    // It's not a good idea to return a stream, otherwise, where should the stream be closed?
    // It violate the design patterns
    // Use {@link #getStringFromResourceByStream(String)} to directly get contents instead.
    @Deprecated
    public InputStream getStreamFromResource(String resourceName) throws UnsupportedEncodingException {

        ClassLoader classLoader = getClass().getClassLoader();

        InputStream is = classLoader.getResourceAsStream(resourceName);
        if (is == null) {
            throw new IllegalArgumentException("file is not found!");
        }
        return is;
    }

    public String getStringFromResourceByStream(String resourceName) throws IOException {
        Preconditions.checkNotNull(resourceName);

        byte buf[];
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new IllegalArgumentException("file is not found!");
            }
            buf = new byte[is.available()];
            is.read(buf);
        }
        return new String(buf);
    }

    public static void main(String[] args) {
        new ResourceRetriever().test();
    }

}
