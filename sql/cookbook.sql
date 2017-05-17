CREATE TABLE ingredient (
      recipeID int, 
      rName VARCHAR(50), 
      amount DOUBLE, 
      unit VARCHAR(20)
);

CREATE TABLE owner (
      firstName VARCHAR(25), 
      lastName VARCHAR(25), 
      email VARCHAR(50), 
      userName VARCHAR(30) NOT NULL PRIMARY KEY, 
      password VARCHAR(25)
);

CREATE TABLE recipe (
        userName VARCHAR(30), 
        pushlishedDate DATE, 
        recipeName VARCHAR(30), 
        description VARCHAR(10000), 
        steps VARCHAR(10000), 
        recipeID int NOT NULL PRIMARY KEY, 
        Image VARCHAR(1000),
        prepTime VARCHAR(30),
        cookTime VARCHAR(30),
        servings INT
);


CREATE TABLE recType(
        typeName VARCHAR(100) NOT NULL,
        recipeID int NOT NULL,

        --Primary Key
        Constraint type_pk
        Primary Key (typeName, recipeID),
        --Foreign Key
        Constraint type_recipe_fk
        Foreign Key (recipeID)
        References recipe (recipeID)
);



INSERT INTO owner (firstName, lastName, email, userName, password)
VALUES ('John', 'Nguyen', 'john@gmail.com', 'john', '123');
