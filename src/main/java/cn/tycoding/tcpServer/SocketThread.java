package cn.tycoding.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * socket 线程类
 * @author huajian
 */
public class SocketThread extends Thread{
    private ServerSocket serverSocket = null;

    public SocketThread(ServerSocket serverScoket){
        try {
            if(null == serverSocket){
                this.serverSocket = new ServerSocket(5005);
                System.out.println("socket start");
            }
        } catch (Exception e) {
            System.out.println("SocketThread创建socket服务出错");
            e.printStackTrace();
        }
    }

    public void run(){
        System.out.println("start receive data");
        while(!this.isInterrupted()){
            try {
                Socket socket = serverSocket.accept();
                if(null != socket && !socket.isClosed()){
                    //处理接受的数据
                    new SocketOperate(socket).start();
                }
                socket.setSoTimeout(30000);

            }catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

    public void closeSocketServer(){
        try {
            if(null!=serverSocket && !serverSocket.isClosed())
            {
                serverSocket.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
