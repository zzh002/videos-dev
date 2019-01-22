
<!DOCTYPE html>
<html>

	<!-- BEGIN HEAD -->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>短视频后台管理系统</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        
        <!-- 公用头部JS start -->
		<#include "common/commonHeaderCSS.ftl"/>
        <!-- 公用头部JS end -->
        
		<style>
			/* 设置jqgrid列中文字内容垂直居中 jqgrid 单元格自动换行 */
			.ui-jqgrid tr.jqgrow td {
				vertical-align:middle;
			 	white-space: normal !important; 
			 	height: auto; 
			 	word-break: break-all; 
			}
		</style>
		
    </head>
    <!-- END HEAD -->

<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
        <div class="page-wrapper">
            
            <!-- 引入header页面 start -->
           	<#include "common/header.ftl">
           	<!-- 引入header页面  end -->
            
            <!-- BEGIN HEADER & CONTENT DIVIDER -->
            <div class="clearfix"> </div>
            <!-- END HEADER & CONTENT DIVIDER -->
            	
            	<div class="page-container">
            	
            	<!-- 菜单 start -->
            	<#include "common/menu.ftl">
            	<!-- 菜单 end -->
            	
                <!-- BEGIN CONTENT -->
				<div class="page-content-wrapper">
				    <!-- BEGIN CONTENT BODY -->    
				    <div class="page-content">
				    
				    	<div class="page-content-body">
				    	
					        <#include "imooc.ftl">
					        
				        </div>
				        
				    </div>
				    <!-- END CONTENT BODY -->
				</div>
				<!-- END CONTENT -->
                
            </div>
            <!-- END CONTAINER -->
        </div>
            
            <!-- 引入footer页面 start -->
           	<#include "common/footer.ftl">
           	<!-- 引入footer页面  end -->
            
        
        <!-- 公用尾部JS start -->
        <#include "common/commonFooterJS.ftl">
        <!-- 公用尾部JS end -->
	
</body>

</html>
