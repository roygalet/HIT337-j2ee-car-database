CONNECT 'jdbc:derby://localhost:1527/Assignment1;create=true;user=roy;password=password;';

CREATE TABLE Cars(
    ID int GENERATED ALWAYS AS IDENTITY,
    UserName varchar(30),
    Make varchar(30),
    Model varchar(30),
    ProductionYear int,
    CONSTRAINT Cars_PK PRIMARY KEY (ID)
);

INSERT INTO CARS(UserName, Make, Model, ProductionYear) VALUES('Admin','Ford','Territory',2005);
