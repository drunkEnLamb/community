package life.beyond.community.cache;

import life.beyond.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO frontEnd = new TagDTO();
        frontEnd.setCategoryName("前端");
        frontEnd.setTags(Arrays.asList("javascript","前端","vue.js","css","html","html5","node.js","react.js","jquery","css3","es6","typescript","chrome","npm","bootstrap","sass","less","chrome-devtools","angular","firefox","coffeescript","safari","postman","postcss","fiddler","yarn","webkit","firebug","edge"));
        tagDTOS.add(frontEnd);

        TagDTO backEnd = new TagDTO();
        backEnd.setCategoryName("后端");
        backEnd.setTags(Arrays.asList("php","java","node.js","python","c++","golang","c","spring","后端","springboot","django","flask","c#","swoole","ruby","asp.net","ruby-on-rails","scala","rust","lavarel"));
        tagDTOS.add(backEnd);

        TagDTO database = new TagDTO();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("mysql","redis","sql","mongodb","数据库","json","elasticsearch","nosql","memcached","postgresql","sqlite","mariadb"));
        tagDTOS.add(database);

        TagDTO mobile = new TagDTO();
        mobile.setCategoryName("移动端");
        mobile.setTags(Arrays.asList("java","android","ios","objective-c","小程序","react-native","swift","xcode","android-studio","flutter","webapp","kotlin"));
        tagDTOS.add(mobile);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","macos","visual-studio-code","windows","vim","sublime-text","intellij-idea","phpstorm","eclipse","webstorm","编辑器","svn","visual-studio","pycharm","emacs"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
