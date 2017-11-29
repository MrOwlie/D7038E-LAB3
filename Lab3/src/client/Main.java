package client;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.RenderManager;
import networking.Packet.TestPacket;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    //constants
    public static final String NAME = "MultiDisk";
    public static final String DEFAULT_SERVER = "localhost";
    public static final int PORT = 6143;
    public static final int VERSION = 1;
    
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
    
    static Client myClient;
    static Modeling model;
    static AssetManager refAssetManager;
    static InputManager refInputManager;
    
    static GameState gameState;
    static InitState initState;
    static EndState endState;
    
    Thread netReadThread;
    Thread netWriteThread;
    Thread modelingThread;
    
    //test
    float timeElapsed = 0;
    static final float TIME = 10f;
    //
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    //test 2

    @Override
    public void simpleInitApp() {        
        //Register packets
        Serializer.registerClass(TestPacket.class);
        refAssetManager = assetManager;
        refInputManager = inputManager;
        //Start client
        
        try{
            
            model = new Modeling();
            NetRead netRead =  new NetRead();
            myClient = Network.connectToServer(NAME, VERSION, DEFAULT_SERVER, PORT, PORT);
            myClient.addMessageListener(netRead);
            myClient.start();       
            
            netWriteThread = new Thread(new NetWrite(myClient));
            netReadThread = new Thread(netRead);
            modelingThread = new Thread(model);
            
            netWriteThread.start();
            netReadThread.start();
            modelingThread.start();
        }
        catch(Exception e){
            System.out.println("ERROR CONNECTING");
            System.out.println(e.getMessage());
        }
        
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        timeElapsed += tpf;
        if(timeElapsed > TIME){
            NetWrite.addMessage(new TestPacket("Hej server"));
            timeElapsed = 0f;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
