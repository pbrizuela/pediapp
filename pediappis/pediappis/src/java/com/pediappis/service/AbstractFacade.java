
package com.pediappis.service;

import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public String verifica(Object email){
        String x="f"; // bandera
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.select(employee.get("id"));
        criteriaQuery.where(criteriaBuilder.equal(employee.get("email"), email));
        Query query = getEntityManager().createQuery(criteriaQuery);
        List<String> lista =  query.getResultList();
       
        if (0 ==lista.size())return "f";
  
        return String.valueOf(lista.get(0));
    }
     
     public List<T> findAllWhereDatos(int id_usuario) {
        String x="f"; //bandera
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        criteriaQuery.where(criteriaBuilder.equal(employee.get("id_usuario"), id_usuario));
        Query query = getEntityManager().createQuery(criteriaQuery);
        
        List<T> lista =query.getResultList();
        HashSet<T> hashSet = new HashSet<T>(lista);
        lista.clear();
        lista.addAll(hashSet);

        for(T list : lista){
            System.out.println(list);
        }
        return lista;
    }
     
    public List<T> findAllwhere(Integer id_hijo) {
             
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(employee.get("vacunasPK").get("idHijo"), id_hijo));
        Query query = getEntityManager().createQuery(criteriaQuery);
        List<T> lista =query.getResultList();
        HashSet<T> hashSet = new HashSet<T>(lista);
        lista.clear();
        lista.addAll(hashSet);

        for(T list : lista){
            System.out.println(list);
        }
        return lista;
     }
    
     public List<T> findAllwhereaplicada(Integer id_hijo,Integer aplicada) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(employee.get("vacunasPK").get("idHijo"), id_hijo),
                criteriaBuilder.equal(employee.get("aplicada"), aplicada)));
        Query query = getEntityManager().createQuery(criteriaQuery);
        List<T> lista =query.getResultList();
        HashSet<T> hashSet = new HashSet<T>(lista);
        lista.clear();
        lista.addAll(hashSet);

        for(T list : lista){
            System.out.println(list);
        }
        return lista;
     }
      
    public List<T> retornaOrdenAlfa(Integer id_hijo) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(employee.get("vacunasPK").get("idHijo"), id_hijo));
        criteriaQuery.orderBy(criteriaBuilder.desc(employee.get("nombre")));
        Query query = getEntityManager().createQuery(criteriaQuery);
        List<T> lista =query.getResultList();
        HashSet<T> hashSet = new HashSet<T>(lista);
        lista.clear();
        lista.addAll(hashSet);

        for(T list : lista){
            System.out.println(list);
        }
        return lista;
     }
    
    public List<T> findAllwhere1(Integer id_usuario) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(employee.get("vacunasPK").get("idUsuario"), id_usuario));
        Query query = getEntityManager().createQuery(criteriaQuery);
        List<T> lista =query.getResultList();
        HashSet<T> hashSet = new HashSet<T>(lista);
        lista.clear();
        lista.addAll(hashSet);

        for(T list : lista){
            System.out.println(list);
        }
        return lista;
     }
}
