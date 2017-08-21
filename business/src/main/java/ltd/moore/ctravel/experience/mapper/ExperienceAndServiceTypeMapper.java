package ltd.moore.ctravel.experience.mapper;

import com.hdos.platform.core.base.BaseMapper;
import ltd.moore.ctravel.experience.model.ExperienceAndServiceTypeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceAndServiceTypeMapper extends BaseMapper<ExperienceAndServiceTypeVO> {
    List<ExperienceAndServiceTypeVO>  getListById(String var1);
}
