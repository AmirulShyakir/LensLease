/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author leeannong
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException()
    {
    }
    
    
    
    public UserAlreadyExistsException(String msg)
    {
        super(msg);
    }
    
}
