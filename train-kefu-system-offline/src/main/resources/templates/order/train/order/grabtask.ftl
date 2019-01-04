<#if tasks.allAccpetTrainNumber??||tasks.acceptSeat??||tasks.acceptDepartDates??
||tasks.crossStation??||tasks.leakCutOffTime??||tasks.acceptTimeRanges??>
    <#if tasks??&&tasks.ticketType??>
    <div class="Stit">抢票任务<small>当前加速等级：${tasks.speedLevel}</small><i class="searchStation"></i>
        <dl class="GradeList" style="left:135px;top:29px;display: none;">
            <dt>
                <span>等级</span>
                <span>金额</span>
            </dt>
            <#if tasks.levels??&& tasks.levels?size gt 0 >
                <#list tasks.levels as level>
                    <dd>
                        <span>${level.levelName!""}</span>
                        <span>${level.amount!""}</span>
                    </dd>
                </#list>
            </#if>
        </dl>
    </div>
        <div class="TabBox">
            <table>
                <tr>
                    <th width="66px">原票</th>
                    <th width="118px">备选车次</th>
                    <th width="128px">备选坐席</th>
                    <th width="98px">备选日期</th>
                    <th width="96px">接受临近</th>
                    <th width="105px">跨站</th>
                    <th width="112px">提交时间</th>
                    <th width="112px">捡漏截止时间</th>
                    <th width="117px">备注</th>
                </tr>
                <tr>
                    <td><span style="display:block;">${tasks.ticketType!""}</span></td>
                    <td><span style="display:block;">${tasks.allAccpetTrainNumber!""}</span></td>
                    <td><span style="display:block;">${tasks.acceptSeat!""}</span></td>
                    <td><span style="display:block;">${tasks.acceptDepartDates!""}</span></td>
                    <td><span style="display:block;">${tasks.acceptTimeRanges!""}</span></td>
                    <td><span style="display:block;">${tasks.crossStation!""}</span></td>
                    <td><span style="display:block;">${tasks.createTime!""}</span></td>
                    <td><span style="display:block;">${tasks.leakCutOffTime!""}</span></td>
                    <td><span style="display:block;">-</span></td>
                </tr>
            </table>
        </div>
    </#if>
</#if>