package com.navy.util;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


/**
 * JSON工具类
 * @author lnavy
 *
 */
public class JsonUtil {
	
	private static ObjectMapper objectMapper = null;
	static{
		objectMapper = getObjectMapper();
	}
	
	private static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		return objectMapper;
	}
	
	/**
	 * Convert JSON to Map Object.
	 * @param json
	 * @return
	 * @throws JsonDSException
	 */
	public static Map<?, ?> asMap(String json)throws Exception{
		Map<?, ?> map = objectMapper.readValue(json, Map.class);
		return map;
	}
	
	/**
	 * Convert List to JSON String.
	 * @param list
	 * @return
	 * @throws JsonDSException
	 */
	public static String jsonList(List<?> list)throws Exception{
		String result = objectMapper.writeValueAsString(list);
		return result;
	}
	
	/**
	 * Convert Map to JSON String.
	 * @param map
	 * @return
	 * @throws JsonDSException
	 */
	public static String jsonMap(Map<?, ?> map)throws Exception{
		String result = objectMapper.writeValueAsString(map);
		return result;
	}
	
	/**
	 * Convert Object to JSON String.
	 * @param map
	 * @return
	 * @throws JsonDSException
	 */
	public static String jsonBean(Object obj)throws Exception{
		String result = objectMapper.writeValueAsString(obj);
		return result;
	}
	
	/**
	 * Convert JSON to Bean<Class>
	 * @param _class
	 * @param json
	 * @return
	 * @throws JsonDSException
	 */
	public static <T> T asBean(Class<T> valueType, String json)throws Exception{
		return objectMapper.readValue(json, valueType);
	}
}
