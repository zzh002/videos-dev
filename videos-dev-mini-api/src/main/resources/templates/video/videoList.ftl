

<script src="../static/pages/js/videoList.js?v=1.1" type="text/javascript"></script>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>首页</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>视频信息</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>视频列表</span>
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
            <form id="searchVideoListForm" class="form-inline" method="post" role="form">
                <div class="form-group">
                    <label class="sr-only" for="id">ID:</label>
                    <input id="id" name="id" type="text" class="form-control" placeholder="ID" />
                </div>
                <div class="form-group">
                    <label class="sr-only" for="userId">用户ID:</label>
                    <input id="userId" name="userId" type="text" class="form-control" placeholder="用户ID" />
                </div>
                <div class="form-group">
                    <label class="sr-only" for="nickname">用户昵称:</label>
                    <input id="nickname" name="nickname" type="text" class="form-control" placeholder="用户昵称" />
                </div>
                <div class="form-group">
                    <select  class="form-control" id="status" name="status">
                        <option value="">---请选择视频状态---</option>
                        <option value="1">正常</option>
                        <option value="2">禁播</option>
                    </select>
                </div>
                <button id="searchVideoListButton" class="btn yellow-casablanca" type="button">搜    索</button>
            </form>
        </div>

    	<div class="col-md-12">
			<br/>
			
			<div class="videoList_wrapper">
			    <table id="videoList"></table>
			    <div id="videoPager"></div>
			</div>  
			
		</div>
	</div>
	<!-- 列表 jqgrid end -->
	
