<#--通用分页组件，自身通过用户传入的URL，并在后面拼接pageIndex=1&pageSize=20来调用ajax方法获得数据
仅支持get方法，需要ajax支持
返回数据应当继承com.ctrip.train.offline.common.interfaces.Pagination以防止出错
Pagination数据结构中的 pageIndex;pageSize;totalRecord;会被记录list则作为返回数据
最后调用用户传入的回调方法来完成页面显示工作。
引用在需要放置分页组件的地方即可
-->

<#--Created By ying_zhou-->
<div style="width: 100%;padding: 2px;text-align: left" class="form-inline">
    <a href="javascript:void(0);"  id="upPage" onclick="return loadData('up');">上页</a>
    （<span id="pageNumber">1</span>/<span id="pageTotal">1</span>页｜共 <span id="pageCount">0</span> 条）
    <a href="javascript:void(0);" id="nextPage"onclick="return loadData('down');">下页</a>
    &nbsp;&nbsp;跳转至第<input type="text" id="inputPaginationIndex" style="width: 30px; height: 20px; font-size: 10px;"
                           onkeyup="if (!(/^\d+$/).test(this.value)) this.value = this.value.replace(/[^0-9\-\.]/g, '');">页
    <a href="javascript:void(0)" onclick="return loadData('go');" >确定 </a>
</div>
<script type="text/javascript">
    var pagigation_data_acccess_url_producer=null;//用户传入的生成URL的方法，最后组件自己在后面添加pageIndex和pageSize
    var pagigation_on_start=null;//翻页时加载等待图片等操作的回调
    var pagigation_on_success=null;//获得数据后的回调方法
    var pagigation_on_error=null;//失败时的回调方法
    var pagigation_pageSize=20;//用户传入的pageSize,默认为20
    /**
     * 初始化
     * @param urlProducer 用户传入的生成URL的方法,不能携带参数
     * @param onStart 翻页时加载等待图片等操作的回调，不返回参数
     * @param onSuccess 成功时的回调，组件会传入 返回的数据（包含整个jsonresult结构）
     * @param onError 失败时的会回调，组件会传入 返回的数据（包含整个jsonresult结构）,可为空
     * @param pageSize 页面大小，默认20，可不传
     */
    function commonPaginationInitial(urlProducer,onStart,onSuccess,onError,pageSize) {
        pagigation_data_acccess_url_producer=urlProducer;
        pagigation_on_start=onStart;
        pagigation_on_success=onSuccess;
        pagigation_on_error=onError;
        if(pageSize){
            pagigation_pageSize=pageSize;
        }
    }
    var pageIndex=1;
    var pageTotal=1;
    var totalCount=0;
    function loadData(action) {
        switch (action){
            case "start"://上一页
                getData(1);
                break;
            case "up"://上一页
                if (pageIndex == 1) {
                    getData(1);
                }
                else {
                    getData(pageIndex - 1);
                }
                break;
            case "down"://下一页
                if (pageIndex == pageTotal) {
                    getData(pageTotal);
                }
                else {
                    getData(pageIndex + 1);
                }
                break;
            case "go"://指定页
                var pageNumber=$("#inputPaginationIndex").val();
                if(pageNumber){
                    getData(pageNumber);
                }
                break;
        }
    }
    function getData(page_index) {
        if(pagigation_on_start){
            pagigation_on_start();
        }
        var url=pagigation_data_acccess_url_producer();
        if(url.indexOf("?")>=0){
            url+="&pageIndex="+page_index+"&pageSize="+pagigation_pageSize;
        }
        else{
            url+="?pageIndex="+page_index+"&pageSize="+pagigation_pageSize;
        }
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.status==0){
                    pageIndex=data.data.response.pageIndex;
                    totalCount=data.data.response.totalRecord;
                    pageTotal=Math.ceil(totalCount.toFixed()/pagigation_pageSize);
                    if(!pageTotal)
                        pageTotal=1;
                    refreshHint(pageIndex,totalCount,pageTotal);
                    pagigation_on_success(data);
                }
                else{
                    if(pagigation_on_error){
                        pagigation_on_error(data);
                    }
                }
            },
            error:function () {
                if(pagigation_on_error){
                    pagigation_on_error();
                }
            }
        });
    }
    function commonPaginationStartSearch() {
        loadData('start');
    }
    function refreshHint(page_index,total_count,page_total) {
        $("#pageNumber").html(page_index);
        $("#pageTotal").html(page_total);
        $("#pageCount").html(total_count);
    }
</script>
