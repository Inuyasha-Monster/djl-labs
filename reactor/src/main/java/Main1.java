import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> list = new ArrayList<>();
        //final String s = list.get(0);
        //System.out.println("s = " + s);

        Runtime runtime = Runtime.getRuntime();

        final Process process = new ProcessBuilder().command("bash", "/c", "ping baidu.com").start();
        process.waitFor();
        final InputStream inputStream = process.getInputStream();
        final byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        final String s1 = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("s1 = " + s1);


    }
}
