/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Owner;

import Recipe.Recipe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "OwnerManager")
@SessionScoped
public class OwnerManager {
    
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    List<String> owners = new ArrayList<String>();
    
    public OwnerManager() {
        try {
            owners = new ArrayList<String>();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://mydbinstance.csbfyjmxbfgp.us-west-2.rds.amazonaws.com:3306/innodb?zeroDateTimeBehavior=convertToNull", "admin", "cecs493b");
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cook?zeroDateTimeBehavior=convertToNull", "root", "lovehurt");
            String sql = "select * from owner";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                owners.add(rs.getString("userName"));
            }
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<String> owners) {
        this.owners = owners;
    }    
}
