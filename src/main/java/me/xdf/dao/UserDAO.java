package me.xdf.dao;
import me.xdf.bean.User;
import me.xdf.service.DBUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDAO {
    // 添加用户
    public static boolean check_password(String username, String password){
        List<User> userList =  UserDAO.getUserByUsername(username);

        // 检查列表是否为空
        if (userList != null && !userList.isEmpty()) {
            return UserDAO.checkPassword(password, userList.get(0).getPassword());
        } else {
            // 处理列表为空的情况，可能是返回 false 或者抛出异常，取决于业务逻辑
            return false;
        }

    }
    public void addUser(User user) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User_Database.user (username, password, gender, email, age, image, salt) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getGender());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getAge());
            preparedStatement.setString(6, user.getImagePath());
            preparedStatement.setString(7, user.getSalt());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 获取所有用户
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User_Database.user");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                String image = resultSet.getString("image");
                User user = new User(id, username, password, gender, email, age, image);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public int getTotalUsers() {
       int sum = 0;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User_Database.user");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
               sum++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    // 根据ID获取用户
    public List<User> getUserById(int userId) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User_Database.user WHERE id = ?");
        ) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String gender = resultSet.getString("gender");
                    String email = resultSet.getString("email");
                    int age = resultSet.getInt("age");
                    String image = resultSet.getString("image");
                    userList.add(new User(id, username, password, gender, email, age, image));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // 根据用户名获取用户
    public static List<User> getUserByUsername(String uname) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User_Database.user WHERE username = ?");
        ) {
            preparedStatement.setString(1, uname);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String gender = resultSet.getString("gender");
                    String email = resultSet.getString("email");
                    int age = resultSet.getInt("age");
                    String image = resultSet.getString("image");
                    userList.add( new User(id, username, password, gender, email, age, image)) ;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // 更新用户信息
    public void updateUser(User user) {
        try (Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE User_Database.user SET username=?, password=?, gender=?, email=?, age=?, image=?, salt=? WHERE id=?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getGender());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getAge());
            preparedStatement.setString(6, user.getImagePath());
            preparedStatement.setString(7, user.getSalt());
            preparedStatement.setInt(8, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    public void updateUserNoPwd(User user) {
        try (Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE User_Database.user SET username=?,  gender=?, email=?, age=?, image=?WHERE id=?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getGender());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getImagePath());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 删除用户
    public void deleteUserAndReuseId(int userId) {
        try (Connection connection = DBUtil.getConnection()) {
            // Disable auto-increment temporarily
            disableAutoIncrement(connection);
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM User_Database.user WHERE id=?")) {
                preparedStatement.setInt(1, userId);
                preparedStatement.executeUpdate();
            }
            enableAutoIncrement(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void disableAutoIncrement(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("ALTER TABLE User_Database.user AUTO_INCREMENT = 1")) {
            preparedStatement.executeUpdate();
        }
    }
    private void enableAutoIncrement(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("ALTER TABLE User_Database.user AUTO_INCREMENT = ?")) {
            int nextId = findMaxUserId(connection) + 1;
            preparedStatement.setInt(1, nextId);
            preparedStatement.executeUpdate();
        }
    }
    private int findMaxUserId(Connection connection) throws SQLException {
        int maxId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) AS max_id FROM User_Database.user")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maxId = resultSet.getInt("max_id");
                }
            }
        }
        return maxId;
    }
    // 检查用户名是否已存在
    public static boolean isUsernameExists(String username) {
        boolean usernameExists = false;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User_Database.user WHERE username = ?")) {
            preparedStatement.setString(1, username);
            System.out.println("方法中输出:"+username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // 如果查询结果包含记录，则用户名已存在
                if (resultSet.next()) {
                    usernameExists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernameExists;
    }
    // 根据用户名进行模糊查询用户列表
    public static List<User> getUsersByUsername(String username) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User_Database.user WHERE username LIKE ?")) {
            preparedStatement.setString(1, "%" + username + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String fetchedUsername = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String gender = resultSet.getString("gender");
                    String email = resultSet.getString("email");
                    String image = resultSet.getString("image");
                    int age = resultSet.getInt("age");
                    User user = new User(id, fetchedUsername, password, gender, email, age, image);
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }


    //获取
    public List<User> getUsersPaginated(int pageNo, int pageSize) {
        List<User> userList = new ArrayList<>();
        if (pageNo < 1) {
            pageNo = 1;
        }
        int start = (pageNo - 1) * pageSize;
        try (
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user LIMIT ?, ?")) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, pageSize);
            System.out.println("+++++++++++"+start+"+++++++++"+pageSize+"++++++++"+pageNo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setGender(resultSet.getString("gender"));
                    user.setEmail(resultSet.getString("email"));
                    user.setAge(resultSet.getInt("age"));
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }


    // 方法以获取用户表中的总记录数
    public int getNoOfRecords() {
        int recordCount = 0;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM user")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                recordCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordCount;
    }

    //生成盐
    public static String generateSalt() {
        return BCrypt.gensalt();
    }

    // 对密码进行加盐哈希处理
    public static String hashPassword(String plainPassword, String salt) {
        return BCrypt.hashpw(plainPassword, salt);
    }

    // 验证密码
//    public static boolean checkPassword(String userInputPassword, String hashedPassword, String salt) {
//        String computedHash = BCrypt.hashpw(userInputPassword, salt);
//        return computedHash.equals(hashedPassword);
//    }
    public static boolean checkPassword(String userInputPassword, String hashedPassword) {
        return BCrypt.checkpw(userInputPassword, hashedPassword);
    }

}