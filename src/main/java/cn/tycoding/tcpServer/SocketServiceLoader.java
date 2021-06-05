package cn.tycoding.tcpServer;

import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SocketServiceLoader extends ContextLoader implements ServletContextListener {

    private SocketThread socketThread;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        if (null != socketThread && !socketThread.isInterrupted()) {
            socketThread.closeSocketServer();
            socketThread.interrupt();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        if (null == socketThread) {
            //新建线程类
            socketThread = new SocketThread(null);
            //启动线程
            socketThread.start();
        }
    }
}
