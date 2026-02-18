package com.ms.user.producers;
import com.ms.user.dto.EmailDto;
import com.ms.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

        @Value("${broker.queue.email.name}")
        private String queueName ;

        public UserProducer(RabbitTemplate rabbitTemplate) {
            this.rabbitTemplate = rabbitTemplate;
        }
        public void publishMessageEmail(UserModel userModel){
            var emailDto = new EmailDto();
            emailDto.setUserId(userModel.getUserId());
            emailDto.setEmailTo(userModel.getEmail());
            emailDto.setSubject("cadastro realizado com sucesso");
            emailDto.setText(userModel.getName() + ", seja bem vindo(a) ! \nAgradecemos o seu cadastro em nossa plataforma,aproveite todos os recursos da nossa plataforma .");
            rabbitTemplate.convertAndSend("",queueName,emailDto);
        }

    }





