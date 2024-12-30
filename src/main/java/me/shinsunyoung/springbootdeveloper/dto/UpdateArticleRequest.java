package me.shinsunyoung.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


// @RequiredArgsConstructor : final 이나 @NonNull 으로 선언된 필드만을 파라미터로 받는 생성자를 생성

@NoArgsConstructor  // 파라미터가 없는 디폴트 생성자를 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
}
