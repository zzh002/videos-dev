
<!DOCTYPE html >

<!-- BEGIN CONTAINER -->
    <!-- BEGIN SIDEBAR -->
    <div class="page-sidebar-wrapper">
        <!-- BEGIN SIDEBAR -->
        <div class="page-sidebar navbar-collapse collapse">
            <!-- BEGIN SIDEBAR MENU -->
            <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="false" data-slide-speed="200" style="padding-top: 20px">
                <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <li class="sidebar-toggler-wrapper hide">
                    <div class="sidebar-toggler">
                        <span>
                        	
                        </span>
                    </div>
                </li>
                
               	<!-- 控制台  start -->
               	<li class="nav-item">
                	<a href="/videos/admin/menu">
                    	<i class="icon-home"></i>
                        	<span class="title">首页</span>
					</a>
               	</li>
               	
               	<!-- 用户管理 start -->
				<li class="nav-item ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-user"></i>
                        <span class="title">用户信息</span>
						<span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item ">
                            <a href="/videos/admin/showUserList" class="ajaxify nav-link ">
                                <span class="title">用户列表</span>
                            </a>
                        </li>
                    </ul>
               	</li>
               	
               	<!-- 背景音乐 start -->
				<li class="nav-item ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-music-tone"></i>
                        <span class="title">bgm管理</span>
						<span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
						<li class="nav-item ">
                            <a id="bgmListMenu" href="/videos/admin/showBgmList" class="ajaxify nav-link ">
                                <span class="title">bgm列表</span>
                            </a>
                        </li>
                        <li class="nav-item ">
                            <a href="/videos/admin/showAddBgm" class="ajaxify nav-link ">
                                <span class="title">添加bgm</span>
                            </a>
                        </li>
                    </ul>
               	</li>
               	
               	<!-- 举报管理 start -->
				<li class="nav-item ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-info"></i>
                        <span class="title">举报管理</span>
						<span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
						<li class="nav-item ">
                            <a href="/videos/admin/showReportList" class="ajaxify nav-link ">
                                <span class="title">举报列表</span>
                            </a>
                        </li>
                    </ul>
               	</li>

                <!-- 视频管理 start -->
                <li class="nav-item ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-disc"></i>
                        <span class="title">视频管理</span>
                        <span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item ">
                            <a href="/videos/admin/showVideoList" class="ajaxify nav-link ">
                                <span class="title">视频列表</span>
                            </a>
                        </li>
                    </ul>
                </li>
               	
            </ul>
            <!-- END SIDEBAR MENU -->
        </div>
        <!-- END SIDEBAR -->
    </div>
    <!-- END SIDEBAR -->