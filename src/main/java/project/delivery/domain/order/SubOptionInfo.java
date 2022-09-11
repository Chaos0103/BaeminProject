package project.delivery.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.store.MenuSubOption;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubOptionInfo extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "sub_option_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_order_id")
    private MenuOrder menuOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_sub_option_id")
    private MenuSubOption menuSubOption;

    public SubOptionInfo(MenuOrder menuOrder, MenuSubOption menuSubOption) {
        this.menuOrder = menuOrder;
        this.menuSubOption = menuSubOption;
    }
}
