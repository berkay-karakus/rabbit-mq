import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
 
public class Consumer {
    private final static String QUEUE_NAME1 = "queue1";
    private final static String QUEUE_NAME2 = "queue2";

    public static void main(String[] args) throws Exception{

         ConnectionFactory connectionFactory = new ConnectionFactory();
         connectionFactory.setHost("localhost");
         Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel();
         channel.queueDeclare(QUEUE_NAME1, false, false, false, null);

         System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

         DeliverCallback deliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
             System.out.println("[x] Received " + message + "'" );
            // channel.basicPublish("", QUEUE_NAME2, null, message.getBytes(Charset.forName("UTF-8")));
         };
         channel.basicConsume(QUEUE_NAME1, true, deliverCallback, consumerTag -> { });

     }
}
