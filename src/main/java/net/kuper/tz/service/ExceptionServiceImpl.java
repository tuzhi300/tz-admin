package net.kuper.tz.service;

import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.common.service.ExceptionService;
import net.kuper.tz.core.controller.exception.IExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionServiceImpl implements IExceptionService {

    @Autowired
    private ExceptionService exceptionService;

    @Override
    public void catchError(Exception exception) {
        try {
            exceptionService.onException(exception);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
