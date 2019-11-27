package space.springboot.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationDto<T> {
    private List<T> pageList;
    private boolean showPre;
    private boolean showFirst;
    private boolean showNext;
    private boolean showLast;
    private Integer currentPage;
    private List<Integer> pages;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        Integer totalPage = totalCount % 10 == 0?totalCount / 10:(totalCount / 10) + 1;

//        展示上一页按钮
        showPre = page == 1?false:true;
//        展示下一页按钮
        showNext = page == totalPage?false:true;


    }
}
