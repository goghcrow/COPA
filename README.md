# 开发规范(草稿)


## 分层约定

api/app/domain/infrastructure 只是逻辑上的名称，对应到具体项目可结合脚手架项目自定义

1. api : 接口层, 对应脚手架 的 api 包dubbo 服务接口
2. app : 应用层, 对应 脚手架项目的 biz/api-impl 包
3. domain: 领域层, 对应脚手架项目的 domain 包
4. infrastructure: 基础设施层, 对应脚手架的 dal/其他各种数据源/nsq/引擎/配置等的通用设施


> App层主要负责获取输入，组装context，做输入校验，发送消息给领域层做业务处理，监听确认消息，如果需要的话使用MQ进行消息通知；
> Domain层主要是通过领域服务（Domain Service），领域对象（Domain Object）的交互，对上层提供业务逻辑的处理，然后调用下层Repository做持久化处理；
> Infrastructure层主要包含Repository，Config和Common，Repository负责数据的CRUD操作，隔离数据源；Config负责应用的配置；Common是一写工具类；负责message通信的也应该放在这一层。

![](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/d388900e59922bbf175387498f017bc0.png)

## package约定

每个 pkg 职责统一

1. 校验器统一放在相应 module 的 extension 包
2. 拦截器统一放在相应 module 的 interceptor 包
3. 校验器统一放在相应 module 的 validator 包



```

应用层
---------------------------------------------
    ├--- assembler : 参数/对象组装器
    ├--- command :
    │   ├--- extension : 扩展点实现
    │   ├--- extensionpoint : 扩展点声明
    │   ├--- query : 查询命令实现, 这里并没有完全遵循 cqrs
    │   └--- ... 具体命令执行器实现
    ├-- event :
    │   ├--- handler : 具体事件对应的处理器
    │   └--- ... 具体事件 采用过去式方式命名  e.g. CustomerCreated
    ├-- service : dubbo 接口实现
    ├-- interceptor : 拦截器
    └-- validator : 校验器扩展点
        ├--- extension : 扩展点实现
        ├--- extensionpoint : 扩展点声明
        └--- ...


领域层
---------------------------------------------
 customer : domain 层具体 module
    ├--- service : 领域服务 , 需要实现 DomainService 接口
    ├--- entity : 领域实体 ,  需要实现 Entity 接口
    ├--- valueobject : 值对象, 需要继承 ValueObject
    ├--- repository : 仓库 , 需要实现 repository 接口
    ├--- factory :  领域实体工厂
    ├--- rule : 领域规则扩展点
    │   ├--- extension : 扩展点实现
    │   └--- extensionpoint : 扩展点声明
    │   ...
    ├--- converor : 对象转换
    └--- ...
```

## 命名约定

1. 扩展点接口名称必须以 ExtPt 结尾
2. 扩展点实现类必须以 Ext 结尾
3. 校验器实现必须以 Validator 结尾


## 异常约定

1. 校验器产生的参数异常统一抛出 ParamException 及其派生类
2. 业务异常统一抛出 BizException 及其派生类
3. 基础设施(数据库，缓存，消息等)统一抛出 InfraException 及其派生类
4. 其他 Crm 业务相关的异常统一抛出 CrmException 及其派生类

- 参数错误：1000 - 1999
- 业务错误：2000 - 2999
- 基础错误：3000 - 3999
- 系统错误：4000 - 4999



## 注解

1. 每个 Command 类型必须对应一个 CommandExecutor 实现
2. EventHandler 处理的事件类型由 execute 方法的参数决定, 必须为 Event 的派生类
3. 必须用 @Command 注解 用来标记 CommandExecutor 实现
4. 必须用 @Event 注解标记 EventHandler 实现



## 扩展点

扩展点（ExtensionPoint）必须通过接口声明，扩展实现（Extension）通过 Annotation 方式标注，
Extension 通过 @Extension(order = ${num}) 声明的匹配顺序进行**唯一**性匹配，匹配规则则 @Extension
标注的 ExtensionPoint 实现类重新 match 方法；

一个从精确到模糊的匹配实例：

场景：
假设一个业务身份需要 两个属性区分，{bizCode, TenantId}，每个业务下又区分不同的租户 id，需要对用户信息修改的检查规则做一个扩展点；


```java
// 框架内容扩展点接口
public interface ExtensionPoint {
    default boolean match() { return false; }
}

// 添加一个 客户修改规则的扩展点
public interface CustomerUpdateRuleExtPt extends Rule, ExtensionPoint {
    boolean updateCustomerCheck(CustomerEntity customerEntity);
}
```


方案：
1. 请求前置拦截器设置上下文信息

```java
Context.set("bizCode", "BIZ_A");
Context.set("tenantId", 1);
```

2. 一个简单的实例， 假设只有两种业务方，BIZ_A 与 BIZ_B

只有 业务 BIZ_A 会进行检查；

```java

// 扩展点1
// A 业务的 修改客户信息规则
@Extension
public class CustomerUpdateBizARuleExt implements CustomerUpdateRuleExtPt {
    @Override
    public void updateCustomerCheck(CustomerEntity customerEntity) {
        // A 业务针对来源做检查
        if(SourceType.AD == customerEntity.getSourceType()){
            throw new BizException("Sorry, Customer from advertisement can not be updated");
        }
    }

    // 匹配规则
    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return "BIZ_A".equals(bizCode);
    }
}

// 扩展点2
// 其他 业务的 修改客户信息规则
@Extension(order = 10)
public class CustomerUpdateBizARuleExt implements CustomerUpdateRuleExtPt {
    @Override
    public void updateCustomerCheck(CustomerEntity customerEntity) {}

    // 匹配规则
    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return "BIZ_B".equals(bizCode);
    }
}

```

3. 一个优先级的匹配实例

通过调整 extension 的 order，

当前用户身份为 {bizCode = "BIZ_B", tenantId = 1} 会匹配到扩展点3，
当前用户身份为 {bizCode = "BIZ_B", tenantId = 2} 会匹配到扩展点2，
当前用户身份为 {bizCode = "BIZ_A", tenantId = 1} 会匹配到扩展点1，
当前用户身份为 {bizCode = "BIZ_A", tenantId = 2} 会匹配到扩展点1，
当前用户身份为 {bizCode = "BIZ_C", tenantId = 1} 会匹配到扩展点4，
当前用户身份为 {bizCode = "BIZ_C", tenantId = 2} 会匹配到扩展点4



```java
// 扩展点1
// A 业务的 修改客户信息规则
@Extension(order = 10)
public class CustomerUpdateBizARuleExt implements CustomerUpdateRuleExtPt {
    @Override
    public void updateCustomerCheck(CustomerEntity customerEntity) {
        // A 业务针对来源做检查
        if(SourceType.AD == customerEntity.getSourceType()){
            throw new BizException("Sorry, Customer from advertisement can not be updated");
        }
    }

    // 匹配规则
    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return "BIZ_A".equals(bizCode);
    }
}

// 扩展点2
// B 业务的 修改客户信息规则
@Extension(order = 10)
public class CustomerUpdateBizARuleExt implements CustomerUpdateRuleExtPt {
    @Override
    public void updateCustomerCheck(CustomerEntity customerEntity) {
        // B 业务不允许修改客户信息
        throw new BizException("Sorry, Customer can not be updated");
    }

    // 匹配规则
    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return "BIZ_B".equals(bizCode);
    }
}

// 扩展点3
// 但是针对 B 业务的特定租户(id=1)允许修改客户信息
@Extension(order = 20)
public class CustomerUpdateBizARuleExt implements CustomerUpdateRuleExtPt {
    @Override
    public void updateCustomerCheck(CustomerEntity customerEntity) {}

    // 匹配规则
    @Override
    public boolean match() {
        // ignored null check
        String bizCode = Context.get("bizCode");
        int tenantId = Context.get("tenantId");
        return "BIZ_B".equals(bizCode) && tenantId == 1;
    }
}

// 扩展点4
// 通用的修改客户信息规则
@Extension(order = 0)
public class CustomerUpdateBizARuleExt implements CustomerUpdateRuleExtPt {
    @Override
    public void updateCustomerCheck(CustomerEntity customerEntity) {
        // 其他业务的所有组合网站来源客户允许修改信息
        if(SourceType.WB == customerEntity.getSourceType()){
            throw new BizException("Sorry, Customer from web site can not be updated");
        }
    }

    @Override
    public boolean match() {
        return true;
    }
}

```


### 图片示例

![](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/8b7d2e54d3f243d3efefd3e798d4123a.png)


4. 使用方式

```java

// 注入扩展点执行器
@Resource
private ExtensionExecutor extensionExecutor;


// 在业务逻辑 内部 执行扩展点, 会根据上下文信息自动匹配执行
extensionExecutor.execute(CustomerUpdateBizARuleExt.class, extension -> extension.updateCustomerCheck(this));
```


5. 其他

1. 可以将扩展点内部匹配规则结合规则引擎使用，在业务流程的需要根据身份或者其他上下文信息扩展的地方埋点，动态修改业务行为；
2. 内置了两类扩展点: 校验 & 规则


两类扩展点：
关于 @Extension(order=1) order 的作用
一个 扩展点执行时 只会选取一个扩展点实现，具体选择逻辑！！！


## 拦截器

1. 前置拦截器注解 @PreInterceptor，可以指定拦截器具体的 Command 类型，否则对所有 Command 起作用

```java
public @interface PreInterceptor {
    Class<? extends Command>[] commands() default {};
}

```
2. 后置拦截器 @PostInterceptor，规则同前置

3. 示例：对所有 要执行的 Command 的字段进行校验

```java

@Data
public class AddCustomerCmd extends Command {
    @NotNull
    @Valid
    private CustomerCO customerCO;
}



@PreInterceptor
public class ValidationInterceptor implements CommandInterceptor {
    private ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
            .messageInterpolator(new MessageInterpolator()).buildValidatorFactory();

    @Override
    public void preIntercept(Command command) {
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Command>> constraintViolations = validator.validate(command);

        constraintViolations.forEach(violation -> {
            throw new ParamException(violation.getPropertyPath() + " " + violation.getMessage());
        });
    }
}
```




## 修改说明


最终方案采用了这是第二个修改版本；

### 第一个版本

把 Command 的执行替换成 责任链模式（借鉴 activiti）， Command 定义为

```java
interface Command<T> {
    T execute(CommandContext ctx);
}
```

所有的服务实现类都包含一个 CommandExecutor属性，每个服务方法只是简单的把命令需要的参数通过构造器注入到 cmd 中，
将 cmd 丢到执行求执行，需要自行转换 cmd 的返回类型 T 到 dubbo-api 约定的 BaseResult 类型；

<br>

优点：

1. 将整个 Command 隐藏起来，外部无感知
2. Command Pattern 与 common-model 无耦合
3. 责任链模式使拦截器非常容易订制
4. Command Context 的加入提供更好的灵活性
5. Thread<Stack<CommandContext>>> 结构允许 命令嵌套执行

缺点：

1. 如果不通过 aspectj 而只是通过command 的中拦截器无法统一处理异常，因为命令返回结果不统一
2. 对原有结构入侵太大，测试成本比较高
3. 具体使用场景
    1. Cmd 涉及到 Bean  组装，需要借助 spring 容器, 且需要 都定义成 prototype，使用者容易迷惑
    2. Cmd 创建代码比较繁琐 AddCustomerCmd cmd = (AddCustomerCmd) ApplicationContextHelper.getBean(AddCustomerCmd.class, customerCO);

```java
@Data
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AddCustomerCmd implements Command<Response> {

    @NotNull
    @Valid
    @Setter
    private CustomerDTO customerCO;

    @Resource
    private ValidatorExecutor validatorExecutor;

    @Resource
    private ExtensionExecutor extensionExecutor;


    public AddCustomerCmd(CustomerDTO customerCO) {
        this.customerCO = customerCO;
    }

    @Override
    public Response execute(CommandContext commandContext) {

        validatorExecutor.validate(AddCustomerValidatorExtPt.class, this);

        //Convert CO to Entity
        CustomerEntity customerEntity = extensionExecutor.execute(CustomerConvertorExtPt.class, extension -> extension.clientToEntity(customerCO));

        //Call Domain Entity for business logic processing
        log.info("Call Domain Entity for business logic processing..."+customerEntity);
        customerEntity.addNewCustomer();

        return Response.buildSuccess();
    }
}



@Service
public class CustomerServiceImpl extends ServiceImpl implements CustomerServiceI {

    @Override
    public Response addCustomer(CustomerDTO customerCO) {
        AddCustomerCmd cmd = (AddCustomerCmd) ApplicationContextHelper.getBean(AddCustomerCmd.class, customerCO);
        return commandExecutor.execute(cmd);
    }

    @Override
    public SingleResponse<CustomerDTO> getCustomer(GetOneCustomerQry getOneCustomerQry) {
        return null;
    }
}
```






### 第二个版本的

优点或者说问题：

1. 把 dubbo-api 需要依赖的 common-model 内的数据结构 与 命令执行与事件触发耦合起来
    1. 不需要做返回值转换
    2. 可以不需要每个业务方定制 AspectJ 版本 的 dubbo 服务异常转换，因为同一了 BaseResult 返回值，命令执行器内部处理了
2. 把 Command 做成 一个 DTO，一个只有数据没有行为的对象，放置于 api 层，Command 的创建非常容易，而命令执行器可以直接通过容器管理，生命周期简单

```java
@Data
public class AddCustomerCmd extends Command {
    @NotNull
    @Valid
    private CustomerCO customerCO;
}

```

与此配合，每个 Command 需要提供一个 CommandExecutor，置于 App 层

```java
public interface CommandExecutor<R extends BaseResult, C extends Command> {
    R execute(C cmd);
}

@Command
@Slf4j
public class AddCustomerCmdExe implements CommandExecutor<BaseResult, AddCustomerCmd> {

    @Resource
    private ValidatorExecutor validatorExecutor;

    @Resource
    private ExtensionExecutor extensionExecutor;


    @Override
    public BaseResult execute(AddCustomerCmd cmd) {
        log.info("Start processing command:" + cmd);
        validatorExecutor.validate(AddCustomerValidatorExtPt.class, cmd);

        //Convert CO to Entity
        CustomerEntity customerEntity = extensionExecutor.execute(CustomerConvertorExtPt.class,
                extension -> extension.clientToEntity(cmd.getCustomerCO()));

        //Call Domain Entity for business logic processing
        log.info("Call Domain Entity for business logic processing..."+customerEntity);
        customerEntity.addNewCustomer();

        log.info("End processing command:" + cmd);
        return new BaseResult();
    }
}

```

### 问题：

1. 修改过程遇到的最大的问题是，整个设计的与 阿里b2bcrm 的身份识别体系耦合，如何泛化扩展点的设计；





## 参考

https://blog.csdn.net/significantfrank/article/details/79286947