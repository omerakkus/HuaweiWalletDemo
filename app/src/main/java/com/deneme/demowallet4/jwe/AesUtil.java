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
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {
    private static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    public AesUtil() {
    }

    
    public static String encryptByGcm(String plainData, String secretKeyStr, byte[] iv) {
        try {
            byte[] secretKeyByte = secretKeyStr.getBytes(StandardCharsets.UTF_8);
            byte[] plainByte = plainData.getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(secretKeyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", BOUNCY_CASTLE_PROVIDER);
            AlgorithmParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(1, secretKey, spec);
            byte[] fBytes = cipher.doFinal(plainByte);
            return new String(Hex.encode(fBytes ));
        } catch (Exception e) {
            throw new IllegalArgumentException("GCM encryption failed. Error: " + e.getMessage());
        }
    }

    public static byte[] getIvByte(int size) {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[size];
            sr.nextBytes(bytes);
            return bytes;
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("Get iv bytes failed. Error: " + e.getMessage());
        }
    }
}
