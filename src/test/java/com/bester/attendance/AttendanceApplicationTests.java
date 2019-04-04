package com.bester.attendance;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttendanceApplicationTests {

    private ZooKeeper zooKeeper;

    @Test
    public void contextLoads() {
    }

    @Before
    public void init() throws IOException {
        Watcher watcher = watchedEvent -> {
            List<String> children;
            try {
                System.out.println("-------start--------");
                children = zooKeeper.getChildren("/", true);
                for (String child : children) {
                    System.out.println(child);
                }
                System.out.println("---------end----------");
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }

        };
        int sessionTimeout = 2000;
        String connect = "39.96.165.167:2181";
        zooKeeper = new ZooKeeper(connect, sessionTimeout, watcher);
    }

    //    创建节点
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String path = zooKeeper.create("/znode-second", "test-second".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    //    获取子节点 并监控节点的变化
    @Test
    public void getDataAndWatch() throws KeeperException, InterruptedException {
//        List<String> children = zooKeeper.getChildren("/", true);
//        for (String child : children) {
//            System.out.println(child);
//        }
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists("/znode-third", false);
        System.out.println(exists == null ? "not exist" : "exist");
    }

}
