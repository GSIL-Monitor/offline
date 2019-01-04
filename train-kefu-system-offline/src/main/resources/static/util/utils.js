var utils = function() {
    // post数据
    var  _post = function (url, data, callback) {
        axios.post(url, data)
            .then(function(response) {
                callback(response.data)
            })
            .catch(function (error) {
                console.log(error);
            })
    };

    // get数据
    var _get = function (url, data, callback) {
        axios.get(url, {
            params: data
        })
            .then(function (response) {
                callback(response.data);
            })
            .catch(function (error) {
                console.log(error);
            })
    };
    var _request = function () {
        //js获得Url数值
        var url = location.search; //获取url中"?"符后的字串
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
            }
        }
        return theRequest;
    };



    return {
        post : _post,
        get : _get,
        getReq: _request
    }
}();