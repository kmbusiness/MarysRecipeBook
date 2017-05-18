/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Haba
 */
@ManagedBean(name = "RecipeEdit")
@RequestScoped
public class RecipeEdit implements Serializable{
    
    private static final long serialVersionUID = 2L;
    @ManagedProperty("#{param.b}")
    String b;
    ArrayList<Recipe> myList = new ArrayList<>();
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    Recipe thisRecipe = new Recipe();
    
    public RecipeEdit() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://mydbinstance.csbfyjmxbfgp.us-west-2.rds.amazonaws.com:3306/innodb?zeroDateTimeBehavior=convertToNull", "admin", "cecs493b");
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cook?zeroDateTimeBehavior=convertToNull", "root", "lovehurt");
            String sql = "select * from recipe";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Recipe rec = new Recipe();
                rec.setUserName(rs.getString("userName"));
                rec.setPushlishedDate(rs.getDate("pushlishedDate"));
                rec.setRecipeName(rs.getString("recipeName"));
                rec.setDescription(rs.getString("description"));
                rec.setSteps(rs.getString("steps"));
                rec.setRecipeID(rs.getInt("recipeID"));
                rec.setImage(rs.getString("Image"));
                rec.setPrepTime(rs.getString("prepTime"));
                rec.setCookTime(rs.getString("cookTime"));
                rec.setServings(rs.getInt("servings"));
                myList.add(rec);
            }
        } catch (ClassNotFoundException | SQLException e) {
        }
        
    }
    public List<Recipe> getMyList() {
        return myList;
    }
    
    public Recipe getThisRecipe() {
        for (int i = 0 ; i < myList.size(); i++) {
            if (myList.get(i).getRecipeName().compareTo(b) == 0) {
                thisRecipe = myList.get(i);
            }
        }
        System.out.println(myList + " STIRNGGGGGGGGGGGGGGGGGGGGGGGg");
        return thisRecipe;
    }
    
    public void setThisRecipe(Recipe p) {
        thisRecipe = p;
    }
    
    public void setB(String b) {
        this.b = b;
    }

    public String getB() {
        return b;
    }
    
    public String edit() {
        return "/Recipe/RecipeEdit.xhtml";
    }
    
    public String save() {
        return "/Recipe/RecipeView.xhtml";
    }
    
    public String cancel() {
        return  "/Recipe/RecipeView.xhtml";
    }
}
