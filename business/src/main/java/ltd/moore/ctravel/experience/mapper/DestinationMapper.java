package ltd.moore.ctravel.experience.mapper;

import com.hdos.platform.core.base.BaseMapper;
import ltd.moore.ctravel.experience.model.DestinationVO;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationMapper extends BaseMapper<DestinationVO> {
    DestinationVO getByDestination(String destination);
}
