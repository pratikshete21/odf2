import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetAllFilesFromShareFolderToByteArray {

    public static void main(String[] args) throws Exception {
        // Create a SMBClient instance
        SMBClient client = new SMBClient();

        // Connect to the server
        client.connect("192.168.1.100", 445);

        // Authenticate with the server
        Session session = client.authenticate("username", "password");

        // Get the shared folder
        DiskShare share = (DiskShare) session.connectShare("/sharedfolder");

        // Get the list of files in the directory
        Path directory = Paths.get("/sharedfolder");
        Files.walk(directory).forEach(file -> {
            if (Files.isRegularFile(file)) {
                // Get the file
                File smbFile = share.openFile(file.toString());

                // Read the file into a byte array
                byte[] bytes = new byte[(int) smbFile.length()];
                smbFile.read(bytes);

                // Do something with the file
                System.out.println(new String(bytes));

                // Close the file
                smbFile.close();
            }
        });

        // Disconnect from the server
        client.disconnect();
    }
}
