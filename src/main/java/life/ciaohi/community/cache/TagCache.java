package life.ciaohi.community.cache;

import life.ciaohi.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program=new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript","php","css","html","html5","java","node.js","python","c++","c","golang","objective-c","typescript","shell","c#","swift","sass","bash","ruby","less","asp.net","lua","scala","coffeescript","actionscript","rust","erlang"));
        tagDTOS.add(program);


        TagDTO framework=new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel","spring","express","django","flask","yii","ruby-on-rails","tornado","koa","struts"));
        tagDTOS.add(framework);


        TagDTO server=new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","缓存","tomcat","负载均衡","unix","hadoop","windows-server"));
        tagDTOS.add(server);

        TagDTO db=new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","mongodb","sql", "nosql","memcached","sqlserve","postgresql","sqlite"));
        tagDTOS.add(db);

        TagDTO tool=new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList(" git","github"," visual-studio-code","vim", "intellij-idea","eclips","ide","svn","visual-studio","atom","emacs","textmate","hg"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String filterInValid(String tags){
        String []split=StringUtils.split(tags,",");
        List<TagDTO> tagDTOS=get();
        List<String> tagList=tagDTOS.stream().flatMap(tag-> tag.getTags().stream()).collect(Collectors.toList());
        String invalid=Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
