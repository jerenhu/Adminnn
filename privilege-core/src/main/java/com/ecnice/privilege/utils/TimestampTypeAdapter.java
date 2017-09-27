/**
 * TimestampTypeAdapter.java V1.0 2013-4-3 下午4:21:53
 *
浙江蘑菇加电子商务有限公司版权所有，保留所有权利。
 *
 * Modification history(By Time Reason):
 *
 * Description:
 */

package com.ecnice.privilege.utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TimestampTypeAdapter implements JsonSerializer<Timestamp>,
		JsonDeserializer<Timestamp> {
	private final DateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");

	public JsonElement serialize(Timestamp ts, Type t,
			JsonSerializationContext jsc) {
		String dfString = format.format(new Date(ts.getTime()));
		return new JsonPrimitive(dfString);
	}

	public Timestamp deserialize(JsonElement json, Type t,
			JsonDeserializationContext jsc) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}

		try {
			Date date = format.parse(json.getAsString());
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}
}
