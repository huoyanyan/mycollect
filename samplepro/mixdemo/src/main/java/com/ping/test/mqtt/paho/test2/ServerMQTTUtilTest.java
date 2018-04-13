package com.ping.test.mqtt.paho.test2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.alibaba.fastjson.JSON;

public class ServerMQTTUtilTest {
	static String deviceIdList;
	public static void main(String[] args) {
		deviceIdList = "all";
		// 设置推送消息体
		String sendTime = System.currentTimeMillis() + "";
		String msgType = "html";
		String title = message.getTitle();
		String content = message.getContent();
		String createTime = message.getCreateTime();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("messageId", messageId);
		data.put("msgType", msgType);
		data.put("title", title);
		data.put("content", content);
		data.put("createTime", createTime);
		data.put("sendTime", sendTime);
		msgType = "posMessage";
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("msgType", msgType);
		messageMap.put("data", data);
		String MQTTObject = JSON.toJSONString(messageMap);
		System.out.println(MQTTObject);
		// 将信息写入消息体
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setQos(1);
		mqttMessage.setRetained(true);
		mqttMessage.setPayload(MQTTObject.getBytes("UTF-8"));
		// 根据deviceIdList内容进行topic设置和发送推送
		if (!deviceIdList.equals("all")) {
			List deviceList = Arrays.asList(deviceIdList.split(","));
			for (int i = 0; i < deviceList.size(); i++) {

				String topic = "pos_message_" + deviceList.get(i);
				ServerMQTTUtil serverMQTTUtil = new ServerMQTTUtil(topic);
				serverMQTTUtil.publish(mqttMessage);
				System.out.println(mqttMessage.isRetained() + "------ratained状态");
			}
		} else {
			String topic = "pos_message_all";
			ServerMQTTUtil serverMQTTUtil = new ServerMQTTUtil(topic);
			serverMQTTUtil.publish(mqttMessage);
			System.out.println(mqttMessage.isRetained() + "------ratained状态");
		}
	}

}
