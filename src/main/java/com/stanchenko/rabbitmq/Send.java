package com.stanchenko.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private static final Logger LOGGER = LoggerFactory.getLogger(Send.class);
	private static final String QUEUE_NAME = "first";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");

		String message = "First message";
		try (Connection connection = connectionFactory.newConnection();
				Channel channel = connection.createChannel()) {

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			LOGGER.info("[x] Sent '{}'", message);
		}
	}
}
