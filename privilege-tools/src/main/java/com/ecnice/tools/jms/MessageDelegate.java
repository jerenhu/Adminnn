package com.ecnice.tools.jms;

import java.io.Serializable;

public interface MessageDelegate {

	/**
	 * @param message
	 * @Description:接受序列化的消息
	 */
	void handleMessage(Serializable message);

}
