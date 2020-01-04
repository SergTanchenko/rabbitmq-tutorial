package com.stanchenko.rabbitmq.work.queues;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.stanchenko.rabbitmq.Receive;

public class Worker {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receive.class);
	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.basicQos(1);

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			LOGGER.info(" [x] Received: '{}'", message);
			try {
				doWork(message);
			}
			catch (InterruptedException e) {
				LOGGER.error("Error: {}", e.getMessage());
			}
			finally {
				LOGGER.info(" [x] Done");
			}
		};

		channel.basicConsume(TASK_QUEUE_NAME, true, deliverCallback, consumerTag -> {
		});

		LOGGER.info(" [*] Waiting for messages...");
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				LOGGER.info(" [x] Task: '{}'. Working... ", task);
				Thread.sleep(1000);
			}
		}
	}
}
