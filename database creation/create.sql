CREATE table Users(
	UserID varchar(40) PRIMARY KEY,
    FirstName varchar(255) NOT NULL,
    LastName varchar(255) NOT NULL,
    Email varchar(255) NOT NULL,
    PasswordHash varchar(255) NOT NULL,
    PasswordSalt varchar(255) NOT NULL,
    regComplete boolean DEFAULT False
);

ALTER TABLE Users
ADD regComplete boolean DEFAULT False;

CREATE table roles(
	RoleID int auto_increment PRIMARY KEY,
    RoleName varchar(20)
);

CREATE table UserRoles(
	UserID varchar(40) REFERENCES Users.UserID,
    RoleID int,
    CONSTRAINT PK_UserRoles PRIMARY KEY (UserID, RoleID),
    
    CONSTRAINT FK_UserRoles_UserID FOREIGN KEY (UserID)
    REFERENCES Users(UserID),
    
    CONSTRAINT FK_UserRoles_RoleID FOREIGN KEY (RoleID)
    REFERENCES roles(RoleID)
    ON DELETE CASCADE
);

SELECT * FROM Users;

CREATE table Countries(
	CountryID int auto_increment PRIMARY KEY,
    CountryName varchar(35) NOT NULL
);

CREATE table States(
	StateID int auto_increment PRIMARY KEY,
    StateName varchar(35) NOT NULL,
    CountryID int NOT NULL,
    
    CONSTRAINT FK_States_CountryID FOREIGN KEY (CountryID)
    REFERENCES Countries(CountryID)
);


CREATE table Cities(
	CityID int auto_increment PRIMARY KEY,
    CityName varchar(35) NOT NULL,
    StateID int NOT NULL,
    
    CONSTRAINT FK_Cities_StateID FOREIGN KEY (StateID)
    REFERENCES States(StateID)
);

CREATE table Locations(
	LocationID int auto_increment PRIMARY KEY,
    StreetName varchar(40) NOT NULL,
    PostalCode varchar(10) NOT NULL,
    LocationNumber int NOT NULL,
    CityID int NOT NULL,
    
    CONSTRAINT FK_Locations_CityID FOREIGN KEY (CityID)
    REFERENCES Cities(CityID)
    
);

CREATE table Clients(
	UserID varchar(40) PRIMARY KEY,
    LocationID int,
    PhoneNumber varchar(20),
    
    CONSTRAINT FK_Clients_UserID FOREIGN KEY (UserID)
    REFERENCES Users(UserID)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Clients_LocationID FOREIGN KEY (LocationID)
    REFERENCES Locations(LocationID)
);


CREATE table VehicleTypes(
	VehicleTypeID int auto_increment PRIMARY KEY,
    TypeName varchar(15) NOT NULL
);

CREATE table Couriers(
	UserID varchar(40) PRIMARY KEY,
    CompanyName varchar(30),
    LicensePlate varchar(15),
    VehicleTypeID int NOT NULL,
    WorkingCityID int NOT NULL,
    
    CONSTRAINT FK_Couriers_VehicleTypeID FOREIGN KEY (VehicleTypeID)
    REFERENCES VehicleTypes (VehicleTypeID),
    
    CONSTRAINT FK_Couriers_WorkingCityID FOREIGN KEY (WorkingCityID)
    REFERENCES Cities (CityID),
    
    CONSTRAINT FK_Couriers_UserID FOREIGN KEY (UserID)
    REFERENCES Users (UserID)
    ON DELETE CASCADE
);



CREATE table Owners(
	UserID varchar(40) PRIMARY KEY,
    
    CONSTRAINT FK_Owners_UserID FOREIGN KEY (UserID)
    REFERENCES Users (UserID)
    ON DELETE CASCADE
);


CREATE table Restaurants(
	RestaurantID varchar(40) PRIMARY KEY,
    RestaurantName varchar(30) NOT NULL,
    LocationID int NOT NULL,
    Description varchar(255),
    
    CONSTRAINT FK_Restaurants_LocationID FOREIGN KEY (LocationID)
    REFERENCES Locations (LocationID)
);

CREATE table OwnsRestaurant(
	UserID varchar(40),
    RestaurantID varchar(40),
    
    CONSTRAINT PK_OwnsRestaurant PRIMARY KEY (UserID, RestaurantID),
    
    CONSTRAINT FK_OwnsRestaurant_UserID FOREIGN KEY (UserID)
    REFERENCES Users (UserID)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_OwnsRestaurant_RestaurantID FOREIGN KEY (RestaurantID)
    REFERENCES Restaurants (RestaurantID)
);



CREATE table Orders(
	OrderID varchar(40),
    ClientID varchar(40),
    CourierID varchar(40),
    RestaurantID varchar(40) NOT NULL,
    Delivered boolean NOT NULL DEFAULT FALSE,
    
    CONSTRAINT PK_Orders PRIMARY KEY (OrderID, ClientID),
    
    CONSTRAINT FK_Orders_ClientID FOREIGN KEY (ClientID)
    REFERENCES Clients (UserID)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Orders_CourierID FOREIGN KEY (CourierID)
    REFERENCES Couriers (UserID)
    ON DELETE CASCADE,
    
    CONSTRAINT FK_Orders_RestaurantID FOREIGN KEY (RestaurantID)
    REFERENCES Restaurants (RestaurantID)
);

CREATE table Products(
	ProductID varchar(40),
    RestaurantID varchar(40),
    ProductName varchar(20) NOT NULL,
    Price float(6) NOT NULL,
    ProductDescription varchar(255),
    
    CONSTRAINT PK_Products PRIMARY KEY (ProductID, RestaurantID),
    
    CONSTRAINT FK_Products_RestaurantID FOREIGN KEY (RestaurantID)
    REFERENCES Restaurants (RestaurantID)
);

CREATE table OrderContainedProducts(
	OrderID varchar(40),
    ProductID varchar(40),
    Amount int NOT NULL DEFAULT 0,
    
    CONSTRAINT PK_OrderContainedProducts PRIMARY KEY (OrderID, ProductID),
    
    CONSTRAINT FK_OrderContainedProducts_OrderID FOREIGN KEY (OrderID)
    REFERENCES Orders (OrderID),
    
    CONSTRAINT FK_OrderContainedProducts_ProductID FOREIGN KEY (ProductID)
    REFERENCES Products (ProductID)
);