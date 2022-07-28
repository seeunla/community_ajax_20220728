import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ll.exam.article.dto.ArticleDto;
import com.ll.exam.util.Ut;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void 실험_assertThat() {
        int rs = 10+20;
        assertThat(rs).isEqualTo(30);
    }
    @Test
    void ObjectMapper__ObjToJsonStr() {
        ArticleDto articleDto = new ArticleDto(1, "제목", "내용");

        String jsonStr = Ut.json.toJsonStr(articleDto, "");
        assertThat(jsonStr).isNotBlank();
        assertThat(jsonStr).isEqualTo("""
                {"id":1,"title":"제목","body":"내용"}
                """.trim());
    }

    @Test
    void ObjectMapper__articleDtoListToJsonStr() {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDtos.add(new ArticleDto(1, "제목", "내용"));
        articleDtos.add(new ArticleDto(2, "제목2", "내용2"));

        String jsonStr = Ut.json.toJsonStr(articleDtos, "");
        assertThat(jsonStr).isEqualTo("""
                [{"id":1,"title":"제목","body":"내용"},{"id":2,"title":"제목2","body":"내용2"}]
                """.trim());
    }

    @Test
    void ObjectMapper__articleDtoMapToJsonStr() {
        Map<String, ArticleDto> articleDtoMap = new HashMap<>();
        articleDtoMap.put("최신",new ArticleDto(1, "제목", "내용"));
        articleDtoMap.put("가장오래된", new ArticleDto(2, "제목2", "내용2"));

        String jsonStr = Ut.json.toJsonStr(articleDtoMap, "");
        assertThat(jsonStr).isEqualTo("""
                {"가장오래된":{"id":2,"title":"제목2","body":"내용2"},"최신":{"id":1,"title":"제목","body":"내용"}}
                """.trim());
    }
    @Test
    void ObjectMapper__jsonStrToObj() {
        ArticleDto articleDtoOrigin = new ArticleDto(1, "제목", "내용");
        String jsonStr = Ut.json.toJsonStr(articleDtoOrigin, "");

        ArticleDto articleDtoFromJson = Ut.json.toObj(jsonStr, ArticleDto.class, null);

        assertThat(articleDtoOrigin).isEqualTo(articleDtoFromJson);
    }

    @Test
    void ObjectMapper__jsonStrToArticleDtoList() {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDtos.add(new ArticleDto(1, "제목", "내용"));
        articleDtos.add(new ArticleDto(2, "제목2", "내용2"));

        String jsonStr = Ut.json.toJsonStr(articleDtos, "");

        List<ArticleDto> articleDtosFromJson = Ut.json.toObj(jsonStr, new TypeReference<>() {}, null);

        assertThat(articleDtosFromJson).isEqualTo(articleDtos);
    }

    @Test
    //JS객체(복잡) => Map<String, ArticleDto>
    void ObjectMapper__jsonStrToArticleDtoMap() {
        Map<String, ArticleDto> articleDtoMap = new HashMap<>();
        articleDtoMap.put("가장오래된", new ArticleDto(1, "제목1", "내용1"));
        articleDtoMap.put("최신", new ArticleDto(2, "제목2", "내용2"));
        String jsonStr = Ut.json.toJsonStr(articleDtoMap, "");

        Map<String, ArticleDto> articleDtoMapFromJson = Ut.json.toMap(jsonStr, new TypeReference<>() {
        }, null);

        assertThat(articleDtoMapFromJson).isEqualTo(articleDtoMap);
    }
}
