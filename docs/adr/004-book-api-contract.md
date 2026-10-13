
## 1. 背景
实现book管理时会数据冲突、参数不符等异常场景的决策

## 2. 选择和决策

### 2.1 详情嵌套 author，列表只带 authorId
(1) Book类中嵌套包含Author类，可根据列表查询或详情查询决定author属性是否查询，跟随Book返回数据；
(2) Book类中将Author信息平铺，SQL中join查询后直接设值

### 2.2 category 必填
category必填，只能返回一类书籍列表；
category非必填，则允许一次返回全部书籍结果

### 2.3 重复书的判定规则（书名 + author_id，返回 409 · 2002，并加 UNIQUE）
（1）只用书名作唯一标识；
（2）用书名+作者名作为组合标识


### 2.4 请求体里的作者是嵌套的 AuthorRequest
(1) BookRequest嵌套包含AuthorRequest
(2) BookRequest不嵌套AuthorRequest，将Author信息平铺列开


## 3. 决策

### 3.1 详情嵌套 author，列表只带 authorId
选择（1），方便管理，数据结构清晰

### 3.2 category 必填
选择Category必填


### 3.3 重复书的判定规则（书名 + author_id，返回 409 · 2002，并加 UNIQUE）
选择书名 + author_id名作为组合的唯一标识


### 3.4 请求体里的作者是嵌套的 AuthorRequest
选择BookRequest嵌套包含AuthorRequest

## 4. 理由
### 4.1 详情嵌套 author，列表只带 authorId
书籍详情页需要展示作者信息，一次join查出来可以减少一次查询请求；

### 4.2 category 必填
如果允许不带category参数，实现分页之前会出现返回全部结果的情况，到时候再加必填约束会具有破坏性
先设置category为必填，未来再放宽，老客户端就不会受影响

### 4.3 重复书的判定规则（书名 + author_id，返回 409 · 2002，并加 UNIQUE）
考虑实际场景下，有可能会出现不同作者、书名相同的情况，应该兼容此类数据；另外唯一性校验不应该只停留在service层，还应该在DB层加UNIQUE约束

### 4.4 请求体里的作者是嵌套的 AuthorRequest
选择嵌套方案，方便管理，数据结构清晰

# 5 代价与后果
## 5.1 
book列表不带作者名，会导致显示作者需要再请求一次；并且将来按国籍汇总书籍，需要新的接口

## 5.2 
category必填则暂时无法一次查询所有书籍列表

## 5.3
同名同作者、不同出版社的书籍只能存储一条书籍记录

## 5.4
改表后需要重新建表

## 5.5
嵌套在book里的author，生日不会覆盖DB表，以author表中存的生日值为准

