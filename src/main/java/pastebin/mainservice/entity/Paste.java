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

    @Column(name = "title")
    private String title;

    @Lob // for CLOB (Character large Object)
    @Column(name = "Link to content")
    private String Content;

    @Column(name = "Created Date")
    private Date createdDate;

    @Column(name = "Expiration Date")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
