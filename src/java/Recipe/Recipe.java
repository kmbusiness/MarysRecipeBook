package Recipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.util.Date;
import javax.servlet.http.Part;

@ManagedBean(name = "Recipe")
@RequestScoped
public class Recipe implements Serializable{

    private String userName;
    private Date pushlishedDate;
    private String recipeName;
    private String description;
    private String steps;
    private String Image;
    private int recipeID;
    private String replacement;
    private String desRep;
    private Part uploadImage;
    private File dropbocImage;
    private String prepTime;
    private String cookTime;
    private int servings;
    public int compareByName(Recipe r1) {
        if (r1.getRecipeName().compareToIgnoreCase(getRecipeName()) == 0)
            return 0;
        else if (r1.getRecipeName().compareToIgnoreCase(getRecipeName()) > 0)
            return 1;
        else
            return -1;
    }

    public File getDropbocImage() {
        return dropbocImage;
    }

    public void setDropbocImage(File dropbocImage) {
        this.dropbocImage = dropbocImage;
    }
    
    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getImage() {
        return Image;
    }
    
    public String getUserName() {
        return userName;
    }

    public Date getPushlishedDate() {
        return pushlishedDate;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getDescription() {
        return description;
    }

    public String getSteps() {
      return steps;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPushlishedDate(Date publishedDate) {
        this.pushlishedDate = publishedDate;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setDescription(String description) {
      replacement = description.replaceAll("\n", "<div></div>");
      this.description = description;
    }

    public void setSteps(String steps) {
      desRep = steps.replaceAll("\n", "<div></div>");  
      this.steps = steps;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
    
    public String toString() {
        return recipeName + " " + description;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String getDesRep() {
        return desRep;
    }

    public void setDesRep(String desRep) {
        this.desRep = desRep;
    }
    
    public Part getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(Part image) {
        this.uploadImage = image;
    }
//    public void doUpload()
//    {
//        try
//        {
//            InputStream in = uploadImage.getInputStream();
//            
//            File f = new File("/Users/johnkmnguyen/Desktop/CookImages/" + uploadImage.getSubmittedFileName());    
//            f.createNewFile();
//            FileOutputStream out = new FileOutputStream(f);
//            
//            byte[] buffer = new byte[1024];
//            int length;
//            
//            while((length = in.read(buffer)) > 0)
//            {
//                out.write(buffer, 0, length);
//            }
//            
//            out.close();
//            in.close();
//        }catch(Exception e)
//        {
//            System.out.println("LOL DOESNT WORK");
//            e.printStackTrace(System.out);
//        }
//    }
    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }
    
    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }
    
    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}
