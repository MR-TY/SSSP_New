package com.ty.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ty.entity.Department;
import com.ty.entity.Employee;
import com.ty.service.DepartmentService;
import com.ty.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;
	/**
	 * 
	* @Function: EmployeeController.java
	* @Description: 分页操作
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月1日 下午11:32:05 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月1日       water           v1.0.0               修改原因
	 */
	@RequestMapping("hello")
	public String getPage(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNoStr,
			Map<String, Object> map) {
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
			if (pageNo < 1) {
				pageNo = 1;
			}
		} catch (Exception e) {
		}
		Page<Employee> page = employeeService.getPage(pageNo, 5);
		map.put("page", page);
		return "emp/list";
	}
	/**
	 * 
	* @Function: EmployeeController.java
	* @Description: 获取所有的部门
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月1日 下午11:31:47 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月1日       water           v1.0.0               修改原因
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("departments", departmentService.getAll());
		map.put("employee", new Employee());
		return "emp/input";
	}
	/**
	 * 
	* @Function: EmployeeController.java
	* @Description: 通过此方法判定是否能进行保存，名字重复不能进行保存
	*				當填寫lastName這一項的時候，就會觸發，函數進入地址
	*				中映射到的函數，通過後端判別，填寫的名字在數據庫中是否存在
	*				同樣的名字，然後返回不同的值，返回到前端，前端判別是否能進行
	*				添加操作
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月1日 下午11:30:18 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月1日       water           v1.0.0               修改原因
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxValidateLastName", method = RequestMethod.POST)
	public String validateLatName(@RequestParam(value = "lastName", required = true) String lastName) {
		Employee employee = employeeService.getByLastName(lastName);
		if (employee == null) {
			return "0";
		} else {
			return "1";
		}
	}

	/**
	 * 
	* @Function: EmployeeController.java
	* @Description: 保存员工
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月1日 下午11:29:21 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月1日       water           v1.0.0               修改原因
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String saveEmployee(Employee employee) {
		employeeService.Save(employee);
		return "redirect:/hello";
	}
	
	/**
	 * 
	* @Function: EmployeeController.java
	* @Description: 任何方法执行，都会执行这个函数，所以可以设置一个条件，
	* 				进行选择性执行，会在任何方法执行之前被调用
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月1日 下午11:32:34 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月1日       water           v1.0.0               修改原因
	 */
	@ModelAttribute
	public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (id != null) {
			Employee employee = employeeService.get(id);
			employee.setDepartment(null);// 当进行修改的时候，如果不设置department为空的时候，就会执行同一个department，此时关联的外键就不能修改，就只能重新启动一个department
			map.put("employee", employee);
		}
	}
	/**
	 * 
	* @Function: EmployeeController.java
	* @Description: 修改中发现一个员工的操作
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月1日 下午11:28:55 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月1日       water           v1.0.0               修改原因
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public String inputOneEmp(@PathVariable("id") Integer id, Map<String, Object> map) {
		Employee employee = employeeService.get(id);
		map.put("employee", employee);
		map.put("departments", departmentService.getAll());
		return "emp/input";
	}
	/**
	 * 修改员工
	* @Function: EmployeeController.java
	* @Description: 该函数的功能描述
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月1日 下午11:29:49 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月1日       water           v1.0.0               修改原因
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.PUT)
	public String UpdateEmployee(Employee employee) {
		employeeService.Save(employee);
		return "redirect:/hello";
	}
	/**
	 * 
	* @Function: EmployeeController.java
	* @Description: 删除操作
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月1日 下午11:28:34 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月1日       water           v1.0.0               修改原因
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {
		employeeService.delete(id);
		return "redirect:/hello";
	}
}
