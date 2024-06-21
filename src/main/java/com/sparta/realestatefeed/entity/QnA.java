package com.sparta.realestatefeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "QNA")
@NoArgsConstructor
public class QnA extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qndId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "apart_id")
    private Apart apart;

    public QnA(String content, User user, Apart apart) {
        this.content = content;
        this.user = user;
        this.apart = apart;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
