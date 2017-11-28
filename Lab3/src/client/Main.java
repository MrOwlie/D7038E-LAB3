package client;

import com.jme3.app.SimpleApplication;
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
    
    Client myClient;
    Modeling model;
    
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
