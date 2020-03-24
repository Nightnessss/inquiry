package com.fehead.inquiry.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

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
@Data
public class MessageDO {

    public static final int TEXT = 0;
    public static final int PIC = 1;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String receiverId;
    private Date time;
    private String content;
    private Integer type;

    public MessageDO(String userId, String receiverId, Date time, String content, Integer type) {
        this.userId = userId;
        this.receiverId = receiverId;
        this.time = time;
        this.content = content;
        this.type = type;
    }

}
