/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Leonardo
 */
public class Actions {

    public static String getElementFromArray(int k,CopyOnWriteArrayList c) {
        String txt = "";
        if (k > c.size() - 1) {
            txt = c.get(c.size() - 1).getClass().getCanonicalName();
        } else {
            txt = c.get(k).getClass().getCanonicalName();
        }

        //System.out.println(txt + txt.equalsIgnoreCase("Graficos.Wall"));
        //System.out.println(txt);
        if (txt.equalsIgnoreCase("Graficos.Wall")) {
            return "pared";
        }
        if (txt.equalsIgnoreCase("Graficos.Player")) {
            return "player";
        }
        if (txt.equalsIgnoreCase("Graficos.Item")) {
            return "item";
        }
        if (txt.equalsIgnoreCase("Graficos.Tunel")) {
            return "tunel";
        }
        return "";

    }
    public static void ColisionDestroy(Item a, Item b, CopyOnWriteArrayList c) {
        if (!a.tipo.equalsIgnoreCase("pared")) {
            a.setDone(true);
            c.remove(a);
            //mg.interactMemory("deleteitem", c.indexOf(a));
        }
        if (!b.tipo.equalsIgnoreCase("pared")) {
            b.setDone(true);
            //mg.interactMemory("deleteitem", c.indexOf(a));
            c.remove(b);
        }
        String type;
        for (int x = 0; x < c.size(); x++) {
            type = Actions.getElementFromArray(x,c);
            if (type.equalsIgnoreCase("tunel")) {
                Tunel irow;
                if (x > c.size() - 1) {
                    irow = (Tunel) c.get(c.size() - 1);
                } else {
                    irow = (Tunel) c.get(x);
                }
                irow.itemIsdeadonTunel();
            }
            if (type.equalsIgnoreCase("item")) {
                x = c.size();
            }
        }


    }

    public static void ColisionDestroy(Item a, CopyOnWriteArrayList c) {
        if (!a.tipo.equalsIgnoreCase("pared")) {
            a.setDone(true);
            c.remove(a);
            //mg.interactMemory("deleteitem", c.indexOf(a));
        }
    }

    public static void MovementItem(Item a) {
        if (!a.tipo.equalsIgnoreCase("pared")) {
            if (a.orientationx) {
                if (a.hormov) {
                    a.movRight();
                } else {
                    a.movLeft();
                }
            } else if (a.vermov) {
                a.movBottom();
            } else {
                a.movTop();
            }
        }
    }

    public static void ChangeMov(Item a) {
        if (a.hormov) {
            a.hormov = false;
        } else {
            a.hormov = true;
        }

        if (a.vermov) {
            a.vermov = false;
        } else {
            a.vermov = true;
        }
    }

    public static void PlayerOverpass(Wall a, Player p) {
        //Implementar que el jugador no pase por la pared
        if (p.isOrientationx()) {
            if (p.isHormov()) {
                p.movLeft();
            } else {
                p.movRight();
            }
        } else if (p.isVermov()) {
            p.movTop();
        } else {
            p.movBottom();
        }
    }

    public static void ColisionDestroy(Player a, Item b, CopyOnWriteArrayList c) {
        if (!a.tipo.equalsIgnoreCase("pared")) {
            a.dead = true;
            c.remove(a);
        }
        if (!b.tipo.equalsIgnoreCase("pared")) {
            b.setDone(true);
            c.remove(b);
        }
    }
}
