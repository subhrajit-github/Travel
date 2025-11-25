package travel.travelInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import travel.payment_flight.Payment;

public class TravelServiceDemo {
private static Connection con;
	
	public static String url="jdbc:postgresql://localhost:5432/Travel?user=postgres&password=123";
	static {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver loaded");
			
	   con=DriverManager.getConnection(url);
			System.out.println("connection established");
			
			con.setAutoCommit(false);
			
			//passenger
			String sql="INSERT into Passenger values(103,'Subhra',21,'BBSR','DEL')";
			Statement stmt=con.createStatement();
			stmt.execute(sql);	
			
			//flight
			String sql1="INSERT into flight values(5130,'AIR_INDIA','BBSR','DELHI','9:30AM_IST','11:30AM_IST')";
			stmt.execute(sql1);
			//SAVEPOINT
			Savepoint savepoint=con.setSavepoint();
			
			//payment
			String sql2="INSERT into payment values(3421,'Subhra','4500')";
			stmt.execute(sql2);
			if(Payment.isSuccess()) {
				//stmt.execute(sql1);
				//stmt.execute(sql2);
				con.commit();
			}
			else{//Connection is not closed
				con.rollback(savepoint);
				con.commit();
				System.out.println("Transaction failed");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String [] args) {
		
	}
}
