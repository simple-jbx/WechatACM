package utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Integer;
import java.util.Properties;

public class DataBaseUtils {
		
	private static String username; //用户名
	private static String password; //密码
	private static String dataBaseName; //数据库名
	static{
		config("config/jdbc.properties");
	}
	
	/**
	 * 配置数据库基本信息
	 * @author simple
	 * @param path
	 */
	public static void config(String path){
		InputStream inputStream = DataBaseUtils.class.getClassLoader().getResourceAsStream(path);
		Properties p = new Properties();
		try{
			p.load(inputStream);
			username = p.getProperty("db.username");
			password = p.getProperty("db.password");
			dataBaseName = p.getProperty("db.dataBaseName");
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取数据库连接
	 * @return Connection
	 */
	public static Connection getConnection(){
		Connection connection = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dataBaseName+"?useUnicode=true&characterEncoding=utf8&useSSL=false",username,password);
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	/**
	 * 关闭数据库连接
	 * @param connection
	 * @param statement
	 * @param rs
	 */
	public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet rs) {
		try{
			if(rs != null) {
				rs.close();
			}
			if(statement != null) {
				statement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 增 删  改
	 *@param sql
	 *@param objects
	 *@return
	 */
	public static void update(String sql, Object...objects) {
		Connection connection  = getConnection();
		PreparedStatement statement = null;
		try{
			statement = (PreparedStatement) connection.prepareStatement(sql);
			for(int i = 0; i < objects.length; i++) {
				statement.setObject(i+1, objects[i]);
			}
			statement.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			closeConnection(connection, statement, null);
		}
	}
	
	
	/**
	 * 查询数据，以list形式返回
	 * @parm sql
	 * @parm objects
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, Object> > queryForList(String sql, Object...objects) {
		List<Map<String, Object> > resultList = new ArrayList<Map<String, Object> >();
		Connection connection = getConnection();
		PreparedStatement statement= null;
		ResultSet rs = null;
		try{
			statement = connection.prepareStatement(sql);
			for(int i = 0; i < objects.length; i++) {
				//System.out.println(i);
				statement.setObject(i+1, objects[i]);
			}
			rs = statement.executeQuery();
			while(rs.next()) {
				ResultSetMetaData resultSetMetaData = rs.getMetaData();//关于ResultSet中列名称和信息的类型
				int count = resultSetMetaData.getColumnCount();//获取列数
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 0; i < count; i++) {
					//System.out.println(resultSetMetaData.getColumnName(i+1)); 名称
					//System.out.println(rs.getObject(resultSetMetaData.getColumnName(i+1))); 值
					map.put(resultSetMetaData.getColumnName(i+1), rs.getObject(resultSetMetaData.getColumnName(i+1)));
				}
				resultList.add(map);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeConnection(connection, statement, rs);
		}
		return resultList;
	}
	
	
	/**
	 * 查询数据，并且以map返回
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static Map<String, Object> queryForMap(String sql, Object...objects) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object> >list = queryForList(sql, objects);
		if(list.size() != 1) {
			return null;
		}
		result = list.get(0);
		return result;
	}
	
	
	/**
	 * 查询数据，返回一个JavaBean
	 * @param sql
	 * @param clazz
	 * @param objects
	 * @return
	 * @throws NOSuchFileException
	 * @throws SecurityException
	 */
	
	public static <T>T queryForBean(String sql, Class<?> clazz, Object...objects) {
		T obj = null;
		Map<String, Object> map = null;
		Field field = null;
		try{
			obj = (T)clazz.newInstance();//创建一个Bean实例
			map = queryForMap(sql, objects);//将结果集先放在一个map中
		}catch(InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if(map == null)
			return null;
		//遍历map
		for(String columnName : map.keySet()) {
			Method method = null;
			//字段名字符转换
			String propertyName = StringUtils.columnToUpperCase(columnName);
			try {
				field = clazz.getDeclaredField(propertyName);
				//System.out.println(field);
			}catch(NoSuchFieldException e) {
				e.printStackTrace();
			}catch(SecurityException e) {
				e.printStackTrace();
			}
			
			//过去JavaBean中的字段
			String fieldType = field.toString().split(" ")[1];
			Object value = map.get(columnName);
			if(value == null)
				continue;
			//JavaBean set方法名
			String setMethodName = "set" + propertyName;
			try{
					if(fieldType.equals("java.lang.Integer")){ //Integer
			        		value = new Integer(String.valueOf(value));
			        }else if(fieldType.equalsIgnoreCase("int")) {
		        		value = new Integer(String.valueOf(value));
			        }else if(fieldType.equalsIgnoreCase("java.util.String")) {
						value = String.valueOf(value);
					}else if(fieldType.equalsIgnoreCase("java.util.Date")) {
						//数据库里的时间与java转换
						String dateStr = String.valueOf(value);
						Timestamp ts = Timestamp.valueOf(dateStr);
						Date date = new Date(ts.getTime());
						value = date;
					}else if(fieldType.equalsIgnoreCase("java.math.BigDecimal")) {
						value = new BigDecimal(String.valueOf(value));
					}else if(fieldType.equalsIgnoreCase("java.lang.Double")) {
						value = new Double(String.valueOf(value));
					}else if(fieldType.equalsIgnoreCase("double")) {
						value = new Double(String.valueOf(value));
					}else if(fieldType.equalsIgnoreCase("java.lang.Float")) {
						value = new Float(String.valueOf(value));
					}else if(fieldType.equalsIgnoreCase("float")) {
						value = new Float(String.valueOf(value));
					}else if(fieldType.equalsIgnoreCase("long")) {
						value = new Long(String.valueOf(value));
					}else if(fieldType.equalsIgnoreCase("java.util.Long")) {
						value = new Long(String.valueOf(value));
					}
			   		     
				//实现动态调用
				method = clazz.getMethod(setMethodName, field.getType());
				method.invoke(obj, value);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
