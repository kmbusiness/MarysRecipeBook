/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recipe;

import Ingredient.Ingredient;
import Type.Type;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Haba
 */
@ManagedBean(name = "RecipeManager")
@SessionScoped
public class RecipeManager implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * @param book what ever
     */
    String book;
    /**
     * @param search Searching string
     */
    String search = "";
    int max = 0, rows = 0, myIndex = -1;
    List<Recipe> recs = new ArrayList<>();
    List<Recipe> recsByName = new ArrayList<>();
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    Recipe thisRecipe = new Recipe();
    Recipe newRecipe = new Recipe();
    String myName = "";
    ArrayList<String> asd = new ArrayList<String>();
    List mySearch = new ArrayList<Recipe>();
    List myType = new ArrayList<Recipe>();
    String action = "";
    boolean view = false, br = false;
    List<Ingredient> myIngre = new ArrayList<Ingredient>();
    List<Type> maiType = new ArrayList<Type>();
    String ingreName, ingreUnit;
    String typeName;
    double ingreAmount;
    String sortby = "0";
    int myScale = 0;
    int autoID = 0;
    double myRatio = 0;
    boolean first = true;

    /**
     * Creates a new instance of Recipe
     */
    public RecipeManager() {
        max = 0;
        rows = 0;
        Recipe thisRecipe = new Recipe();
        newRecipe = new Recipe();
        myName = "";
        ResultSet rs = null;
       
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yeet?zeroDateTimeBehavior=convertToNull", "root", "1234");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecipeManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
             init();
    }

    public List<Type> getMaiType() {
        if (first)
            initializeMyType();
        System.out.println("OMGE WEWEAMOP");
        return maiType;
    }

    public void setMaiType(List<Type> maiType) {
        this.maiType = maiType;
    }

    
    public List getMyType() {
        return myType;
    }

    public void setMyType(List myType) {
        this.myType = myType;
    }
    
    public void initializeMyType() {
        maiType = new ArrayList<Type>();
        try {
            String sql = "select * from recType WHERE recipeID=" + thisRecipe.getRecipeID();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Type typ = new Type();
                typ.setType(rs.getString("typeName"));
                typ.setRecipeID(rs.getInt("recipeID"));
                maiType.add(typ);
                System.out.println("LOL YES");
            }
        } catch (SQLException e) {
        }
    }
    
    public String deleteTypee(Type typ) {
        try {
            
            String sql = "delete from recType where recipeID=? && typeName=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, typ.getRecipeID());
            ps.setString(2, typ.getType());
            int i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        maiType.remove(typ);
        return null;
    }
    
    public String insertType() {
        Type typ = new Type(thisRecipe.getRecipeID(), typeName);
        maiType.add(typ);
        typeName = "";
        return null;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    
    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    
    public int getMyScale() {
        return myScale;
    }

    public void setMyScale(int myScale) {
        this.myScale = myScale;
    }

    public double getMyRatio() {
        return myRatio;
    }

    public void setMyRatio(double myRatio) {
        this.myRatio = myRatio;
    }


    public List<Recipe> getRecsByName() {
        recsByName = new ArrayList<Recipe>();
        for (int i = 0; i < recs.size(); i++) {
            recsByName.add(recs.get(i));
        }
        int j;
        boolean flag = true;   // set flag to true to begin first pass
        Recipe temp;   //holding variable
        while (flag) {
            flag = false;    //set flag to false awaiting a possible swap
            for (j = 0; j < recsByName.size() - 1; j++) {
                if (recsByName.get(j).compareByName(recsByName.get(j + 1)) < 0) // change to > for ascending sort
                {
//                    temp = recsByName.get(j);                //swap elements
                    Collections.swap(recsByName, j, j + 1);
//                    recsByName.set(j, temp);
                    flag = true;              //shows a swap occurred
                }
            }
        }
        return recsByName;
    }

    public void setRecsByName(List<Recipe> recsByName) {
        this.recsByName = recsByName;
    }

    public String getSortby() {
        return sortby;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }

    public String getIngreName() {
        return ingreName;
    }

    public void setIngreName(String ingreName) {
        this.ingreName = ingreName;
    }

    public String getIngreUnit() {
        return ingreUnit;
    }

    public void setIngreUnit(String ingreUnit) {
        this.ingreUnit = ingreUnit;
    }

    public double getIngreAmount() {
        return ingreAmount;
    }

    public void setIngreAmount(double ingreAmount) {
        this.ingreAmount = ingreAmount;
    }

    public List<Ingredient> getMyIngre() {

        if (first) {
            initializeIngredient();
        }

        return myIngre;
    }
    public void initializeIngredient() {
        myIngre = new ArrayList<Ingredient>();
        try {
            String sql = "select * from ingredient WHERE recipeID=" + thisRecipe.getRecipeID();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ingredient ingre = new Ingredient();
                ingre.setrName(rs.getString("rName"));
                ingre.setAmount(rs.getDouble("amount"));
                ingre.setUnit(rs.getString("unit"));
                myIngre.add(ingre);
                System.out.println("WTF LOL" );
            }
        } catch (SQLException e) {
        }
    }
    
    public void setMyIngre(List<Ingredient> myIngre) {
        this.myIngre = myIngre;
    }

    public String getAction() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        action = params.get("book");
        for (int i = 0; i < recs.size(); i++) {
            if (recs.get(i).getRecipeName().compareTo(action) == 0) {
                thisRecipe = recs.get(i);
            }
        }
        myScale = thisRecipe.getServings();
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List getMySearch() {
        return mySearch;
    }

    public void setMySearch(List mySearch) {
        this.mySearch = mySearch;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Recipe getNewRecipe() {
        return newRecipe;
    }

    public void setNewRecipe(Recipe r) {
        newRecipe = r;
    }

    public int getMax() {
        max = 0;
        return getRecs().size();
    }

    public int getRows() {
        rows = 0;
        return (int) recs.size();
    }

    public List<Recipe> getRecs() {
        if (sortby.equals("0"))
            return recs;
        else if (sortby.equals("1"))
            return getRecsByName();
        return null;
    }

    public String getView(String name) {
        return "/Recipe/RecipeView";
    }

    public String getMyName() {
        return myName;
    }

    public Recipe getThisRecipe() {
        if (view) {
            getAction();
        }
        return thisRecipe;
    }

    public void setThisRecipe(Recipe r) {
        thisRecipe = r;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getBook() {
        return book;
    }

    public int getMyIndex() {
        return myIndex;
    }

    public ArrayList<String> getAsd() {
        return asd;
    }

    public String indexview() {
        view = true;
        first = true;
        
        return "/Recipe/RecipeView";
    }
    
    public String publicview() {
        view = true;
        first = true;
        return "/Recipe/PublicView";
    }
    

    public String editview() {
        return "/Recipe/RecipeView";
    }

    public String edit() {
        view = false;
        first = false;
        return "/Recipe/RecipeEdit";
    }

    public String save() throws InterruptedException {
        int temp = 0;
        for (int i = 0; i < recs.size(); i++) {
            if (recs.get(i).getRecipeID() > temp)
                temp = recs.get(i).getRecipeID();
        }
        autoID = temp + 1;
        try
        {
            if(newRecipe.getUploadImage() != null) {
                InputStream in = newRecipe.getUploadImage().getInputStream();

                File f = new File("/Users/johnkmnguyen/Desktop/Cookbook_v7 new n improved/RecipeCollection_2/web/"
                        + newRecipe.getUploadImage().getSubmittedFileName());
                f.createNewFile();
                FileOutputStream out = new FileOutputStream(f);

                byte[] buffer = new byte[1024];
                int length;

                while((length = in.read(buffer)) > 0)
                {
                    out.write(buffer, 0, length);
                }

                out.close();
                in.close();
            }
        }catch(IOException e)
        {
            System.out.println("LOL DOESNT WORK");
            e.printStackTrace(System.out);
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String sql = "INSERT INTO recipe (userName,pushlishedDate,recipeName,description,steps,recipeID,image,prepTime,cookTime,servings) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, newRecipe.getUserName());
            Date d = new Date();
            
            java.sql.Date dt = java.sql.Date.valueOf(fmt.format(d));
            thisRecipe.setPushlishedDate(d);
            ps.setDate(2, dt);
            ps.setString(3, newRecipe.getRecipeName());
            ps.setString(4, newRecipe.getDescription());
            ps.setString(5, newRecipe.getSteps());
            ps.setInt(6, autoID);
            if (newRecipe.getUploadImage() != null)
                ps.setString(7, newRecipe.getUploadImage().getSubmittedFileName());
            else
                ps.setString(7, "Default.png");
            ps.setString(8, newRecipe.getPrepTime());
            ps.setString(9, newRecipe.getCookTime());
            ps.setInt(10, newRecipe.getServings());
            int i = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Waiting for the recipe to be added to DB
        //System.wait(1000);
        
        insertIngreFinal(autoID);
        insertTypeFinal(autoID);
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yeet?zeroDateTimeBehavior=convertToNull", "root", "1234");
//            String sql = "SELECT * FROM recipe WHERE recipeName = " + newRecipe.getRecipeName();
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                insertIngreFinal(rs.getInt("recipeID"));
//                insertTypeFinal(rs.getInt("recipeID"));
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            /* Lazy */
//        }
        System.out.println(thisRecipe.getRecipeID());
        
        newRecipe = new Recipe();
        init();
//        newRecipe = new Recipe();
         return "/faces/home?faces-redirect=true";
    }

    public String update() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String sql = "UPDATE Recipe "
                    + "SET recipeName=?,description=?,steps=?,image=?,prepTime=?,cookTime=?,servings=? "
                    + "WHERE recipeID=" + thisRecipe.getRecipeID();
            ps = con.prepareStatement(sql);
            ps.setString(1, thisRecipe.getRecipeName());
            ps.setString(2, thisRecipe.getDescription());
            ps.setString(3, thisRecipe.getSteps());
            ps.setString(4, thisRecipe.getImage());
            ps.setString(5, thisRecipe.getPrepTime());
            ps.setString(6, thisRecipe.getCookTime());
            ps.setInt(7, thisRecipe.getServings());
            int i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            /* Lazy */
        }
        editIngreUpdate();
        editTypeUpdate();
        init();
        return "/Recipe/RecipeView";
    }
    
    public void editIngreUpdate() {
        ArrayList<String> tempIngreName = new ArrayList<String>();
        try {
            String sql = "SELECT * FROM ingredient WHERE recipeID = " + thisRecipe.getRecipeID();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                tempIngreName.add(rs.getString("rName"));
            }
            for (int i = 0; i < myIngre.size(); i++) {
                if (!tempIngreName.contains(myIngre.get(i).getrName())) {
                    sql = "INSERT INTO ingredient (recipeID, rName, amount, unit) VALUES(?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, thisRecipe.getRecipeID());
                    ps.setString(2, myIngre.get(i).getrName());
                    ps.setDouble(3, myIngre.get(i).getAmount());
                    ps.setString(4, myIngre.get(i).getUnit());
                    int j = ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void editTypeUpdate() {
        ArrayList<String> tempTypeName = new ArrayList<String>();
        try {
            String sql = "SELECT * FROM recType WHERE recipeID = " + thisRecipe.getRecipeID();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                tempTypeName.add(rs.getString("typeName"));
            }
            for (int i = 0; i < maiType.size(); i++) {
                if (!tempTypeName.contains(maiType.get(i).getType())) {
                    sql = "INSERT INTO recType (typeName, recipeID) VALUES(?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, maiType.get(i).getType());
                    ps.setInt(2, thisRecipe.getRecipeID());
                    int j = ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String searching() {
        mySearch = new ArrayList<Recipe>();
        for (int i = 0; i < recs.size(); i++) {
            if (recs.get(i).getRecipeName().toLowerCase().contains(search.toLowerCase())) {
                mySearch.add(recs.get(i));
            }
        }
        try {
            String sql = "select * from ingredient WHERE rName=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, search);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < recs.size(); i++) {
                    if (recs.get(i).getRecipeID() == (rs.getInt("recipeID"))) {
                        mySearch.add(recs.get(i));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "select * from recType WHERE typeName=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, search);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < recs.size(); i++) {
                    if (recs.get(i).getRecipeID() == (rs.getInt("recipeID")) && !mySearch.contains(recs.get(i))) {
                        mySearch.add(recs.get(i));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        search = "";
        return "/PrivateSearch.xhtml?faces-redirect=true";
    }
    
    public String publicSearching() {
        mySearch = new ArrayList<Recipe>();
        for (int i = 0; i < recs.size(); i++) {
            if (recs.get(i).getRecipeName().toLowerCase().contains(search.toLowerCase())) {
                mySearch.add(recs.get(i));
            }
        }
        try {
            String sql = "select * from ingredient WHERE rName=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, search);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < recs.size(); i++) {
                    if (recs.get(i).getRecipeID() == (rs.getInt("recipeID"))) {
                        mySearch.add(recs.get(i));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "select * from recType WHERE typeName=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, search);
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < recs.size(); i++) {
                    if (recs.get(i).getRecipeID() == (rs.getInt("recipeID")) && !mySearch.contains(recs.get(i))) {
                        mySearch.add(recs.get(i));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        search = "";
        return "/search.xhtml?faces-redirect=true";
    }
    
    public String delete() {
        deleteIngre(thisRecipe.getRecipeID());
        deleteType(thisRecipe.getRecipeID());
      try {
            String sql = "delete from recipe where recipeName=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, thisRecipe.getRecipeName());
            int i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recs.remove(thisRecipe);
        return "/home?faces-redirect=true";
    }
    // Delete ingredient when delete recipe
    public void deleteIngre(int ID) {
        try {
            String sql = "delete from ingredient where recipeID=" + ID;
            ps = con.prepareStatement(sql);
            int i = ps.executeUpdate();
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }
    // Delete type when delete recipe
    public void deleteType(int ID) {
        try {
            String sql = "delete from recType where recipeID=" + ID;
            ps = con.prepareStatement(sql);
            int i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String deleteIngree(Ingredient ing) {
        try {
            String sql = "delete from ingredient where rName=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, ing.getrName());
            int i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myIngre.remove(ing);
        return null;
    }

    public String insertIngre() {
        Ingredient iningre = new Ingredient(ingreName, ingreAmount, ingreUnit);
        myIngre.add(iningre);
        System.out.println(myIngre.size());
        ingreAmount = 0;
        ingreUnit = "";
        ingreName = "";
        return null;
    }
    // CALL THIS SHIT IN SAVE() AND USE THIS RECIPE TO ADD ALL INGREDIENTS TO DB
    // DO THE SAME WITH TYPES!!!!!
    public void insertIngreFinal(int ID) {
        for (int i = 0; i < myIngre.size(); i++) {
            try {
                String sql = "INSERT INTO ingredient (recipeID, rName, amount, unit) VALUES(?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, ID);
                ps.setString(2, myIngre.get(i).getrName());
                ps.setDouble(3, myIngre.get(i).getAmount());
                ps.setString(4, myIngre.get(i).getUnit());
                int j = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void insertTypeFinal(int ID) {
        for (int i = 0; i < maiType.size(); i++) {
            try {
                String sql = "INSERT INTO recType (typeName, recipeID) VALUES(?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, maiType.get(i).getType());
                ps.setInt(2, ID);
                int j = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List sortByID() {
        Collections.sort(recs, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                if (r1.getRecipeID() < r2.getRecipeID())
                    return -1;
                else if (r1.getRecipeID() == r2.getRecipeID())
                    return 0;
                else
                    return 1;
            }
        });
        return recs;
    }

    public String scale() {
        initializeIngredient();
        first = false;
        myRatio = (double)myScale/thisRecipe.getServings();
        for(int i = 0; i < myIngre.size(); i++) {
            myIngre.get(i).setAmount(myIngre.get(i).getAmount()*myRatio);
        }
        return "/Recipe/RecipeView.xhtml";
    }
    
    public String create() {
        myIngre = new ArrayList<Ingredient>();
        maiType = new ArrayList<Type>();
        first = false;
        return "faces/Recipe/RecipeCreate.xhtml";
    }
    
    public String type(int maiType, int bak) {
        myType = new ArrayList<Recipe>();
        try {
            String sql = "select * from recType WHERE typeName=?";
            ps = con.prepareStatement(sql);
            if (maiType == 1)
                ps.setString(1, "breakfast");
            else if (maiType == 2)
                ps.setString(1, "lunch");
            else if (maiType == 3)
                ps.setString(1, "dinner");
            else
                ps.setString(1, "dessert");
            rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < recs.size(); i++) {
                    if (recs.get(i).getRecipeID() == (rs.getInt("recipeID"))) {
                        myType.add(recs.get(i));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (bak == 1)
            return "/type.xhtml?faces-redirect=true";
        else
            return "/PrivateType.xhtml?faces-redirect=true";
    }
    public void init() {
        recs = new ArrayList<Recipe>();
        try {
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
                recs.add(rec);
            }
        } catch (SQLException e) {
        }
    }
}
