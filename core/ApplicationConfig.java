/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package core;

import core.EasyI18N;
import core.ui.component.dialog.GOptionPane;
import core.ui.component.dialog.ImageShowDialog;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import util.Log;
import util.functions;

public class ApplicationConfig {
    private static final String GITEE_CONFIG_URL = "https://gitee.com/beichendram/Godzilla/raw/master/%s";
    private static final String GIT_CONFIG_URL = "https://raw.githubusercontent.com/BeichenDream/Godzilla/master/%s";
    private static String ACCESS_URL = "https://gitee.com/beichendram/Godzilla/raw/master/%s";
    public static final String GIT = "https://github.com/BeichenDream/Godzilla";
    private static final HashMap<String, String> headers = new HashMap();

    public static void invoke() {
        if (functions.getCurrentJarFile() == null) {
            return;
        }
        HashMap<String, String> configMap = null;
        try {
            configMap = ApplicationConfig.getAppConfig(String.format(GITEE_CONFIG_URL, "application.config"));
            ACCESS_URL = GITEE_CONFIG_URL;
        } catch (Exception e) {
            try {
                configMap = ApplicationConfig.getAppConfig(String.format(GIT_CONFIG_URL, "application.config"));
                ACCESS_URL = GIT_CONFIG_URL;
            } catch (Exception e2) {
                Log.error("Network connection failure");
            }
        }
        try {
            String tipString;
            HashMap<String, String> md5SumMap = ApplicationConfig.getAppConfig(String.format(ACCESS_URL, "hashsumJar"));
            String hashString = md5SumMap.get("4.01");
            File jarFile = functions.getCurrentJarFile();
            String jarHashString = new String();
            if (jarFile != null) {
                FileInputStream inputStream = new FileInputStream(jarFile);
                byte[] jar = functions.readInputStream(inputStream);
                inputStream.close();
                jarHashString = functions.SHA(jar, "SHA-512");
            }
            if (hashString != null) {
                if (jarFile != null) {
                    if (!jarHashString.equals(hashString)) {
//                        tipString = EasyI18N.getI18nString("\u4f60\u4f7f\u7528\u7684\u8f6f\u4ef6\u53ef\u80fd\u5df2\u88ab\u75c5\u6bd2\u611f\u67d3   \u6587\u4ef6\u54c8\u5e0c\u6548\u9a8c\u5931\u8d25\r\n\u6548\u9a8cJar\u54c8\u5e0c:%s\r\n\u672c\u5730Jar\u54c8\u5e0c:%s", hashString, jarHashString);
//                        GOptionPane.showMessageDialog(null, tipString, EasyI18N.getI18nString("\u8b66\u544a\t\u5f53\u524d\u7248\u672c:", "4.01"), 2);
//                        Log.error(String.format(tipString, hashString, jarHashString));
//                        System.exit(0);
                    } else {
                        Log.error(EasyI18N.getI18nString("\u6548\u9a8cHash\u6210\u529f   Hash Url:%s\r\n\u6548\u9a8cJar\u54c8\u5e0c:%s\r\n\u672c\u5730Jar\u54c8\u5e0c:%s", String.format(ACCESS_URL, "hashsumJar"), hashString, jarHashString));
                    }
                } else {
                    tipString = EasyI18N.getI18nString("\u672a\u627e\u5230Jar\u4f4d\u7f6e\r\n\u4f60\u4f7f\u7528\u7684\u8f6f\u4ef6\u53ef\u80fd\u5df2\u88ab\u75c5\u6bd2\u611f\u67d3   \u6587\u4ef6\u54c8\u5e0c\u6548\u9a8c\u5931\u8d25");
                    GOptionPane.showMessageDialog(null, tipString, EasyI18N.getI18nString("\u8b66\u544a\t\u5f53\u524d\u7248\u672c:%s", "4.01", hashString), 2);
                    Log.error(tipString);
                    System.exit(0);
                }
            } else {
                tipString = EasyI18N.getI18nString("\u672a\u627e\u5230\u5f53\u524d\u7248\u672c(%s)\u7684Hash\r\n\u5f53\u524dHash:%s\r\n\u4f60\u4f7f\u7528\u7684\u8f6f\u4ef6\u53ef\u80fd\u5df2\u88ab\u75c5\u6bd2\u611f\u67d3   \u6587\u4ef6\u54c8\u5e0c\u6548\u9a8c\u5931\u8d25", "4.01", jarHashString);
                JOptionPane.showMessageDialog(null, tipString, EasyI18N.getI18nString("\u8b66\u544a\t\u5f53\u524d\u7248\u672c:%s", "4.01"), 2);
                Log.error(String.format(tipString, "4.01"));
                System.exit(0);
            }
        } catch (Exception e) {
            Log.error(e);
        }
        if (configMap != null && configMap.size() > 0) {
            String version = configMap.get("currentVersion");
            boolean isShowGroup = Boolean.valueOf(configMap.get("isShowGroup"));
            String wxGroupImageUrl = configMap.get("wxGroupImageUrl");
            String showGroupTitle = configMap.get("showGroupTitle");
            String gitUrl = configMap.get("gitUrl");
            boolean isShowAppTip = Boolean.valueOf(configMap.get("isShowAppTip"));
            String appTip = configMap.get("appTip");
            if (version != null && wxGroupImageUrl != null && appTip != null && gitUrl != null) {
                if (functions.stringToint(version.replace(".", "")) > functions.stringToint("4.01".replace(".", ""))) {
                    GOptionPane.showMessageDialog(null, EasyI18N.getI18nString("\u65b0\u7248\u672c\u5df2\u7ecf\u53d1\u5e03\n\u5f53\u524d\u7248\u672c:%s\n\u6700\u65b0\u7248\u672c:%s", "4.01", version), "message", 2);
                    functions.openBrowseUrl(gitUrl);
                }
                if (isShowAppTip) {
                    JOptionPane.showMessageDialog(null, appTip, "message", 1);
                }
                if (isShowGroup) {
                    try {
                        ImageIcon imageIcon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(functions.httpReqest(wxGroupImageUrl, "GET", headers, null))));
                        ImageShowDialog.showImageDiaolog(imageIcon, showGroupTitle);
                    } catch (IOException e) {
                        Log.error(e);
                        Log.error("showGroup fail!");
                    }
                }
            }
        }
    }

    private static HashMap<String, String> getAppConfig(String configUrl) throws Exception {
        String[] lines;
        String configString;
        byte[] result = functions.httpReqest(configUrl, "GET", headers, null);
        if (result == null) {
            throw new Exception("readApplication Fail!");
        }
        try {
            configString = new String(result, "utf-8");
        } catch (UnsupportedEncodingException e) {
            configString = new String(result);
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (String line : lines = configString.split("\n")) {
            int index = line.indexOf(58);
            if (index == -1) continue;
            hashMap.put(line.substring(0, index).trim(), line.substring(index + 1).trim());
        }
        return hashMap;
    }

    static {
        headers.put("Accept", "*/*");
        headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)");
    }
}

