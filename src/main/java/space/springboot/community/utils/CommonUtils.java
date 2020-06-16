package space.springboot.community.utils;

public class CommonUtils {

    /**
     * @desc 计算分页偏移量
     * @param totalPage
     * @param page
     * @param size
     * @return
     */
    public static Integer calculatePageOffset(Integer totalPage,Integer page,Integer size){
        if (page < 1) {
            page = 1;
        } else if (page > totalPage) {
            page = totalPage;
        }
//        偏移量
        Integer offset = size * (page - 1);
        return offset;
    }

    /**
     * @desc 计算分页总页数
     * @param totalCount
     * @return
     */
    public static Integer calculateTotalPage(Integer totalCount){
        Integer totalPage ;
        if (totalCount != 0){
            totalPage = totalCount % 10 == 0 ? totalCount / 10 : (totalCount / 10) + 1;
        }else {
            totalPage = 1;
        }
        return totalPage;
    }
}
