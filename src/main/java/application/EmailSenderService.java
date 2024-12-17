package application;

import adapters.EmailSenderGateway;
import core.EmailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe que vai implementar o core da nossa aplicacao.
 *
 * Ela tem como objetivo ajudar a implementar o objetivo de fornecer um servico de envio de emails flexivel e adaptavel
 */
@Service
public class EmailSenderService implements EmailProvider {
    private final EmailSenderGateway emailGateway; //

   //O gatewayy encapusla a comunicacao com a API de email
    //Fazendo com que essa comunicacao nao seja direta, e tenha um adaptador por tras, facilitando
    @Autowired
    public EmailSenderService(EmailSenderGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        this.emailGateway.sendEmail(to, subject, body);
    }
}
