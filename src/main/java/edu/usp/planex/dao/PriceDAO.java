package edu.usp.planex.dao;

/**
 * Created by giulianoprado on 24/07/17.
 */

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.usp.planex.model.Price;
import edu.usp.planex.model.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PriceDAO {

    @Value("${planex.datasource.url}")
    private String dbUrl;

    @Value("${planex.dataDAO.lastTradeListLimit}")
    private int lastTradeListLimit = 10;

    @Autowired
    private DataSource dataSource;

    public List<Provider> getProviderList() {
        try (Connection connection = getConnection()) {
            List<Provider> providerList = new ArrayList<Provider>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Provider");
            while(rs.next()){
                Provider provider = new Provider();
                provider.setId(rs.getInt("id"));
                provider.setFullName(rs.getString("fullname"));
                provider.setShortName(rs.getString("shortname"));
                provider.setTypeId(rs.getInt("type"));
                providerList.add(provider);
            }
            return providerList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addPrice (double value, String provider) {
        try (Connection connection = getConnection()) {
            List<Provider> providerList = new ArrayList<Provider>();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO Price (provider, value, time) VALUES ((SELECT id FROM Provider WHERE shortname='" + provider + "'), '" + value + "', now())");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Price> getPricesByProvider(String provider, String date) {
        try (Connection connection = getConnection()) {
            List<Price> priceList = new ArrayList<Price>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT price.id priceid, provider.id providerid, provider, value, date, shortname, fullname, type FROM Price JOIN Provider ON Price.provider=Provider.id WHERE Date='" + date + "' AND Provider.shortname = '" + provider + "'");
            while(rs.next()){
                Price price = new Price();
                price.setProvider(new Provider());
                price.getProvider().setId(rs.getInt("providerid"));
                price.getProvider().setFullName(rs.getString("fullname"));
                price.getProvider().setShortName(rs.getString("shortname"));
                price.getProvider().setTypeId(rs.getInt("type"));
                price.setDate(rs.getDate("date"));
                price.setValue(rs.getDouble("value"));
                price.setId(rs.getInt("priceid"));
                priceList.add(price);
            }
            return priceList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Price> getLastPricesByProvider(String provider) {
        try (Connection connection = getConnection()) {
            List<Price> priceList = new ArrayList<Price>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT price.id priceid, provider.id providerid, provider, value, date, shortname, fullname, type FROM Price JOIN Provider ON Price.provider=Provider.id WHERE Provider.shortname = '" + provider + "' ORDER BY date DESC");
            int remainingElements = lastTradeListLimit;
            while(rs.next() && remainingElements-->0){
                Price price = new Price();
                price.setProvider(new Provider());
                price.getProvider().setId(rs.getInt("providerid"));
                price.getProvider().setFullName(rs.getString("fullname"));
                price.getProvider().setShortName(rs.getString("shortname"));
                price.getProvider().setTypeId(rs.getInt("type"));
                price.setDate(rs.getDate("date"));
                price.setValue(rs.getDouble("value"));
                price.setId(rs.getInt("priceid"));
                priceList.add(price);
            }
            return priceList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection() throws URISyntaxException, SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        dbUrl="jdbc:postgresql://ec2-54-204-32-145.compute-1.amazonaws.com/d3124slnilcg78?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&user=zrtfjkpdlyemhx&password=b9fccf4b1cf4925cf4c95bf19236535cbf7ee6f62dea983680eab0457c89dcb5";
        return DriverManager.getConnection(dbUrl);
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }

}
