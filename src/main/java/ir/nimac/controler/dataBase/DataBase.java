package ir.nimac.controler.dataBase;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import ir.nimac.Logger;
import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.*;
import ir.nimac.model.Clevel.powerups.PowerUp;
import ir.nimac.model.map.Map;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ir.nimac.controler.dataBase.QueryString.*;

public class DataBase {

    /**
     * a table for user/ enemy/ powerups/ walls
     * a table for game with uniq id maker for game, user;
     */
    private static DataBase dataBase = null;
    private ComboPooledDataSource comboPooledDataSource;


    private DataBase() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass(DATABASE_DRIVER_NAME.toString());
            comboPooledDataSource.setJdbcUrl(
                    DATABASE_TYPE.toString()
                            + HOST_IP.toString()
                            + ":"
                            + HOST_PORT.toString()
                            + "?"
                            + DATABASE_CONFIG.toString());
            comboPooledDataSource.setUser(USERNAME.toString());
            comboPooledDataSource.setPassword(PASSWORD.toString());
            comboPooledDataSource.setMaxStatements(100);
            comboPooledDataSource.setMaxStatementsPerConnection(50);
            comboPooledDataSource.setAutoCommitOnClose(true);
        } catch (PropertyVetoException e) {
            Logger.getInstance().debugLog(e.getMessage());
        }

        try (Connection connection = comboPooledDataSource.getConnection()) {
            connection
                    .createStatement()
                    .execute(
                            CREATE_DATABASE_IF_NOT_EXISTS.toString()
                                    + DB_NAME.toString()
                                    + ";");
            connection.createStatement().execute("USE " + DB_NAME.toString() + ";");
            buildTable(connection);
        } catch (SQLException e) {
            Logger.getInstance().debugLog(e.getMessage());
        }
    }

    public static DataBase getInstance() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public void saveMap(Map map) {
        try (Connection connection = getConnection()) {
            deletePlayer(connection, map.getGameId());
            deleteEnemy(connection, map.getGameId());
            deleteBomb(connection, map.getGameId());
            deletePowerUp(connection, map.getGameId());
            deleteFire(connection, map.getGameId());
            deleteWall(connection, map.getGameId());
            deleteGame(connection, map.getGameId());
            saveGame(connection, map);
            savePlayers(connection, map);
            saveEnemy(connection, map);
            saveBomb(connection, map);
            savePowerUp(connection, map);
            saveFire(connection, map);
            saveWall(connection, map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(long mapId, Map map) {
        map.setGameId(mapId);
        try (Connection connection = getConnection()) {
            loadGame(connection, map);
            loadPlayer(connection, map);
            loadEnemy(connection, map);
            loadBomb(connection, map);
            loadPowerUp(connection, map);
            loadFire(connection, map);
            loadWall(connection, map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Long> getAllMaps() {
        ArrayList<Long> maps = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_MAPS.toString());
            resultSet.beforeFirst();
            while (resultSet.next()) {
                maps.add(resultSet.getLong("gameId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maps;

    }

    private void buildTable(Connection connection) throws SQLException {
        connection
                .createStatement()
                .execute(CHECK_TABLE_GAME_EXIST.toString());
        connection
                .createStatement()
                .execute(CHECK_TABLE_PLAYER_EXIST.toString());
        connection
                .createStatement()
                .execute(CHECK_TABLE_ENEMY_EXIST.toString());
        connection
                .createStatement()
                .execute(CHECK_TABLE_POWERUP_EXIST.toString());
        connection
                .createStatement()
                .execute(CHECK_TABLE_BOMB_EXIST.toString());
        connection
                .createStatement()
                .execute(CHECK_TABLE_WALL_EXIST.toString());
        connection
                .createStatement()
                .execute(CHECK_TABLE_FIRE_EXIST.toString());
    }

    public synchronized Connection getConnection() {
        Connection connection = null;
        try {
            connection = comboPooledDataSource.getConnection();
            connection.createStatement().execute("USE " + DB_NAME.toString() + ";");
            return connection;
        } catch (SQLException e) {
            Logger.getInstance().debugLog(e.getMessage());
        }
        return connection;
    }


    private void loadGame(Connection connection, Map map) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(READ_FROM_GAME_TABLE.toString());
        preparedStatement.setLong(1, map.getGameId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            map.setMapDimention(resultSet.getInt("dimX"), resultSet.getInt("dimY"));
            map.setLevel(resultSet.getInt("level"));
            map.setGamePause(resultSet.getBoolean("isPause"));
            map.reset();
            Door door = new Door(new Location(resultSet.getInt("locXDoor"),
                    resultSet.getInt("locYDoor")), map);
            door.setVisible(resultSet.getBoolean("isVisible"));
            map.doorList.add(door);
        }
    }

    private void saveGame(Connection connection, Map map) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_TO_GAME_TABLE.toString());
        statement.setLong(1, map.getGameId());
        statement.setInt(2, map.getMapWidth());
        statement.setInt(3, map.getMapHeight());
        statement.setInt(4, map.doorList.get(0).location.getX());
        statement.setInt(5, map.doorList.get(0).location.getX());
        statement.setBoolean(6, map.doorList.get(0).isVisible());
        statement.setInt(7, map.getLevel());
        statement.setBoolean(8, map.isGamePause());
        statement.execute();
    }

    private void deleteGame(Connection connection, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_FROM_GAME_TABLE.toString());
        statement.setLong(1, id);
        statement.execute();
    }

    private void loadPlayer(Connection connection, Map map) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(READ_FROM_PLAYER_TABLE.toString());
        preparedStatement.setLong(1, map.getGameId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            Player player = new Player(new Location(resultSet.getInt("posX"), resultSet.getInt("posY")),
                    resultSet.getLong("id"), resultSet.getString("ip"), resultSet.getString("name"),
                    resultSet.getInt("bombLeft"), resultSet.getBoolean("isDead"),
                    resultSet.getInt("level"), resultSet.getInt("bombRadius"),
                    resultSet.getBoolean("bombControl"), resultSet.getLong("points"),
                    resultSet.getInt("speed"), resultSet.getBoolean("isGost"),
                    resultSet.getTimestamp("initDate"), map);
            map.getMe().setPlayer(player);
            map.playerList.add(player);
        }
    }

    private void savePlayers(Connection connection, Map map) throws SQLException {
        for (int i = 0; i < map.playerList.size(); i++) {
            Player u = map.playerList.get(i);
            PreparedStatement statement = connection.prepareStatement(ADD_TO_PLAYER_TABLE.toString());
            statement.setLong(1, map.getGameId());
            statement.setInt(2, u.location.getX());
            statement.setInt(3, u.location.getY());
            statement.setLong(4, u.getId());
            statement.setString(5, u.getIp());
            statement.setString(6, u.getName());
            statement.setInt(7, u.getBombLeft());
            statement.setBoolean(8, u.isDead());
            statement.setInt(9, u.getLevel());
            statement.setInt(10, u.getBombRadius());
            statement.setBoolean(11, u.isBombControl());
            statement.setLong(12, u.getPoints());
            statement.setInt(13, u.getSpeed());
            statement.setBoolean(14, u.isGost());
            statement.setTimestamp(15, u.getInitDate());
            statement.execute();
        }
    }

    private void deletePlayer(Connection connection, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_FROM_PLAYER_TABLE.toString());
        statement.setLong(1, id);
        statement.execute();
    }

    private void loadEnemy(Connection connection, Map map) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(READ_FROM_ENEMY_TABLE.toString());
        preparedStatement.setLong(1, map.getGameId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            Enemy enemy = EnemyFactory.build(new Location(resultSet.getInt("posX"), resultSet.getInt("posY")),
                    resultSet.getInt("type"), map);
            map.enemyList.add(enemy);
        }
    }

    private void saveEnemy(Connection connection, Map map) throws SQLException {
        for (int i = 0; i < map.enemyList.size(); i++) {
            Enemy e = map.enemyList.get(i);
            PreparedStatement statement = connection.prepareStatement(ADD_TO_ENEMY_TABLE.toString());
            statement.setLong(1, map.getGameId());
            statement.setInt(2, e.location.getX());
            statement.setInt(3, e.location.getY());
            statement.setInt(4, e.getType());
            statement.setBoolean(5, e.isDead());
            statement.setInt(6, e.getMinLevel());
            statement.setBoolean(7, e.isGost());
            statement.execute();
        }
    }

    private void deleteEnemy(Connection connection, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_FROM_ENEMY_TABLE.toString());
        statement.setLong(1, id);
        statement.execute();
    }

    private void loadBomb(Connection connection, Map map) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(READ_FROM_BOMB_TABLE.toString());
        preparedStatement.setLong(1, map.getGameId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            Bomb bomb = new Bomb(new Location(resultSet.getInt("posX"), resultSet.getInt("posY")),
                    resultSet.getLong("ownerId"), resultSet.getInt("power"),
                    resultSet.getLong("remaining"), map);
            map.bombList.add(bomb);
        }
    }

    private void saveBomb(Connection connection, Map map) throws SQLException {
        for (int i = 0; i < map.bombList.size(); i++) {
            Bomb b = map.bombList.get(i);
            PreparedStatement statement = connection.prepareStatement(ADD_TO_BOMB_TABLE.toString());
            statement.setLong(1, map.getGameId());
            statement.setInt(2, b.location.getX());
            statement.setInt(3, b.location.getY());
            statement.setLong(4, b.getOwnerId());
            statement.setInt(5, b.getPower());
            statement.setLong(6, b.getRemaining());
            statement.execute();
        }
    }

    private void deleteBomb(Connection connection, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_FROM_BOMB_TABLE.toString());
        statement.setLong(1, id);
        statement.execute();
    }

    private void loadFire(Connection connection, Map map) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(READ_FROM_FIRE_TABLE.toString());
        preparedStatement.setLong(1, map.getGameId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            Fire fire = new Fire(new Location(resultSet.getInt("posX"), resultSet.getInt("posY")), map);
            map.fireList.add(fire);
        }
    }

    private void saveFire(Connection connection, Map map) throws SQLException {
        for (int i = 0; i < map.fireList.size(); i++) {
            Fire f = map.fireList.get(i);
            PreparedStatement statement = connection.prepareStatement(ADD_TO_FIRE_TABLE.toString());
            statement.setLong(1, map.getGameId());
            statement.setInt(2, f.location.getX());
            statement.setInt(3, f.location.getY());
            statement.execute();
        }
    }

    private void deleteFire(Connection connection, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_FROM_FIRE_TABLE.toString());
        statement.setLong(1, id);
        statement.execute();
    }

    private void loadPowerUp(Connection connection, Map map) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(READ_FROM_POWERUP_TABLE.toString());
        preparedStatement.setLong(1, map.getGameId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            String[] mys = new String[5];
            mys[0] = String.valueOf(resultSet.getInt("posX"));
            mys[1] = String.valueOf(resultSet.getInt("posY"));
            mys[2] = String.valueOf(resultSet.getInt("type"));
            mys[3] = String.valueOf(resultSet.getInt("amount"));
            mys[4] = String.valueOf(resultSet.getBoolean("visible"));
            PowerUp powerUp = PowerUp.makeInstance(mys, 0, map);
            map.powerUpList.add(powerUp);
        }
    }

    private void savePowerUp(Connection connection, Map map) throws SQLException {
        for (int i = 0; i < map.powerUpList.size(); i++) {
            PowerUp p = map.powerUpList.get(i);
            PreparedStatement statement = connection.prepareStatement(ADD_TO_POWERUP_TABLE.toString());
            statement.setLong(1, map.getGameId());
            statement.setInt(2, p.location.getX());
            statement.setInt(3, p.location.getY());
            statement.setLong(4, p.getType());
            statement.setInt(5, p.getEffectAmount());
            statement.setBoolean(6, p.isVisible());
            statement.execute();
        }
    }

    private void deletePowerUp(Connection connection, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_FROM_POWERUP_TABLE.toString());
        statement.setLong(1, id);
        statement.execute();
    }

    private void loadWall(Connection connection, Map map) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(READ_FROM_WALL_TABLE.toString());
        preparedStatement.setLong(1, map.getGameId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            Wall wall = new Wall(new Location(resultSet.getInt("posX"), resultSet.getInt("posY")),
                    map);
            map.wallList.add(wall);
        }
    }

    private void saveWall(Connection connection, Map map) throws SQLException {
        for (int i = 0; i < map.wallList.size(); i++) {
            Wall w = map.wallList.get(i);
            PreparedStatement statement = connection.prepareStatement(ADD_TO_WALL_TABLE.toString());
            statement.setLong(1, map.getGameId());
            statement.setInt(2, w.location.getX());
            statement.setInt(3, w.location.getY());
            statement.execute();
        }
    }

    private void deleteWall(Connection connection, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_FROM_WALL_TABLE.toString());
        statement.setLong(1, id);
        statement.execute();
    }
}
