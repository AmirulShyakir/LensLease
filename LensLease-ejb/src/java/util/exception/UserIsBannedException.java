/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;



public class UserIsBannedException extends Exception
{
    public UserIsBannedException()
    {
    }
    
    
    
    public UserIsBannedException(String msg)
    {
        super(msg);
    }
}