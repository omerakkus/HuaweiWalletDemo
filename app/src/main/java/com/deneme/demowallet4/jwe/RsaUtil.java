/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.deneme.demowallet4.jwe;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * RSA-encryption utility class.
 *
 * @since 2019-12-12
 */
public class RsaUtil {

    /**
     * Signature algorithm.
     */
    private static final String SIGN_ALGORITHMS256 = "SHA256WithRSA/PSS";

    private static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * Sign content.
     *
     * @param content data to be signed.
     * @param privateKey merchant's private key.
     * @return the signed value.
     */
    public static String sign(String content, String privateKey) {
        String charset = "utf-8";
        try {
            PKCS8EncodedKeySpec privatePKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(privatePKCS8);

            Signature signatureObj = Signature.getInstance(SIGN_ALGORITHMS256);
            signatureObj.initSign(priKey);
            signatureObj.update(content.getBytes(charset));

            byte[] signed = signatureObj.sign();
            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            throw new IllegalArgumentException("Get signature failed. Error: " + e.getMessage());
        }
    }

    /**
     * Encrypt bytes.
     *
     * @param bytes bytes to be encrypted.
     * @param publicKey public key.
     * @param algorithm encryption algorithm.
     * @return the encrypted string.
     */
    public static String encrypt(byte[] bytes, String publicKey, String algorithm) throws Exception {
        Key key = getPublicKey(publicKey);
        Cipher cipher = Cipher.getInstance(algorithm, BOUNCY_CASTLE_PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b1 = cipher.doFinal(bytes);
        return Base64.getEncoder().encodeToString(b1);
    }

    /**
     * Convert a public key string to a PublicKey.
     *
     * @param key the public key string.
     * @return the converted PublicKey.
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * Verify a signature.
     *
     * @param content content of the signature.
     * @param publicKey the public key string.
     * @param sign the signature to be verified.
     * @return if the signature is valid.
     */
    public static boolean verifySignature(String content, String publicKey, String sign) throws Exception {
        PublicKey key = getPublicKey(publicKey);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS256);
        signature.initVerify(key);
        signature.update(content.getBytes());
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }
}
