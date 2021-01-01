package com.packtpub.gwtbook.samples.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.packtpub.gwtbook.samples.client.PageableDataService;

public class PageableDataServiceImpl extends RemoteServiceServlet
    implements PageableDataService {

  private ArrayList customerData = new ArrayList();

  public PageableDataServiceImpl() {
    super();
    loadData();
  }

  private void loadData() {
    try {
      Class.forName("org.hsqldb.jdbcDriver");
      Connection conn = DriverManager.getConnection(
          "jdbc:hsqldb:file:samplesdb", "sa", "");

      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM users");

      for (; rs.next();) {
        ArrayList customer = new ArrayList();
        customer.add((String) rs.getObject(2));
        customer.add((String) rs.getObject(3));
        customer.add((String) rs.getObject(4));
        customer.add((String) rs.getObject(5));
        customer.add((String) rs.getObject(6));
        customerData.add(customer);
      }
      st.execute("SHUTDOWN");
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public List getCustomerData(int startIndex, int numItems) {
    ArrayList customers = new ArrayList();
    for (int i = startIndex; i < (startIndex + numItems); i++) {
      if (i >= customerData.size())
      {
    	  break;
      }
      else
      {
    	customers.add((ArrayList) customerData.get(i));
      }
    }
    return customers;
  }
}