	    	<ul class="nav">
              <li <#if !_currTopNav??>class="active"</#if>>
                <a href="${wwwroot}">首页</a>
              </li>
              <li <#if _currTopNav??&_currTopNav='item'>class="active"</#if>>
                <a href="${wwwroot}/item">易物</a>
              </li>
              <li <#if _currTopNav??&_currTopNav='about'>class="active"</#if>>
                <a href="${wwwroot}/about">关于</a>
              </li>
            </ul>