/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mrowlie
 */
public class PlayerDisk extends Disk{
    
    
    int score;
    
    BitmapFont font;
    BitmapText text;
    
    public static int playerAmount;
    public static ConcurrentHashMap<Integer, PlayerDisk> playerMap = new ConcurrentHashMap();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public PlayerDisk(AssetManager assetManager, int id) {
        super(assetManager, ColorRGBA.Blue, Main.PLAYER_R, id);
        score = 0;
        font = assetManager.loadFont("Interface/Fonts/Console.fnt");
        text = new BitmapText(font, false);
        text.setSize(26);
        text.setColor(ColorRGBA.Black);
        text.setText("p" + id);
        text.setQueueBucket(RenderQueue.Bucket.Transparent);
        this.diskNode.attachChild(text);
        text.setLocalTranslation(- text.getHeight() / 2, text.getHeight() / 2, Main.FRAME_THICKNESS + 1f);
        
        PlayerDisk.playerMap.put(id, this);
        
    }
    
    public void addScore(int amount) {
        this.score += amount;
        
    }
    
    public void removeScore(int amount) {
        this.score -= amount;
    }
    
    public void moveNorth(float tpf){
        //this.setVelocity(this.getVelocity().getX(), this.getVelocity().getY() + 500 * tpf);
    }
    
    public void moveSouth(float tpf){
        //this.setVelocity(this.getVelocity().getX(), this.getVelocity().getY() - 500 * tpf);
    }
    
    public void moveEast(float tpf){
        //this.setVelocity(this.getVelocity().getX() + 500 * tpf, this.getVelocity().getY());
    }
    
    public void moveWest(float tpf){
        //this.setVelocity(this.getVelocity().getX() - 500 * tpf, this.getVelocity().getY());
    }
    
}
