// todo 通过动态生成JS来解决 不用每一次都手动在这里改，在QConfig里控制版本保证浏览器更新缓存

/**
 * @return {string}
 */
function ConvertProductLine(code) {
    switch(code){
        case '1':
            return '火车票（老版）';
        case '3':
            return '欧铁';
        case '10':
            return '国内机票（老版）';
        case '31':
            return '国际机票';
        case '32':
            return '去哪儿（老版）';
        case '134':
            return '火车票';
        case '135':
            return '国内机票';
        case '136':
            return '去哪儿';
        case '137':
            return '国内专车（火车）';
        case '138':
            return '国内专车（飞机）';
        default:
            return "未配置";
    }
}

/**
 * @return {string}
 */
function ConvertNoticeState(state) {
    switch(state){
        case 80:
            return '待处理';
        case 81:
            return '处理中';
        case 82:
            return '暂缓';
        case 83:
            return '已解决';
        case 94:
            return '已转投诉';
        case 100:
            return '已转领班';
        case 102:
            return '已交班';
        default:
            return "未配置";
    }
}

/**
 * @return {string}
 */
function ConvertEventType(eventType) {
    switch(eventType){
        case 0:
            return "无";
        case 1:
            return '通知';
        case 2:
            return '投诉';
        case 3:
            return '航班停运';
        case 4:
            return '领班';
        case 5:
            return '119通知';
    }
}
/**
 * @return {string}
 */
function StaffGroupType(eventType) {
    switch(eventType){
        case "0":
            return "无";
        case "1":
            return '通知';
        case "2":
            return '投诉';
        case "4":
            return '领班';
        case "5":
            return '在线';
        case "6":
            return '电话';
    }
}

/**
 * 行程
 * @param state
 * @returns {string}
 * @constructor
 */
function TripTypeEmum(state) {
    switch(state){
        case 1:
            return "单程";
        case 2:
            return "往返";
        case 4:
            return "多程";
        default:
            return "未配置";
    }
}

/**
 * 仓位
 * @param state
 * @returns {string}
 * @constructor
 */
function ClassGradeEmum(state) {
    switch(state){
        case 0:
            return "经济舱";
        case 1:
            return "超级经济舱";
        case 2:
            return "公务舱";
        case 3:
            return "头等舱";
        case 9:
            return "公务舱+头等舱";
        default:
            return "未配置";
    }
}/**
 乘客类型
 0=Unknow=未知
 1=Adult=成人
 2=Child=儿童
 3=Baby=婴儿
 4=Old=老人
 * 仓位
 * @param state
 * @returns {string}
 * @constructor
 */
function PassengerTypeEmum(state) {
    switch(state){
        case 0:
            return "未知";
        case 1:
            return "成人";
        case 2:
            return "儿童";
        case 3:
            return "婴儿";
        case 4:
            return "老人";
        default:
            return "未配置";
    }
}

function PassportTypeEmum(passportType){
        var passport = "";
        switch (passportType)
        {
            case "01":
                passport = "身份证";
                break;
            case "02":
                passport = "护照";
                break;
            case "03":
                passport = "学生证";
                break;
            case "04":
                passport = "军人证";
                break;
            case "06":
                passport = "驾驶证";
                break;
            case "07":
                passport = "回乡证";
                break;
            case "08":
                passport = "台胞证";
                break;
            case "10":
                passport = "港澳通行证";
                break;
            case "11":
                passport = "国际海员证";
                break;
            case "20":
                passport = "外国人永久居留证";
                break;
            case "21":
                passport = "旅行证";
                break;
            case "22":
                passport = "台湾通行证";
                break;
            case "23":
                passport = "士兵证";
                break;
            case "24":
                passport = "临时身份证";
                break;
            case "25":
                passport = "户口簿";
                break;
            case "26":
                passport = "警官证";
                break;
            case "99":
                passport = "其它";
                break;
            default:
                passport = "其它";
                break;
        }
        return passport;
    }













