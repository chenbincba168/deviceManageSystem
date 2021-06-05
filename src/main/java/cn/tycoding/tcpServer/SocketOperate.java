package cn.tycoding.tcpServer;

import java.io.*;
import java.net.Socket;




class message implements java.io.Serializable {
    public String Magic;
    public byte Version;
    public byte Msgtype;
    public char Datatype;
    public byte Resv;
    public int Timestamp;
    public int Seq;
    public int datasize;
    public String msgBody;
}





/**
 * 多线程处理socket接收的数据
 * @author huajian
 *
 */

public class SocketOperate extends Thread {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private message receiveMsg;

    public SocketOperate(Socket socket) {
        this.socket = socket;
    }

    public void run()
    {
        try {
            while (true) {
                /*接收客户端的消息并打印*/
                System.out.println(socket);
                inputStream = socket.getInputStream();
                byte[] recBuffer = new byte[1024];
                inputStream.read(recBuffer);
                System.out.println("bytes = " + new String(recBuffer));

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                Object object = in.readObject();
                receiveMsg = (message) object;
                System.out.println("receiveMsg.Magic = " + receiveMsg.Magic);


                /*向客户端发送消息*/
                outputStream = socket.getOutputStream();
                outputStream.write("respone data".getBytes());
            }
        } catch (Exception e) {
            System.out.println("客户端主动断开连接了");
            e.printStackTrace();
        }
        //操作结束，关闭socket
        try{
            socket.close();
        }catch(IOException e){
            System.out.println("关闭连接出现异常");
            e.printStackTrace();
        }
    }
}
