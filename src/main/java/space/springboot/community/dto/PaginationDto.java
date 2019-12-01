package space.springboot.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto<T> {
    private List<T> pageList;
    private boolean showPre;
    private boolean showFirst;
    private boolean showNext;
    private boolean showLast;
    private Integer currentPage;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        currentPage = page;
        pages.add(page);
        /**
         * 页码显示逻辑
         * 显示前后3页
         * 若当前页码 - i > 0，则把page - i倒插入pages
         * 若当前页码 + i <= 总页数，则把page + i插入pages
         */
        for (int i = 1 ; i <= 3; i++){
            if(page - i > 0){
                pages.add(0,page - i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }

//        展示上一页按钮
        showPre = page != 1;
//        展示下一页按钮
        showNext = page != totalPage;
//        展示第一页页码
        showFirst = !pages.contains(1);
//        展示最后一页
        showLast = !pages.contains(totalPage);


    }
}
