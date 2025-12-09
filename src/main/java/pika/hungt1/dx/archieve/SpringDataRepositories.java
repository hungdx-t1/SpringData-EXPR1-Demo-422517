package pika.hungt1.dx.archieve;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pika.hungt1.dx.models.Product;

import java.util.List;
import java.util.UUID;

@NullMarked
@SuppressWarnings("unused")
public interface SpringDataRepositories {

    // Example Class
    @Entity
    @Data
    class User {
        @Id Long id;
    }

    // Spring Data JPA (JPA Repository)
    // dùng cho CSDL quan hệ (MySQL, Postgre, SQLSERVER) - automatic CRUD cùng hibernate
    interface UserRepositoryJPA extends JpaRepository<User, Long> {
        List<User> findByEmail(String email);
    }

    // Spring Data JDBC (JDBC Repository) - bản JPAR thu nhỏ, không có hibernate
    // Không có lazy loading, không relationship phức tạp. Vì thế JDBCR chạy nhanh, dễ debug
    interface UserRepositoryJDBC extends CrudRepository<User, Long> {

    }

    // Spring Data MongoDB (MongoDB Repository) - thường dùng khi làm việc với MongoDB (NoSQL)
    interface UserRepositoryMongo extends MongoRepository<User, String> {

    }

    // Spring Data Redis (Redis ở đây có thể là cache, queue, session)
    @RedisHash("User")
    public class UserRedis {  }

    // Spring Data Elasticsearch
    // Dùng để tìm kiếm dữ liệu với Elasticsearch – mạnh về full-text search.
    interface ProductRepository extends ElasticsearchRepository<Product, String> { }

    // Spring Data Cassandra (cơ sở dữ liệu phân tán Apache Cassandra)
    interface UserRepositoryCas extends CassandraRepository<User, UUID> { }

    // Spring Data Neo4j (CSDL dạng đồ thị GraphDB)
    interface MovieRepository extends Neo4jRepository<Movie, Long> { }

    // Spring Data R2DBC (bản non-blocking của JDBC, Dùng cho hệ thống xử lý tải lớn)
    interface UserRepositoryR2 extends ReactiveCrudRepository<User, Long> { }

    // ngoài ra còn nhiều loại khác như
    // Spring Data LDAP - truy vấn danh mục, thư mục người dùng
    // Spring Data Solr - Dùng để tìm kiếm với Apache Solr , searching server
    // Spring Data for Apache Geode / GemFire - Giống Redis nhưng chuyên nghiệp hơn
}
