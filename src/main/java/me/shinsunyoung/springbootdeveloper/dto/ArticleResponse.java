package me.shinsunyoung.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.Article;


// @NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 생성
// @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
// @RequiredArgsConstructor : final 이나 @NonNull 으로 선언된 필드만을 파라미터로 받는 생성자를 생성

@Getter
public class ArticleResponse {
    private long id;
    private final String title;
    private final String content;

    // Article 엔터티를 인수로 받는 생성자를 추가
    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
