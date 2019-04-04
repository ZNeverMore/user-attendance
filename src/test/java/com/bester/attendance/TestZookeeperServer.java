package com.bester.attendance;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author zhangqiang
 * @date 2019-04-04
 */

public class TestZookeeperServer {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        TestZookeeperServer server = new TestZookeeperServer();

//        1.获取连接
        server.getConnect();
        server.register("server2");
        server.business();


    }

    private String connectString = "39.96.165.167:2181";
    private int sessionTimeOut = 2000;
    private ZooKeeper zooKeeper;

    private void getConnect() throws IOException {
        Watcher watcher = watcherEvent -> {

        };
        zooKeeper = new ZooKeeper(connectString, sessionTimeOut, watcher);
    }

    /**
     * create方法的第三个参数： 类似于设置访问权限
     * 第四个参数： 为命令行中的 -s， -e
     * -s意思是：有序号的
     * -e意思是：临时的, 客户端关闭连接后节点就消失
     *
     * @param hostName 注册节点的服务器名称
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void register(String hostName) throws KeeperException, InterruptedException {
        String path = zooKeeper.create("/servers/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println(hostName + "is online;" + path);

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

}
