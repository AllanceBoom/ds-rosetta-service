"""
肝纤维化研究数据管理系统 URL 配置
"""
from django.contrib import admin
from django.urls import path, include
from django.conf import settings
from django.conf.urls.static import static
from drf_spectacular.views import SpectacularAPIView, SpectacularSwaggerView, SpectacularRedocView

urlpatterns = [
    # 管理后台
    path('admin/', admin.site.urls),
    
    # API文档
    path('api/schema/', SpectacularAPIView.as_view(), name='schema'),
    path('api/docs/', SpectacularSwaggerView.as_view(url_name='schema'), name='swagger-ui'),
    path('api/redoc/', SpectacularRedocView.as_view(url_name='schema'), name='redoc'),
    
    # API路由
    path('api/auth/', include('apps.authentication.urls')),
    path('api/experiments/', include('apps.experiments.urls')),
    path('api/samples/', include('apps.samples.urls')),
    path('api/biomarkers/', include('apps.biomarkers.urls')),
    path('api/analysis/', include('apps.analysis.urls')),
    path('api/reports/', include('apps.reports.urls')),
]

# 开发环境下的静态文件和媒体文件服务
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
    urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)

# 自定义管理后台标题
admin.site.site_header = "肝纤维化研究数据管理系统"
admin.site.site_title = "LFRS Admin"
admin.site.index_title = "系统管理"