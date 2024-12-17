package core;

/**
 * Essa interface define a regra de negocio do sistema.
 * Ela fornece a asisnatura de metodo que o provedor de email vai implementar para enviar um email
 *
 * Ao implementar essa interface, voce garante que todo provedor de email tera essa funcionalidade basica
 *
 * Essencial para o failover do desafio, pois caso um provedor falhe, ele ira para o outro sem prejudicaro usuario
 */
public interface EmailProvider {

    void sendEmail(String to, String subject, String body);
}
