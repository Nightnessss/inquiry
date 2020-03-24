package com.fehead.inquiry.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.response.CommonReturnType;
import org.springframework.web.bind.annotation.*;

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
 * @author Nightnessss 2020/3/21 22:39
 */
@RestController
@RequestMapping("api/v1/inquiry")
public class ChatController extends BaseController {

    @GetMapping("/chat/{userId}")
    public CommonReturnType chat(@PathVariable("userId") String userId) {


        return CommonReturnType.create(null);
    }
}
