package adapters;

/**
 * Contrato  que ira se relacionar com os servicos externos, ou seja, com os provedores de servicos de email
 */
public interface EmailSenderGateway {

    void sendEmail(String to, String subject, String body);
}
