package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;


/*
 * Classe responsavel por carregar no DepartmentController 
 * a lista de departamentos da entidade Department
 */
public class DepartmentService {
	
	//Retorna ima lista de departamentos
	public List<Department> findAll(){
		List<Department> list = new ArrayList<Department>();
		list.add(new Department(1, "Books"));
		list.add(new Department(2, "Computers"));
		list.add(new Department(1, "Electronics"));
		return list;
	}
}
