package broker.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    @Value("${filename}")
    private String logFilePath;

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.groupId.name}",
            containerFactory = "loggerKafkaListenerContainerFactory")
    private void listener(String message) {
        message = LocalDateTime.now() + " | " + message;
        try {
            File file = new File(logFilePath);
            if (file.exists()) {
                FileWriter writer = new FileWriter(file, true);
                writer.write(message + "\n");
                writer.close();
            } else {
                FileWriter writer = new FileWriter(file);
                writer.write(message + "\n");
                writer.close();
            }
            log.info("message written to log file");
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

}