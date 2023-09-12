# odf2

import jcifs.smb.SmbFile;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFileInputStream;
import java.io.ByteArrayOutputStream;

public class JCIFSShareFolderExample {
    public static void main(String[] args) {
        // Define the shared folder path
        String sharedFolderPath = "smb://server/share";

        // Define the Excel file name you want to retrieve
        String excelFileName = "example.xlsx";

        // Define your credentials (username and password)
        String username = "your_username";
        String password = "your_password";

        try {
            // Create an SMB file object with the shared folder path and credentials
            SmbFile smbFile = new SmbFile(sharedFolderPath + "/" + excelFileName, new jcifs.smb.NtlmPasswordAuthentication(null, username, password));

            // Check if the shared file exists
            if (smbFile.exists() && smbFile.isFile()) {
                // Read the Excel file into a byte array
                try (SmbFileInputStream inputStream = new SmbFileInputStream(smbFile)) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] excelBytes = outputStream.toByteArray();

                    // Now you have the Excel file content in excelBytes byte array
                    // You can process or save it as needed
                }
            } else {
                System.err.println("Excel file does not exist in the shared folder.");
            }
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
