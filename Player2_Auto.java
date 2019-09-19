package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.*;
import java.net.*;

public class Player2_Auto { //program for computer as second player in Battleship
    static boolean done = false;
    static PlayerUI pUI = new PlayerUI();
    static boolean readySent = false, readyReceived = false;
    static boolean turn = false;
    static String letter = "ABCDEFGHIJ";
    static boolean row_col = true;
    static PlayerUI.UserBoard.Space button;
    public static void main(String[] args) {
        try {
            int port = 7778;
            InetAddress host = InetAddress.getLocalHost();   
            Socket connectionSock = new Socket(host.getHostName(), port);
            ObjectOutputStream oos = new ObjectOutputStream(connectionSock.getOutputStream());             
            ObjectInputStream ois = new ObjectInputStream(connectionSock.getInputStream());
            while(pUI.shipType < 11) {
                button = pUI.uboard.spaces[(int) Math.floor(Math.random() * 10)][(int) Math.floor(Math.random() * 10)];
                int randNum = (int) Math.round(Math.random());
                if(randNum == 0)
                    pUI.row_col = true;
                else 
                    pUI.row_col = false;
                if(row_col && pUI.available(button)) {
                    switch(pUI.shipType) {
                        case 0: 
                            if(button.getYPos() <= 5) {
                                pUI.ships[pUI.shipType].xy[0] = button.getXPos();
                                pUI.ships[pUI.shipType].xy[1] = button.getYPos();
                                for(int c = 0; c < 5; c++) {
                                    pUI.uboard.spaces[button.getXPos()][button.getYPos() + c].setShip(pUI.ships[pUI.shipType]);
                                }
                            }
                            else {
                                pUI.ships[pUI.shipType].xy[0] = button.getXPos();
                                pUI.ships[pUI.shipType].xy[1] = 5;
                                for(int c = 5; c < 10; c++) {
                                    pUI.uboard.spaces[button.getXPos()][c].setShip(pUI.ships[pUI.shipType]);
                                }
                            }
                            break;
                        case 1: pUI.sbc2switch12(button); break;
                        case 2: pUI.sbc2switch12(button); break;
                        case 3: pUI.sbc2switch3456(button); break;
                        case 4: pUI.sbc2switch3456(button); break;
                        case 5: pUI.sbc2switch3456(button); break;
                        case 6: pUI.sbc2switch3456(button); break;
                        case 7: pUI.sbc2switch78(button); break;
                        case 8: pUI.sbc2switch78(button); break;
                        case 9: pUI.sbc2switch78(button); break;
                        case 10: pUI.sbc2switch78(button); break;
                    }
                    pUI.shipType++;
                }
                else if(pUI.available(button)){
                    switch(pUI.shipType) {
                        case 0:
                            if(button.getXPos() <= 5) {
                                pUI.ships[pUI.shipType].xy[0] = button.getXPos();
                                pUI.ships[pUI.shipType].xy[1] = button.getYPos();
                                for(int c = 0; c < 5; c++) {
                                    pUI.uboard.spaces[button.getXPos() + c][button.getYPos()].setShip(pUI.ships[pUI.shipType]);
                                }
                            }
                            else {
                                pUI.ships[pUI.shipType].xy[0] = 3;
                                pUI.ships[pUI.shipType].xy[1] = button.getYPos();
                                for(int c = 5; c < 10; c++) {
                                    pUI.uboard.spaces[c][button.getYPos()].setShip(pUI.ships[pUI.shipType]);
                                }
                            }
                            break;
                        case 1: pUI.sbc2switch12(button); break;
                        case 2: pUI.sbc2switch12(button); break;
                        case 3: pUI.sbc2switch3456(button); break;
                        case 4: pUI.sbc2switch3456(button); break;
                        case 5: pUI.sbc2switch3456(button); break;
                        case 6: pUI.sbc2switch3456(button); break;
                        case 7: pUI.sbc2switch78(button); break;
                        case 8: pUI.sbc2switch78(button); break;
                        case 9: pUI.sbc2switch78(button); break;
                        case 10: pUI.sbc2switch78(button); break;
                    }
                    pUI.shipType++;
                }
            }
            while(!done) {
                if(!readySent) {
                    oos.writeObject("P2 Ready");
                    readySent = true;
                }
                else if(turn) {
                    int randLetter = (int) Math.floor((Math.random() * 10));
                    String message = "" + letter.charAt(randLetter) + (int) Math.floor((Math.random() * 10) + 1);
                    oos.writeObject(message);
                    turn = false;
                }
                else {
                    String message = (String) ois.readObject();
                    if(message.equals("Game Over")) {
                        done = true;
                    }
                    if(message.length() < 4) {
                        boolean shipSunk = pUI.uboard.spaces[x(message)][Integer.parseInt(message.substring(1)) - 1].setHit();
                        if(pUI.uboard.spaces[x(message)][Integer.parseInt(message.substring(1)) - 1].getShip() != null) {
                            oos.writeObject("Hit.");
                        }
                        else {
                            oos.writeObject("Miss.");
                        }
                        if(shipSunk) {
                            done = true;
                            for(int c = 0; c < 11; c++) {
                                if(!pUI.ships[c].sunken) {
                                    done = false;
                                }
                            }
                            if(!done)
                                oos.writeObject(pUI.uboard.spaces[x(message)][Integer.valueOf(message.substring(1)) - 1].getShip().name + " sunk.");
                            else {
                                oos.writeObject("Game Over");
                            }
                        }
                        turn = true;
                    }
                }
            }
            oos.close();
            ois.close();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public static int x(String message) {
        int x;
        if(message.charAt(0) == 'A')
            x = 0;
        else if(message.charAt(0) == 'B')
            x = 1;
        else if(message.charAt(0) == 'C')
            x = 2;
        else if(message.charAt(0) == 'D')
            x = 3;
        else if(message.charAt(0) == 'E')
            x = 4;
        else if(message.charAt(0) == 'F')
            x = 5;
        else if(message.charAt(0) == 'G')
            x = 6;
        else if(message.charAt(0) == 'H')
            x = 7;
        else if(message.charAt(0) == 'I')
            x = 8;
        else
            x = 9;
        return x;
    }
}
