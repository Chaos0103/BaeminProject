package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCategory extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "menu_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private int priority;
    @Column(nullable = false)
    private String name;
    private String content;

    public MenuCategory(Store store, int priority, String name, String content) {
        this.store = store;
        this.priority = priority;
        this.name = name;
        this.content = content;
    }
}
