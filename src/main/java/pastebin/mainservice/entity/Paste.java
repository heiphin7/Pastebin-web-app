package pastebin.mainservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "paste")
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Link to content")
    private String linkToContent;

    @Column(name = "Created Date")
    private Date createdDate;

    @Column(name = "Expiration Date")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
