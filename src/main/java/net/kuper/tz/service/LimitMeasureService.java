package net.kuper.tz.service;

import net.kuper.tz.common.entity.RequestLimitUpdateEntity;
import net.kuper.tz.common.service.RequestLimitService;
import net.kuper.tz.core.controller.limit.ILimitMeasure;
import net.kuper.tz.core.controller.limit.LimitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LimitMeasureService implements ILimitMeasure {

    @Autowired
    private RequestLimitService requestLimitService;

    @Override
    public LimitInfo getLimitInfo(String ip, String session, Date sessionBegin, Object userId, Date userBegin, String limitId) {
        LimitInfo info = new LimitInfo();
        info.setSessionCount(requestLimitService.querySessionCount(limitId, session, sessionBegin));
        if (null != userId && !"".equals(userId)) {
            info.setUserCount(requestLimitService.queryUserCount(limitId, String.valueOf(userId), userBegin));
        } else {
            info.setUserCount(0L);
        }
        return info;
    }

    @Override
    public void record(Date date, String ip, String session, Object userId, String limitId, String path, String description) {
        RequestLimitUpdateEntity limitEntity = new RequestLimitUpdateEntity();
        limitEntity.setCreateTime(date);
        limitEntity.setIp(ip);
        limitEntity.setSessionId(session);
        limitEntity.setUserId(String.valueOf(userId));
        limitEntity.setLimitId(limitId);
        limitEntity.setUrl(path);
        limitEntity.setDescription(description);
        requestLimitService.save(limitEntity);
    }

}
