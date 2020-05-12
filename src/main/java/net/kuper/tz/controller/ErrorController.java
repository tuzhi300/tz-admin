package net.kuper.tz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.controller.Res;
import net.kuper.tz.core.controller.ResCode;
import net.kuper.tz.core.controller.auth.IgnoreAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "异常")
@RestController
@RequestMapping("/error")
public class ErrorController {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 未登录
     */
    @IgnoreAuth
    @ApiOperation("未登录")
    @ResponseBody
    @GetMapping(value = "/unlogin")
    public Res unlogin() {
        return Res.code(ResCode.ERROR_SYS_AUTH_UNLOGIN);
    }

    /**
     * 未登录
     */
    @IgnoreAuth
    @ApiOperation("登录已失效")
    @ResponseBody
    @GetMapping(value = "/login/invalid")
    public Res loginInvalid() {
        return Res.code(ResCode.ERROR_SYS_AUTH_FAIL);
    }


    /**
     * 无权限
     */
    @IgnoreAuth
    @ApiOperation("无权限")
    @ResponseBody
    @GetMapping(value = "/unauth")
    public Res unauth() {
        return Res.code(ResCode.ERROR_SYS_PERMISSION);
    }
}
