/**
 *
 */
package veinthrough.test.io.file;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.TimeZone;

import com.google.common.collect.Lists;
import veinthrough.test.AbstractUnitTester;
import veinthrough.test.util.DateFormatTest;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. mkdir()/mkdirs():
 *   mkdir(): parent must be existed.
 * 2. mkdir()/createNewFile():
 *   if parent doesn't exist, mkdir() return false while createNewFile() throw IOException().
 * 3. createNewFile():
 *   return true if the named file does not exist and was successfully created;
 *   false if the named file already exists.
 * 4. File(String)/File(URI):
 *   File(String) can handle both "\" and "/";
 *   URI can't handle "\", but only "/".
 *  </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Test contans:
 * 1. print separator.
 * 2. create directory.
 * 3. create sub directory.
 * 4. create file.
 * 5. list files in a directory.
 * 6. print file info.
 * 7. file descriptor
 * </pre>
 *
 */
@SuppressWarnings("unused")
public class FileTest extends AbstractUnitTester {
    private static final TimeZone SHANG_HAI = TimeZone.getTimeZone("Asia/Shanghai");
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS z";

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        try {
            _test();
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void _test() throws IOException, URISyntaxException {
        //testSeparator();
        //testCreateDir();
        //testCreateSubDir();
        //testCreateFile();
        //testListFiles();
        fileDescriptorTest();
    }

    private void testSeparator() {
        //";"
        System.out.printf("File.pathSeparator=\"%s\"\n", File.pathSeparator);
        System.out.printf("File.pathSeparatorChar=\"%c\"\n", File.pathSeparatorChar);
        //Windows:"\", Linux:"/"
        System.out.printf("File.separator=\"%s\"\n", File.separator);
        System.out.printf("File.separatorChar=\"%c\"\n", File.separatorChar);
    }

    //mkdir, parent must be existed
    //mkdirs: create dirs including non-existed parent dir
    private void testCreateDir() {
        //mkdir, parent must be existed
        File dir1 = new File("veinthrough/dir1");
        dir1.mkdir();
        //mkdirs: create dirs including non-existed parent dir
        File dir2 = new File("veinthrough/dir2");
        dir2.mkdirs();
        //mkdir, parent must be existed
        try {
            //should be "file:/E:" instead of "file:E:"
            File dir3 = new File(new URI("file:/E:/test/dir3"));
            dir3.mkdir();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void testCreateSubDir() {
        File sub1 = new File("veinthrough", "dir1");
        sub1.mkdir();

        File sub2 = new File("veinthrough/dir2");
        sub2.mkdir();

        //mkdirs include non-existed parent dir
        File dir3 = new File("veinthrough/dir3");
        dir3.mkdirs();

        try {
            //should be "file:/E:" instead of "file:E:"
            File dir4 = new File(new URI("file:/E:/test/dir4"));
            dir4.mkdirs();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //true if the named file does not exist and was successfully created;
    //false if the named file already exists
    //exception if the parent directory isn't existed
    private void testCreateFile() throws IOException, URISyntaxException {
        //parent directory must be existed
        File file1 = new File("test/file1.txt");
        file1.createNewFile();

        File file2 = new File("test", "file2.txt");
        file2.createNewFile();

        File dir = new File("test");
        //filePath = ...\veinthrough\test\test\file3.txt
        //File(String) can handle both "\" and "/"
        String filePath = dir.getAbsolutePath() + File.separator + "file3.txt";
        File file3 = new File(filePath);
        file3.createNewFile();

        //filePath = file:\...\veinthrough\test\test\file4.txt
        filePath = "file:\\" + dir.getAbsolutePath() + File.separator + "file4.txt";
        //URI can't handle "\", but only "/"
        filePath = filePath.replace("\\", "/");
        File file4 = new File(new URI(filePath));
        //URI uri = new URL(filePath).toURI();
        file4.createNewFile();

        //filePath = file:\...\veinthrough\test\test\file4.txt
        filePath = "file:\\" + dir.getAbsolutePath() + File.separator + "file5.txt";
        //the same effect
        //filePath = "file:/" + dir.getAbsolutePath() + File.separator + "file5.txt";
        //URI can't handle "\", but only "/"
        File file5 = new File(new URL(filePath).toURI());
        file5.createNewFile();
    }

    private void testListFiles() throws IOException {
        File dir = new File("test");
        List<File> file_list = Lists.newArrayList(dir.listFiles());
        for(int i=0; i<file_list.size(); i++){
            File file = file_list.get(i);
            String dirOrFile = "File";
            if(file.isDirectory()) {
                dirOrFile = "Directory";
                file_list.addAll(Lists.newArrayList(file.listFiles()));
            }
            System.out.printf("[%s:%s]:\n" + "%s",
                    dirOrFile, file.getName(), fileInfo(file));
        }

    }

    private String fileInfo(File file) throws IOException {
        return String.format("    parent:%s\n"
                + "    path:%s\n"
                + "    absolute path:%s\n"
                + "    canonical path:%s\n"
                + "    lastModified:%s\n",
                file.getParent(),
                file.getPath(),
                file.getAbsolutePath(),
                file.getCanonicalPath(),
                new DateFormatTest().formatDate(
                        file.lastModified(), DEFAULT_DATE_PATTERN, SHANG_HAI));
    }

    /*
     * test file descriptor FileOutputStram/FileInputStream
     */
    public void fileDescriptorTest() {
        testStandFD();
        String fileName = "test/file1.txt";
        testWriteFD(fileName);
        testReadFD(fileName);
    }

    //03 -- err, standard error
    //01 -- in, standard input
    //02 -- out, standard output
    //standard output stream should not be closed, otherwise, it may effect the output next.
    private void testStandFD() {
        @SuppressWarnings("resource")
        PrintStream out = new PrintStream(
            new FileOutputStream(FileDescriptor.out));
        out.println("standard output descriptor");
    }

    private void testWriteFD(String fileName) {
        try(FileOutputStream fos1 = new FileOutputStream(fileName);
            FileOutputStream fos2 = new FileOutputStream(fos1.getFD())) {
            System.out.printf("fdout(%s) is %s\n", fos1.getFD(), fos1.getFD().valid());
            fos1.write('A');
            fos2.write('a');
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void testReadFD(String fileName) {
        try(FileInputStream fis1 = new FileInputStream(fileName);
                FileInputStream fis2 = new FileInputStream(fis1.getFD())) {
                System.out.printf("fdin(%s) is %s\n", fis1.getFD(), fis1.getFD().valid());
                System.out.println((char)fis1.read());
                System.out.println((char)fis2.read());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FileTest().test();
    }

}
