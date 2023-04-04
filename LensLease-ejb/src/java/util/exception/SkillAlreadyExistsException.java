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
public class SkillAlreadyExistsException extends Exception {
    public SkillAlreadyExistsException() {}
    
    public SkillAlreadyExistsException(String msg) {
        super(msg);
    }
}
