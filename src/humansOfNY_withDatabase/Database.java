package humansOfNY_withDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to access to the database
 */
public class Database {

	private Connection conn = null;
	private boolean connectedToDatabase = false;
	private Statement statement;
	private ResultSet rows;
	private String query;

	public Database() {
		try {

			conn = DriverManager.getConnection("jdbc:derby:Images", null, null);
			connectedToDatabase = true;
			statement = conn.createStatement();

			String selectStuff = "select url from picture";

			rows = statement.executeQuery(selectStuff);

		}

		catch (SQLException ex) {

			System.out.println("SQLException: " + ex.getMessage());

			System.out.println("VendorError: " + ex.getErrorCode());
		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * setQuery is used to change the query of the database. Also throws
	 * SQLException
	 * 
	 * @param: query The query passed in through control panel.
	 */
	public void setQuery(String query) throws SQLException,
			IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");
		this.query = query;
		// specify query and execute it
		rows = statement.executeQuery(query);

	} // end method setQuery

	/**
	 * getImages creates an array of specified criteria
	 * 
	 * @return: return the array to be used to retrieve the image urls
	 */

	public String[] getImages() {

		int rowcount = 0;
		try {

			while (rows.next()) {
				rowcount++;
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		String[] images = new String[rowcount];

		try {
			rows = statement.executeQuery(query);
			int i = 0;
			while (rows.next()) {

				images[i] = rows.getString("URL");
				i++;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return images;

	}

}
