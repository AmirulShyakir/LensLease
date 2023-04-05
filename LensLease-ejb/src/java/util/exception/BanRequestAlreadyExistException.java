/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author junwe
 */
public class BanRequestAlreadyExistException extends Exception {

    /**
     * Creates a new instance of <code>BanRequestAlreadyExistException</code>
     * without detail message.
     */
    public BanRequestAlreadyExistException() {
    }

    /**
     * Constructs an instance of <code>BanRequestAlreadyExistException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public BanRequestAlreadyExistException(String msg) {
        super(msg);
    }
}
