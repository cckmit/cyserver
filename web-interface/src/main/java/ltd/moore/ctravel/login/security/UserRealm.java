package ltd.moore.ctravel.login.security;

/**
 * Created by Cocouzx on 2017-6-28 0028.
 */
import com.hdos.platform.base.role.service.RoleService;
import com.hdos.platform.base.user.model.AccountVO;
import com.hdos.platform.base.user.service.AccountService;

import java.security.Principal;
import java.util.List;

import com.hdos.platform.core.shiro.UserProfile;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.service.CustomerAccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    CustomerAccountService customerAccountService;
    public UserRealm() {
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        String username = token.getUsername();
        String password = DigestUtils.md5Hex(String.valueOf(token.getPassword()));
        CustomerAccountVO accountVO = customerAccountService.queryAccountByAccountAndPwd(username, password);
        if(null != accountVO) {
            return new SimpleAuthenticationInfo(accountVO, accountVO.getPassword(), getName());
        } else {
            return null;
        }
    }
}
