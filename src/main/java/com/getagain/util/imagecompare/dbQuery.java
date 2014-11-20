package com.getagain.util.imagecompare;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class dbQuery {

	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.95:3306/ngsp_stag_28Apr14", "ngsp", "ngsp") ;
		Statement stmt = (Statement) conn.createStatement() ;
		String query = "select name, user_type from book" ;
		ResultSet rs = stmt.executeQuery(query) ;
		try {
	        while ( rs.next() ) {
	            int numColumns = rs.getMetaData().getColumnCount();
	            for ( int i = 1 ; i <= numColumns ; i++ ) {
	               // Column numbers start at 1.
	               // Also there are many methods on the result set to return
	               //  the column as a particular type. Refer to the Sun documentation
	               //  for the list of valid conversions.
	               System.out.print(rs.getObject(i) );
	               	               
	            }
	            System.out.println("");
	        }
	    } finally {
	        try { rs.close(); } catch (Throwable ignore) { /* Propagate the original exception
	        instead of this one that you may want just logged */ }
	            }
	}
	
}
