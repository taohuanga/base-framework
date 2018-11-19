package com.towcent.base.common.utils;

import com.towcent.base.common.constants.PublicContains;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 加密工具类
 */
public class EncryptionUtils {
	private static final Log log = LogFactory.getLog(EncryptionUtils.class);
	// 初始化向量
	public static final String INITIALIZATION_VECTOR = "cnBHdE9F";
	// 转换模式
	public static final String TRANSFORMATION = "Blowfish/CBC/PKCS5Padding";
	// 密钥
	public static final String BLOWFISH_ENCRYPT = "WoNHIgh@$2=0=1=5";
	// 密钥算法名称
	public static final String BLOWFISH = "Blowfish";

	/**
	 * 生成md5
	 * @param data
	 * @return
	 * modify by yuesheng.yin 2014/9/24
	 * 去掉同步关键字
	 */
	public static String md5(String data) {
		if (StringUtils.isEmpty(data)) {
			return "";
		}
		return DigestUtils.md5Hex(data);
	}

	/**
	 * sha加密
	 * @param data
	 * @return
	 * modify by yuesheng.yin 2014/9/24
	 * 去掉同步关键字
	 */
	@SuppressWarnings("deprecation")
	public static String sha(String data) {
		if (StringUtils.isEmpty(data)) {
			return "";
		}
		return DigestUtils.shaHex(data);
	}

	/**
	 * sha加密
	 * 支持上G的大文件
	 * @param data
	 * @return
	 * add by wei.b 2015/5/11
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public static String sha4Path(String filePath) throws IOException {
		File f = new File(filePath);
		if (!f.exists()) {
			return "";
		}
		InputStream in = null;
		try {
			in = new FileInputStream(f);
			return DigestUtils.shaHex(in);
		} catch (FileNotFoundException e) {
			log.error("sha加密失败,文件没找到", e);
		} catch (IOException e) {
			log.error("sha加密失败", e);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return "";
	}

	/**
	 * base64编码
	 * @param data
	 * @return
	 * modify by yuesheng.yin 2014/9/24
	 * 去掉同步关键字
	 */
	public static String base64Encode(String data) {
		if (StringUtils.isEmpty(data)) {
			return "";
		}
		try {
			return new String(Base64.encodeBase64(data.getBytes(), true), PublicContains.UTF8_ENCODE);
		} catch (UnsupportedEncodingException e) {
			log.error("base64编码失败", e);
		}
		return "";
	}

	/**
	 * base64解码
	 * @param data
	 * @return
	 * modify by yuesheng.yin 2014/9/24
	 * 去掉同步关键字
	 */
	public static String base64Decode(String data) {
		if (StringUtils.isEmpty(data)) {
			return "";
		}
		try {
			return new String(Base64.decodeBase64(data.getBytes()), PublicContains.UTF8_ENCODE);
		} catch (UnsupportedEncodingException e) {
			log.error("base64解码失败", e);
		}
		return "";
	}

	/**
	 * Blowfish加密
	 * @param encryptKey 密钥
	 * @param text 加密文本
	 */
	public static String blowfishEncode(String encryptKey, String text) throws Exception {
		// 根据给定的字节数组构造一个密钥  Blowfish-与给定的密钥内容相关联的密钥算法的名称
		SecretKeySpec sksSpec = new SecretKeySpec(encryptKey.getBytes(), BLOWFISH);
		// 使用 initializationVector 中的字节作为 IV 来构造一个 IvParameterSpec 对象
		AlgorithmParameterSpec iv = new IvParameterSpec(INITIALIZATION_VECTOR.getBytes());
		// 返回实现指定转换的 Cipher 对象
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		// 用密钥和随机源初始化此 Cipher
		cipher.init(Cipher.ENCRYPT_MODE, sksSpec, iv);
		// 加密文本
		byte[] encrypted = cipher.doFinal(text.getBytes());
		return new String(Hex.encodeHex(encrypted));
	}

	/**
	 * Blowfish解密
	 * @param encryptKey 密钥
	 * @param text加密文本
	 */
	public static String blowfishDecode(String encryptKey, String text) throws Exception {
		byte[] encrypted = null;
		try {
			encrypted = Hex.decodeHex(text.toCharArray());
		} catch (Exception e) {
			log.error("Blowfish解密失败", e);
		}

		SecretKeySpec skeSpect = new SecretKeySpec(encryptKey.getBytes(), BLOWFISH);
		AlgorithmParameterSpec iv = new IvParameterSpec(INITIALIZATION_VECTOR.getBytes());
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, skeSpect, iv);
		byte[] decrypted = cipher.doFinal(encrypted);
		return new String(decrypted);
	}

	/**
	 * Blowfish加密
	 * 优先加载环境变量
	 * @param text 加密文本
	 */
	public static String blowfishEncodeByEnvVar(String text) throws Exception {
		String encryptKey = System.getProperty("blowfishEncryptKey");
		encryptKey = StringUtils.isBlank(encryptKey) ? BLOWFISH_ENCRYPT : encryptKey;
		return blowfishEncode(encryptKey, text);
	}

	/**
	 * Blowfish解密
	 * 优先加载环境变量
	 * @param text加密文本
	 */
	public static String blowfishDecodeByEnvVar(String text) throws Exception {
		String encryptKey=System.getProperty("blowfishEncryptKey");
		encryptKey=StringUtils.isBlank(encryptKey)?BLOWFISH_ENCRYPT:encryptKey;
		return blowfishDecode(encryptKey, text);
	}

}
