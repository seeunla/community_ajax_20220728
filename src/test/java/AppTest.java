import com.fasterxml.jackson.core.JsonProcessingException;
import com.ll.exam.article.dto.ArticleDto;
import com.ll.exam.util.Ut;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void 실험_assertThat() {
        int rs = 10+20;
        assertThat(rs).isEqualTo(30);
    }
    @Test
    void ObjectMapper__ObjToJsonStr() throws JsonProcessingException {
        ArticleDto articleDto = new ArticleDto(1, "제목", "내용");

        String jsonStr = Ut.json.toJonStr(articleDto, "");
        assertThat(jsonStr).isNotBlank();
        assertThat(jsonStr).isEqualTo("""
                {"id":1,"title":"제목","body":"내용"}
                """.trim());
    }

    @Test
    void ObjectMapper__articleDtoListToJsonStr() throws JsonProcessingException {
       List<ArticleDto> articleDtos = new ArrayList<>();
        articleDtos.add(new ArticleDto(1, "제목", "내용"));
        articleDtos.add(new ArticleDto(2, "제목2", "내용2"));

        String jsonStr = Ut.json.toJonStr(articleDtos, "");
        assertThat(jsonStr).isEqualTo("""
                [{"id":1,"title":"제목","body":"내용"},{"id":2,"title":"제목2","body":"내용2"}]
                """.trim());
    }

    @Test
    void ObjectMapper__articleDtoMapToJsonStr() {
        HashMap<String, ArticleDto> articleDtoMap = new HashMap<>();
        articleDtoMap.put("최신",new ArticleDto(1, "제목", "내용"));
        articleDtoMap.put("가장오래된", new ArticleDto(2, "제목2", "내용2"));

        String jsonStr = Ut.json.toJonStr(articleDtoMap, "");
        assertThat(jsonStr).isEqualTo("""
                {"가장오래된":{"id":2,"title":"제목2","body":"내용2"},"최신":{"id":1,"title":"제목","body":"내용"}}
                """.trim());
    }
    @Test
    void ObjectMapper__jsonStrToObj() {
        ArticleDto articleDtoOrigin = new ArticleDto(1, "제목", "내용");
        String jsonStr = Ut.json.toJonStr(articleDtoOrigin, "");

        ArticleDto articleDtoFromJson = Ut.json.toObj(jsonStr, ArticleDto.class, null);

        assertThat(articleDtoOrigin).isEqualTo(articleDtoFromJson);
    }
}
