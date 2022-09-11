package project.delivery.domain.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuSubOption {

    @Id @GeneratedValue
    @Column(name = "menu_sub_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_sub_category_id")
    private MenuSubCategory category;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;

    public MenuSubOption(MenuSubCategory category, String name, int price) {
        this.category = category;
        this.name = name;
        this.price = price;
        category.addMenuSubOption(this);
    }
}
