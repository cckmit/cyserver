package ltd.moore.ctravel.experience.mapper;

import com.hdos.platform.core.base.BaseMapper;
import ltd.moore.ctravel.experience.model.ExperienceAllVO;
import ltd.moore.ctravel.experience.model.ExperienceDetailVO;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceDetailMapper extends BaseMapper<ExperienceDetailVO> {
    /**
     * 根据experiID获取detail信息
     * @param experienceId
     * @return
     */
    ExperienceDetailVO getDetailById(String experienceId);
}
