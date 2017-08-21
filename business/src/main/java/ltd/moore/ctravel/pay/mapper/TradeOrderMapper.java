package ltd.moore.ctravel.pay.mapper;

import ltd.moore.ctravel.pay.model.OrderInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Cocouzx on 2017-7-4 0004.
 */
@Repository
public interface TradeOrderMapper {
    /**
     * 创建订单
     * @param orderInfoVO
     */
    void insert(OrderInfoVO orderInfoVO);
    /**
     * 根据ID获得订单信息
     * @param orderId
     * @return
     */
    OrderInfoVO getOrderInfoById(String orderId);
    /**
     * 更新order
     * @param orderInfoVO
     */
    void update(OrderInfoVO orderInfoVO);

    /**
     * 根据客户ID查找订单信息
     * @param customerId
     * @return
     */
    List<OrderInfoVO> queryOrderInfoByCustomerId(String customerId);

    /**
     * 订单个数
     * @param queryCondition
     * @return
     */
    int count(@Param("condition") Map<String, Object> queryCondition);


    /**
     * 订单详细信息
     */
    List<OrderInfoVO> list(@Param("condition") Map<String, Object> queryCondition, RowBounds rowBounds);
}
