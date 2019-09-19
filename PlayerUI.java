package network;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PlayerUI extends JFrame { //the user interface for playing Battleship
    UserBoard uboard = new UserBoard(10);
    OpponentBoard oboard = new OpponentBoard(10);
    JPanel send_receive = new JPanel();
    JPanel send = new JPanel();
    JLabel instruction = new JLabel(" Click the board to set the ship positions");
    JPanel sendHolder = new JPanel();
    JTextField sendTextBox = new JTextField(3);
    JButton sendButton = new JButton("Attack");
    JTextArea receive = new JTextArea(15, 5);
    JScrollPane scrollR = new JScrollPane(receive);
    JPanel uboardHold = new JPanel();
    Letters uLet = new Letters();
    Numbers uNum = new Numbers();
    JPanel oboardHold = new JPanel();
    Letters oLet = new Letters();
    Numbers oNum = new Numbers();
    
    Ship[] ships = new Ship[11];
    boolean row_col = true;
    int shipType = 0;
    
    public PlayerUI() {
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        uboardHold.setLayout(new BorderLayout());
        uboardHold.add(uboard, BorderLayout.CENTER);
        uboardHold.add(uLet, BorderLayout.WEST);
        uboardHold.add(uNum, BorderLayout.NORTH);
        oboardHold.setLayout(new BorderLayout());
        oboardHold.add(oboard, BorderLayout.CENTER);
        oboardHold.add(oLet, BorderLayout.WEST);
        oboardHold.add(oNum, BorderLayout.NORTH);
        
        add(uboardHold, BorderLayout.WEST);
        add(oboardHold, BorderLayout.EAST);
        
        send_receive.setLayout(new GridLayout(1, 2));
        send.setLayout(new BorderLayout());
        sendHolder.setLayout(new FlowLayout());
        sendHolder.add(sendTextBox);
        sendHolder.add(sendButton);
        send.add(instruction, BorderLayout.NORTH);
        send.add(sendHolder, BorderLayout.CENTER);
        send_receive.add(send);
        receive.setEditable(false);
        scrollR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollR.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        send_receive.add(scrollR);
        add(send_receive, BorderLayout.SOUTH);
        
        for(int c = 0; c < 11; c++) {
            ships[c] = new Ship(c);
        }
    }
    public class Letters extends JPanel {
        JButton a = new JButton("A");
        JButton b = new JButton("B");
        JButton c = new JButton("C");
        JButton d = new JButton("D");
        JButton e = new JButton("E");
        JButton f = new JButton("F");
        JButton g = new JButton("G");
        JButton h = new JButton("H");
        JButton i = new JButton("I");
        JButton j = new JButton("J");
        Letters() {
            setLayout(new GridLayout(10, 1));
            a.setBackground(Color.CYAN);
            add(a);
            b.setBackground(Color.CYAN);
            add(b);
            c.setBackground(Color.CYAN);
            add(c);
            d.setBackground(Color.CYAN);
            add(d);
            e.setBackground(Color.CYAN);
            add(e);
            f.setBackground(Color.CYAN);
            add(f);
            g.setBackground(Color.CYAN);
            add(g);
            h.setBackground(Color.CYAN);
            add(h);
            i.setBackground(Color.CYAN);
            add(i);
            j.setBackground(Color.CYAN);
            add(j);
        }
    }
    public class Numbers extends JPanel {
        JButton[] nums = new JButton[10];
        JButton empty = new JButton();
        Numbers() {
            setLayout(new GridLayout(1, 10));
            empty.setBackground(Color.CYAN);
            add(empty);
            for(int c = 1; c < 11; c++) {
                nums[c - 1] = new JButton(Integer.toString(c));
                nums[c - 1].setBackground(Color.CYAN);
                add(nums[c - 1]);
            }
        }
    }
    public class UserBoard extends JPanel {
        int size;
        Space[][] spaces;
        SetByClick[][] listeners;
        public UserBoard(int s) {
            size = s;
            spaces = new Space[size][size];
            listeners = new SetByClick[size][size];
            setLayout(new GridLayout(size, size));
            for(int c = 0; c < size; c++) {
                for(int c2 = 0; c2 < size; c2++) {
                    spaces[c][c2] = new Space(c, c2);
                    spaces[c][c2].setBackground(Color.BLUE);
                    listeners[c][c2] = new SetByClick();
                    spaces[c][c2].addMouseListener(listeners[c][c2]);
                    add(spaces[c][c2]);
                }
            }
        }
        public class Space extends JButton{
            private Ship ship = null;
            private boolean hit = false;
            private int x, y;
            Space(int xs, int ys) {
                x = xs;
                y = ys;
            }
            public void setShip(Ship s) {
                ship = s;
            }
            public Ship getShip() {
                return ship;
            }
            public int getXPos() {
                return x;
            }
            public int getYPos() {
                return y;
            }
            public boolean setHit() {
                hit = true;
                setBackground(Color.WHITE);
                if(ship != null) {
                    return ship.sunk(x, y);
                }
                else
                    return false;
            }
        }
    }
    public class OpponentBoard extends JPanel{
        int size;
        Space[][] spaces;
        public OpponentBoard(int s) {
            size = s;
            spaces = new Space[size][size];
            setLayout(new GridLayout(size, size));
            for(int c = 0; c < size; c++) {
                for(int c2 = 0; c2 < size; c2++) {
                    spaces[c][c2] = new Space(c, c2);
                    spaces[c][c2].setBackground(Color.BLUE);
                    add(spaces[c][c2]);
                }
            }
        }
        public class Space extends JButton{
            int x, y;
            Space(int xs, int ys) {
                x = xs;
                y = ys;
            }
            public int getXPos() {
                return x;
            }
            public int getYPos() {
                return y;
            }
        }
    }
    public static class Ship {
        String name;
        int[] xy;
        boolean sunken = false;
        boolean[] alive;
        Ship(int n) {
            switch(n) {
                case 0: name = "aircraft carrier"; alive = new boolean[5]; break;
                case 1: name = "battleship"; alive = new boolean[4]; break;
                case 2: name = "battleship"; alive = new boolean[4]; break;
                case 3: name = "destroyer"; alive = new boolean[3]; break;
                case 4: name = "destroyer"; alive = new boolean[3]; break;
                case 5: name = "submarine"; alive = new boolean[3]; break;
                case 6: name = "submarine"; alive = new boolean[3]; break;
                case 7: name = "patrol boat"; alive = new boolean[2]; break;
                case 8: name = "patrol boat"; alive = new boolean[2]; break;
                case 9: name = "patrol boat"; alive = new boolean[2]; break;
                case 10: name = "patrol boat"; alive = new boolean[2]; break;
            }
            xy = new int[2];
            for(int c = 0; c < alive.length; c++) {
                alive[c] = true;
            }
        }
        public boolean sunk(int x, int y) {
            if(x - xy[0] == 0 && y - xy[1] == 0)
                alive[0] = false;
            else if(x - xy[0] == 0)
                alive[y - xy[1]] = false;
            else if(y - xy[1] == 0)
                alive[x - xy[0]] = false;
            sunken = true;
            for(int c = 0; c < alive.length; c++) {
                if(alive[c])
                    sunken = false;
            }
            return sunken;
        }
    }
    public class SetByClick implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            UserBoard.Space button = (UserBoard.Space) e.getSource();
            if(shipType < 11) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    if(row_col)
                        row_col = false;
                    else
                        row_col = true;
                }
                else {
                    if(row_col && available(button)) {
                        switch(shipType) {
                            case 0: 
                                if(button.getYPos() <= 5) {
                                    ships[shipType].xy[0] = button.getXPos();
                                    ships[shipType].xy[1] = button.getYPos();
                                    for(int c = 0; c < 5; c++) {
                                        uboard.spaces[button.getXPos()][button.getYPos() + c].setShip(ships[shipType]);
                                    }
                                }
                                else {
                                    ships[shipType].xy[0] = button.getXPos();
                                    ships[shipType].xy[1] = 5;
                                    for(int c = 5; c < 10; c++) {
                                        uboard.spaces[button.getXPos()][c].setShip(ships[shipType]);
                                    }
                                }
                                break;
                            case 1: sbc2switch12(button); break;
                            case 2: sbc2switch12(button); break;
                            case 3: sbc2switch3456(button); break;
                            case 4: sbc2switch3456(button); break;
                            case 5: sbc2switch3456(button); break;
                            case 6: sbc2switch3456(button); break;
                            case 7: sbc2switch78(button); break;
                            case 8: sbc2switch78(button); break;
                            case 9: sbc2switch78(button); break;
                            case 10: sbc2switch78(button); break;
                        }
                        shipType++;
                    }
                    else if(available(button)){
                        switch(shipType) {
                            case 0:
                                if(button.getXPos() <= 5) {
                                    ships[shipType].xy[0] = button.getXPos();
                                    ships[shipType].xy[1] = button.getYPos();
                                    for(int c = 0; c < 5; c++) {
                                        uboard.spaces[button.getXPos() + c][button.getYPos()].setShip(ships[shipType]);
                                    }
                                }
                                else {
                                    ships[shipType].xy[0] = 3;
                                    ships[shipType].xy[1] = button.getYPos();
                                    for(int c = 5; c < 10; c++) {
                                        uboard.spaces[c][button.getYPos()].setShip(ships[shipType]);
                                    }
                                }
                                break;
                            case 1: sbc2switch12(button); break;
                            case 2: sbc2switch12(button); break;
                            case 3: sbc2switch3456(button); break;
                            case 4: sbc2switch3456(button); break;
                            case 5: sbc2switch3456(button); break;
                            case 6: sbc2switch3456(button); break;
                            case 7: sbc2switch78(button); break;
                            case 8: sbc2switch78(button); break;
                            case 9: sbc2switch78(button); break;
                            case 10: sbc2switch78(button); break;
                        }
                        shipType++;
                    }
                }
            }
        }        
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {
            UserBoard.Space button = (UserBoard.Space) e.getSource();
            if(shipType < 11) {
                if(row_col) {
                    switch(shipType) {
                        case 0: 
                            if(button.getYPos() <= 5) {
                                for(int c = 0; c < 5; c++) {
                                    uboard.spaces[button.getXPos()][button.getYPos() + c].setBackground(Color.GREEN);
                                }
                            }
                            else {
                                for(int c = 5; c < 10; c++) {
                                    uboard.spaces[button.getXPos()][c].setBackground(Color.GREEN);
                                }
                            }
                            break;
                        case 1: sbcswitch12(button); break;
                        case 2: sbcswitch12(button); break;
                        case 3: sbcswitch3456(button); break;
                        case 4: sbcswitch3456(button); break;
                        case 5: sbcswitch3456(button); break;
                        case 6: sbcswitch3456(button); break;
                        case 7: sbcswitch78(button); break;
                        case 8: sbcswitch78(button); break;
                        case 9: sbcswitch78(button); break;
                        case 10: sbcswitch78(button); break;
                    }
                }
                else {
                    switch(shipType) {
                        case 0: 
                            if(button.getXPos() <= 5) {
                                for(int c = 0; c < 5; c++) {
                                    uboard.spaces[button.getXPos() + c][button.getYPos()].setBackground(Color.GREEN);
                                }
                            }
                            else {
                                for(int c = 5; c < 10; c++) {
                                    uboard.spaces[c][button.getYPos()].setBackground(Color.GREEN);
                                }
                            }
                            break;
                        case 1: sbcswitch12(button); break;
                        case 2: sbcswitch12(button); break;
                        case 3: sbcswitch3456(button); break;
                        case 4: sbcswitch3456(button); break;
                        case 5: sbcswitch3456(button); break;
                        case 6: sbcswitch3456(button); break;
                        case 7: sbcswitch78(button); break;
                        case 8: sbcswitch78(button); break;
                        case 9: sbcswitch78(button); break;
                        case 10: sbcswitch78(button); break;
                    }
                }
            }
        }
        @Override
        public void mouseExited(MouseEvent e) {
            if(shipType < 11) {
                for(int c = 0; c < 10; c++) {
                    for(int c2 = 0; c2 < 10; c2++) {
                        if(uboard.spaces[c][c2].ship == null) {
                            uboard.spaces[c][c2].setBackground(Color.BLUE);
                        }
                    }
                }
            }
        }
    }
    public void sbcswitch12(UserBoard.Space button) {
        if(row_col) {
            if(button.getYPos() <= 6) {
                for(int c = 0; c < 4; c++) {
                    uboard.spaces[button.getXPos()][button.getYPos() + c].setBackground(Color.GREEN);
                }
            }
            else {
                for(int c = 6; c < 10; c++) {
                    uboard.spaces[button.getXPos()][c].setBackground(Color.GREEN);
                }
            }
        }
        else {
            if(button.getXPos() <= 6) {
                for(int c = 0; c < 4; c++) {
                    uboard.spaces[button.getXPos() + c][button.getYPos()].setBackground(Color.GREEN);
                }
            }
            else {
                for(int c = 6; c < 10; c++) {
                    uboard.spaces[c][button.getYPos()].setBackground(Color.GREEN);
                }
            }
        }
    }
    public void sbcswitch3456(UserBoard.Space button) {
        if(row_col) {
            if(button.getYPos() <= 7) {
                for(int c = 0; c < 3; c++) {
                    uboard.spaces[button.getXPos()][button.getYPos() + c].setBackground(Color.GREEN);
                }
            }
            else {
                for(int c = 7; c < 10; c++) {
                    uboard.spaces[button.getXPos()][c].setBackground(Color.GREEN);
                }
            }
        }
        else {
            if(button.getXPos() <= 7) {
                for(int c = 0; c < 3; c++) {
                    uboard.spaces[button.getXPos() + c][button.getYPos()].setBackground(Color.GREEN);
                }
            }
            else {
                for(int c = 7; c < 10; c++) {
                    uboard.spaces[c][button.getYPos()].setBackground(Color.GREEN);
                }
            }
        }
    }
    public void sbcswitch78(UserBoard.Space button) {
        if(row_col) {
            if(button.getYPos() <= 8) {
                for(int c = 0; c < 2; c++) {
                    uboard.spaces[button.getXPos()][button.getYPos() + c].setBackground(Color.GREEN);
                }
            }
            else {
                for(int c = 8; c < 10; c++) {
                    uboard.spaces[button.getXPos()][c].setBackground(Color.GREEN);
                }
            }
        }
        else {
            if(button.getXPos() <= 8) {
                for(int c = 0; c < 2; c++) {
                    uboard.spaces[button.getXPos() + c][button.getYPos()].setBackground(Color.GREEN);
                }
            }
            else {
                for(int c = 8; c < 10; c++) {
                    uboard.spaces[c][button.getYPos()].setBackground(Color.GREEN);
                }
            }
        }
    }
    public void sbc2switch12(UserBoard.Space button) {
        if(row_col) {
            if(button.getYPos() <= 6) {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 0; c < 4; c++) {
                    uboard.spaces[button.getXPos()][button.getYPos() + c].setShip(ships[shipType]);
                }
            }
            else {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = 6;
                for(int c = 6; c < 10; c++) {
                    uboard.spaces[button.getXPos()][c].setShip(ships[shipType]);
                }
            }
        }
        else {
            if(button.getXPos() <= 6) {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 0; c < 4; c++) {
                    uboard.spaces[button.getXPos() + c][button.getYPos()].setShip(ships[shipType]);
                }
            }
            else {
                ships[shipType].xy[0] = 6;
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 6; c < 10; c++) {
                    uboard.spaces[c][button.getYPos()].setShip(ships[shipType]);
                }
            }
        }
    }
    public void sbc2switch3456(UserBoard.Space button) {
        if(row_col) {
            if(button.getYPos() <= 7) {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 0; c < 3; c++) {
                    uboard.spaces[button.getXPos()][button.getYPos() + c].setShip(ships[shipType]);
                }
            }
            else {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = 7;
                for(int c = 7; c < 10; c++) {
                    uboard.spaces[button.getXPos()][c].setShip(ships[shipType]);
                }
            }
        }
        else {
            if(button.getXPos() <= 7) {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 0; c < 3; c++) {
                    uboard.spaces[button.getXPos() + c][button.getYPos()].setShip(ships[shipType]);
                }
            }
            else {
                ships[shipType].xy[0] = 7;
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 7; c < 10; c++) {
                    uboard.spaces[c][button.getYPos()].setShip(ships[shipType]);
                }
            }
        }
    }
    public void sbc2switch78(UserBoard.Space button) {
        if(row_col) {
            if(button.getYPos() <= 8) {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 0; c < 2; c++) {
                    uboard.spaces[button.getXPos()][button.getYPos() + c].setShip(ships[shipType]);
                }
            }
            else {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = 8;
                for(int c = 8; c < 10; c++) {
                    uboard.spaces[button.getXPos()][c].setShip(ships[shipType]);
                }
            }
        }
        else {
            if(button.getXPos() <= 8) {
                ships[shipType].xy[0] = button.getXPos();
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 0; c < 2; c++) {
                    uboard.spaces[button.getXPos() + c][button.getYPos()].setShip(ships[shipType]);
                }
            }
            else {
                ships[shipType].xy[0] = 8;
                ships[shipType].xy[1] = button.getYPos();
                for(int c = 8; c < 10; c++) {
                    uboard.spaces[c][button.getYPos()].setShip(ships[shipType]);
                }
            }
        }
    }
    public boolean available(UserBoard.Space button) {
        int size;
        boolean av = true;
        if(shipType == 0)
            size = 5;
        else if(shipType == 1 || shipType == 2)
            size = 4;
        else if(shipType < 7)
            size = 3;
        else
            size = 2;
        if((button.getYPos() <= 10 - size && row_col) || (button.getXPos() <= 10 - size && row_col == false)) {
            for(int c = 0; c < size; c++) {
                if(row_col) {
                    if(uboard.spaces[button.getXPos()][button.getYPos() + c].ship != null) {
                        av = false;
                    }
                }
                else {
                    if(uboard.spaces[button.getXPos() + c][button.getYPos()].ship != null) {
                        av = false;
                    }
                }
            }
        }
        else {
            for(int c = 10 - size; c < 10; c++) {
                if(row_col) {
                    if(uboard.spaces[button.getXPos()][c].ship != null) {
                        av = false;
                    }
                }
                else {
                    if(uboard.spaces[c][button.getYPos()].ship != null) {
                        av = false;
                    }
                }
            }
        }
        return av;
    }
}
