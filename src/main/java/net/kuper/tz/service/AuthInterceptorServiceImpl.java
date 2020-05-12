package net.kuper.tz.service;

import net.kuper.tz.core.controller.auth.IAuthInterceptorService;
import net.kuper.tz.system.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthInterceptorServiceImpl implements IAuthInterceptorService {
    private static List<String> whiteList = new ArrayList<>();

    static {
        whiteList.add("/");
        whiteList.add("/static");
    }

    @Override
    public List<String> whiteServletPath() {
        return AuthInterceptorServiceImpl.whiteList;
    }

    @Override
    public String userIdFaild() {
        return "id";
    }

    @Override
    public Class userIdClass() {
        return String.class;
    }

    @Override
    public Class userBeanClass() {
        return UserEntity.class;
    }
}
