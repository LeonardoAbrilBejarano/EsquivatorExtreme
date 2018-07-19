/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class Player extends Canvas implements Runnable,KeyListener{
    int iditem;
    int x = 0;
    int y = 0;
    int xc = 0;
    int yc = 0;
    Universo u;
    boolean orientationx;
    int speed=1;
    boolean hormov;
    boolean vermov;
    BufferedImage imgCar;
    boolean done;
    String tipo;
    boolean dead=false;
    JLabel score;
    
    
    public Player(Universo u,int x,int y,boolean horizontal,int xc, int yc,int iditm,String tipo) throws IOException {
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
        Graphics2D g = this.imgCar.createGraphics();
        //g.drawImage(this.imgCar, 0, 0, null);
        g.drawImage(this.imgCar, 0, 0,xc,yc, null);
        //this.setBackground(Color.red);
        //this.setOpaque(true);
        
    }

    
    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    
    
    public JLabel getScore() {
        return score;
    }

    public void setScore(JLabel score) {
        this.score = score;
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
                u.CheckColision(null,this);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        setBounds(1000000, 1000000, 0, 0);
    }

    public int getIditem() {
        return iditem;
    }

    public void removethis(){
        setBounds(1000000, 1000000, 0, 0);
        
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

    public boolean isOrientationx() {
        return orientationx;
    }

    public void setOrientationx(boolean orientationx) {
        this.orientationx = orientationx;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public BufferedImage getImgCar() {
        return imgCar;
    }

    public void setImgCar(BufferedImage imgCar) {
        this.imgCar = imgCar;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      /*
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            movRight();
            repaint();
            u.CheckColision(this);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            movLeft();
            repaint();
            u.CheckColision(this);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            movBottom();
            repaint();
            u.CheckColision(this);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            movTop();
            repaint();
            u.CheckColision(this);
        }
*/
    }
       
    public void movRight() {
            //x++;
            x=x+10;
            setBounds(x, y, xc, yc);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    
    public void movLeft() {
            //x--;
            x=x-10;
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
            //y++;
            y=y+10;
            setBounds(x, y, xc, yc);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    public void movTop() {
            //y--;
            y=y-10;
            setBounds(x, y, xc, yc);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }

    }


    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
