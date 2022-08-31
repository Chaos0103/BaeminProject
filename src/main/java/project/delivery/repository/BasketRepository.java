package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.basket.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
