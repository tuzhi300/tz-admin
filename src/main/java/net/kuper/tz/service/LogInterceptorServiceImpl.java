package net.kuper.tz.service;

import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.common.entity.LogUpdateEntity;
import net.kuper.tz.common.service.LogService;
import net.kuper.tz.core.controller.log.ILogInterceptorService;
import net.kuper.tz.core.controller.log.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogInterceptorServiceImpl implements ILogInterceptorService {

    @Autowired
    private LogService logService;


    @Override
    public void saveLog(LogInfo info) {
        LogUpdateEntity entity = new LogUpdateEntity();

        entity.setOsName(info.getOsName());
        entity.setOsVersion(info.getOsVersion());
        entity.setBrowerName(info.getBrowerName());
        entity.setBrowerType(info.getBrowerType());
        entity.setBrowerVersion(info.getBrowerVersion());

        entity.setCreateTime(info.getCreateDate());
        entity.setIp(info.getIp());
        entity.setMethod(info.getMethod());
        entity.setOperation(info.getOperation());

        entity.setParams(info.getParams());
        entity.setResult(info.getResult());
        entity.setTime(info.getTime());
        entity.setServletPath(info.getServletPath());
        entity.setType(info.getType());
        entity.setUserId(String.valueOf(info.getUserId()));
        entity.setUserType(1);
        logService.save(entity);
    }

}
