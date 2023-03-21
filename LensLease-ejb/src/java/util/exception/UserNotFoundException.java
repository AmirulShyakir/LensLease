/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;



public class UserNotFoundException extends Exception
{
    public UserNotFoundException()
    {
    }
    
    
    
    public UserNotFoundException(String msg)
    {
        super(msg);
    }
}