import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import java.nio.charset.Charset;
public class Producer {
    private final static String QUEUE_NAME1 = "queue1";

    public static void main(String[] argv){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try ( Connection connection = factory.newConnection();
              Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME1, false, false, false, null);

            // 10 günlük hava durumu tahminini al
            Scanner input = new Scanner(System.in);
            for(int i = 0; i < 10; i++){
                int degree = input.nextInt();
                // queue'ya publish etmek için ayarla
                String degree_string = degree + "C";
                // mesajları belirtilen queue'ya gönder
                channel.basicPublish("", QUEUE_NAME1, null,
                        degree_string.getBytes(StandardCharsets.UTF_8));
            }

        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        }
    }

