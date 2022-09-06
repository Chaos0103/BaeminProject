package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    public MenuCategory(Store store, int priority, String name, String content) {
        this.store = store;
        this.priority = priority;
        this.name = name;
        this.content = content;
    }

    //== 연관관계 메서드==//
    public void addMenu(Menu menu) {
        this.menus.add(menu);
    }
}
