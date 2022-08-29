package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOption extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "menu_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    private String optionName;
    @Column(nullable = false)
    private int price;

    public MenuOption(Menu menu, String optionName, int price) {
        this.menu = menu;
        this.optionName = optionName;
        this.price = price;
    }
}
