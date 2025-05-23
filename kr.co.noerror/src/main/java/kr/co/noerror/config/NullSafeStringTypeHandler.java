//package kr.co.noerror.config;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//
//@MappedTypes(String.class)
//@MappedJdbcTypes(JdbcType.VARCHAR)
//public class NullSafeStringTypeHandler extends BaseTypeHandler<String>  {
//
//	@Override
//	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
//			throws SQLException {
//		if (parameter == null) {
//	            ps.setNull(i, Types.VARCHAR); //null이면 직접 처리
//        } else {
//            ps.setString(i, parameter);
//        }
//		
//	}
//
//	@Override
//	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
//	    return rs.getString(columnName);
//	}
//
//	@Override
//	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//		return rs.getString(columnIndex);
//	}
//
//	@Override
//	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//		return cs.getString(columnIndex);
//	}
//
//}
