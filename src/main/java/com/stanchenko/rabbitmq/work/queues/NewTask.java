package com.stanchenko.rabbitmq.work.queues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class NewTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewTask.class);
	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");

		String message = "Message....";
		try (Connection connection = connectionFactory.newConnection();
				Channel channel = connection.createChannel()) {

			channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

			channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes());
			LOGGER.info("[x] Sent '{}'", message);
		}
	}
}
