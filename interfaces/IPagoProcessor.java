package interfaces;

import valueobjects.Money;

/**
 * Interfaz que define el procesador de pagos (Adapter Pattern).
 * Se implementará completamente en la Parte 4.
 */
public interface IPagoProcessor {
    /**
     * Autoriza un pago por el monto especificado.
     * @param monto El monto a autorizar
     * @param referencia Referencia de la transacción
     * @return Referencia de autorización
     */
    String autorizar(Money monto, String referencia);

    /**
     * Captura un pago previamente autorizado.
     * @param referencia La referencia de autorización
     */
    void capturar(String referencia);

    /**
     * Reembolsa un pago.
     * @param referencia La referencia del pago a reembolsar
     * @param monto El monto a reembolsar
     */
    void reembolsar(String referencia, Money monto);
}

