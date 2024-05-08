package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


/*
 * Classe responsavel por carregar no DepartmentController 
 * a lista de departamentos da entidade Department
 */
public class DepartmentService {
	
	//Cria uma referencia para a interface DepartmentDao
	//O Daofactory na verdade instancial a classe de conexao SellerDaoJDBC 
	//Essa classe ja implementa todos os metodosdefinidos pela interface DepartmentDao
	//E ja realiza a instancia a conexao com banco de dados na classe DB
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	
	//Retorna ima lista de departamentos
	public List<Department> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Department obj) {
		if(obj.getId() == null) {//Nao nao tiver id ainda -> entao e uma insercao
			dao.insert(obj);
		}else {//Se ja tiver
			dao.update(obj);
		}
		
	}
	
	
}
