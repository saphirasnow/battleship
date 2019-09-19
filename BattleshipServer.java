package network;
import java.io.*;
import java.net.*;
public class BattleshipServer { //server for communication between two players of Battleship
    static boolean messageAvailable1 = false;
    static boolean messageAvailable2 = false;
    static int turn = 1;
    static boolean done = false;
    static String message1 = "testMessage";
    static String message2 = "testMessage";
    static boolean ready2 = false;
    static int count = 0;
    public static void main(String[] args) {
        try {
            ServerSocket serverSock1 = new ServerSocket(7777);
            ServerSocket serverSock2 = new ServerSocket(7778);
            ConnectionHandler1 handler1 = new ConnectionHandler1(serverSock1);
            ConnectionHandler2 handler2 = new ConnectionHandler2(serverSock2);
            handler1.start();
            handler2.start();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    static class ConnectionHandler1 extends Thread {
        private Socket socket;
        private ServerSocket serverSock;
        public ConnectionHandler1(ServerSocket sSock) {
            serverSock = sSock;
        }
        @Override
        public void run() {
            ObjectInputStream ois;
            ObjectOutputStream oos;
            try {
                Socket connectionSock = serverSock.accept();
                socket = connectionSock;
                ois = new ObjectInputStream(socket.getInputStream());             
                oos = new ObjectOutputStream(socket.getOutputStream());
                while(!done) {
                    Thread.sleep(1);
                    if(turn == 1) {
                        if(!messageAvailable2) {
                            message1 = (String) ois.readObject();
                            messageAvailable2 = true;
                        }
                    }
                    else if(turn == 2) {
                        if(message2.equals("Game Over") || message1.equals("Game Over")) {
                            done = true;
                        }
                        if(!messageAvailable1) {
                            Thread.sleep(10);
                        }
                        else {
                            oos.writeObject(message2);
                            messageAvailable1 = false;
                            if(message2.charAt(message2.length() - 1) != '.')
                                turn = 1;
                        }
                    }
                }
                ois.close();
                oos.close();
                this.interrupt();
                return;
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
    }
    static class ConnectionHandler2 extends Thread {
        private Socket socket;
        private ServerSocket serverSock;
        public ConnectionHandler2(ServerSocket sSock) {
            serverSock = sSock;
        }
        @Override
        public void run() {
            ObjectInputStream ois;
            ObjectOutputStream oos;
            try {
                Socket connectionSock = serverSock.accept();
                socket = connectionSock;
                ois = new ObjectInputStream(socket.getInputStream());             
                oos = new ObjectOutputStream(socket.getOutputStream());
                String temp = (String) ois.readObject();
                while(!done) {
                    Thread.sleep(1);
                    if(turn == 2) {
                        if(!messageAvailable1) {
                            message2 = (String) ois.readObject();
                            messageAvailable1 = true;
                        }
                    }
                    else if(turn == 1) {
                        if(message2.equals("Game Over") || message1.equals("Game Over")) {
                            done = true;
                        }
                        if(!messageAvailable2) {
                            Thread.sleep(10);
                        }
                        else {
                            oos.writeObject(message1);
                            messageAvailable2 = false;
                            if(message1.charAt(message1.length() - 1) != '.')
                                turn = 2;
                        }
                    }
                }
                ois.close();
                oos.close();
                this.interrupt();
                return;
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
    }
}
