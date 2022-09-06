package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategory category;

    @Column(nullable = false)
    private String mainTitle;
    private String subTitle;
    private String introduction;
    @Column(nullable = false)
    private boolean open;
    @Column(nullable = false)
    private boolean representativeMenu;
    @Column(nullable = false)
    private boolean bestMenu;
    @Column(nullable = false)
    private boolean alcohol;
    private UploadFile uploadFile;

    public Menu(MenuCategory category, String mainTitle, String subTitle, String introduction, boolean open, boolean representativeMenu, boolean bestMenu, boolean alcohol, UploadFile uploadFile) {
        this.category = category;
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.introduction = introduction;
        this.open = open;
        this.representativeMenu = representativeMenu;
        this.bestMenu = bestMenu;
        this.alcohol = alcohol;
        this.uploadFile = uploadFile;
        category.addMenu(this);
    }
}
