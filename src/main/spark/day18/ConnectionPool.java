package day18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

/**
 * @ClassName: ConnectionPool
 * @Description: TODO
 * @Author: xqg
 * @Date: 2018/10/16 14:13
 *
 * 简易版连接池
 */
public class ConnectionPool {
    //静态的Connection队列
    private static LinkedList<Connection> connectionLinkedList;

    /**
     * 加载驱动
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接,多线程访问并发控制
     */
    public synchronized static Connection getConnection() {
        try {
            if (connectionLinkedList == null) {
                connectionLinkedList = new LinkedList<Connection>();
                for (int i = 0; i <= 10; i++) {
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/mytest",
                            "root",
                            "123456"
                    );
                    connectionLinkedList.push(conn);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return connectionLinkedList.poll();
    }

    /**
     * 返回去一个连接
     */
    public static void returnConnection(Connection conn){
        connectionLinkedList.push(conn);
    }
}