package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {
    @Id
    //속성값에 뭐를 따로 지정해주지 않아도 자동적으로 1씩 증감한다.
    //GenerationType.IDENTITY 해당 컬럼만의 독립적인 시퀸스를 생성하여 번호를 증감할 때 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //제목을 200자미만으로 설정한다.
    @Column(length = 200)
    private String subject;

    //글자수를 제한하지 않을 때
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    //question과 answer은 1:N관계를 정의하기 위해 @OneToMany를 선언해준다.
    //mappedBy는 참고 엔티티의 속성명을 의미하므로 question를 참고해야하니 question을 적어준다.
    //cascade는 종속이란 뜻으로 question가 삭제됐을 때 엮여있는 answer들도 같이 삭제가 되게 해준다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
