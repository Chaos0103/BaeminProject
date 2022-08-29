package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuSubOptionCategory {

    @Id @GeneratedValue
    @Column(name = "menu_sub_option_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private int priority;
    @Column(nullable = false)
    private String categoryName;

    public MenuSubOptionCategory(Menu menu, int priority, String categoryName) {
        this.menu = menu;
        this.priority = priority;
        this.categoryName = categoryName;
    }
}
