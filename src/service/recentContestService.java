package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.DataBaseUtils;

public class recentContestService {
	

	public List<Map<String, Object> > getData() {
		String sql = "select * from recentoj";
		List<Map<String, Object> > list = DataBaseUtils.queryForList(sql);
		List<Map<String, Object> > resultList = new ArrayList<Map<String, Object> >();
		Map<String, Object> map ;
		String url = "";
		for(int i = 0; i < list.size(); i++) {
			map = list.get(i);
			url = map.get("url").toString().trim();
			while(url.endsWith("/")) {
				url = url.substring(0, url.length() - 1);
			}
			map.remove("url");
			map.put("url", url);	
			resultList.add(map);
		}
		return resultList;
	}
}
