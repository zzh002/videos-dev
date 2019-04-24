

<script src="../static/pages/js/bgmList.js?v=1.0.0.2"
	type="text/javascript"></script>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>首页</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>bgm管理</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>背景音乐列表展示</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
                        
	<div class="row">

        <!-- 搜索内容 -->
        <div class="col-md-12">
            <br/>
            <form id="searchBgmListForm" class="form-inline" method="post" role="form">
                <div class="form-group">
                    <label class="sr-only" for="id">ID:</label>
                    <input id="id" name="id" type="text" class="form-control" placeholder="ID" />
                </div>
                <div class="form-group">
                    <label class="sr-only" for="author">作者:</label>
                    <input id="author" name="author" type="text" class="form-control" placeholder="作者" />
                </div>
                <div class="form-group">
                    <label class="sr-only" for="name">歌曲名称:</label>
                    <input id="name" name="name" type="text" class="form-control" placeholder="歌曲名称" />
                </div>
                <button id="searchBgmListButton" class="btn yellow-casablanca" type="button">搜    索</button>
            </form>
        </div>

    	<div class="col-md-12">
            <br/>
			<div class="bgmList_wrapper">
                <table id="bgmList"></table> 
    			<div id="bgmListPager"></div>
             </div>
             
		</div>
	</div>
