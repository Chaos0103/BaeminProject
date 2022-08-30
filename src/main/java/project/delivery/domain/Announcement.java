package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement {

    @Lob
    @Column(nullable = false)
    private String content;
    @Column(name = "announcement_created_date")
    private LocalDateTime createdDate;

    public Announcement(String content) {
        this.content = content;
        this.createdDate = LocalDateTime.now();
    }
}
