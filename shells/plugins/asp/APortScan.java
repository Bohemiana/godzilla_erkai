package shells.plugins.asp;

import core.annotation.PluginAnnotation;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import shells.plugins.generic.PortScan;
import util.functions;

@PluginAnnotation(
        payloadName = "AspDynamicPayload",
        Name = "PortScanemojiuD83DuDE00\uD83D\uDE00", // Name中继续保持原字符串
        DisplayName = "端口扫描"
)
public class APortScan extends PortScan {
    private static final String CLASS_NAME = "PortScan";

    public APortScan() {
    }

    public byte[] readPlugin() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(String.format("assets/%s.asp", "PortScan"));
        return functions.readInputStreamAutoClose(inputStream);
    }

    public String getClassName() {
        return "PortScan";
    }

    // 在UI界面中显示图标和文本
    public static void main(String[] args) {
        // 加载图标
        ImageIcon icona = new ImageIcon(APortScan.class.getResource("/shells/plugins/asp/chat.ico"));

        // 使用JButton来组合图标和文本
        JButton button = new JButton("PortScanemojiuD83DuDE00aaaaaa", icona);
        button.setHorizontalAlignment(SwingConstants.LEFT); // 设置图标和文本对齐方式

        // 创建UI窗口来显示
        JFrame frame = new JFrame("端口扫描");
        frame.add(button);
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}