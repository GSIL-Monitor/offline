function diffMilis(date1, date2) {
    var type1 = typeof date1, type2 = typeof date2;
    if (type1 == 'string')
        date1 = stringToTime(date1);
    else if (date1.getTime)
        date1 = date1.getTime();
    if (type2 == 'string')
        date2 = stringToTime(date2);
    else if (date2.getTime)
        date2 = date2.getTime();
    return (date1 - date2) ;//获得毫秒数
}
function stringToTime(string) {
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(parseInt(d[0], 10) || null,
        (parseInt(d[1], 10) || 1) - 1,
        parseInt(d[2], 10) || null,
        parseInt(t[0], 10) || null,
        parseInt(t[1], 10) || null,
        parseInt(t[2], 10) || null)).getTime();
}

function DiffDay (date1, date2) {
    return diffMilis(date1, date2)/ 1000 / 60 / 60 / 24; //把相差的毫秒数转换为天数
}
function DiffMinute (date1, date2) {
    return (diffMilis(date1, date2).toFixed()/ 1000 / 60).toFixed(1) ; //把相差的毫秒数转换为分钟数
}

