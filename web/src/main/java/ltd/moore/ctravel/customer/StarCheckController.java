package ltd.moore.ctravel.customer;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.common.page.Page;
import ltd.moore.ctravel.customer.mapper.CustomerAccountMapper;
import ltd.moore.ctravel.customer.mapper.CustomerDetailMapper;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.model.CustomerDetailVO;
import ltd.moore.ctravel.customer.service.CustomerAccountService;
import ltd.moore.ctravel.customer.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomerAccount控制器
 * @author caicai
 * @version 1.0
 */
@Controller
@RequestMapping("/starAccount")
public class StarCheckController {
   
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerDetailService customerDetailService;
	@Autowired
	private CustomerDetailMapper customerDetailMapper;
	@Autowired
	private CustomerAccountMapper customerAccountMapper;
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(String account,String mobile,int page,int rows) {
		Map<String, Object> queryCondition = new HashMap<String,Object>();
		queryCondition.put("account", account);
		queryCondition.put("mobile", mobile);
		queryCondition.put("idcardCheckStatus", "1");
		Page<CustomerAccountVO> customerAccountPage = customerAccountService.findPage(queryCondition, page, rows);
		JSONObject rst = new JSONObject();
		rst.put("total", customerAccountPage.getTotalElements());
		rst.put("rows", customerAccountPage.getContent());
		return rst.toJSONString();
	}
	
	/**
	 * 主页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init(Model model) {
		return "/customer/starCheck";
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String addCustomerAccount() {
		return "/customer/customerAdd";
	}
	/**
	 * 保存
	 * @param customerAccountVO
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String saveCustomerAccount(CustomerAccountVO customerAccountVO,CustomerDetailVO customerDetailVO) {
		return  customerAccountService.saveCustomerAccount(customerAccountVO,customerDetailVO) ? "false" : "success";
	}
	
	/**
	 * 批量删除
	 * @param customerIds
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String deleteCustomerAccount(String customerIds) {
		String[] ids = customerIds.split(",");
		if(!customerAccountService.deleteCustomerAccounts(ids)){
			return "false";
		}
		return "success";
	}
	
	/**
	 * 修改客户信息页面
	 * @return
	 */
	@RequestMapping(value = "/modify/{customerId}")
	public String modifyCustomerAccount(@PathVariable("customerId") String customerId,Model model) {
		CustomerAccountVO customerAccountVO = customerAccountService.readCustomerAccountById(customerId);
		CustomerDetailVO customerDetailVO = customerDetailService.readCustomerDetailById(customerId);
		model.addAttribute("customerAccountVO", customerAccountVO);
		model.addAttribute("customerDetailVO", customerDetailVO);
		return "/customer/starCheckDetail";
	}
	/**
	 * 查看评分信息
	 * @return
	 */
	@RequestMapping(value = "/score/{customerId}")
	public String score(@PathVariable("customerId") String customerId,Model model) {
		CustomerAccountVO customerAccountVO = customerAccountService.readCustomerAccountById(customerId);
		model.addAttribute("customerAccountVO", customerAccountVO);
		return "/customer/customerScore";
	}
	/**
	 * 审核通过
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/checkPass")
	@ResponseBody
	public String checkPass(String customerId) {
//		CustomerAccountVO customerAccountVO = customerAccountService.readCustomerAccountById(customerId);
		CustomerDetailVO customerDetailVO =new CustomerDetailVO();
		CustomerAccountVO customerAccountVO =new CustomerAccountVO();
		customerDetailVO.setCustomerId(customerId);
		customerDetailVO.setIdcardCheckStatus(9);
		customerAccountVO.setCustomerId(customerId);
		customerAccountVO.setCustomerType(2);
		customerAccountMapper.update(customerAccountVO);
		customerDetailMapper.update(customerDetailVO);
		return "success";
	}
	/**
	 * 审核不通过
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/checkFail")
	@ResponseBody
	public String checkFail(String customerId) {
//		CustomerDetailVO customerDetailVO = customerDetailService.readCustomerDetailById(customerId);
//		CustomerAccountVO customerAccountVO = customerAccountService.readCustomerAccountById(customerId);
		CustomerDetailVO customerDetailVO =new CustomerDetailVO();
		customerDetailVO.setCustomerId(customerId);
		customerDetailVO.setIdcardCheckStatus(2);
		customerDetailMapper.update(customerDetailVO);
		return "success";
	}
}
