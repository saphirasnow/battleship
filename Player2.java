package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.*;
import java.net.*;

public class Player2 { //program for second player in Battleship
    static boolean done = false;
    static PlayerUI pUI = new PlayerUI();
    static boolean attackPressed = false;
    static boolean readySent = false, readyReceived = false;
    static boolean turn = false;
    static String attacked = "  ";
    public static void main(String[] args) {
        AttackListener attackListen = new AttackListener();
        pUI.sendButton.addActionListener(attackListen);
        pUI.setVisible(true);
        try {
            int port = 7778;
            InetAddress host = InetAddress.getLocalHost();   
            Socket connectionSock = new Socket(host.getHostName(), port);
            ObjectOutputStream oos = new ObjectOutputStream(connectionSock.getOutputStream());             
            ObjectInputStream ois = new ObjectInputStream(connectionSock.getInputStream());
            while(pUI.shipType < 11) {
                pUI.instruction.setText("Set your ship positions.");
            }
            while(!done) {
                if(!readySent) {
                    pUI.receive.append("Sending: P2 Ready" + "\n");
                    pUI.scrollR.getVerticalScrollBar().setValue(pUI.scrollR.getVerticalScrollBar().getMaximum());
                    oos.writeObject("P2 Ready");
                    readySent = true;
                }
                else if(turn) {
                    if(attackPressed) {
                        String message = pUI.sendTextBox.getText();
                        pUI.sendTextBox.setText("");
                        if(valid(message)) {
                            pUI.oboard.spaces[x(message)][Integer.parseInt(message.substring(1)) - 1].setBackground(Color.WHITE);
                            pUI.receive.append("Sending: " + message + "\n");
                            pUI.scrollR.getVerticalScrollBar().setValue(pUI.scrollR.getVerticalScrollBar().getMaximum());
                            oos.writeObject(message);
                            attacked = message;
                            turn = false;
                        }
                        else {
                            pUI.instruction.setText("Invalid Attack");
                            Thread.sleep(500);
                        }
                        attackPressed = false;
                    }
                    else {
                        pUI.instruction.setText("Enter your attack.");
                    }
                }
                else {
                    pUI.instruction.setText("Waiting for Opponent");
                    String message = (String) ois.readObject();
                    pUI.receive.append("Received: " + message + "\n");
                    pUI.scrollR.getVerticalScrollBar().setValue(pUI.scrollR.getVerticalScrollBar().getMaximum());
                    if(message.equals("Game Over")) {
                        pUI.instruction.setText("You Win");
                        done = true;
                    }
                    if(message.equals("Hit.")) {
                        pUI.oboard.spaces[x(attacked)][Integer.parseInt(attacked.substring(1)) - 1].setBackground(Color.RED);
                    }
                    if(message.length() < 4) {
                        boolean shipSunk = pUI.uboard.spaces[x(message)][Integer.parseInt(message.substring(1)) - 1].setHit();
                        if(pUI.uboard.spaces[x(message)][Integer.parseInt(message.substring(1)) - 1].getShip() != null) {
                            oos.writeObject("Hit.");
                            pUI.uboard.spaces[x(message)][Integer.parseInt(message.substring(1)) - 1].setBackground(Color.RED);
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
                                pUI.instruction.setText("You Lose");
                                pUI.receive.append("Game Over");
                                pUI.scrollR.getVerticalScrollBar().setValue(pUI.scrollR.getVerticalScrollBar().getMaximum());
                            }
                        }
                        turn = true;
                        attackPressed = false;
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
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    public static boolean valid(String message) {
        boolean valid = false;
        if(message.length() < 2)
            return false;
        for(int c = 0; c < 10; c++) {
            if(message.substring(1).equals(Integer.toString(c + 1)))
                valid = true;
        }
        if(valid) {
            if(message.charAt(0) == 'A')
                valid = true;
            else if(message.charAt(0) == 'B')
                valid = true;
            else if(message.charAt(0) == 'C')
                valid = true;
            else if(message.charAt(0) == 'D')
                valid = true;
            else if(message.charAt(0) == 'E')
                valid = true;
            else if(message.charAt(0) == 'F')
                valid = true;
            else if(message.charAt(0) == 'G')
                valid = true;
            else if(message.charAt(0) == 'H')
                valid = true;
            else if(message.charAt(0) == 'I')
                valid = true;
            else if(message.charAt(0) == 'J')
                valid = true;
            else
                valid = false;
        }
        return valid;
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
    public static class AttackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            attackPressed = true;
        }
    }
}
