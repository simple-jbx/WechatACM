package utils;

public class StringUtils {
	public static boolean isEmpty(String str){
		return null == str || str.equals("") || str.matches("\\s*");
	}
	
	public static String defaultValue(String content, String defaultValue){
		if(isEmpty(content))
			return defaultValue;
		return content;
	}
	
	/**
	 * 把数据库字段名称改成驼峰方式
	 * @param column
	 * @return String
	 */
	public static String columnToProperty(String column) {
		//如果字段名为空，就返回空字符串
		if(isEmpty(column))
			return "";
		//获取字段长度  一般字段长度较小
		Byte length = (byte) column.length();
		
		StringBuilder sb = new StringBuilder(length);
		//遍历字段每一个字符
		for(int j = 0; j < length; j++) {
			//匹配到第一个_
			if(column.charAt(j) == '_') {
				while(column.charAt(++j) == '_');
				sb.append(("" + column.charAt(j)).toUpperCase());
			}else {
				//如果字符不是_保存
				sb.append(column.charAt(j));
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 把数据库字段名称改成驼峰方式
	 * @param column
	 * @return String
	 */
	public static String columnToUpperCase(String column) {
		//如果字段名为空，就返回空字符串
		if(isEmpty(column))
			return "";
		//直接将字段名转换为大写返回即可
		return column.toUpperCase();
	}
	
	
	/**
	 * 讲一个字符串的首字母改成大写
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstCharacter(String str) {
		StringBuilder sb = new StringBuilder();
		char[] arr = str.toCharArray();
		for(int i = 0; i < arr.length; i++) {
			if(i == 0) {
				sb.append((arr[i] + "").toUpperCase());
			}else {
				sb.append((arr[i] + ""));
			}
		}
		return sb.toString();
	}
}
