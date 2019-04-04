package com.bester.attendance;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqiang
 * @date 2019-04-04
 */

public class TestZookeeperClient {


    private String connectString = "39.96.165.167:2181";
    private int sessionTimeOut = 2000;
    private ZooKeeper zkClient;

    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        TestZookeeperClient client = new TestZookeeperClient();
//        1.获取连接
        client.getConnect();
//        2.注册监听
        client.getChildren();
//        3.业务逻辑处理
        client.business();
    }

    private void getConnect() throws IOException {
        Watcher watcher = watcherEvent -> {
            try {
                getChildren();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }

        };
        zkClient = new ZooKeeper(connectString, sessionTimeOut,watcher);
    }

    private void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/servers", true);
//        存储服务器节点主机名称集合
        List<Object> hosts = new ArrayList<>();
        for (String child : children) {
            byte[] data = zkClient.getData("/servers/" + child, false, null);
            hosts.add(new String(data));
        }
        System.out.println(hosts);
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

}
