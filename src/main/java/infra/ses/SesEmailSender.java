package infra.ses;

import adapters.EmailSenderGateway;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.example.sendEmail.SendEmailApplication;
import core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta classe implementa a interface EmailSenderGateway, que é usada pelo serviço de envio de e-mails.
 * Ela contém a implementação específica para o envio de e-mails utilizando o serviço Amazon Simple Email Service (SES).
 *
 * O serviço SES da Amazon permite o envio de e-mails de maneira escalável e com alta disponibilidade.
 *
 * A classe está configurada para ser gerenciada pelo Spring Framework, graças à anotação @Service, o que
 * permite a injeção de dependências e a utilização dessa classe como um Bean no contexto da aplicação.
 */
@Service // Anotação Spring que marca a classe como um serviço e permite a injeção de dependências.
public class SesEmailSender implements EmailSenderGateway {

    // Instância do serviço SES da Amazon, utilizada para enviar e-mails
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    /**
     * Construtor da classe, que recebe o AmazonSimpleEmailService através da injeção de dependência.
     * O Spring cuidará da criação e configuração dessa instância automaticamente.
     *
     * @param amazonSimpleEmailService Instância do serviço SES que será utilizada para enviar e-mails.
     */
    @Autowired // Anotação que permite ao Spring injetar a dependência de AmazonSimpleEmailService.
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService){
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    /**
     * Implementação do método da interface EmailSenderGateway para o envio de e-mails utilizando o serviço SES.
     *
     * Esse método recebe os parâmetros necessários (destinatário, assunto e corpo do e-mail) e prepara uma
     * requisição para o SES. O envio do e-mail é feito através da API do Amazon SES.
     *
     * Se ocorrer algum erro durante o envio, uma exceção customizada será lançada.
     *
     * @param to Endereço de e-mail do destinatário.
     * @param subject Assunto do e-mail.
     * @param body Corpo do e-mail (conteúdo do e-mail).
     */
    @Override
    public void sendEmail(String to, String subject, String body) {
        // Criação do objeto SendEmailRequest, que representa a requisição para enviar um e-mail via SES
        SendEmailRequest emailRequest = new SendEmailRequest()
                .withSource("carlostechsoftware@hotmail.com") // Remetente do e-mail
                .withDestination(new Destination().withToAddresses(to)) // Destinatário do e-mail
                .withMessage(new Message() // Definição do conteúdo do e-mail
                        .withSubject(new Content(subject)) // Assunto do e-mail
                        .withBody(new Body().withText(new Content(body)))); // Corpo do e-mail em formato de texto

        try {
            // Envio do e-mail para o SES da AWS
            this.amazonSimpleEmailService.sendEmail(emailRequest);
        } catch (AmazonServiceException erro) {
            // Caso ocorra um erro durante o envio do e-mail, uma exceção personalizada é lançada
            // A exceção EmailServiceException encapsula o erro e fornece uma mensagem mais amigável
            throw new EmailServiceException("Falha ao enviar email", erro);
        }
    }
}
