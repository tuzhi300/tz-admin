package net.kuper.tz.listener;

import net.kuper.tz.core.properties.TzProperties;
import net.kuper.tz.core.utils.JavaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private TzProperties tzProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 项目启动成功
        JavaUtils.addLibraryPath(tzProperties.getSigarHome());
    }
}
