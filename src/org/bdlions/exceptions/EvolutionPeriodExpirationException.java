package org.bdlions.exceptions;

/**
 *
 * @author nazmul hasan
 */
public class EvolutionPeriodExpirationException extends Exception{
    public EvolutionPeriodExpirationException(){
        this("Evolution Period is expired.");
    }
    public EvolutionPeriodExpirationException(String message){
        super(message);
    }
    
}
