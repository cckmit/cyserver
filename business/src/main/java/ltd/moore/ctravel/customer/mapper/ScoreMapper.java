package ltd.moore.ctravel.customer.mapper;

import com.hdos.platform.core.base.BaseMapper;
import ltd.moore.ctravel.customer.model.ScoreVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreMapper extends BaseMapper<ScoreVO> {
    List<ScoreVO> list1(@Param("condition") Map<String, Object> var1);

    List<ScoreVO> list1(@Param("condition") Map<String, Object> var1, @Param("rowBounds") RowBounds var2);
}
