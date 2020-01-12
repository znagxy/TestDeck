# TestDeck
Task1

首先想好每张牌都是一个object类，定义Card类包含点数跟花色
一副牌Deck类必定有一个包含54张牌的cards的集合变量。首先想到的是用array
定义Deck的构造函数，默认包含大小王 54张，如果不包含大小王就是52张。
实例化Deck的时候，对集合cards初始化。

打牌之前必定会有洗牌功能，所以定义了shuffle方法来实现集合的随机排序
发牌功能，一般都是按顺序发牌，每发一张牌，集合就要移除一个元素。考虑到集合remove的时候用linklist效率比较高，比较方便。所以把cards数据类型改成linklist
考虑到task2是一个算牌大小游戏，重写toString 方法，提供一些当然这副牌的牌库的信息。


Task2

用task的Deck类，实现1个发牌员，3个players的算牌游戏。
典型的1个生产者3个消费者模型。

V1， 初始版本
多线程实现生产消费者模型，所以选择4个线程 sender线程只负责发牌，player1，player2，player3只负责拿牌。
我选择阻塞队列+ 自旋来实现此功能。

因为是按照顺序的拿牌，所以定义volatile status来控制当前线程能不能成功从队列里拿到牌
定义了4个方法，让4个线程各自调用自己的方法。当点数超过50的时候，改变volatile boolean flag类型的变量，通知所有线程，游戏结束。


V2，优化
实现了player人数不固定，可以自定义玩家人数。


