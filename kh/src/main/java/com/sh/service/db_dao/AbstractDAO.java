package com.sh.service.db_dao;

import org.hibernate.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Dropwizard's AbstractDAO
 *
 */
public class AbstractDAO<E> {

    private final SessionFactory sessionFactory;
    private final Class<?> entityClass;

    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory    a session provider
     */
    public AbstractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = checkNotNull(sessionFactory);
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<?>) type.getActualTypeArguments()[0];
    }

    /**
     * Returns the current {@link Session}.
     *
     * @return the current session
     */
    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Creates a new {@link Criteria} cosmos for {@code <E>}.
     *
     * @return a new {@link Criteria} cosmos
     * @see Session#createCriteria(Class)
     */
    protected Criteria criteria() {
        return currentSession().createCriteria(entityClass);
    }

    /**
     * Returns a named {@link Query}.
     *
     * @param queryName the name of the cosmos
     * @return the named cosmos
     * @see Session#getNamedQuery(String)
     */
    protected Query namedQuery(String queryName) throws HibernateException {
        return currentSession().getNamedQuery(checkNotNull(queryName));
    }

    /**
     * Returns the entity class managed by this DAO.
     *
     * @return the entity class managed by this DAO
     */
    @SuppressWarnings("unchecked")
    public Class<E> getEntityClass() {
        return (Class<E>) entityClass;
    }

    /**
     * Convenience method to return a single instance that matches the criteria, or null if the
     * criteria returns no results.
     *
     * @param criteria the {@link Criteria} cosmos to run
     * @return the single result or {@code null}
     * @throws HibernateException if there is more than one matching result
     * @see Criteria#uniqueResult()
     */
    @SuppressWarnings("unchecked")
    protected E uniqueResult(Criteria criteria) throws HibernateException {
        return (E) checkNotNull(criteria).uniqueResult();
    }

    /**
     * Convenience method to return a single instance that matches the cosmos, or null if the cosmos
     * returns no results.
     *
     * @param query the cosmos to run
     * @return the single result or {@code null}
     * @throws HibernateException if there is more than one matching result
     * @see Query#uniqueResult()
     */
    @SuppressWarnings("unchecked")
    protected E uniqueResult(Query query) throws HibernateException {
        return (E) checkNotNull(query).uniqueResult();
    }

    /**
     * Get the results of a {@link Criteria} cosmos.
     *
     * @param criteria the {@link Criteria} cosmos to run
     * @return the list of matched cosmos results
     * @see Criteria#list()
     */
    @SuppressWarnings("unchecked")
    protected List<E> list(Criteria criteria) throws HibernateException {
        return checkNotNull(criteria).list();
    }

    /**
     * Get the results of a cosmos.
     *
     * @param query the cosmos to run
     * @return the list of matched cosmos results
     * @see Query#list()
     */
    @SuppressWarnings("unchecked")
    protected List<E> list(Query query) throws HibernateException {
        return checkNotNull(query).list();
    }

    /**
     * Return the persistent instance of {@code <E>} with the given identifier, or {@code null} if
     * there is no such persistent instance. (If the instance, or a proxy for the instance, is
     * already associated with the session, return that instance or proxy.)
     *
     * @param id an identifier
     * @return a persistent instance or {@code null}
     * @throws HibernateException
     * @see Session#get(Class, Serializable)
     */
    @SuppressWarnings("unchecked")
    protected E get(Serializable id) {
        return (E) currentSession().get(entityClass, checkNotNull(id));
    }

    /**
     * Either save or update the given instance, depending upon resolution of the unsaved-value
     * checks (see the manual for discussion of unsaved-value checking).
     * <p/>
     * This operation cascades to associated instances if the association is mapped with
     * <tt>cascade="save-update"</tt>.
     *
     * @param entity a transient or detached instance containing new or updated state
     * @throws HibernateException
     * @see Session#saveOrUpdate(Object)
     */
    protected E persist(E entity) throws HibernateException {
        currentSession().saveOrUpdate(checkNotNull(entity));
        return entity;
    }

    /**
     * Force initialization of a proxy or persistent collection.
     * <p/>
     * Note: This only ensures initialization of a proxy object or collection;
     * it is not guaranteed that the elements INSIDE the collection will be initialized/materialized.
     *
     * @param proxy a persistable object, proxy, persistent collection or {@code null}
     * @throws HibernateException if we can't initialize the proxy at this time, eg. the {@link Session} was closed
     */
    protected <T> T initialize(T proxy) throws HibernateException {
        if (!Hibernate.isInitialized(proxy)) {
            Hibernate.initialize(proxy);
        }
        return proxy;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
