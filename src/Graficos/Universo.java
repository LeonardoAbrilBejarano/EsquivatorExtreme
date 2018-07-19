/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import static Graficos.Actions.ColisionDestroy;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Leonardo
 */
public class Universo extends JFrame implements KeyListener, Runnable {

    /**
     *
     * @author Leonardo
     */
    private JPanel panel;
    private CopyOnWriteArrayList obs;
    private int contitems;
    private Player p1;
    private JTable tStatistics;
    public boolean keepRuning=true;

    //ArrayList por si hay multijugaddor
    //private ArrayList player;
    private ArrayList scores;

    public Universo() throws IOException {
        init();
    }

    private void addStatisticsToPane(Container pane) {
        //Name
        scores = new ArrayList();
        int namey = 0;

        JLabel name = new JLabel();
        name.setBackground(Color.CYAN);
        name.setText("Player");
        name.setBounds(0, namey, 100, 15);
        pane.add(name);
        namey = namey + 15;
        for (int i = 0; i < 1; i++) {
            JLabel namerow = new JLabel();
            namerow.setBackground(Color.CYAN);
            namerow.setText("Leo");
            namerow.setBounds(0, namey, 100, 15);
            pane.add(namerow);
            scores.add(namerow);
            namey = namey + 15;
        }

        JLabel score = new JLabel();
        score.setBackground(Color.CYAN);
        score.setText("Score");
        namey = 0;
        score.setBounds(50, namey, 50, 15);
        pane.add(score);
        namey = namey + 15;
        for (int i = 0; i < 1; i++) {
            JLabel scorerow = new JLabel();
            scorerow.setBackground(Color.CYAN);
            scorerow.setText("0");
            scorerow.setBounds(50, namey, 50, 15);
            pane.add(scorerow);
            namey = namey + 15;
            p1.setScore(scorerow);
        }

    }

    public void addpointscore(Player p) {
        JLabel playerscorerow = p.getScore();
        int score = Integer.parseInt(playerscorerow.getText());
        score = score + 10;
        playerscorerow.setText("" + score);
        p.setScore(playerscorerow);
    }

    private void init() throws IOException {
        //mg= new MemoryGame(obs);
        contitems = 0;
        setTitle("abstract");
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 600);
        panel = new JPanel();
        panel.setLayout(null);
        add(panel);
        obs = new CopyOnWriteArrayList();
        initPlayers();
        addStatisticsToPane(panel);
        addKeyListener(this);
        initWalls();
        initTunel();

    }

    public void initTunel() throws IOException {
        Tunel t = new Tunel(this.contitems, this, "tunel", 600, 300, 300, 300, "blackhole.jpg");
        contitems++;
        panel.add(t);
        obs.add(t);
    }

    public void CheckColision(Item n, Player p) throws InterruptedException {
        //if (obs.contains(i)) {
        if (n == null) {
            CheckColision(p);
        } else {
            CheckColision(n);
        }
    }

    public void CheckColision(Player i) {
        //Futuro.Multiplayer
        //if (obs.contains(i)) {
        String type;
        for (int k = 0; k < obs.size(); k++) {
            type = Actions.getElementFromArray(k, obs);
            if (type.equalsIgnoreCase("pared")) {
                Wall i2 = (Wall) obs.get(k);
                if (i.getIditem() != i2.getIditem()) {
                    //if (checkXwall(i, i2) && checkYwall(i, i2)) {
                    if (i.getBounds().intersects(i2.getBounds())) {
                        Actions.PlayerOverpass(i2, i);
                    }
                }
            }
            if (type.equalsIgnoreCase("item")) {
                Item i2 = (Item) obs.get(k);
                if (i.getIditem() != i2.getIditem()) {
                    if (i.getBounds().intersects(i2.getBounds())) {
                        Actions.ColisionDestroy(i, i2, obs);
                    }
                }
            }
        }
        //}
    }

    public void CheckColision(Item i) throws InterruptedException {
        //if (obs.contains(i)) {
        String type;
        //synchronized (obs) {
        for (int k = obs.size() - 1; k > 0; k--) {
            type = Actions.getElementFromArray(k, obs);
            if (type.equalsIgnoreCase("pared")) {
                Wall i2;
                if (k > obs.size() - 1) {
                    i2 = (Wall) obs.get(obs.size() - 1);
                } else {
                    i2 = (Wall) obs.get(k);
                }
                if (i.getIditem() != i2.getIditem()) {
                    //if (checkXwall(i, i2) && checkYwall(i, i2)) {
                    if (i.getBounds().intersects(i2.getBounds())) {
                        Actions.ChangeMov(i);
                    }
                }
            }
            if (type.equalsIgnoreCase("tunel")) {
                Tunel i2;
                if (k > obs.size() - 1) {
                    i2 = (Tunel) obs.get(obs.size() - 1);
                } else {
                    i2 = (Tunel) obs.get(k);
                }
                if (i.getBounds().intersects(i2.getBounds()) || i2.idactualitem == i.getIditem()) {
                    if (i2.idactualitem == i.getIditem()) {
                        i2.travessingTunel(i);
                        //Este metodo SI tiene sentido
                        //Necesitan estar tanto el travessing tunel como el checkifitemisontunel
                    } else {
                        i2.enteringTunel(i);

                    }
                }
            }
            if (type.equalsIgnoreCase("item")) {
                Item i2;
                if (k > obs.size() - 1) {
                    i2 = (Item) obs.get(obs.size() - 1);
                } else {
                    i2 = (Item) obs.get(k);
                }
                if (i.getIditem() != i2.getIditem()) {
                    if (i.getBounds().intersects(i2.getBounds())) {

                        //Solucion para los problemas del tunel
                        i2.setX(100000);
                        i2.setY(100000);
                        i2.setXc(0);
                        i2.setYc(0);
                        i2.setbounds(100000, 100000, 0, 0);

                        i.setX(100000);
                        i.setY(100000);
                        i.setXc(0);
                        i.setYc(0);
                        i.setbounds(100000, 100000, 0, 0);

                        Actions.ColisionDestroy(i, i2, obs);

                        this.addpointscore(p1);
                    }
                }
            }
            if (type.equalsIgnoreCase("player")) {
                Player i2;
                if (k > obs.size() - 1) {
                    i2 = (Player) obs.get(obs.size() - 1);
                } else {
                    i2 = (Player) obs.get(k);
                }
                if (i.getIditem() != i2.getIditem()) {
                    if (i.getBounds().intersects(i2.getBounds())) {
                        Actions.ColisionDestroy(i2, i, obs);
                        i2.dead = true;
                    }
                }
            }
            //}
        }
        //}
    }

    public boolean checkX(Item i, Item i2) {
        int n1 = i.getX() - i2.getX();
        if (n1 < 0) {
            n1 = n1 * -1;
        }
        int n2 = (i.getXc() + i2.getXc()) / 2;
        if (n1 < n2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkXwall(Item i, Wall i2) {
        int n1 = i.getX() - i2.getX();
        if (n1 < 0) {
            n1 = n1 * -1;
        }
        int n2 = (i.getXc() + i2.getXc()) / 2;
        if (n1 < n2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkY(Item i, Item i2) {
        int n1 = i.getY() - i2.getY();
        if (n1 < 0) {
            n1 = n1 * -1;
        }
        int n2 = (i.getYc() + i2.getYc()) / 2;
        if (n1 < n2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkYwall(Item i, Wall i2) {
        int n1 = i.getY() - i2.getY();
        if (n1 < 0) {
            n1 = n1 * -1;
        }
        int n2 = (i.getYc() + i2.getYc()) / 2;
        if (n1 < n2) {
            return true;
        } else {
            return false;
        }
    }

    private void initPlayers() throws IOException {
        Player p = new Player(this, 700, 300, true, 75, 25, this.contitems, "player");
        contitems++;
        new Thread(p).start();
        panel.add(p);
        obs.add(p);
        this.p1 = p;
    }

    public void initWalls() throws IOException {
        /*
        Wall n = new Wall(this.contitems, this, "pared", 600, 300, 150, 150,"wall.jpg");
        contitems++;
        panel.add(n);
        obs.add(n);

        //TOP
         */
        Wall n2 = new Wall(this.contitems, this, "pared", 100, 0, 1200, 1, "wall.jpg");
        contitems++;
        //new Thread(n2).start();
        panel.add(n2);
        obs.add(n2);
        //BOT
        Wall n3 = new Wall(this.contitems, this, "pared", 100, 570, 1200, 1, "wall.jpg");
        contitems++;
        //new Thread(n3).start();
        panel.add(n3);
        obs.add(n3);
        //Right
        Wall n4 = new Wall(this.contitems, this, "pared", 1300, 0, 1, 600, "wall.jpg");
        contitems++;
        //new Thread(n4).start();
        panel.add(n4);
        obs.add(n4);
        //left
        Wall n5 = new Wall(this.contitems, this, "pared", 100, 0, 1, 600, "wall.jpg");
        contitems++;
        //new Thread(n5).start();
        panel.add(n5);
        obs.add(n5);
    }

    public void generateObstacles() throws IOException {
        Random rnd = new Random();
        int k = (int) (rnd.nextDouble() * 515);
        Item o = new Item(this, 100, k, true, 75, 25, this.contitems, "item");
        contitems++;
        //new Thread(o).start();
        o.runThread();
        panel.add(o);
        obs.add(o);
        k = (int) (rnd.nextDouble() * 1035);
        Item o2 = new Item(this, k + 100, 0, false, 75, 25, contitems, "item");
        contitems++;
        //new Thread(o2).start();
        o2.runThread();
        panel.add(o2);
        obs.add(o2);
    }

    //Random  rnd = new Random();
    //(int)(rnd.nextDouble() * 6 + 1);
    /*
    public static void main(String[] args) throws IOException {
        Universo u = new Universo();
        u.setLocationRelativeTo(null);
        u.setVisible(true);
        new Thread(u).start();
    }
     */
    @Override
    public void run() {
        boolean gameover=false;
        while(keepRuning){
        int interval = 2000;
        while (!p1.dead&&keepRuning) {
            try {
                //System.out.println(p1.dead);
                this.generateObstacles();
                if (p1.dead) {
                    interval = 0;
                }
            } catch (IOException ex) {
                Logger.getLogger(Universo.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (!p1.dead) {
                    Thread.sleep(interval);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Universo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(!gameover){
        Wall n5 = null;
        try {
            n5 = new Wall(this.contitems, this, "pared", 0, 0, 1300, 600, "gameover2.png");
        } catch (IOException ex) {
            Logger.getLogger(Universo.class.getName()).log(Level.SEVERE, null, ex);
        }
        contitems++;
        panel.removeAll();
        panel.add(n5);
        obs.add(n5);
        String type;
        for (int i = 0; i < obs.size(); i++) {
            type = Actions.getElementFromArray(i, obs);
            if (type.equalsIgnoreCase("pared")) {
                Wall i2 = (Wall) obs.get(i);
                i2.done = true;

            }
            if (type.equalsIgnoreCase("item")) {
                Item i2 = (Item) obs.get(i);
                i2.done = true;
            }
            if (type.equalsIgnoreCase("player")) {
                Player i2 = (Player) obs.get(i);
                i2.done = true;
                i2.removethis();
            }
        }
        }
        gameover=true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.print("HELLO");
        if (p1.dead == false) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                p1.movRight();
                p1.repaint();
                //Know the direction if it hits by a wall
                p1.setOrientationx(true);
                p1.setHormov(true);
                CheckColision(p1);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                p1.movLeft();
                p1.repaint();
                p1.setOrientationx(true);
                p1.setHormov(false);
                CheckColision(p1);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_T) {
                p1.movTop();
                p1.repaint();
                p1.setOrientationx(false);
                p1.setVermov(false);
                CheckColision(p1);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                p1.movBottom();
                p1.repaint();
                p1.setOrientationx(false);
                p1.setVermov(true);
                CheckColision(p1);
            }
        }
    }

    //DRIVERS FOR APLICATION ANDROID
    public void playermoveRight() {
        if (p1.dead == false) {
            p1.movRight();
            p1.repaint();
            //Know the direction if it hits by a wall
            p1.setOrientationx(true);
            p1.setHormov(true);
            CheckColision(p1);
        }
    }

    public void playermoveLeft(){
        if (p1.dead == false) {
                p1.movLeft();
                p1.repaint();
                p1.setOrientationx(true);
                p1.setHormov(false);
                CheckColision(p1);
        }
    }
    
    public void playermoveTop(){
        if (p1.dead == false) {
                p1.movTop();
                p1.repaint();
                p1.setOrientationx(false);
                p1.setVermov(false);
                CheckColision(p1);
        }
    }
    
    public void playermoveBot(){
        if (p1.dead == false) {
                p1.movBottom();
                p1.repaint();
                p1.setOrientationx(false);
                p1.setVermov(true);
                CheckColision(p1);
        }
    }

    //Restart game
    public void setKeepRuning(boolean keepRuning) {
        this.keepRuning = keepRuning;
    }
    
}
