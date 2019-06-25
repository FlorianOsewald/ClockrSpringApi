package com.osewald.springrest.h2.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import com.osewald.springrest.h2.model.Timestamp;

public class TimeStampType implements UserType {

	@Override
	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] durations, SharedSessionContractImplementor sessions, Object owner)
			throws HibernateException, SQLException {
		int hours = rs.getInt(durations[0]);

		if (rs.wasNull()) {
			return null;
		}
		int minutes = rs.getInt(durations[1]);

		Timestamp timestamp = new Timestamp(hours, minutes);

		return timestamp;

	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		System.out.println("In NullSafeSet");
		System.out.println("Object is null? " + Objects.isNull(value));
		if (Objects.isNull(value)) {
			//Fake it til you make it
			java.util.Date dt = new java.util.Date();
			int hours = dt.getHours();
			int minutes = dt.getMinutes();
			st.setInt(index, hours);
			st.setInt(index + 1 , minutes);
		} else {
			Timestamp timestamp = (Timestamp) value;
			st.setInt(index, timestamp.getHours());
			st.setInt(index + 1, timestamp.getMinutes());
		}

	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class returnedClass() {
		return Timestamp.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.INTEGER, Types.INTEGER };
	}

}
