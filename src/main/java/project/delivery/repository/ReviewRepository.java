package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Review;
import project.delivery.repository.custom.ReviewRepositoryCustom;


public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    @Query("select avg(r.rating) from Review r where r.store.id = :storeId")
    Float ratingAvgByStoreId(@Param("storeId") Long storeId);

}
