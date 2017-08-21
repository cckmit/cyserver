package ltd.moore.ctravel.refreshpwd.service;

import ltd.moore.ctravel.refreshpwd.dto.RefreshPwdDTO;
import ltd.moore.ctravel.register.dto.RegisterDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by youcai on 2017/6/6
 *
 *
 */
@Service
public interface RefreshPwdService {

    /**
     * 找回密码验证
     *
     * @param refreshPwdDTO
     * @return
     */
    Map<String, Object> refreshpwdValidation(RefreshPwdDTO refreshPwdDTO);

    /**
     * 找回密码
     *
     * @param refreshPwdDTO
     * @return
     */
    Map<String,Object> refreshpwd(RefreshPwdDTO refreshPwdDTO);
}
