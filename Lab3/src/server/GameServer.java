/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mrowlie
 */
public class GameServer {
    
    protected Server server;
    
    public GameServer(String gameName, int version, int port) {
        //Start the server
        try {
            this.server = Network.createServer(gameName, version, port, port);
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Register packets
        Serializer.registerClass(networking.Packet.TestPacket.class);
        
    }
    
    
    
}
