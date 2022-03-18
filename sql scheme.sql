DROP TABLE REPLIES CASCADE CONSTRAINTS;
DROP TABLE DISCUSSION_POSTS CASCADE CONSTRAINTS;
DROP TABLE ANNOUNCEMENTS CASCADE CONSTRAINTS;
DROP TABLE GAME_CAROUSEL CASCADE CONSTRAINTS;
DROP TABLE GAME_GENRES CASCADE CONSTRAINTS;
DROP TABLE GAME_LANGUAGES CASCADE CONSTRAINTS;
DROP TABLE GAME_LINKS CASCADE CONSTRAINTS;
DROP TABLE GAME_TAGS CASCADE CONSTRAINTS;
DROP TABLE IS_FRIENDS_WITH CASCADE CONSTRAINTS;
DROP TABLE USER_WISHLISTS CASCADE CONSTRAINTS;
DROP TABLE PLAYS CASCADE CONSTRAINTS;
DROP TABLE DEVELOPERS_GAMES_ON_PLATFORM CASCADE CONSTRAINTS;
DROP TABLE OWNS CASCADE CONSTRAINTS;
DROP TABLE REVIEWS1A CASCADE CONSTRAINTS;
DROP TABLE REVIEWS1B CASCADE CONSTRAINTS;
DROP TABLE UNLOCKS CASCADE CONSTRAINTS;
DROP TABLE ACHIEVEMENTS CASCADE CONSTRAINTS;
DROP TABLE SHOPPING_CART_GAMES CASCADE CONSTRAINTS;
DROP TABLE INVOICES CASCADE CONSTRAINTS;
DROP TABLE ANNOUNCEMENT_BOARDS CASCADE CONSTRAINTS;
DROP TABLE DISCUSSION_BOARDS CASCADE CONSTRAINTS;
DROP TABLE SHOPPING_CARTS CASCADE CONSTRAINTS;
DROP TABLE GAMES2 CASCADE CONSTRAINTS;
DROP TABLE GAMES1 CASCADE CONSTRAINTS;
DROP TABLE DEVELOPERS CASCADE CONSTRAINTS;
DROP TABLE USERS CASCADE CONSTRAINTS;
DROP VIEW EXPENSIVE_HIGH_MEMORY_GAMES CASCADE CONSTRAINTS;
DROP VIEW NA_DEVELOPERS CASCADE CONSTRAINTS;
DROP VIEW USERS_WHO_DEFAULT CASCADE CONSTRAINTS;



CREATE TABLE USERS (
    userID NUMBER(8, 0) PRIMARY KEY CHECK (userID >= 0),
    country VARCHAR2(30) NOT NULL,
    username VARCHAR2(25) UNIQUE NOT NULL,
    password VARCHAR2(25) NOT NULL,
    emailAddress VARCHAR2(50) UNIQUE NOT NULL,
    firstName VARCHAR2(30),
    lastName VARCHAR2(30),
    dateCreated DATE NOT NULL,
    profileDescription VARCHAR2(300),
    profileAvatar BLOB,
    profileBackgroundImage BLOB,
    profileThemeColor VARCHAR2(6) DEFAULT '111111'
);


/* Select general info of all users who have created their accounts after the start of 2014 */
SELECT userID, firstName, lastName, emailAddress, country AS Country_of_Residence, dateCreated AS Date_Account_Created
FROM USERS
WHERE dateCreated >= TO_DATE('01/01/2014', 'DD/MM/YYYY')
ORDER BY userID ASC;


CREATE TABLE DEVELOPERS (
    developerID NUMBER(8, 0) PRIMARY KEY CHECK (developerID > 0),
    name VARCHAR2(50) UNIQUE NOT NULL,
    contactEmailAddress VARCHAR2(50) UNIQUE NOT NULL,
    contactAddress VARCHAR2(100) UNIQUE NOT NULL,
    contactPhone NUMBER(10, 0) UNIQUE NOT NULL CHECK (contactPhone > 0),
    contactCountry VARCHAR2(50) NOT NULL,
    website VARCHAR2(300) UNIQUE NOT NULL
);

/* List the number of developers on the platform from each given country they are located */
SELECT contactCountry AS Developer_Country, COUNT(*) AS Number_of_Developers
FROM DEVELOPERS
GROUP BY contactCountry
ORDER BY Number_of_Developers DESC;


CREATE TABLE GAMES1 (
    gameID NUMBER(8, 0) PRIMARY KEY CHECK (gameID >= 0),
    developerID NUMBER(8, 0) REFERENCES DEVELOPERS(developerID) ON DELETE CASCADE,
    developerName VARCHAR2(50) REFERENCES DEVELOPERS(name) ON DELETE CASCADE,
    title VARCHAR2(30) UNIQUE NOT NULL,
    price NUMBER(5, 2) NOT NULL CHECK (price >= 0),
    summaryDescription VARCHAR2(300) NOT NULL,
    detailedDescription VARCHAR2(2000),
    minimumProcessor VARCHAR2(50) NOT NULL,
    minimumGraphics VARCHAR2(50) NOT NULL, 
    minimumMemory NUMERIC(2, 0) NOT NULL CHECK (minimumMemory > 0), 
    minimumOperatingSystem VARCHAR2(50) NOT NULL,
    minimumStorageSpace VARCHAR2(50) NOT NULL
);

CREATE TABLE GAMES2 (
    title VARCHAR2(30) REFERENCES GAMES1(title) ON DELETE CASCADE,
    displayImage BLOB
);

/* For BCNF decomposition displayImg was actually 'D' and title was actually 'E' */

/* List the number of games in each pricing category (most sell at prices 19.99 29.99 and 59.99) 
   from each developer that have a minimum memory requirement of less than or equal to 4GB */
SELECT developerID, price AS Selling_Cost, COUNT(*) AS Number_of_Games
FROM GAMES1
WHERE minimumMemory <= 4
GROUP BY developerID, price
ORDER BY price ASC;


CREATE TABLE USER_WISHLISTS (
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    title VARCHAR2(30) REFERENCES GAMES1(title) ON DELETE CASCADE,
    PRIMARY KEY (userID, gameID)
);

/* List the number of people who have wishlisted each game
   (does not include game title where no users have wishlisted them) */
SELECT title AS Game_Title, COUNT(userID) AS Number_of_Wishlisted
FROM USER_WISHLISTS
GROUP BY title
ORDER BY Number_of_Wishlisted DESC;


CREATE TABLE GAME_CAROUSEL (
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    carouselItemID NUMBER(8, 0),
    carouselItem BLOB,
    PRIMARY KEY (gameID, carouselItemID)
);

/* List the number of carousel items for each game
   (does not include NULL carousel items and does not include games
   where no carousel items have been created) */
SELECT gameID, COUNT(*) AS Number_of_Carousel_Items
FROM GAME_CAROUSEL
WHERE carouselItem IS NOT NULL
GROUP BY gameID
ORDER BY Number_of_Carousel_Items, gameID ASC;


CREATE TABLE GAME_LANGUAGES (
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    voiceLanguage VARCHAR2(25),
    subtitleLanguage VARCHAR2(25),
    interfaceLanguage VARCHAR2(25),
    PRIMARY KEY (gameID, voiceLanguage, subtitleLanguage, interfaceLanguage)
);

/* List the number of games that support each type of voiced language */
SELECT voiceLanguage AS Language_Voiced, COUNT(gameID) AS Num_of_Games_Supported
FROM GAME_LANGUAGES
GROUP BY voiceLanguage
ORDER BY Num_of_Games_Supported DESC;


CREATE TABLE GAME_GENRES (
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    genre VARCHAR2(25) NOT NULL,
    PRIMARY KEY (gameID, genre)
);

/* List the number of games in each genre (games can belong to
   more than one genre)*/
SELECT genre, COUNT(gameID) AS Num_of_Games
FROM GAME_GENRES
GROUP BY genre
ORDER BY Num_of_Games DESC;


CREATE TABLE GAME_TAGS (
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    tag VARCHAR2(25) NOT NULL,
    PRIMARY KEY (gameID, tag)
);

/* List the number of games in each tag (games can have more than
   one tag)*/
SELECT tag, COUNT(gameID) AS Num_of_Games
FROM GAME_TAGS
GROUP BY tag
ORDER BY Num_of_Games DESC;


CREATE TABLE GAME_LINKS (
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    externalCommunityLink VARCHAR2(500),
    PRIMARY KEY (gameID, externalCommunityLink)
);

/* Select the external community link(s) (if they exists) for each game
   if the link goes to twitter or instagram*/
SELECT gameID, externalCommunityLink
FROM GAME_LINKS
WHERE externalCommunityLink LIKE 'https://www.instagram.com%' OR externalCommunityLink LIKE 'https://www.twitter.com%'
ORDER BY gameID ASC, externalCommunityLink ASC;


CREATE TABLE DEVELOPERS_GAMES_ON_PLATFORM (
    developerID NUMBER(8, 0) REFERENCES DEVELOPERS(developerID) ON DELETE CASCADE,
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    title VARCHAR2(30) REFERENCES GAMES1(title) ON DELETE CASCADE,
    PRIMARY KEY (developerID, gameID)
);

/* List the number of games each developer has on platform
   (does not include developers who have no games on platform) */
SELECT developerID, COUNT(gameID) AS Num_of_Games_on_Platform
FROM DEVELOPERS_GAMES_ON_PLATFORM
GROUP BY developerID
ORDER BY Num_of_Games_on_Platform DESC, developerID ASC;


CREATE TABLE ANNOUNCEMENT_BOARDS (
    announcementBoardID NUMBER(8, 0) PRIMARY KEY CHECK(announcementBoardID > 0),
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    developerID NUMBER(8, 0) REFERENCES DEVELOPERS(developerID) ON DELETE CASCADE,
    name VARCHAR2(150) NOT NULL
);

/* Select announcement board id, game id, and announcement board names
   that contain 'Updates' or 'Announcements' anywhere in their name */
SELECT announcementBoardID, gameID, name AS Name_of_Announcement_Board
FROM ANNOUNCEMENT_BOARDS
WHERE name LIKE '%Updates%' OR name LIKE '%Announcements%'
ORDER BY announcementBoardID ASC, name DESC;


CREATE TABLE DISCUSSION_BOARDS (
    discussionBoardID NUMBER(8, 0) PRIMARY KEY CHECK(discussionBoardID > 0),
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    name VARCHAR2(150) NOT NULL
);

/* List the discussion board id and its associated name for all
   discussion boards */
SELECT discussionBoardID, name AS Name_of_Discussion_Board
FROM DISCUSSION_BOARDS
ORDER BY Name_of_Discussion_Board DESC;


CREATE TABLE DISCUSSION_POSTS (
    postID NUMBER(15, 0) PRIMARY KEY CHECK(postID > 0),
    discussionBoardID NUMBER(8, 0) REFERENCES DISCUSSION_BOARDS(discussionBoardID) ON DELETE CASCADE,
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    username VARCHAR2(25) REFERENCES USERS(username) ON DELETE CASCADE,
    content VARCHAR2(4000) NOT NULL,
    link VARCHAR2(300) UNIQUE NOT NULL,
    title VARCHAR2(150) NOT NULL,
    numberOfReplies NUMBER(4, 0) DEFAULT 0,
    displayImage BLOB,
    datePosted DATE NOT NULL
);

/* Select general discussion post information for those discussion posts that
   were posted from the beginning of 2021 */
SELECT title, username, numberOfReplies, datePosted
FROM DISCUSSION_POSTS
WHERE datePosted >= TO_DATE('01/01/2021', 'DD/MM/YYYY')
ORDER BY numberOfReplies DESC;


CREATE TABLE REPLIES (
    postID NUMBER(15, 0) PRIMARY KEY CHECK(postID > 0),
    discussionPostID NUMBER(15, 0) REFERENCES DISCUSSION_POSTS(postID) ON DELETE CASCADE,
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    username VARCHAR2(25) REFERENCES USERS(username) ON DELETE CASCADE,
    content VARCHAR2(4000) NOT NULL,
    link VARCHAR2(300) UNIQUE NOT NULL,
    datePosted DATE NOT NULL
);

/* List the number of replies each user has written across all discussion
   boards after the beginning of 2021 */
SELECT userID, COUNT(*) AS Num_of_Replies_Written_2021
FROM REPLIES
WHERE datePosted >= TO_DATE('01/01/2021', 'DD/MM/YYYY')
GROUP BY userID
ORDER BY Num_of_Replies_Written_2021 DESC;


CREATE TABLE ANNOUNCEMENTS (
    postID NUMBER(15, 0) PRIMARY KEY CHECK(postID > 0),
    announcementBoardID NUMBER(8, 0) REFERENCES ANNOUNCEMENT_BOARDS(announcementBoardID) ON DELETE CASCADE,
    developerID NUMBER(8, 0) REFERENCES DEVELOPERS(developerID) ON DELETE CASCADE,
    developerName VARCHAR2(50) REFERENCES DEVELOPERS(name) ON DELETE CASCADE,
    content VARCHAR2(4000) NOT NULL,
    link VARCHAR2(300) UNIQUE NOT NULL,
    displayImage BLOB,
    datePosted DATE NOT NULL
);

/* Select general information about the announcements of each developer (who has created
   an announcement on the current date) */
SELECT developerName, link, displayImage
FROM ANNOUNCEMENTS
WHERE datePosted = TO_DATE(SYSDATE, 'DD/MM/YYYY')
ORDER BY developerName DESC;


CREATE TABLE IS_FRIENDS_WITH (
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    friendUserID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    PRIMARY KEY (userID, friendUserID)
);

/* List the number of friends each user has (does not include users
   who have no friends) */
SELECT userID, COUNT(*) AS Number_of_Friends
FROM IS_FRIENDS_WITH
GROUP BY userID
ORDER BY Number_of_Friends DESC, userID DESC;


CREATE TABLE OWNS (
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    numberOfUnlockedAchievements NUMBER(3, 0) DEFAULT 0,
    isFavourite NUMBER(1, 0) DEFAULT 0 CHECK(isFavourite = 0 OR isFavourite = 1),
    dateLastPlayed DATE,
    PRIMARY KEY(userID, gameID)
);

/* For each game, count the number of users who have favourited it
   and have unlocked more than 4 achievements in it*/
SELECT gameID, COUNT(userID) AS Num_User_Who_Fav_And_Unlck
FROM OWNS
WHERE isFavourite = 1 AND numberOfUnlockedAchievements > 4
GROUP BY gameID
ORDER BY Num_User_Who_Fav_And_Unlck DESC;


CREATE TABLE PLAYS (
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    numberOfHoursPlayed NUMBER(4, 1) DEFAULT 0.0,
    PRIMARY KEY(userID, gameID)
);

/* List the total number of hours each user has played across all
   the games they own */
SELECT userID, SUM(numberOfHoursPlayed) AS Total_Hours_Played_On_Account
FROM PLAYS
GROUP BY userID
ORDER BY Total_Hours_Played_On_Account DESC, userID ASC;


CREATE TABLE ACHIEVEMENTS (
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    name VARCHAR2(50) UNIQUE NOT NULL,
    iconImage BLOB,
    description VARCHAR2(300) NOT NULL,
    PRIMARY KEY(gameID, name)
);

/* Select the gameID, the name, and description of each achievement
   that has in the description the strings defeat, attack, or defend 
   (can include words such as defeated, attacked, defended, etc, or any 
   other word containing these strings)*/
SELECT gameID, name AS Achievement_Name, description AS Achievement_Description
FROM ACHIEVEMENTS
WHERE description LIKE '%defeat%' OR description LIKE '%attack%' OR description LIKE '%defend%'
ORDER BY Achievement_Name ASC;


CREATE TABLE UNLOCKS (
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    name VARCHAR(50) REFERENCES ACHIEVEMENTS(name) ON DELETE CASCADE,
    PRIMARY KEY(userID, gameID, name)
);

/* For each achievement of each game, count the number of users who 
   have unlocked it (does not include achievements with no unlocks) */
SELECT gameID, name, COUNT(userID) AS Num_of_Unlocks
FROM UNLOCKS
GROUP BY gameID, name
ORDER BY Num_of_Unlocks DESC, name ASC;


CREATE TABLE REVIEWS1A (
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    username VARCHAR2(25) REFERENCES USERS(username) ON DELETE CASCADE,
    reviewText VARCHAR(4000),
    rating NUMBER(1, 0) NOT NULL,
    datePosted DATE NOT NULL,
    PRIMARY KEY(gameID, userID)
);

CREATE TABLE REVIEWS1B (
    rating NUMBER(1, 0) PRIMARY KEY,
    recommendation NUMBER(1, 0) NOT NULL CHECK (recommendation = 1 OR recommendation = 0)
);

/* Do not need REVIEWS2 table from 3NF diagram because all those attributes are
   already in the USERS table and userID is still primary key in that table as well */

/* For each game (that has reviews), calculate its average user rating */
SELECT gameID, AVG(rating) AS Average_Rating
FROM REVIEWS1A
GROUP BY gameID
ORDER BY Average_Rating DESC, gameID ASC;


CREATE TABLE SHOPPING_CARTS (
    cartID NUMBER(8, 0) PRIMARY KEY CHECK (cartID >= 0),
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    numberOfItems NUMBER(2, 0) NOT NULL,
    /* the attributes from this to below are given, when the shopping cart is purchased */
    datePurchased DATE,        
    creditCardNumber NUMBER(16, 0),
    creditCardCVV NUMBER(3, 0),
    creditCardNameOnCard VARCHAR2(150),
    creditCardExpiryDateMonth NUMBER(2, 0),
    creditCardExpiryDateYear NUMBER(4, 0)
);

/* For the collection of carts each user has bought, calculate the 
   average number of items in each collection */
SELECT userID, AVG(numberOfItems) AS Avg_Num_Items_Shopping_Cart
FROM SHOPPING_CARTS
WHERE datePurchased IS NOT NULL
GROUP BY userID
ORDER BY Avg_Num_Items_Shopping_Cart DESC, userID ASC;


CREATE TABLE SHOPPING_CART_GAMES (
    cartID NUMBER(8, 0) REFERENCES SHOPPING_CARTS(cartID) ON DELETE CASCADE,
    gameID NUMBER(8, 0) REFERENCES GAMES1(gameID) ON DELETE CASCADE,
    PRIMARY KEY (cartID, gameID)
);

/* List the number of games in each cart */
SELECT cartID, COUNT(*) AS Number_of_Games
FROM SHOPPING_CART_GAMES
GROUP BY cartID
ORDER BY Number_of_Games DESC, cartID ASC;


CREATE TABLE INVOICES (
    invoiceID NUMBER(15, 0) PRIMARY KEY CHECK(invoiceID >= 0),
    userID NUMBER(8, 0) REFERENCES USERS(userID) ON DELETE CASCADE,
    cartID NUMBER(8, 0) REFERENCES SHOPPING_CARTS(cartID) ON DELETE CASCADE,
    totalPrice NUMBER(6, 2) NOT NULL
);

/* Select the invoice ID and the invoice total price of those 
   invoices that have a total price of more than 29.99 dollars */
SELECT DISTINCT invoiceID, totalPrice
FROM INVOICES 
WHERE totalPrice > 29.99
ORDER BY totalPrice DESC;

/* add reviews1b and games2 */

insert into users values(1, 'germany', 'un1', 'password', 'test@test1.com',
NULL, NULL, TO_DATE('17/12/2015', 'DD/MM/YYYY'), NULL, NULL, NULL, 111111);
insert into users values(2, 'germany', 'un2', 'password', 'test@test2.com',
'george', 'test', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1);
insert into users values(3, 'canada', 'un3', 'password', 'test@test3.com',
'tesing', 'test', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1);
insert into users values(4, 'usa', 'un4', 'password', 'test@test4.com',
'testing', 'tester', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1);
insert into users values(5, 'china', 'un5', 'password', 'test@test5.com',
'george', 'costanza', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1);
insert into users values(6, 'canada', 'un6', 'password', 'test@test6.com',
'tesing', 'test', TO_DATE('17/12/2015', 'DD/MM/YYYY'), 'dd', null, null, 1);

INSERT INTO developers VALUES(1, 'Test name 1', 'test1@test.com', '1 test ave', 5419872051, 'Canada', 'www.test1.com');
INSERT INTO developers VALUES(2, 'Test name 2', 'test2@test.com', '2 test ave', 5419872054, 'United States', 'www.test2.com');
INSERT INTO developers VALUES(3, 'Test name 3', 'test3@test.com', '3 test ave', 5419872052, 'Canada', 'www.test3.com');
INSERT INTO developers VALUES(4, 'Test name 4', 'test4@test.com', '4 test ave', 5419872055, 'Canada', 'www.test4.com');
INSERT INTO developers VALUES(5, 'Test name 5', 'test5@test.com', '5 test ave', 5419872056, 'United States', 'www.test5.com');
INSERT INTO developers VALUES(6, 'Test name 6', 'test6@test.com', '6 test ave', 5419872057, 'Germany', 'www.test6.com');

insert into GAMES1 VALUES(1, 1, 'Test name 1', 'Test game 1', 110, 'summary', 'description', '4GHz', '16GB', 16, 'Windows 10', '50GB');
insert into GAMES1 VALUES(2, 2, 'Test name 2', 'Test game 2', 110, 'summary', 'description', '5GHz', '4GB', 4, 'Windows 10', '10GB');
insert into GAMES1 VALUES(3, 3, 'Test name 3', 'Test game 3', 110, 'summary', 'description', '2GHz', '8GB', 8, 'Windows 10', '24GB');
insert into GAMES1 VALUES(4, 1, 'Test name 4', 'Test game 4', 110, 'summary', 'description', '1GHz', '16GB', 16, 'Windows 10', '42GB');
insert into GAMES1 VALUES(5, 2, 'Test name 5', 'Test game 5', 110, 'summary', 'description', '4GHz', '24GB', 4, 'Windows 10', '100GB');
insert into GAMES1 VALUES(6, 3, 'Test name 6', 'Test game 6', 110, 'summary', 'description', '5GHz', '8GB', 8, 'Windows 10', '72GB');
--adding multiple1 games for a developer
insert into GAMES1 VALUES(7, 1, 'Test name 1', 'Test game 7', 110, 'summary', 'description', '4GHz', '6GB', 4, 'Windows 10', '9GB');
insert into GAMES1 VALUES(8, 1, 'Test name 1', 'Test game 8', 110, 'summary', 'description', '4GHz', '8GB', 4, 'Windows 10', '63GB');
insert into GAMES1 VALUES(9, 3, 'Test name 3', 'Test game 9', 110, 'summary', 'description', '2GHz', '8GB', 8, 'Windows 10', '47GB');

insert into GAMES2 VALUES('Test game 1', null);
insert into GAMES2 VALUES('Test game 2', null);
insert into GAMES2 VALUES('Test game 3', null);
insert into GAMES2 VALUES('Test game 4', null);
insert into GAMES2 VALUES('Test game 5', null);
insert into GAMES2 VALUES('Test game 6', null);
insert into GAMES2 VALUES('Test game 7', null);
insert into GAMES2 VALUES('Test game 8', null);
insert into GAMES2 VALUES('Test game 9', null);

INSERT INTO USER_WISHLISTS VALUES(4, 7, 'Test game 7');

INSERT INTO USER_WISHLISTS VALUES(1, 2, 'Test game 2');
INSERT INTO USER_WISHLISTS VALUES(1, 5, 'Test game 5');

INSERT INTO USER_WISHLISTS VALUES(3, 1, 'Test game 1');
INSERT INTO USER_WISHLISTS VALUES(3, 2, 'Test game 2');
INSERT INTO USER_WISHLISTS VALUES(3, 6, 'Test game 6');

INSERT INTO USER_WISHLISTS VALUES(5, 2, 'Test game 2');
INSERT INTO USER_WISHLISTS VALUES(5, 4, 'Test game 4');
INSERT INTO USER_WISHLISTS VALUES(5, 7, 'Test game 7');
INSERT INTO USER_WISHLISTS VALUES(5, 9, 'Test game 9');

insert into game_carousel values (1, 1, null);
insert into game_carousel values (1, 2, null);
insert into game_carousel values (1, 3, null);
insert into game_carousel values (2, 4, null);
insert into game_carousel values (3, 5, null);
insert into game_carousel values (4, 6, null);
insert into game_carousel values (5, 7, null);

insert into game_genres values (1, 'RPG');
insert into game_genres values (2, 'FPS');
insert into game_genres values (2, 'RPG');
insert into game_genres values (3, 'Platformer');
insert into game_genres values (4, 'RTS');
insert into game_genres values (5, 'RPG');
insert into game_genres values (5, 'Turn based combat');
insert into game_genres values (6, 'adventure');
insert into game_genres values (7, 'party');
insert into game_genres values (8, 'MMORPG');
insert into game_genres values (9, 'Horror');

insert into OWNS VALUES(1, 1, 101, 0, TO_DATE('06/07/2013', 'DD/MM/YYYY'));
insert into OWNS VALUES(2, 2, 102, 1, TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into OWNS VALUES(3, 3, 103, 0, TO_DATE('04/11/2011', 'DD/MM/YYYY'));
insert into OWNS VALUES(4, 4, 104, 1, TO_DATE('02/02/2002', 'DD/MM/YYYY'));
insert into OWNS VALUES(5, 5, 105, 0, TO_DATE('05/12/2019', 'DD/MM/YYYY'));
insert into OWNS VALUES(6, 6, 106, 1, TO_DATE('17/03/2018', 'DD/MM/YYYY'));

insert into PLAYS VALUES(1, 1, 51);
insert into PLAYS VALUES(2, 2, 52);
insert into PLAYS VALUES(3, 3, 53);
insert into PLAYS VALUES(4, 4, 54);
insert into PLAYS VALUES(5, 5, 55);
insert into PLAYS VALUES(6, 6, 56);

insert into ACHIEVEMENTS VALUES(1, 'achv_nam1', null, 'description text');
insert into ACHIEVEMENTS VALUES(2, 'achv_nam2', null, 'description text');
insert into ACHIEVEMENTS VALUES(3, 'achv_nam3', null, 'description text');
insert into ACHIEVEMENTS VALUES(4, 'achv_nam4', null, 'description text');
insert into ACHIEVEMENTS VALUES(5, 'achv_nam5', null, 'description text');
insert into ACHIEVEMENTS VALUES(6, 'achv_nam6', null, 'description text');

insert into UNLOCKS VALUES(1, 1, 'achv_nam1');
insert into UNLOCKS VALUES(2, 2, 'achv_nam2');
insert into UNLOCKS VALUES(3, 3, 'achv_nam3');
insert into UNLOCKS VALUES(4, 4, 'achv_nam4');
insert into UNLOCKS VALUES(5, 5, 'achv_nam5');
insert into UNLOCKS VALUES(6, 6, 'achv_nam6');

insert into REVIEWS1A VALUES(1, 1, 'un1', 'rev_1', 1, TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into REVIEWS1A VALUES(2, 2, 'un2', 'rev_2', 2, TO_DATE('02/02/2002', 'DD/MM/YYYY'));
insert into REVIEWS1A VALUES(3, 3, 'un3', 'rev_3', 3, TO_DATE('03/03/2003', 'DD/MM/YYYY'));
insert into REVIEWS1A VALUES(4, 4, 'un4', 'rev_4', 4, TO_DATE('04/04/2004', 'DD/MM/YYYY'));
insert into REVIEWS1A VALUES(5, 5, 'un5', 'rev_5', 5, TO_DATE('05/05/2005', 'DD/MM/YYYY'));
insert into REVIEWS1A VALUES(6, 6, 'un6', 'rev_6', 4, TO_DATE('06/06/2006', 'DD/MM/YYYY'));

insert into REVIEWS1B VALUES(0, 0);
insert into REVIEWS1B VALUES(1, 0);
insert into REVIEWS1B VALUES(2, 0);
insert into REVIEWS1B VALUES(3, 1);
insert into REVIEWS1B VALUES(4, 1);
insert into REVIEWS1B VALUES(5, 1);

insert into SHOPPING_CARTS VALUES(1, 1, 6, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4400669750808362, 295, 'Timothy Higdon', 12, 2026);
insert into SHOPPING_CARTS VALUES(2, 2, 5, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4532404434704410, 369, 'Maria Bazile', 11, 2022);
insert into SHOPPING_CARTS VALUES(3, 3, 4, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4539922183663217, 907, 'Beverly Evans', 03, 2025);
insert into SHOPPING_CARTS VALUES(4, 4, 3, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4929600365958674, 663, 'Jean Williamson', 05, 2027);
insert into SHOPPING_CARTS VALUES(5, 5, 2, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4024007191785095, 289, 'Mary Barbour', 10, 2024);
insert into SHOPPING_CARTS VALUES(6, 6, 1, TO_DATE('01/01/2001', 'DD/MM/YYYY'), 4716204035039663, 281, 'Richard Barr', 06, 2023);

insert into SHOPPING_CART_GAMES VALUES(6, 6);
insert into SHOPPING_CART_GAMES VALUES(5, 5);
insert into SHOPPING_CART_GAMES VALUES(4, 4);
insert into SHOPPING_CART_GAMES VALUES(3, 3);
insert into SHOPPING_CART_GAMES VALUES(2, 2);
insert into SHOPPING_CART_GAMES VALUES(1, 1);

insert into INVOICES VALUES(492800872744817, 6, 6, 11.11);
insert into INVOICES VALUES(845096205777424, 5, 5, 22.22);
insert into INVOICES VALUES(989826851290719, 4, 4, 33.33);
insert into INVOICES VALUES(210938036500992, 3, 3, 44.44);
insert into INVOICES VALUES(608007814172440, 2, 2, 55.55);
insert into INVOICES VALUES(104187339856323, 1, 1, 66.66);

insert into game_languages values (1, 'english', 'english', 'english');
insert into game_languages values (1, 'french', 'french', 'french');
insert into game_languages values (1, 'german', 'german', 'german');
insert into game_languages values (2, 'english', 'english', 'english');
insert into game_languages values (3, 'french', 'french', 'french');
insert into game_languages values (4, 'german', 'german', 'german');
insert into game_languages values (5, 'german', 'german', 'german');
insert into game_languages values (6, 'spanish', 'spanish', 'spanish');
insert into game_languages values (7, 'english', 'english', 'english');
insert into game_languages values (8, 'english', 'english', 'english');
insert into game_languages values (9, 'english', 'english', 'english');

insert into game_tags values (1, 'tag 1');
insert into game_tags values (1, 'tag 2');
insert into game_tags values (2, 'tag 3');
insert into game_tags values (3, 'tag 1');
insert into game_tags values (3, 'tag 4');
insert into game_tags values (4, 'tag 5');
insert into game_tags values (4, 'tag 6');
insert into game_tags values (4, 'tag 7');
insert into game_tags values (5, 'tag 1');
insert into game_tags values (6, 'tag 5');
insert into game_tags values (7, 'tag 2');
insert into game_tags values (8, 'tag 8');
insert into game_tags values (9, 'tag 3');

insert into discussion_boards VALUES(1, 1, 'test title');
insert into discussion_boards VALUES(2, 2, 'test title');
insert into discussion_boards VALUES(3, 3, 'test title');
insert into discussion_boards VALUES(4, 4, 'test title');
insert into discussion_boards VALUES(5, 5, 'test title');
insert into discussion_boards VALUES(6, 6, 'test title');
insert into discussion_boards VALUES(7, 7, 'test title');
insert into discussion_boards VALUES(8, 8, 'test title');
insert into discussion_boards VALUES(9, 9, 'test title');

insert into announcement_boards VALUES(1, 1, 1, 'Updates');
insert into announcement_boards VALUES(2, 2, 2, 'test title');
insert into announcement_boards VALUES(3, 3, 3, 'test title');
insert into announcement_boards VALUES(4, 4, 1, 'Patches');
insert into announcement_boards VALUES(5, 5, 2,'test title');
insert into announcement_boards VALUES(6, 6, 2,'test title');
insert into announcement_boards VALUES(7, 7, 1,'test title');
insert into announcement_boards VALUES(8, 8, 1,'test title');
insert into announcement_boards VALUES(9, 9, 3,'test title');

insert into DISCUSSION_POSTS VALUES(1, 1, 1, 'un1', 'test post 1', 'test link 1', 'test title 1', 0, null, TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(2, 2, 1, 'un1', 'test post 1', 'test link 2', 'test title 2', 0, null, TO_DATE('01/02/2001', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(3, 3, 1, 'un1', 'test post 1', 'test link 3', 'test title 3', 0, null, TO_DATE('02/01/2001', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(4, 2, 1, 'un1', 'test post 1', 'test link 4', 'test title 4', 0, null, TO_DATE('01/01/2002', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(5, 4, 1, 'un1', 'test post 1', 'test link 5', 'test title 5', 0, null, TO_DATE('01/01/2003', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(6, 6, 1, 'un1', 'test post 1', 'test link 6', 'test title 6', 0, null, TO_DATE('01/05/2001', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(7, 7, 1, 'un1', 'test post 1', 'test link 7', 'test title 7', 0, null, TO_DATE('05/06/2012', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(8, 6, 1, 'un1', 'test post 1', 'test link 8', 'test title 8', 0, null, TO_DATE('07/05/2021', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(9, 2, 1, 'un1', 'test post 1', 'test link 9', 'test title 9', 0, null, TO_DATE('31/03/2009', 'DD/MM/YYYY'));
insert into DISCUSSION_POSTS VALUES(10, 4, 1, 'un1', 'test post 1', 'test link 10', 'test title 10', 0, null, TO_DATE('31/03/2009', 'DD/MM/YYYY'));

insert into replies VALUES(1, 1, 1, 'un2', 'test reply 1', 'test link 1', TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into replies VALUES(2, 1, 3, 'un3', 'test reply 2', 'test link 2', TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into replies VALUES(3, 2, 2, 'un2', 'test reply 3', 'test link 3', TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into replies VALUES(4, 2, 4, 'un4', 'test reply 4', 'test link 4', TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into replies VALUES(5, 4, 5, 'un5', 'test reply 5', 'test link 5', TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into replies VALUES(6, 4, 1, 'un1', 'test reply 6', 'test link 6', TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into replies VALUES(7, 4, 5, 'un5', 'test reply 7', 'test link 7', TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into replies VALUES(8, 5, 4, 'un4', 'test reply 8', 'test link 8', TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into replies VALUES(9, 1, 1, 'un1', 'test reply 9', 'test link 9', TO_DATE('01/01/2001', 'DD/MM/YYYY'));



insert into announcements VALUES(1, 1, 1, 'Test name 1', 'Test content 1', 'test link 1', null,  TO_DATE('01/01/2012', 'DD/MM/YYYY'));
insert into announcements VALUES(2, 2, 2, 'Test name 2', 'Test content 2', 'test link 2', null,  TO_DATE('01/01/2012', 'DD/MM/YYYY'));
insert into announcements VALUES(3, 5, 3, 'Test name 3', 'Test content 3', 'test link 3', null,  TO_DATE('01/01/2012', 'DD/MM/YYYY'));
insert into announcements VALUES(4, 7, 4, 'Test name 4', 'Test content 4', 'test link 4', null,  TO_DATE('01/01/2017', 'DD/MM/YYYY'));
insert into announcements VALUES(5, 9, 5, 'Test name 5', 'Test content 5', 'test link 5', null,  TO_DATE('01/01/2019', 'DD/MM/YYYY'));
insert into announcements VALUES(6, 1, 6, 'Test name 6', 'Test content 6', 'test link 6', null,  TO_DATE('01/01/2020', 'DD/MM/YYYY'));
insert into announcements VALUES(7, 2, 1, 'Test name 1', 'Test content 7', 'test link 7', null,  TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into announcements VALUES(8, 2, 2, 'Test name 2', 'Test content 8', 'test link 8', null,  TO_DATE('01/01/2001', 'DD/MM/YYYY'));
insert into announcements VALUES(9, 8, 3, 'Test name 3', 'Test content 9', 'test link 9', null,  TO_DATE('01/01/2001', 'DD/MM/YYYY'));


insert into IS_FRIENDS_WITH VALUES(1, 2);
insert into IS_FRIENDS_WITH VALUES(1, 3);
insert into IS_FRIENDS_WITH VALUES(1, 4);
insert into IS_FRIENDS_WITH VALUES(1, 5);
insert into IS_FRIENDS_WITH VALUES(1, 6);
insert into IS_FRIENDS_WITH VALUES(2, 3);
insert into IS_FRIENDS_WITH VALUES(3, 5);

insert into GAME_LINKS VALUES(1, 'test link 1');
insert into GAME_LINKS VALUES(2, 'test link 2');
insert into GAME_LINKS VALUES(3, 'test link 3');
insert into GAME_LINKS VALUES(4, 'test link 4');
insert into GAME_LINKS VALUES(6, 'test link 5');
insert into GAME_LINKS VALUES(1, 'test link 6');
insert into GAME_LINKS VALUES(9, 'test link 7');
insert into GAME_LINKS VALUES(1, 'test link 8');
insert into GAME_LINKS VALUES(3, 'test link 9');

insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(1, 1, 'Test game 1');
insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(2, 2, 'Test game 1');
insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(3, 3, 'Test game 1');
insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(1, 4, 'Test game 1');
insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(2, 5, 'Test game 1');
insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(3, 6, 'Test game 1');
insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(1, 7, 'Test game 1');
insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(1, 8, 'Test game 1');
insert into DEVELOPERS_GAMES_ON_PLATFORM VALUES(3, 9, 'Test game 1');




/* ------------------------- */

/* join queries*/
SELECT DISTINCT DEVELOPERS.name AS DEVELOPER_NAME
FROM DEVELOPERS, GAMES1, GAME_LANGUAGES
WHERE GAME_LANGUAGES.voiceLanguage = 'english' AND
      GAMES1.price < 120.00 AND
      DEVELOPERS.developerID = GAMES1.developerID AND
      GAMES1.gameID = GAME_LANGUAGES.gameID
ORDER BY DEVELOPER_NAME;
      

SELECT DISTINCT GAMES1.gameID AS GAME_ID, GAMES1.title AS GAME_TITLE
FROM GAMES1, ANNOUNCEMENT_BOARDS, ANNOUNCEMENTS
WHERE (ANNOUNCEMENT_BOARDS.name LIKE '%Patches%' OR ANNOUNCEMENT_BOARDS.name LIKE '%Updates%') AND
      ANNOUNCEMENTS.datePosted > TO_DATE('01/01/2001', 'DD/MM/YYYY') AND
      GAMES1.gameID = ANNOUNCEMENT_BOARDS.gameID AND
      ANNOUNCEMENT_BOARDS.announcementBoardID = ANNOUNCEMENTS.announcementBoardID
ORDER BY GAMES1.gameID;


/* views */

CREATE VIEW EXPENSIVE_HIGH_MEMORY_GAMES(Game_Title, Game_Price, Game_Memory) AS
    (SELECT title, price, minimumMemory
     FROM GAMES1
     WHERE price > 80.00 AND 
           minimumMemory > 8)
WITH READ ONLY;
 
CREATE VIEW NA_DEVELOPERS(Developer_ID, Developer_Name, Developer_Email, Developer_Address, Developer_Phone, Developer_Country, Developer_Website) AS
    (SELECT *
     FROM DEVELOPERS
     WHERE contactCountry = 'Canada' OR
           contactCountry = 'United States' OR
           contactCountry = 'Mexico'
    )
WITH READ ONLY;

CREATE VIEW USERS_WHO_DEFAULT(User_ID, Username, User_Email) AS
    (SELECT userID, username, emailAddress
     FROM USERS
     WHERE firstName IS NULL AND
           lastName IS NULL AND
           profileDescription IS NULL AND
           profileAvatar IS NULL AND
           profileBackgroundImage IS NULL AND
           profileThemeColor = '111111'
    )
WITH READ ONLY;


/* advanced queries */

/* selects games (title and gameID) that are in at least 2 shopping carts
   but do not have a review rating of less than 2*/
select game.title title, scg.gameID gmid
FROM GAMES1 game, SHOPPING_CART_GAMES scg
where game.gameID = scg.gameID AND 
      NOT EXISTS (
        SELECT rating
        FROM REVIEWS1A
        where REVIEWS1A.gameID = GAME.gameID AND REVIEWS1A.rating < 2
    )
GROUP BY game.title, scg.gameID
HAVING COUNT(scg.cartID) >= 2
order by game.title ASC;

/* selects all players who play both the game  with gameID 1 and game with
    gameID 2*/
select u.username, u.userID FROM USERS u 
WHERE
EXISTS (
select p1.userid FROM plays p1, plays p2 WHERE
p1.userID = p2.userID AND p1.gameID = 1 and p2.gameID = 2 AND u.userID = p1.userID
)
ORDER BY u.username ASC;

/* selects usersd who play game 1 or game 6 */
select u.username, u.userID
FROM USERS u, PLAYS p
WHERE p.userID = u.userID AND p.gameID = 1
UNION
(
select u2.username, u2.userID
FROM USERS u2, PLAYS p2
WHERE p2.userID = u2.userID AND p2.gameID = 6
);

/* selects all games with 2 or more reviews*/
select g.title, g.gameID 
FROM GAMES1 g WHERE EXISTS (
select r.gameID FROM REVIEWS1A r
WHERE r.gameID = g.gameID
GROUP BY r.gameID
HAVING COUNT(r.userID) >= 2
);

/* select all games expect for the ones that are only on a single user's
    wish list*/ 
select g.title, g.gameID FROM GAMES1 g
WHERE NOT EXISTS (
select g2.gameID FROM USER_WISHLISTS g2
WHERE g2.gameID = g.gameID
GROUP BY g2.gameID
HAVING COUNT(g2.userID) = 1
);









