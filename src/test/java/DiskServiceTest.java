import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class DiskServiceTest {
    private DiskService diskService;

    @Before
    public void authorize(){
        Properties property = new Properties();
        try {
            FileInputStream in = new FileInputStream("src/test/resources/config.properties");
            property.load(in);
            String user = property.getProperty("user");
            String token = property.getProperty("token");
            diskService = new DiskService(user, token);
        } catch (IOException e) {
            System.err.println("Отсутствует конфигурационный файл.");
        }
    }

    @Test
    public void testFilesInfo() throws Exception {
        assertTrue(diskService.getFilesInfo("image").contains("Мишки.jpg image"));
        assertFalse(diskService.getFilesInfo("image").contains("Мишки.jpg audio"));
    }

    @Test
    public void getFile(){
        String existingFile = diskService.getFilesInfo("image").get(0).split(" ")[0];
        assertTrue(diskService.getFile(existingFile));
        assertFalse(diskService.getFile("qqq"));
    }

    @Test
    public void uploadFile() {
        assertTrue(diskService.uploadFile("src/downloads/кот.jpg", "/", "src//test//resources//"));
        assertTrue(diskService.getFilesInfo("image").contains("кот.jpg image"));
    }

    @Test
    public void makeFolder(){
        assertTrue(diskService.makeFolder("/", "новая папка"));
    }

    @Test
    public void deleteFile(){
        diskService.uploadFile("src/downloads/кот.jpg", "/", "src//test//resources//");
        assertTrue(diskService.deleteFile("src/downloads/кот.jpg"));
    }
}
