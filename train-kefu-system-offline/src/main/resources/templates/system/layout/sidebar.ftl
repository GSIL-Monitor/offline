<!-- sidebar menu -->
<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
    <div class="menu_section">
        <ul class="nav side-menu">
            <@menuTag method="menus">
                <#if menus?? && menus?size gt 0>
                    <#list menus as item>
                        <#if item.nodes?? && item.nodes?size gt 0>
                            <li>
                                <a><i class="${item.icon?if_exists}"></i> ${item.menuName?if_exists}<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <#list item.nodes as node>
                                        <li><a href="${node.url?if_exists}"><i class="${node.icon?if_exists}"></i>${node.menuName?if_exists}</a></li>
                                    </#list>
                                </ul>
                            </li>
                        <#else>
                            <li><a href="${item.url?if_exists}" ><i class="${item.icon?if_exists}"></i>${item.menuName?if_exists}</a></li>
                        </#if>
                    </#list>
                </#if>
            </@menuTag>
        </ul>
    </div>
</div>
<div class="sidebar-footer hidden-small">
    <a >&nbsp;</a>
    <a >&nbsp;</a>
    <a >&nbsp;</a>
    <a href="/passport/logout" data-toggle="tooltip" data-placement="top" title="" data-original-title="退出系统">
        <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
    </a>
</div>