## 更新

### 1.引入Scope和ScopeManager

OpenTracing0.30.x 的最大特点是引入了Scope和ScopeManager概念。在多线程的场景下，ScopeManager管理着每个线程的Scope，一个Scope可以理解为一个容器，存储着当前活动着的Span。OpenTracing中实现类是ThreadLocalScopeManager，SofaTracer之前版本的实现是SofaTracerThreadLocalTraceContext，更新后，我们直接使用了ThreadLocalScopeManager来对SofaTracer中的Span进行管理。

### 2. ignoreActiveSpan

在Span调用start()方法开启Span时，如果没有为Span指定关系，默认情况下，会将当前线程中Active Span设置为父Span，可以将ignoreActiveSpan设置为True进行关闭。

### 3. try-with-resources

由于Scope默认实现了Closeable接口，可以很方便的使用try-with-resources方式来进行管理，在语句结束后，会自动执行Scope关闭操作，从而恢复上下文。

### 4. Binary

OpenTracing0.30.x对透传格式进行了进一步优化，提供了Binary接口，我们在基于BinaryFormater基础上做了修改，和API保持一致。

### 5. AbstractTracer

AbstractTracer中包含了clientSend，clientReceive，serverReceive，serverSend，它们分别会创建并且在当前线程中激活Span，有了Scope之后，四个方法只负责创建Span的任务，并不涉及Span的管理工作。这样就需要在实际应用中，结合try-with-resources以及Scope来进行操作


### 6. 异步 Callable,Runnable

待完成

### 7. 插件

插件根据埋点分类，可分为三类，基于代理，基于组件自身提供的hook，基于拦截器或者过滤器，还在修改中