package ltd.moore.ctravel.experience.mapper;

import com.hdos.platform.core.base.BaseMapper;
import ltd.moore.ctravel.experience.model.ServiceTypeVO;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeMapper extends BaseMapper<ServiceTypeVO> {
    ServiceTypeVO getByServiceName(String serviceName);
}
