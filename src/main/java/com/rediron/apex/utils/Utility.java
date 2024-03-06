package com.rediron.apex.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

public class Utility {

	public static Connection conn = null;
	public static ResultSet rs;
	public static Statement st;;

	public static String decodestring(String password) {

		byte[] decodestring = Base64.decodeBase64(password);
		return (new String(decodestring));

	}

	public static void encodedString(String password1) {

		byte[] encodestring = Base64.encodeBase64(password1.getBytes());

		System.out.println(new String(encodestring));

	}

	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/com/rediron/apex/config/config.properties");
		prop.load(fis);
		return prop.getProperty(key);

	}

	public static String getCurrentDateTime() {

		DateFormat dateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		Calendar cal = Calendar.getInstance();
		String time = "" + dateFormat.format(cal.getTime());
		return time;
	}

	public static String getCurrentDate() {
		return getCurrentDateTime().substring(0, 11);
	}

	public static void main(String s[]) {
		System.out.println(getCurrentDate());

	}

	public static String dateFormater(String date) throws ParseException {
		Date date1 = new SimpleDateFormat("MM/dd/yy").parse(date);
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String strDate1 = formatter.format(date1);
		return strDate1;
	}

	public static List<String> getDataFromDatabase(String query, String columnName) throws SQLException {
		String data = "";
		List<String> DBList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(getGlobalValue("JDBCURL"), getGlobalValue("DBUsrName"),
					getGlobalValue("DBPASS"));
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				data = rs.getString(columnName);
				DBList.add(data);
			}
			return DBList;
		} catch (IOException e) {

			return null;
		}

		finally {
			try {
				if (conn != null) {
					conn.close();
					System.out.println("connection is closed ");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static String executeQuery(String query, String columnName) throws Exception {
		String data = "";
		List<String> DBData = getDataFromDatabase(query, columnName);
		try {
			data = DBData.get(0);
		} catch (ArrayIndexOutOfBoundsException e) {

			e.printStackTrace();
		}
		return data;
	}

	public static void executeandcommit(String query) throws Exception {
		try {
			conn = DriverManager.getConnection(getGlobalValue("JDBCURL"), getGlobalValue("DBUsrName"),
					getGlobalValue("DBPASS"));
			st = conn.createStatement();
			st.execute(query);

		} catch (IOException e) {

		}

		finally {
			try {
				if (conn != null) {
					conn.close();
					System.out.println("connection is closed ");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * This method will return the list of column name from data base
	 * 
	 * @Author Kapil
	 * @return
	 * @throws Exception
	 */
//	public static List<String> getDBTextList(String query, String columnName) throws Exception {
//		List<String> colName = new ArrayList<>();
//		Statement st = getDataFromDatabase();
//		ResultSet rs = st.executeQuery(query);
//		while (rs.next()) {
//			String data = rs.getString(columnName);
//			colName.add(data);
//		}
//		return colName;
//	}

	/**
	 * This method will return the list of flags from DBMS
	 * 
	 * @Author Kapil
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getFlagValueFromDBMS(String query) throws Exception {
		List<Object> flag = new ArrayList<>();
		try (Connection c = DriverManager.getConnection(getGlobalValue("JDBCURL"), getGlobalValue("DBUsrName"),
				getGlobalValue("DBPASS")); Statement s = c.createStatement()) {
			try {
				// First, we have to enable the DBMS_OUTPUT. Otherwise,
				// all calls to DBMS_OUTPUT made on our connection won't
				// have any effect.
				s.executeUpdate("begin dbms_output.enable(); end;");

				// Now, this is the actually interesting procedure call
				s.executeUpdate(query);

				// After we're done with our call(s), we can proceed to
				// fetch the SERVEROUTPUT explicitly, using
				// DBMS_OUTPUT.GET_LINES
				try (CallableStatement call = c.prepareCall("declare "
//			        		+"v_Data       DBMS_OUTPUT.CHARARR;"
//			        		 +  "v_NumLines   NUMBER;"
						+ "  num integer := 1000;" + "begin " + "  dbms_output.get_lines(?, num);" + "end;")) {
					call.registerOutParameter(1, Types.ARRAY, "DBMSOUTPUT_LINESARRAY");
					call.execute();

					Array array = null;
					try {
						array = call.getArray(1);
						// Stream.of((Object[]) array.getArray()).forEach(System.out::println);
						Object[] s1 = (Object[]) array.getArray();

						for (Object s2 : s1) {

							// System.out.println(s2);
							flag.add(s2);
						}
					}

					finally {
						if (array != null)
							array.free();
					}
				}
			}

			// Don't forget to disable DBMS_OUTPUT for the remaining use
			// of the connection.
			finally {
				s.executeUpdate("begin dbms_output.disable(); end;");
			}
		}

		return flag;
	}

	public static String getRandomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(2);
		return generatedString;
	}

	public static String getRandomNumber() {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		return number;
	}

	public static String roundOffString(String value) {
		return String.format("%.2f", value);
	}

	public static int parseToInteger(String value) {
		return Integer.parseInt(value);
	}
}
