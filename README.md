# quartjob
定时任务配置平台（quart job）

主要功能介绍
========
*通过接口添加定时任务，该定时任务需要配置的具体信息有：* **cron定时时间表达式、http请求链接、http请求方式以及对应的参数** 具体如下：
`/**
     * GET or POST
     */
    private String httpMethod;
    /**
     * http请求链接
     */
    private String url;
    /**
     * 时间设置，参考quartz说明文档
     */
    private String time;
    /**
     * post请求时为json格式
     */
    private String params;`
 
 主要API：
 =======
 + 查询所有配置 
 `quartzManager/findJobs`
 + 重新加载所有定时任务，并执行 
 ``
 + 修改一个任务的触发时间 
 ``
 + 停止执行指定的定时任务 
 ``
 + 开始执行指定的定时任务 
 ``
