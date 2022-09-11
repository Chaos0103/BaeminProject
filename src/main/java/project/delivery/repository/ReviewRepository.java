package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.member.Review;
import project.delivery.repository.custom.ReviewRepositoryCustom;


public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

//    @Query("select avg(r.rating) from Review r where r.store.id = :storeId")
//    Float ratingAvgByStoreId(@Param("storeId") Long storeId);

}
