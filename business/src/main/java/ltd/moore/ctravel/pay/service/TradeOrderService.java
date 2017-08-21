package ltd.moore.ctravel.pay.service;

import com.hdos.platform.common.page.Page;
import com.hdos.platform.common.page.PageImpl;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.pay.mapper.TradeOrderMapper;
import ltd.moore.ctravel.pay.model.OrderInfoVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Cocouzx on 2017-7-4 0004.
 */
@Service
@Transactional
public class TradeOrderService {
    @Autowired
    private TradeOrderMapper tradeOrderMapper;
    /**
     * 创建订单
     * @param orderInfoVO
     */
    public void createOrder(OrderInfoVO orderInfoVO) {
        tradeOrderMapper.insert(orderInfoVO);
    }

    /**
     * 根据ID获得订单信息
     * @param orderId
     * @return
     */
    public OrderInfoVO getOrderInfoById(String orderId) {
        return tradeOrderMapper.getOrderInfoById(orderId);
    }

    /**
     * 更新order
     * @param orderInfoVO
     */
    public void update(OrderInfoVO orderInfoVO) {
        tradeOrderMapper.update(orderInfoVO);
    }

    /**
     * 根据客户ID查找订单信息
     * @param talentId
     * @return
     */
    public List<OrderInfoVO> queryOrderInfoByCustomerId(String talentId) {
        return tradeOrderMapper.queryOrderInfoByCustomerId(talentId);
    }

    /**
     * 分页显示订单信息
     * @param queryCondition
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<OrderInfoVO> findPage(Map<String, Object> queryCondition, int pageNumber, int pageSize) {
        int total = tradeOrderMapper.count(queryCondition);
        RowBounds rowBounds = new RowBounds((pageNumber - 1) * pageSize, pageSize);
        List<OrderInfoVO> content = total > 0 ? tradeOrderMapper.list(queryCondition, rowBounds) : new ArrayList<OrderInfoVO>(0);
        return new PageImpl<OrderInfoVO>(content, pageNumber, pageSize, total);
    }
}
