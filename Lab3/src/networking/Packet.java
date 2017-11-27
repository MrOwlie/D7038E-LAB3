/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author mrowlie
 */
public class Packet {
    
    
    abstract public static class MyAbstractMessage extends AbstractMessage {
        
        public MyAbstractMessage() {
        }
    }
  
    @Serializable
    public static class TestPacket extends MyAbstractMessage {
        
        protected String message;
        
        public TestPacket(){
            
        }
        
        public TestPacket(String message) {
            this.message = message;
        }
        
        public String getMessage(){
            return message;
        }
    }
    
}


  
