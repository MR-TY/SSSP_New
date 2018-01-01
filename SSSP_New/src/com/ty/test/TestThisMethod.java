package com.ty.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.ejb.QueryHints;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ty.EmployeeRepository.DepartmentRepository;
import com.ty.entity.Department;


public class TestThisMethod {
	private ApplicationContext context = null;
	private DepartmentRepository departmentRepository = null;
	private EntityManagerFactory entityManagerFactory = null;
	{
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		departmentRepository = context.getBean(DepartmentRepository.class);
		entityManagerFactory = context.getBean(EntityManagerFactory.class);
	}
	
	@Test
	public void test() {
		departmentRepository.findAll();
		departmentRepository.findAll();
	}
	/**
	 * 
	* @Function: TestThisMethod.java
	* @Description:JPA的二级缓存需要使用entityManager，并且利用query调用setHint打开二级缓存
	*
	* @param:描述1描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: water
	* @date: 2018年1月2日 上午11:38:17 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年1月2日       water           v1.0.0               修改原因
	 */
	@Test
	public void TestJpaSecondLevel(){
		String jpql = "from Department d";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery(jpql);
		List<Department> departments = query.setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
		
		Query query1 = entityManager.createQuery(jpql);
		List<Department> departments1 = query1.setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
	}
	
	@Test
	public void TestJpaSecondLevel1(){
		departmentRepository.getAll();
		departmentRepository.getAll();
	}
}
