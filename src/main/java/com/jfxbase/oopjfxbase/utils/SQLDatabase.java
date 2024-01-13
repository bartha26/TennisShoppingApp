package com.jfxbase.oopjfxbase.utils;

import SimpleClasses.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.*;

public class SQLDatabase {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/tennis";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "tudor123";
    private static final String INSERT_ORDER_QUERY = "INSERT INTO orders (user_id, shippingaddress, price) VALUES (?, ?, ?)";
    private static final String INSERT_PRORDER_QUERY = "INSERT INTO productsorder (order_id, product_id, quantity, color_id) VALUES(?, ?, ?, ?)";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String FIND_USER_ID_QUERY = "SELECT user_id FROM users WHERE user_name = ?";
    private static final String FIND_PRODUCT_ID_QUERY = "SELECT product_id FROM products WHERE product_name = ?";
    private static final String FIND_COLOR_ID = "select c.color_id from color c where c.color_name = ?";
    private static final String INSERT_REVIEW_QUERY = "INSERT INTO review (rating, comments, product_id, user_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_PASSWORD_QUERY = "UPDATE users SET user_password = ? WHERE user_name = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO users (user_name, user_password) VALUES (?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE user_name = ?";
    private static final String AUTHENTICATION_QUERY = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
    private static final String FIND_REVIEW_QUERY = "select * from review r where r.user_id = ? and r.product_id = ?";
    private static final String MY_REVIEWS_QUERY = "select * from review r join users u on r.user_id = u.user_id join products p on p.product_id =r.user_id where r.user_id = ?";
    private static final String FIND_USER_ID = "select * from users where user_name = ?";
    private static final String QUERY_SHIRT = "select * from products p join shirts s on p.product_id = s.shirt_id join company c on c.company_id = p.company_id  where p.product_name = ?";
    private static final String QUERY_SHORTS = "select * from products p join shorts s on p.product_id = s.short_id join company c on c.company_id = p.company_id where p.product_name = ?";
    private static final String QUERY_SHOES = "select * from products p join shoes s on p.product_id = s.shoes_id join company c on c.company_id = p.company_id  where p.product_name = ?";
    private static final String QUERY_RACKETS = "select * from products p join rackets r on r.rackets_id =p.product_id join company c on c.company_id =p.company_id  where p.product_name = ?";
    private static final String SELECT_ORDERS = "SELECT order_id, user_id, price, date, shippingaddress FROM orders where user_id = ?";
    private static final String SELECT_PRODUCTS_ORDER = "SELECT order_id, product_id, quantity, color_id FROM productsorder where order_id = ?";

    public Integer fetchDataUserId(String name) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_ID)) {

            preparedStatement.setString(1, name);
            Integer id = 0;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getInt("user_id");
                }
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Review> fetchDataFromDatabaseMyReviews(Integer userId1) {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(MY_REVIEWS_QUERY)) {
                preparedStatement.setInt(1, userId1);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public boolean findReview(Integer userId, Integer productId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_REVIEW_QUERY)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATION_QUERY)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If there is at least one row in the result set, the username and password match
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean registerUser(String username, String password) {
        // Check if the username begins with a letter
        if(username.isEmpty() || password.isEmpty())
            return false;
        if (!Character.isLetter(username.charAt(0))) {
            System.out.println("Username must begin with a letter.");
            return false;
        }

        // Check if the password contains both a letter and a number
        if (!(password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*"))) {
            System.out.println("Password must contain both a letter and a number.");
            return false;
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY)) {

            // Set parameters in the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query to insert a new user
            int rowsAffected = preparedStatement.executeUpdate();

            // If at least one row is affected, the user is registered successfully
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error registering user", e);
        }
    }

    public static boolean deleteUser(String username) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {

            // Set parameters in the prepared statement
            preparedStatement.setString(1, username);

            // Execute the query to delete the user
            int rowsAffected = preparedStatement.executeUpdate();

            // If at least one row is affected, the user is deleted successfully
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public static boolean updatePassword(String username, String newPassword) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_QUERY)) {

            // Check if the password contains both a letter and a number for the new password
            if (!(newPassword.matches(".*[a-zA-Z].*") && newPassword.matches(".*\\d.*"))) {
                System.out.println("New password must contain both a letter and a number.");
                return false;
            }

            // Set parameters in the prepared statement
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);

            // Execute the query to update the user's password
            int rowsAffected = preparedStatement.executeUpdate();

            // If at least one row is affected, the password is updated successfully
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error updating password", e);
        }
    }

    public ObservableList<Users> fetchDataFromDatabase() {
        ObservableList<Users> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("user_id");
                    String name = resultSet.getString("user_name");
                    String password = resultSet.getString("user_password");
                    Users user = new Users(name, password);
                    user.setId(id);
                    data.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void configureTableColumns(TableView<Users> table) {
        TableColumn<Users, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Users, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser_name()));

        TableColumn<Users, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));

        table.getColumns().addAll(idColumn, nameColumn, passwordColumn);
    }

    public ObservableList<Review> fetchDataFromDatabaseSpecificProductReview(String productName) {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM review r join products p on p.product_id = r.product_id join users u on u.user_id = r.user_id WHERE r.product_id = (SELECT product_id FROM products p WHERE p.product_name = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, productName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName1 = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName1, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Review> fetchDataFromDatabaseBrandReview(String brandName) {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM review r JOIN products p ON r.product_id = p.product_id JOIN company c ON p.company_id = c.company_id join users u on u.user_id = r.user_id WHERE c.company_name = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, brandName);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Review> fetchDataFromDatabaseBrandProductReview(String brandName, String productName) {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM review r JOIN products p ON r.product_id = p.product_id JOIN company c ON p.company_id = c.company_id join users u on u.user_id = r.user_id WHERE c.company_name = ? AND p.product_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, brandName);
                preparedStatement.setString(2, productName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName1 = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName1, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Review> fetchDataFromDatabaseRatingReview() {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM review r join products p on p.product_id = r.product_id join users u on u.user_id = r.user_id ORDER BY r.rating DESC";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName1 = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName1, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList<Review> fetchDataFromDatabaseShoesReview() {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM review r JOIN shoes s ON s.shoes_id=r.product_id join products p on p.product_id = r.product_id join users u on u.user_id = r.user_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName1 = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName1, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Review> fetchDataFromDatabaseShortsReview() {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM review r JOIN shorts s ON s.short_id=r.product_id join products p on p.product_id = r.product_id join users u on u.user_id = r.user_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName1 = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName1, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Review> fetchDataFromDatabaseShirtReview() {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM review r JOIN shirts s ON s.shirt_id=r.product_id join products p on p.product_id = r.product_id join users u on u.user_id = r.user_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName1 = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName1, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Review> fetchDataFromDatabaseRacketsReview() {
        ObservableList<Review> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM review r JOIN rackets r2 ON r2.rackets_id=r.product_id join products p on p.product_id = r.product_id join users u on u.user_id = r.user_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer reviewId = resultSet.getInt("review_id");
                        Integer rating = resultSet.getInt("rating");
                        Integer userId = resultSet.getInt("user_id");
                        String comments = resultSet.getString("comments");
                        Integer productId = resultSet.getInt("product_id");
                        String productName1 = resultSet.getString("product_name");
                        String userName = resultSet.getString("user_name");

                        Review review = new Review(rating, userName, comments, productName1, productId, userId);
                        review.setReviewId(reviewId);

                        data.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void configureTableColumnsReview(TableView<Review> table) {
        TableColumn<Review, Integer> idColumn = new TableColumn<>("Review ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getReviewId()).asObject());

        TableColumn<Review, Integer> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRating()).asObject());

        TableColumn<Review, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());

        TableColumn<Review, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));

        TableColumn<Review, String> commentsColumn = new TableColumn<>("Comments");
        commentsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComments()));

        TableColumn<Review, Integer> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        TableColumn<Review, String> productColumn = new TableColumn<>("Product Name");
        productColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));

        table.getColumns().addAll(idColumn, ratingColumn, userIdColumn, userNameColumn, commentsColumn, productIdColumn, productColumn);
    }

    public ObservableList<Racket> fetchDataFromDatabaseRackets(String query, Integer min, Integer max) {
        ObservableList<Racket> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, min);
                preparedStatement.setInt(2, max);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer racketId = resultSet.getInt("product_id");
                        Double price = resultSet.getDouble("price");
                        String brand = resultSet.getString("company_name");
                        Integer brandId = resultSet.getInt("company_id");
                        String name = resultSet.getString("product_name");
                        Integer headSize = resultSet.getInt("headsize");

                        Racket racket = new Racket(racketId, price, brandId, name, brand, headSize);
                        data.add(racket);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Racket> fetchDataFromDatabaseRacketsName(String name1) {
        ObservableList<Racket> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_RACKETS)) {
                preparedStatement.setString(1, name1);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer racketId = resultSet.getInt("product_id");
                        Double price = resultSet.getDouble("price");
                        String brand = resultSet.getString("company_name");
                        Integer brandId = resultSet.getInt("company_id");
                        String name = resultSet.getString("product_name");
                        Integer headSize = resultSet.getInt("headsize");

                        Racket racket = new Racket(racketId, price, brandId, name, brand, headSize);
                        data.add(racket);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void configureTableColumnsRackets(TableView<Racket> table) {
        TableColumn<Racket, Integer> racketIdColumn = new TableColumn<>("Racket ID");
        racketIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        TableColumn<Racket, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Racket, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Racket, Integer> brandIdColumn = new TableColumn<>("Brand ID");
        brandIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Racket, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));

        TableColumn<Racket, Integer> headSizeColumn = new TableColumn<>("Head Size");
        headSizeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHeadSize()).asObject());

        table.getColumns().addAll(racketIdColumn, priceColumn, brandColumn, brandIdColumn, nameColumn, headSizeColumn);
    }

    public ObservableList<Shoes> fetchDataFromDatabaseShoes(String query, Integer min, Integer max) {
        ObservableList<Shoes> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, min);
                preparedStatement.setInt(2, max);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer shoeId = resultSet.getInt("product_id");
                        Double price = resultSet.getDouble("price");
                        String brand = resultSet.getString("company_name");
                        Integer brandId = resultSet.getInt("company_id");
                        String name = resultSet.getString("product_name");

                        Shoes shoe = new Shoes(shoeId, price, brandId, name, brand);
                        data.add(shoe);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Shoes> fetchDataFromDatabaseShoesName(String name1) {
        ObservableList<Shoes> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SHOES)) {
                preparedStatement.setString(1, name1);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer shoeId = resultSet.getInt("product_id");
                        Double price = resultSet.getDouble("price");
                        String brand = resultSet.getString("company_name");
                        Integer brandId = resultSet.getInt("company_id");
                        String name = resultSet.getString("product_name");

                        Shoes shoe = new Shoes(shoeId, price, brandId, name, brand);
                        data.add(shoe);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void configureTableColumnsShoes(TableView<Shoes> table) {
        TableColumn<Shoes, Integer> shoesIdColumn = new TableColumn<>("Shoes ID");
        shoesIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        TableColumn<Shoes, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Shoes, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Shoes, Integer> brandIdColumn = new TableColumn<>("Brand ID");
        brandIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Shoes, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));

        table.getColumns().addAll(shoesIdColumn, priceColumn, brandColumn, brandIdColumn, nameColumn);
    }

    public ObservableList<Shorts> fetchDataFromDatabaseShorts(String query, Integer min, Integer max) {
        ObservableList<Shorts> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, min);
                preparedStatement.setInt(2, max);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer shoeId = resultSet.getInt("product_id");
                        Double price = resultSet.getDouble("price");
                        String brand = resultSet.getString("company_name");
                        Integer brandId = resultSet.getInt("company_id");
                        String name = resultSet.getString("product_name");
                        String shortsLength = resultSet.getString("short_length");
                        Shorts shorts = new Shorts(shoeId, price, brandId, name, brand, shortsLength);
                        data.add(shorts);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Shorts> fetchDataFromDatabaseShortsName(String name1) {
        ObservableList<Shorts> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SHORTS)) {
                preparedStatement.setString(1, name1);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer shoeId = resultSet.getInt("product_id");
                        Double price = resultSet.getDouble("price");
                        String brand = resultSet.getString("company_name");
                        Integer brandId = resultSet.getInt("company_id");
                        String name = resultSet.getString("product_name");
                        String shortsLength = resultSet.getString("short_length");
                        Shorts shorts = new Shorts(shoeId, price, brandId, name, brand, shortsLength);
                        data.add(shorts);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void configureTableColumnsShorts(TableView<Shorts> table) {
        TableColumn<Shorts, Integer> shoesIdColumn = new TableColumn<>("Shoes ID");
        shoesIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        TableColumn<Shorts, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Shorts, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Shorts, Integer> brandIdColumn = new TableColumn<>("Brand ID");
        brandIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Shorts, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));

        TableColumn<Shorts, String> lengthColumn = new TableColumn<>("ShortsLength");
        lengthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShortsLength()));

        table.getColumns().addAll(shoesIdColumn, priceColumn, brandColumn, brandIdColumn, nameColumn, lengthColumn);
    }

    public ObservableList<Shirt> fetchDataFromDatabaseShirt(String query, Integer min, Integer max) {
        ObservableList<Shirt> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, min);
                preparedStatement.setInt(2, max);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer shirtId = resultSet.getInt("product_id");
                        Double price = resultSet.getDouble("price");
                        String brand = resultSet.getString("company_name");
                        Integer brandId = resultSet.getInt("company_id");
                        String name = resultSet.getString("product_name");

                        Shirt shirt = new Shirt(shirtId, price, brandId, name, brand);
                        data.add(shirt);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<Shirt> fetchDataFromDatabaseShirtName(String name1) {
        ObservableList<Shirt> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SHIRT)) {
                preparedStatement.setString(1, name1);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer shirtId = resultSet.getInt("product_id");
                        Double price = resultSet.getDouble("price");
                        String brand = resultSet.getString("company_name");
                        Integer brandId = resultSet.getInt("company_id");
                        String name = resultSet.getString("product_name");

                        Shirt shirt = new Shirt(shirtId, price, brandId, name, brand);
                        data.add(shirt);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void configureTableColumnsShirt(TableView<Shirt> table) {
        TableColumn<Shirt, Integer> shirtIdColumn = new TableColumn<>("Shirt ID");
        shirtIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        TableColumn<Shirt, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Shirt, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Shirt, Integer> brandIdColumn = new TableColumn<>("Brand ID");
        brandIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Shirt, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));

        table.getColumns().addAll(shirtIdColumn, priceColumn, brandColumn, brandIdColumn, nameColumn);
    }

    public ObservableList<ColorProducts> fetchDataFromDatabaseColor(String query, Integer minPrice, Integer maxPrice) {
        ObservableList<ColorProducts> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, maxPrice);
                preparedStatement.setInt(2, minPrice);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer productId = resultSet.getInt("product_id");
                        String color = resultSet.getString("color_name");
                        ColorProducts colorProducts = new ColorProducts(productId, color);
                        data.add(colorProducts);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<ColorProducts> fetchDataFromDatabaseColorName(String query, String name) {
        ObservableList<ColorProducts> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer productId = resultSet.getInt("product_id");
                        String color = resultSet.getString("color_name");
                        ColorProducts colorProducts = new ColorProducts(productId, color);
                        data.add(colorProducts);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void configureTableColumnsColor(TableView<ColorProducts> table) {
        TableColumn<ColorProducts, Integer> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        TableColumn<ColorProducts, String> colorColumn = new TableColumn<>("Color");
        colorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));

        table.getColumns().addAll(productIdColumn, colorColumn);
    }

    public ObservableList<ProductSize> fetchDataFromDatabaseSize(String query, String id, String size1, Integer minPrice, Integer maxPrice) {
        ObservableList<ProductSize> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, maxPrice);
                preparedStatement.setInt(2, minPrice);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer productId = resultSet.getInt(id);
                        String size = resultSet.getString(size1);
                        Integer quantity = resultSet.getInt("quantity");
                        ProductSize productSize = new ProductSize(productId, size, quantity);
                        data.add(productSize);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ObservableList<ProductSize> fetchDataFromDatabaseSizeName(String query, String id, String size1, String name) {
        ObservableList<ProductSize> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer productId = resultSet.getInt(id);
                        String size = resultSet.getString(size1);
                        Integer quantity = resultSet.getInt("quantity");
                        ProductSize productSize = new ProductSize(productId, size, quantity);
                        data.add(productSize);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void configureTableColumnsSize(TableView<ProductSize> table) {
        TableColumn<ProductSize, Integer> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getShoeId()).asObject());

        TableColumn<ProductSize, String> sizeColumn = new TableColumn<>("size/weight");
        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSize()));

        TableColumn<ProductSize, Integer> quantityColumn = new TableColumn<>("quantity");
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        table.getColumns().addAll(productIdColumn, sizeColumn, quantityColumn);
    }

    public static boolean addReview(Review review) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REVIEW_QUERY)) {

            // Set parameters in the prepared statement
            preparedStatement.setInt(1, review.getRating());
            preparedStatement.setString(2, review.getComments());
            preparedStatement.setInt(3, review.getProductId());
            preparedStatement.setInt(4, review.getUserId());

            // Execute the query to insert a new review
            int rowsAffected = preparedStatement.executeUpdate();

            // If at least one row is affected, the review is added successfully
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error adding review", e);
        }
    }

    public static Integer findProductId(String productName) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_ID_QUERY)) {

            // Set the product_name parameter in the prepared statement
            preparedStatement.setString(1, productName);

            // Execute the query to find the product_id
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Return the product_id if a match is found
                    return resultSet.getInt("product_id");
                }
            }

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error finding product_id", e);
        }

        // Return null if no match is found
        return null;
    }

    public static Integer findUserId(String productName) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_ID_QUERY)) {

            // Set the product_name parameter in the prepared statement
            preparedStatement.setString(1, productName);

            // Execute the query to find the product_id
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Return the product_id if a match is found
                    return resultSet.getInt("user_id");
                }
            }

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error finding user_id", e);
        }

        // Return null if no match is found
        return null;
    }

    public static boolean updateShoppingCart(String productName, String quantity, String size, String color, String query, String col, String type) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters in the prepared statement
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, Integer.parseInt(quantity));
            preparedStatement.setString(3, size);
            preparedStatement.setString(4, color);

            // Execute the query to find the matching products
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("product_name");
                    Double price = resultSet.getDouble("price");
                    Integer id = resultSet.getInt("product_id");
                    String size1 = resultSet.getString(col);
                    String color1 = resultSet.getString("color_name");

                    // Create a new Item object
                    Item newItem = new Item();
                    newItem.addItem(name, price, id, quantity, size1, color1, type);

                    // Add the new item to the shopping cart
                    ShoppingCartManager.getInstance().getShoppingCart().getItems().add(newItem);

                    // Update the total price in the shopping cart
                    ShoppingCartManager.getInstance().getShoppingCart().setTotalPrice(
                            ShoppingCartManager.getInstance().getShoppingCart().getTotalPrice() + (price * Integer.parseInt(quantity))
                    );
                    return true;
                }
            }

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error finding products in the shopping cart", e);
        }
        return false;
    }

    public static int insertOrder(Integer userId, Double price, String shippingAddress) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters in the prepared statement
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, shippingAddress);
            preparedStatement.setDouble(3, price);

            // Execute the query to insert a new order
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if at least one row is affected
            if (rowsAffected > 0) {
                // Retrieve the generated order_id
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated order ID.");
                    }
                }
            } else {
                throw new SQLException("Failed to insert order.");
            }

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error inserting order", e);
        }
    }


    public static boolean insertProductOrder(Integer productId, Integer orderId, Integer quantity, Integer colorId) {

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRORDER_QUERY)) {

            // Set parameters in the prepared statement
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, colorId);

            // Execute the query to insert a new user
            int rowsAffected = preparedStatement.executeUpdate();

            // If at least one row is affected, the user is registered successfully
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error inserting products-order details", e);
        }
    }

    public static boolean updateQuantity(Integer quantity, Integer productId, Integer sizeId, String query) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters in the prepared statement
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, sizeId);

            // Execute the query to update the user's password
            int rowsAffected = preparedStatement.executeUpdate();

            // If at least one row is affected, the password is updated successfully
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error updating quantity", e);
        }
    }

    public static Integer findColorId(String color) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_COLOR_ID)) {

            // Set parameters in the prepared statement
            preparedStatement.setString(1, color);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Return the product_id if a match is found
                    return resultSet.getInt("color_id");
                }
            }
        } catch (SQLException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException("Error finding color", e);
        }
        return -1;
    }
    public ObservableList<Order> fetchDataFromDatabaseOrders() {
        ObservableList<Order> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS)) {

            // Set the user ID parameter
            preparedStatement.setInt(1, UserManager.getInstance().getCurrentUser().getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int userId = resultSet.getInt("user_id");
                    double price = resultSet.getDouble("price");
                    java.sql.Date date = resultSet.getDate("date");
                    String shippingAddress = resultSet.getString("shippingaddress");

                    Order order = new Order(orderId, userId, price, date, shippingAddress);
                    data.add(order);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
    // Function to configure table columns for orders
    public void configureTableColumnsOrders(TableView<Order> table) {
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderId()).asObject());

        TableColumn<Order, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());

        TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Order, java.sql.Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));

        TableColumn<Order, String> shippingAddressColumn = new TableColumn<>("Shipping Address");
        shippingAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShippingAddress()));

        table.getColumns().addAll(orderIdColumn, userIdColumn, priceColumn, dateColumn, shippingAddressColumn);
    }
    public ObservableList<ProductsOrder> fetchDataFromDatabaseProductsOrder(Integer orderId1) {
        ObservableList<ProductsOrder> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_ORDER)) {

            preparedStatement.setInt(1, orderId1);  // Correct placement

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    int colorId = resultSet.getInt("color_id");

                    ProductsOrder productsOrder = new ProductsOrder(orderId, productId, quantity, colorId);
                    data.add(productsOrder);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // Function to configure table columns for productsorder table
    public void configureTableColumnsProductsOrder(TableView<ProductsOrder> table) {
        TableColumn<ProductsOrder, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderId()).asObject());

        TableColumn<ProductsOrder, Integer> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        TableColumn<ProductsOrder, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        TableColumn<ProductsOrder, Integer> colorIdColumn = new TableColumn<>("Color ID");
        colorIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getColorId()).asObject());

        table.getColumns().addAll(orderIdColumn, productIdColumn, quantityColumn, colorIdColumn);
    }
}




