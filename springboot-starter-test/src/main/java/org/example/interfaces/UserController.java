package org.example.interfaces;



//import org.example.DoRateLimiter.annotation.DoRateLimiter;
//import org.example.whiteList.annotation.DoWhiteList;
import org.example.enity.UserInfo;
import org.example.methodext.annotation.DoMethodExt;
import org.example.whiteList.annotation.DoWhiteList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 通过：http://localhost:8081/api/queryUserInfo?userId=aaa
     * 拦截：http://localhost:8081/api/queryUserInfo?userId=123
     */
    @DoMethodExt(method = "blacklist",returnJson = "{\"code\":\"1111\",\"info\":\"黑名单不可访问用户拦截！\"}")

    @DoWhiteList(key = "userId", returnJson = "{\"code\":\"1111\",\"info\":\"非白名单可访问用户拦截！\"}")
//    @DoHystrix(timeoutValue = 350,returnJson = "{\"code\":\"1111\",\"info\":\"调用时间超过350毫秒，熔断返回\"}")
//    @DoRateLimiter(peritsPerSecond = 1,returnJson = "{\"code\":\"1111\",\"info\":\"访问量超过1，熔断返回\"}")
    @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public UserInfo queryUserInfo(@RequestParam String userId)  {
        logger.info("查询用户信息，userId：{}", userId);
//        Thread.sleep(1000000);
        return new UserInfo("虫虫:" + userId, 19, "天津市东丽区万科赏溪苑14-0000");
    }

    public boolean blacklist(@RequestParam String userId) {
        if ("bbb".equals(userId) || "222".equals(userId)) {
            logger.info("拦截自定义黑名单用户 userId：{}", userId);
            return false;
        }
        return true;
    }

}

