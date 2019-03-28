package edu.eci.persistences;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.eci.models.User;
import edu.eci.persistences.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Qualifier("UserPostgresRepository")
public class UserPostgresRepository implements IUserRepository {
	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUsername;
	@Value("${spring.datasource.password}")
	private String dbPassword;

    @Autowired
    private DataSource dataSource;

    @Override
    public User getUserByUserName(String userName) {
        String query="SELECT * FROM users WHERE name='"+userName+"';";
        User u=new User();
        try(Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
                
             u.setName(rs.getString("name"));
             u.setId(UUID.fromString(rs.getString("id")));
             return u;
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
            
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        List<User> users=new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                User user = new User();
                user.setName(rs.getString("name"));
                user.setId(UUID.fromString(rs.getString("id")));
                users.add(user);
            }
            return users;
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(UUID id) {
    	String query="SELECT * FROM users WHERE id='"+id.toString()+"');";
        User u=new User();
        try(Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
             u.setName(rs.getString("name"));
             u.setId(UUID.fromString(rs.getString("id")));
             return u;
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID save(User entity) {
    	String query="INSERT INTO users (id,name) VALUES('"+entity.getId()+"','"+entity.getName()+"');" ;
    	System.out.println(query);
        try(Connection connection = dataSource.getConnection()){
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
    public void update(User entity) {
    	String query="UPDATE users SET name='"+entity.getName()+"' WHERE id='"+entity.getId().toString()+"';";
        try(Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User o) {
    	String query="DELETE FROM users WHERE name='"+o.getName()+"' AND id='"+o.getId().toString()+"';";
        try(Connection connection = dataSource.getConnection()){
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
    	String query="DELETE FROM users WHERE id='"+id.toString()+"';";
        try(Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Bean
    public DataSource dataSource() throws SQLException {
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
