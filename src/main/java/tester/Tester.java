package tester;

import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Tester {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(new Employee("xa12tt", "Kurt", "Wonnegut", new BigDecimal(335567)));
			em.persist(new Employee("hyu654", "Hanne", "Olsen", new BigDecimal(435867)));
			em.persist(new Employee("uio876", "Jan", "Olsen", new BigDecimal(411567)));
			em.persist(new Employee("klo999", "Irene", "Petersen", new BigDecimal(33567)));
			em.persist(new Employee("jik666", "Tian", "Wonnegut", new BigDecimal(56567)));
			em.getTransaction().commit();

			//Complete all these small tasks. Your will find the solution to all, but the last,
			//In this document: https://en.wikibooks.org/wiki/Java_Persistence/JPQL#JPQL_supported_functions
			//1) Create a query to fetch all employees with a salary > 100000 and print out all the salaries
			List<Employee> employees = em.createQuery("SELECT e FROM Employee e WHERE e.salary > 100000", Employee.class)
				.getResultList();

			employees.forEach(e -> {
				System.out.println("Employee:" + e.getSalary());
			});

			//= //2) Create a query to fetch the employee with the id "klo999" and print out the firstname
			Employee employee = em.find(Employee.class, "klo999");
			System.out.println("Employee with id klo999: " + employee.getFirstName());

			//3) Create a query to fetch the highest salary and print the value
			BigDecimal employeeMax = (BigDecimal) em.createQuery("SELECT MAX(e.salary) FROM Employee e").getSingleResult();
			System.out.println("Max salary employee: " + employeeMax);

			//4) Create a query to fetch the firstName of all Employees and print the names
			List<String> names = em.createQuery("SELECT e.firstName FROM Employee e").getResultList();
			names.forEach(name -> System.out.println("Name: " + name));

			//5 Create a query to calculate the number of employees and print the number
			long employeeAmount = (long) em.createQuery("SELECT COUNT(e) FROM Employee e").getSingleResult();
			System.out.println("Amount of employess: " + employeeAmount);

			//6 Create a query to fetch the Employee with the higest salary and print all his details
			List<Employee> highSalary = em.createQuery("SELECT e FROM Employee e ORDER BY e.salary", Employee.class)
				.getResultList();
			System.out.println("Highest salary person: " + highSalary.get(highSalary.size() - 1));
			//There has to be a better way :D
		} finally {
			em.close();
			emf.close();
		}
	}

}
