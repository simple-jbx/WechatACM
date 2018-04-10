package utils;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;

public class List2JsonUtils {
	public static String list2Json2String(List<Map<String, Object> > list) {
		 String json = JSON.toJSON(list).toString();
		 return json;		 
	}
}
