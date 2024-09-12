package org.paulpucha.api.cuenta.bancaria.cliente.exception;

/**
 * <b> Clase ClienteException implementada para el manejo de excepciones del msvc-cliente. </b>
 *
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Paul Pucha $, $Date: 08 jul. 2024 $]
 *     </p>
 */
public class ClienteException extends Exception {

    private static final long serialVersionUID = 3263046821289003394L;
    private final Integer errorCode;

    public ClienteException() {
        super();
        this.errorCode = null;
    }

    public ClienteException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public ClienteException(String message, Throwable cause, Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ClienteException(Throwable cause) {
        super(cause);
        this.errorCode = null;
    }

    public ClienteException(String message) {
        super(message);
        this.errorCode = null;
    }

    public ClienteException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "CuentaException{" +
                "message=" + getMessage() +
                ", errorCode=" + errorCode +
                ", cause=" + getCause() +
                '}';
    }
}
