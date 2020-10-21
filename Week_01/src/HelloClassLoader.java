import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class HelloClassLoader extends ClassLoader{
    public static String XCLASS_PATH = "Hello.xlass";
    public static void main(String[] args) {
        Class<?> clzz = new HelloClassLoader().findClass("Hello");
        try {
            Method hello = clzz.getDeclaredMethod("hello");
            hello.setAccessible(true);
            hello.invoke(clzz.newInstance());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Class<?> findClass(String className) {
        byte[] bytes = convertFileToBytes();
        return defineClass(className, bytes, 0, bytes.length);
    }

    private byte[] convertFileToBytes() {
        List<Byte> bytes = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(new File(XCLASS_PATH))) {
            int singleByte;
            // read byte by byte
            while((singleByte = fis.read()) != -1) {
                byte converted = (byte) (255 - singleByte);
                bytes.add(converted);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] converted = new byte[bytes.size()];
        for(int i = 0; i < converted.length; i++) {
            converted[i] = bytes.get(i).byteValue();
        }
        return converted;
    }

}
