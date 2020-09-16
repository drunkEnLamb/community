package life.beyond.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        //计算总页数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        this.page = page;

        //显示页码数组
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i >= 1)
                pages.add(0,page - i);
            if (page + i <= totalPage)
                pages.add(page + i);
        }


        //判断前一页按钮是否显示
        if (page == 1)
            showPrevious = false;
        else
            showPrevious = true;
        //判断后一页按钮是否显示
        if (page == totalPage)
            showNext = false;
        else
            showNext = true;

        //判断第一页按钮是否显示
        if (pages.contains(1))
            showFirstPage = false;
        else
            showFirstPage = true;

        //判断尾页按钮是否显示
        if (pages.contains(totalPage))
            showEndPage = false;
        else
            showEndPage = true;
    }
}
