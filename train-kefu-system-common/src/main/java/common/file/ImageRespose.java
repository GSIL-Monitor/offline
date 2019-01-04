package common.file;


public class ImageRespose {

    /**
     * 是否上传成功
     */
    private  Boolean isSuccess;

    /**
     * 返回信息
     */
    private  String message;

    /**
     * 文件名
     */
    private  String file_name;

    /**
     * 带域名的Url
     */
    private  String url;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
