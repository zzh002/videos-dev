

<script src="../static/pages/js/reportList.js?v=1.1" type="text/javascript"></script>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>首页</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>举报信息</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>举报列表</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
        
    <!-- 列表 jqgrid start -->                
	<div class="row">

        <!-- 搜索内容 -->
        <div class="col-md-12">
            <br/>
            <form id="searchUsersReportsForm" class="form-inline" method="post" role="form">
                <div class="form-group">
                    <label class="sr-only" for="id">ID:</label>
                    <input id="id" name="id" type="text" class="form-control" placeholder="ID" />
                </div>
                <div class="form-group">
                    <select  class="form-control" id="title" name="title">
                        <option value="">---请选择举报类型---</option>
                        <option value="色情低俗">色情低俗</option>
                        <option value="政治敏感">政治敏感</option>
                        <option value="涉嫌诈骗">涉嫌诈骗</option>
                        <option value="辱骂谩骂">辱骂谩骂</option>
                        <option value="广告垃圾">广告垃圾</option>
                        <option value="诱导分享">诱导分享</option>
                        <option value="引人不适">引人不适</option>
                        <option value="过于暴力">过于暴力</option>
                        <option value="违法违纪">违法违纪</option>
                        <option value="其它原因">其它原因</option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="sr-only" for="dealUsername">被举报人:</label>
                    <input id="dealUsername" name="dealUsername" type="text" class="form-control" placeholder="被举报人" />
                </div>
                <div class="form-group">
                    <label class="sr-only" for="dealVideoId">被举报视频ID:</label>
                    <input id="dealVideoId" name="dealVideoId" type="text" class="form-control" placeholder="被举报视频ID" />
                </div>
                <div class="form-group">
                    <select  class="form-control" id="status" name="status">
                        <option value="">---请选择视频状态---</option>
                        <option value="1">正常</option>
                        <option value="2">禁播</option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="sr-only" for="submitUsername">提交用户:</label>
                    <input id="submitUsername" name="submitUsername" type="text" class="form-control" placeholder="提交用户" />
                </div>
                <button id="searchUsersReportsButton" class="btn yellow-casablanca" type="button">搜    索</button>
            </form>
        </div>

    	<div class="col-md-12">
			<br/>
			
			<div class="usersReportsList_wrapper">  
			    <table id="usersReportsList"></table>  
			    <div id="usersReportsListPager"></div>  
			</div>  
			
		</div>
	</div>
	<!-- 列表 jqgrid end -->
	
