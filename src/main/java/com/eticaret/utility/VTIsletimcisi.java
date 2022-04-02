package com.eticaret.utility;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.Field;
import java.util.List;


public class VTIsletimcisi<T> {

    private Session session;
    private Transaction transaction;

    private void Open(){
        session = HibernateUtility.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }
    private void CloseCommit(){
        transaction.commit();
        session.close();
    }
    private void CloseRollBack(){
        transaction.rollback();
        session.close();
    }

    public void save(T t){
        try{
            Open();
            session.save(t); // Eğer kayıt sırasında sorun olursa rollback yap
            CloseCommit();
        }catch (Exception exception){
            CloseRollBack();
        }
    }
    public void update(T t){
        try{
            Open();
            session.update(t); // Eğer kayıt sırasında sorun olursa rollback yap
            CloseCommit();
        }catch (Exception exception){
            CloseRollBack();
        }
    }
    public void delete(T t){
        try{
            Open();
            session.delete(t); // Eğer kayıt sırasında sorun olursa rollback yap
            CloseCommit();
        }catch (Exception exception){
            CloseRollBack();
        }
    }
    public List<T> findAll(T t){
        Open();
        List<T> result = null;
        Criteria cr = session.createCriteria(t.getClass());
        result = cr.list();
        CloseCommit();
        return result;
    }

    public T findById(long id, T t){
        T result = null;
        Open();
        Criteria cr = session.createCriteria(t.getClass());
        cr.add(Restrictions.eq("id",id));
        if(cr.list().size()>0){
            result = (T)cr.list().get(0);
        }
        return result;
    }

    public List<T> findByColumn(String columnName, String value,boolean isEquals, T t){

        List<T> result = null;
        Open();
        Criteria cr = session.createCriteria(t.getClass());
        if(isEquals)
            cr.add(Restrictions.eq(columnName,value));
        else
           cr.add(Restrictions.ilike(columnName,"%"+value+"%"));
        result = cr.list();
        return result;
    }

    public List<T> findAnyItem(T t){
        List<T> result = null;
        Class cl = t.getClass();

        Field[] fields = cl.getDeclaredFields();
        Open();
        Criteria cr = session.createCriteria(t.getClass());
        try{
            for(int i=0; i<fields.length;i++){
                fields[i].setAccessible(true);
                if(fields[i].getType().equals("long")){
                  if((long)fields[i].get(t) > 0){
                        cr.add(Restrictions.eq(fields[i].getName(),(long)fields[i].get(t)));
                    }
                }else if(fields[i].getType().equals("class java.lang.String")){
                    if(fields[i].get(t) != null)
                        cr.add(Restrictions.ilike(fields[i].getName(), "%"+fields[i].get(t)+"%"));
                }
            }
        }catch (Exception exception){
            System.out.println("Hata..: "+ exception.toString());
        }
        result = cr.list();
        return result;
    }

}
