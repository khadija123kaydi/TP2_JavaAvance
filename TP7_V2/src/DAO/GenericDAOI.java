package DAO;
import java.util.List;

import Model.Employees;
public interface GenericDAOI<T> {
	public void addGeneric(T emp);
	public void modifyGeneric(T emp);
	public void deleteGeneric(int id);
	public List<Object[]> getAllGeneric();
	

	
}
