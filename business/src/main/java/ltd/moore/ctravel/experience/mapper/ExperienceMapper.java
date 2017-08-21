package ltd.moore.ctravel.experience.mapper;

import com.hdos.platform.core.base.BaseMapper;
import ltd.moore.ctravel.experience.model.ExperienceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceMapper extends BaseMapper<ExperienceVO> {
    List<String> getExperienceByCustomerId(String customerId);
}
