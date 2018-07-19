/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *
 * @author Leonardo
 */
public class Item extends Canvas implements Runnable{
    int iditem;
    int x = 0;
    int y = 0;
    int xc = 0;
    int yc = 0;
    Universo u;
    boolean orientationx;
    int speed=7;
    boolean hormov;
    boolean vermov;
    BufferedImage imgCar;
    boolean done;
    String tipo;
    Thread itemthread;
    
    /*
    x : distance from canvas to left jframe
    y : distance from canvas to top jframe
    horizontal: movement horiz or ver
    xc: size of canvas(x)
    yc: size of canvas(y)
    iditm: identifier UNIQUE for a item
    tipo: type of item
    */
    public Item(Universo u,int x,int y,boolean horizontal,int xc, int yc,int iditm,String tipo) throws IOException {
        this.tipo=tipo;
        this.x = 1135;
        this.u=u;
        this.x=x;
        this.xc=xc;
        this.yc=yc;
        this.y=y;
        this.iditem=iditm;
        this.orientationx=horizontal;
        setBounds(x, y, xc, yc);
        //this.setText("Obstacle");
        this.imgCar = ImageIO.read(new File("enemy.jpg"));
        //Graphics2D g = this.imgCar.createGraphics();
        //g.drawImage(this.imgCar, 0, 0, null);
        //g.drawImage(this.imgCar, 0, 0,xc,yc, null);
        //this.setBackground(Color.red);
        //this.setOpaque(true);
        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawImage(this.imgCar, 0, 0, null);
        g.drawImage(this.imgCar, 0, 0,xc,yc, null);
        //this.setBackground(Color.red);
    }
    @Override
    public void run() {
        done=false;
        repaint();
        while(!done) {
            try {
                u.CheckColision(this,null);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }
            Actions.MovementItem(this);
        }
        setBounds(1000000, 1000000, 0, 0);
    }

    public Thread getItemthread() {
        return itemthread;
    }

    public void setItemthread(Thread itemthread) {
        this.itemthread = itemthread;
    }

    public void setbounds(int x,int y, int xc, int yc){
        this.x=x;
        this.y=y;
        this.xc=xc;
        this.yc=yc;
        
    }
            
            
    public void runThread(){
        this.itemthread = new Thread(this);
        this.itemthread.start();
    }
    
    public void changeMovH(){
        if(hormov){
        hormov=false;
        }else{
        hormov=true;
        }
    }
    
    public void changeMovV(){
        if(vermov){
        vermov=false;
        }else{
        vermov=true;
        }
    }
    
    
    public void movRight() {

            x++;
            setBounds(x, y, xc, yc);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    
    public void movLeft() {

            x--;
            setBounds(x, y, xc, yc);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    public void movGravity() {
        if (y+yc < 600) {
            y++;
            setBounds(x, y, xc, yc);
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void movBottom() {

            y++;
            setBounds(x, y, xc, yc);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    public void movTop() {

            y--;
            setBounds(x, y, xc, yc);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    public boolean isOrientationx() {
        return orientationx;
    }

    public void setOrientationx(boolean orientationx) {
        this.orientationx = orientationx;
    }

    public boolean isHormov() {
        return hormov;
    }

    public void setHormov(boolean hormov) {
        this.hormov = hormov;
    }

    public boolean isVermov() {
        return vermov;
    }

    public void setVermov(boolean vermov) {
        this.vermov = vermov;
    }

    
    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXc() {
        return xc;
    }

    public void setXc(int xc) {
        this.xc = xc;
    }

    public int getYc() {
        return yc;
    }

    public void setYc(int yc) {
        this.yc = yc;
    }
    
    

    public Universo getU() {
        return u;
    }

    public void setU(Universo u) {
        this.u = u;
    }

    public boolean isHor() {
        return orientationx;
    }

    public void setHor(boolean hor) {
        this.orientationx = hor;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    
}
