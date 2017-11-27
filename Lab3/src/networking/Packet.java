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
        protected int senderID;
        
        public MyAbstractMessage(int senderID) {
            this.senderID = senderID;
        }
        
        public int getSenderID(){
            return senderID;
        }
    }
  
    @Serializable
    public static class TestPacket extends MyAbstractMessage {
        
        protected String message;
        
        public TestPacket(int senderID, String message) {
            super(senderID);
            this.message = message;

        }
        
        public String getMessage(){
            return message;
        }
    }
    
}


  
