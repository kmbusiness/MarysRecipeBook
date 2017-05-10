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
        recipeID int NOT NULL PRIMARY KEY auto_increment, 
        Image VARCHAR(55),
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





INSERT INTO recipe (userName, pushlishedDate, recipeName, description, steps, Image, prepTime, cookTime, servings) 
VALUES ('test', '2015-03-01', 'Beef Broccoli', 'This is a beef broccoli dish that is from Korea origin.', '1. dsfdnfaskjnjksandsjndnsakjdnsak 2. dfhdsfjhdsjkfhdkjshfdjshfksdhfksdj', 'beef-broccoli.JPG', '30 mins', '10 mins', '2');
INSERT INTO recipe (userName, pushlishedDate, recipeName, description, steps, Image, prepTime, cookTime, servings) 
VALUES ('test', '2016-03-01', 'Chicken Noodle Soup', 'This is a chicken noodle dish that is from Korea origin.', '1. dsfdnfaskjnjksandsjndnsakjdnsak 2. dfhdsfjhdsjkfhdkjshfdjshfksdhfksdj', 'chicken-noodle.JPG', '4 mins', '5 mins', '3');
INSERT INTO recipe (userName, pushlishedDate, recipeName, description, steps, Image, prepTime, cookTime, servings) 
VALUES ('test', '2017-03-01', 'Chicken Nuggets', 'This is a chicken nugget dish that is from American origin.', '1. dsfdnfaskjnjksandsjndnsakjdnsak 2. dfhdsfjhdsjkfhdkjshfdjshfksdhfksdj', 'chicken-nuggets.JPG', '1 min', '1 hour', '10');
INSERT INTO recipe (userName, pushlishedDate, recipeName, description, steps, Image, prepTime, cookTime, servings) 
VALUES ('test', '2017-01-01', 'Corn Bread', 'This is a corn bread dish that is from American origin.', '1. dsfdnfaskjnjksandsjndnsakjdnsak 2. dfhdsfjhdsjkfhdkjshfdjshfksdhfksdj', 'corn-bread.JPG', '30 mins', '10 mins', '2');
INSERT INTO recipe (userName, pushlishedDate, recipeName, description, steps, Image, prepTime, cookTime, servings) 
VALUES ('test', '2013-04-01', 'Pizza', 'This is a pizza dish that is from Italian origin.', '1. dsfdnfaskjnjksandsjndnsakjdnsak 2. dfhdsfjhdsjkfhdkjshfdjshfksdhfksdj', 'pizza.JPG', '40 mins', '2 hours', '11');
INSERT INTO recipe (userName, pushlishedDate, recipeName, description, steps, Image, prepTime, cookTime, servings) 
VALUES ('test', '2013-04-01', 'Chocolate Cake', 'asdfsdfsdfssda', '1.sdfsdfsdfdsf 2.dsfdsfsdfdsdfs 3.sdfsdfdsfsdfsdfsdfsdf', 'chocolate.JPG', '30 mins', '10 mins', '2');
INSERT INTO recipe (userName, pushlishedDate, recipeName, description, steps, Image, prepTime, cookTime, servings) 
VALUES ('test', '2012-11-03', 'Fresh Lobster Dish', 'RAWRRRRRRRRRRRR', '1. qeqwsdfsfsfds12321', 'lobster.JPG', '50 mins', '10 mins', '6');
INSERT INTO recipe (userName, pushlishedDate, recipeName, description, steps, Image, prepTime, cookTime, servings)  
VALUES ('test', '2010-03-03', 'Bulgogi Special', 'This is so delicious!', '1. 23212312312312313213', 'bulgogi.JPG', '3 mins', '20 mins', '7');


insert into recType (typeName, recipeID)
values('Hot', 1);

insert into recType (typeName, recipeID)
values('Cold', 1);
insert into recType (typeName, recipeID)
values('Breakfast', 1);
insert into recType (typeName, recipeID)
values('Lunch', 2);
insert into recType (typeName, recipeID)
values('Dinner', 3);
insert into recType (typeName, recipeID)
values('Breakfast', 4);
insert into recType (typeName, recipeID)
values('Lunch', 5);
insert into recType (typeName, recipeID)
values('Dinner', 6);
insert into recType (typeName, recipeID)
values('Breakfast', 7);
insert into recType (typeName, recipeID)
values('Lunch', 8);