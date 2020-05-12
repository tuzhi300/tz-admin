package net.kuper.tz.service;

import net.kuper.tz.core.properties.AdminProperties;
import net.kuper.tz.core.service.SuperRoleService;
import net.kuper.tz.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperRoleServiceImpl implements SuperRoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminProperties properties;

    @Override
    public boolean isSuperRoleByUser(Object userId) {
        boolean result = false;
        List<String> roleIds = roleDao.queryUserRoleIds(String.valueOf(userId));
        String roleId= properties.getSuperRoleId();
        if (roleIds != null && roleIds.contains(roleId)) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean isSuperRole(Object roleId) {
        return roleId.equals(properties.getSuperRoleId());
    }

    @Override
    public Object superRoleId() {
        return this.properties.getSuperRoleId();
    }
}
