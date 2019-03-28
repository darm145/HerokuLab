package edu.eci.persistences;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import edu.eci.models.Car;

import edu.eci.persistences.repositories.ICarRepository;

@Component
@Qualifier("CarPostgresRepository")
public class CarPostgresRepository implements ICarRepository{
	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUsername;
	@Value("${spring.datasource.password}")
	private String dbPassword;

    @Autowired
    private DataSource CardataSource;

    

    @Override
    public List<Car> findAll() {
        String query = "SELECT * FROM cars";
        List<Car> cars=new ArrayList<>();

        try(Connection connection = CardataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Car car = new Car();
                car.setBrand((rs.getString("brand")));
                car.setId(UUID.fromString(rs.getString("id")));
                car.setLicencePlate(rs.getString("licencePlate"));
                cars.add(car);
            }
            return cars;
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car find(UUID id) {
    	String query="SELECT * FROM cars WHERE id='"+id.toString()+"');";
        try(Connection connection = CardataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            Car car = new Car();
            car.setBrand((rs.getString("brand")));
            car.setId(UUID.fromString(rs.getString("id")));
            car.setLicencePlate(rs.getString("licencePlate"));
            
             return car;
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID save(Car entity) {
    	String query="INSERT INTO  Cars (id,licenceplate,brand) VALUES ('"+entity.getId().toString()+"','"+entity.getLicencePlate()+"','"+entity.getBrand()+"');";
        try(Connection connection = CardataSource.getConnection()){
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
             return entity.getId();
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Car entity) {
    	String query="UPDATE Cars SET brand='"+entity.getBrand()+"',licencePlate='"+entity.getLicencePlate()+"' WHERE id='"+entity.getId().toString()+"';";
    	System.out.println(query);
        try(Connection connection = CardataSource.getConnection()){
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Car o) {
    	String query="DELETE FROM cars WHERE id='"+o.getId().toString()+"';";
        try(Connection connection = CardataSource.getConnection()){
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(UUID id) {
    	String query="DELETE FROM cars WHERE id='"+id.toString()+"';";
        try(Connection connection = CardataSource.getConnection()){
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Bean
    public DataSource CardataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setUsername(dbUsername);
            config.setPassword(dbPassword);
            config.setMaximumPoolSize(2);
            return new HikariDataSource(config);
        }
    }

	


}