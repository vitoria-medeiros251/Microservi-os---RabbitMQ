package com.ms.email.consumers;
import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);
    private final EmailService emailService;

    public EmailConsumer(EmailService emailService){
        this.emailService = emailService;
    }

      @RabbitListener(queues = "${broker.queue.email.name}")
      public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto){
        logger.info("Mensagem recebida: {}", emailRecordDto);
        var emailModel = new EmailModel();
          BeanUtils.copyProperties(emailRecordDto,emailModel);
          emailService.sendEmail(emailModel);
          logger.info("Email processado para: {}", emailRecordDto.emailTo());
      }
}
