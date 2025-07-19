#!/bin/bash

# 等待MySQL数据库启动
echo "等待MySQL数据库启动..."
while ! nc -z $DB_HOST $DB_PORT; do
  sleep 0.1
done
echo "MySQL数据库已启动"

# 运行数据库迁移
echo "运行数据库迁移..."
python manage.py migrate --noinput

# 创建超级用户（如果不存在）
echo "创建超级用户..."
python manage.py shell << EOF
from django.contrib.auth import get_user_model
User = get_user_model()
if not User.objects.filter(username='admin').exists():
    User.objects.create_superuser('admin', 'admin@example.com', 'admin123456')
    print('超级用户已创建')
else:
    print('超级用户已存在')
EOF

# 收集静态文件
echo "收集静态文件..."
python manage.py collectstatic --noinput

# 启动Gunicorn服务器
echo "启动应用服务器..."
exec gunicorn core.wsgi:application \
    --bind 0.0.0.0:8000 \
    --workers 3 \
    --worker-class gevent \
    --worker-connections 1000 \
    --max-requests 1000 \
    --max-requests-jitter 100 \
    --timeout 30 \
    --keep-alive 2 \
    --log-level info \
    --access-logfile - \
    --error-logfile -