package ir.nimac.controler.dataBase;

public enum QueryString {
    DATABASE_TYPE("jdbc:mysql://"),
    DATABASE_DRIVER_NAME("com.mysql.cj.jdbc.Driver"),
    CREATE_DATABASE_IF_NOT_EXISTS("CREATE DATABASE IF NOT EXISTS "),
    HOST_IP("127.0.0.1"),
    HOST_PORT("3306"),
    USERNAME("bomber"),
    PASSWORD("bomber"),
    DB_NAME("bomber"),
    DATABASE_CONFIG("useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8"),
    CHECK_TABLE_GAME_EXIST(
            "CREATE TABLE IF NOT EXISTS Game (gameId BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,"+
                    "dimX INT,"+
                    "dimY INT,"+
                    "locXDoor INT,"+
                    "locYDoor INT," +
                    "isVisible TINYINT,"+
                    "level INT," +
                    "isPause TINYINT"
                    + ");"),
    ADD_TO_GAME_TABLE("INSERT INTO Game (gameId, dimX, dimY, locXDoor, locYDoor, " +
            "isVisible, level, isPause)" +
            " VALUES (?, ?, ?, ? , ?, " +
            "?, ?, ?);"),
    READ_FROM_GAME_TABLE("SELECT * FROM Game Where gameId = ?;"),
    DELETE_FROM_GAME_TABLE("DELETE FROM Game WHERE gameId = ?;"),
    GET_ALL_MAPS("SELECT * FROM Game;"),

    CHECK_TABLE_PLAYER_EXIST(
            "CREATE TABLE IF NOT EXISTS Player("
                    + "gameId BIGINT NOT NULL,"
                    + " CONSTRAINT Player FOREIGN KEY (gameId) REFERENCES Game (gameId),"
                    + " posX INT,"
                    + " posY INT,"
                    + " id BIGINT,"
                    + " ip varchar(20),"
                    + " name varchar(50),"
                    + " bombLeft INT,"
                    + " isDead TINYINT,"
                    + " level INT,"
                    + " bombRadius INT,"
                    + " bombControl TINYINT,"
                    + " points BIGINT,"
                    + " speed INT,"
                    + " isGost TINYINT,"
                    + " initDate TIMESTAMP"
                    + ") CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
    ADD_TO_PLAYER_TABLE(
            "INSERT INTO Player (gameId, posX, posY, id, ip," +
                    " name, bombLeft, isDead, level, bombRadius," +
                    " bombControl, points, speed, isGost, initDate)" +
                    " VALUES ((SELECT (gameId) FROM Game WHERE gameId Like ?), ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?);"),
    READ_FROM_PLAYER_TABLE("SELECT * FROM Player Where gameId = ?;"),
    DELETE_FROM_PLAYER_TABLE("DELETE FROM Player WHERE gameId = ?;"),
    CHECK_TABLE_ENEMY_EXIST(
            "CREATE TABLE IF NOT EXISTS Enemy("
                    + "gameId BIGINT NOT NULL,"
                    + " CONSTRAINT Enemy FOREIGN KEY (gameId) REFERENCES Game (gameId),"
                    + " posX INT,"
                    + " posY INT,"
                    + " type INT,"
                    + " isDead TINYINT,"
                    + " minLevel INT,"
                    + " isGost TINYINT"
                    + ") CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
    ADD_TO_ENEMY_TABLE(
            "INSERT INTO Enemy (gameId, posX, posY, type," +
                    " isDead, minLevel, isGost)" +
                    " VALUES ((SELECT (gameId) FROM Game WHERE gameId Like ?), ?, ?, ?, ?," +
                    " ?, ?);"),
    READ_FROM_ENEMY_TABLE("SELECT * FROM Enemy Where gameId = ?;"),
    DELETE_FROM_ENEMY_TABLE("DELETE FROM Enemy WHERE gameId = ?;"),
    CHECK_TABLE_BOMB_EXIST(
            "CREATE TABLE IF NOT EXISTS Bomb("
                    + "gameId BIGINT NOT NULL,"
                    + " CONSTRAINT Bomb FOREIGN KEY (gameId) REFERENCES Game (gameId),"
                    + " posX INT,"
                    + " posY INT,"
                    + " ownerId BIGINT,"
                    + " power INT,"
//          + " plantTime TIMESTAMP,"
//          + " runImmediatly TINYINT,"
                    + " remaining BIGINT"
                    + " ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
    ADD_TO_BOMB_TABLE(
            "INSERT INTO Bomb (gameId, posX, posY, ownerId, power," +
                    " remaining)" +
                    " VALUES ((SELECT (gameId) FROM Game WHERE gameId Like ?), ?, ?, ?, ?," +
                    " ?);"),
    READ_FROM_BOMB_TABLE("SELECT * FROM Bomb Where gameId = ?;"),
    DELETE_FROM_BOMB_TABLE("DELETE FROM Bomb WHERE gameId = ?;"),
    CHECK_TABLE_FIRE_EXIST(
            "CREATE TABLE IF NOT EXISTS Fire("
                    + "gameId BIGINT NOT NULL,"
                    + " CONSTRAINT Fire FOREIGN KEY (gameId) REFERENCES Game (gameId),"
                    + " posX INT,"
                    + " posY INT"
                    + " ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
    ADD_TO_FIRE_TABLE(
            "INSERT INTO Fire (gameId, posX, posY)" +
                    " VALUES ((SELECT (gameId) FROM Game WHERE gameId Like ?), ?, ?);"),
    READ_FROM_FIRE_TABLE("SELECT * FROM Fire Where gameId = ?;"),
    DELETE_FROM_FIRE_TABLE("DELETE FROM Fire WHERE gameId = ?;"),
    CHECK_TABLE_POWERUP_EXIST(
            "CREATE TABLE IF NOT EXISTS PowerUp("
                    + "gameId BIGINT NOT NULL,"
                    + " CONSTRAINT PowerUp FOREIGN KEY (gameId) REFERENCES Game (gameId),"
                    + " posX INT,"
                    + " posY INT,"
                    + " type INT,"
                    + " amount INT,"
                    + " visible TINYINT"
                    + " ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
    ADD_TO_POWERUP_TABLE(
            "INSERT INTO PowerUp (gameId, posX, posY, type, amount, visible)" +
                    " VALUES ((SELECT (gameId) FROM Game WHERE gameId Like ?), ?, ?, ?, ?, ?);"),
    READ_FROM_POWERUP_TABLE("SELECT * FROM PowerUp Where gameId = ?;"),
    DELETE_FROM_POWERUP_TABLE("DELETE FROM PowerUp WHERE gameId = ?;"),
    CHECK_TABLE_WALL_EXIST(
            "CREATE TABLE IF NOT EXISTS Wall("
                    + "gameId BIGINT NOT NULL,"
                    + " CONSTRAINT Wall FOREIGN KEY (gameId) REFERENCES Game (gameId),"
                    + " posX INT,"
                    + " posY INT"
                    + " ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"),
    ADD_TO_WALL_TABLE(
            "INSERT INTO Wall (gameId, posX, posY)" +
                    " VALUES ((SELECT (gameId) FROM Game WHERE gameId Like ?), ?, ?);"),
    READ_FROM_WALL_TABLE("SELECT * FROM Wall Where gameId = ?;"),
    DELETE_FROM_WALL_TABLE("DELETE FROM Wall WHERE gameId = ?;");


    private static final String checkUrlExistInWebSiteTable =
            "SELECT * FROM WebSite WHERE url LIKE ?";
    private static final String addUrlToWebSiteTable =
            "INSERT INTO WebSite (url, class) VALUES (?,?)";
    private static final String updateUrlClassTagWebSiteTable =
            "UPDATE WebSite SET class=? WHERE url=?";
    private static final String getAllWebSitesFromWebSiteTable = "SELECT * FROM WebSite";
    private static final String selectWebSitesFromWebSiteTable =
            "SELECT * FROM WebSite WHERE url LIKE ?";
    private static final String addRSSItemToRSSItemsTable =
            "INSERT  INTO RssItem "
                    + "(title, description, link, pubDate, article, newsWebPage) VALUES "
                    + "(?, ?, ?, ?, ?, (SELECT (url) FROM WebSite WHERE url LIKE ?))";
    private static final String selectRssItemFromRssTableByWebPage =
            "SELECT * FROM RssItem WHERE newsWebPage LIKE ?";
    private static final String selectRssByLink = "SELECT * FROM RssItem WHERE link LIKE ?";
    private static final String selectAllRss = "SELECT * FROM RssItem";

    String query;

    QueryString(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return query;
    }
}
