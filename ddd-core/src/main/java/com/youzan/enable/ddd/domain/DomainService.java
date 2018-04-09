package com.youzan.enable.ddd.domain;

/**
 * 领域服务 (需要多个聚合参与)
 *
 * If, to accomplish a use case, you need, at domain level,
 * coordinate 2 or more aggregates you put the coordination logic in domain services calling aggregate methods.
 * If just one aggregate is needed then no domain service is involved. Just call aggregate method from app service.
 *
 * If a method logically belongs to an entity, put it there.
 * If there isn't any entity where the method would make sense, put it in a (stateless!) domain service.
 *
 *  https://stackoverflow.com/questions/32898158/domain-services-vs-entity-methods-in-domain-model
 *
 *  和领域对象不同，领域服务是以动词开头来命名的，比如资金转帐服务可以命名为MoneyTransferService。
 *  领域服务是无状态的，它存在的意义就是协调领域对象共完成某个操作，所有的状态还是都保存在相应的领域对象中。
 *  领域服务还有一个很重要的功能就是可以避免领域逻辑泄露到应用层。
 *
 * @author chuxiaofeng
 */
public interface DomainService {

}
