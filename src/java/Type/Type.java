package Type;

/**
 *
 * @author johnkmnguyen
 */
public class Type 
{
    private int recipeID;
    private String type;

    public Type() {}
    public Type(int id, String t) {
        this.recipeID = id;
        this.type = t;
    }
    
    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
}

