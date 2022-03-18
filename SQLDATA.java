/*
data class
generated with bash script from SQL file
 */
public class SQLDATA {

    String dropREPLIES = "DROP TABLE REPLIES CASCADE CONSTRAINTS";
    String dropDISCUSSION_POSTS = "DROP TABLE DISCUSSION_POSTS CASCADE CONSTRAINTS";
    String dropANNOUNCEMENTS = "DROP TABLE ANNOUNCEMENTS CASCADE CONSTRAINTS";
    String dropGAME_CAROUSEL = "DROP TABLE GAME_CAROUSEL CASCADE CONSTRAINTS";
    String dropGAME_GENRES = "DROP TABLE GAME_GENRES CASCADE CONSTRAINTS";
    String dropGAME_LANGUAGES = "DROP TABLE GAME_LANGUAGES CASCADE CONSTRAINTS";
    String dropGAME_LINKS = "DROP TABLE GAME_LINKS CASCADE CONSTRAINTS";
    String dropGAME_TAGS = "DROP TABLE GAME_TAGS CASCADE CONSTRAINTS";
    String dropIS_FRIENDS_WITH = "DROP TABLE IS_FRIENDS_WITH CASCADE CONSTRAINTS";
    String dropUSER_WISHLISTS = "DROP TABLE USER_WISHLISTS CASCADE CONSTRAINTS";
    String dropPLAYS = "DROP TABLE PLAYS CASCADE CONSTRAINTS";
    String dropDEVELOPERS_GAMES_ON_PLATFORM = "DROP TABLE DEVELOPERS_GAMES_ON_PLATFORM CASCADE CONSTRAINTS";
    String dropOWNS = "DROP TABLE OWNS CASCADE CONSTRAINTS";
    String dropREVIEWS1A = "DROP TABLE REVIEWS1A CASCADE CONSTRAINTS";
    String dropREVIEWS1B = "DROP TABLE REVIEWS1B CASCADE CONSTRAINTS";
    String dropUNLOCKS = "DROP TABLE UNLOCKS CASCADE CONSTRAINTS";
    String dropACHIEVEMENTS = "DROP TABLE ACHIEVEMENTS CASCADE CONSTRAINTS";
    String dropSHOPPING_CART_GAMES = "DROP TABLE SHOPPING_CART_GAMES CASCADE CONSTRAINTS";
    String dropINVOICES = "DROP TABLE INVOICES CASCADE CONSTRAINTS";
    String dropANNOUNCEMENT_BOARDS = "DROP TABLE ANNOUNCEMENT_BOARDS CASCADE CONSTRAINTS";
    String dropDISCUSSION_BOARDS = "DROP TABLE DISCUSSION_BOARDS CASCADE CONSTRAINTS";
    String dropSHOPPING_CARTS = "DROP TABLE SHOPPING_CARTS CASCADE CONSTRAINTS";
    String dropGAMES2 = "DROP TABLE GAMES2 CASCADE CONSTRAINTS";
    String dropGAMES1 = "DROP TABLE GAMES1 CASCADE CONSTRAINTS";
    String dropDEVELOPERS = "DROP TABLE DEVELOPERS CASCADE CONSTRAINTS";
    String dropUSERS = "DROP TABLE USERS CASCADE CONSTRAINTS";
    /*String dropEXPENSIVE_HIGH_MEMORY_GAMES = "DROP VIEW EXPENSIVE_HIGH_MEMORY_GAMES";
    String dropNA_DEVELOPERS = "DROP VIEW NA_DEVELOPERS";
    String dropUSERS_WHO_DEFAULT = "DROP VIEW USERS_WHO_DEFAULT";*/

    String[] dropArray = {dropREPLIES, dropDISCUSSION_POSTS, dropANNOUNCEMENTS, dropGAME_CAROUSEL,
            dropGAME_GENRES, dropGAME_LANGUAGES, dropGAME_LINKS, dropGAME_TAGS, dropIS_FRIENDS_WITH, dropUSER_WISHLISTS,
            dropPLAYS, dropDEVELOPERS_GAMES_ON_PLATFORM, dropOWNS, dropREVIEWS1A, dropREVIEWS1B, dropUNLOCKS,
            dropACHIEVEMENTS, dropSHOPPING_CART_GAMES, dropINVOICES, dropANNOUNCEMENT_BOARDS, dropDISCUSSION_BOARDS,
            dropSHOPPING_CARTS, dropGAMES2, dropGAMES1, dropDEVELOPERS, dropUSERS,//dropEXPENSIVE_HIGH_MEMORY_GAMES,
            //dropNA_DEVELOPERS,dropUSERS_WHO_DEFAULT
    };

    String createUSERS = "CREATE TABLE USERS ("+
            "    userID NUMBER(8, 0) PRIMARY KEY CHECK (userID >= 0),"+
            "    country VARCHAR2(30) NOT NULL,"+
            "    username VARCHAR2(25) UNIQUE NOT NULL,"+
            "    password VARCHAR2(25) NOT NULL,"+
            "    emailAddress VARCHAR2(50) UNIQUE NOT NULL,"+
            "    firstName VARCHAR2(30),"+
            "    lastName VARCHAR2(30),"+
            "    dateCreated DATE NOT NULL,"+
            "    profileDescription VARCHAR2(300),"+
            "    profileAvatar BLOB,"+
            "    profileBackgroundImage BLOB,"+
            "    profileThemeColor VARCHAR2(6) DEFAULT '111111'"+
            ")";

    String createDEVELOPERS = ""+
            "CREATE TABLE DEVELOPERS ("+
            "    developerID NUMBER(8, 0) PRIMARY KEY CHECK (developerID > 0),"+
            "    name VARCHAR2(50) UNIQUE NOT NULL,"+
            "    contactEmailAddress VARCHAR2(50) UNIQUE NOT NULL,"+
            "    contactAddress VARCHAR2(100) UNIQUE NOT NULL,"+
            "    contactPhone NUMBER(10, 0) UNIQUE NOT NULL CHECK (contactPhone > 0),"+
            "    contactCountry VARCHAR2(50) NOT NULL,"+
            "    website VARCHAR2(300) UNIQUE NOT NULL"+
            ")";

    String createGAMES1 = ""+
            "CREATE TABLE GAMES1 ("+
            "    gameID NUMBER(8, 0) PRIMARY KEY CHECK (gameID >= 0),"+
            "    developerID NUMBER(8, 0) REFERENCES DEVELOPERS(developerID) ON DELETE CASCADE,"+
            "    developerName VARCHAR2(50) REFERENCES DEVELOPERS(name) ON DELETE CASCADE,"+
            "    title VARCHAR2(30) UNIQUE NOT NULL,"+
            "    price NUMBER(5, 2) NOT NULL CHECK (price >= 0),"+
            "    summaryDescription VARCHAR2(300) NOT NULL,"+
            "    detailedDescription VARCHAR2(2000),"+
            "    minimumProcessor VARCHAR2(50) NOT NULL,"+
            "    minimumGraphics VARCHAR2(50) NOT NULL,"+
            "    minimumMemory NUMERIC(2, 0) NOT NULL CHECK (minimumMemory > 0),"+
            "    minimumOperatingSystem VARCHAR2(50) NOT NULL,"+
            "    minimumStorageSpace VARCHAR2(50) NOT NULL"+
            ")";

    String createGAMES2 = ""+
            "CREATE TABLE GAMES2 ("+
            "    title VARCHAR2(30) REFERENCES GAMES1(title) ON DELETE CASCADE,"+
            "    displayImage BLOB"+
            ")";

    String createUSER_WISHLISTS = ""+
            "CREATE TABLE USER_WISHLISTS ("+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    title VARCHAR2(30) REFERENCES GAMES1(title) ON DELETE CASCADE,"+
            "    PRIMARY KEY (userID, gameID)"+
            ")";

    String createGAME_CAROUSEL = ""+
            "CREATE TABLE GAME_CAROUSEL ("+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    carouselItemID NUMBER(8, 0),"+
            "    carouselItem BLOB,"+
            "    PRIMARY KEY (gameID, carouselItemID)"+
            ")";

    String createGAME_LANGUAGES = ""+
            "CREATE TABLE GAME_LANGUAGES ("+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    voiceLanguage VARCHAR2(25),"+
            "    subtitleLanguage VARCHAR2(25),"+
            "    interfaceLanguage VARCHAR2(25),"+
            "    PRIMARY KEY (gameID, voiceLanguage, subtitleLanguage, interfaceLanguage)"+
            ")";

    String createGAME_GENRES = ""+
            "CREATE TABLE GAME_GENRES ("+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    genre VARCHAR2(25) NOT NULL,"+
            "    PRIMARY KEY (gameID, genre)"+
            ")";

    String createGAME_TAGS = ""+
            "CREATE TABLE GAME_TAGS ("+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    tag VARCHAR2(25) NOT NULL,"+
            "    PRIMARY KEY (gameID, tag)"+
            ")";

    String createGAME_LINKS = ""+
            "CREATE TABLE GAME_LINKS ("+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    externalCommunityLink VARCHAR2(500),"+
            "    PRIMARY KEY (gameID, externalCommunityLink)"+
            ")";

    String createDEVELOPERS_GAMES_ON_PLATFORM = ""+
            "CREATE TABLE DEVELOPERS_GAMES_ON_PLATFORM ("+
            "    developerID NUMBER(8, 0) REFERENCES DEVELOPERS(developerID) ON DELETE CASCADE,"+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID),"+
            "    title VARCHAR2(30) REFERENCES GAMES1(title),"+
            "    PRIMARY KEY (developerID, gameID)"+
            ")";

    String createANNOUNCEMENT_BOARDS = ""+
            "CREATE TABLE ANNOUNCEMENT_BOARDS ("+
            "    announcementBoardID NUMBER(8, 0) PRIMARY KEY CHECK(announcementBoardID > 0),"+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    developerID NUMBER(8, 0) REFERENCES DEVELOPERS(developerID) ON DELETE CASCADE,"+
            "    name VARCHAR2(150) NOT NULL"+
            ")";

    String createDISCUSSION_BOARDS = ""+
            "CREATE TABLE DISCUSSION_BOARDS ("+
            "    discussionBoardID NUMBER(8, 0) PRIMARY KEY CHECK(discussionBoardID > 0),"+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    name VARCHAR2(150) NOT NULL"+
            ")";

    String createDISCUSSION_POSTS = ""+
            "CREATE TABLE DISCUSSION_POSTS ("+
            "    postID NUMBER(15, 0) PRIMARY KEY CHECK(postID > 0),"+
            "    discussionBoardID NUMBER(8, 0) REFERENCES DISCUSSION_BOARDS(discussionBoardID) ON DELETE CASCADE,"+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    username VARCHAR2(25) REFERENCES USERS(username) ON DELETE CASCADE,"+
            "    content VARCHAR2(4000) NOT NULL,"+
            "    link VARCHAR2(300) UNIQUE NOT NULL,"+
            "    title VARCHAR2(150) NOT NULL,"+
            "    numberOfReplies NUMBER(4, 0) DEFAULT 0,"+
            "    displayImage BLOB,"+
            "    datePosted DATE NOT NULL"+
            ")";

    String createREPLIES = ""+
            "CREATE TABLE REPLIES ("+
            "    postID NUMBER(15, 0) PRIMARY KEY CHECK(postID > 0),"+
            "    discussionPostID NUMBER(15, 0) REFERENCES DISCUSSION_POSTS(postID) ON DELETE CASCADE,"+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    username VARCHAR2(25) REFERENCES USERS(username) ON DELETE CASCADE, "+
            "    content VARCHAR2(4000) NOT NULL,"+
            "    link VARCHAR2(300) UNIQUE NOT NULL,"+
            "    datePosted DATE NOT NULL"+
            ")";

    String createANNOUNCEMENTS = ""+
            "CREATE TABLE ANNOUNCEMENTS ("+
            "    postID NUMBER(15, 0) PRIMARY KEY CHECK(postID > 0),"+
            "    announcementBoardID NUMBER(8, 0) REFERENCES ANNOUNCEMENT_BOARDS(announcementBoardID) ON DELETE CASCADE,"+
            "    developerID NUMBER(8, 0) REFERENCES DEVELOPERS(developerID) ON DELETE CASCADE,"+
            "    developerName VARCHAR2(50) REFERENCES DEVELOPERS(name) ON DELETE CASCADE,"+
            "    content VARCHAR2(4000) NOT NULL,"+
            "    link VARCHAR2(300) UNIQUE NOT NULL,"+
            "    displayImage BLOB,"+
            "    datePosted DATE NOT NULL"+
            ")";

    String createIS_FRIENDS_WITH = ""+
            "CREATE TABLE IS_FRIENDS_WITH ("+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    friendUserID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    PRIMARY KEY (userID, friendUserID)"+
            ")";

    String createOWNS = ""+
            "CREATE TABLE OWNS ("+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    numberOfUnlockedAchievements NUMBER(3, 0) DEFAULT 0,"+
            "    isFavourite NUMBER(1, 0) DEFAULT 0 CHECK(isFavourite = 0 OR isFavourite = 1),"+
            "    dateLastPlayed DATE,"+
            "    PRIMARY KEY(userID, gameID)"+
            ")";

    String createPLAYS = ""+
            "CREATE TABLE PLAYS ("+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    numberOfHoursPlayed NUMBER(4, 1) DEFAULT 0.0,"+
            "    PRIMARY KEY(userID, gameID)"+
            ")";

    String createACHIEVEMENTS = ""+
            "CREATE TABLE ACHIEVEMENTS ("+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    name VARCHAR2(50) UNIQUE NOT NULL,"+
            "    iconImage BLOB,"+
            "    description VARCHAR2(300) NOT NULL,"+
            "    PRIMARY KEY(gameID, name)"+
            ")";

    String createUNLOCKS = ""+
            "CREATE TABLE UNLOCKS ("+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    name VARCHAR(50) REFERENCES ACHIEVEMENTS(name) ON DELETE CASCADE,"+
            "    PRIMARY KEY(userID, gameID, name)"+
            ")";

    String createREVIEWS1A = ""+
            "CREATE TABLE REVIEWS1A ("+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    username VARCHAR2(25) REFERENCES USERS(username) ON DELETE CASCADE,"+
            "    reviewText VARCHAR(4000),"+
            "    rating NUMBER(1, 0) NOT NULL,"+
            "    datePosted DATE NOT NULL,"+
            "    PRIMARY KEY(gameID, userID)"+
            ")";

    String createREVIEWS1B = ""+
            "CREATE TABLE REVIEWS1B ("+
            "    rating NUMBER(1, 0) PRIMARY KEY,"+
            "    recommendation NUMBER(1, 0) NOT NULL CHECK (recommendation = 1 OR recommendation = 0)"+
            ")";

    String createSHOPPING_CARTS = ""+
            "CREATE TABLE SHOPPING_CARTS ("+
            "    cartID NUMBER(8, 0) PRIMARY KEY CHECK (cartID >= 0),"+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    numberOfItems NUMBER(2, 0) NOT NULL,"+
            "    /* the attributes from this to below are given, when the shopping cart is purchased */"+
            "    datePurchased DATE,"+
            "    creditCardNumber NUMBER(16, 0),"+
            "    creditCardCVV NUMBER(3, 0),"+
            "    creditCardNameOnCard VARCHAR2(150),"+
            "    creditCardExpiryDateMonth NUMBER(2, 0),"+
            "    creditCardExpiryDateYear NUMBER(4, 0)"+
            ")";

    String createSHOPPING_CART_GAMES = ""+
            "CREATE TABLE SHOPPING_CART_GAMES ("+
            "    cartID NUMBER(8, 0) REFERENCES SHOPPING_CARTS(cartID) ON DELETE CASCADE,"+
            "    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,"+
            "    PRIMARY KEY (cartID, gameID)"+
            ")";

    String createINVOICES = ""+
            "CREATE TABLE INVOICES ("+
            "    invoiceID NUMBER(15, 0) PRIMARY KEY CHECK(invoiceID >= 0),"+
            "    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,"+
            "    cartID NUMBER(8, 0) REFERENCES SHOPPING_CARTS(cartID) ON DELETE CASCADE,"+
            "    totalPrice NUMBER(6, 2) NOT NULL"+
            ")";

    String[] createsArray = {createUSERS,createDEVELOPERS,createGAMES1,createGAMES2,createUSER_WISHLISTS,
            createGAME_CAROUSEL,createGAME_LANGUAGES,createGAME_GENRES,createGAME_TAGS,createGAME_LINKS,
            createDEVELOPERS_GAMES_ON_PLATFORM,createANNOUNCEMENT_BOARDS,createDISCUSSION_BOARDS,createDISCUSSION_POSTS,
            createREPLIES,createANNOUNCEMENTS,createIS_FRIENDS_WITH,createOWNS,createPLAYS,createACHIEVEMENTS,
            createUNLOCKS,createREVIEWS1A,createREVIEWS1B,createSHOPPING_CARTS,createSHOPPING_CART_GAMES,
            createINVOICES};

    String insertusers449 = "insert into users values(1, 'germany', 'un1', 'password', 'test@test1.com',NULL, NULL, TO_DATE('17/12/2015', 'DD/MM/YYYY'), NULL, NULL, NULL, 111111)";
    String insertusers450 = "insert into users values(2, 'germany', 'un2', 'password', 'test@test2.com','george', 'test', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1)";
    String insertusers451 = "insert into users values(3, 'canada', 'un3', 'password', 'test@test3.com','tesing', 'test', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1)";
    String insertusers452 = "insert into users values(4, 'usa', 'un4', 'password', 'test@test4.com','testing', 'tester', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1)";
    String insertusers453 = "insert into users values(5, 'china', 'un5', 'password', 'test@test5.com','george', 'costanza', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1)";
    String insertusers454 = "insert into users values(6, 'canada', 'un6', 'password', 'test@test6.com','tesing', 'test', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1)";
    String insertdevelopers456 = "INSERT INTO developers VALUES(1, 'Test name 1', 'test1@test.com', '1 test ave', 5419872051, 'Canada', 'www.test1.com')";
    String insertdevelopers457 = "INSERT INTO developers VALUES(2, 'Test name 2', 'test2@test.com', '2 test ave', 5419872054, 'United States', 'www.test2.com')";
    String insertdevelopers458 = "INSERT INTO developers VALUES(3, 'Test name 3', 'test3@test.com', '3 test ave', 5419872052, 'Canada', 'www.test3.com')";
    String insertdevelopers459 = "INSERT INTO developers VALUES(4, 'Test name 4', 'test4@test.com', '4 test ave', 5419872055, 'Canada', 'www.test4.com')";
    String insertdevelopers460 = "INSERT INTO developers VALUES(5, 'Test name 5', 'test5@test.com', '5 test ave', 5419872056, 'United States', 'www.test5.com')";
    String insertdevelopers461 = "INSERT INTO developers VALUES(6, 'Test name 6', 'test6@test.com', '6 test ave', 5419872057, 'Germany', 'www.test6.com')";
    String insertGAMES1463 = "insert into GAMES1 VALUES(1, 1, 'Test name 1', 'Test game 1', 110, 'summary', 'description', '4GHz', '16GB', 16, 'Windows 10', '50GB')";
    String insertGAMES1464 = "insert into GAMES1 VALUES(2, 2, 'Test name 2', 'Test game 2', 110, 'summary', 'description', '5GHz', '4GB', 4, 'Windows 10', '10GB')";
    String insertGAMES1465 = "insert into GAMES1 VALUES(3, 3, 'Test name 3', 'Test game 3', 110, 'summary', 'description', '2GHz', '8GB', 8, 'Windows 10', '24GB')";
    String insertGAMES1466 = "insert into GAMES1 VALUES(4, 1, 'Test name 4', 'Test game 4', 110, 'summary', 'description', '1GHz', '16GB', 16, 'Windows 10', '42GB')";
    String insertGAMES1467 = "insert into GAMES1 VALUES(5, 2, 'Test name 5', 'Test game 5', 110, 'summary', 'description', '4GHz', '24GB', 4, 'Windows 10', '100GB')";
    String insertGAMES1468 = "insert into GAMES1 VALUES(6, 3, 'Test name 6', 'Test game 6', 110, 'summary', 'description', '5GHz', '8GB', 8, 'Windows 10', '72GB')";
    String insertGAMES1470 = "insert into GAMES1 VALUES(7, 1, 'Test name 1', 'Test game 7', 110, 'summary', 'description', '4GHz', '6GB', 4, 'Windows 10', '9GB')";
    String insertGAMES1471 = "insert into GAMES1 VALUES(8, 1, 'Test name 1', 'Test game 8', 110, 'summary', 'description', '4GHz', '8GB', 4, 'Windows 10', '63GB')";
    String insertGAMES1472 = "insert into GAMES1 VALUES(9, 3, 'Test name 3', 'Test game 9', 110, 'summary', 'description', '2GHz', '8GB', 8, 'Windows 10', '47GB')";
    String insertGAMES2474 = "insert into GAMES2 VALUES('Test game 1', null)";
    String insertGAMES2475 = "insert into GAMES2 VALUES('Test game 2', null)";
    String insertGAMES2476 = "insert into GAMES2 VALUES('Test game 3', null)";
    String insertGAMES2477 = "insert into GAMES2 VALUES('Test game 4', null)";
    String insertGAMES2478 = "insert into GAMES2 VALUES('Test game 5', null)";
    String insertGAMES2479 = "insert into GAMES2 VALUES('Test game 6', null)";
    String insertGAMES2480 = "insert into GAMES2 VALUES('Test game 7', null)";
    String insertGAMES2481 = "insert into GAMES2 VALUES('Test game 8', null)";
    String insertGAMES2482 = "insert into GAMES2 VALUES('Test game 9', null)";
    String insertUSER_WISHLISTS484 = "INSERT INTO USER_WISHLISTS VALUES(4, 7, 'Test game 7')";
    String insertUSER_WISHLISTS486 = "INSERT INTO USER_WISHLISTS VALUES(1, 2, 'Test game 2')";
    String insertUSER_WISHLISTS487 = "INSERT INTO USER_WISHLISTS VALUES(1, 5, 'Test game 5')";
    String insertUSER_WISHLISTS489 = "INSERT INTO USER_WISHLISTS VALUES(3, 1, 'Test game 1')";
    String insertUSER_WISHLISTS490 = "INSERT INTO USER_WISHLISTS VALUES(3, 2, 'Test game 2')";
    String insertUSER_WISHLISTS491 = "INSERT INTO USER_WISHLISTS VALUES(3, 6, 'Test game 6')";
    String insertUSER_WISHLISTS493 = "INSERT INTO USER_WISHLISTS VALUES(5, 2, 'Test game 2')";
    String insertUSER_WISHLISTS494 = "INSERT INTO USER_WISHLISTS VALUES(5, 4, 'Test game 4')";
    String insertUSER_WISHLISTS495 = "INSERT INTO USER_WISHLISTS VALUES(5, 7, 'Test game 7')";
    String insertUSER_WISHLISTS496 = "INSERT INTO USER_WISHLISTS VALUES(5, 9, 'Test game 9')";
    String insertgame_carousel498 = "insert into game_carousel values (1, 1, null)";
    String insertgame_carousel499 = "insert into game_carousel values (1, 2, null)";
    String insertgame_carousel500 = "insert into game_carousel values (1, 3, null)";
    String insertgame_carousel501 = "insert into game_carousel values (2, 4, null)";
    String insertgame_carousel502 = "insert into game_carousel values (3, 5, null)";
    String insertgame_carousel503 = "insert into game_carousel values (4, 6, null)";
    String insertgame_carousel504 = "insert into game_carousel values (5, 7, null)";
    String insertgame_genres506 = "insert into game_genres values (1, 'RPG')";
    String insertgame_genres507 = "insert into game_genres values (2, 'FPS')";
    String insertgame_genres508 = "insert into game_genres values (2, 'RPG')";
    String insertgame_genres509 = "insert into game_genres values (3, 'Platformer')";
    String insertgame_genres510 = "insert into game_genres values (4, 'RTS')";
    String insertgame_genres511 = "insert into game_genres values (5, 'RPG')";
    String insertgame_genres512 = "insert into game_genres values (5, 'Turn based combat')";
    String insertgame_genres513 = "insert into game_genres values (6, 'adventure')";
    String insertgame_genres514 = "insert into game_genres values (7, 'party')";
    String insertgame_genres515 = "insert into game_genres values (8, 'MMORPG')";
    String insertgame_genres516 = "insert into game_genres values (9, 'Horror')";
    String insertOWNS518 = "insert into OWNS VALUES(1, 1, 101, 0, TO_DATE('06/07/2013', 'DD/MM/YYYY'))";
    String insertOWNS519 = "insert into OWNS VALUES(2, 2, 102, 1, TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertOWNS520 = "insert into OWNS VALUES(3, 3, 103, 0, TO_DATE('04/11/2011', 'DD/MM/YYYY'))";
    String insertOWNS521 = "insert into OWNS VALUES(4, 4, 104, 1, TO_DATE('02/02/2002', 'DD/MM/YYYY'))";
    String insertOWNS522 = "insert into OWNS VALUES(5, 5, 105, 0, TO_DATE('05/12/2019', 'DD/MM/YYYY'))";
    String insertOWNS523 = "insert into OWNS VALUES(6, 6, 106, 1, TO_DATE('17/03/2018', 'DD/MM/YYYY'))";
    String insertPLAYS525 = "insert into PLAYS VALUES(1, 1, 51)";
    String insertPLAYS526 = "insert into PLAYS VALUES(2, 2, 52)";
    String insertPLAYS527 = "insert into PLAYS VALUES(3, 3, 53)";
    String insertPLAYS528 = "insert into PLAYS VALUES(4, 4, 54)";
    String insertPLAYS529 = "insert into PLAYS VALUES(5, 5, 55)";
    String insertPLAYS530 = "insert into PLAYS VALUES(6, 6, 56)";
    String insertACHIEVEMENTS532 = "insert into ACHIEVEMENTS VALUES(1, 'achv_nam1', null, 'description text')";
    String insertACHIEVEMENTS533 = "insert into ACHIEVEMENTS VALUES(2, 'achv_nam2', null, 'description text')";
    String insertACHIEVEMENTS534 = "insert into ACHIEVEMENTS VALUES(3, 'achv_nam3', null, 'description text')";
    String insertACHIEVEMENTS535 = "insert into ACHIEVEMENTS VALUES(4, 'achv_nam4', null, 'description text')";
    String insertACHIEVEMENTS536 = "insert into ACHIEVEMENTS VALUES(5, 'achv_nam5', null, 'description text')";
    String insertACHIEVEMENTS537 = "insert into ACHIEVEMENTS VALUES(6, 'achv_nam6', null, 'description text')";
    String insertUNLOCKS539 = "insert into UNLOCKS VALUES(1, 1, 'achv_nam1')";
    String insertUNLOCKS540 = "insert into UNLOCKS VALUES(2, 2, 'achv_nam2')";
    String insertUNLOCKS541 = "insert into UNLOCKS VALUES(3, 3, 'achv_nam3')";
    String insertUNLOCKS542 = "insert into UNLOCKS VALUES(4, 4, 'achv_nam4')";
    String insertUNLOCKS543 = "insert into UNLOCKS VALUES(5, 5, 'achv_nam5')";
    String insertUNLOCKS544 = "insert into UNLOCKS VALUES(6, 6, 'achv_nam6')";
    String insertREVIEWS1A546 = "insert into REVIEWS1A VALUES(1, 1, 'un1', 'rev_1', 1, TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertREVIEWS1A547 = "insert into REVIEWS1A VALUES(2, 2, 'un2', 'rev_2', 2, TO_DATE('02/02/2002', 'DD/MM/YYYY'))";
    String insertREVIEWS1A548 = "insert into REVIEWS1A VALUES(3, 3, 'un3', 'rev_3', 3, TO_DATE('03/03/2003', 'DD/MM/YYYY'))";
    String insertREVIEWS1A549 = "insert into REVIEWS1A VALUES(4, 4, 'un4', 'rev_4', 4, TO_DATE('04/04/2004', 'DD/MM/YYYY'))";
    String insertREVIEWS1A550 = "insert into REVIEWS1A VALUES(5, 5, 'un5', 'rev_5', 5, TO_DATE('05/05/2005', 'DD/MM/YYYY'))";
    String insertREVIEWS1A551 = "insert into REVIEWS1A VALUES(6, 6, 'un6', 'rev_6', 4, TO_DATE('06/06/2006', 'DD/MM/YYYY'))";
    String insertREVIEWS1B553 = "insert into REVIEWS1B VALUES(0, 0)";
    String insertREVIEWS1B554 = "insert into REVIEWS1B VALUES(1, 0)";
    String insertREVIEWS1B555 = "insert into REVIEWS1B VALUES(2, 0)";
    String insertREVIEWS1B556 = "insert into REVIEWS1B VALUES(3, 1)";
    String insertREVIEWS1B557 = "insert into REVIEWS1B VALUES(4, 1)";
    String insertREVIEWS1B558 = "insert into REVIEWS1B VALUES(5, 1)";
    String insertSHOPPING_CARTS560 = "insert into SHOPPING_CARTS VALUES(1, 1, 6, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4400669750808362, 295, 'Timothy Higdon', 12, 2026)";
    String insertSHOPPING_CARTS561 = "insert into SHOPPING_CARTS VALUES(2, 2, 5, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4532404434704410, 369, 'Maria Bazile', 11, 2022)";
    String insertSHOPPING_CARTS562 = "insert into SHOPPING_CARTS VALUES(3, 3, 4, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4539922183663217, 907, 'Beverly Evans', 03, 2025)";
    String insertSHOPPING_CARTS563 = "insert into SHOPPING_CARTS VALUES(4, 4, 3, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4929600365958674, 663, 'Jean Williamson', 05, 2027)";
    String insertSHOPPING_CARTS564 = "insert into SHOPPING_CARTS VALUES(5, 5, 2, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4024007191785095, 289, 'Mary Barbour', 10, 2024)";
    String insertSHOPPING_CARTS565 = "insert into SHOPPING_CARTS VALUES(6, 6, 1, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4716204035039663, 281, 'Richard Barr', 06, 2023)";
    String insertSHOPPING_CART_GAMES567 = "insert into SHOPPING_CART_GAMES VALUES(6, 6)";
    String insertSHOPPING_CART_GAMES568 = "insert into SHOPPING_CART_GAMES VALUES(5, 5)";
    String insertSHOPPING_CART_GAMES569 = "insert into SHOPPING_CART_GAMES VALUES(4, 4)";
    String insertSHOPPING_CART_GAMES570 = "insert into SHOPPING_CART_GAMES VALUES(3, 3)";
    String insertSHOPPING_CART_GAMES571 = "insert into SHOPPING_CART_GAMES VALUES(2, 2)";
    String insertSHOPPING_CART_GAMES572 = "insert into SHOPPING_CART_GAMES VALUES(1, 1)";
    String insertINVOICES574 = "insert into INVOICES VALUES(492800872744817, 6, 6, 11.11)";
    String insertINVOICES575 = "insert into INVOICES VALUES(845096205777424, 5, 5, 22.22)";
    String insertINVOICES576 = "insert into INVOICES VALUES(989826851290719, 4, 4, 33.33)";
    String insertINVOICES577 = "insert into INVOICES VALUES(210938036500992, 3, 3, 44.44)";
    String insertINVOICES578 = "insert into INVOICES VALUES(608007814172440, 2, 2, 55.55)";
    String insertINVOICES579 = "insert into INVOICES VALUES(104187339856323, 1, 1, 66.66)";
    String insertgame_languages581 = "insert into game_languages values (1, 'english', 'english', 'english')";
    String insertgame_languages582 = "insert into game_languages values (1, 'french', 'french', 'french')";
    String insertgame_languages583 = "insert into game_languages values (1, 'german', 'german', 'german')";
    String insertgame_languages584 = "insert into game_languages values (2, 'english', 'english', 'english')";
    String insertgame_languages585 = "insert into game_languages values (3, 'french', 'french', 'french')";
    String insertgame_languages586 = "insert into game_languages values (4, 'german', 'german', 'german')";
    String insertgame_languages587 = "insert into game_languages values (5, 'german', 'german', 'german')";
    String insertgame_languages588 = "insert into game_languages values (6, 'spanish', 'spanish', 'spanish')";
    String insertgame_languages589 = "insert into game_languages values (7, 'english', 'english', 'english')";
    String insertgame_languages590 = "insert into game_languages values (8, 'english', 'english', 'english')";
    String insertgame_languages591 = "insert into game_languages values (9, 'english', 'english', 'english')";
    String insertgame_tags593 = "insert into game_tags values (1, 'tag 1')";
    String insertgame_tags594 = "insert into game_tags values (1, 'tag 2')";
    String insertgame_tags595 = "insert into game_tags values (2, 'tag 3')";
    String insertgame_tags596 = "insert into game_tags values (3, 'tag 1')";
    String insertgame_tags597 = "insert into game_tags values (3, 'tag 4')";
    String insertgame_tags598 = "insert into game_tags values (4, 'tag 5')";
    String insertgame_tags599 = "insert into game_tags values (4, 'tag 6')";
    String insertgame_tags600 = "insert into game_tags values (4, 'tag 7')";
    String insertgame_tags601 = "insert into game_tags values (5, 'tag 1')";
    String insertgame_tags602 = "insert into game_tags values (6, 'tag 5')";
    String insertgame_tags603 = "insert into game_tags values (7, 'tag 2')";
    String insertgame_tags604 = "insert into game_tags values (8, 'tag 8')";
    String insertgame_tags605 = "insert into game_tags values (9, 'tag 3')";
    String insertdiscussion_boards607 = "insert into discussion_boards VALUES(1, 1, 'test title')";
    String insertdiscussion_boards608 = "insert into discussion_boards VALUES(2, 2, 'test title')";
    String insertdiscussion_boards609 = "insert into discussion_boards VALUES(3, 3, 'test title')";
    String insertdiscussion_boards610 = "insert into discussion_boards VALUES(4, 4, 'test title')";
    String insertdiscussion_boards611 = "insert into discussion_boards VALUES(5, 5, 'test title')";
    String insertdiscussion_boards612 = "insert into discussion_boards VALUES(6, 6, 'test title')";
    String insertdiscussion_boards613 = "insert into discussion_boards VALUES(7, 7, 'test title')";
    String insertdiscussion_boards614 = "insert into discussion_boards VALUES(8, 8, 'test title')";
    String insertdiscussion_boards615 = "insert into discussion_boards VALUES(9, 9, 'test title')";
    String insertannouncement_boards617 = "insert into announcement_boards VALUES(1, 1, 1, 'Updates')";
    String insertannouncement_boards618 = "insert into announcement_boards VALUES(2, 2, 2, 'test title')";
    String insertannouncement_boards619 = "insert into announcement_boards VALUES(3, 3, 3, 'test title')";
    String insertannouncement_boards620 = "insert into announcement_boards VALUES(4, 4, 1, 'Patches')";
    String insertannouncement_boards621 = "insert into announcement_boards VALUES(5, 5, 2,'test title')";
    String insertannouncement_boards622 = "insert into announcement_boards VALUES(6, 6, 2,'test title')";
    String insertannouncement_boards623 = "insert into announcement_boards VALUES(7, 7, 1,'test title')";
    String insertannouncement_boards624 = "insert into announcement_boards VALUES(8, 8, 1,'test title')";
    String insertannouncement_boards625 = "insert into announcement_boards VALUES(9, 9, 3,'test title')";
    String insertDISCUSSION_POSTS627 = "insert into DISCUSSION_POSTS VALUES(1, 1, 1, 'un1', 'test post 1', 'test link 1', 'test title 1', 0, null, TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS628 = "insert into DISCUSSION_POSTS VALUES(2, 2, 1, 'un1', 'test post 1', 'test link 2', 'test title 2', 0, null, TO_DATE('01/02/2001', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS629 = "insert into DISCUSSION_POSTS VALUES(3, 3, 1, 'un1', 'test post 1', 'test link 3', 'test title 3', 0, null, TO_DATE('02/01/2001', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS630 = "insert into DISCUSSION_POSTS VALUES(4, 2, 1, 'un1', 'test post 1', 'test link 4', 'test title 4', 0, null, TO_DATE('01/01/2002', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS631 = "insert into DISCUSSION_POSTS VALUES(5, 4, 1, 'un1', 'test post 1', 'test link 5', 'test title 5', 0, null, TO_DATE('01/01/2003', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS632 = "insert into DISCUSSION_POSTS VALUES(6, 6, 1, 'un1', 'test post 1', 'test link 6', 'test title 6', 0, null, TO_DATE('01/05/2001', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS633 = "insert into DISCUSSION_POSTS VALUES(7, 7, 1, 'un1', 'test post 1', 'test link 7', 'test title 7', 0, null, TO_DATE('05/06/2012', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS634 = "insert into DISCUSSION_POSTS VALUES(8, 6, 1, 'un1', 'test post 1', 'test link 8', 'test title 8', 0, null, TO_DATE('07/05/2021', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS635 = "insert into DISCUSSION_POSTS VALUES(9, 2, 1, 'un1', 'test post 1', 'test link 9', 'test title 9', 0, null, TO_DATE('31/03/2009', 'DD/MM/YYYY'))";
    String insertDISCUSSION_POSTS636 = "insert into DISCUSSION_POSTS VALUES(10, 4, 1, 'un1', 'test post 1', 'test link 10', 'test title 10', 0, null, TO_DATE('31/03/2009', 'DD/MM/YYYY'))";
    String insertreplies638 = "insert into replies VALUES(1, 1, 1, 'un2', 'test reply 1', 'test link 1', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertreplies639 = "insert into replies VALUES(2, 1, 3, 'un3', 'test reply 2', 'test link 2', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertreplies640 = "insert into replies VALUES(3, 2, 2, 'un2', 'test reply 3', 'test link 3', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertreplies641 = "insert into replies VALUES(4, 2, 4, 'un4', 'test reply 4', 'test link 4', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertreplies642 = "insert into replies VALUES(5, 4, 5, 'un5', 'test reply 5', 'test link 5', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertreplies643 = "insert into replies VALUES(6, 4, 1, 'un1', 'test reply 6', 'test link 6', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertreplies644 = "insert into replies VALUES(7, 4, 5, 'un5', 'test reply 7', 'test link 7', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertreplies645 = "insert into replies VALUES(8, 5, 4, 'un4', 'test reply 8', 'test link 8', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertreplies646 = "insert into replies VALUES(9, 1, 1, 'un1', 'test reply 9', 'test link 9', TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertannouncements650 = "insert into announcements VALUES(1, 1, 1, 'Test name 1', 'Test content 1', 'test link 1', null,  TO_DATE('01/01/2012', 'DD/MM/YYYY'))";
    String insertannouncements651 = "insert into announcements VALUES(2, 2, 2, 'Test name 2', 'Test content 2', 'test link 2', null,  TO_DATE('01/01/2012', 'DD/MM/YYYY'))";
    String insertannouncements652 = "insert into announcements VALUES(3, 5, 3, 'Test name 3', 'Test content 3', 'test link 3', null,  TO_DATE('01/01/2012', 'DD/MM/YYYY'))";
    String insertannouncements653 = "insert into announcements VALUES(4, 7, 4, 'Test name 4', 'Test content 4', 'test link 4', null,  TO_DATE('01/01/2017', 'DD/MM/YYYY'))";
    String insertannouncements654 = "insert into announcements VALUES(5, 9, 5, 'Test name 5', 'Test content 5', 'test link 5', null,  TO_DATE('01/01/2019', 'DD/MM/YYYY'))";
    String insertannouncements655 = "insert into announcements VALUES(6, 1, 6, 'Test name 6', 'Test content 6', 'test link 6', null,  TO_DATE('01/01/2020', 'DD/MM/YYYY'))";
    String insertannouncements656 = "insert into announcements VALUES(7, 2, 1, 'Test name 1', 'Test content 7', 'test link 7', null,  TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertannouncements657 = "insert into announcements VALUES(8, 2, 2, 'Test name 2', 'Test content 8', 'test link 8', null,  TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertannouncements658 = "insert into announcements VALUES(9, 8, 3, 'Test name 3', 'Test content 9', 'test link 9', null,  TO_DATE('01/01/2001', 'DD/MM/YYYY'))";
    String insertIS_FRIENDS_WITH661 = "insert into IS_FRIENDS_WITH VALUES(1, 2)";
    String insertIS_FRIENDS_WITH662 = "insert into IS_FRIENDS_WITH VALUES(1, 3)";
    String insertIS_FRIENDS_WITH663 = "insert into IS_FRIENDS_WITH VALUES(1, 4)";
    String insertIS_FRIENDS_WITH664 = "insert into IS_FRIENDS_WITH VALUES(1, 5)";
    String insertIS_FRIENDS_WITH665 = "insert into IS_FRIENDS_WITH VALUES(1, 6)";
    String insertIS_FRIENDS_WITH666 = "insert into IS_FRIENDS_WITH VALUES(2, 3)";
    String insertIS_FRIENDS_WITH667 = "insert into IS_FRIENDS_WITH VALUES(3, 5)";
    String insertGAME_LINKS669 = "insert into GAME_LINKS VALUES(1, 'test link 1')";
    String insertGAME_LINKS670 = "insert into GAME_LINKS VALUES(2, 'test link 2')";
    String insertGAME_LINKS671 = "insert into GAME_LINKS VALUES(3, 'test link 3')";
    String insertGAME_LINKS672 = "insert into GAME_LINKS VALUES(4, 'test link 4')";
    String insertGAME_LINKS673 = "insert into GAME_LINKS VALUES(6, 'test link 5')";
    String insertGAME_LINKS674 = "insert into GAME_LINKS VALUES(1, 'test link 6')";
    String insertGAME_LINKS675 = "insert into GAME_LINKS VALUES(9, 'test link 7')";
    String insertGAME_LINKS676 = "insert into GAME_LINKS VALUES(1, 'test link 8')";
    String insertGAME_LINKS677 = "insert into GAME_LINKS VALUES(3, 'test link 9')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM679 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(1, 1, 'Test game 1')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM680 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(2, 2, 'Test game 1')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM681 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(3, 3, 'Test game 1')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM682 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(1, 4, 'Test game 1')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM683 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(2, 5, 'Test game 1')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM684 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(3, 6, 'Test game 1')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM685 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(1, 7, 'Test game 1')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM686 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(1, 8, 'Test game 1')";
    String insertDEVELOPERS_GAMES_ON_PLATFORM687 = "insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(3, 9, 'Test game 1')";

    String[] inserts = {insertusers449, insertusers450, insertusers451, insertusers452, insertusers453, insertusers454,
            insertdevelopers456, insertdevelopers457, insertdevelopers458, insertdevelopers459, insertdevelopers460,
            insertdevelopers461, insertGAMES1463, insertGAMES1464, insertGAMES1465, insertGAMES1466, insertGAMES1467,
            insertGAMES1468, insertGAMES1470, insertGAMES1471, insertGAMES1472, insertGAMES2474, insertGAMES2475,
            insertGAMES2476, insertGAMES2477, insertGAMES2478, insertGAMES2479, insertGAMES2480, insertGAMES2481,
            insertGAMES2482, insertUSER_WISHLISTS484, insertUSER_WISHLISTS486, insertUSER_WISHLISTS487,
            insertUSER_WISHLISTS489, insertUSER_WISHLISTS490, insertUSER_WISHLISTS491, insertUSER_WISHLISTS493,
            insertUSER_WISHLISTS494, insertUSER_WISHLISTS495, insertUSER_WISHLISTS496, insertgame_carousel498,
            insertgame_carousel499, insertgame_carousel500, insertgame_carousel501, insertgame_carousel502,
            insertgame_carousel503, insertgame_carousel504, insertgame_genres506, insertgame_genres507,
            insertgame_genres508, insertgame_genres509, insertgame_genres510, insertgame_genres511,
            insertgame_genres512, insertgame_genres513, insertgame_genres514, insertgame_genres515,
            insertgame_genres516, insertOWNS518, insertOWNS519, insertOWNS520, insertOWNS521, insertOWNS522,
            insertOWNS523, insertPLAYS525, insertPLAYS526, insertPLAYS527, insertPLAYS528, insertPLAYS529,
            insertPLAYS530, insertACHIEVEMENTS532, insertACHIEVEMENTS533, insertACHIEVEMENTS534, insertACHIEVEMENTS535,
            insertACHIEVEMENTS536, insertACHIEVEMENTS537, insertUNLOCKS539, insertUNLOCKS540, insertUNLOCKS541,
            insertUNLOCKS542, insertUNLOCKS543, insertUNLOCKS544, insertREVIEWS1A546, insertREVIEWS1A547,
            insertREVIEWS1A548, insertREVIEWS1A549, insertREVIEWS1A550, insertREVIEWS1A551, insertREVIEWS1B553,
            insertREVIEWS1B554, insertREVIEWS1B555, insertREVIEWS1B556, insertREVIEWS1B557, insertREVIEWS1B558,
            insertSHOPPING_CARTS560, insertSHOPPING_CARTS561, insertSHOPPING_CARTS562, insertSHOPPING_CARTS563,
            insertSHOPPING_CARTS564, insertSHOPPING_CARTS565, insertSHOPPING_CART_GAMES567, insertSHOPPING_CART_GAMES568,
            insertSHOPPING_CART_GAMES569, insertSHOPPING_CART_GAMES570, insertSHOPPING_CART_GAMES571,
            insertSHOPPING_CART_GAMES572, insertINVOICES574, insertINVOICES575, insertINVOICES576, insertINVOICES577,
            insertINVOICES578, insertINVOICES579, insertgame_languages581, insertgame_languages582,
            insertgame_languages583, insertgame_languages584, insertgame_languages585, insertgame_languages586,
            insertgame_languages587, insertgame_languages588, insertgame_languages589, insertgame_languages590,
            insertgame_languages591, insertgame_tags593, insertgame_tags594, insertgame_tags595, insertgame_tags596,
            insertgame_tags597, insertgame_tags598, insertgame_tags599, insertgame_tags600, insertgame_tags601,
            insertgame_tags602, insertgame_tags603, insertgame_tags604, insertgame_tags605, insertdiscussion_boards607,
            insertdiscussion_boards608, insertdiscussion_boards609, insertdiscussion_boards610,
            insertdiscussion_boards611, insertdiscussion_boards612, insertdiscussion_boards613,
            insertdiscussion_boards614, insertdiscussion_boards615, insertannouncement_boards617,
            insertannouncement_boards618, insertannouncement_boards619, insertannouncement_boards620,
            insertannouncement_boards621, insertannouncement_boards622, insertannouncement_boards623,
            insertannouncement_boards624, insertannouncement_boards625, insertDISCUSSION_POSTS627,
            insertDISCUSSION_POSTS628, insertDISCUSSION_POSTS629, insertDISCUSSION_POSTS630,
            insertDISCUSSION_POSTS631, insertDISCUSSION_POSTS632, insertDISCUSSION_POSTS633, insertDISCUSSION_POSTS634,
            insertDISCUSSION_POSTS635, insertDISCUSSION_POSTS636, insertreplies638, insertreplies639, insertreplies640,
            insertreplies641, insertreplies642, insertreplies643, insertreplies644, insertreplies645, insertreplies646,
            insertannouncements650, insertannouncements651, insertannouncements652, insertannouncements653,
            insertannouncements654, insertannouncements655, insertannouncements656, insertannouncements657,
            insertannouncements658, insertIS_FRIENDS_WITH661, insertIS_FRIENDS_WITH662, insertIS_FRIENDS_WITH663,
            insertIS_FRIENDS_WITH664, insertIS_FRIENDS_WITH665, insertIS_FRIENDS_WITH666, insertIS_FRIENDS_WITH667,
            insertGAME_LINKS669, insertGAME_LINKS670, insertGAME_LINKS671, insertGAME_LINKS672, insertGAME_LINKS673,
            insertGAME_LINKS674, insertGAME_LINKS675, insertGAME_LINKS676, insertGAME_LINKS677,
            insertDEVELOPERS_GAMES_ON_PLATFORM679, insertDEVELOPERS_GAMES_ON_PLATFORM680,
            insertDEVELOPERS_GAMES_ON_PLATFORM681, insertDEVELOPERS_GAMES_ON_PLATFORM682,
            insertDEVELOPERS_GAMES_ON_PLATFORM683, insertDEVELOPERS_GAMES_ON_PLATFORM684,
            insertDEVELOPERS_GAMES_ON_PLATFORM685, insertDEVELOPERS_GAMES_ON_PLATFORM686,
            insertDEVELOPERS_GAMES_ON_PLATFORM687};

    String select48 = "SELECT userID, firstName, lastName, emailAddress, country AS Country_of_Residence, dateCreated AS Date_Account_Created"+
            " FROM USERS" +
            " WHERE dateCreated >= TO_DATE('01/01/2014', 'DD/MM/YYYY')" +
            " ORDER BY userID ASC" ;
    String select65 = "SELECT contactCountry AS Developer_Country, COUNT(*) AS Number_of_Developers"+
            " FROM DEVELOPERS" +
            " GROUP BY contactCountry" +
            " ORDER BY Number_of_Developers DESC" ;
    String select95 = "SELECT developerID, price AS Selling_Cost, COUNT(*) AS Number_of_Games"+
            " FROM GAMES1" +
            " WHERE minimumMemory <= 4" +
            " GROUP BY developerID, price" +
            " ORDER BY price ASC" ;
    String select111 = "SELECT title AS Game_Title, COUNT(userID) AS Number_of_Wishlisted"+
            " FROM USER_WISHLISTS" +
            " GROUP BY title" +
            " ORDER BY Number_of_Wishlisted DESC" ;
    String select127 = "SELECT gameID, COUNT(*) AS Number_of_Carousel_Items"+
            " FROM GAME_CAROUSEL" +
            " WHERE carouselItem IS NOT NULL" +
            " GROUP BY gameID" +
            " ORDER BY Number_of_Carousel_Items, gameID ASC" ;
    String select143 = "SELECT voiceLanguage AS Language_Voiced, COUNT(gameID) AS Num_of_Games_Supported"+
            " FROM GAME_LANGUAGES" +
            " GROUP BY voiceLanguage" +
            " ORDER BY Num_of_Games_Supported DESC" ;
    String select157 = "SELECT genre, COUNT(gameID) AS Num_of_Games"+
            " FROM GAME_GENRES" +
            " GROUP BY genre" +
            " ORDER BY Num_of_Games DESC" ;
    String select171 = "SELECT tag, COUNT(gameID) AS Num_of_Games"+
            " FROM GAME_TAGS" +
            " GROUP BY tag" +
            " ORDER BY Num_of_Games DESC" ;
    String select185 = "SELECT gameID, externalCommunityLink"+
            " FROM GAME_LINKS" +
            " WHERE externalCommunityLink LIKE 'https://www.instagram.com%' OR externalCommunityLink LIKE 'https://www.twitter.com%'" +
            " ORDER BY gameID ASC, externalCommunityLink ASC" ;
    String select200 = "SELECT developerID, COUNT(gameID) AS Num_of_Games_on_Platform"+
            " FROM DEVELOPERS_GAMES_ON_PLATFORM" +
            " GROUP BY developerID" +
            " ORDER BY Num_of_Games_on_Platform DESC, developerID ASC" ;
    String select215 = "SELECT announcementBoardID, gameID, name AS Name_of_Announcement_Board"+
            " FROM ANNOUNCEMENT_BOARDS" +
            " WHERE name LIKE '%Updates%' OR name LIKE '%Announcements%'" +
            " ORDER BY announcementBoardID ASC, name DESC" ;
    String select229 = "SELECT discussionBoardID, name AS Name_of_Discussion_Board"+
            " FROM DISCUSSION_BOARDS" +
            " ORDER BY Name_of_Discussion_Board DESC" ;
    String select249 = "SELECT title, username, numberOfReplies, datePosted"+
            " FROM DISCUSSION_POSTS" +
            " WHERE datePosted >= TO_DATE('01/01/2021', 'DD/MM/YYYY')" +
            " ORDER BY numberOfReplies DESC" ;
    String select267 = "SELECT userID, COUNT(*) AS Num_of_Replies_Written_2021"+
            " FROM REPLIES" +
            " WHERE datePosted >= TO_DATE('01/01/2021', 'DD/MM/YYYY')" +
            " GROUP BY userID" +
            " ORDER BY Num_of_Replies_Written_2021 DESC" ;
    String select287 = "SELECT developerName, link, displayImage"+
            " FROM ANNOUNCEMENTS" +
            " WHERE datePosted = TO_DATE(SYSDATE, 'DD/MM/YYYY')" +
            " ORDER BY developerName DESC" ;
    String select301 = "SELECT userID, COUNT(*) AS Number_of_Friends"+
            " FROM IS_FRIENDS_WITH" +
            " GROUP BY userID" +
            " ORDER BY Number_of_Friends DESC, userID DESC" ;
    String select318 = "SELECT gameID, COUNT(userID) AS Num_User_Who_Fav_And_Unlck"+
            " FROM OWNS" +
            " WHERE isFavourite = 1 AND numberOfUnlockedAchievements > 4" +
            " GROUP BY gameID" +
            " ORDER BY Num_User_Who_Fav_And_Unlck DESC" ;
    String select334 = "SELECT userID, SUM(numberOfHoursPlayed) AS Total_Hours_Played_On_Account"+
            " FROM PLAYS" +
            " GROUP BY userID" +
            " ORDER BY Total_Hours_Played_On_Account DESC, userID ASC" ;
    String select352 = "SELECT gameID, name AS Achievement_Name, description AS Achievement_Description"+
            " FROM ACHIEVEMENTS" +
            " WHERE description LIKE '%defeat%' OR description LIKE '%attack%' OR description LIKE '%defend%'" +
            " ORDER BY Achievement_Name ASC" ;
    String select367 = "SELECT gameID, name, COUNT(userID) AS Num_of_Unlocks"+
            " FROM UNLOCKS" +
            " GROUP BY gameID, name" +
            " ORDER BY Num_of_Unlocks DESC, name ASC" ;
    String select392 = "SELECT gameID, AVG(rating) AS Average_Rating"+
            " FROM REVIEWS1A" +
            " GROUP BY gameID" +
            " ORDER BY Average_Rating DESC, gameID ASC" ;
    String select413 = "SELECT userID, AVG(numberOfItems) AS Avg_Num_Items_Shopping_Cart"+
            " FROM SHOPPING_CARTS" +
            " WHERE datePurchased IS NOT NULL" +
            " GROUP BY userID" +
            " ORDER BY Avg_Num_Items_Shopping_Cart DESC, userID ASC" ;
    String select427 = "SELECT cartID, COUNT(*) AS Number_of_Games"+
            " FROM SHOPPING_CART_GAMES" +
            " GROUP BY cartID" +
            " ORDER BY Number_of_Games DESC, cartID ASC" ;
    String select442 = "SELECT DISTINCT invoiceID, totalPrice"+
            " FROM INVOICES" +
            " WHERE totalPrice > 29.99" +
            " ORDER BY totalPrice DESC" ;
    String select694 = "SELECT DISTINCT DEVELOPERS.name AS DEVELOPER_NAME"+
            " FROM DEVELOPERS, GAMES1, GAME_LANGUAGES" +
            " WHERE GAME_LANGUAGES.voiceLanguage = 'english' AND" +
            "       GAMES1.price < 120.00 AND" +
            "       DEVELOPERS.developerID = GAMES1.developerID AND" +
            "       GAMES1.gameID = GAME_LANGUAGES.gameID" +
            " ORDER BY DEVELOPER_NAME" ;
    String select703 = "SELECT DISTINCT GAMES1.gameID AS GAME_ID, GAMES1.title AS GAME_TITLE"+
            " FROM GAMES1, ANNOUNCEMENT_BOARDS, ANNOUNCEMENTS" +
            " WHERE (ANNOUNCEMENT_BOARDS.name LIKE '%Patches%' OR ANNOUNCEMENT_BOARDS.name LIKE '%Updates%') AND" +
            "       ANNOUNCEMENTS.datePosted > TO_DATE('01/01/2001', 'DD/MM/YYYY') AND" +
            "       GAMES1.gameID = ANNOUNCEMENT_BOARDS.gameID AND" +
            "       ANNOUNCEMENT_BOARDS.announcementBoardID = ANNOUNCEMENTS.announcementBoardID" +
            " ORDER BY GAMES1.gameID" ;
    String select747 = "select game.title title, scg.gameID gmid"+
            " FROM GAMES1 game, SHOPPING_CART_GAMES scg" +
            " where game.gameID = scg.gameID AND" +
            "       NOT EXISTS (" +
            "         SELECT rating" +
            "         FROM REVIEWS1A" +
            "         where REVIEWS1A.gameID = GAME.gameID AND REVIEWS1A.rating < 2" +
            "     )" +
            " GROUP BY game.title, scg.gameID" +
            " HAVING COUNT(scg.cartID) >= 2" +
            " order by game.title ASC" ;
    String select761 = "select u.username, u.userID FROM USERS u"+
            " WHERE" +
            " EXISTS (" +
            " select p1.userid FROM plays p1, plays p2 WHERE" +
            " p1.userID = p2.userID AND p1.gameID = 1 and p2.gameID = 2 AND u.userID = p1.userID" +
            " )" +
            " ORDER BY u.username ASC" ;
    String select770 = "select u.username, u.userID"+
            " FROM USERS u, PLAYS p" +
            " WHERE p.userID = u.userID AND p.gameID = 1" +
            " UNION" +
            " (" +
            " select u2.username, u2.userID" +
            " FROM USERS u2, PLAYS p2" +
            " WHERE p2.userID = u2.userID AND p2.gameID = 6" +
            " )" ;
    String select781 = "select g.title, g.gameID"+
            " FROM GAMES1 g WHERE EXISTS (" +
            " select r.gameID FROM REVIEWS1A r" +
            " WHERE r.gameID = g.gameID" +
            " GROUP BY r.gameID" +
            " HAVING COUNT(r.userID) >= 2" +
            " )" ;
    String select791 = "select g.title, g.gameID FROM GAMES1 g"+
            " WHERE NOT EXISTS (" +
            " select g2.gameID FROM USER_WISHLISTS g2" +
            " WHERE g2.gameID = g.gameID" +
            " GROUP BY g2.gameID" +
            " HAVING COUNT(g2.userID) = 1" +
            " )" ;


    String[] selects = {select48, select65, select95, select111, select127, select143, select157, select171, select185,
            select200, select215, select229, select249, select267, select287, select301, select318, select334, select352,
            select367, select392, select413, select427, select442, select694, select703, select747, select761, select770,
            select781, select791};

}
