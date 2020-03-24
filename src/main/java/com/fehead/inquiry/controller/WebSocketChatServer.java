package com.fehead.inquiry.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fehead.inquiry.dao.MessageDO;
import com.fehead.inquiry.service.model.BASE64DecodedMultipartFile;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import util.HttpClientUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 写代码 敲快乐
 * だからよ...止まるんじゃねぇぞ
 * ▏n
 * █▏　､⺍
 * █▏ ⺰ʷʷｨ
 * █◣▄██◣
 * ◥██████▋
 * 　◥████ █▎
 * 　　███▉ █▎
 * 　◢████◣⌠ₘ℩
 * 　　██◥█◣\≫
 * 　　██　◥█◣
 * 　　█▉　　█▊
 * 　　█▊　　█▊
 * 　　█▊　　█▋
 * 　　 █▏　　█▙
 * 　　 █
 *
 * @author Nightnessss 2020/3/22 13:17
 */
@Component
@ServerEndpoint("/chat/{userId}")
public class WebSocketChatServer {

    private static Logger logger = LoggerFactory.getLogger(WebSocketChatServer.class);
    private static Map<String, Session> chatSessions = new ConcurrentHashMap<>();
    private final String URL = "http://47.92.196.104:8801/image";

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        chatSessions.put(userId, session);
        logger.info(userId + "用户建立连接");

    }

    @OnMessage
    public void onMessage(Session session, String jsonStr) throws BusinessException, IOException {
        if (StringUtils.isEmpty(jsonStr)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        MessageDO message = JSONObject.parseObject(jsonStr, MessageDO.class);
        if (message.getType() == MessageDO.PIC) {
            String postRet = HttpClientUtil.filePost(URL, Base64Str2MultipartFile(message.getContent()));
            JSONObject jsonObject = JSON.parseObject(postRet);
            message.setContent((String) jsonObject.get("url"));
        }

        List<String> toUsers = new ArrayList<>();
        toUsers.add(message.getUserId());
        if (!message.getUserId().equals(message.getReceiverId())) {
            toUsers.add(message.getReceiverId());
        }
        sendMessage(JSONObject.toJSONString(message), toUsers);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session) {
        chatSessions.remove(userId);
    }

    @OnError
    public void onError(Session session, Throwable error) throws BusinessException {
        throw new BusinessException(EmBusinessError.MESSAGE_ERROR, error.getMessage());
    }

    private static void sendMessageToAll(String msg) {
        chatSessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void sendMessage(String msg, List<String> toUsers) throws BusinessException {
        for (String toUser : toUsers) {
            try {
                chatSessions.get(toUser).getBasicRemote().sendText(msg);
            } catch (IOException e) {
                throw new BusinessException(EmBusinessError.MESSAGE_ERROR, "消息发送失败");
            }
        }

    }

    private static MultipartFile Base64Str2MultipartFile(String base64Data) throws BusinessException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(base64Data)) {
        }
        System.out.println("==接收到的数据=="+base64Data);
        //base64格式前头
        String dataPrix = "";
        //实体部分数据
        String data = "";
        if(base64Data==null||"".equals(base64Data)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }else {
            //将字符串分成数组
            String [] d = base64Data.split("base64,");
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
        }
        //图片后缀，用以识别哪种格式数据
        String suffix = "";
        //data:image/jpeg;base64,base64编码的jpeg图片数据
        String imageJPEG = "data:image/jpeg;";
        String imageICON = "data:image/x-icon;";
        String imageGIF = "data:image/gif;";
        String imagePNG = "data:image/png;";
        if(imageJPEG.equalsIgnoreCase(dataPrix)){
            suffix = ".jpg";
        }else if(imageICON.equalsIgnoreCase(dataPrix)){
            //data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        }else if(imageGIF.equalsIgnoreCase(dataPrix)){
            //data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        }else if(imagePNG.equalsIgnoreCase(dataPrix)){
            //data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "请上传jpg/ico/gif/png格式的图片");
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String tempFileName = uuid + suffix;

        MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(base64Data, tempFileName);
        return multipartFile;

    }
}
