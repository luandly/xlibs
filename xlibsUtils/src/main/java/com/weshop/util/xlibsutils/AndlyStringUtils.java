package com.weshop.util.xlibsutils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 2013年9月30日16:41:45
 * @version this_version 1.0
 * @version 2013093001 1.2.3.1
 *
 */
public class AndlyStringUtils {
	public static final String NULL1 = "null";
	public static final String NULL2 = "nullnull";
	public static final String NULL3 = "nullnullnull";
	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";
	/**
	 * 电子名片校验 包含的必须是手机号码
	 */
	public static boolean isVCARD(String str) {
		boolean flag = false;
		if (!isNullOrEmpty(str)) {
			// 符合电子名片的格式
			if (str.contains("END:VCARD") && str.length() > 20
					&& str.contains("TEL:")) {
				String subStr = getTelNumInVCARD(str);
				// 符合手机号格式
				if (!isNullOrEmpty(subStr) && isPhoneNumber(subStr)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 获取电子名片中的电话号码
	 * 
	 * @param str
	 * @return
	 */
	public static String getTelNumInVCARD(String str) {
		return str.substring(str.indexOf("TEL:") + 4, str.indexOf("END:VCARD"))
				.trim();
	}

	/**
	 * 获取电子名片中的 联系人姓名
	 * 
	 * @param str
	 * @return
	 */
	public static String getNameInVCARD(String str) {
		String[] strarr = str.split(":");
		return strarr[3].substring(0, strarr[3].indexOf("TEL")).trim();
	}

	/**
	 * TEL:15820986930 电话格式校验 包含的必须是手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isTEL(String str) {
		boolean flag = false;
		if (!isNullOrEmpty(str)) {
			int length = str.length();
			if (str.startsWith("TEL:") && length > 12 && length < 20) {
				String subStr = str.substring(str.indexOf(":") + 1);
				if (!isNullOrEmpty(subStr) && isPhoneNumber(subStr)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 获取电话格式中的电话号码
	 * @param str
	 * @return
	 */
	public static String getTelNum(String str) {
		return str.substring(str.indexOf(":") + 1);
	}

	public static String getTelNumInSms(String str) {
		return str.substring(0, str.indexOf(":"));
	}

	/**
	 * 网页地址过滤 白名单
	 * 
	 * @param str
	 *            准备访问的URL地址
	 * @param strArr
	 *            从配置文件读取白名单 应该包含
	 * @return
	 */
	public static boolean isMyURL(String str, String[] strArr) {
		boolean flag = false;
		if (!isNullOrEmpty(str)
				&& (str.startsWith("http://") || str.startsWith("www."))) {
			for (int i = 0; i < strArr.length; i++) {
				if (str.contains(strArr[i])) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 网页地址过滤
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isURL(String str) {
		boolean flag = false;
		if (!isNullOrEmpty(str)
				&& (str.startsWith("http://") || str.startsWith("www."))) {
			flag = true;
		}
		return flag;
	}

	// 是否国际编号
	public static boolean isFigureISNum(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()
				|| !((str.startsWith("977") || str.startsWith("978") || str
						.startsWith("979")) && str.length() == 13)) {
			return false;
		}
		return true;
	}
	/**
	 * 判断字符串是否为空，为空则返回真
	 * 
	 * @param oldString
	 * @return
	 */
	public static boolean isNullOrEmpty(String oldString) {
		// return null == oldString || "".equals(oldString) ||
		// oldString.isEmpty();
		return null == oldString || "".equals(oldString.toString().trim())
				|| "null".equalsIgnoreCase(oldString.toString().trim());
	}

	/**
	 * 判断字符串是否为空，为空或为限制字符则返回真 限制字符：比如选择框中的“请选择”等等
	 * 
	 * @param oldString
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String oldString, String... str) {
		boolean flag = false;
		// 当前字符串不为空，有内容。但是这些内容是需要过滤的内容。比如下拉选择框中的“请选择”
		if (!isNullOrEmpty(oldString)) {
			for (int i = 0; i < str.length; i++) {
				if (oldString.equals(str[i])) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public static boolean isPhoneNumber(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
			if (!flag) {
				String tregEx = "[0]{1}[0-9]{2,3}-[0-9]{7,8}"; // 表示a或f // //
																// 0832-80691990
				flag = Pattern.compile(tregEx).matcher(mobiles).find();
			}

		} catch (Exception e) {
			flag = false;
		}
		return flag;

		// (http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?
	}

	public static boolean isPhoneNumber34578(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^((1[34578]))\\d{9}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
			if (!flag) {
				String tregEx = "[0]{1}[0-9]{2,3}-[0-9]{7,8}"; // 表示a或f // //
				// 0832-80691990
				flag = Pattern.compile(tregEx).matcher(mobiles).find();
			}

		} catch (Exception e) {
			flag = false;
		}
		return flag;

		// (http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?
	}


	/***
	 * 对json型字符串中一个值进行切割。
	 * 
	 * @return 字符串数组
	 */
	public static HashMap<String, String> splitJsonStr(String oldString) {
		HashMap<String, String> tempMap = null;
		if (!isNullOrEmpty(oldString)) {
			int length = oldString.length();
			if (oldString.startsWith("{") && oldString.endsWith("}")
					&& length > 2) {
				oldString = oldString.substring(1, length - 1);
				int length1 = oldString.length();
				if (3 < length1 && oldString.contains(",")) {
					String[] strArr = oldString.split(",");
					tempMap = new HashMap<String, String>();
					if (strArr.length > 0) {
						for (int i = 0; i < strArr.length; i++) {
							if (!isNullOrEmpty(strArr[i])) {
								if (3 < length1 && strArr[i].contains(":")) {
									if (strArr[i].contains("\"")) {
										strArr[i] = strArr[i].replace("\"", "");
									}
									String str[] = strArr[i].split(":");
									if (str.length == 2) {
										tempMap.put(str[0], str[1]);
									}
								}
							}
						}
					}
				}

			}
		}
		return tempMap;
	}




	/**
	 * 在数组中获取匹配的字符串的值 N:lixiaobin 作为字符串数组中的一项 N=str
	 * 
	 * @param strArr
	 *            字符串数组
	 * @param str
	 *            匹配的值 N:
	 * @return 冒号后面的值 lixiaobin
	 */
	private static String getMyVCardNameInArr(String[] strArr, String str) {
		String temp = "";
		if (null != strArr && strArr.length > 0) {
			for (int i = 0; i < strArr.length; i++) {
				String indexStr = strArr[i];
				if (!isNullOrEmpty(indexStr) && !isNullOrEmpty(str)) {
					if (indexStr.contains(str) && indexStr.contains(":")
							&& indexStr.startsWith(str)) {
						temp = indexStr.substring(indexStr.indexOf(":") + 1,
								indexStr.length());
						break;
					}
				}
			}
		}
		return temp;
	}

	/**
	 * 对电子名片字符串进行切割，返回字符串数组 用空格进行切割
	 */
	private static String[] getVCardToArray(String str) {
		/**
		 * 把备注内容单独拿出来，防止有换行。有换行则分割出来的值不对 从NOTE:开始并包含他。后面取值的时候是需要用其来进行比对拿值
		 */
		String noteStr2end = " ";
		String noteStr = "NOTE:";
		String endVCard = "END:VCARD";
		if (str.contains(noteStr) && str.contains(endVCard)) {
			noteStr2end = str.substring(str.indexOf(noteStr),
					str.indexOf(endVCard));
			str = str.substring(0, str.indexOf(noteStr));
		}
		String[] strarr = null;
		// if (str.contains(" ")) {
		// strarr = str.split(" ");
		// } else
		if (str.contains("\n")) {
			strarr = str.split("\n");
			// } else if (str.contains("\t")) {
			// strarr = str.split("\t");
			// } else if (str.contains("\n\t")) {
			// strarr = str.split("\n\t");
		}
		String[] arrNew = new String[strarr.length + 1];
		for (int i = 0; i < strarr.length; i++) {
			arrNew[i] = strarr[i];
		}
		arrNew[arrNew.length - 1] = noteStr2end;
		return arrNew;
	}

	/**
	 * 判断数组是否是根节点在首位
	 * 
	 * @return
	 * @author liupengcheng
	 */
	public static boolean isRoot(String[] item) {
		boolean flag = false;
		if (null != item && item.length >= 3) {
			// 判断首行为版本号，其中不包含冒号
			// if (!item[0].contains(":")) {
			//
			// }
			String headTxt = item[1];
			if (!isNullOrEmpty(headTxt) && headTxt.contains(":")) {
				int index = headTxt.indexOf(":");
				// 1XM:酒水 代表根 如果是根节点是首位则返回真
				if (index == 3) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 颠倒数组顺序
	 * 
	 * @param objArr
	 * @return
	 */
	public static String[] reverseArray(String[] objArr) {
		String[] objArr2 = new String[objArr.length];
		for (int i = 0, j = objArr.length - 1; j >= 0; i++, j--) {
			objArr2[i] = objArr[j];
			// System.err.println(objArr[i] + "--" + objArr[j]);
		}
		return objArr2;
	}

	/**
	 * null、“”、“null”
	 * 
	 * @param oldString
	 * @return 空 返回 TRUE 否则 返回 FALSE
	 */
	public static boolean isNullOrEmpty2(String oldString) {
		if (null == oldString || TextUtils.isEmpty(oldString)
				|| "".equals(oldString) || "null".equalsIgnoreCase(oldString)) {
			return true;
		}
		if (null == oldString.trim() || TextUtils.isEmpty(oldString.trim())
				|| "".equals(oldString.trim())
				|| "null".equalsIgnoreCase(oldString.trim())) {
			return true;
		}
		return false;
	}

	/***
	 * 获取UUID的前8位 产生盐
	 * 
	 * @return
	 */
	public static String get8UUID4Salted() {

		UUID uuid = UUID.randomUUID();

		String str = uuid.toString();
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18);
		return temp;
	}


	/**
	 * 根据传入json字符串、相应的key获取boolean类型值
	 * 
	 * @param jsonDataStr
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public static boolean getJsonBoolValueByKey(String jsonDataStr, String key)
			throws JSONException {
		JSONObject jsonData = new JSONObject(jsonDataStr);
		return jsonData.getBoolean(key);
	}

	/**
	 * 根据传入json字符串、相应的key获取字符串值
	 * 
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public static String getJsonStrValueByKey(String showData, String key)
			throws JSONException {
		// LLog.e("getJsonStrValueByKey", showData+"-getLoginInfo--" + key);
		if (showData.startsWith("[")) {
			showData = showData.substring(1, showData.length());
		}
		if (showData.endsWith("]")) {
			showData = showData.substring(0, showData.length() - 1);
		}
		JSONObject jsonData = new JSONObject(showData);

		return jsonData.getString(key);
	}

	public static HashMap<String, Object> getDStoreCategoriesbyJsonApi2(
			String data) throws Exception {
		if (AndlyStringUtils.isNullOrEmpty2(data)) {
			return null;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		data = data.substring(1, data.length() - 1);
		data = data.replaceAll("\"", "");
		String[] ary1 = data.split(",");// 根据”,“号分隔字符串，获得数组对象
		String[] ary3 = ary1;// 根据”,“号分隔字符串，获得数组对象
		for (int i = 0; i < ary1.length; i++) {// 循环取出数组中的对象
			String[] ary2 = ary1[i].split(":");// 根据”:“冒号分隔字符串，获得键-值数组对象
			map.put(ary2[1], ary2[0]);// 将对象以键-值对的方式存入map中
			ary3[i] = ary2[1];
		}
		// for (int i = 0; i < ary1.length; i++) {
		// ary3[i]=ary1[i].split(":")[0];//根据”:“冒号分隔字符串，获得键-值数组对象
		// }
		System.err.println();
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		mapReturn.put("map", map);
		mapReturn.put("StringArray", ary3);
		return mapReturn;
	}

	public static String[] mapKeySet2StringArray(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		Set<String> setStr = hashMap.keySet();
		int arrLength = setStr.size();
		String[] strLeimuArr1 = new String[arrLength];
		int i = 0;
		for (Iterator<String> iterator = setStr.iterator(); iterator.hasNext(); i++) {
			strLeimuArr1[i] = (String) iterator.next();
		}
		return strLeimuArr1;
	}


	/**
	 * base64编码
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("NewApi")
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		path = Base64.encodeToString(buffer, Base64.DEFAULT);
		return path;
	}


	private static String readTextFile(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
		}
		return outputStream.toString();
	}

	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

    /**
     * 返回 变为*的银行卡卡号格式
     * @param num
     * @return 6230760060000817354 **** **** **** 7354
     */
    public static String getBankNumber(String num) {

        String startStr = "**** **** **** ";
        if (!TextUtils.isEmpty(num) && num.length() > 5) {
//            num = Util.strReplace(num, 0, num.length() - 4, "*");
            startStr = startStr + num.substring(num.length() - 4, num.length());
        } else {
            startStr = num;
        }
        return startStr;
    }
}
