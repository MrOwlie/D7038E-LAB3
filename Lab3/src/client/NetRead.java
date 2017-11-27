/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import networking.Packet;

/**
 *
 * @author mrowlie
 */
public class NetRead implements Runnable, MessageListener<Client> {

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageReceived(Client source, Message m) {
        if(m instanceof Packet.testPacket){
           Packet.testPacket packet = (Packet.testPacket)m;
           System.out.println(packet.getMessage());
        }
    }
    
}
