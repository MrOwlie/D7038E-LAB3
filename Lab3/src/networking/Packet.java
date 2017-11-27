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
        
    }
  
    @Serializable
    abstract public static class testPacket extends MyAbstractMessage {
        
        String message;
        
        public testPacket(int senderID, String message) {
            super(senderID);
            this.message = message;

        }
    }
    
}


  
