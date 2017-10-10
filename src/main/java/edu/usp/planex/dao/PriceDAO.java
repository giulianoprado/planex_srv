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
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceDAO {

    @Value("${planex.datasource.url}")
    private String dbUrl;

    @Value("${planex.dataDAO.lastTradeListLimit}")
    private int lastTradeListLimit = 10;

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

    public void addPrice (double value, String provider, String date) {
        try (Connection connection = getConnection()) {
            List<Provider> providerList = new ArrayList<Provider>();
            Statement stmt = connection.createStatement();
            if (date.equals("today")) {
                stmt.executeUpdate("INSERT INTO Price (provider, value, date) VALUES ((SELECT id FROM Provider WHERE id='" + provider + "'), '" + value + "', NOW()'')");
            }
            else {
                stmt.executeUpdate("INSERT INTO Price (provider, value, date) VALUES ((SELECT id FROM Provider WHERE id='" + provider + "'), '" + value + "', '" + date + "')");

            }
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
            ResultSet rs = stmt.executeQuery("SELECT price.id priceid, provider.id providerid, provider, value, date, shortname, fullname, type FROM Price JOIN Provider ON Price.provider=Provider.id WHERE Provider.id = '" + provider + "' ORDER BY date DESC");
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
                price.setCount(1);
                price.setPriceDistance(1);
                priceList.add(price);
            }
            return priceList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Price> getAggregatedPricesByProviderAndDate(String provider, String date) {
        try (Connection connection = getConnection()) {
            List<Price> priceList = new ArrayList<Price>();
            Statement stmt = connection.createStatement();
            String sql = "SELECT abs(DATE_PART('day', date - timestamp '" + date + "')) date_diff, avg(value) avgprice, count(value) cntprice, date, provider.id providerid, shortname, fullname, type ";
            sql += "FROM Price JOIN Provider ON Price.provider=Provider.id ";
            sql += "WHERE Provider.id = '" + provider + "' ";
            sql += "GROUP BY date, Provider.id ";
            sql += "ORDER BY date_diff ASC ";
            ResultSet rs = stmt.executeQuery(sql);
            int remainingElements = lastTradeListLimit;
            while(rs.next() && remainingElements-->0){
                Price price = new Price();
                price.setProvider(new Provider());
                price.getProvider().setId(rs.getInt("providerid"));
                price.getProvider().setFullName(rs.getString("fullname"));
                price.getProvider().setShortName(rs.getString("shortname"));
                price.getProvider().setTypeId(rs.getInt("type"));
                price.setDate(rs.getDate("date"));
                price.setValue(rs.getDouble("avgprice"));
                price.setCount(rs.getInt("cntprice"));
                price.setPriceDistance(rs.getInt("date_diff"));
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

}
