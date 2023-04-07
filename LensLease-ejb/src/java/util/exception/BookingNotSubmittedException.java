/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;



public class BookingNotSubmittedException extends Exception
{
    public BookingNotSubmittedException()
    {
    }
    
    
    
    public BookingNotSubmittedException(String msg)
    {
        super(msg);
    }
}