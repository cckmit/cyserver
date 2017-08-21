package ltd.moore.ctravel.experience.mapper;

import com.hdos.platform.core.base.BaseMapper;
import ltd.moore.ctravel.experience.model.ServiceTimeVO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ServiceTimeMapper extends BaseMapper<ServiceTimeVO> {
    List<ServiceTimeVO> getByExperienceId(String experienceId);
}
