## Boot实践
开始日期2019年11月10日

添加README

##更新日志
###02/02/22
完成二级评论功能


## 知识点

@Component
类似于SpringMVC中的@Service\
被此注释的类可以使用@Autowire注释来调用，不用new

controller中的参数注释\
@CookieValue
可以直接获取相应name的cookie值

## themeleaf使用

th:href往标签添加超链接地址\
**@{路径(参数 = ${data})}**

onclick中传递参数\
1.**th:onclick = "'javascript:functionName('+${param}+')'"**\
2.**th:onclick = "functionName([[${param}]])"**
## Cookies

不能跨域，有过期时间，路径，域名

## 文档
[bootstrap文档](https://v3.bootcss.com/components/) 

[OAuth文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)

