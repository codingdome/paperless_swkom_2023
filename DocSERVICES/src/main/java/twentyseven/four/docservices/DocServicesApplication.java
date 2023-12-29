package twentyseven.four.docservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocServicesApplication {

    public static void main(String[] args) {

        // Set the JNA library path
//        System.setProperty("jna.library.path", "/opt/homebrew/Cellar/tesseract/5.3.3/lib");

        // Start the Spring application
        SpringApplication.run(DocServicesApplication.class, args);
    }

}
