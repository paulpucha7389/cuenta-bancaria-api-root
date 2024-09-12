package org.paulpucha.api.cuenta.bancaria.cuenta.exception;

/**
 * <b> Clase CuentaException implementada para el manejo de excepciones del msvc-cuenta. </b>
 *
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Paul Pucha $, $Date: 08 jul. 2024 $]
 *     </p>
 */
public class CuentaException extends Exception {

    private static final long serialVersionUID = 3263046821289003394L;

    private final Integer errorCode;

    public CuentaException() {
        super();
        this.errorCode = null;
    }

    public CuentaException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public CuentaException(String message, Throwable cause, Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CuentaException(Throwable cause) {
        super(cause);
        this.errorCode = null;
    }

    public CuentaException(String message) {
        super(message);
        this.errorCode = null;
    }

    public CuentaException(String message, Integer errorCode) {
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
