package ltd.moore.ctravel.customer;

import java.util.HashMap;
import java.util.Map;

import com.hdos.platform.common.page.Page;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.model.CustomerDetailVO;
import ltd.moore.ctravel.customer.service.CustomerAccountService;
import ltd.moore.ctravel.customer.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * CustomerAccount控制器
 * @author caicai
 * @version 1.0
 */
@Controller
@RequestMapping("/customerAccount")
public class CustomerAccountController {
   
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerDetailService customerDetailService;
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(String account,String mobile,String customerType,int page,int rows) {
		Map<String, Object> queryCondition = new HashMap<String,Object>();
		if("0".equals(customerType)){
			customerType="";
		}
		queryCondition.put("account", account);
		queryCondition.put("mobile", mobile);
		queryCondition.put("customerType", customerType);
		Page<CustomerAccountVO> customerAccountPage = customerAccountService.findPage(queryCondition, page, rows);
		//Page<CustomerDetailVO> customerAccountPage2 = customerDetailService.findPage(queryCondition, page, rows);
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
		return "/customer/customerMain";
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
		return "/customer/customerDetail";
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
}
