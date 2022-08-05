package com.jpastudy.study.repository;

import com.jpastudy.study.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public Post find(Long id){
        return em.find(Post.class, id);
    }

    public List<Post> findAll(){
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public void delete(Long id){
        Post post = em.find(Post.class, id);
        if(post!=null) {
            em.remove(post);
        }
    }


    public List<Post> findBySubject(String subject){
        return em.createQuery("select p from Post p where p.subject like :subject", Post.class)
                .setParameter("subject", "%"+subject+"%")
                .getResultList();
    }

}
