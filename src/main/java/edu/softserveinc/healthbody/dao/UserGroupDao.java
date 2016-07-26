package edu.softserveinc.healthbody.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.softserveinc.healthbody.constants.DaoConstants;
import edu.softserveinc.healthbody.constants.DaoStatementsConstant.UserGroupQueries;
import edu.softserveinc.healthbody.db.ConnectionManager;
import edu.softserveinc.healthbody.entity.Group;
import edu.softserveinc.healthbody.entity.User;
import edu.softserveinc.healthbody.entity.UserGroup;
import edu.softserveinc.healthbody.exceptions.CloseStatementException;
import edu.softserveinc.healthbody.exceptions.DataBaseReadingException;
import edu.softserveinc.healthbody.exceptions.EmptyResultSetException;
import edu.softserveinc.healthbody.exceptions.JDBCDriverException;
import edu.softserveinc.healthbody.exceptions.QueryNotFoundException;

public final class UserGroupDao extends AbstractDao<UserGroup> {
	
	private static volatile UserGroupDao instance;

	
	private UserGroupDao() {
		init();
	}
	
	
	public static UserGroupDao getInstance(){
		if (instance == null) {
			synchronized (UserGroupDao.class) {
				if (instance == null) {
					instance = new UserGroupDao();
				}
			}
		}
		return instance;
	}

	@Override
	protected void init() {
		for (UserGroupQueries userGroupViewQueries:UserGroupQueries.values()) {
			sqlQueries.put(userGroupViewQueries.getDaoQuery(), userGroupViewQueries);
		}
		
	}

	

	@Override
	public UserGroup createInstance(final String[] args) {
		return new UserGroup(args[0] == null ? UUID.randomUUID().toString() : args[0],
							 args[1] == null ? UUID.randomUUID().toString() : args[1], 
							 args[2] == null ? UUID.randomUUID().toString() : args[2]);
	}


	@Override
	protected String[] getFields(final UserGroup entity) {
		List<String> fields = new ArrayList<>();
		fields.add(entity.getIdUserGroup());
		fields.add(entity.getIdUser());
		fields.add(entity.getIdGroup());
		return (String[]) fields.toArray();
	}
	
	
	public boolean addUserToGroup(final User user, final Group group)
			throws QueryNotFoundException, JDBCDriverException, DataBaseReadingException {
		boolean result = false;		
		result = insert(new UserGroup(UUID.randomUUID().toString(), user.getId(), group.getId()));
		return result;
	}
	
	public List<UserGroup> getUGbyId(final String id) 
			throws QueryNotFoundException, JDBCDriverException, DataBaseReadingException, 
			CloseStatementException, EmptyResultSetException {
		return getAllbyId(id);
	}
	
	public boolean createUserGroup (final User user, final Group group)
			throws QueryNotFoundException, JDBCDriverException, DataBaseReadingException {
		boolean result = false;
		String query = sqlQueries.get(DaoQueries.INSERT).toString();
			if (query == null) {
				throw new QueryNotFoundException(String.format(DaoConstants.QUERY_NOT_FOUND, DaoQueries.INSERT.name()));
			}
			try (PreparedStatement pst = ConnectionManager.getInstance().getConnection().prepareStatement(query)) {
				int i = 0;
				pst.setString(i++, UUID.randomUUID().toString());
				pst.setString(i++, user.getId());
				pst.setString(i++, group.getIdGroup());
					
				result = pst.execute();
			} catch (SQLException e) {
					throw new DataBaseReadingException(DaoConstants.DATABASE_READING_ERROR, e);
			}
		return result;
	}
	
	public boolean deleteByUserId (final String id) 
			throws QueryNotFoundException, JDBCDriverException, DataBaseReadingException {
		return deleteById(id);
	}
}
