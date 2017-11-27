/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import java.util.ArrayList;
import networking.Packet.TestPacket;

/**
 *
 * @author mrowlie
 */
public class Main extends SimpleApplication{
    //Server constants
    public static final String NAME = "MultiDisk";
    public static final String DEFAULT_SERVER = "localhost";
    public static final int PORT = 6143;
    public static final int VERSION = 1;
    float timeElapsed = 0;
    public static final float time = 10f;
    
    //Game constants
    static final float START_TIME = 30f;
    
    //Frame constants
    static final float FRAME_THICKNESS = 24f;
    static final float FREE_AREA_WIDTH = 492f;
    static final float FRAME_SIZE = FREE_AREA_WIDTH + 2f * FRAME_THICKNESS;
    
    //Location constants
    static final float PLAYER_COORD = FREE_AREA_WIDTH / 6;
    static final float POSNEG_MAX_COORD = FREE_AREA_WIDTH / 3;
    static final float POSNEG_BETWEEN_COORD = PLAYER_COORD;
    
    //Disk radius constants
    static final float PLAYER_R = 20f;
    static final float POSDISK_R = 16f;
    static final float NEGDISK_R = 16f;
    
    public static Main app;
    
    public static int playerAmount = 1;
    
    public static ArrayList<PlayerDisk> players = new ArrayList();
    
    private static GameServer server;

    private static Thread modelingThread;
    private static Modeling modeling;
    
    public Main() {
        
        
        
    }
    
    public static void main(String[] args){
        Main application = new Main();
        Main.app = application;
        application.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        Main.server = new GameServer(NAME, VERSION, PORT);
        Main.modelingThread = new Thread(modeling = new Modeling());
        Main.modelingThread.start();
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
        server.server.close();
        super.destroy();
    }
    
}
