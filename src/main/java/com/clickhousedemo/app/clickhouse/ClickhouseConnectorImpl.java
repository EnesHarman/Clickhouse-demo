package com.clickhousedemo.app.clickhouse;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.clickhouse.jdbc.ClickHouseResultSet;
import com.clickhousedemo.app.model.Message;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class ClickhouseConnectorImpl {
    static String url = "jdbc:ch://localhost:8123/events_test";

    public static List<Message> getData(List<Message> messages) throws SQLException {
        Properties properties = new Properties();
        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from strings");
            while (rs.next()) {
                messages.add(Message.builder()
                        .id(rs.getInt(1))
                        .message(rs.getString(2))
                        .build());
            }
            return messages;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void writeData(int id, String message) throws SQLException {
        Properties properties = new Properties();
        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("insert into strings values(?, ?)");
            ps.setInt(1, id);
            ps.setString(2, message);
            ps.addBatch();
            ps.executeBatch();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) throws SQLException {
        writeData(105,"C");
        List<Message> messages = new ArrayList<>();
        getData(messages);
        messages.sort(Comparator.comparing(Message::getId)); //Data comes in random order.
        System.out.println(messages); // [Message(id=102, message=Java), Message(id=103, message=Python), Message(id=104, message=C#)]
    }

}
