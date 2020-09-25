package life.beyond.community.controller;

import life.beyond.community.cache.TagCache;
import life.beyond.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    PublishService publishService;

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,Model model){

        publishService.getById(id,model);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Long id,
                            HttpServletRequest request,
                            Model model){
        boolean publish = publishService.publish(title, description, tag,id, request, model);
        model.addAttribute("tags", TagCache.get());
        if(publish==false)
            return "publish";
        return "redirect:/";
    }
}
