package me.shinsunyoung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.Article;
import me.shinsunyoung.springbootdeveloper.dto.AddArticleRequest;
import me.shinsunyoung.springbootdeveloper.dto.ArticleResponse;
import me.shinsunyoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.shinsunyoung.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;

    /*
        ResponseEntity 이란?
        ResponseEntity는 Status Code, Header, Body 등 HTTP Response 전체를 나타냅니다.
        즉, @RestController와 함께 사용되어 완전한 HTTP 응답을 구성할 수 있습니다.

        https://burningfalls.github.io/java/what-is-response-entity/
        https://velog.io/@hyensukim/Spring-ResponseEntity%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-HTTP-Response-%EC%B2%98%EB%A6%AC


        사용 방법
        ResponseEntity<T> responseEntity = new ResponseEntity<>(body, headers, status);

        @GetMapping("/hello")
        ResponseEntity<String> hello() {
            return new ResponseEntity<>("Hello World!", HttpStatus.OK);
        }

        @GetMapping("/customHeader")
        ResponseEntity<String> customHeader() {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "foo");

            return new ResponseEntity<>(
              "Custom header set", headers, HttpStatus.OK);
        }

        * Map 과 MultiValueMap
        https://taehoung0102.tistory.com/182
        https://hyeri0903.tistory.com/269

     */

    ////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article saveArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saveArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        /*
            map(ArticleResponse::new)는 **메서드 레퍼런스(method reference)**를 사용해
            스트림 내 각 Article 객체를 ArticleResponse 객체로 변환하는 부분입니다.

            메서드 레퍼런스란?

            ArticleResponse::new는 article -> new ArticleResponse(article)와 동일한 의미입니다.
            람다(lambda) 표현식 대신 간단하게 표현하기 위해 사용됩니다.
            동작 방식

            blogService.findAll()로부터 반환된 List<Article>를 stream()으로 스트림화합니다.
            .map(ArticleResponse::new)는 각 Article 객체를 ArticleResponse의 생성자에 전달해
            새 ArticleResponse 객체를 만듭니다.
            이 과정을 거치면 최종적으로 List<ArticleResponse> 형태가 됩니다.
            전제 조건

            ArticleResponse에 Article 객체를 파라미터로 받는
            생성자(예: public ArticleResponse(Article article) { ... })가 반드시 존재해야 합니다.
            그렇지 않으면 컴파일 에러가 발생합니다.
            즉, .map(ArticleResponse::new)는 스트림 안에 있는 Article 객체들을 빠르고 간결하게
            ArticleResponse 형태로 변환하기 위한 문법이라고 볼 수 있습니다.

         */
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok().body(articles);
    }


    @GetMapping("/api/articles/{userId}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable("userId") long userId) {
        Article article = blogService.findById(userId);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{userId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("userId") long userId) {
        blogService.delete(userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{userId}")
    public ResponseEntity<Article> updateArticle(@PathVariable("userId") long userId,
                                                 @RequestBody UpdateArticleRequest request) {
        Article article = blogService.update(userId, request);
        return ResponseEntity.ok().body(article);

    }

}
