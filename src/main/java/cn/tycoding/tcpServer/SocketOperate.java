package cn.tycoding.tcpServer;

import java.io.*;
import java.net.Socket;
import net.sf.json.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cn.tycoding.utils.getStringLength;

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
    JSONObject responseJson;
    int openDoor = 1;
    String tip = "你好";
    String level = "green";
    String name = "张三";
    Map connectMap;


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
//                System.out.println(socket);
                inputStream = socket.getInputStream();
                byte[] recBuffer = new byte[3000];
                inputStream.read(recBuffer);
                String maic = new String(recBuffer, 0, 4);
                if(maic.equals("QYZN")) {
                    System.out.println("Magic = " + maic);
                    System.out.println("Version = " + recBuffer[4]);
                    System.out.println("Msgtype = " + recBuffer[5]);
                    System.out.println("Datatype = " + recBuffer[6]);
                    System.out.println("Resv = " + recBuffer[7]);
                    System.out.println("Timestamp = " + (recBuffer[11]<<24 | recBuffer[10]<<16 | recBuffer[9]<<8 | recBuffer[8]));
                    System.out.println("Seq = " + (recBuffer[15]<<24 | recBuffer[14]<<16 | recBuffer[13]<<8 | recBuffer[12]));

                    int dataSize = recBuffer[19]<<24 | recBuffer[18]<<16 | recBuffer[17]<<8 | recBuffer[16];
                    System.out.println("dataSize = " + dataSize);
                    if(dataSize != 0) {
//                    String receivePayload = new String(recBuffer, 20, recBuffer.length - 20);
                        String receivePayload = new String(recBuffer, 20, 1500);
                        System.out.println("receivePayloadLen = " + receivePayload.length() + " " + "receivePayload = " + receivePayload);
                        if(receivePayload.contains("snapshot_face"))
                        {
                            responseJson = JSONObject.fromObject("{\"message\":\"snapshot_face\",\"result\":0,\"desc\":\"\",\"data\":null}");
                        }
                        else {
                            if(!receivePayload.isEmpty()) {
                                JSONObject jsonObj = JSONObject.fromObject(receivePayload);
                                responseJson = parseMessage(jsonObj);
                            }
                        }
                    } else {
                        responseJson = null;
                    }

                    /*向客户端发送消息*/
                    recBuffer[5] = 4;//响应类型
                    Arrays.fill(recBuffer, 20, recBuffer.length - 1, (byte)0);

                    int dataLen = 0;
                    if(responseJson != null) {
                        dataLen = getStringLength.length(responseJson.toString());
                        System.out.println("response dataLen = " + dataLen);
                        System.out.println("responseJson = " + responseJson.toString());
                        byte[] byt = responseJson.toString().getBytes("UTF-8");

                        System.arraycopy(byt, 0, recBuffer, 20, byt.length);
                        String reponsePayload = new String(recBuffer, 20, recBuffer.length - 20);
//                        System.out.println("reponsePayload = " + reponsePayload);

                        recBuffer[19] = (byte) ((dataLen & 0xFFFFFFFF)>>24);
                        recBuffer[18] = (byte) ((dataLen & 0x00FFFFFF)>>16);
                        recBuffer[17] = (byte) ((dataLen & 0x0000FFFF)>>8);
                        recBuffer[16] = (byte) dataLen;

                        String sendPayload = new String(recBuffer, 20, recBuffer.length - 20);
                        System.out.println("sendDataLen = " + dataLen);
                        System.out.println("sendPayloadLen = " + sendPayload.length() + " " + "sendPayload = " + sendPayload);

                        outputStream = socket.getOutputStream();
                        outputStream.write(recBuffer, 0, dataLen + 20);
                    }
                }

                Thread.sleep(20);
            }
        } catch (Exception e) {
            System.out.println("服务器主动断开连接");
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
        switch (message) {
            case "register":
                responseJson = registerParse(jsonObj);
                break;
            case "login":
                responseJson = login(jsonObj);
                break;
            case "sync_time":
                responseJson = syncTime(jsonObj);
                break;
            case "qrcode_data":
                responseJson = qrodeData(jsonObj);
                break;
            case "query_qrcode":
                responseJson = queryQrode(jsonObj);
                break;
            case "snapshot_face":
                responseJson = snapshotFace(jsonObj);
                break;

            default:responseJson = null;
        }
//        if(message == "register") {
//            responseJson = registerParse(jsonObj);
//        } else if(message == "login") {
//            responseJson = login(jsonObj);
//        }
        return responseJson;
    }

    public JSONObject registerParse(JSONObject jsonObj) {
        /*
        {"message":"register","data":{"serial":"8b8b7b54131f2cb7","mac":"4A0347A91FC2","timestamp":"1628753058","sign":"5b28affa20f8b7b99fd1ec799c415535","type":"MTO71nC-V"}}
        {"message":"register","result":0,"desc":"","data":{"uuid":"503337a3498b4e6b8349b084c26e1147","passwd":"sUYpY/mV+WDaBacu","activateCodeNo":null,"activateCodeKey":null,"activateCode":null}}
        * */
        responseJson = JSONObject.fromObject("{\"message\":\"register\",\"result\":0,\"desc\":\"\",\"data\":{\"uuid\":\"503337a3498b4e6b8349b084c26e1147\",\"passwd\":\"sUYpY/mV+WDaBacu\",\"activateCodeNo\":null}}");
//        responseJson = JSONObject.fromObject("{\"result\":0,\"desc\":\"Success\",\"data\":{\"uuid\":\"7d569218eb31cb1d\",\"passwd\":\"51589158\"}}");
        return  responseJson;
    }

    public JSONObject login(JSONObject jsonObj) {
        /*
        * {"message":"login","data":{"uuid":"503337a3498b4e6b8349b084c26e1147","timestamp":"1628753058","sign":"63e36826374cdb690f4fdae1b9744856","version":"2.33.13_2021-08-10_18:30 MTO71nC-V"}}
        {"message":"login","result":0,"desc":"","data":null}
        * */
        responseJson = JSONObject.fromObject("{\"message\":\"login\",\"result\":0,\"desc\":\"\",\"data\":null}");
//        System.out.println("login");
        return  responseJson;
    }

    public JSONObject syncTime(JSONObject jsonObj) {
        /*
         {"message":"sync_time","data":{"timestamp":"20190531145826123"}}
         {"message":"sync_time","result":0,"desc":"","data":{"timestamp":"20210813113236873"}}
        * */
        responseJson = new JSONObject();
        responseJson.put("message", "sync_time");
        responseJson.put("result", 0);
        responseJson.put("desc", "");

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(d);
//        System.out.println("dateTime：" + dateTime);

        JSONObject timeStamp = new JSONObject();
        responseJson.put("data", timeStamp);
//        System.out.println("syncTime json = " + responseJson.toString());

        return  responseJson;
    }

    /*扫苏康码查询苏康码信息*/
    public JSONObject qrodeData(JSONObject jsonObj) {
        /*
        {"message":"qrcode_data","data":{"content":"http://1024w.net"}}
        {"message":"qrcode_data","result":0,"desc":"123","data":{"opendoor":0}}
        * */
        responseJson = new JSONObject();
        responseJson.put("message", "qrcode_data");
        responseJson.put("result", 0);
        responseJson.put("desc", "");

        JSONObject data = new JSONObject();
        data.put("opendoor", openDoor);
        data.put("name", name);
        data.put("tip", tip);
        data.put("idcardNo", "450522199811256969");
        data.put("level", level);
        responseJson.put("data", data);
//        responseJson.put("data", "");
        return  responseJson;
    }

    /*
    * 通过身份证查询苏康码信息
    * */
    public JSONObject queryQrode(JSONObject jsonObj) {
        /*
        {"message":"query_qrcode","data":{"content":"789"}}
        {"message":"query_qrcode","result":0,"desc":"123","data":{"opendoor":0}}
        * */
        responseJson = new JSONObject();
        responseJson.put("message", "query_qrcode");
        responseJson.put("result", 0);
        responseJson.put("desc", "");

        JSONObject data = new JSONObject();
        data.put("opendoor", openDoor);
        data.put("name", name);
        data.put("tip", tip);
        data.put("idcardNo", "450522199811256969");
        data.put("level", level);
        responseJson.put("data", data);
//        responseJson.put("data", "");
        return  responseJson;
    }

    public JSONObject snapshotFace(JSONObject jsonObj) {
        responseJson = JSONObject.fromObject("{\"message\":\"snapshot_face\",\"result\":0,\"desc\":\"\",\"data\":null}\n");
//        System.out.println("login");
        return  responseJson;
    }
}
