package cn.tycoding.tcpServer;

import java.io.*;
import java.net.Socket;
import net.sf.json.*;
import java.util.Arrays;

enum PacketType {
    HEARTBEAT(1),  //心跳消息包
    NOTIFICATION(2),  //通知消息包
    REQUEST(3),      //请求消息包
    RESPONSE(4);      //相应消息包
    private final int value;

    PacketType(int value) {
        this.value = value;
    }
}

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
    JSONObject responseJson;

    public SocketOperate(Socket socket) {
        this.socket = socket;
    }

    /**
     * 将整数转换为byte数组并指定长度
     */
    private static byte[] intToBytes(int a, int length) {
        byte[] bs = new byte[length];
        for (int i = bs.length - 1; i >= 0; i--) {
            bs[i] = (byte) (a % 0xFF);
            a = a / 0xFF;
        }
        return bs;
    }

    /**
     * 将byte数组转换为整数
     */
    private static int bytesToInt(byte[] bs) {
        int a = 0;
        for (int i = bs.length - 1; i >= 0; i--) {
            a += bs[i] * Math.pow(0xFF, bs.length - i - 1);
        }
        return a;
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
                System.out.println("Magic = " + new String(recBuffer, 0, 4));
                System.out.println("Version = " + recBuffer[4]);
                System.out.println("Msgtype = " + recBuffer[5]);
                System.out.println("Datatype = " + recBuffer[6]);
                System.out.println("Resv = " + recBuffer[7]);
                System.out.println("Timestamp = " + (recBuffer[8]<<24 | recBuffer[9]<<16 | recBuffer[10]<<8 | recBuffer[11]));
                System.out.println("Seq = " + (recBuffer[12]<<24 | recBuffer[13]<<16 | recBuffer[14]<<8 | recBuffer[15]));
                System.out.println("datasize = " + (recBuffer[19]<<24 | recBuffer[18]<<16 | recBuffer[17]<<8 | recBuffer[16]));

                String receivePayload = new String(recBuffer, 20, recBuffer.length - 20);
                System.out.println("receivePayload = " + receivePayload);
                JSONObject jsonObj = JSONObject.fromObject(receivePayload);
                JSONObject responseJson = parseMessage(jsonObj);

                /*向客户端发送消息*/
                int dataLen = responseJson.toString().length();
                System.out.println("dataLen = " + dataLen);

                recBuffer[19] = (byte) ((dataLen & 0xFFFFFFFF)>>24);
                recBuffer[18] = (byte) ((dataLen & 0x00FFFFFF)>>16);
                recBuffer[17] = (byte) ((dataLen & 0x0000FFFF)>>8);
                recBuffer[16] = (byte) dataLen;

                recBuffer[5] = 4;//响应类型
                Arrays.fill(recBuffer, 20, recBuffer.length - 1, (byte)0);

                System.out.println("responseJson = " + responseJson.toString());
                byte[] byt = responseJson.toString().getBytes();

                System.arraycopy(byt, 0, recBuffer, 20, byt.length);
                String reponsePayload = new String(recBuffer, 20, recBuffer.length - 20);
                System.out.println("reponsePayload = " + reponsePayload);

                outputStream = socket.getOutputStream();
                outputStream.write(recBuffer);
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

    public JSONObject parseMessage(JSONObject jsonObj) {
        String message = jsonObj.getString("message");
        System.out.println("message = " + message);
        switch (message) {
            case "register":
                responseJson = registerParse(jsonObj);
                break;
            case "login":
                responseJson = login(jsonObj);
                break;
        }
//        if(message == "register") {
//            responseJson = registerParse(jsonObj);
//        } else if(message == "login") {
//            responseJson = login(jsonObj);
//        }
        System.out.println("parseMessage");
        return responseJson;
    }

    public JSONObject registerParse(JSONObject jsonObj) {
        /*
        {"message":"register","data":{"serial":"8b8b7b54131f2cb7","mac":"4A0347A91FC2","timestamp":"1628753058","sign":"5b28affa20f8b7b99fd1ec799c415535","type":"MTO71nC-V"}}
        {"message":"register","result":0,"desc":"","data":{"uuid":"503337a3498b4e6b8349b084c26e1147","passwd":"sUYpY/mV+WDaBacu","activateCodeNo":null,"activateCodeKey":null,"activateCode":null}}
        * */
        responseJson = JSONObject.fromObject("{\"message\":\"register\",\"result\":0,\"desc\":\"\",\"data\":{\"uuid\":\"503337a3498b4e6b8349b084c26e1147\",\"passwd\":\"sUYpY/mV+WDaBacu\",\"activateCodeNo\":null}}");
        System.out.println("registerParse");
        return  responseJson;
    }

    public JSONObject login(JSONObject jsonObj) {
        /*
        * {"message":"login","data":{"uuid":"503337a3498b4e6b8349b084c26e1147","timestamp":"1628753058","sign":"63e36826374cdb690f4fdae1b9744856","version":"2.33.13_2021-08-10_18:30 MTO71nC-V"}}
        {"message":"login","result":0,"desc":"","data":null}
        * */
        responseJson = JSONObject.fromObject("{\"message\":\"login\",\"result\":0,\"desc\":\"\",\"data\":null}");
        System.out.println("login");
        return  responseJson;
    }
}
