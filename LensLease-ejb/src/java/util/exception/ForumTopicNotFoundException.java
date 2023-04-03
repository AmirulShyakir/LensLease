/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;



public class ForumTopicNotFoundException extends Exception
{
    public ForumTopicNotFoundException()
    {
    }
    
    public ForumTopicNotFoundException(String msg)
    {
        super(msg);
    }
}