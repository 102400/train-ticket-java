# 通用
- /api
- request:
```json
{
  "clientType": "android | ios | web | ...",
  "phone": "noToken时传空",
  "token": "noToken时传空",
  "data": {
    "...": "..."
  }
}
```
- response:
```json
{
  "code": "",
  "message": "",
  "data": {
    "...": "..."
  }
}
```

# 账户

## 注册
- POST /account/register
- request:
```json
{
  "phone": "手机号",
  "password": "密码"
}
```
- response:
```json
""
```

## 登陆
- POST /account/login
- request:
```json
{
  "phone": "手机号",
  "password": "密码"
}
```
- response:
```json
{
  "token": ""
}
```

## 关于
- POST /account/about
- request:
```json
""
```
- response:
```json
{
  "id": "",
  "phone": ""
}
```