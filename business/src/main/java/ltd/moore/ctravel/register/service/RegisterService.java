package ltd.moore.ctravel.register.service;

import ltd.moore.ctravel.register.dto.RegisterDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhutail on 2017/6/5
 */
@Service
public interface RegisterService {

    /**
     * 注册验证
     *
     * @param registerDTO
     * @return
     */
    Map<String, Object> registerValidation(RegisterDTO registerDTO);

    /**
     * 注册
     *
     * @param registerDTO
     * @return
     */
    Map<String,Object> register(RegisterDTO registerDTO);
}
