package nju.edu.uml.webumldesigner.util;

import nju.edu.uml.webumldesigner.serviceImpl.GroupEditServiceImpl;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

public class GroupEditUtil {
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditServiceImpl>> groupEditList = new ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditServiceImpl>>();

    private Session session;

}
