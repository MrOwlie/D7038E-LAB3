/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import networking.Packet.TestPacket;

/**
 *
 * @author mrowlie
 */
public class Main extends SimpleApplication{
    //server constants
    public static final String NAME = "MultiDisk";
    public static final String DEFAULT_SERVER = "localhost";
    public static final int PORT = 6143;
    public static final int VERSION = 1;
    float timeElapsed = 0;
    public static final float time = 10f; 
    
    private static GameServer server;

    public Main() {
        Main.server = new GameServer(NAME, VERSION, PORT);
    }
    
    public static void main(String[] args){
        Main app = new Main();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        timeElapsed += tpf;
        if(timeElapsed > time){
            Main.server.netWrite.addMessage(new TestPacket("Hej client"));
            timeElapsed = 0f;
        }
    }
    
    @Override
    public void destroy() {
        System.out.println("DESRYTRY");
        server.server.close();
        super.destroy();
    }
    
}
