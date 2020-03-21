package com.fehead.inquiry.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

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
 * @author Nightnessss 2020/3/21 22:45
 */
public class MessageDO {

    public static final int TEXT = 0;
    public static final int PIC = 1;

    private int id;
    private String userId;
    private String receiverId;
    private Date time;
    private int type;

    public MessageDO(String userId, String receiverId, Date time, int type) {
        this.userId = userId;
        this.receiverId = receiverId;
        this.time = time;
        this.type = type;
    }
}
