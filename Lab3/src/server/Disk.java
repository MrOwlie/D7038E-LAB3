/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import java.util.ArrayList;

/**
 *
 * @author mrowlie
 */
public abstract class Disk {
    
    
    public static ArrayList<Disk> disks = new ArrayList<Disk>();
    
    public boolean justCollided;
    public boolean hasCollided;
    
    public float radius;
    
    private float mass;
    
    public Vector2f pos;
    
    private Vector2f v;
    private final float friction = 0.2f;
    
    public int diskID;
    private static int diskAmount = 0;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Disk(float radius) {
            this.radius = radius;
            this.pos = new Vector2f();
            this.v = new Vector2f(0, 0);
            this.mass = FastMath.PI * FastMath.sqr(radius);
            this.diskID = Disk.diskAmount++;
            
            Disk.disks.add(this);
        
    }
    
    public void tick(float tpf) {
        if(!Disk.disks.contains(this)){
            Disk.disks.add(this);
        }
        
        pos.x = pos.x + v.x * tpf;
        pos.y = pos.y + v.y * tpf;
        
        
        v.set(v.x * (1 / (1 + friction * tpf)), v.y * (1 / (1 + friction * tpf)));
        
        resolveCollision(tpf);
        
        
    }
    
    public void setVelocity(float x, float y) {
        this.v.set(x, y);
    }
    
    public Vector2f getVelocity() {
        return this.v;
    }
    
    public void setPosition(float x, float y) {
        this.pos.set(x, y);
    }
    
    public void setPosition(Vector2f pos){
        this.pos = pos;
    }
    
    public void resolveCollision(float tpf){
        //Frame collision
        //north
        if(pos.y + radius + v.y * tpf > Main.FREE_AREA_WIDTH / 2){
            v.set(v.x, -v.y);
            this.pos.y = Main.FREE_AREA_WIDTH / 2 - (this.radius * 1.1f);
            Main.server.netWrite.updateDisk(this.diskID, this.pos.x, this.pos.y, this.v.x, this.v.y);
        }
        //south
        if(pos.y - radius + v.y * tpf < -Main.FREE_AREA_WIDTH / 2){
            v.set(v.x, -v.y);
            this.pos.y = - Main.FREE_AREA_WIDTH / 2 + (this.radius * 1.1f);
            Main.server.netWrite.updateDisk(this.diskID, this.pos.x, this.pos.y, this.v.x, this.v.y);
        }
        //east
        if(pos.x + radius + v.x * tpf > Main.FREE_AREA_WIDTH / 2){
            v.set(-v.x, v.y);
            this.pos.x = Main.FREE_AREA_WIDTH / 2 - (this.radius * 1.1f);
            Main.server.netWrite.updateDisk(this.diskID, this.pos.x, this.pos.y, this.v.x, this.v.y);
        }
        //west
        if(pos.x - radius + v.x * tpf < -Main.FREE_AREA_WIDTH / 2){
            v.set(-v.x, v.y);
            this.pos.x = - Main.FREE_AREA_WIDTH / 2 + (this.radius * 1.1f);
            Main.server.netWrite.updateDisk(this.diskID, this.pos.x, this.pos.y, this.v.x, this.v.y);
        }
        
        //Disk collisions
        float dx;
        float dy;
        float distance;
        
          
        for(Disk d : Disk.disks){
            if(d != this){
                dx = this.pos.x - d.pos.x;
                dy = this.pos.y - d.pos.y;
                distance = FastMath.sqrt(FastMath.sqr(dx) + FastMath.sqr(dy));
                if(distance < d.radius + this.radius){
                    if((this.getClass() == PlayerDisk.class) && (d.getClass() == PositiveDisk.class)) {
                        PlayerDisk playerDisk = (PlayerDisk) this;
                        PositiveDisk posDisk = (PositiveDisk) d;
                        playerDisk.addScore(posDisk.worth);
                        posDisk.subWorth();
                        
                    } else if (this.getClass() == PositiveDisk.class && (d.getClass() == PlayerDisk.class)) {
                        PlayerDisk playerDisk = (PlayerDisk) d;
                        PositiveDisk posDisk = (PositiveDisk) this;
                        playerDisk.addScore(posDisk.worth);
                        posDisk.subWorth();
                    } else if ((this.getClass() == PlayerDisk.class) && (d.getClass() == NegativeDisk.class)) {
                        PlayerDisk playerDisk = (PlayerDisk) this;
                        NegativeDisk negDisk = (NegativeDisk) d;
                        playerDisk.removeScore(3);

                    } else if (this.getClass() == NegativeDisk.class && (d.getClass() == PlayerDisk.class)) {
                        PlayerDisk playerDisk = (PlayerDisk) d;
                        NegativeDisk negDisk = (NegativeDisk) this;
                        playerDisk.removeScore(3);
                        
                        
                    }
                    Disk.diskCollision(tpf, this, d);
                }    
            }
            
        }
        
            
    }
    
    public void setVelocity(Vector2f velocity){
        this.v = velocity;
    }
    
    static void diskCollision(float tpf, Disk disk1, Disk disk2) {
        
        Vector2f relativePos = disk1.pos.subtract(disk2.pos);
        Vector2f relativeSpeed = disk1.v.subtract(disk2.v);
        float collisionTime = (FastMath.sqrt(relativePos.x*relativePos.x + relativePos.y*relativePos.y) - 
                (disk1.radius+disk2.radius)) / 
                FastMath.sqrt(relativeSpeed.x*relativeSpeed.x + relativeSpeed.y*relativeSpeed.y);
        
        disk1.setPosition(disk1.pos.x - disk1.v.x * -collisionTime, disk1.pos.y - disk1.v.y * -collisionTime);
        disk2.setPosition(disk2.pos.x - disk2.v.x * -collisionTime, disk2.pos.y - disk2.v.y * -collisionTime);
        
        double collisionAngle = FastMath.atan2((disk2.pos.y - disk1.pos.y), (disk2.pos.x - disk1.pos.x));         
        double speed1 = FastMath.sqrt(FastMath.sqr(disk1.v.x) + FastMath.sqr(disk1.v.y));
        double speed2 = FastMath.sqrt(FastMath.sqr(disk2.v.x) + FastMath.sqr(disk2.v.y));

        double direction1 = FastMath.atan2(disk1.v.y, disk1.v.x);
        double direction2 = FastMath.atan2(disk2.v.y, disk2.v.x);
        double newSpeedX1 = speed1 * FastMath.cos((float)direction1 - (float)collisionAngle);
        double newSpeedY1 = speed1 * FastMath.sin((float)direction1 - (float)collisionAngle);
        double newSpeedX2 = speed2 * FastMath.cos((float)direction2 - (float)collisionAngle);
        double newSpeedY2 = speed2 * FastMath.sin((float)direction2 - (float)collisionAngle);

        double finalSpeedX1 = ((disk1.mass - disk2.mass) * newSpeedX1 + (disk2.mass + disk2.mass) * newSpeedX2) / (disk1.mass + disk2.mass);
        double finalSpeedX2 = ((disk1.mass + disk1.mass) * newSpeedX1 + (disk2.mass - disk1.mass) * newSpeedX2) / (disk1.mass + disk2.mass);
        double finalSpeedY1 = newSpeedY1;
        double finalSpeedY2 = newSpeedY2;

        double cosAngle = FastMath.cos((float)collisionAngle);
        double sinAngle = FastMath.sin((float)collisionAngle);
        disk1.v.x = (float) (cosAngle * finalSpeedX1 - sinAngle * finalSpeedY1);
        disk1.v.y = (float) (sinAngle * finalSpeedX1 + cosAngle * finalSpeedY1);
        disk2.v.x = (float) (cosAngle * finalSpeedX2 - sinAngle * finalSpeedY2);
        disk2.v.y = (float) (sinAngle * finalSpeedX2 + cosAngle * finalSpeedY2);

        Main.server.netWrite.updateDisk(disk1.diskID, disk1.pos.x, disk1.pos.y, disk1.v.x, disk1.v.y);
        Main.server.netWrite.updateDisk(disk2.diskID, disk2.pos.x, disk2.pos.y, disk2.v.x, disk2.v.y);

        
    }
    
    
}















