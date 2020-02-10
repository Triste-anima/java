## 简易国际象棋

##### 特殊规则(实现难点)

>1.吃过路兵：如果对方的兵第一次行棋且直进两格，刚好形成本方有兵与其横向紧贴并列，则本方的兵可以立即斜进，把对方的兵吃掉，并视为一步棋。这个动作必须立刻进行，缓着后无效。

>2.兵升变：本方任何一个兵直进达到对方底线时，即可升变为除“王”和“兵”以外的任何一种棋子，可升变为“后”、“车”、“马”、“象”，不能不变(默认变为王后)。这被视为一步棋。升变后按新棋子的规则走棋。

>3.王车易位：每局棋中，双方各有一次机会，让王朝车的方向移动两格，然后车越过王，放在与王紧邻的一格上，作为王执行的一步棋。王车易位需满足6个条件，在这里实现了其中3个：

>>①王与用来易位的车均从未被移动过（已实现）

>>②王与用来易位的车之间没有其他棋子阻隔（已实现）

>>③王不能正被对方“将军”

>>④王所经过的格子不能在对方棋子的攻击范围之内

>>⑤王所到达的格子不能被对方“将军”（即王不可以送吃）

>>⑥王和对应的车必须处在同一横行（即通过兵的升变得到的车不能用来进行“王车易位”）（已实现，通过设置兵升变成的车的属性为：已移动过）

##### 胜利判断：王被对方吃掉（没有实现将军的判定）