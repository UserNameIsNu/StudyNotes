# 安装

## 1.

把压缩包解压到想要的地方。

如压缩包叫 `mysql-8.0.41-winx64.zip`，解压到 `C:\App\MySQL`。

那么根目录就是 `C:\App\MySQL\mysql-8.0.41-winx64`。

## 2.

在根目录内创建 `my.ini` 文件。

写入：

```powershell
[mysqld]
# 设置3306端口
port=3306
# 设置mysql的安装目录
basedir=C:\App\MySQL\mysql-8.0.41-winx64
# 设置mysql数据库的数据的存放目录
datadir=C:\App\MySQL\mysql-8.0.41-winx64\data
# 允许最大连接数
max_connections=200
# 允许连接失败的次数。这是为了防止有人从该主机试图攻击数据库系统
max_connect_errors=10
# 服务端使用的字符集默认为UTF8
character-set-server=utf8
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
# 默认使用“mysql_native_password”插件认证
default_authentication_plugin=mysql_native_password

[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8

[client]
# 设置mysql客户端连接服务端时默认使用的端口
port=3306
default-character-set=utf8
```

有需要可以修改相关配置。

## 3.

管理员身份打开控制台。

执行 `cd C:\App\MySQL\mysql-8.0.41-winx64\bin`，进入到 `bin` 目录。

执行 `./mysqld --initialize --user=root --console` 创建一个 `root` 用户。

然后理应会炸出一大段东西。

找到 `root@localhost: d+!zW&yH+6,f`，后面的乱码一样的就是临时密码（要暂时记住！）。

## 4.

执行 `./mysqld --install MySQL8.0.41` 安装 MySQL 服务。

`MySQL8.0.41` 可以修改，就是注册的服务名。

## 5.

执行 `net start MySQL8.0.41` 启动服务。

## 6.

执行 `mysql -uroot -P protNumber -p` 登录。

理应出现 `Enter password:`，输入刚才的临时密码。

## 7.

执行 `ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '新密码';` 修改密码。

## 8.

执行 `exit` 退出登录。

然后重新登录，新密码应该是能用的。

## PS:

在任务管理器的服务中可以搜到注册的服务。

如果同时安装多个 MySQL，那么在使用其中某个时，要把其它所有 MySQL服务全部关掉。

# 删除

## 1.

执行 `net stop MySQL8.0.41` 停止服务。

## 2.

控制面板删除 `mysql` 开头的文件。

## 3.

删除安装目录。

## 4.

执行 `./mysqld -remove MySQL8.0.41` 删除服务

## 5.

win + R 输入 `regedit` 打开注册表。

删除 `HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\Services\Eventlog\Application\MySQL`。

删除 `HKEY_LOCAL_MACHINE\SYSTEM\ControlSet002\Services\Eventlog\Application\MySQL`。

删除 `HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\Eventlog\Application\MySQL`。