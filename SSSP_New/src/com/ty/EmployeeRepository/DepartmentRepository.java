package com.ty.EmployeeRepository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.ty.entity.Department;
/**
 * 
* Copyright: Copyright (c) 2018 LanRu-Caifu
* 
* @ClassName: DepartmentRepository.java
* @Description: 
*         @QueryHints({@QueryHint(name=org.hibernate.ejb.QueryHints.HINT_CACHEABLE,value="true")})
* 		     此設置是進行二級緩存的開啟操作
*
* @version: v1.0.0
* @author: water
* @date: 2018年1月2日 上午12:02:11 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年1月2日       water           v1.0.0               修改原因
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	@QueryHints({@QueryHint(name=org.hibernate.ejb.QueryHints.HINT_CACHEABLE,value="true")})
	@Query("from Department d")
	List<Department> getAll();
}
