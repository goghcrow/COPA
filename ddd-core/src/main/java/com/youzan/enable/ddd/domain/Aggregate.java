package com.youzan.enable.ddd.domain;

/**
 * 聚合
 *
 * <h2>What is an aggregate?</h2>
 * A larger unit of encapsulation than just a class.
 * Every transaction is scoped to a single aggregate.
 * The lifetimes of the components of an aggregate are bounded by the lifetime of the entire aggregate.
 *
 * Concretely, an aggregate will handle commands, apply events, and have a state model encapsulated within
 * it that allows it to implement the required command validation, thus upholding the invariants (business rules) of the aggregate.
 *
 * <h2>What is the difference between an aggregate and an aggregate root?</h2>
 * The aggregate forms a tree or graph of object relations.
 * The aggregate root is the "top" one, which speaks for the whole and may delegates down to the rest.
 * It is important because it is the one that the rest of the world communicates with.
 *
 *
 *
 * 聚合，它通过定义对象之间清晰的所属关系和边界来实现领域模型的内聚，并避免了错综复杂的难以维护的对象关系网的形成。
 * 聚合定义了一组具有内聚关系的相关对象的集合，我们把聚合看作是一个修改数据的最小原子单元。
 * 聚合根，每个聚合都有一个根对象，根对象管理聚合内的其他子对象（实体、值对象）；
 * 聚合之间的交互都是通过聚合根来交互，不能绕过聚合根去直接和聚合下的子实体进行交互。
 *
 *
 * 聚合根 实体 值对象 关系
 * 1. 实体有 id，有生命周期，有状态（值对象描述），通过 id 判等
 * 2. 聚合根是实体，id 全局唯一
 * 3. 值对象核心思想是值，无生命周期，与是否复杂类型无关，通过值判等
 *
 *
 * 聚合设计原则
 * 1. 聚合是用来封装真正的不变性，而不是简单的将对象组合在一起
 * 2. 聚合应尽量设计的小
 * 3. 聚合之间的关联通过id，而不是对象引用
 * 4. 聚合内强一致性，聚合之间最终一致性
 *
 *
 * 聚合设计原则
 * 1. 聚合作为一种边界，主要用于维护业务完整性，此时应遵循业务规则中定义的不变量（Invariant）
 * 2. 作为聚合边界内的非聚合根实体对象，若可能被别的调用者单独调用，则应该作为单独的聚合分离出来
 * 3. 在聚合边界内的非聚合根对象，与聚合根之间应该存在直接或间接的引用关系，且可以通过对象的引用方式；若必须采用Id来引用，则说明被引用的对象不属于该聚合
 * 4. 若一个对象缺少另一个对象作为其主对象就不可能存在，则该对象一定属于该主对象的聚合边界内
 * 5. 若一个实体对象，可能被多个聚合引用，则该实体对象应首先考虑作为单独的聚合
 *
 * @author chuxiaofeng
 */
/* private */ interface Aggregate {
}
