
new Vue({
    el:"#orderrefundapplybox",
    data:{
        data:""
    },
    created:function(){
        this.$http.get('/order/train/refund/getRefundInfo', {"OrderId":1}).then(function(response){
            // 响应成功回调
            console.log(response);
        }, function(err){
            // 响应错误回调
            console.log(err);
        });
    }
});

