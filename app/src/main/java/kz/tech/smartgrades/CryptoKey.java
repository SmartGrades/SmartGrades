package kz.tech.smartgrades;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.generators.Ed448KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class CryptoKey {
    private static final String PRIVATE_KEY = "privateKey";
    private static final String PUBLIC_KEY = "publicKey";
    public static Ed25519PublicKeyParameters createKeyPair() {
        Ed25519KeyPairGenerator kpg = new Ed25519KeyPairGenerator();
        KeyGenerationParameters param = new KeyGenerationParameters(new SecureRandom(), 2048);
        kpg.init(param);
        AsymmetricCipherKeyPair keyPair = kpg.generateKeyPair();
        Ed25519PublicKeyParameters pub = (Ed25519PublicKeyParameters) keyPair.getPublic();
        Ed25519PrivateKeyParameters priv = (Ed25519PrivateKeyParameters) keyPair.getPrivate();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(App.app());
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PRIVATE_KEY, Arrays.toString(priv.getEncoded()));
        editor.putString(PUBLIC_KEY, Arrays.toString(pub.getEncoded()));
        return pub;
    }
    public static byte[] getPublicKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.app());
        String stringPublic = preferences.getString(PUBLIC_KEY, null);
        byte[] arrayPublic = new byte[0];
        if (stringPublic != null) {
            String[] split = stringPublic.substring(1, stringPublic.length()-1).split(", ");
            arrayPublic = new byte[split.length];
            for (int i = 0; i < split.length; i++) {
                arrayPublic[i] = Byte.parseByte(split[i]);
            }
        }
        return arrayPublic;
    }
    public static byte[] getPrivateKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.app());
        String stringPrivate = preferences.getString(PRIVATE_KEY, null);
        byte[] arrayPrivate = new byte[0];
        if (stringPrivate != null) {
            String[] split = stringPrivate.substring(1, stringPrivate.length()-1).split(", ");
            arrayPrivate = new byte[split.length];
            for (int i = 0; i < split.length; i++) {
                arrayPrivate[i] = Byte.parseByte(split[i]);
            }
        }
        return arrayPrivate;
    }

    private static String get_SHA_512_SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}